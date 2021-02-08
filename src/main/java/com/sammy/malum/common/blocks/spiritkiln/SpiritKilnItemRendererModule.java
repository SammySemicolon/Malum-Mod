package com.sammy.malum.common.blocks.spiritkiln;

import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.tileentityrendering.modules.ItemModule;
import net.minecraft.util.math.vector.Vector3f;

public class SpiritKilnItemRendererModule extends ItemModule
{
    @Override
    public SimpleInventory simpleInventory(SimpleTileEntity tileEntity)
    {
        if (tileEntity instanceof SpiritKilnCoreTileEntity)
        {
            return ((SpiritKilnCoreTileEntity) tileEntity).inventory;
        }
        return null;
    }
    
    @Override
    public Vector3f itemOffset(SimpleTileEntity tileEntity, float partialTicks, float distance, int currentPoint, int totalPoints)
    {
        return new Vector3f(SpiritKilnCoreTileEntity.itemOffset(tileEntity));
    }
}
