package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.BlockHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICatalyzerAccelerationTarget {

    MalumSpiritType getActiveSpiritType();

    Vec3 getAccelerationPoint();

    boolean canBeAccelerated();

    void setAccelerationData(CrucibleAccelerationData data);

    default int getLookupRange() {
        return 4;
    }

    default void recalibrateAccelerators(Level level, BlockPos pos) {
        setAccelerationData(new CrucibleAccelerationData(this, getLookupRange(), level, pos));
    }
}
