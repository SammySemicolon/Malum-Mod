package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.totem.TotemBaseBlock;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.registry.content.SpiritRiteRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import com.sammy.malum.core.systems.tile.SimpleTileEntity;
import com.sammy.malum.network.packets.particle.totem.TotemParticlePacket;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemBaseTileEntity extends SimpleTileEntity implements ITickableTileEntity {
    public TotemBaseTileEntity() {
        super(TileEntityRegistry.TOTEM_BASE_TILE_ENTITY.get());
    }

    public MalumRiteType rite;
    public ArrayList<MalumSpiritType> spirits = new ArrayList<>();
    public boolean active;
    public int progress;
    public int height;

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
        super.readData(compound);
    }

    @Override
    public void tick() {
        if (MalumHelper.areWeOnServer(world)) {
            if (rite != null) {
                progress++;
                if (progress >= rite.interval()) {
                    TotemBaseBlock baseBlock = (TotemBaseBlock) getBlockState().getBlock();
                    rite.executeRite((ServerWorld) world, pos, baseBlock.corrupted);
                    progress = 0;
                }
            } else if (active) {
                progress--;
                if (progress <= 0) {
                    height++;
                    BlockPos polePos = pos.up(height);
                    if (world.getTileEntity(polePos) instanceof TotemPoleTileEntity) {
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
                }
            }
        }
    }

    public void disableOtherRites(MalumRiteType rite) {
        int range = rite.range();
        ArrayList<BlockPos> totemBases = new ArrayList<>(MalumHelper.getBlocks(pos, range, b -> world.getTileEntity(b) instanceof TotemBaseTileEntity && !b.equals(pos)));
        for (BlockPos basePos : totemBases) {
            TotemBaseTileEntity tileEntity = (TotemBaseTileEntity) world.getTileEntity(basePos);
            if (rite.equals(tileEntity.rite)) {
                tileEntity.endRite();
            } else {
                if (basePos.withinDistance(pos, 0.5f + range * 0.5f)) {
                    tileEntity.endRite();
                }
            }
        }
    }

    public void addPole(BlockPos polePos) {
        Direction poleFacing = world.getBlockState(polePos).get(HORIZONTAL_FACING);
        ArrayList<TotemPoleTileEntity> poles = getPoles();
        if (poles.isEmpty() || poles.stream().allMatch(p -> p.getBlockState().get(HORIZONTAL_FACING).equals(poleFacing) && ((TotemPoleBlock) p.getBlockState().getBlock()).corrupted == ((TotemBaseBlock) getBlockState().getBlock()).corrupted)) {
            TotemPoleTileEntity tileEntity = (TotemPoleTileEntity) world.getTileEntity(polePos);
            if (tileEntity.type != null) {
                spirits.add(tileEntity.type);
                tileEntity.riteStarting(height);
            }
        }
    }

    public ArrayList<TotemPoleTileEntity> getPoles() {
        ArrayList<TotemPoleTileEntity> poles = new ArrayList<>();
        for (int i = 1; i <= height; i++) {
            if (world.getTileEntity(pos.up(i)) instanceof TotemPoleTileEntity) {
                poles.add((TotemPoleTileEntity) world.getTileEntity(pos.up(i)));
            }
        }
        return poles;
    }

    public void startRite() {
        active = true;
        MalumHelper.updateAndNotifyState(world, pos);
    }

    public void completeRite(MalumRiteType rite) {
        world.playSound(null, pos, SoundRegistry.TOTEM_CHARGED, SoundCategory.BLOCKS, 1, 1);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), TotemParticlePacket.fromSpirits(spirits, pos, true));
        getPoles().forEach(p -> p.riteComplete(height));
        progress = 0;
        if (rite.isInstant) {
            rite.riteEffect((ServerWorld) world, pos);
            resetTotem();
            return;
        }
        this.rite = rite;
        disableOtherRites(rite);
    }

    public void endRite() {
        world.playSound(null, pos, SoundRegistry.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 0.5f);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), TotemParticlePacket.fromSpirits(spirits, pos, false));
        resetTotem();
    }

    public void resetTotem() {
        getPoles().forEach(TotemPoleTileEntity::riteEnding);
        height = 0;
        rite = null;
        active = false;
        progress = 0;
        spirits.clear();
    }
}