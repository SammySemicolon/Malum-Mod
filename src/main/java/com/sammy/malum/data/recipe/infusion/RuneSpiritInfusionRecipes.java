package com.sammy.malum.data.recipe.infusion;

import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class RuneSpiritInfusionRecipes {

    public static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_ROCK.get(), 4, ItemRegistry.TAINTED_SLATE.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(AERIAL_SPIRIT, 4)
                .addSpirit(AQUEOUS_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(INFERNAL_SPIRIT, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_SLATE.get(), 1, ItemRegistry.ALTERED_SLATE.get(), 1)
                .addSpirit(ELDRITCH_SPIRIT, 8)
                .addExtraItem(ItemRegistry.NULL_SLATE.get(), 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_SLATE.get(), 1, ItemRegistry.RUNE_OF_IDLE_RESTORATION.get(), 1)
                .addSpirit(SACRED_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_SLATE.get(), 1, ItemRegistry.RUNE_OF_CULLING.get(), 1)
                .addSpirit(WICKED_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_SLATE.get(), 1, ItemRegistry.RUNE_OF_REINFORCEMENT.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_SLATE.get(), 1, ItemRegistry.RUNE_OF_VOLATILE_DISTORTION.get(), 1)
                .addSpirit(ELDRITCH_SPIRIT, 8)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_SLATE.get(), 1, ItemRegistry.RUNE_OF_DEXTERITY.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_SLATE.get(), 1, ItemRegistry.RUNE_OF_ALIMENT_CLEANSING.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_SLATE.get(), 1, ItemRegistry.RUNE_OF_REACTIVE_SHIELDING.get(), 1)
                .addSpirit(EARTHEN_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_SLATE.get(), 1, ItemRegistry.RUNE_OF_HASTE.get(), 1)
                .addSpirit(INFERNAL_SPIRIT, 32)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALTERED_SLATE.get(), 1, ItemRegistry.RUNE_OF_BOLSTERING.get(), 1)
                .addSpirit(SACRED_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.ALTERED_SLATE.get(), 1, ItemRegistry.RUNE_OF_SACRIFICIAL_EMPOWERMENT.get(), 1)
                .addSpirit(WICKED_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.ALTERED_SLATE.get(), 1, ItemRegistry.RUNE_OF_SPELL_MASTERY.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.ALTERED_SLATE.get(), 1, ItemRegistry.RUNE_OF_THE_HERETIC.get(), 1)
                .addSpirit(ELDRITCH_SPIRIT, 8)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.ALTERED_SLATE.get(), 1, ItemRegistry.RUNE_OF_UNNATURAL_STAMINA.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.ALTERED_SLATE.get(), 1, ItemRegistry.RUNE_OF_TWINNED_DURATION.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.ALTERED_SLATE.get(), 1, ItemRegistry.RUNE_OF_TOUGHNESS.get(), 1)
                .addSpirit(EARTHEN_SPIRIT, 32)
                .build(consumer);
        new SpiritInfusionRecipeBuilder(ItemRegistry.ALTERED_SLATE.get(), 1, ItemRegistry.RUNE_OF_IGNEOUS_SOLACE.get(), 1)
                .addSpirit(INFERNAL_SPIRIT, 32)
                .build(consumer);
    }
}
