package com.sammy.malum.core.systems.blockentity;

import com.sammy.malum.MalumHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class SimpleInventoryBlockEntity extends SimpleBlockEntity
{
    public SimpleBlockEntityInventory inventory;

    public SimpleInventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        inventory.playerHandleItem(player.level, player, hand);
        MalumHelper.updateAndNotifyState(getBlockState(), player.level, getBlockPos());
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onBreak() {

        super.onBreak();
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        inventory.save(compound);
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        inventory.load(compound);
        super.load(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap);
    }
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
