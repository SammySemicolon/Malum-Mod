package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.packets.particle.TotemParticlePacket;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.registry.content.SpiritRiteRegistry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemBaseTileEntity extends SimpleBlockEntity {

    public MalumRiteType rite;
    public ArrayList<MalumSpiritType> spirits = new ArrayList<>();
    public boolean active;
    public int progress;
    public int height;
    public boolean corrupted;

    public TotemBaseTileEntity(BlockPos pos, BlockState state) {
        super(TileEntityRegistry.TOTEM_BASE_TILE_ENTITY.get(), pos, state);
        this.corrupted = ((TotemPoleBlock)state.getBlock()).corrupted;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        if (rite != null) {
            compound.putString("rite", rite.identifier);
        }
        compound.putInt("spiritCount", spirits.size());
        for (int i = 0; i < spirits.size(); i++) {
            MalumSpiritType type = spirits.get(i);
            compound.putString("spirit_" + i, type.identifier);
        }
        compound.putBoolean("active", active);
        compound.putInt("progress", progress);
        compound.putInt("height", height);
        compound.putBoolean("corrupted", corrupted);
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        rite = SpiritRiteRegistry.getRite(compound.getString("rite"));
        int size = compound.getInt("spiritCount");
        for (int i = 0; i < size; i++) {
            spirits.add(SpiritHelper.getSpiritType(compound.getString("spirit_" + i)));
        }
        active = compound.getBoolean("active");
        progress = compound.getInt("progress");
        height = compound.getInt("height");
        corrupted = compound.getBoolean("corrupted");
        super.load(compound);
    }

    public void tick() {
        if (rite != null) {
            progress++;
            if (progress >= rite.interval(corrupted)) {
                rite.executeRite(level, worldPosition, corrupted);
                progress = 0;
                MalumHelper.updateAndNotifyState(level, worldPosition);
            }
        } else if (active) {
            if (level.isClientSide) {
                progress--;
                if (progress <= 0) {
                    height++;
                    BlockPos polePos = worldPosition.above(height);
                    if (level.getBlockEntity(polePos) instanceof TotemPoleTileEntity) {
                        addPole(polePos);
                    } else {
                        MalumRiteType rite = SpiritRiteRegistry.getRite(spirits);
                        if (rite == null) {
                            endRite();
                        } else {
                            completeRite(rite);
                        }
                    }
                    progress = 20;
                    MalumHelper.updateAndNotifyState(level, worldPosition);
                }
            }
        }
    }

    public void disableOtherRites(MalumRiteType rite) {
        int range = rite.range(corrupted);
        ArrayList<BlockPos> totemBases = new ArrayList<>(MalumHelper.getBlocks(worldPosition, range, b -> level.getBlockEntity(b) instanceof TotemBaseTileEntity && !b.equals(worldPosition)));
        for (BlockPos basePos : totemBases) {
            TotemBaseTileEntity tileEntity = (TotemBaseTileEntity) level.getBlockEntity(basePos);
            if (rite.equals(tileEntity.rite)) {
                tileEntity.endRite();
            } else if (tileEntity.rite != null) {
                if (basePos.closerThan(worldPosition, 0.5f + range * 0.5f)) {
                    tileEntity.endRite();
                }
            }
        }
    }

    public void addPole(BlockPos polePos) {
        Direction poleFacing = level.getBlockState(polePos).getValue(HORIZONTAL_FACING);
        ArrayList<TotemPoleTileEntity> poles = getPoles();
        if (poles.isEmpty() || poles.stream().allMatch(p -> p.getBlockState().getValue(HORIZONTAL_FACING).equals(poleFacing) && p.corrupted == corrupted)) {
            TotemPoleTileEntity tileEntity = (TotemPoleTileEntity) level.getBlockEntity(polePos);
            if (tileEntity.type != null) {
                spirits.add(tileEntity.type);
                tileEntity.riteStarting(height);
            }
        }
    }

    public ArrayList<TotemPoleTileEntity> getPoles() {
        ArrayList<TotemPoleTileEntity> poles = new ArrayList<>();
        for (int i = 1; i <= height; i++) {
            if (level.getBlockEntity(worldPosition.above(i)) instanceof TotemPoleTileEntity) {
                poles.add((TotemPoleTileEntity) level.getBlockEntity(worldPosition.above(i)));
            }
        }
        return poles;
    }

    public void startRite() {
        active = true;
        progress = 0;
        MalumHelper.updateAndNotifyState(level, worldPosition);
    }

    public void completeRite(MalumRiteType rite) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGED, SoundSource.BLOCKS, 1, 1);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemParticlePacket(spirits.stream().map(s -> s.color).collect(Collectors.toCollection(ArrayList::new)), worldPosition.getX(), worldPosition.getY()+1, worldPosition.getZ()));
        getPoles().forEach(p -> p.riteComplete(height));
        progress = 0;
        if (rite.isInstant) {
            rite.riteEffect(level, worldPosition);
            resetTotem();
            return;
        }
        this.rite = rite;
        disableOtherRites(rite);
        MalumHelper.updateAndNotifyState(level, worldPosition);
    }

    public void endRite() {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGE, SoundSource.BLOCKS, 1, 0.5f);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemParticlePacket(spirits.stream().map(s -> s.color).collect(Collectors.toCollection(ArrayList::new)), worldPosition.getX(), worldPosition.getY()+1, worldPosition.getZ()));
        resetTotem();
    }

    public void resetTotem() {
        getPoles().forEach(TotemPoleTileEntity::riteEnding);
        height = 0;
        rite = null;
        active = false;
        progress = 0;
        spirits.clear();
        MalumHelper.updateAndNotifyState(level, worldPosition);
    }
}