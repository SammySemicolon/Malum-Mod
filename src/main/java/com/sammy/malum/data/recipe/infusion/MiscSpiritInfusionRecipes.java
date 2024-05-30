package com.sammy.malum.data.recipe.infusion;

import com.sammy.malum.data.recipe.builder.SpiritInfusionRecipeBuilder;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class MiscSpiritInfusionRecipes {

    public static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_PLANKS.get(), 2, ItemRegistry.RUNEWOOD_OBELISK.get(), 1)
                .addExtraItem(ItemRegistry.HALLOWED_GOLD_INGOT.get(), 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addSpirit(SACRED_SPIRIT, 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_PLANKS.get(), 2, ItemRegistry.BRILLIANT_OBELISK.get(), 1)
                .addExtraItem(ItemRegistry.CLUSTER_OF_BRILLIANCE.get(), 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addSpirit(AQUEOUS_SPIRIT, 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_ITEM_PEDESTAL.get(), 1, ItemRegistry.RUNIC_WORKBENCH.get(), 1)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 4)
                .addExtraItem(ItemRegistry.HALLOWED_GOLD_INGOT.get(), 2)
                .addSpirit(AQUEOUS_SPIRIT, 16)
                .addSpirit(SACRED_SPIRIT, 8)
                .build(consumer);

    }
}
