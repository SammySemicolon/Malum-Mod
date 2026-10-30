package com.sammy.malum.common.block.building.banner.alchemized;

import com.sammy.malum.common.block.building.banner.MalumBannerBlock;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class AlchemizedBannerBlock extends MalumBannerBlock<AlchemizedBannerBlockEntity> {

    private final DyeColor color;

    public AlchemizedBannerBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return color;
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        final ItemStack stack = super.getCloneItemStack(state, target, level, pos, player);
        if (level.getBlockEntity(pos) instanceof AlchemizedBannerBlockEntity banner) {
            stack.set(MalumDataComponents.FANCY_BANNER_PATTERN, banner.patternData);
        }
        return stack;
    }
}
