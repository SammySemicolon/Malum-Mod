package com.sammy.malum.common.block.storage.pedestal;

import com.sammy.malum.common.block.storage.MalumItemHolderBlockEntity;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ItemPedestalBlockEntity extends MalumItemHolderBlockEntity {

    public static final Vec3 PEDESTAL_ITEM_OFFSET = new Vec3(0.5f, 1.1f, 0.5f);

    public ItemPedestalBlockEntity(BlockEntityType<? extends ItemPedestalBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemPedestalBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.ITEM_PEDESTAL.get(), pos, state);
    }

    @Override
    public Vec3 getItemOffset(float partialTicks) {
        if (inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem) {
            float gameTime = level.getGameTime() + partialTicks;
            return PEDESTAL_ITEM_OFFSET.add(0, (float) Math.sin((gameTime % 360) / 20f) * 0.05f, 0);
        }
        return PEDESTAL_ITEM_OFFSET;
    }
}