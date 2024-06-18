package com.sammy.malum.core.handlers.hiding.flags;

import net.minecraft.world.flag.FeatureFlagUniverse;

public class UncappedFeatureFlag {
    public final FeatureFlagUniverse universe;
    public final int maskBit;

    public UncappedFeatureFlag(FeatureFlagUniverse pUniverse, int pMaskBit) {
        this.universe = pUniverse;
        this.maskBit = pMaskBit;
    }
}
