package com.sammy.malum.common.blockentity.mirror;

import com.sammy.malum.common.entity.spirit.MirrorItemEntity;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.items.IItemHandler;

public class EmitterMirrorBlockEntity extends MirrorBlockEntity {
    public EmitterMirrorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.EMITTER_MIRROR.get(), pos, state);
    }

    @Override
    public void attachedTick(IItemHandler handler) {
        for (int i = handler.getSlots() - 1; i >= 0; i--) {
            ItemStack stack = handler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                cooldown = 20;
                MirrorItemEntity entity = new MirrorItemEntity(level, getBlockState().getValue(BlockStateProperties.FACING),stack.split(stack.getCount()),getBlockPos());
                level.addFreshEntity(entity);
                break;
            }
        }
    }
}
