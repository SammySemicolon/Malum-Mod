package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.item.catalyzer_augment.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.BlockHelper;

import java.util.*;
import java.util.stream.*;

public interface ICatalyzerAccelerationTarget {

    MalumSpiritType getActiveSpiritType();

    Vec3 getAccelerationPoint();

    default List<Optional<AbstractAugmentItem>> getAugmentTypes() {
        final List<ItemStack> augments = getAugments();
        return augments.isEmpty() ? Collections.emptyList() : getAugments().stream().map(AbstractAugmentItem::getAugmentType).collect(Collectors.toList());
    }

    List<ItemStack> getAugments();

    boolean canBeAccelerated();

    CrucibleAccelerationData getAccelerationData();

    void setAccelerationData(CrucibleAccelerationData data);

    CrucibleTuning.CrucibleTuningType getTuningType();

    float getWexingEngineInfluence();

    default int getLookupRange() {
        return 4;
    }

    default void recalibrateAccelerators(Level level, BlockPos pos) {
        setAccelerationData(CrucibleAccelerationData.createData(this, getLookupRange(), level, pos));
    }
}
