package com.sammy.malum.common.block.storage;

import com.sammy.malum.client.*;
import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;

public class ItemPedestalBlockEntity extends ItemHolderBlockEntity implements IAltarProvider {

    public ItemPedestalBlockEntity(BlockEntityType<? extends ItemPedestalBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public ItemPedestalBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.ITEM_PEDESTAL.get(), pos, state);
        inventory = new LodestoneBlockEntityInventory(1, 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    public LodestoneBlockEntityInventory getInventoryForAltar() {
        return inventory;
    }

    @Override
    public Vec3 getItemPosForAltar() {
        return getItemPos();
    }

    @Override
    public BlockPos getBlockPosForAltar() {
        return worldPosition;
    }

    public Vec3 getItemPos() {
        return BlockHelper.fromBlockPos(getBlockPos()).add(itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.1f, 0.5f);
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem item) {
                Vec3 pos = getItemPos();
                double x = pos.x;
                double y = pos.y + Math.sin((level.getGameTime() ) / 20f) * 0.1f;
                double z = pos.z;
                ParticleEffects.spawnSpiritGlimmerParticles(level, x, y, z, item.type.getPrimaryColor(), item.type.getSecondaryColor());
            }
        }
    }
}
