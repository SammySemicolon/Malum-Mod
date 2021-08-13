package com.sammy.malum.common.block.spirit_altar;

import com.sammy.malum.core.mod_systems.tile.SimpleTileEntityInventory;
import net.minecraft.util.math.vector.Vector3d;

public interface IAltarProvider
{
    public SimpleTileEntityInventory providedInventory();
    public Vector3d providedItemPos();
}
