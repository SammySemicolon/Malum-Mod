package com.sammy.malum.compability.farmersdelight;

import com.sammy.malum.core.setup.content.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import team.lodestar.lodestone.systems.item.tools.magic.MagicKnifeItem;
import vectorwing.farmersdelight.common.utility.TextUtils;

import static com.sammy.malum.core.setup.content.item.ItemRegistry.GEAR_PROPERTIES;
import static com.sammy.malum.core.setup.content.item.ItemTiers.ItemTierEnum.SOUL_STAINED_STEEL;

public class FarmersDelightCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("farmersdelight");
    }

    public static class LoadedOnly {

        public static Item makeMagicKnife() {
            return new MagicKnifeItem(SOUL_STAINED_STEEL, -1.5f, 0, 2, GEAR_PROPERTIES());
        }

        public static void addInfo(IRecipeRegistration registration) {
            registration.addIngredientInfo(new ItemStack(ItemRegistry.SOUL_STAINED_STEEL_KNIFE.get()), VanillaTypes.ITEM_STACK, TextUtils.getTranslation("jei.info.knife"));
        }
    }
}
