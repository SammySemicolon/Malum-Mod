package com.sammy.malum.common.block.storage.pedestal;

import com.sammy.malum.common.block.storage.MalumItemHolderItemDisplayData;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;

public class ItemPedestalItemDisplayData extends MalumItemHolderItemDisplayData {

    public ItemPedestalItemDisplayData(LodestoneItemStackBlockHandler parent) {
        super(parent);
    }

    @Override
    public Vec3 getDisplayCenter(LodestoneBlockEntity parent, float partialTicks) {
        var pos = parent.getBlockPos();
        return new Vec3(pos.getX() + 0.5f, pos.getY() + 1.15f, pos.getZ() + 0.5f);
    }
}
