package com.sammy.malum.registry.client;

import com.sammy.malum.client.VoidRevelationHandler;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.core.handlers.HiddenItemHandler;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class HiddenItemRegistry {
	private static boolean voidItem() {
		return !VoidRevelationHandler.hasSeenTheRevelation();
	}

	private static boolean postCrystalItem() {
		return voidItem() || !hasBlackCrystal();
	}

	public static boolean hasBlackCrystal() {
		Player player = Minecraft.getInstance().player;
		return player != null && MalumPlayerDataCapability.getCapability(player).hasBeenRejected;
	}

	public static void registerHiddenItems() {
		// Convert to tag later?

		List.of(
			// Encyclopedia
			ItemRegistry.ENCYCLOPEDIA_ESOTERICA,

			// Base materials
			ItemRegistry.BLOCK_OF_NULL_SLATE, ItemRegistry.NULL_SLATE,
			ItemRegistry.BLOCK_OF_VOID_SALTS, ItemRegistry.VOID_SALTS,
			ItemRegistry.BLOCK_OF_MNEMONIC_FRAGMENT, ItemRegistry.MNEMONIC_FRAGMENT,
			ItemRegistry.AURIC_EMBERS,
			ItemRegistry.BLOCK_OF_MALIGNANT_LEAD, ItemRegistry.MALIGNANT_LEAD
		).forEach(it -> HiddenItemHandler.registerItemToHide(it, HiddenItemRegistry::voidItem));

		List.of(
			// Umbral Spirit
			ItemRegistry.UMBRAL_SPIRIT,

			// Anomalous Design
			ItemRegistry.ANOMALOUS_DESIGN, ItemRegistry.COMPLETE_DESIGN, ItemRegistry.FUSED_CONSCIOUSNESS,

			// Malignant Pewter
			ItemRegistry.MALIGNANT_PEWTER_INGOT, ItemRegistry.MALIGNANT_PEWTER_PLATING,
			ItemRegistry.MALIGNANT_PEWTER_NUGGET, ItemRegistry.BLOCK_OF_MALIGNANT_PEWTER,

			// Equipment
			ItemRegistry.MALIGNANT_STRONGHOLD_HELMET, ItemRegistry.MALIGNANT_STRONGHOLD_CHESTPLATE,
			ItemRegistry.MALIGNANT_STRONGHOLD_LEGGINGS, ItemRegistry.MALIGNANT_STRONGHOLD_BOOTS,
			ItemRegistry.WEIGHT_OF_WORLDS, ItemRegistry.EROSION_SCEPTER,

			// Runes
			ItemRegistry.VOID_TABLET,
			ItemRegistry.RUNE_OF_BOLSTERING, ItemRegistry.RUNE_OF_SACRIFICIAL_EMPOWERMENT,
			ItemRegistry.RUNE_OF_SPELL_MASTERY, ItemRegistry.RUNE_OF_THE_HERETIC,
			ItemRegistry.RUNE_OF_UNNATURAL_STAMINA, ItemRegistry.RUNE_OF_TWINNED_DURATION,
			ItemRegistry.RUNE_OF_TOUGHNESS, ItemRegistry.RUNE_OF_IGNEOUS_SOLACE,

			// Trinkets
			ItemRegistry.RING_OF_THE_ENDLESS_WELL, ItemRegistry.RING_OF_GROWING_FLESH,
			ItemRegistry.RING_OF_GRUESOME_CONCENTRATION, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE,
			ItemRegistry.NECKLACE_OF_THE_WATCHER, ItemRegistry.BELT_OF_THE_LIMITLESS
		).forEach(it -> HiddenItemHandler.registerItemToHide(it, HiddenItemRegistry::postCrystalItem));
	}
}
