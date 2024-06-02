package com.sammy.malum.common.block.curiosities.obelisk.brilliant;

import com.sammy.malum.common.block.curiosities.obelisk.ObeliskCoreBlockEntity;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;

import java.util.function.Supplier;

public class BrilliantObeliskBlockEntity extends ObeliskCoreBlockEntity {
    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.BRILLIANT_OBELISK_COMPONENT.get().defaultBlockState())));

    public BrilliantObeliskBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.BRILLIANT_OBELISK.get(), STRUCTURE.get(), pos, state);
    }
}