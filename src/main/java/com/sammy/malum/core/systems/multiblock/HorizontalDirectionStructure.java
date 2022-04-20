package com.sammy.malum.core.systems.multiblock;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.List;

public class HorizontalDirectionStructure extends MultiBlockStructure{
    public HorizontalDirectionStructure(ArrayList<StructurePiece> structurePieces) {
        super(structurePieces);
    }

    @Override
    public void place(BlockPlaceContext context) {
        structurePieces.forEach(s -> s.place(context.getClickedPos(), context.getLevel(), s.state.setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite())));
    }

    public static HorizontalDirectionStructure of(StructurePiece... pieces) {
        return new HorizontalDirectionStructure(new ArrayList<>(List.of(pieces)));
    }

}
