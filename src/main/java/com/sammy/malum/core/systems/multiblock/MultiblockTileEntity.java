package com.sammy.malum.core.systems.multiblock;

import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiblockTileEntity extends SimpleTileEntity
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
            List<Integer> x = new ArrayList<>();
            List<Integer> y = new ArrayList<>();
            List<Integer> z = new ArrayList<>();
            parts.forEach(b -> {
                x.add(b.getX());
                y.add(b.getY());
                z.add(b.getZ());
            });
            compound.putIntArray("x", x);
            compound.putIntArray("y", y);
            compound.putIntArray("z", z);
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        int[] x = compound.getIntArray("x");
        int[] y = compound.getIntArray("y");
        int[] z = compound.getIntArray("z");
        for (int i = 0; i < x.length; i++)
        {
            parts.add(new BlockPos(x[i],y[i],z[i]));
        }
        super.readData(compound);
    }
    
    @Override
    public void remove()
    {
        parts.forEach(b -> {
            if (world.getTileEntity(b) instanceof BoundingBlockTileEntity)
            {
                world.removeBlock(b, true);
            }
        });
        super.remove();
    }
}
