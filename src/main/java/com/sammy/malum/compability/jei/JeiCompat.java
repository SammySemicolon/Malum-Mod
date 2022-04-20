package com.sammy.malum.compability.jei;

import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.ModList;
import se.mickelus.tetra.aspect.ItemAspect;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.items.modular.ModularItem;

public class JeiCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("jei");
    }

    public static class LoadedOnly {

        public static boolean isRecipesUi(Screen screen) {
            return screen instanceof IRecipesGui;
        }
    }
}