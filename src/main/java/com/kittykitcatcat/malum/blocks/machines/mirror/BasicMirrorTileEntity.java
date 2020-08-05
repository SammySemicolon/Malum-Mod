package com.kittykitcatcat.malum.blocks.machines.mirror;

import com.kittykitcatcat.malum.blocks.utility.BasicTileEntity;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class BasicMirrorTileEntity extends BasicTileEntity implements ITickableTileEntity
{
    public BasicMirrorTileEntity()
    {
        super(ModTileEntities.basic_mirror_tile_entity);
    }
    public BasicMirrorTileEntity(TileEntityType type)
    {
        super(type);
    }
    public boolean cancelNextTransfer;
    public boolean transfer;
    public ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return true;
        }
        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return ItemStack.EMPTY;
        }
        @Override
        protected void onContentsChanged(int slot)
        {
            BasicMirrorTileEntity.this.markDirty();
            if (!world.isRemote)
            {
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    };
    public final LazyOptional<IItemHandler> lazyOptional = LazyOptional.of(() -> inventory);
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put("inventory", inventory.serializeNBT());
        compound.putBoolean("cancelNextTransfer", cancelNextTransfer);
        compound.putBoolean("canTransfer", transfer);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
    }
    @Override
    public void tick()
    {
        if (!world.isRemote)
        {
            if (world.getGameTime() % 10L == 0L)
            {
                if (cancelNextTransfer)
                {
                    transfer = false;
                    cancelNextTransfer = false;
                }
                else
                {
                    transfer = true;
                }
            }
            else if (transfer)
            {
                transfer = false;
            }
        }
    }
}