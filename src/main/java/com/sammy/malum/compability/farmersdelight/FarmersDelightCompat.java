package com.sammy.malum.compability.farmersdelight;

import com.sammy.malum.common.item.curiosities.MagicKnifeItem;
import com.sammy.malum.registry.common.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static com.sammy.malum.registry.common.item.ItemTiers.ItemTierEnum.SOUL_STAINED_STEEL;

public class FarmersDelightCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = FabricLoader.getInstance().isModLoaded("farmersdelight");
    }

    public static class LoadedOnly {

    }
}
