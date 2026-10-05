package com.sammy.malum.common.block.curiosities.sorcery.runic_workbench;

import com.sammy.malum.common.block.storage.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.modules.toolkit.blockentity.*;
import team.lodestar.lodestone.modules.toolkit.inventory.*;

public class RunicWorkbenchItemDisplayData extends MalumItemHolderItemDisplayData {

    public RunicWorkbenchItemDisplayData(LodestoneItemStackBlockHandler parent) {
        super(parent);
    }

    @Override
    public Vec3 getDisplayCenter(LodestoneBlockEntity parent, float partialTicks) {
        var pos = parent.getBlockPos();
        return new Vec3(pos.getX() + 0.5f, pos.getY() + 1.15f, pos.getZ() + 0.5f);
    }
}
