package com.sammy.malum.common.blocks.spiritaltar;

import com.ibm.icu.impl.coll.UVector32;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritkiln.functional.SpiritKilnCoreTileEntity;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.tileentityrendering.modules.ItemModule;
import net.minecraft.util.math.vector.Vector3f;

public class SpiritAltarRendererModule extends ItemModule
{
    @Override
    public SimpleInventory simpleInventory(SimpleTileEntity tileEntity)
    {
        if (tileEntity instanceof SpiritAltarTileEntity)
        {
            return ((SpiritAltarTileEntity) tileEntity).spiritInventory;
        }
        return null;
    }
    
    @Override
    public Vector3f itemOffset(SimpleTileEntity tileEntity, float partialTicks, float distance, int currentPoint, int totalPoints)
    {
        return new Vector3f(SpiritAltarTileEntity.itemOffset((SpiritAltarTileEntity) tileEntity, currentPoint));
    }
}
