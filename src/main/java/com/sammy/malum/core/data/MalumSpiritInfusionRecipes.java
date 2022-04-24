package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.core.data.builder.SpiritInfusionRecipeBuilder;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.item.ItemTagRegistry;
import com.sammy.ortus.setup.OrtusItemTags;
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

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;
import static com.sammy.ortus.setup.OrtusItemTags.*;

public class MalumSpiritInfusionRecipes extends RecipeProvider implements IConditionBuilder {
    public MalumSpiritInfusionRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Malum Spirit Infusion Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        new SpiritInfusionRecipeBuilder(Items.GUNPOWDER, 1, ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 16, ItemRegistry.TAINTED_ROCK.get(), 16)
                .addSpirit(SACRED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 16, ItemRegistry.TWISTED_ROCK.get(), 16)
                .addSpirit(WICKED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.GLOWSTONE_DUST, 4, ItemRegistry.ETHER.get(), 2)
                .addSpirit(INFERNAL_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addExtraItem(ItemRegistry.BLAZING_QUARTZ_FRAGMENT.get(), 4)
                .addExtraItem(Items.BLAZE_POWDER, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ETHER.get(), 1, ItemRegistry.IRIDESCENT_ETHER.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT, 4)
                .addExtraItem(Items.PRISMARINE_CRYSTALS, 1)
                .addExtraItem(ItemRegistry.ARCANE_CHARCOAL_FRAGMENT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.GOLD_INGOT, 1, ItemRegistry.HALLOWED_GOLD_INGOT.get(), 1)
                .addExtraItem(Items.QUARTZ, 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .addSpirit(SACRED_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_PLANKS.get(), 2, ItemRegistry.RUNEWOOD_OBELISK.get(), 1)
                .addExtraItem(ItemRegistry.HALLOWED_SPIRIT_RESONATOR.get(), 1)
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
                .addSpirit(AERIAL_SPIRIT, 4)
                .addSpirit(AQUEOUS_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(INFERNAL_SPIRIT, 4)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOULWOOD_LOG.get(), 4, ItemRegistry.SOULWOOD_TOTEM_BASE.get(), 4)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 6)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addSpirit(AERIAL_SPIRIT, 4)
                .addSpirit(AQUEOUS_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(INFERNAL_SPIRIT, 4)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_INGOT, 1, ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 1)
                .addExtraItem(Items.SOUL_SAND, 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.CRUDE_SCYTHE.get(), 1, ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(WICKED_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_HELMET, 1, ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(WICKED_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_CHESTPLATE, 1, ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(WICKED_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_LEGGINGS, 1, ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(WICKED_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_BOOTS, 1, ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(WICKED_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.WOOL), 2, ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(Ingredient.of(Tags.Items.STRING), 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(WICKED_SPIRIT, 2)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .addSpirit(AERIAL_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_HELMET, 1, ItemRegistry.SOUL_HUNTER_CLOAK.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .addExtraItem(Ingredient.of(Tags.Items.LEATHER), 1)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_CHESTPLATE, 1, ItemRegistry.SOUL_HUNTER_ROBE.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(Ingredient.of(Tags.Items.LEATHER), 1)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_LEGGINGS, 1, ItemRegistry.SOUL_HUNTER_LEGGINGS.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(Ingredient.of(Tags.Items.LEATHER), 1)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_BOOTS, 1, ItemRegistry.SOUL_HUNTER_BOOTS.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .addExtraItem(Ingredient.of(Tags.Items.LEATHER), 1)
                .addExtraItem(Ingredient.of(Tags.Items.FEATHERS), 2)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_RING.get(), 1, ItemRegistry.RING_OF_ARCANE_REACH.get(), 1)
                .addExtraItem(ItemRegistry.HALLOWED_SPIRIT_RESONATOR.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 6)
                .addSpirit(AERIAL_SPIRIT, 12)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_RING.get(), 1, ItemRegistry.RING_OF_ARCANE_SPOIL.get(), 1)
                .addExtraItem(ItemRegistry.STAINED_SPIRIT_RESONATOR.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 6)
                .addSpirit(WICKED_SPIRIT, 12)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_RING.get(), 1, ItemRegistry.RING_OF_PROWESS.get(), 1)
                .addExtraItem(ItemRegistry.CLUSTER_OF_BRILLIANCE.get(), 2)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_LAPIS), 4)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_EMERALD), 2)
                .addSpirit(ARCANE_SPIRIT, 24)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_RING.get(), 1, ItemRegistry.RING_OF_CURATIVE_TALENT.get(), 1)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(Items.GHAST_TEAR, 1)
                .addSpirit(SACRED_SPIRIT, 24)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_RING.get(), 1, ItemRegistry.RING_OF_WICKED_INTENT.get(), 1)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(Items.WITHER_SKELETON_SKULL, 1)
                .addSpirit(WICKED_SPIRIT, 24)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_NECKLACE.get(), 1, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE.get(), 1)
                .addExtraItem(ItemRegistry.STAINED_SPIRIT_RESONATOR.get(), 1)
                .addExtraItem(Ingredient.of(Tags.Items.STORAGE_BLOCKS_IRON), 1)
                .addExtraItem(Ingredient.of(Tags.Items.INGOTS_IRON), 2)
                .addSpirit(WICKED_SPIRIT, 24)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_NECKLACE.get(), 1, ItemRegistry.NECKLACE_OF_THE_MYSTIC_MIRROR.get(), 1)
                .addExtraItem(ItemRegistry.HALLOWED_SPIRIT_RESONATOR.get(), 1)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 8)
                .addExtraItem(ItemRegistry.SPECTRAL_LENS.get(), 1)
                .addSpirit(SACRED_SPIRIT, 24)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_BELT.get(), 1, ItemRegistry.WARDED_BELT.get(), 1)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 16)
                .addSpirit(EARTHEN_SPIRIT, 24)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.GLASS_PANE, 4, ItemRegistry.SPECTRAL_LENS.get(), 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addExtraItem(Items.ENDER_PEARL, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.HAY_BLOCK, 1, ItemRegistry.POPPET.get(), 2)
                .addSpirit(WICKED_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addExtraItem(Items.WHEAT, 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get(), 1, ItemRegistry.TYRVING.get(), 1)
                .addSpirit(WICKED_SPIRIT, 24)
                .addSpirit(ELDRITCH_SPIRIT, 6)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 16)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 8)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 4)
                .addExtraItem(Ingredient.of(Tags.Items.OBSIDIAN), 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.WARDED_BELT.get(), 1, ItemRegistry.MAGEBANE_BELT.get(), 1)
                .addSpirit(WICKED_SPIRIT, 16)
                .addSpirit(ELDRITCH_SPIRIT, 4)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 16)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 8)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 6)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.FURNACE, 1, ItemRegistry.SPIRIT_CRUCIBLE.get(), 1)
                .addSpirit(INFERNAL_SPIRIT, 12)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 8)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.CLAY_BALL, 8, ItemRegistry.ALCHEMICAL_IMPETUS.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 3)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_IMPETUS.get(), 1, ItemRegistry.CEASELESS_IMPETUS.get(), 1)
                .addSpirit(SACRED_SPIRIT, 64)
                .addSpirit(ELDRITCH_SPIRIT, 4)
                .addExtraItem(Items.TOTEM_OF_UNDYING, 1)
                .addExtraItem(Items.GHAST_TEAR, 1)
                .addExtraItem(ItemRegistry.HALLOWED_SPIRIT_RESONATOR.get(), 2)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_EMERALD), 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(), 1, ItemRegistry.SPIRIT_CATALYZER.get(), 1)
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .addExtraItem(ItemRegistry.ETHER.get(), 2)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 8)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_ROCK_ITEM_STAND.get(), 1, ItemRegistry.TWISTED_TABLET.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 4)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 4)
                .build(consumer);

        metalImpetusRecipe(consumer, ItemRegistry.IRON_IMPETUS, Tags.Items.INGOTS_IRON);
        metalImpetusRecipe(consumer, ItemRegistry.COPPER_IMPETUS, Tags.Items.INGOTS_COPPER);
        metalImpetusRecipe(consumer, ItemRegistry.GOLD_IMPETUS, Tags.Items.INGOTS_GOLD);
        metalImpetusRecipe(consumer, ItemRegistry.LEAD_IMPETUS, INGOTS_LEAD);
        metalImpetusRecipe(consumer, ItemRegistry.SILVER_IMPETUS, INGOTS_SILVER);
        metalImpetusRecipe(consumer, ItemRegistry.ALUMINUM_IMPETUS, INGOTS_ALUMINUM);
        metalImpetusRecipe(consumer, ItemRegistry.NICKEL_IMPETUS, INGOTS_NICKEL);
        metalImpetusRecipe(consumer, ItemRegistry.URANIUM_IMPETUS, INGOTS_URANIUM);
        metalImpetusRecipe(consumer, ItemRegistry.OSMIUM_IMPETUS, INGOTS_OSMIUM);
        metalImpetusRecipe(consumer, ItemRegistry.ZINC_IMPETUS, INGOTS_ZINC);
        metalImpetusRecipe(consumer, ItemRegistry.TIN_IMPETUS, INGOTS_TIN);
    }

    public void metalImpetusRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<ImpetusItem> output, TagKey<Item> ingot) {

        ConditionalRecipe.builder().addCondition(not(new TagEmptyCondition(ingot.location().toString()))).addRecipe(
                        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_IMPETUS.get(), 1, output.get(), 1)
                                .addSpirit(EARTHEN_SPIRIT, 8)
                                .addSpirit(INFERNAL_SPIRIT, 8)
                                .addExtraItem(Ingredient.of(Tags.Items.GUNPOWDER), 4)
                                .addExtraItem(Ingredient.of(ingot), 6)
                                ::build)
                .generateAdvancement()
                .build(consumer, MalumMod.prefix("impetus_creation_" + ingot.location().getPath().replace("ingots/", "")));
    }
}