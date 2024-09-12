
package com.sammy.malum.core.events;

import com.sammy.malum.common.item.curiosities.*;

public class RuntimeEvents {

    @SubscribeEvent
    public static void anvilUpdate(AnvilUpdateEvent event) {
        CatalystFlingerItem.anvilUpdate(event);
    }
}
