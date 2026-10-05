package com.sammy.malum.common.block.building.banner.fancy;

import com.sammy.malum.common.block.building.banner.MalumBannerBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.ArtificeBlockConnectionData;
import com.sammy.malum.common.data.component.SoulwovenBannerPatternDataComponent;
import com.sammy.malum.common.data.component.banner.FancyBannerDataComponent;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.registry.common.MalumParticleEffectTypes;
import com.sammy.malum.registry.common.block.MalumBlockEntities;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import com.sammy.malum.registry.common.sound.MalumSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

import javax.annotation.Nullable;

public class FancyBannerBlockEntity extends MalumBannerBlockEntity {

    public FancyBannerDataComponent patternData;

    public FancyBannerBlockEntity(BlockPos pos, BlockState state) {
        super(MalumBlockEntities.FANCY_BANNER.get(), pos, state);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(MalumDataComponents.FANCY_BANNER_PATTERN, patternData);
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        patternData = componentInput.get(MalumDataComponents.FANCY_BANNER_PATTERN);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        if (patternData != null) {
            FancyBannerDataComponent.CODEC.encodeStart(NbtOps.INSTANCE, patternData).result().ifPresent(nbt -> tag.put("pattern_data", nbt));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        patternData = FancyBannerDataComponent.CODEC.parse(NbtOps.INSTANCE, tag.get("pattern_data")).result().orElse(null);
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("pattern_data");
    }
}