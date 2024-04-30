package com.sammy.malum.core.handlers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class HiddenItemHandler {
	private static final Map<Supplier<? extends Item>, BooleanSupplier> ITEMS_TO_HIDE = new HashMap<>();
	private static final HashMap<UUID, Runnable> INVOKED_WHEN_CONDITIONS_CHANGE = new HashMap<>();

	public static void registerItemToHide(Supplier<? extends Item> item, BooleanSupplier condition) {
		ITEMS_TO_HIDE.put(item, condition);
	}

	public static void registerItemToHide(Block block, BooleanSupplier condition) {
		registerItemToHide(block::asItem, condition);
	}

	public static void registerItemToHide(Item item, BooleanSupplier condition) {
		registerItemToHide(item::asItem, condition);
	}

	public static void alwaysHideItem(Supplier<? extends Item> item) {
		registerItemToHide(item, () -> true);
	}

	public static void alwaysHideItem(Block block) {
		alwaysHideItem(block::asItem);
	}

	public static void alwaysHideItem(Item item) {
		alwaysHideItem(item::asItem);
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

	public static Output computeHiddenItems(List<Item> previous) {
		List<Item> toHide = new ArrayList<>();
		List<Item> toUnhide = new ArrayList<>();

		for (var entry : ITEMS_TO_HIDE.entrySet()) {
			Item item = entry.getKey().get();
			if (item != Items.AIR) {
				boolean shouldHide = entry.getValue().getAsBoolean();
				boolean wasHidden = previous.contains(item);
				if (shouldHide != wasHidden) {
					if (shouldHide)
						toHide.add(item);
					else
						toUnhide.add(item);
				}
			}
		}

		return new Output(toHide, toUnhide);
	}

	public record Output(List<Item> toHide, List<Item> toUnhide) {
		// NO-OP
	}
}
