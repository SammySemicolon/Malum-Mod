package com.sammy.malum.blocks.machines.funkengine.discholder;

import com.sammy.malum.ClientHandler;
import com.sammy.malum.blocks.utility.BasicTileEntity;
import com.sammy.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class DiscHolderTileEntity extends BasicTileEntity implements ITickableTileEntity
{
    public DiscHolderTileEntity()
    {
        super(ModTileEntities.disc_holder_tile_entity);
    }
    
    public boolean shuffle;
    public ItemStackHandler inventory = new ItemStackHandler(12)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 1;
        }
    
        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return ItemStack.EMPTY;
        }
    
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return false;
        }
    
        @Override
        protected void onContentsChanged(int slot)
        {
            DiscHolderTileEntity.this.markDirty();
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
        compound.putBoolean("shuffle", shuffle);
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        shuffle = compound.getBoolean("shuffle");
    }
    public void transferDiscs()
    {
    
    }
    
    @Override
    public void tick()
    {
    }
}