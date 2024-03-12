package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraftforge.common.crafting.conditions.*;

import java.util.function.*;

public class MalumRuneworkingRecipes implements IConditionBuilder {

    protected static void buildRecipes(Consumer<FinishedRecipe> consumer) {

        new RunicWorkbenchRecipeBuilder(ItemRegistry.TAINTED_SLATE.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK.get(), 4)
                .setSecondaryInput(ItemRegistry.PROCESSED_SOULSTONE.get(), 8)
                .build(consumer, "tainted_slate");

        new RunicWorkbenchRecipeBuilder(ItemRegistry.ALTERED_SLATE.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.NULL_SLATE.get(), 8)
                .build(consumer, "altered_slate");

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_IDLE_RESTORATION.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.SACRED_SPIRIT.get(), 32)
                .build(consumer, "rune_of_idle_restoration");
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_BOLSTERING.get(), 1)
                .setPrimaryInput(ItemRegistry.ALTERED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.SACRED_SPIRIT.get(), 32)
                .build(consumer, "rune_of_bolstering");

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_CULLING.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.WICKED_SPIRIT.get(), 32)
                .build(consumer, "rune_of_culling");
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_SACRIFICIAL_EMPOWERMENT.get(), 1)
                .setPrimaryInput(ItemRegistry.ALTERED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.WICKED_SPIRIT.get(), 32)
                .build(consumer, "rune_of_sacrificial_empowerment");

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_REINFORCEMENT.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.ARCANE_SPIRIT.get(), 32)
                .build(consumer, "rune_of_reinforcement");
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_SPELL_MASTERY.get(), 1)
                .setPrimaryInput(ItemRegistry.ALTERED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.ARCANE_SPIRIT.get(), 32)
                .build(consumer, "rune_of_spell_mastery");

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_VOLATILE_DISTORTION.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.ELDRITCH_SPIRIT.get(), 8)
                .build(consumer, "rune_of_volatile_distortion");
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_HERETIC.get(), 1)
                .setPrimaryInput(ItemRegistry.ALTERED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.ELDRITCH_SPIRIT.get(), 8)
                .build(consumer, "rune_of_the_heretic");

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_DEXTERITY.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.AERIAL_SPIRIT.get(), 32)
                .build(consumer, "rune_of_dexterity");
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_UNNATURAL_STAMINA.get(), 1)
                .setPrimaryInput(ItemRegistry.ALTERED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.AERIAL_SPIRIT.get(), 32)
                .build(consumer, "rune_of_unnatural_stamina");

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_ALIMENT_CLEANSING.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.AQUEOUS_SPIRIT.get(), 32)
                .build(consumer, "rune_of_aliment_cleansing");
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_TWINNED_DURATION.get(), 1)
                .setPrimaryInput(ItemRegistry.ALTERED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.AQUEOUS_SPIRIT.get(), 32)
                .build(consumer, "rune_of_twinned_duration");

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_REACTIVE_SHIELDING.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.EARTHEN_SPIRIT.get(), 32)
                .build(consumer, "rune_of_reactive_shielding");
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_TOUGHNESS.get(), 1)
                .setPrimaryInput(ItemRegistry.ALTERED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.EARTHEN_SPIRIT.get(), 32)
                .build(consumer, "rune_of_toughness");

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_HASTE.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.INFERNAL_SPIRIT.get(), 32)
                .build(consumer, "rune_of_haste");
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_IGNEOUS_SOLACE.get(), 1)
                .setPrimaryInput(ItemRegistry.ALTERED_SLATE.get(), 1)
                .setSecondaryInput(ItemRegistry.INFERNAL_SPIRIT.get(), 32)
                .build(consumer, "rune_of_igneous_solace");

    }
}
