package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.data.builder.SpiritAugmentingRecipeBuilder;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.item.ItemSkin;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class MalumAugmentingRecipes extends RecipeProvider {
    public MalumAugmentingRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Malum Augmenting Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        addHoodie(consumer, Ingredient.of(Items.BREAD), "ace");
        addHoodie(consumer, Ingredient.of(Items.ARROW), "aro");
        addHoodie(consumer, Ingredient.of(Items.WHEAT_SEEDS), "aroace");
        addHoodie(consumer, Ingredient.of(Items.WHEAT), "bi");
        addHoodie(consumer, Ingredient.of(Items.RAW_IRON), "demiboy");
        addHoodie(consumer, Ingredient.of(Items.RAW_COPPER), "demigirl");
        addHoodie(consumer, Ingredient.of(Items.MOSS_BLOCK), "enby");
        addHoodie(consumer, Ingredient.of(Items.MELON_SLICE), "gay");
        addHoodie(consumer, Ingredient.of(Items.WATER_BUCKET), "genderfluid");
        addHoodie(consumer, Ingredient.of(Items.GLASS_BOTTLE), "genderqueer");
        addHoodie(consumer, Ingredient.of(Items.AZALEA), "intersex");
        addHoodie(consumer, Ingredient.of(Items.HONEYCOMB), "lesbian");
        addHoodie(consumer, Ingredient.of(Tags.Items.CROPS_CARROT), "pan");
        addHoodie(consumer, Ingredient.of(Items.REPEATER), "plural");
        addHoodie(consumer, Ingredient.of(Items.COMPARATOR), "poly");
        addHoodie(consumer, Ingredient.of(Items.STONE_BRICK_WALL), "pride");
        addHoodie(consumer, Ingredient.of(Tags.Items.EGGS), "trans");

        addSkin(consumer, Ingredient.of(Items.NETHERITE_SCRAP), "commando_drip");
    }

    public void addHoodie(Consumer<FinishedRecipe> consumer, Ingredient input, String value) {
        Ingredient armors = Ingredient.of(ItemRegistry.SOUL_HUNTER_CLOAK.get(), ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(), ItemRegistry.SOUL_HUNTER_ROBE.get(), ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(), ItemRegistry.SOUL_HUNTER_LEGGINGS.get(), ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(), ItemRegistry.SOUL_HUNTER_BOOTS.get(), ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get());
        new SpiritAugmentingRecipeBuilder(armors, input, makeStringTag(ItemSkin.MALUM_SKIN_TAG, value + "_drip")).build(consumer, MalumMod.malumPath("augmenting/hoodie_" + value));
    }

    public void addSkin(Consumer<FinishedRecipe> consumer, Ingredient input, String value) {
        Ingredient armors = Ingredient.of(ItemRegistry.SOUL_HUNTER_ROBE.get(), ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get());
        new SpiritAugmentingRecipeBuilder(armors, input, makeStringTag(ItemSkin.MALUM_SKIN_TAG, value)).build(consumer, MalumMod.malumPath("augmenting/" + value));
    }

    public CompoundTag makeStringTag(String key, String value) {
        CompoundTag tag = new CompoundTag();
        tag.putString(key, value);
        return tag;
    }
}