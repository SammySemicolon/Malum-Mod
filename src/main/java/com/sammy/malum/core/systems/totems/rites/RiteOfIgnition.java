package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.common.blocks.spiritkiln.functional.SpiritKilnCoreTileEntity;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RiteOfIgnition extends AffectBlocksRite
{
    public RiteOfIgnition(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    @Override
    public int range()
    {
        return 8;
    }
    
    @Override
    public boolean doVertical()
    {
        return true;
    }
    
    @Override
    public boolean effect(BlockPos pos, BlockState state, World world)
    {
        if (world.getTileEntity(pos) instanceof SpiritKilnCoreTileEntity)
        {
            SpiritKilnCoreTileEntity tileEntity = (SpiritKilnCoreTileEntity) world.getTileEntity(pos);
        }
        return false;
    }
}
