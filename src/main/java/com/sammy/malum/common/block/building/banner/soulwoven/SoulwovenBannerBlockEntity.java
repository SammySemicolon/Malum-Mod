package com.sammy.malum.common.block.building.banner.soulwoven;

import com.sammy.malum.common.block.building.banner.MalumBannerBlockEntity;
import com.sammy.malum.common.data.component.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.*;
import net.minecraft.core.component.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.neoforge.common.*;
import team.lodestar.lodestone.helpers.block.*;

import javax.annotation.*;

public class SoulwovenBannerBlockEntity extends MalumBannerBlockEntity {

    public SpiritArcanaType spirit;
    public SoulwovenBannerPatternDataComponent patternData;
    public boolean intense;

    public SoulwovenBannerBlockEntity(BlockPos pos, BlockState state) {
        super(MalumBlockEntities.SOULWOVEN_BANNER.get(), pos, state);
        this.patternData = SoulwovenBannerPatternDataComponent.DEFAULT;
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(MalumDataComponents.SOULWOVEN_BANNER_PATTERN, patternData);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        patternData = componentInput.get(MalumDataComponents.SOULWOVEN_BANNER_PATTERN);
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player pPlayer, ItemStack pStack, InteractionHand pHand) {
        if (level instanceof ServerLevel serverLevel) {
            if (pStack.getItem() instanceof SpiritShardItem shardItem) {
                if (spirit != null && shardItem.matches(spirit) && intense) {
                    return super.onUseWithItem(pPlayer, pStack, pHand);
                }
                if (shardItem.matches(MalumSpiritTypes.UMBRAL_SPIRIT)) {
                    return super.onUseWithItem(pPlayer, pStack, pHand);
                }
                if (!pPlayer.isCreative()) {
                    pStack.shrink(1);
                }
                setSpirit(serverLevel, shardItem.getSpirit());
                pPlayer.swing(pHand, true);
            }
            if (pStack.canPerformAction(ItemAbilities.SHEARS_DISARM)) {
                if (spirit != null) {
                    setSpirit(serverLevel, null);
                    pPlayer.swing(pHand, true);
                }
            }
        }
        return super.onUseWithItem(pPlayer, pStack, pHand);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (spirit != null) {
            spirit.save(tag);
        }
        tag.putBoolean("intense", intense);
        patternData.save(tag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        spirit = SpiritArcanaType.load(tag).orElse(null);
        intense = tag.getBoolean("intense");
        patternData = SoulwovenBannerPatternDataComponent.load(tag);
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("pattern");
    }

    public void setSpirit(ServerLevel level, @Nullable SpiritArcanaType newSpirit) {
        var effectType = newSpirit != null ? MalumParticleEffectTypes.APPLY_SOULWOVEN_BANNER_GLOW : MalumParticleEffectTypes.REMOVE_SOULWOVEN_BANNER_GLOW;
        var particle = effectType.createEffect(worldPosition);
        if (newSpirit != null) {
            particle.color(newSpirit);
        }
        else if (spirit != null) {
            particle.color(spirit);
        }
        particle.spawn(level);
        intense = spirit == newSpirit;
        spirit = newSpirit;
        level.playSound(null, worldPosition, MalumSoundEvents.TOTEM_ENGRAVE.get(), SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
        level.playSound(null, worldPosition, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
        BlockStateHelper.updateState(level, worldPosition);
    }
}