package com.sammy.malum.common.blocks.spiritaltar;

import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.util.math.vector.Vector3d;

public interface IAltarProvider
{
    public SimpleInventory providedInventory();
    public Vector3d providedItemOffset();
}
