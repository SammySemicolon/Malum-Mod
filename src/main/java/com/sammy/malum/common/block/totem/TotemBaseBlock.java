package com.sammy.malum.common.block.totem;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TotemBaseBlock<T extends TotemBaseBlockEntity> extends LodestoneEntityBlock<T>
{
    public final boolean corrupted;
    public TotemBaseBlock(Properties properties, boolean corrupted)
    {
        super(properties);
        this.corrupted = corrupted;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof TotemBaseBlockEntity totem) {
            return totem.active ? totem.spirits.size() : 0;
        }
        return 0;
    }
}
