package com.sammy.malum.compability.jei;

import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.fml.ModList;

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