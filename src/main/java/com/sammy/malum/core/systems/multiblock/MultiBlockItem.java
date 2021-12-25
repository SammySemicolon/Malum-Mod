package com.sammy.malum.core.systems.multiblock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class MultiBlockItem extends BlockItem {
    public final Supplier<MultiBlockStructure> structure;
    public MultiBlockItem(Block block, Properties properties, Supplier<MultiBlockStructure> structure) {
        super(block, properties);
        this.structure = structure;
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        if (!structure.get().canPlace(context.getClickedPos(), context.getLevel()))
        {
            return false;
        }
        return super.canPlace(context, state);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        structure.get().place(context.getClickedPos(), context.getLevel());
        return super.placeBlock(context, state);
    }
}
