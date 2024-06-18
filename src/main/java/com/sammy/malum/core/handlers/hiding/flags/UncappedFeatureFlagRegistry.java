package com.sammy.malum.core.handlers.hiding.flags;

import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagUniverse;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Consumer;

public class UncappedFeatureFlagRegistry {

    private static final Logger LOGGER = LogUtils.getLogger();
    private final FeatureFlagUniverse universe;
    private final Map<ResourceLocation, UncappedFeatureFlag> names;
    private final UncappedFeatureFlagSet allFlags;

    public UncappedFeatureFlagRegistry(FeatureFlagUniverse pUniverse, UncappedFeatureFlagSet pAllFlags, Map<ResourceLocation, UncappedFeatureFlag> pNames) {
        this.universe = pUniverse;
        this.names = pNames;
        this.allFlags = pAllFlags;
    }

    public boolean isSubset(UncappedFeatureFlagSet pSet) {
        return pSet.isSubsetOf(this.allFlags);
    }

    public UncappedFeatureFlagSet allFlags() {
        return this.allFlags;
    }

    public UncappedFeatureFlagSet fromNames(Iterable<ResourceLocation> pNames) {
        return this.fromNames(pNames, (flag) -> {
            LOGGER.warn("Unknown feature flag: {}", flag);
        });
    }

    public UncappedFeatureFlagSet subset(UncappedFeatureFlag... pFlags) {
        return UncappedFeatureFlagSet.create(this.universe, Arrays.asList(pFlags));
    }

    public UncappedFeatureFlagSet fromNames(Iterable<ResourceLocation> pNames, Consumer<ResourceLocation> pOnError) {
        Set<UncappedFeatureFlag> set = Sets.newIdentityHashSet();

        for (ResourceLocation resourcelocation : pNames) {
            UncappedFeatureFlag featureflag = this.names.get(resourcelocation);
            if (featureflag == null) {
                pOnError.accept(resourcelocation);
            } else {
                set.add(featureflag);
            }
        }

        return UncappedFeatureFlagSet.create(this.universe, set);
    }

    public Set<ResourceLocation> toNames(UncappedFeatureFlagSet pSet) {
        Set<ResourceLocation> set = new HashSet<>();
        this.names.forEach((resourceLocation, featureFlag) -> {
            if (pSet.contains(featureFlag)) {
                set.add(resourceLocation);
            }

        });
        return set;
    }

    public Codec<UncappedFeatureFlagSet> codec() {
        return ResourceLocation.CODEC.listOf().comapFlatMap((resourceLocations) -> {
            Set<ResourceLocation> errorSet = new HashSet<>();
            UncappedFeatureFlagSet flagSet = this.fromNames(resourceLocations, errorSet::add);
            return !errorSet.isEmpty() ? DataResult.error(() -> "Unknown feature ids: " + errorSet, flagSet) : DataResult.success(flagSet);
        }, (flagSet) -> List.copyOf(this.toNames(flagSet)));
    }

    public static class Builder {
        private final FeatureFlagUniverse universe;
        private int id;
        private final Map<ResourceLocation, UncappedFeatureFlag> flags = new LinkedHashMap<>();

        public Builder(String pId) {
            this.universe = new FeatureFlagUniverse(pId);
        }

        public UncappedFeatureFlag createVanilla(String pId) {
            return this.create(new ResourceLocation("minecraft", pId));
        }

        public UncappedFeatureFlag create(ResourceLocation pLocation) {
            if (this.id >= 64) {
                throw new IllegalStateException("Too many feature flags");
            } else {
                UncappedFeatureFlag featureflag = new UncappedFeatureFlag(this.universe, this.id++);
                UncappedFeatureFlag featureflag1 = this.flags.put(pLocation, featureflag);
                if (featureflag1 != null) {
                    throw new IllegalStateException("Duplicate feature flag " + pLocation);
                } else {
                    return featureflag;
                }
            }
        }

        public UncappedFeatureFlagRegistry build() {
            UncappedFeatureFlagSet flagSet = UncappedFeatureFlagSet.create(this.universe, this.flags.values());
            return new UncappedFeatureFlagRegistry(this.universe, flagSet, Map.copyOf(this.flags));
        }
    }
}
