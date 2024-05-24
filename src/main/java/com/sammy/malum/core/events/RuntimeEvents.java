package com.sammy.malum.core.events;

import com.sammy.malum.common.enchantment.*;

public class RuntimeEvents {

    @SubscribeEvent
    public static void addItemAttributes(ItemAttributeModifierEvent event) {
        HauntedEnchantment.addMagicDamage(event);
    }
}

