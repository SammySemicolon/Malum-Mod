package com.sammy.malum.common.blocks.taintedfurnace;

import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.tileentityrendering.modules.ItemModule;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.world.World;

public class TaintedFurnaceItemRendererModule extends ItemModule
{
    @Override
    public SimpleInventory simpleInventory(SimpleTileEntity tileEntity)
    {
        if (tileEntity instanceof TaintedFurnaceCoreTileEntity)
        {
            return ((TaintedFurnaceCoreTileEntity) tileEntity).inventory;
        }
        return null;
    }
    
    @Override
    public Vector2f itemOffset(World world, float partialTicks, float distance, int currentPoint, int totalPoints)
    {
        return Vector2f.ZERO;
    }
}
