package com.sammy.malum.blocks.utility;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import static com.sammy.malum.MalumHelper.inputStackIntoTE;
import static com.sammy.malum.MalumHelper.vectorFromBlockPos;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public abstract class BasicTileEntity extends TileEntity
{
    public BasicTileEntity(TileEntityType type)
    {
        super(type);
    }
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        return compound;
    }
    
    @Override
    public void read(BlockState state, CompoundNBT compound)
    {
        super.read(state, compound);
        read(compound);
    }
    
    @Override
    public CompoundNBT getUpdateTag()
    {
        return write(new CompoundNBT());
    }
    
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(pos, 0, nbt);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
    {
        super.onDataPacket(net, packet);
        read(packet.getNbtCompound());
    }
    
    public void read(CompoundNBT compound)
    {
    
    }
    
//    public boolean output(ItemStack stack, BlockPos pos)
//    {
//        TileEntity inputTileEntity = world.getTileEntity(pos);
//        if (inputTileEntity != null)
//        {
//            LazyOptional<IItemHandler> inventory = inputTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
//            if (inventory.isPresent())
//            {
//                return inputStackIntoTE(inputTileEntity, stack);
//            }
//        }
//        dropItem(stack,pos);
//        return true;
//    }
    public void dropItem(ItemStack stack, BlockPos pos)
    {
        Vector3d entityPos = vectorFromBlockPos(pos).add(0.5, 0.5, 0.5).subtract(0,0,0);
        ItemEntity entity = new ItemEntity(world, entityPos.x, entityPos.y, entityPos.z, stack.copy());
        entity.setMotion(0, 0, 0);
        world.addEntity(entity);
    }
}