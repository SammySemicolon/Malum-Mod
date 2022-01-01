package com.sammy.malum.core.systems.multiblock;

import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public abstract class MultiBlockCoreEntity extends SimpleBlockEntity {

    ArrayList<BlockPos> componentPositions = new ArrayList<>();

    public MultiBlockCoreEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        getStructure().structurePieces.forEach(p -> {
            Vec3i offset = p.offset;
            componentPositions.add(pos.offset(offset));
        });
    }

    public MultiBlockStructure getStructure() {
        return null;
    }

    @Override
    public void onBreak() {
        componentPositions.forEach(p -> {
            if (level.getBlockEntity(p) instanceof MultiBlockComponentEntity) {
                level.destroyBlock(p, false);
            }
        });
        if (level.getBlockEntity(worldPosition) instanceof MultiBlockCoreEntity)
        {
            level.destroyBlock(worldPosition, true);
        }
    }
}
