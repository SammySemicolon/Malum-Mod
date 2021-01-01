package com.sammy.malum.common.blocks.itemstand;

import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.tileentityrendering.modules.ItemModule;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import static net.minecraft.state.properties.BlockStateProperties.FACING;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class ItemStandItemRendererModule extends ItemModule
{
    @Override
    public SimpleInventory simpleInventory(SimpleTileEntity tileEntity)
    {
        if (tileEntity instanceof SimpleInventoryTileEntity)
        {
            return ((SimpleInventoryTileEntity) tileEntity).inventory;
        }
        return null;
    }
    
    @Override
    public Vector3f itemOffset(SimpleTileEntity tileEntity, float partialTicks, float distance, int currentPoint, int totalPoints)
    {
        return new Vector3f(ItemStandTileEntity.itemOffset(tileEntity));
    }
}
