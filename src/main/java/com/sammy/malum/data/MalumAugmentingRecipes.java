package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.data.builder.SpiritAugmentingRecipeBuilder;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.core.systems.item.ItemSkin;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

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
        Ingredient both = Ingredient.of(ItemRegistry.SOUL_HUNTER_CLOAK.get(), ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(), ItemRegistry.SOUL_HUNTER_ROBE.get(), ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(), ItemRegistry.SOUL_HUNTER_LEGGINGS.get(), ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(), ItemRegistry.SOUL_HUNTER_BOOTS.get(), ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get());
        Ingredient soulHunter = Ingredient.of(ItemRegistry.SOUL_HUNTER_CLOAK.get(), ItemRegistry.SOUL_HUNTER_ROBE.get(), ItemRegistry.SOUL_HUNTER_LEGGINGS.get(), ItemRegistry.SOUL_HUNTER_BOOTS.get());
        Ingredient soulStainedSteel = Ingredient.of(ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(), ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(), ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(), ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get());

        addHoodie(consumer, Ingredient.of(ItemRegistry.ACE_PRIDEWEAVE.get()), "ace");
        addHoodie(consumer, Ingredient.of(ItemRegistry.AGENDER_PRIDEWEAVE.get()), "agender");
        addHoodie(consumer, Ingredient.of(ItemRegistry.ARO_PRIDEWEAVE.get()), "aro");
        addHoodie(consumer, Ingredient.of(ItemRegistry.AROACE_PRIDEWEAVE.get()), "aroace");
        addHoodie(consumer, Ingredient.of(ItemRegistry.BI_PRIDEWEAVE.get()), "bi");
        addHoodie(consumer, Ingredient.of(ItemRegistry.DEMIBOY_PRIDEWEAVE.get()), "demiboy");
        addHoodie(consumer, Ingredient.of(ItemRegistry.DEMIGIRL_PRIDEWEAVE.get()), "demigirl");
        addHoodie(consumer, Ingredient.of(ItemRegistry.ENBY_PRIDEWEAVE.get()), "enby");
        addHoodie(consumer, Ingredient.of(ItemRegistry.GAY_PRIDEWEAVE.get()), "gay");
        addHoodie(consumer, Ingredient.of(ItemRegistry.GENDERFLUID_PRIDEWEAVE.get()), "genderfluid");
        addHoodie(consumer, Ingredient.of(ItemRegistry.GENDERQUEER_PRIDEWEAVE.get()), "genderqueer");
        addHoodie(consumer, Ingredient.of(ItemRegistry.INTERSEX_PRIDEWEAVE.get()), "intersex");
        addHoodie(consumer, Ingredient.of(ItemRegistry.LESBIAN_PRIDEWEAVE.get()), "lesbian");
        addHoodie(consumer, Ingredient.of(ItemRegistry.PAN_PRIDEWEAVE.get()), "pan");
        addHoodie(consumer, Ingredient.of(ItemRegistry.PLURAL_PRIDEWEAVE.get()), "plural");
        addHoodie(consumer, Ingredient.of(ItemRegistry.POLY_PRIDEWEAVE.get()), "poly");
        addHoodie(consumer, Ingredient.of(ItemRegistry.PRIDE_PRIDEWEAVE.get()), "pride");
        addHoodie(consumer, Ingredient.of(ItemRegistry.TRANS_PRIDEWEAVE.get()), "trans");

        addSkin(consumer, both, Ingredient.of(Items.NETHERITE_SCRAP), "commando_drip");
        addSkin(consumer, both, Ingredient.of(Items.COMPARATOR), "executioner_drip");

        addSkin(consumer, soulStainedSteel, Ingredient.of(ItemRegistry.ANCIENT_WEAVE.get()), "ancient_metal");
        addSkin(consumer, soulHunter, Ingredient.of(ItemRegistry.ANCIENT_WEAVE.get()), "ancient_cloth");
    }

    public void addHoodie(Consumer<FinishedRecipe> consumer, Ingredient input, String value) {
        Ingredient both = Ingredient.of(ItemRegistry.SOUL_HUNTER_CLOAK.get(), ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(), ItemRegistry.SOUL_HUNTER_ROBE.get(), ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(), ItemRegistry.SOUL_HUNTER_LEGGINGS.get(), ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(), ItemRegistry.SOUL_HUNTER_BOOTS.get(), ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get());
        new SpiritAugmentingRecipeBuilder(both, input, makeStringTag(ItemSkin.MALUM_SKIN_TAG, value + "_drip")).build(consumer, MalumMod.malumPath("augmenting/hoodie_" + value));
    }

    public void addSkin(Consumer<FinishedRecipe> consumer, Ingredient armors, Ingredient input, String value) {
        new SpiritAugmentingRecipeBuilder(armors, input, makeStringTag(ItemSkin.MALUM_SKIN_TAG, value)).build(consumer, MalumMod.malumPath("augmenting/" + value));
    }

    public CompoundTag makeStringTag(String key, String value) {
        CompoundTag tag = new CompoundTag();
        tag.putString(key, value);
        return tag;
    }
}