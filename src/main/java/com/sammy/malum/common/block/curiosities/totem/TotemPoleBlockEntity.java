package com.sammy.malum.common.block.curiosities.totem;

import com.google.common.collect.Sets;
import com.sammy.malum.common.block.storage.stand.ItemStandBlockEntity;
import com.sammy.malum.common.item.curiosities.tools.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.TotemPoleActivationEffectPacket;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Set;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class TotemPoleBlockEntity extends LodestoneBlockEntity {

    public enum TotemPoleState {
        INACTIVE,
        VISUAL_ONLY,
        CHARGING,
        ACTIVE
    }

    public MalumSpiritType type;
    public TotemPoleState totemPoleState = TotemPoleState.INACTIVE;
    public TotemBaseBlockEntity totemBase;
    public int totemBaseYLevel;
    public int chargeProgress;

    public final boolean isSoulwood;
    public final Block logBlock;
    public final Direction direction;

    public TotemPoleBlockEntity(BlockEntityType<? extends TotemPoleBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.isSoulwood = ((TotemPoleBlock<?>) state.getBlock()).isSoulwood;
        this.logBlock = ((TotemPoleBlock<?>) state.getBlock()).logBlock.get();
        this.direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
    }

    public TotemPoleBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.TOTEM_POLE.get(), pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.getItem() instanceof TotemicStaffItem && !totemPoleState.equals(TotemPoleState.ACTIVE) && !totemPoleState.equals(TotemPoleState.CHARGING)) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            totemPoleState = totemPoleState.equals(TotemPoleState.INACTIVE) ? TotemPoleState.VISUAL_ONLY : TotemPoleState.INACTIVE;
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemPoleActivationEffectPacket(type.getPrimaryColor(), worldPosition));
            level.playSound(null, worldPosition, SoundRegistry.TOTEM_ENGRAVE.get(), SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
            if (isSoulwood) {
                level.playSound(null, worldPosition, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
            }
            BlockHelper.updateState(level, worldPosition);
            return InteractionResult.SUCCESS;
        }
        else if (held.canPerformAction(ToolActions.AXE_STRIP)) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            if (type != null) {
                level.setBlockAndUpdate(worldPosition, logBlock.defaultBlockState());
                MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemPoleActivationEffectPacket(type.getPrimaryColor(), worldPosition));
                level.playSound(null, worldPosition, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1, 1);
                if (isSoulwood) {
                    level.playSound(null, worldPosition, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
                }
                onBreak(player);
                return InteractionResult.SUCCESS;
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (type != null) {
            compound.putString("type", type.identifier);
        }
        if (!totemPoleState.equals(TotemPoleState.INACTIVE)) {
            compound.putInt("totemPoleState", totemPoleState.ordinal());
        }
        if (chargeProgress != 0) {
            compound.putInt("chargeProgress", chargeProgress);
        }
        if (totemBaseYLevel != 0) {
            compound.putInt("totemBaseYLevel", totemBaseYLevel);
        }
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.contains("type")) {
            type = SpiritHelper.getSpiritType(compound.getString("type"));
        }
        totemPoleState = compound.contains("totemPoleState") ? TotemPoleState.values()[compound.getInt("totemPoleState")] : TotemPoleState.INACTIVE;
        chargeProgress = compound.getInt("chargeProgress");
        totemBaseYLevel = compound.getInt("totemBaseYLevel");
        super.load(compound);
    }

    @Override
    public void init() {
        super.init();
        if (level.getBlockEntity(new BlockPos(getBlockPos().getX(), totemBaseYLevel, getBlockPos().getZ())) instanceof TotemBaseBlockEntity totemBaseBlockEntity) {
            totemBase = totemBaseBlockEntity;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (totemPoleState.equals(TotemPoleState.INACTIVE)) {
            chargeProgress = chargeProgress > 0 ? chargeProgress - 1 : 0;
        } else {
            int cap = totemPoleState.equals(TotemPoleState.CHARGING) ? 10 : 20;
            chargeProgress = chargeProgress < cap ? chargeProgress + 1 : cap;
        }
        if (level.isClientSide) {
            if (type != null && !totemPoleState.equals(TotemPoleState.INACTIVE)) {
                passiveParticles();
                if (totemBase != null && totemBase.rite != null) {
                    getFilters().forEach(this::filterParticles);
                }
            }
        }
    }

    public Set<ItemStandBlockEntity> getFilters() {
        Set<ItemStandBlockEntity> standBlockEntities = Sets.newHashSet();
        for (Direction value : Direction.values()) {
            BlockEntity blockEntity = level.getBlockEntity(worldPosition.relative(value));
            if (blockEntity instanceof ItemStandBlockEntity standBlockEntity) {
                standBlockEntities.add(standBlockEntity);
            }
        }
        return standBlockEntities;
    }

    public void setSpirit(MalumSpiritType type) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_ENGRAVE.get(), SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
        level.playSound(null, worldPosition, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemPoleActivationEffectPacket(type.getPrimaryColor(), worldPosition));
        this.type = type;
        this.chargeProgress = 10;
        BlockHelper.updateState(level, worldPosition);
    }

    public void riteStarting(TotemBaseBlockEntity totemBase, int height) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGE.get(), SoundSource.BLOCKS, 1, 0.9f + 0.2f * height);
        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemPoleActivationEffectPacket(type.getPrimaryColor(), worldPosition));
        this.totemBaseYLevel = worldPosition.getY() - height;
        this.totemBase = totemBase;
        this.totemPoleState = TotemPoleState.CHARGING;
        BlockHelper.updateState(level, worldPosition);
    }

    public void enable() {
        this.totemPoleState = TotemPoleState.ACTIVE;
        BlockHelper.updateState(level, worldPosition);
    }

    public void disable() {
        this.totemPoleState = TotemPoleState.INACTIVE;
        BlockHelper.updateState(level, worldPosition);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if (level.isClientSide) {
            return;
        }
        BlockPos basePos = new BlockPos(worldPosition.getX(), totemBaseYLevel, worldPosition.getZ());
        if (level.getBlockEntity(basePos) instanceof TotemBaseBlockEntity base) {
            if (base.active) {
                base.endRite();
            }
        }
    }

    public void filterParticles(ItemStandBlockEntity itemStandBlockEntity) {
        if (level.getGameTime() % 6L == 0) {
            if (!itemStandBlockEntity.inventory.getStackInSlot(0).isEmpty()) {
                Vec3 itemPos = itemStandBlockEntity.getItemPos();
                WorldParticleBuilder.create(LodestoneParticleRegistry.STAR_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.04f, 0.1f, 0f).build())
                        .setScaleData(GenericParticleData.create(0.5f, 1f + level.random.nextFloat() * 0.1f, 0).setEasing(Easing.QUINTIC_IN, Easing.CUBIC_IN_OUT).build())
                        .setSpinData(SpinParticleData.create(0, 0.2f, 0).setSpinOffset((level.getGameTime() * 0.02f) % 360).setEasing(Easing.CUBIC_IN, Easing.EXPO_IN).build())
                        .setColorData(ColorParticleData.create(type.getPrimaryColor(), type.getSecondaryColor()).setEasing(Easing.BOUNCE_IN_OUT).setCoefficient(0.5f).build())
                        .setLifetime(25)
                        .setRandomOffset(0.1)
                        .setRandomMotion(0.02f)
                        .setRandomMotion(0.0025f, 0.0025f)
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, itemPos.x, itemPos.y, itemPos.z);
            }
        }
    }

    public void passiveParticles() {
        if (level.getGameTime() % 6L == 0) {
            Color color = type.getPrimaryColor();
            Color endColor = type.getSecondaryColor();
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0, 0.06f, 0.12f).build())
                    .setSpinData(SpinParticleData.create(0.2f).build())
                    .setScaleData(GenericParticleData.create(0, 0.4f, 0).setEasing(Easing.LINEAR, Easing.CIRC_IN_OUT).build())
                    .setColorData(ColorParticleData.create(color, endColor).setEasing(Easing.SINE_IN).setCoefficient(0.5f).build())
                    .setLifetime(35)
                    .addMotion(0, Mth.nextFloat(level.random, -0.03f, 0.03f), 0)
                    .enableNoClip()
                    .setRandomOffset(0.1f, 0.2f)
                    .setRandomMotion(0.01f, 0.02f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .surroundBlock(level, worldPosition, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);

            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0, 0.06f, 0.03f).build())
                    .setSpinData(SpinParticleData.create(0.1f).build())
                    .setScaleData(GenericParticleData.create(0f, 0.55f, 0.3f).build())
                    .setColorData(ColorParticleData.create(color, endColor).setCoefficient(0.5f).build())
                    .setLifetime(60)
                    .addMotion(0, Mth.nextFloat(level.random, -0.03f, 0.03f), 0)
                    .setRandomOffset(0.1f, 0.2f)
                    .enableNoClip()
                    .setRandomMotion(0.01f, 0.02f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .surroundBlock(level, worldPosition, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
        }
    }
}