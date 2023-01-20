package com.sammy.malum.data.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.data.recipe.builder.SpiritFocusingRecipeBuilder;
import com.sammy.malum.data.recipe.builder.SpiritRepairRecipeBuilder;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;
import static team.lodestar.lodestone.setup.LodestoneItemTags.*;

public class MalumSpiritFocusingRecipes extends RecipeProvider implements IConditionBuilder {
    public MalumSpiritFocusingRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Malum Spirit Crucible Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        int metalDuration = 900;
        int shortDuration = 300;

        new SpiritRepairRecipeBuilder("wooden_", 0.5f, Ingredient.of(ItemTags.PLANKS), 4)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "wooden");

        new SpiritRepairRecipeBuilder("flint_", 0.5f, Ingredient.of(Items.FLINT), 2)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .build(consumer, "flint");

        new SpiritRepairRecipeBuilder("stone_", 0.5f, Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 2)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .build(consumer, "stone");

        new SpiritRepairRecipeBuilder("copper_", 0.5f, Ingredient.of(Tags.Items.INGOTS_COPPER), 2)
                .addSpirit(EARTHEN_SPIRIT, 6)
                .build(consumer, "copper");

        new SpiritRepairRecipeBuilder("iron_", 0.5f, Ingredient.of(Tags.Items.INGOTS_IRON), 2)
                .addItem(ItemRegistry.CRUDE_SCYTHE.get())
                .addSpirit(EARTHEN_SPIRIT, 8)
                .build(consumer, "iron");

        new SpiritRepairRecipeBuilder("golden_", 0.5f, Ingredient.of(Tags.Items.INGOTS_GOLD), 2)
                .addSpirit(ARCANE_SPIRIT, 8)
                .build(consumer, "gold");

        new SpiritRepairRecipeBuilder("diamond_", 0.5f, Ingredient.of(Tags.Items.GEMS_DIAMOND), 2)
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .build(consumer, "diamond");

        new SpiritRepairRecipeBuilder("netherite_", 0.5f, Ingredient.of(Tags.Items.INGOTS_NETHERITE), 1)
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .build(consumer, "netherite");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), 8)
                .addItem(ItemRegistry.TYRVING.get())
                .addSpirit(WICKED_SPIRIT, 16)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(ELDRITCH_SPIRIT, 2)
                .build(consumer, "tyrving");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), 4)
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_HELMET.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get())
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(WICKED_SPIRIT, 4)
                .build(consumer, "special_soul_stained_steel");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), 2)
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_AXE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_PICKAXE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SHOVEL.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_HOE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_KNIFE.get())
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(WICKED_SPIRIT, 2)
                .build(consumer, "soul_stained_steel");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SPIRIT_FABRIC.get()), 4)
                .addItem(ItemRegistry.SOUL_HUNTER_CLOAK.get())
                .addItem(ItemRegistry.SOUL_HUNTER_ROBE.get())
                .addItem(ItemRegistry.SOUL_HUNTER_LEGGINGS.get())
                .addItem(ItemRegistry.SOUL_HUNTER_BOOTS.get())
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(WICKED_SPIRIT, 4)
                .build(consumer, "soul_hunter_armor");

        new SpiritRepairRecipeBuilder("none", 1f, Ingredient.of(ItemRegistry.ARCANE_SPIRIT.get()), 8)
                .addItem(ItemRegistry.ALCHEMICAL_IMPETUS.get().getCrackedVariant())
                .build(consumer, "alchemical_impetus_restoration");

        new SpiritRepairRecipeBuilder("none", 1f, Ingredient.of(ItemRegistry.INFERNAL_SPIRIT.get()), 8)
                .addItem(ItemRegistry.CRACKED_IRON_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_GOLD_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_COPPER_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_LEAD_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_SILVER_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_ALUMINUM_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_NICKEL_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_URANIUM_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_OSMIUM_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_ZINC_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_TIN_IMPETUS.get())
                .build(consumer, "metal_impetus_restoration");

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.GUNPOWDER, 8)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.GLOWSTONE_DUST, 8)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.REDSTONE, 8)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.QUARTZ, 4)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), ItemRegistry.BLAZING_QUARTZ.get(), 4)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.PRISMARINE_SHARD, 8)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.AMETHYST_SHARD, 8)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        addImpetusRecipes(consumer, metalDuration, ItemRegistry.IRON_IMPETUS, ItemRegistry.IRON_NODE);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.GOLD_IMPETUS, ItemRegistry.GOLD_NODE);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.COPPER_IMPETUS, ItemRegistry.COPPER_NODE);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.LEAD_IMPETUS, ItemRegistry.LEAD_NODE, NUGGETS_LEAD);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.SILVER_IMPETUS, ItemRegistry.SILVER_NODE, NUGGETS_SILVER);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.ALUMINUM_IMPETUS, ItemRegistry.ALUMINUM_NODE, NUGGETS_ALUMINUM);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.NICKEL_IMPETUS, ItemRegistry.NICKEL_NODE, NUGGETS_NICKEL);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.URANIUM_IMPETUS, ItemRegistry.URANIUM_NODE, NUGGETS_URANIUM);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.OSMIUM_IMPETUS, ItemRegistry.OSMIUM_NODE, NUGGETS_OSMIUM);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.ZINC_IMPETUS, ItemRegistry.ZINC_NODE, NUGGETS_ZINC);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.TIN_IMPETUS, ItemRegistry.TIN_NODE, NUGGETS_TIN);
    }

    public void addImpetusRecipes(Consumer<FinishedRecipe> consumer, int duration, RegistryObject<ImpetusItem> impetus, RegistryObject<Item> node) {
        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.get()), node.get(), 3)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 4)
                .build(consumer, MalumMod.malumPath("node_focusing_" + node.get().getRegistryName().getPath().replace("_node", "")));
    }

    public void addImpetusRecipes(Consumer<FinishedRecipe> consumer, int duration, RegistryObject<ImpetusItem> impetus, RegistryObject<Item> node, TagKey<Item> nugget) {
        ConditionalRecipe.builder().addCondition(not(new TagEmptyCondition(nugget.location()))).addRecipe(
                        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.get()), node.get(), 3)
                                .addSpirit(EARTHEN_SPIRIT, 2)
                                .addSpirit(INFERNAL_SPIRIT, 1)
                                ::build
                )
                .generateAdvancement()
                .build(consumer, MalumMod.malumPath("node_focusing_" + nugget.location().getPath().replace("nuggets/", "")));
    }
}
