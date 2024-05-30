package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.builder.RunicWorkbenchRecipeBuilder;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class MalumRuneworkingRecipes {

    protected static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_IDLE_RESTORATION.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.SACRED_SPIRIT.get(), 16)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_BOLSTERING.get(), 1)
                .setPrimaryInput(ItemRegistry.VOID_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.SACRED_SPIRIT.get(), 16)
                .build(consumer);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_CULLING.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.WICKED_SPIRIT.get(), 16)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_SACRIFICIAL_EMPOWERMENT.get(), 1)
                .setPrimaryInput(ItemRegistry.VOID_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.WICKED_SPIRIT.get(), 16)
                .build(consumer);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_REINFORCEMENT.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.ARCANE_SPIRIT.get(), 16)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_SPELL_MASTERY.get(), 1)
                .setPrimaryInput(ItemRegistry.VOID_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.ARCANE_SPIRIT.get(), 16)
                .build(consumer);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_VOLATILE_DISTORTION.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.ELDRITCH_SPIRIT.get(), 16)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_HERETIC.get(), 1)
                .setPrimaryInput(ItemRegistry.VOID_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.ELDRITCH_SPIRIT.get(), 16)
                .build(consumer);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_DEXTERITY.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.AERIAL_SPIRIT.get(), 16)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_UNNATURAL_STAMINA.get(), 1)
                .setPrimaryInput(ItemRegistry.VOID_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.AERIAL_SPIRIT.get(), 16)
                .build(consumer);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_ALIMENT_CLEANSING.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.AQUEOUS_SPIRIT.get(), 16)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_TWINNED_DURATION.get(), 1)
                .setPrimaryInput(ItemRegistry.VOID_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.AQUEOUS_SPIRIT.get(), 16)
                .build(consumer);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_REACTIVE_SHIELDING.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.EARTHEN_SPIRIT.get(), 16)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_TOUGHNESS.get(), 1)
                .setPrimaryInput(ItemRegistry.VOID_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.EARTHEN_SPIRIT.get(), 16)
                .build(consumer);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_FERVOR.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.INFERNAL_SPIRIT.get(), 16)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_IGNEOUS_SOLACE.get(), 1)
                .setPrimaryInput(ItemRegistry.VOID_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.INFERNAL_SPIRIT.get(), 16)
                .build(consumer);


        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_MOTION.get(), 1)
                .setPrimaryInput(ItemRegistry.RUNEWOOD_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.AERIAL_SPIRIT.get(), 32)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_AETHER.get(), 1)
                .setPrimaryInput(ItemRegistry.SOULWOOD_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.AERIAL_SPIRIT.get(), 32)
                .build(consumer);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_LOYALTY.get(), 1)
                .setPrimaryInput(ItemRegistry.RUNEWOOD_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.AQUEOUS_SPIRIT.get(), 32)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_SEAS.get(), 1)
                .setPrimaryInput(ItemRegistry.SOULWOOD_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.AQUEOUS_SPIRIT.get(), 32)
                .build(consumer);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_WARDING.get(), 1)
                .setPrimaryInput(ItemRegistry.RUNEWOOD_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.EARTHEN_SPIRIT.get(), 32)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_ARENA.get(), 1)
                .setPrimaryInput(ItemRegistry.SOULWOOD_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.EARTHEN_SPIRIT.get(), 32)
                .build(consumer);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_HASTE.get(), 1)
                .setPrimaryInput(ItemRegistry.RUNEWOOD_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.INFERNAL_SPIRIT.get(), 32)
                .build(consumer);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_HELLS.get(), 1)
                .setPrimaryInput(ItemRegistry.SOULWOOD_TABLET.get(), 1)
                .setSecondaryInput(ItemRegistry.INFERNAL_SPIRIT.get(), 32)
                .build(consumer);

    }
}
