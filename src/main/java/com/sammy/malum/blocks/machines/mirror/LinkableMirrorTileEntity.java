package com.sammy.malum.blocks.machines.mirror;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class LinkableMirrorTileEntity extends BasicMirrorTileEntity implements ITickableTileEntity
{
    public int currentMirror;
    public List<BlockPos> linkedPositions;
    
    public LinkableMirrorTileEntity(TileEntityType type)
    {
        super(type);
    }
    
    public void link(BlockPos linkPos)
    {
    
    }

    public BasicMirrorTileEntity getCurrentMirrorTileEntity()
    {
        if (linkedPositions == null)
        {
            linkedPositions = new ArrayList<>();
        }
        if (!linkedPositions.isEmpty())
        {
            if (world.getTileEntity(linkedPositions.get(currentMirror)) instanceof BasicMirrorTileEntity)
            {
                currentMirror++;
                if (currentMirror >= linkedPositions.size())
                {
                    currentMirror = 0;
                }
                return (BasicMirrorTileEntity) world.getTileEntity(linkedPositions.get(currentMirror));
            }
            else
            {
                linkedPositions.remove(currentMirror);
                currentMirror = 0;
            }
        }
        return null;
    }
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        compound.putInt("currentMirror", currentMirror);
        if (linkedPositions != null && !linkedPositions.isEmpty())
        {
            compound.putInt("mirrorCount", linkedPositions.size());
            for (int i = 0; i < linkedPositions.size(); i++)
            {
                BlockPos pos = linkedPositions.get(i);
                compound.putInt("blockPosX" + i, pos.getX());
                compound.putInt("blockPosY" + i, pos.getY());
                compound.putInt("blockPosZ" + i, pos.getZ());
            }
        }
        return super.write(compound);
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        currentMirror = compound.getInt("currentMirror");
        if (compound.contains("mirrorCount"))
        {
            for (int i = 0; i < compound.getInt("mirrorCount"); i++)
            {
                int x = compound.getInt("blockPosX" + i);
                int y = compound.getInt("blockPosY" + i);
                int z = compound.getInt("blockPosZ" + i);
                BlockPos pos = new BlockPos(x, y, z);
                if (linkedPositions == null)
                {
                    linkedPositions = new ArrayList<>();
                }
                linkedPositions.add(pos);
            }
        }
    }
    
    @Override
    public void tick()
    {
    }
}