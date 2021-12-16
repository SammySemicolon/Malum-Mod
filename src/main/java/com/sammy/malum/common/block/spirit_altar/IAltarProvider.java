package com.sammy.malum.common.block.spirit_altar;

import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import net.minecraft.util.math.vector.Vector3d;

public interface IAltarProvider
{
    public SimpleBlockEntityInventory providedInventory();
    public Vector3d providedItemPos();
}
