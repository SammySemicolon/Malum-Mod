package com.sammy.malum.common.block.storage.stand;

import com.sammy.malum.common.block.storage.MalumItemHolderBlockEntity;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;


public class ItemStandBlockEntity extends MalumItemHolderBlockEntity {

    public ItemStandBlockEntity(BlockEntityType<? extends ItemStandBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemStandBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.ITEM_STAND.get(), pos, state);
    }

    @Override
    public Vec3 getItemOffset(float partialTicks) {
        Direction direction = getBlockState().getValue(BlockStateProperties.FACING);
        float gameTime = level.getGameTime() + partialTicks;
        float xOffset = direction.getStepX() * 0.25f;
        float yOffset = direction.getStepY() * 0.1f + (inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem ? (float) Math.sin((gameTime % 360) / 20f) * 0.05f : 0);
        float zOffset = direction.getStepZ() * 0.25f;
        return new Vec3(0.5f - xOffset, 0.5f - yOffset, 0.5f - zOffset);
    }
}
