package com.sammy.malum.common.blockentity.crucible;

import com.sammy.malum.core.helper.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.HashMap;

public interface IAccelerationTarget {

    boolean canBeAccelerated();

    ArrayList<ICrucibleAccelerator> getAccelerators();

    ArrayList<BlockPos> getAcceleratorPositions();

    default int getLookupRange() {
        return 4;
    }

    default HashMap<ICrucibleAccelerator.CrucibleAcceleratorType, Integer> recalibrateAccelerators(Level level, BlockPos pos) {
        getAccelerators().clear();
        getAcceleratorPositions().clear();
        ArrayList<ICrucibleAccelerator> nearbyAccelerators = BlockHelper.getBlockEntities(ICrucibleAccelerator.class, level, pos, getLookupRange());
        HashMap<ICrucibleAccelerator.CrucibleAcceleratorType, Integer> entries = new HashMap<>();
        for (ICrucibleAccelerator accelerator : nearbyAccelerators) {
            if (accelerator.canStartAccelerating() && (accelerator.getTarget() == null || accelerator.getTarget() == this)) {
                accelerator.setTarget(this);
                int max = accelerator.getAcceleratorType().maximumEntries;
                int amount = entries.computeIfAbsent(accelerator.getAcceleratorType(), (a) -> 0);
                if (amount < max) {
                    getAccelerators().add(accelerator);
                    getAcceleratorPositions().add(((BlockEntity) accelerator).getBlockPos());
                    entries.replace(accelerator.getAcceleratorType(), amount + 1);
                }
            }
        }
        return entries;
    }

    default void saveAcceleratorData(CompoundTag compound) {
        CompoundTag acceleratorTag = new CompoundTag();
        ArrayList<BlockPos> positions = getAcceleratorPositions();
        if (!positions.isEmpty()) {
            acceleratorTag.putInt("amount", positions.size());
            for (int i = 0; i < positions.size(); i++) {
                BlockPos position = positions.get(i);
                BlockHelper.saveBlockPos(acceleratorTag, position, "accelerator_" + i + "_");
            }
            compound.put("acceleratorData", acceleratorTag);
        }
    }

    default void loadAcceleratorData(Level level, CompoundTag compound) {
        getAcceleratorPositions().clear();
        getAccelerators().clear();
        if (compound.contains("acceleratorData")) {
            CompoundTag acceleratorTag = compound.getCompound("acceleratorData");
            int amount = acceleratorTag.getInt("amount");
            for (int i = 0; i < amount; i++) {
                BlockPos pos = BlockHelper.loadBlockPos(acceleratorTag, "accelerator_" + i + "_");
                if (level != null && level.getBlockEntity(pos) instanceof ICrucibleAccelerator accelerator) {
                    getAccelerators().add(accelerator);
                } else if (level != null) {
                    continue;
                }
                getAcceleratorPositions().add(pos);
            }
        }
    }
}