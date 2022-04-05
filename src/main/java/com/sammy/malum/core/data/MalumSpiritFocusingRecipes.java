package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.SpiritFocusingRecipeBuilder;
import com.sammy.malum.core.data.builder.SpiritRepairRecipeBuilder;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;

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
        int metalDuration = 1200;
        int shortDuration = 300;

        new SpiritRepairRecipeBuilder("wooden_", 1f, Ingredient.of(ItemTags.PLANKS), 4)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "wooden_items");

        new SpiritRepairRecipeBuilder("stone_", 1f, Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 2)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .build(consumer, "stone_items");

        new SpiritRepairRecipeBuilder("iron_", 0.5f, Ingredient.of(Tags.Items.INGOTS_IRON), 2)
                .addItem(ItemRegistry.CRUDE_SCYTHE.get())
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(SACRED_SPIRIT, 4)
                .build(consumer, "iron_items");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), 4)
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_HELMET.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get())
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(WICKED_SPIRIT, 2)
                .build(consumer, "special_soul_stained_steel_items");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), 2)
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_AXE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_PICKAXE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SHOVEL.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_HOE.get())
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(WICKED_SPIRIT, 2)
                .build(consumer, "soul_stained_steel_items");

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.GUNPOWDER), 8)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.GLOWSTONE_DUST), 8)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.REDSTONE), 8)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        metalNodeRecipe(consumer, metalDuration, ItemRegistry.IRON_IMPETUS, ItemRegistry.IRON_NODE);
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.GOLD_IMPETUS, ItemRegistry.GOLD_NODE);
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.COPPER_IMPETUS, ItemRegistry.COPPER_NODE);
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.LEAD_IMPETUS, ItemRegistry.LEAD_NODE, "forge:nuggets/lead");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.SILVER_IMPETUS, ItemRegistry.SILVER_NODE, "forge:nuggets/silver");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.ALUMINUM_IMPETUS, ItemRegistry.ALUMINUM_NODE, "forge:nuggets/aluminum");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.NICKEL_IMPETUS, ItemRegistry.NICKEL_NODE, "forge:nuggets/nickel");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.URANIUM_IMPETUS, ItemRegistry.URANIUM_NODE, "forge:nuggets/uranium");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.OSMIUM_IMPETUS, ItemRegistry.OSMIUM_NODE, "forge:nuggets/osmium");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.ZINC_IMPETUS, ItemRegistry.ZINC_NODE, "forge:nuggets/zinc");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.TIN_IMPETUS, ItemRegistry.TIN_NODE, "forge:nuggets/tin");
    }

    public void metalNodeRecipe(Consumer<FinishedRecipe> consumer, int duration, RegistryObject<Item> impetus, RegistryObject<Item> node) {
        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.get()), Ingredient.of(node.get()), 2)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);
    }

    public void metalNodeRecipe(Consumer<FinishedRecipe> consumer, int duration, RegistryObject<Item> impetus, RegistryObject<Item> node, String tag) {

        ConditionalRecipe.builder().addCondition(not(new TagEmptyCondition(tag))).addRecipe(
                        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.get()), Ingredient.of(node.get()), 2)
                                .addSpirit(EARTHEN_SPIRIT, 2)
                                .addSpirit(INFERNAL_SPIRIT, 1)
                                ::build
                )
                .generateAdvancement()
                .build(consumer, DataHelper.prefix("conditional_node" + tag.replace("forge:nuggets", "")));
    }
}