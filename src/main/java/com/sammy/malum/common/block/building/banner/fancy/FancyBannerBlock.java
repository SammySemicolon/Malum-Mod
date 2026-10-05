package com.sammy.malum.common.block.building.banner.fancy;

import com.sammy.malum.common.block.building.banner.MalumBannerBlock;
import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlockEntity;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class FancyBannerBlock extends MalumBannerBlock<FancyBannerBlockEntity> {

    private final DyeColor color;

    public FancyBannerBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return color;
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        final ItemStack stack = super.getCloneItemStack(state, target, level, pos, player);
        if (level.getBlockEntity(pos) instanceof FancyBannerBlockEntity banner) {
            stack.set(MalumDataComponents.FANCY_BANNER_PATTERN, banner.patternData);
        }
        return stack;
    }
}
