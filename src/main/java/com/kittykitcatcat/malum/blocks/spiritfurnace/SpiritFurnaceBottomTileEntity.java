package com.kittykitcatcat.malum.blocks.spiritfurnace;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class SpiritFurnaceBottomTileEntity extends TileEntity implements ITickableTileEntity
{
    public SpiritFurnaceBottomTileEntity()
    {
        super(ModTileEntities.spirit_furnace_bottom_tile_entity);
    }
    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }
    @Override
    public void handleUpdateTag(CompoundNBT tag)
    {
        read(tag);
    }
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(pos, 0, getUpdateTag());
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        handleUpdateTag(pkt.getNbtCompound());
    }

    public boolean isSmelting;
    public int burnTime;
    public ItemStackHandler inventory = new ItemStackHandler(3)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            if(stack.getItem().equals(ModItems.spirit_charcoal))
            {
                if (slot == 0)
                {
                    return true;
                }
            }
            return false;
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
            SpiritFurnaceBottomTileEntity.this.markDirty();
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
        compound.putInt("burnTime", burnTime);
        compound.putBoolean("isSmelting", isSmelting);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        burnTime = compound.getInt("burnTime");
        isSmelting = compound.getBoolean("isSmelting");
    }

    public ItemStack getFuelStack(ItemStackHandler inventory)
    {
        return inventory.getStackInSlot(0);
    }
    public ItemStack getInputStack(ItemStackHandler inventory)
    {
        return inventory.getStackInSlot(1);
    }
    public ItemStack getOutputStack(ItemStackHandler inventory)
    {
        return inventory.getStackInSlot(2);
    }
    @Override
    public void tick()
    {
        if (world.getTileEntity(pos.up()) instanceof SpiritFurnaceTopTileEntity)
        {
            SpiritFurnaceTopTileEntity topTileEntity = (SpiritFurnaceTopTileEntity) world.getTileEntity(pos.up());
            ItemStack stackToAdd = topTileEntity.inventory.getStackInSlot(0);
            if (stackToAdd.getItem().equals(getInputStack(inventory).getItem()))
            {
                if (stackToAdd.getCount() > 0 && getInputStack(inventory).getCount() < 64)
                {
                    if (getInputStack(inventory).equals(ItemStack.EMPTY))
                    {
                        MalumHelper.setStackInTEInventory(inventory, new ItemStack(stackToAdd.getItem()), 1);
                    }
                    else
                    {
                        MalumHelper.addStackToTEInventory(inventory, stackToAdd, 1);
                    }
                    MalumHelper.setStackInTEInventory(topTileEntity.inventory, ItemStack.EMPTY, 0);
                }
            }
        }
    }
}