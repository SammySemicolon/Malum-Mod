package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import team.lodestar.lodestone.helpers.*;

import java.util.*;

public class CrucibleAccelerationData {

    public final ArrayList<BlockPos> positions = new ArrayList<>();
    public final ArrayList<ICrucibleAccelerator> accelerators = new ArrayList<>();
    public final Map<CrucibleAcceleratorType, Integer> typeCount;
    public final float speedIncrease;
    public final float damageChance;
    public final int maximumDamageInstances;

    public CrucibleAccelerationData(ICatalyzerAccelerationTarget target, int lookupRange, Level level, BlockPos pos) {
        Collection<ICrucibleAccelerator> nearbyAccelerators = BlockHelper.getBlockEntities(ICrucibleAccelerator.class, level, pos, lookupRange);
        Map<CrucibleAcceleratorType, Integer> typeCount = new HashMap<>();
        for (ICrucibleAccelerator accelerator : nearbyAccelerators) {
            if (accelerator.canStartAccelerating()) {
                if (accelerator.getTarget() == null || accelerator.getTarget().equals(target) || (accelerator.getTarget() != null && !accelerator.getTarget().canBeAccelerated())) {
                    var acceleratorType = accelerator.getAcceleratorType();
                    int max = acceleratorType.maximumEntries;
                    int amount = typeCount.getOrDefault(acceleratorType, 0);
                    accelerator.setTarget(target);
                    if (amount < max) {
                        addAccelerator(accelerator);
                        typeCount.put(acceleratorType, amount + 1);
                    }
                }
            }
        }
        this.typeCount = typeCount;
        this.speedIncrease = typeCount.entrySet().stream().map((entry) -> entry.getKey().getAcceleration(entry.getValue())).reduce(Float::sum).orElse(0f);
        this.damageChance = typeCount.entrySet().stream().map((entry) -> entry.getKey().getDamageChance(entry.getValue())).reduce(Float::sum).orElse(0f);
        this.maximumDamageInstances = typeCount.entrySet().stream().map((entry) -> entry.getKey().getMaximumDamage(entry.getValue())).reduce(Integer::sum).orElse(0);
    }
    public CrucibleAccelerationData(Map<CrucibleAcceleratorType, Integer> typeCount) {
        this.typeCount = typeCount;
        this.speedIncrease = typeCount.entrySet().stream().map((entry) -> entry.getKey().getAcceleration(entry.getValue())).reduce(Float::sum).orElse(0f);
        this.damageChance = typeCount.entrySet().stream().map((entry) -> entry.getKey().getDamageChance(entry.getValue())).reduce(Float::sum).orElse(0f);
        this.maximumDamageInstances = typeCount.entrySet().stream().map((entry) -> entry.getKey().getMaximumDamage(entry.getValue())).reduce(Integer::sum).orElse(0);
    }

    private void addAccelerator(ICrucibleAccelerator crucibleAccelerator) {
        positions.add(((BlockEntity) crucibleAccelerator).getBlockPos());
        accelerators.add(crucibleAccelerator);
    }

    public void save(CompoundTag compound) {
        CompoundTag acceleratorTag = new CompoundTag();
        if (!positions.isEmpty()) {
            acceleratorTag.putInt("amount", positions.size());
            for (int i = 0; i < positions.size(); i++) {
                BlockPos position = positions.get(i);
                BlockHelper.saveBlockPos(acceleratorTag, position, "accelerator_" + i + "_");
            }
            compound.put("acceleratorData", acceleratorTag);
        }
    }

    public static CrucibleAccelerationData load(Level level, ICatalyzerAccelerationTarget target, CompoundTag compound) {
        if (level == null) {
            return null;
        }
        Map<CrucibleAcceleratorType, Integer> typeCount = new HashMap<>();
        CrucibleAccelerationData data = new CrucibleAccelerationData(typeCount);
        if (compound.contains("acceleratorData")) {
            CompoundTag acceleratorTag = compound.getCompound("acceleratorData");
            int amount = acceleratorTag.getInt("amount");
            for (int i = 0; i < amount; i++) {
                BlockPos pos = BlockHelper.loadBlockPos(acceleratorTag, "accelerator_" + i + "_");
                if (level.getBlockEntity(pos) instanceof ICrucibleAccelerator accelerator) {
                    typeCount.compute(accelerator.getAcceleratorType(), (type, count) -> count == null ? 1 : count + 1);
                    data.addAccelerator(accelerator);
                    accelerator.setTarget(target);
                    BlockHelper.updateState(level, pos);
                }
            }
        }
        return data;
    }
}
