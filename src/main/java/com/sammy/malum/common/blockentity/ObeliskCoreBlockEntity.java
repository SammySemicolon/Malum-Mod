package com.sammy.malum.common.blockentity;

import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.systems.multiblock.MultiBlockCoreEntity;
import com.sammy.malum.core.systems.multiblock.MultiBlockStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ObeliskCoreBlockEntity extends MultiBlockCoreEntity {
    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.RUNEWOOD_OBELISK_COMPONENT.get().defaultBlockState())));

    public ObeliskCoreBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.OBELISK.get(), pos, state);
    }

    @Override
    public MultiBlockStructure getStructure() {
        return STRUCTURE.get();
    }
}
