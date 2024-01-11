package com.sammy.malum.common.block.curiosities.spirit_crucible;

import java.util.*;
import java.util.function.*;

public class CrucibleTuning {

    public final CrucibleTuningType buffedStat;
    public final TunedValue speedIncreaseMultiplier;
    public final InverseTunedValue fuelEfficiencyIncreaseMultiplier;
    public final InverseTunedValue damageChanceMultiplier;
    public final TunedValue bonusYieldChanceMultiplier;
    public final TunedValue chainFocusingChanceMultiplier;
    public final TunedValue damageAbsorptionChanceMultiplier;
    public final TunedValue restorationChanceMultiplier;

    public CrucibleTuning(CrucibleTuningType buffedStat) {
        this.buffedStat = buffedStat;
        this.speedIncreaseMultiplier = new TunedValue(CrucibleTuningType.PROCESSING_SPEED, buffedStat);
        this.fuelEfficiencyIncreaseMultiplier = new InverseTunedValue(CrucibleTuningType.FUEL_USAGE_RATE, buffedStat);
        this.damageChanceMultiplier = new InverseTunedValue(CrucibleTuningType.INSTABILITY, buffedStat);
        this.bonusYieldChanceMultiplier = new TunedValue(CrucibleTuningType.FORTUNE_CHANCE, buffedStat);
        this.chainFocusingChanceMultiplier = new TunedValue(CrucibleTuningType.CHAIN_FOCUSING_CHANCE, buffedStat);
        this.damageAbsorptionChanceMultiplier = new TunedValue(CrucibleTuningType.SHIELDING_CHANCE, buffedStat);
        this.restorationChanceMultiplier = new TunedValue(CrucibleTuningType.RESTORATION_CHANCE, buffedStat);
    }

    public enum CrucibleTuningType {
        NONE(null, null),
        PROCESSING_SPEED(d -> d.processingSpeed),
        FUEL_USAGE_RATE(d -> d.fuelUsageRate),
        INSTABILITY(d -> d.damageChance > 0, d -> String.format("%.2f", d.getAccelerationData().damageChance - d.getWexingEngineInfluence() * 0.15f)),
        FORTUNE_CHANCE(d -> d.bonusYieldChance),
        CHAIN_FOCUSING_CHANCE(d -> d.chainFocusingChance),
        SHIELDING_CHANCE(d -> d.damageAbsorptionChance),
        RESTORATION_CHANCE(d -> d.restorationChance);

        public final Predicate<CrucibleAccelerationData> isValueValid;
        public final Function<ICatalyzerAccelerationTarget, String> statDisplayFunction;

        CrucibleTuningType(Predicate<CrucibleAccelerationData> isValueValid, Function<ICatalyzerAccelerationTarget, String> statDisplayFunction) {
            this.isValueValid = isValueValid;
            this.statDisplayFunction = statDisplayFunction;
        }
        CrucibleTuningType(Function<CrucibleAccelerationData, Float> valueGetter) {
            this(d -> valueGetter.apply(d) > 0, d -> String.format("%.2f", valueGetter.apply(d.getAccelerationData())));
        }

        public static List<CrucibleTuningType> getValidValues(CrucibleAccelerationData data) {
            List<CrucibleTuningType> validValues = new ArrayList<>();
            for (int i = 1; i < values().length; i++) {
                CrucibleTuningType type = values()[i];
                if (type.isValueValid.test(data)) {
                    validValues.add(type);
                }
            }
            return validValues;
        }

        public CrucibleTuningType next(CrucibleTuningType current, ICatalyzerAccelerationTarget target) {
            final List<CrucibleTuningType> validValues = getValidValues(target.getAccelerationData());
            final int index = current.equals(NONE) ? 0 : validValues.indexOf(current) + 1;
            if (index == validValues.size()) {
                return NONE;
            }
            return validValues.get((index) % validValues.size());
        }

        public String translation() {
            return "malum.gui.crucible.tuning." + toString().toLowerCase(Locale.ROOT);
        }
    }

    public enum AppliedTuning {
        BUFF(TunedValue::getPositiveMultiplier),
        DEBUFF(TunedValue::getNegativeMultiplier),
        NONE(TunedValue::getBaseValue);

        public final Function<TunedValue, Float> multiplierGetter;

        AppliedTuning(Function<TunedValue, Float> multiplierGetter) {
            this.multiplierGetter = multiplierGetter;
        }
    }

    public static class TunedValue {
        public final CrucibleTuningType tuningType;
        public final AppliedTuning appliedTuning;

        public TunedValue(CrucibleTuningType tuningType, AppliedTuning appliedTuning) {
            this.tuningType = tuningType;
            this.appliedTuning = appliedTuning;
        }

        public TunedValue(CrucibleTuningType tuningType, CrucibleTuningType buffedStat) {
            this.tuningType = tuningType;
            this.appliedTuning = buffedStat.equals(CrucibleTuningType.NONE) ? AppliedTuning.NONE : tuningType.equals(buffedStat) ? AppliedTuning.BUFF : AppliedTuning.DEBUFF;
        }

        public float getMultiplier() {
            switch (appliedTuning) {
                case NONE -> {
                    return getBaseValue();
                }
                case BUFF -> {
                    return getPositiveMultiplier();
                }
                case DEBUFF -> {
                    return getNegativeMultiplier();
                }
                default -> throw new NullPointerException("Tuned Value Tuning Type is somehow null.");
            }
        }

        protected float getBaseValue() {
            return 1f;
        }

        protected float getPositiveMultiplier() {
            return 1.2f;
        }

        protected float getNegativeMultiplier() {
            return 0.9f;
        }
    }

    public static class InverseTunedValue extends TunedValue {
        public InverseTunedValue(CrucibleTuningType tuningType, AppliedTuning appliedTuning) {
            super(tuningType, appliedTuning);
        }

        public InverseTunedValue(CrucibleTuningType tuningType, CrucibleTuningType buffedStat) {
            super(tuningType, buffedStat);
        }

        @Override
        protected float getPositiveMultiplier() {
            return 0.8f;
        }

        @Override
        protected float getNegativeMultiplier() {
            return 1.1f;
        }
    }
}
