package com.sammy.malum.registry.client;

import com.sammy.malum.client.VoidRevelationHandler;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.core.handlers.HiddenTagHandler;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class HiddenTagRegistry {
	public static boolean hasBlackCrystal() {
		Player player = Minecraft.getInstance().player;
		return player != null && MalumPlayerDataCapability.getCapability(player).hasBeenRejected;
	}

	public static void registerHiddenTags() {
		HiddenTagHandler.hideTagWhen(ItemTagRegistry.HIDDEN_ALWAYS, () -> true);
		HiddenTagHandler.hideTagWhen(ItemTagRegistry.HIDDEN_UNTIL_VOID, () -> !VoidRevelationHandler.hasSeenTheRevelation());
		HiddenTagHandler.hideTagWhen(ItemTagRegistry.HIDDEN_UNTIL_BLACK_CRYSTAL, () -> !hasBlackCrystal());
	}
}
