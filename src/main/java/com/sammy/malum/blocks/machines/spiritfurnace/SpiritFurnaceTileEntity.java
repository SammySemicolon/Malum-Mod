package com.sammy.malum.blocks.machines.spiritfurnace;

import com.sammy.malum.blocks.utility.multiblock.MultiblockTileEntity;
import com.sammy.malum.init.ModRecipes;
import com.sammy.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class SpiritFurnaceTileEntity extends MultiblockTileEntity implements ITickableTileEntity
{
    
    public SpiritFurnaceTileEntity()
    {
        super(ModTileEntities.spirit_furnace_tile_entity);
    }
    
    //region inventory stuff
    public enum stackSlotEnum
    {
        fuel(0), smeltable(1);
        
        public final int type;
        
        stackSlotEnum(int type) { this.type = type;}
    }
    
    
    public ItemStackHandler fuelInventory = new ItemStackHandler(1)
    {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return ModRecipes.getSpiritFurnaceFuelData(stack) != null;
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
            SpiritFurnaceTileEntity.this.markDirty();
            if (!world.isRemote)
            {
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    };
    public final LazyOptional<IItemHandler> fuelInventoryOptional = LazyOptional.of(() -> fuelInventory);
    
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return fuelInventoryOptional.cast();
        }
        return super.getCapability(cap);
    }
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return fuelInventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }
    
    public ItemStackHandler smeltableInventory = new ItemStackHandler(1)
    {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return ModRecipes.getSpiritFurnaceRecipe(stack) != null;
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
            SpiritFurnaceTileEntity.this.markDirty();
            if (!world.isRemote)
            {
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    };
    public final LazyOptional<IItemHandler> smeltableInventoryOptional = LazyOptional.of(() -> smeltableInventory);
    
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, BlockPos pos)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return smeltableInventoryOptional.cast();
        }
        return super.getCapability(cap, pos);
    }
    
    public ItemStack getItem(stackSlotEnum slot)
    {
        if (slot == stackSlotEnum.fuel)
        {
            return fuelInventory.getStackInSlot(0);
        }
        else
        {
            return smeltableInventory.getStackInSlot(0);
        }
    }
    //endregion
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put("smeltableInventory", smeltableInventory.serializeNBT());
        compound.put("fuelInventory", fuelInventory.serializeNBT());
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        smeltableInventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("smeltableInventory")));
        fuelInventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("fuelInventory")));
    }
    @Override
    public void tick()
    {
    
    }
}