package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.*;
import java.util.stream.*;

public interface ICatalyzerAccelerationTarget {

    boolean isValidAccelerationTarget();

    CrucibleAccelerationData getAccelerationData();

    void setAccelerationData(CrucibleAccelerationData data);

    List<ItemStack> getExtraAugments();

    ItemStack getCoreAugment();

    default int getSearchRadius() {
        return 4;
    }

    MalumSpiritType getActiveSpiritType();

    Vec3 getVisualAccelerationPoint();

    CrucibleTuning.CrucibleAttributeType getTuningType();

    default List<Optional<AbstractAugmentItem>> getAugments() {
        List<ItemStack> augments = getExtraAugments();
        final ItemStack coreAugment = getCoreAugment();
        return (coreAugment.isEmpty() && augments.isEmpty()) ? Collections.emptyList() : Stream.concat(augments.stream(), Stream.<ItemStack>builder().add(coreAugment).build()).map(AbstractAugmentItem::getAugmentType).collect(Collectors.toList());
    }

    default void recalibrateAccelerators(Level level, BlockPos pos) {
        setAccelerationData(CrucibleAccelerationData.createData(this, getSearchRadius(), level, pos));
    }
}
