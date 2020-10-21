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
    
    public void output(ItemStack stack, BlockPos pos)
    {
        Direction direction = world.getBlockState(pos).get(HORIZONTAL_FACING);
        TileEntity inputTileEntity = world.getTileEntity(pos.subtract(direction.getDirectionVec()));
        if (inputTileEntity != null)
        {
            boolean success = inputStackIntoTE(inputTileEntity, direction.getOpposite(),stack);
            if (success)
            {
                return;
            }
        }
        Vector3i directionVec = direction.getDirectionVec();
        Vector3d entityPos = vectorFromBlockPos(pos).add(0.5, 0.5, 0.5).subtract(directionVec.getX() * 0.8f, 0, directionVec.getZ() * 0.8f);
        ItemEntity entity = new ItemEntity(world, entityPos.x, entityPos.y, entityPos.z, stack.copy());
        entity.setMotion(-directionVec.getX() * 0.1f, 0.05f, -directionVec.getZ() * 0.1f);
        world.addEntity(entity);
    }
}