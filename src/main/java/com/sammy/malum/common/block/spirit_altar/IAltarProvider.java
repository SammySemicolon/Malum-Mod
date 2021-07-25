package com.sammy.malum.common.block.spirit_altar;

import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.util.math.vector.Vector3d;

public interface IAltarProvider
{
    public SimpleInventory providedInventory();
    public Vector3d providedItemPos();
}
