package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.item.catalyzer_augment.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import team.lodestar.lodestone.helpers.*;

import java.util.*;
import java.util.stream.*;

public class CrucibleAccelerationData {

    public static final CrucibleAccelerationData DEFAULT = new CrucibleAccelerationData();

    public final List<BlockPos> positions = new ArrayList<>();
    public final List<ICrucibleAccelerator> accelerators = new ArrayList<>();

    public final float processingSpeed;
    public final float damageChance;

    public final float bonusYieldChance;
    public final float fuelUsageRate;
    public final float chainFocusingChance;
    public final float damageAbsorptionChance;
    public final float restorationChance;

    public static CrucibleAccelerationData createData(ICatalyzerAccelerationTarget target, int lookupRange, Level level, BlockPos pos) {
        Collection<ICrucibleAccelerator> nearbyAccelerators = BlockHelper.getBlockEntities(ICrucibleAccelerator.class, level, pos, lookupRange);
        Collection<ICrucibleAccelerator> validAccelerators = new ArrayList<>();
        Map<ICrucibleAccelerator.CrucibleAcceleratorType, Integer> typeCount = new HashMap<>();

        for (ICrucibleAccelerator accelerator : nearbyAccelerators) {
            if (accelerator.canStartAccelerating()) {
                if (accelerator.getTarget() == null || accelerator.getTarget().equals(target) || (accelerator.getTarget() != null && !accelerator.getTarget().canBeAccelerated())) {
                    var acceleratorType = accelerator.getAcceleratorType();
                    int max = acceleratorType.maximumEntries;
                    int amount = typeCount.getOrDefault(acceleratorType, 0);
                    if (amount < max) {
                        accelerator.setTarget(target);
                        validAccelerators.add(accelerator);
                        typeCount.put(acceleratorType, amount + 1);
                    }
                }
            }
        }
        return new CrucibleAccelerationData(target, typeCount, validAccelerators);
    }

    public CrucibleAccelerationData(ICatalyzerAccelerationTarget target, Map<ICrucibleAccelerator.CrucibleAcceleratorType, Integer> typeCount, Collection<ICrucibleAccelerator> accelerators) {
        final List<AbstractAugmentItem> augments = Stream.concat(accelerators.stream().map(ICrucibleAccelerator::getAugmentType), target.getAugmentTypes().stream()).filter(Optional::isPresent).map(Optional::get).toList();
        CrucibleTuning tuning = new CrucibleTuning(target.getTuningType());
        this.processingSpeed = (1 + typeCount.entrySet().stream().map((entry) -> entry.getKey().getAcceleration(entry.getValue())).reduce(Float::sum).orElse(0f)
                + augments.stream().map(AbstractAugmentItem::getSpeedIncrease).reduce(Float::sum).orElse(0f)) * tuning.speedIncreaseMultiplier.getMultiplier();
        this.damageChance = Mth.clamp((typeCount.entrySet().stream().map((entry) -> entry.getKey().getExtraDamageRollChance(entry.getValue())).reduce(Float::sum).orElse(0f)
                + augments.stream().map(AbstractAugmentItem::getDamageChanceIncrease).reduce(Float::sum).orElse(0f)) * tuning.damageChanceMultiplier.getMultiplier(), 0, 0.99f);

        this.bonusYieldChance = augments.stream().map(AbstractAugmentItem::getBonusYieldChanceIncrease).reduce(Float::sum).orElse(0f) * tuning.bonusYieldChanceMultiplier.getMultiplier();
        this.fuelUsageRate = (1 + augments.stream().map(AbstractAugmentItem::getFuelUsageRateIncrease).reduce(Float::sum).orElse(0f)) * tuning.fuelEfficiencyIncreaseMultiplier.getMultiplier();
        this.chainFocusingChance = augments.stream().map(AbstractAugmentItem::getInstantCompletionChance).reduce(Float::sum).orElse(0f) * tuning.chainFocusingChanceMultiplier.getMultiplier();
        this.damageAbsorptionChance = augments.stream().map(AbstractAugmentItem::getCompleteDamageNegationChance).reduce(Float::sum).orElse(0f) * tuning.damageAbsorptionChanceMultiplier.getMultiplier();
        this.restorationChance = augments.stream().map(AbstractAugmentItem::getRestorationChance).reduce(Float::sum).orElse(0f) * tuning.restorationChanceMultiplier.getMultiplier();
        accelerators.forEach(this::addAccelerator);
    }

    public CrucibleAccelerationData() {
        this.processingSpeed = 1;
        this.damageChance = 0;
        this.bonusYieldChance = 0;
        this.fuelUsageRate = 1;
        this.chainFocusingChance = 0;
        this.damageAbsorptionChance = 0;
        this.restorationChance = 0;
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
        }
        compound.put("acceleratorData", acceleratorTag);
    }

    public static CrucibleAccelerationData load(Level level, ICatalyzerAccelerationTarget target, CompoundTag compound) {
        if (level == null) {
            return DEFAULT;
        }
        Map<ICrucibleAccelerator.CrucibleAcceleratorType, Integer> typeCount = new HashMap<>();
        Collection<ICrucibleAccelerator> accelerators = new ArrayList<>();
        if (compound.contains("acceleratorData")) {
            CompoundTag acceleratorTag = compound.getCompound("acceleratorData");
            int amount = acceleratorTag.getInt("amount");
            for (int i = 0; i < amount; i++) {
                BlockPos pos = BlockHelper.loadBlockPos(acceleratorTag, "accelerator_" + i + "_");
                if (level.getBlockEntity(pos) instanceof ICrucibleAccelerator accelerator) {
                    typeCount.compute(accelerator.getAcceleratorType(), (type, count) -> count == null ? 1 : count + 1);
                    accelerators.add(accelerator);
                    accelerator.setTarget(target);
                    BlockHelper.updateState(level, pos);
                }
            }
            return new CrucibleAccelerationData(target, typeCount, accelerators);
        }
        else {
            return DEFAULT;
        }
    }
}
