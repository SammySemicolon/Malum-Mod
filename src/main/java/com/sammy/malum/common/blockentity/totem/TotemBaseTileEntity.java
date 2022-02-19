package com.sammy.malum.common.blockentity.totem;

import com.sammy.malum.common.block.totem.TotemBaseBlock;
import com.sammy.malum.common.packets.particle.TotemParticlePacket;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.SoundRegistry;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.setup.content.SpiritRiteRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.core.setup.PacketRegistry.INSTANCE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemBaseTileEntity extends SimpleBlockEntity {

    public MalumRiteType rite;
    public ArrayList<MalumSpiritType> spirits = new ArrayList<>();
    public ArrayList<BlockPos> poles = new ArrayList<>();
    public boolean active;
    public int progress;
    public int height;
    public boolean corrupted;
    public Direction direction;

    public TotemBaseTileEntity(BlockEntityType<? extends TotemBaseTileEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.corrupted = ((TotemBaseBlock<?>) state.getBlock()).corrupted;
    }
    public TotemBaseTileEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.TOTEM_BASE.get(), pos, state);
    }

    @Override
    public void tick() {
        if (rite != null) {
            progress++;
            if (progress >= rite.interval(corrupted)) {
                rite.executeRite(level, worldPosition, corrupted);
                progress = 0;
                if (!level.isClientSide) {
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
            }
        } else if (active) {
            if (!level.isClientSide) {
                progress--;
                if (progress <= 0) {
                    height++;
                    BlockPos polePos = worldPosition.above(height);
                    if (level.getBlockEntity(polePos) instanceof TotemPoleTileEntity pole) {
                        addPole(pole);
                    } else {
                        MalumRiteType rite = SpiritRiteRegistry.getRite(spirits);
                        if (rite == null) {
                            endRite();
                        } else {
                            completeRite(rite);
                            setChanged();
                        }
                    }
                    progress = 20;
                    BlockHelper.updateState(level, worldPosition);
                }
            }
        }
    }

    @Override
    public void onBreak() {
        if (!level.isClientSide) {
            poles.forEach(p -> {
                if (level.getBlockEntity(p) instanceof TotemPoleTileEntity pole) {
                    pole.riteEnding();
                }
            });
            if (height > 1) {
                level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGE, SoundSource.BLOCKS, 1, 0.5f);
                INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemParticlePacket(spirits.stream().map(s -> s.color).collect(Collectors.toCollection(ArrayList::new)), worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ()));
            }
        }
    }


    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (active && rite == null) {
            return InteractionResult.FAIL;
        }
        if (!level.isClientSide) {
            if (active) {
                endRite();
            } else {
                startRite();
            }
            BlockHelper.updateState(level, worldPosition);
        }
        player.swing(InteractionHand.MAIN_HAND, true);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
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
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        rite = SpiritRiteRegistry.getRite(compound.getString("rite"));
        int size = compound.getInt("spiritCount");
        spirits.clear();
        for (int i = 0; i < size; i++) {
            spirits.add(SpiritHelper.getSpiritType(compound.getString("spirit_" + i)));
        }
        active = compound.getBoolean("active");
        progress = compound.getInt("progress");
        height = compound.getInt("height");
        corrupted = compound.getBoolean("corrupted");
        poles.clear();
        for (int i = 1; i <= height; i++) {
            poles.add(new BlockPos(worldPosition.getX(), worldPosition.getY() + i, worldPosition.getZ()));
        }
        super.load(compound);
    }

    public void addPole(TotemPoleTileEntity pole) {
        Direction direction = pole.getBlockState().getValue(HORIZONTAL_FACING);
        if (poles.isEmpty()) {
            this.direction = direction;
        }
        if (pole.corrupted == corrupted && direction.equals(this.direction)) {
            if (pole.type != null) {
                spirits.add(pole.type);
                poles.add(pole.getBlockPos());
                pole.riteStarting(height);
            }
        }
    }

    public void disableOtherRites(MalumRiteType rite) {
        int range = rite.range(corrupted);
        List<TotemBaseTileEntity> totemBases = BlockHelper.getBlocks(worldPosition, range, b -> level.getBlockEntity(b) instanceof TotemBaseTileEntity && !b.equals(worldPosition)).stream().map(b -> (TotemBaseTileEntity) level.getBlockEntity(b)).collect(Collectors.toCollection(ArrayList::new));
        for (TotemBaseTileEntity blockEntity : totemBases) {
            if (rite.equals(blockEntity.rite)) {
                blockEntity.endRite();
            } else if (blockEntity.rite != null) {
                int otherRange = blockEntity.rite.range(blockEntity.corrupted);
                if (blockEntity.worldPosition.distSqr(worldPosition, false) <= (range * range) / 2f || worldPosition.distSqr(blockEntity.worldPosition, false) <= (otherRange * otherRange) / 2f) {
                    blockEntity.endRite();
                }
            }
        }
    }

    public void completeRite(MalumRiteType rite) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_ACTIVATED, SoundSource.BLOCKS, 1, 0.75f + height * 0.1f);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemParticlePacket(spirits.stream().map(s -> s.color).collect(Collectors.toCollection(ArrayList::new)), worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ()));
        poles.forEach(p -> {
            if (level.getBlockEntity(p) instanceof TotemPoleTileEntity pole) {
                pole.riteComplete();
            }
        });
        progress = 0;
        rite.executeRite(level, worldPosition, corrupted);
        if (rite.isInstant(corrupted)) {
            resetRite();
            return;
        }
        this.rite = rite;
        disableOtherRites(rite);
    }

    public void startRite() {
        resetValues();
        active = true;
    }

    public void endRite() {
        if (height > 1) {
            level.playSound(null, worldPosition, SoundRegistry.TOTEM_CANCELLED, SoundSource.BLOCKS, 1, 1);
            INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemParticlePacket(spirits.stream().map(s -> s.color).collect(Collectors.toCollection(ArrayList::new)), worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ()));
        }
        resetRite();
    }

    public void resetRite() {
        poles.forEach(p -> {
            if (level.getBlockEntity(p) instanceof TotemPoleTileEntity pole) {
                pole.riteEnding();
            }
        });
        resetValues();
    }

    public void resetValues() {
        height = 0;
        rite = null;
        active = false;
        progress = 0;
        spirits.clear();
        poles.clear();
    }
}