package com.sammy.malum.data.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.data.recipe.builder.VoidFavorRecipeBuilder;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class MalumVoidFavorRecipes {

    protected static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.ENCYCLOPEDIA_ARCANA.get()), ItemRegistry.ENCYCLOPEDIA_ESOTERICA.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(Ingredient.of(Tags.Items.RAW_MATERIALS), ItemRegistry.RAW_SOULSTONE.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(Ingredient.of(Tags.Items.STORAGE_BLOCKS_IRON), ItemRegistry.ANOMALOUS_DESIGN.get(), 1)
                .build(consumer);
        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.COMPLETE_DESIGN.get()), ItemRegistry.FUSED_CONSCIOUSNESS.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(ItemRegistry.PROCESSED_SOULSTONE.get(), ItemRegistry.NULL_SLATE.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(ItemRegistry.HEX_ASH.get(), ItemRegistry.VOID_SALTS.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(ItemRegistry.CHUNK_OF_BRILLIANCE.get(), ItemRegistry.MNEMONIC_FRAGMENT.get(), 1)
                .build(consumer);
        new VoidFavorRecipeBuilder(ItemRegistry.CLUSTER_OF_BRILLIANCE.get(), ItemRegistry.MNEMONIC_FRAGMENT.get(), 2)
                .build(consumer, MalumMod.malumPath("mnemonic_fragment_from_cluster"));

        new VoidFavorRecipeBuilder(Items.BLAZE_POWDER, ItemRegistry.AURIC_EMBERS.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(ItemRegistry.CTHONIC_GOLD.get(), ItemRegistry.MALIGNANT_LEAD.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.THE_DEVICE.get()), ItemRegistry.THE_VESSEL.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(Items.BLACK_WOOL, ItemRegistry.TOPHAT.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(ItemRegistry.ARCANE_ELEGY.get(), ItemRegistry.AESTHETICA.get(), 1)
                .build(consumer);
    }
}
