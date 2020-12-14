package com.sammy.malum.core.systems.spirits.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class SimpleInventorySpiritRequestTileEntity extends SimpleInventoryTileEntity implements ISpiritRequestTileEntity
{
    protected ArrayList<BlockPos> cachedHolders;
    public SimpleInventorySpiritRequestTileEntity(TileEntityType type)
    {
        super(type);
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (!getCachedHolders().isEmpty())
        {
            compound.putInt("cachedHolderCount", cachedHolders.size());
            for (int i = 0; i < cachedHolders.size(); i++)
            {
                MalumHelper.writeBlockPosExtra(compound,cachedHolders.get(i), "cachedHolder" + i);
            }
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        if (compound.contains("cachedHolderCount"))
        {
            for (int i = 0; i < compound.getInt("cachedHolderCount"); i++)
            {
                addCachedHolder(MalumHelper.readBlockPosExtra(compound, "cachedHolder" + i));
            }
        }
        super.readData(compound);
    }
    @Override
    public void addCachedHolder(BlockPos pos)
    {
        if (cachedHolders == null)
        {
            cachedHolders = new ArrayList<>();
        }
        if (cachedHolders.contains(pos))
        {
            return;
        }
        cachedHolders.add(pos);
    }
    
    @Override
    public void resetCachedHolders()
    {
        cachedHolders = new ArrayList<>();
    }
    
    @Override
    public ArrayList<BlockPos> getCachedHolders()
    {
        if (cachedHolders == null)
        {
            cachedHolders = new ArrayList<>();
        }
        return cachedHolders;
    }
}
