package com.sammy.malum.registry.client;

import com.sammy.malum.client.VoidRevelationHandler;
import com.sammy.malum.core.handlers.HiddenTagHandler;
import com.sammy.malum.registry.common.item.ItemTagRegistry;

import static com.sammy.malum.client.VoidRevelationHandler.RevelationType.BLACK_CRYSTAL;
import static com.sammy.malum.client.VoidRevelationHandler.RevelationType.VOID_READER;

public class HiddenTagRegistry {

    public static void registerHiddenTags() {
        HiddenTagHandler.hideTagWhen(ItemTagRegistry.HIDDEN_ALWAYS, () -> true);
        HiddenTagHandler.hideTagWhen(ItemTagRegistry.HIDDEN_UNTIL_VOID, () -> !VoidRevelationHandler.hasSeenTheRevelation(VOID_READER));
        HiddenTagHandler.hideTagWhen(ItemTagRegistry.HIDDEN_UNTIL_BLACK_CRYSTAL, () -> !VoidRevelationHandler.hasSeenTheRevelation(BLACK_CRYSTAL));
    }
}
