package com.sammy.malum.common.block.curiosities.totem;

import com.sammy.malum.common.item.curiosities.tools.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.data.*;
import io.github.fabricators_of_create.porting_lib.tool.ToolActions;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;


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
        boolean success = false;
        if (held.getItem() instanceof TotemicStaffItem && !totemPoleState.equals(TotemPoleState.ACTIVE) && !totemPoleState.equals(TotemPoleState.CHARGING)) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            totemPoleState = totemPoleState.equals(TotemPoleState.INACTIVE) ? TotemPoleState.VISUAL_ONLY : TotemPoleState.INACTIVE;
            success = true;
        }
        else if (held.canPerformAction(ToolActions.AXE_STRIP)) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            if (type != null) {
                level.setBlockAndUpdate(worldPosition, logBlock.defaultBlockState());
                success = true;
                onBreak(player);
            }
        }
        if (success) {
            ParticleEffectTypeRegistry.TOTEM_POLE_ACTIVATED.createPositionedEffect(level, new PositionEffectData(worldPosition));
            level.playSound(null, worldPosition, SoundRegistry.TOTEM_ENGRAVE.get(), SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
            if (isSoulwood) {
                level.playSound(null, worldPosition, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
            }
            BlockHelper.updateState(level, worldPosition);
            return InteractionResult.SUCCESS;
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
            type = SpiritHarvestHandler.getSpiritType(compound.getString("type"));
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
            if (type != null && totemPoleState.equals(TotemPoleState.ACTIVE)) {
                TotemParticleEffects.activeTotemPoleParticles(this);
            }
        }
    }

    public void setSpirit(MalumSpiritType type) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_ENGRAVE.get(), SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
        level.playSound(null, worldPosition, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
        ParticleEffectTypeRegistry.TOTEM_POLE_ACTIVATED.createPositionedEffect(level, new PositionEffectData(worldPosition));
        this.type = type;
        this.chargeProgress = 10;
        BlockHelper.updateState(level, worldPosition);
    }

    public void riteStarting(TotemBaseBlockEntity totemBase, int height) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGE.get(), SoundSource.BLOCKS, 1, 0.9f + 0.2f * height);
        ParticleEffectTypeRegistry.TOTEM_POLE_ACTIVATED.createPositionedEffect(level, new PositionEffectData(worldPosition));
        this.totemBaseYLevel = worldPosition.getY() - height;
        this.totemBase = totemBase;
        this.totemPoleState = TotemPoleState.CHARGING;
        BlockHelper.updateState(level, worldPosition);
    }

    public void setState(TotemPoleState state) {
        this.totemPoleState = state;
        BlockHelper.updateState(level, worldPosition);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if (level.isClientSide) {
            return;
        }
        BlockPos basePos = new BlockPos(worldPosition.getX(), totemBaseYLevel, worldPosition.getZ());
        if (level.getBlockEntity(basePos) instanceof TotemBaseBlockEntity base && base.isActiveOrAssembling()) {
            base.onBreak(player);
        }
    }
}