package com.kittykitcatcat.malum.blocks.machines.mirror;

import com.kittykitcatcat.malum.init.ModTileEntities;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class InputMirrorTileEntity extends BasicMirrorTileEntity implements ITickableTileEntity
{
    public InputMirrorTileEntity()
    {
        super(ModTileEntities.input_mirror_tile_entity);
    }

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
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
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

    @Override
    public void tick()
    {
        if (transferCooldown <= 0)
        {
            ItemStack stack = inventory.getStackInSlot(0);
            if (stack != ItemStack.EMPTY)
            {
                Direction direction = getBlockState().get(HORIZONTAL_FACING);
                TileEntity inputTileEntity = world.getTileEntity(pos.subtract(direction.getDirectionVec()));
                if (inputTileEntity != null)
                {
                    inputTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction).ifPresent(itemHandler ->
                    {
                        for (int j = 0; j < itemHandler.getSlots(); j++)
                        {
                            ItemStack inserted = itemHandler.insertItem(j, stack.copy(), false);
                            stack.setCount(0);
                            if (inserted.isEmpty())
                            {
                                break;
                            }
                        }
                    });
                }
            }
        }
    }
}