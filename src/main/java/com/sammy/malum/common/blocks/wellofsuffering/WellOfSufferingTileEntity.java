package com.sammy.malum.common.blocks.wellofsuffering;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.MathHelper;

public class WellOfSufferingTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public float renderedWater;
    public float water;

    public WellOfSufferingTileEntity()
    {
        super(MalumTileEntities.WELL_OF_SUFFERING_TILE_ENTITY.get());
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putFloat("water", water);
        compound.putFloat("rendered_water", renderedWater);
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        water = compound.getFloat("water");
        renderedWater = compound.getFloat("rendered_water");
        super.readData(compound);
    }

    @Override
    public void tick()
    {
        water = MathHelper.lerp(0.1f, water, 1f);
        renderedWater = renderedWater < water ? MathHelper.lerp(0.05f, renderedWater,water) : MathHelper.lerp(0.01f, renderedWater,water);
    }
}
