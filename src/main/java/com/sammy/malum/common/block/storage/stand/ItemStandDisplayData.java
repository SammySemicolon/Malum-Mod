package com.sammy.malum.common.block.storage.stand;

import com.sammy.malum.common.block.storage.MalumItemHolderItemDisplayData;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;

public class ItemStandDisplayData extends MalumItemHolderItemDisplayData {

    public ItemStandDisplayData(LodestoneItemStackBlockHandler parent) {
        super(parent);
    }

    @Override
    public Vec3 getDisplayCenter(LodestoneBlockEntity parent, float partialTicks) {
        var pos = parent.getBlockPos();
        var direction = parent.getBlockState().getValue(ItemStandBlock.FACING);
        float xOffset = direction.getStepX() * 0.05f;
        float yOffset = direction.getStepY() * 0.05f;
        float zOffset = direction.getStepZ() * 0.05f;
        return new Vec3(pos.getX() + 0.5f - xOffset, pos.getY() + 0.5f - yOffset, pos.getZ() + 0.5f - zOffset);
    }
}
