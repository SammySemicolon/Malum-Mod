package com.sammy.malum.core.systems.multiblock;

import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.tile.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class MultiblockTileEntity extends SimpleTileEntity
{
    public List<BlockPos> parts;
    public MultiblockTileEntity(TileEntityType type)
    {
        super(type);
        parts = new ArrayList<>();
    }
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (!parts.isEmpty())
        {
            compound.putInt("partsSize", parts.size());
            for (int i = 0; i < parts.size(); i++)
            {
                MalumHelper.writeBlockPosExtra(compound,parts.get(i), "part" + i);
            }
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        if (compound.contains("partsSize"))
        {
            parts = new ArrayList<>();
            for (int i = 0; i < compound.getInt("partsSize"); i++)
            {
                parts.add(MalumHelper.readBlockPosExtra(compound, "part" + i));
            }
        }
        super.readData(compound);
    }
    
    @Override
    public void remove()
    {
        parts.forEach(b -> {
            if (world.getTileEntity(b) instanceof BoundingBlockTileEntity)
            {
                if (MalumHelper.areWeOnClient(world))
                {
                    ClientHelper.spawnBlockParticles(b, world.getBlockState(b));
                }
                world.removeBlock(b, true);
            }
        });
        super.remove();
    }
}
