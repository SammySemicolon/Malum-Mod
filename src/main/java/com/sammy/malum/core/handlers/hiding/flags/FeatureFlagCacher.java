package com.sammy.malum.core.handlers.hiding.flags;

import net.minecraft.resources.ResourceLocation;

public interface FeatureFlagCacher {
	Iterable<ResourceLocation> malum$cachedFeatureFlags();
}
