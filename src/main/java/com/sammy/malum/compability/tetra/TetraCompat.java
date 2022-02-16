package com.sammy.malum.compability.tetra;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.ModList;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.items.modular.ModularItem;

public class TetraCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("tetra");
    }

    public static class LoadedOnly {
        private static final ItemEffect SHATTERS_SOULS = ItemEffect.get("malum.shatters_souls");

        public static boolean hasSoulStrike(ItemStack stack) {
            if (stack.getItem() instanceof ModularItem modularItem) {
                return modularItem.getEffectLevel(stack, SHATTERS_SOULS) > 0;
            }
            return false;
        }

        public static void fireArrow(EntityJoinWorldEvent event) {
            if (event.getEntity() instanceof Arrow arrow) {
                if (arrow.getOwner() instanceof Player player) {
                    if (hasSoulStrike(player.getUseItem())) {
                        arrow.addTag("malum:soul_arrow");
                    }
                }
            }
        }
    }
}