package com.sammy.malum.common.block.storage.pedestal;

import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;

public class ItemPedestalBlockEntity extends MalumItemHolderBlockEntity {

    public static final Vec3 PEDESTAL_ITEM_OFFSET = new Vec3(0.5f, 1.1f, 0.5f);

    public ItemPedestalBlockEntity(BlockEntityType<? extends ItemPedestalBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemPedestalBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.ITEM_PEDESTAL.get(), pos, state);
    }

    @Override
    public Vec3 getItemOffset() {
        if (inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem) {
            return PEDESTAL_ITEM_OFFSET.add(0, (float)Math.sin((level.getGameTime() % 360) / 20f) * 0.05f, 0);
        }
        return PEDESTAL_ITEM_OFFSET;
    }
}