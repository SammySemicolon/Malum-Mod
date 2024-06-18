package com.sammy.malum.core.handlers.hiding;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagRegistry;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;

import java.util.*;
import java.util.function.BooleanSupplier;

public class HiddenTagHandler {
    private static final Map<TagKey<Item>, BooleanSupplier> ITEMS_TO_HIDE = new HashMap<>();
    private static final HashMap<UUID, Runnable> INVOKED_WHEN_CONDITIONS_CHANGE = new HashMap<>();

    public static void hideTagWhen(TagKey<Item> item, BooleanSupplier condition) {
        ITEMS_TO_HIDE.put(item, condition);
    }


	private static FeatureFlagRegistry FLAGS;
	private static final Map<TagKey<Item>, FeatureFlag> FEATURE_FLAGS = new HashMap<>();

	public static void buildFeatureFlagSet(ResourceLocation name) {
		var builder = new FeatureFlagRegistry.Builder(name.toString());
		for (TagKey<Item> tag : ITEMS_TO_HIDE.keySet()) {
			FEATURE_FLAGS.put(tag, builder.create(tag.location()));
		}
		FLAGS = builder.build();
	}

	public static FeatureFlagSet createFeatureFlagSet() {
		Set<FeatureFlag> flags = new HashSet<>();
		for (var entry : ITEMS_TO_HIDE.entrySet()) {
			FeatureFlag flag = FEATURE_FLAGS.get(entry.getKey());
			if (flag != null && !entry.getValue().getAsBoolean())
				flags.add(flag);
		}
		return FLAGS.subset(flags.toArray(new FeatureFlag[0]));
	}

	public static FeatureFlagSet createAllEnabledFlagSet() {
		return FLAGS.allFlags();
	}

	public static UUID registerHiddenItemListener(Runnable runnable) {
		runnable.run();
		UUID uuid = UUID.randomUUID();
		INVOKED_WHEN_CONDITIONS_CHANGE.put(uuid, runnable);
		return uuid;
	}

    public static void removeListener(UUID listener) {
        INVOKED_WHEN_CONDITIONS_CHANGE.remove(listener);
    }

    public static void conditionsChanged() {
        INVOKED_WHEN_CONDITIONS_CHANGE.values().forEach(Runnable::run);
    }

    public static List<TagKey<Item>> tagsToHide() {
        List<TagKey<Item>> tags = new ArrayList<>();
        for (var entry : ITEMS_TO_HIDE.entrySet()) {
            if (entry.getValue().getAsBoolean())
                tags.add(entry.getKey());
        }
        return tags;
    }
}
