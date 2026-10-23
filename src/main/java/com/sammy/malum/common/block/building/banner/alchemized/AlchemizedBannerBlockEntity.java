package com.sammy.malum.common.block.building.banner.alchemized;

import com.sammy.malum.common.block.building.banner.MalumBannerBlockEntity;
import com.sammy.malum.common.data.component.banner.FancyBannerDataComponent;
import com.sammy.malum.registry.common.block.MalumBlockEntities;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

public class AlchemizedBannerBlockEntity extends MalumBannerBlockEntity {

    public FancyBannerDataComponent patternData;
    public final DyeColor color;

    public AlchemizedBannerBlockEntity(BlockPos pos, BlockState state) {
        super(MalumBlockEntities.FANCY_BANNER.get(), pos, state);
        color = ((AlchemizedBannerBlock)state.getBlock()).getColor();
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