package com.sammy.malum.data.recipe.infusion;

import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class TotemicSpiritInfusionRecipes {

    public static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_PLANKS.get(), 2, ItemRegistry.RUNEWOOD_OBELISK.get(), 1)
                .addExtraItem(ItemRegistry.HALLOWED_GOLD_INGOT.get(), 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(SACRED_SPIRIT, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_PLANKS.get(), 2, ItemRegistry.BRILLIANT_OBELISK.get(), 1)
                .addExtraItem(ItemRegistry.CLUSTER_OF_BRILLIANCE.get(), 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(AQUEOUS_SPIRIT, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_LOG.get(), 4, ItemRegistry.RUNEWOOD_TOTEM_BASE.get(), 4)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 6)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOULWOOD_LOG.get(), 4, ItemRegistry.SOULWOOD_TOTEM_BASE.get(), 4)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 6)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .build(consumer);
    }
}
