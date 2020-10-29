package com.sammy.malum.blocks.utility.multiblock;

import com.sammy.malum.blocks.utility.BasicTileEntity;
import com.sammy.malum.init.ModTileEntities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BoundingBlockTileEntity extends BasicTileEntity implements ITickableTileEntity
{
    public BlockPos ownerPos;
    public BoundingBlockTileEntity()
    {
        super(ModTileEntities.bounding_block_tile_entity);
    }
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        if (ownerPos != null)
        {
            compound.putInt("blockPosX", ownerPos.getX());
            compound.putInt("blockPosY", ownerPos.getY());
            compound.putInt("blockPosZ", ownerPos.getZ());
        }
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if (compound.contains("blockPosX"))
        {
            int x = compound.getInt("blockPosX");
            int y = compound.getInt("blockPosY");
            int z = compound.getInt("blockPosZ");
            ownerPos = new BlockPos(x, y, z);
        }
    }
    
    @Override
    public void remove()
    {
        if (ownerPos != null)
        {
            TileEntity tileEntity = world.getTileEntity(ownerPos);
            if (tileEntity instanceof MultiblockTileEntity)
            {
                world.removeBlock(ownerPos, true);
            }
        }
        super.remove();
    }
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        if (ownerPos != null)
        {
            TileEntity tileEntity = world.getTileEntity(ownerPos);
            if (tileEntity instanceof MultiblockTileEntity)
            {
                return ((MultiblockTileEntity) tileEntity).getCapability(cap, pos);
            }
        }
        return super.getCapability(cap);
    }
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side)
    {
        if (ownerPos != null)
        {
            TileEntity tileEntity = world.getTileEntity(ownerPos);
            if (tileEntity instanceof MultiblockTileEntity)
            {
                return ((MultiblockTileEntity) tileEntity).getCapability(cap, pos);
            }
        }
        return super.getCapability(cap, side);
    }
    
    @Override
    public void tick()
    {
    }
}