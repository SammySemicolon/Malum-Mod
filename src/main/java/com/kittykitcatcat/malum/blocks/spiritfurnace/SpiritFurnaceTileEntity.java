package com.kittykitcatcat.malum.blocks.spiritfurnace;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class SpiritFurnaceTileEntity extends TileEntity implements ITickableTileEntity
{
    public SpiritFurnaceTileEntity()
    {
        super(ModTileEntities.spirit_furnace_tile_entity);
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

    }
}