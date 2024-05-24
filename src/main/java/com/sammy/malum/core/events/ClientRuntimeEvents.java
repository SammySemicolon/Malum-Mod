package com.sammy.malum.core.events;

import com.sammy.malum.client.renderer.block.*;
import com.sammy.malum.common.item.augment.*;

public class ClientRuntimeEvents {


    @SubscribeEvent
    public static void itemTooltipEvent(ItemTooltipEvent event) {
        AbstractAugmentItem.addAugmentAttributeTooltip(event);
    }
}
