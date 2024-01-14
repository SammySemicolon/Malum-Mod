package com.sammy.malum.common.block.curiosities.spirit_crucible;

import java.util.*;
import java.util.function.*;

public class CrucibleTuning {

    public final CrucibleTuningType buffedStat;
    public final Map<CrucibleTuningType, ? extends TuningModifier> values = new HashMap<>();

    public final BaseOneTuningModifier focusingSpeedMultiplier;
    public final InverseTuningModifier fuelUsageRate;
    public final BaseZeroInverseTuningModifier damageChanceMultiplier;
    public final TuningModifier bonusYieldChanceMultiplier;
    public final TuningModifier chainFocusingChanceMultiplier;
    public final TuningModifier damageAbsorptionChanceMultiplier;
    public final TuningModifier restorationChanceMultiplier;

    public CrucibleTuning(ICatalyzerAccelerationTarget target) {
        this.buffedStat = target.getTuningType();
        this.focusingSpeedMultiplier = new BaseOneTuningModifier(CrucibleTuningType.FOCUSING_SPEED, target);
        this.fuelUsageRate = new InverseTuningModifier(CrucibleTuningType.FUEL_USAGE_RATE, target);
        this.damageChanceMultiplier = new BaseZeroInverseTuningModifier(CrucibleTuningType.INSTABILITY, target);
        this.bonusYieldChanceMultiplier = new TuningModifier(CrucibleTuningType.FORTUNE_CHANCE, target);
        this.chainFocusingChanceMultiplier = new TuningModifier(CrucibleTuningType.CHAIN_FOCUSING_CHANCE, target);
        this.damageAbsorptionChanceMultiplier = new TuningModifier(CrucibleTuningType.SHIELDING_CHANCE, target);
        this.restorationChanceMultiplier = new TuningModifier(CrucibleTuningType.RESTORATION_CHANCE, target);
    }

    public enum CrucibleTuningType {
        NONE(),
        FOCUSING_SPEED(d -> d.focusingSpeed),
        FUEL_USAGE_RATE(d -> d.fuelUsageRate),
        INSTABILITY(d -> d.damageChance),
        FORTUNE_CHANCE(d -> d.bonusYieldChance),
        CHAIN_FOCUSING_CHANCE(d -> d.chainFocusingChance),
        SHIELDING_CHANCE(d -> d.damageAbsorptionChance),
        RESTORATION_CHANCE(d -> d.restorationChance);

        public final Function<CrucibleAccelerationData, CrucibleAccelerationData.TunedValue> valueGetter;
        public final Predicate<CrucibleAccelerationData> isValueValid;
        public final Function<CrucibleAccelerationData, String> statDisplayFunction;

        CrucibleTuningType() {
            this(null, null, null);
        }

        CrucibleTuningType(Function<CrucibleAccelerationData, CrucibleAccelerationData.TunedValue> valueGetter) {
            this(valueGetter, d -> valueGetter.apply(d).getValue(d) > 0, d -> String.format("%.2f", valueGetter.apply(d).getValue(d)));
        }

        CrucibleTuningType(Function<CrucibleAccelerationData, CrucibleAccelerationData.TunedValue> valueGetter, Predicate<CrucibleAccelerationData> isValueValid, Function<CrucibleAccelerationData, String> statDisplayFunction) {
            this.valueGetter = valueGetter;
            this.isValueValid = isValueValid;
            this.statDisplayFunction = statDisplayFunction;
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

    public enum AppliedTuningType {
        BUFF(TuningModifier::getPositiveMultiplier),
        DEBUFF(TuningModifier::getNegativeMultiplier),
        NONE(TuningModifier::getDefaultMultiplier);

        public final Function<TuningModifier, Float> multiplierGetter;

        AppliedTuningType(Function<TuningModifier, Float> multiplierGetter) {
            this.multiplierGetter = multiplierGetter;
        }
    }

    public static class TuningModifier {

        public static TuningModifier DEFAULT = new TuningModifier(CrucibleTuningType.NONE, AppliedTuningType.NONE);
        public final CrucibleTuningType tuningType;
        public final AppliedTuningType appliedTuningType;

        public TuningModifier(CrucibleTuningType tuningType, AppliedTuningType appliedTuningType) {
            this.tuningType = tuningType;
            this.appliedTuningType = appliedTuningType;
        }

        public TuningModifier(CrucibleTuningType tuningType, ICatalyzerAccelerationTarget target) {
            this.tuningType = tuningType;
            this.appliedTuningType = target.getTuningType().equals(CrucibleTuningType.NONE) ? AppliedTuningType.NONE : tuningType.equals(target.getTuningType()) ? AppliedTuningType.BUFF : AppliedTuningType.DEBUFF;
        }

        public CrucibleAccelerationData.TunedValue createTunedValue(float value) {
            return new CrucibleAccelerationData.TunedValue(this, value);
        }

        public float getMultiplier() {
            float value = getDefaultMultiplier();
            switch (appliedTuningType) {
                case BUFF -> value = getPositiveMultiplier();
                case DEBUFF -> value = getNegativeMultiplier();
            }
            return value;
        }

        public float getBaseValue() {
            return 0f;
        }

        public float getDifference(CrucibleAccelerationData data, CrucibleAccelerationData.TunedValue tunedValue) {
            return tunedValue.getValue(data);
        }

        protected float getMultiplierScalar(float attributeMultiplier) {
            return 1+attributeMultiplier;
        }

        private float getDefaultMultiplier() {
            return 1f;
        }

        float getPositiveMultiplier() {
            return 1.2f;
        }

        float getNegativeMultiplier() {
            return 0.9f;
        }
    }

    public static class BaseOneTuningModifier extends TuningModifier {
        public BaseOneTuningModifier(CrucibleTuningType tuningType, AppliedTuningType appliedTuningType) {
            super(tuningType, appliedTuningType);
        }

        public BaseOneTuningModifier(CrucibleTuningType tuningType, ICatalyzerAccelerationTarget target) {
            super(tuningType, target);
        }

        @Override
        public float getBaseValue() {
            return 1f;
        }
        @Override
        public float getDifference(CrucibleAccelerationData data, CrucibleAccelerationData.TunedValue tunedValue) {
            return tunedValue.getValue(data) - getBaseValue();
        }
    }

    public static class InverseTuningModifier extends BaseOneTuningModifier {
        public InverseTuningModifier(CrucibleTuningType tuningType, AppliedTuningType appliedTuningType) {
            super(tuningType, appliedTuningType);
        }

        public InverseTuningModifier(CrucibleTuningType tuningType, ICatalyzerAccelerationTarget target) {
            super(tuningType, target);
        }

        @Override
        public float getDifference(CrucibleAccelerationData data, CrucibleAccelerationData.TunedValue tunedValue) {
            return getBaseValue() - tunedValue.getValue(data);
        }
        @Override
        protected float getMultiplierScalar(float attributeMultiplier) {
            return 1-attributeMultiplier;
        }

        @Override
        float getPositiveMultiplier() {
            return 0.8f;
        }

        @Override
        float getNegativeMultiplier() {
            return 1.1f;
        }
    }

    public static class BaseZeroInverseTuningModifier extends InverseTuningModifier {
        public BaseZeroInverseTuningModifier(CrucibleTuningType tuningType, AppliedTuningType appliedTuningType) {
            super(tuningType, appliedTuningType);
        }

        public BaseZeroInverseTuningModifier(CrucibleTuningType tuningType, ICatalyzerAccelerationTarget target) {
            super(tuningType, target);
        }

        @Override
        public float getBaseValue() {
            return 0f;
        }
    }
}
