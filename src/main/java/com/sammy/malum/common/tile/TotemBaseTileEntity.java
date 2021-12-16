package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.totem.TotemBaseBlock;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.packets.particle.TotemParticlePacket;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.registry.content.SpiritRiteRegistry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.core.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.core.BlockPos;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemBaseTileEntity extends SimpleTileEntity implements ITickableTileEntity {

    public MalumRiteType rite;
    public ArrayList<MalumSpiritType> spirits = new ArrayList<>();
    public boolean active;
    public int progress;
    public int height;
    public boolean isCorrupted;

    public TotemBaseTileEntity() {
        super(TileEntityRegistry.TOTEM_BASE_TILE_ENTITY.get());
    }
    public TotemBaseTileEntity(boolean isCorrupted) {
        this();
        this.isCorrupted = isCorrupted;
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound) {
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
        compound.putBoolean("isCorrupted", isCorrupted);
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound) {
        rite = SpiritRiteRegistry.getRite(compound.getString("rite"));
        int size = compound.getInt("spiritCount");
        for (int i = 0; i < size; i++) {
            spirits.add(SpiritHelper.getSpiritType(compound.getString("spirit_" + i)));
        }
        active = compound.getBoolean("active");
        progress = compound.getInt("progress");
        height = compound.getInt("height");
        isCorrupted = compound.getBoolean("isCorrupted");
        super.readData(compound);
    }

    @Override
    public void tick() {
        if (rite != null) {
            progress++;
            if (progress >= rite.interval(isCorrupted)) {
                rite.executeRite(level, LevelPosition, isCorrupted);
                progress = 0;
                MalumHelper.updateAndNotifyState(level, LevelPosition);
            }
        } else if (active) {
            if (MalumHelper.areWeOnServer(level)) {
                progress--;
                if (progress <= 0) {
                    height++;
                    BlockPos polePos = LevelPosition.above(height);
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
                    MalumHelper.updateAndNotifyState(level, LevelPosition);
                }
            }
        }
    }

    public void disableOtherRites(MalumRiteType rite) {
        int range = rite.range(isCorrupted);
        ArrayList<BlockPos> totemBases = new ArrayList<>(MalumHelper.getBlocks(LevelPosition, range, b -> level.getBlockEntity(b) instanceof TotemBaseTileEntity && !b.equals(LevelPosition)));
        for (BlockPos basePos : totemBases) {
            TotemBaseTileEntity tileEntity = (TotemBaseTileEntity) level.getBlockEntity(basePos);
            if (rite.equals(tileEntity.rite)) {
                tileEntity.endRite();
            } else if (tileEntity.rite != null) {
                if (basePos.closerThan(LevelPosition, 0.5f + range * 0.5f)) {
                    tileEntity.endRite();
                }
            }
        }
    }

    public void addPole(BlockPos polePos) {
        Direction poleFacing = level.getBlockState(polePos).getValue(HORIZONTAL_FACING);
        ArrayList<TotemPoleTileEntity> poles = getPoles();
        if (poles.isEmpty() || poles.stream().allMatch(p -> p.getBlockState().getValue(HORIZONTAL_FACING).equals(poleFacing) && ((TotemPoleBlock) p.getBlockState().getBlock()).corrupted == ((TotemBaseBlock) getBlockState().getBlock()).corrupted)) {
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
            if (level.getBlockEntity(LevelPosition.above(i)) instanceof TotemPoleTileEntity) {
                poles.add((TotemPoleTileEntity) level.getBlockEntity(LevelPosition.above(i)));
            }
        }
        return poles;
    }

    public void startRite() {
        active = true;
        progress = 0;
        MalumHelper.updateAndNotifyState(level, LevelPosition);
    }

    public void completeRite(MalumRiteType rite) {
        level.playSound(null, LevelPosition, SoundRegistry.TOTEM_CHARGED, SoundCategory.BLOCKS, 1, 1);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(LevelPosition)), new TotemParticlePacket(spirits.stream().map(s -> s.color).collect(Collectors.toCollection(ArrayList::new)), LevelPosition.getX(), LevelPosition.getY()+1, LevelPosition.getZ()));
        getPoles().forEach(p -> p.riteComplete(height));
        progress = 0;
        if (rite.isInstant) {
            rite.riteEffect(level, LevelPosition);
            resetTotem();
            return;
        }
        this.rite = rite;
        disableOtherRites(rite);
        MalumHelper.updateAndNotifyState(level, LevelPosition);
    }

    public void endRite() {
        level.playSound(null, LevelPosition, SoundRegistry.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 0.5f);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(LevelPosition)), new TotemParticlePacket(spirits.stream().map(s -> s.color).collect(Collectors.toCollection(ArrayList::new)), LevelPosition.getX(), LevelPosition.getY()+1, LevelPosition.getZ()));
        resetTotem();
    }

    public void resetTotem() {
        getPoles().forEach(TotemPoleTileEntity::riteEnding);
        height = 0;
        rite = null;
        active = false;
        progress = 0;
        spirits.clear();
        MalumHelper.updateAndNotifyState(level, LevelPosition);
    }
}