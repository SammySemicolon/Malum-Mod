package com.sammy.malum.common.block.item_storage;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class WoodItemPedestalBlock extends ItemPedestalBlock
{
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(4, 0, 4, 12, 3, 12),
            Block.box(5, 3, 5, 11, 11, 11),
            Block.box(4, 11, 4, 12, 13, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public WoodItemPedestalBlock(Properties properties)
    {
        super(properties);
    }

}
