package com.sammy.malum.common.blockentity;

import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.multiblock.MultiBlockCoreEntity;
import com.sammy.malum.core.systems.multiblock.MultiBlockStructure;
import com.sammy.malum.core.systems.multiblock.MultiBlockStructure.StructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

import static com.sammy.malum.common.block.fusion_plate.FusionPlateComponentBlock.CORNER;
import static com.sammy.malum.core.setup.block.BlockRegistry.SOULWOOD_FUSION_PLATE_COMPONENT;
import static net.minecraft.core.Direction.*;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class FusionPlateBlockEntity extends MultiBlockCoreEntity {
    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(
            new StructurePiece(-1, 0, 0, SOULWOOD_FUSION_PLATE_COMPONENT.get().defaultBlockState().setValue(HORIZONTAL_FACING, WEST)),
            new StructurePiece(1, 0, 0, SOULWOOD_FUSION_PLATE_COMPONENT.get().defaultBlockState().setValue(HORIZONTAL_FACING, EAST)),
            new StructurePiece(0, 0, -1, SOULWOOD_FUSION_PLATE_COMPONENT.get().defaultBlockState().setValue(HORIZONTAL_FACING, NORTH)),
            new StructurePiece(0, 0, 1, SOULWOOD_FUSION_PLATE_COMPONENT.get().defaultBlockState().setValue(HORIZONTAL_FACING, SOUTH)),
            new StructurePiece(-1, 0, -1, SOULWOOD_FUSION_PLATE_COMPONENT.get().defaultBlockState().setValue(HORIZONTAL_FACING, WEST).setValue(CORNER, true)),
            new StructurePiece(1, 0, 1, SOULWOOD_FUSION_PLATE_COMPONENT.get().defaultBlockState().setValue(HORIZONTAL_FACING, EAST).setValue(CORNER, true)),
            new StructurePiece(1, 0, -1, SOULWOOD_FUSION_PLATE_COMPONENT.get().defaultBlockState().setValue(HORIZONTAL_FACING, NORTH).setValue(CORNER, true)),
            new StructurePiece(-1, 0, 1, SOULWOOD_FUSION_PLATE_COMPONENT.get().defaultBlockState().setValue(HORIZONTAL_FACING, SOUTH).setValue(CORNER, true))));

    public FusionPlateBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.FUSION_PLATE.get(), pos, state);
    }

    @Override
    public MultiBlockStructure getStructure() {
        return STRUCTURE.get();
    }


}
