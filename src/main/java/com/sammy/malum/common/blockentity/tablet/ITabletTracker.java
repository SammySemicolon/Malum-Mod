package com.sammy.malum.common.blockentity.tablet;

import com.sammy.malum.core.helper.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public interface ITabletTracker {
    void setTablet(TwistedTabletBlockEntity blockEntity);

    TwistedTabletBlockEntity getTablet();

    void setTabletPos(BlockPos pos);

    BlockPos getTabletPos();

    default void saveTwistedTabletData(CompoundTag compound) {
        CompoundTag twistedTabletTag = new CompoundTag();
        if (getTabletPos() != null) {
            BlockHelper.saveBlockPos(twistedTabletTag, getTabletPos());
            compound.put("twistedTabletData", twistedTabletTag);
        }
    }

    default void loadTwistedTabletData(Level level, CompoundTag compound) {
        if (compound.contains("twistedTabletData")) {
            CompoundTag twistedTabletTag = compound.getCompound("twistedTabletData");
            setTabletPos(BlockHelper.loadBlockPos(twistedTabletTag));
            if (level != null && getTabletPos() != null && level.getBlockEntity(getTabletPos()) instanceof TwistedTabletBlockEntity twistedTablet) {
                setTablet(twistedTablet);
            }
        }
    }
}