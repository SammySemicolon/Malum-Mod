package com.sammy.malum.common.block.curiosities.poppetry;

import com.sammy.malum.common.block.storage.MalumItemHolderItemDisplayData;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;

public class PoppetPillowDisplayData extends MalumItemHolderItemDisplayData {

    public PoppetPillowDisplayData(LodestoneItemStackBlockHandler parent) {
        super(parent);
    }

    @Override
    public Vec3 getDisplayCenter(LodestoneBlockEntity parent, float partialTicks) {
        var pos = parent.getBlockPos();
        return new Vec3(pos.getX() + 0.5f, pos.getY() + 0.75f, pos.getZ() + 0.5f);
    }
}
