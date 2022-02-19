package com.sammy.malum.compability.farmersdelight;

import com.sammy.malum.core.setup.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import vectorwing.farmersdelight.common.utility.TextUtils;

public class FarmersDelightCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("farmersdelight");
    }
    public static class LoadedOnly
    {
        public static boolean isMagicKnife(Item item)
        {
            return item instanceof MagicMalumKnifeItem;
        }

        public static void addInfo(IRecipeRegistration registration)
        {
            registration.addIngredientInfo(new ItemStack(ItemRegistry.SOUL_STAINED_STEEL_KNIFE.get()), VanillaTypes.ITEM, TextUtils.getTranslation("jei.info.knife"));
        }
    }
}