package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.item.augment.AbstractAugmentItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ICatalyzerAccelerationTarget {

    MalumSpiritType getActiveSpiritType();

    Vec3 getAccelerationPoint();

    default List<Optional<AbstractAugmentItem>> getAugmentTypes() {
        List<ItemStack> augments = getAugments();
        final ItemStack coreAugment = getCoreAugment();
        return (coreAugment.isEmpty() && augments.isEmpty()) ? Collections.emptyList() : Stream.concat(augments.stream(), Stream.<ItemStack>builder().add(coreAugment).build()).map(AbstractAugmentItem::getAugmentType).collect(Collectors.toList());
    }

    List<ItemStack> getAugments();

    ItemStack getCoreAugment();

    boolean canBeAccelerated();

    CrucibleAccelerationData getAccelerationData();

    void setAccelerationData(CrucibleAccelerationData data);

    CrucibleTuning.CrucibleAttributeType getTuningType();

    default int getLookupRange() {
        return 4;
    }

    default void recalibrateAccelerators(Level level, BlockPos pos) {
        setAccelerationData(CrucibleAccelerationData.createData(this, getLookupRange(), level, pos));
    }
}
