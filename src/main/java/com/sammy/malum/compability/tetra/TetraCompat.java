package com.sammy.malum.compability.tetra;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.items.modular.ModularItem;

public class TetraCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("tetra");
    }

    public static class LoadedOnly {
        private static final ItemEffect SOUL_STRIKE = ItemEffect.get("malum.soul_strike");

        public static boolean hasSoulStrike(ItemStack stack) {
            if (stack.getItem() instanceof ModularItem modularItem) {
                return modularItem.getEffectLevel(stack, SOUL_STRIKE) > 0;
            }
            return false;
        }
    }
}