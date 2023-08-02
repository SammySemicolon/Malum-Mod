package com.sammy.malum.common.block.curiosities.totem;

import com.sammy.malum.common.block.storage.ItemStandBlockEntity;
import com.sammy.malum.common.packets.particle.curiosities.rite.SpiritRiteActivationEffectPacket;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.SpiritRiteRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemBaseBlockEntity extends LodestoneBlockEntity {

    public MalumRiteType rite;
    public List<MalumSpiritType> spirits = new ArrayList<>();
    public Set<BlockPos> poles = new HashSet<>();
    public Set<TotemPoleBlockEntity> cachedTotemPoleInstances = new HashSet<>();
    public Set<BlockPos> filters = new HashSet<>();
    public Set<ItemStandBlockEntity> cachedFilterInstances = new HashSet<>();
    public boolean active;
    public int progress;
    public int height;
    public boolean corrupted;
    public Direction direction;

    public TotemBaseBlockEntity(BlockEntityType<? extends TotemBaseBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.corrupted = ((TotemBaseBlock<?>) state.getBlock()).corrupted;
    }
    public TotemBaseBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.TOTEM_BASE.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            if (rite != null) {
                progress++;
                if (progress >= rite.getRiteTickRate(corrupted)) {
                    if (direction == null) {
                        BlockPos polePos = worldPosition.above(height);
                        if (level.getBlockEntity(polePos) instanceof TotemPoleBlockEntity pole) {
                            direction = pole.direction;
                        }
                    }
                    rite.executeRite(this);
                    progress = 0;
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
            } else if (active) {
                progress--;
                if (progress <= 0) {
                    height++;
                    BlockPos polePos = worldPosition.above(height);
                    if (level.getBlockEntity(polePos) instanceof TotemPoleBlockEntity pole) {
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
    public void onBreak(@Nullable Player player) {
        if (!level.isClientSide) {
            poles.forEach(p -> {
                if (level.getBlockEntity(p) instanceof TotemPoleBlockEntity pole) {
                    pole.riteEnding();
                }
            });
            if (height > 1) {
                level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGE.get(), SoundSource.BLOCKS, 1, 0.5f);
                MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new SpiritRiteActivationEffectPacket(spirits.stream().map(s -> s.identifier).toList(), worldPosition.above()));
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
    public void init() {
        super.init();
        poles.forEach(p -> {
            if (level.getBlockEntity(p) instanceof TotemPoleBlockEntity totemPole) {
                cachedTotemPoleInstances.add(totemPole);
                totemPole.getFilters().forEach(this::addFilter);
            }
        });
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (rite != null) {
            compound.putString("rite", rite.identifier);
        }
        if (!spirits.isEmpty()) {
            compound.putInt("spiritCount", spirits.size());
            for (int i = 0; i < spirits.size(); i++) {
                MalumSpiritType type = spirits.get(i);
                compound.putString("spirit_" + i, type.identifier);
            }
        }
        compound.putBoolean("active", active);
        if (active) {
            compound.putInt("progress", progress);
            compound.putInt("height", height);
        }
        if (direction != null) {
            compound.putString("direction", direction.name());
        }
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
        poles.clear();
        filters.clear();
        cachedTotemPoleInstances.clear();
        for (int i = 1; i <= height; i++) {
            poles.add(new BlockPos(worldPosition.getX(), worldPosition.getY() + i, worldPosition.getZ()));
        }
        direction = Direction.byName(compound.getString("direction"));
        corrupted = compound.getBoolean("corrupted");
        progress = compound.getInt("progress");
        super.load(compound);
    }
    public void addFilter(ItemStandBlockEntity itemStand) {
        filters.add(itemStand.getBlockPos());
        cachedFilterInstances.add(itemStand);
    }
    public void addPole(TotemPoleBlockEntity pole) {
        Direction direction = pole.getBlockState().getValue(HORIZONTAL_FACING);
        if (poles.isEmpty()) {
            this.direction = direction;
        }
        if (pole.corrupted == corrupted && direction.equals(this.direction)) {
            if (pole.type != null) {
                spirits.add(pole.type);
                poles.add(pole.getBlockPos());
                filters.addAll(pole.getFilters().stream().map(BlockEntity::getBlockPos).collect(Collectors.toList()));
                cachedFilterInstances.addAll(pole.getFilters());
                pole.riteStarting(this, height);
            }
        }
    }

    public void completeRite(MalumRiteType rite) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_ACTIVATED.get(), SoundSource.BLOCKS, 1, 0.75f + height * 0.1f);
        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new SpiritRiteActivationEffectPacket(spirits.stream().map(s -> s.identifier).collect(Collectors.toCollection(ArrayList::new)), worldPosition.above()));
        poles.forEach(p -> {
            if (level.getBlockEntity(p) instanceof TotemPoleBlockEntity pole) {
                pole.riteComplete();
            }
        });
        progress = 0;
        rite.executeRite(this);
        if (rite.isOneAndDone(corrupted)) {
            return;
        }
        this.rite = rite;
        disableOtherRites();
    }

    public void disableOtherRites() {
        int range = rite.getRiteRadius(corrupted);
        BlockHelper.getBlockEntitiesStream(TotemBaseBlockEntity.class, level, rite.getRiteEffectCenter(this), range).filter(blockEntity -> !blockEntity.equals(this) && rite.equals(blockEntity.rite) && corrupted == blockEntity.corrupted).forEach(TotemBaseBlockEntity::endRite);

        BlockHelper.getBlockEntitiesStream(TotemBaseBlockEntity.class, level, worldPosition, 10).filter(blockEntity -> !blockEntity.equals(this) && rite.equals(blockEntity.rite) && corrupted == blockEntity.corrupted).forEach(b -> {
            b.tryDisableRite(this);
        });

    }
    public void tryDisableRite(TotemBaseBlockEntity target) {
        int range = rite.getRiteRadius(corrupted);

        Collection<TotemBaseBlockEntity> otherTotems = BlockHelper.getBlockEntities(TotemBaseBlockEntity.class, level, rite.getRiteEffectCenter(this), range);
        if (otherTotems.contains(target)) {
            endRite();
        }
    }

    public void startRite() {
        resetValues();
        active = true;
    }

    public void endRite() {
        if (height > 1) {
            level.playSound(null, worldPosition, SoundRegistry.TOTEM_CANCELLED.get(), SoundSource.BLOCKS, 1, 1);
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new SpiritRiteActivationEffectPacket(spirits.stream().map(s -> s.identifier).collect(Collectors.toCollection(ArrayList::new)), worldPosition.above()));
        }
        resetRite();
    }

    public void resetRite() {
        poles.forEach(p -> {
            if (level.getBlockEntity(p) instanceof TotemPoleBlockEntity pole) {
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
