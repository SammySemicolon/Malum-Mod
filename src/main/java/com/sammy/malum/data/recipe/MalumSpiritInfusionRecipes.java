package com.sammy.malum.data.recipe;

import com.sammy.malum.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.crafting.*;
import net.minecraftforge.common.crafting.conditions.*;
import net.minecraftforge.registries.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;
import static team.lodestar.lodestone.setup.LodestoneItemTags.*;

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
                .addSpirit(ARCANE_SPIRIT.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.CLAY_BALL, 4, ItemRegistry.ALCHEMICAL_CALX.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 2)
                .addSpirit(EARTHEN_SPIRIT.get(), 2)
                .addSpirit(AQUEOUS_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 16, ItemRegistry.TAINTED_ROCK.get(), 16)
                .addSpirit(SACRED_SPIRIT.get(), 1)
                .addSpirit(ARCANE_SPIRIT.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 16, ItemRegistry.TWISTED_ROCK.get(), 16)
                .addSpirit(WICKED_SPIRIT.get(), 1)
                .addSpirit(ARCANE_SPIRIT.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.GLOWSTONE_DUST, 4, ItemRegistry.ETHER.get(), 2)
                .addSpirit(INFERNAL_SPIRIT.get(), 2)
                .addSpirit(ARCANE_SPIRIT.get(), 1)
                .addExtraItem(ItemRegistry.BLAZING_QUARTZ.get(), 1)
                .addExtraItem(Items.BLAZE_POWDER, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ETHER.get(), 1, ItemRegistry.IRIDESCENT_ETHER.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT.get(), 2)
                .addExtraItem(Items.PRISMARINE_CRYSTALS, 1)
                .addExtraItem(ItemRegistry.ARCANE_CHARCOAL.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.GOLD_INGOT, 1, ItemRegistry.HALLOWED_GOLD_INGOT.get(), 1)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_QUARTZ), 4)
                .addSpirit(SACRED_SPIRIT.get(), 2)
                .addSpirit(ARCANE_SPIRIT.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_PLANKS.get(), 2, ItemRegistry.RUNEWOOD_OBELISK.get(), 1)
                .addExtraItem(ItemRegistry.HALLOWED_GOLD_INGOT.get(), 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(AERIAL_SPIRIT.get(), 8)
                .addSpirit(SACRED_SPIRIT.get(), 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_PLANKS.get(), 2, ItemRegistry.BRILLIANT_OBELISK.get(), 1)
                .addExtraItem(ItemRegistry.CLUSTER_OF_BRILLIANCE.get(), 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(AERIAL_SPIRIT.get(), 8)
                .addSpirit(AQUEOUS_SPIRIT.get(), 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_LOG.get(), 4, ItemRegistry.RUNEWOOD_TOTEM_BASE.get(), 4)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 6)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addSpirit(AERIAL_SPIRIT.get(), 2)
                .addSpirit(AQUEOUS_SPIRIT.get(), 2)
                .addSpirit(EARTHEN_SPIRIT.get(), 2)
                .addSpirit(INFERNAL_SPIRIT.get(), 2)
                .addSpirit(ELDRITCH_SPIRIT.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOULWOOD_LOG.get(), 4, ItemRegistry.SOULWOOD_TOTEM_BASE.get(), 4)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 6)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addSpirit(AERIAL_SPIRIT.get(), 2)
                .addSpirit(AQUEOUS_SPIRIT.get(), 2)
                .addSpirit(EARTHEN_SPIRIT.get(), 2)
                .addSpirit(INFERNAL_SPIRIT.get(), 2)
                .addSpirit(ELDRITCH_SPIRIT.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_INGOT, 1, ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addSpirit(WICKED_SPIRIT.get(), 3)
                .addSpirit(EARTHEN_SPIRIT.get(), 1)
                .addSpirit(ARCANE_SPIRIT.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.CRUDE_SCYTHE.get(), 1, ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addSpirit(EARTHEN_SPIRIT.get(), 8)
                .addSpirit(WICKED_SPIRIT.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_HELMET, 1, ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(EARTHEN_SPIRIT.get(), 8)
                .addSpirit(WICKED_SPIRIT.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_CHESTPLATE, 1, ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(EARTHEN_SPIRIT.get(), 8)
                .addSpirit(WICKED_SPIRIT.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_LEGGINGS, 1, ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(EARTHEN_SPIRIT.get(), 8)
                .addSpirit(WICKED_SPIRIT.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_BOOTS, 1, ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(EARTHEN_SPIRIT.get(), 8)
                .addSpirit(WICKED_SPIRIT.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.WOOL), 2, ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(Ingredient.of(Tags.Items.STRING), 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(WICKED_SPIRIT.get(), 2)
                .addSpirit(EARTHEN_SPIRIT.get(), 1)
                .addSpirit(AERIAL_SPIRIT.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_HELMET, 1, ItemRegistry.SOUL_HUNTER_CLOAK.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addExtraItem(Ingredient.of(Tags.Items.LEATHER), 2)
                .addSpirit(AERIAL_SPIRIT.get(), 8)
                .addSpirit(EARTHEN_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_CHESTPLATE, 1, ItemRegistry.SOUL_HUNTER_ROBE.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addExtraItem(Ingredient.of(Tags.Items.LEATHER), 2)
                .addSpirit(AERIAL_SPIRIT.get(), 8)
                .addSpirit(EARTHEN_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_LEGGINGS, 1, ItemRegistry.SOUL_HUNTER_LEGGINGS.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addExtraItem(Ingredient.of(Tags.Items.LEATHER), 2)
                .addSpirit(AERIAL_SPIRIT.get(), 8)
                .addSpirit(EARTHEN_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_BOOTS, 1, ItemRegistry.SOUL_HUNTER_BOOTS.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addExtraItem(Ingredient.of(Tags.Items.LEATHER), 2)
                .addExtraItem(Ingredient.of(Tags.Items.FEATHERS), 2)
                .addSpirit(AERIAL_SPIRIT.get(), 8)
                .addSpirit(EARTHEN_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_RING.get(), 1, ItemRegistry.RING_OF_ESOTERIC_SPOILS.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 8)
                .addSpirit(WICKED_SPIRIT.get(), 8)
                .addSpirit(ARCANE_SPIRIT.get(), 8)
                .addSpirit(ELDRITCH_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_RING.get(), 1, ItemRegistry.RING_OF_ARCANE_PROWESS.get(), 1)
                .addExtraItem(ItemRegistry.CLUSTER_OF_BRILLIANCE.get(), 4)
                .addExtraItem(ItemRegistry.ALCHEMICAL_CALX.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 24)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_RING.get(), 1, ItemRegistry.RING_OF_CURATIVE_TALENT.get(), 1)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(Items.GHAST_TEAR, 1)
                .addExtraItem(ItemRegistry.ALCHEMICAL_CALX.get(), 4)
                .addSpirit(SACRED_SPIRIT.get(), 16)
                .addSpirit(ARCANE_SPIRIT.get(), 16)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_RING.get(), 1, ItemRegistry.RING_OF_ALCHEMICAL_MASTERY.get(), 1)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(Items.NETHER_WART, 4)
                .addExtraItem(Items.FERMENTED_SPIDER_EYE, 1)
                .addExtraItem(ItemRegistry.ALCHEMICAL_CALX.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 16)
                .addSpirit(AQUEOUS_SPIRIT.get(), 16)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_NECKLACE.get(), 1, ItemRegistry.NECKLACE_OF_TIDAL_AFFINITY.get(), 1)
                .addExtraItem(ItemRegistry.GOLD_NODE.get(), 6)
                .addExtraItem(Items.HEART_OF_THE_SEA, 1)
                .addExtraItem(Items.NAUTILUS_SHELL, 2)
                .addSpirit(AQUEOUS_SPIRIT.get(), 16)
                .addSpirit(SACRED_SPIRIT.get(), 16)
                .addSpirit(ELDRITCH_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_NECKLACE.get(), 1, ItemRegistry.NECKLACE_OF_BLISSFUL_HARMONY.get(), 1)
                .addExtraItem(Ingredient.of(Tags.Items.INGOTS_IRON), 6)
                .addExtraItem(Items.PHANTOM_MEMBRANE, 4)
                .addExtraItem(ItemRegistry.ASTRAL_WEAVE.get(), 2)
                .addExtraItem(Items.DIAMOND, 2)
                .addSpirit(AERIAL_SPIRIT.get(), 16)
                .addSpirit(AQUEOUS_SPIRIT.get(), 16)
                .addSpirit(SACRED_SPIRIT.get(), 16)
                .addSpirit(ELDRITCH_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_RING.get(), 1, ItemRegistry.RING_OF_THE_DEMOLITIONIST.get(), 1)
                .addExtraItem(ItemRegistry.COPPER_NODE.get(), 6)
                .addExtraItem(Items.GUNPOWDER, 4)
                .addExtraItem(ItemRegistry.CURSED_GRIT.get(), 4)
                .addSpirit(INFERNAL_SPIRIT.get(), 32)
                .addSpirit(ELDRITCH_SPIRIT.get(), 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_NECKLACE.get(), 1, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE.get(), 1)
                .addExtraItem(Ingredient.of(Tags.Items.STORAGE_BLOCKS_IRON), 1)
                .addExtraItem(Ingredient.of(Tags.Items.INGOTS_IRON), 2)
                .addSpirit(WICKED_SPIRIT.get(), 16)
                .addSpirit(EARTHEN_SPIRIT.get(), 16)
                .addSpirit(ELDRITCH_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_NECKLACE.get(), 1, ItemRegistry.NECKLACE_OF_THE_MYSTIC_MIRROR.get(), 1)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 8)
                .addExtraItem(ItemRegistry.SPECTRAL_OPTIC.get(), 1)
                .addExtraItem(Items.ENDER_EYE, 1)
                .addSpirit(SACRED_SPIRIT.get(), 24)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_RING.get(), 1, ItemRegistry.RING_OF_DESPERATE_VORACITY.get(), 1)
                .addExtraItem(Items.BONE, 2)
                .addExtraItem(ItemRegistry.GRIM_TALC.get(), 2)
                .addExtraItem(Items.ROTTEN_FLESH, 8)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addSpirit(WICKED_SPIRIT.get(), 16)
                .addSpirit(SACRED_SPIRIT.get(), 8)
                .addSpirit(ARCANE_SPIRIT.get(), 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_BELT.get(), 1, ItemRegistry.BELT_OF_THE_STARVED.get(), 1)
                .addExtraItem(Items.BONE, 2)
                .addExtraItem(ItemRegistry.GRIM_TALC.get(), 4)
                .addExtraItem(ItemRegistry.ROTTING_ESSENCE.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addSpirit(WICKED_SPIRIT.get(), 32)
                .addSpirit(AQUEOUS_SPIRIT.get(), 8)
                .addSpirit(ARCANE_SPIRIT.get(), 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_RING.get(), 1, ItemRegistry.RING_OF_THE_HOARDER.get(), 1)
                .addExtraItem(Items.ENDER_PEARL, 2)
                .addExtraItem(Ingredient.of(Tags.Items.INGOTS_IRON), 4)
                .addExtraItem(Items.GUNPOWDER, 8)
                .addSpirit(EARTHEN_SPIRIT.get(), 16)
                .addSpirit(SACRED_SPIRIT.get(), 8)
                .addSpirit(ARCANE_SPIRIT.get(), 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_BELT.get(), 1, ItemRegistry.BELT_OF_THE_PROSPECTOR.get(), 1)
                .addExtraItem(ItemRegistry.CTHONIC_GOLD.get(), 1)
                .addExtraItem(Items.RAW_GOLD, 4)
                .addExtraItem(Items.RAW_IRON, 4)
                .addExtraItem(Items.RAW_COPPER, 4)
                .addSpirit(EARTHEN_SPIRIT.get(), 32)
                .addSpirit(INFERNAL_SPIRIT.get(), 32)
                .addSpirit(ARCANE_SPIRIT.get(), 16)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.HAY_BLOCK, 1, ItemRegistry.POPPET.get(), 2)
                .addSpirit(WICKED_SPIRIT.get(), 4)
                .addSpirit(EARTHEN_SPIRIT.get(), 4)
                .addExtraItem(Items.WHEAT, 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.FURNACE, 1, ItemRegistry.SPIRIT_CRUCIBLE.get(), 1)
                .addSpirit(INFERNAL_SPIRIT.get(), 12)
                .addSpirit(ARCANE_SPIRIT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 8)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 8, ItemRegistry.ALCHEMICAL_IMPETUS.get(), 1)
                .addSpirit(ARCANE_SPIRIT.get(), 4)
                .addSpirit(EARTHEN_SPIRIT.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 3)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(), 2, ItemRegistry.SPIRIT_CATALYZER.get(), 2)
                .addSpirit(INFERNAL_SPIRIT.get(), 16)
                .addSpirit(AERIAL_SPIRIT.get(), 16)
                .addSpirit(ELDRITCH_SPIRIT.get(), 2)
                .addExtraItem(ItemRegistry.ETHER.get(), 1)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 8)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_ROCK_ITEM_STAND.get(), 1, ItemRegistry.TWISTED_TABLET.get(), 1)
                .addSpirit(AERIAL_SPIRIT.get(), 8)
                .addSpirit(EARTHEN_SPIRIT.get(), 8)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 4)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.COALS), 4, ItemRegistry.ARCANE_CHARCOAL.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 1)
                .addSpirit(INFERNAL_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.GLOWSTONE_DUST, 4, ItemRegistry.CURSED_GRIT.get(), 4)
                .addSpirit(INFERNAL_SPIRIT.get(), 4)
                .addSpirit(WICKED_SPIRIT.get(), 2)
                .addSpirit(ARCANE_SPIRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.TNT, 1, ItemRegistry.ETHERIC_NITRATE.get(), 4)
                .addSpirit(INFERNAL_SPIRIT.get(), 8)
                .addSpirit(ARCANE_SPIRIT.get(), 8)
                .addExtraItem(ItemRegistry.ETHER.get(), 1)
                .addExtraItem(Ingredient.of(Tags.Items.GUNPOWDER), 4)
                .addExtraItem(ItemRegistry.CURSED_GRIT.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ETHERIC_NITRATE.get(), 8, ItemRegistry.VIVID_NITRATE.get(), 8)
                .addSpirit(AERIAL_SPIRIT.get(), 8)
                .addSpirit(AQUEOUS_SPIRIT.get(), 8)
                .addSpirit(INFERNAL_SPIRIT.get(), 8)
                .addSpirit(EARTHEN_SPIRIT.get(), 8)
                .addSpirit(ELDRITCH_SPIRIT.get(), 2)
                .addExtraItem(ItemRegistry.CURSED_GRIT.get(), 4)
                .addExtraItem(Ingredient.of(Tags.Items.GUNPOWDER), 4)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_PRISMARINE), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RAW_SOULSTONE.get(), 16, ItemRegistry.CORRUPTED_RESONANCE.get(), 1)
                .addSpirit(ARCANE_SPIRIT.get(), 32)
                .addSpirit(WICKED_SPIRIT.get(), 32)
                .addSpirit(ELDRITCH_SPIRIT.get(), 4)
                .addExtraItem(ItemRegistry.CLUSTER_OF_BRILLIANCE.get(), 2)
                .addExtraItem(Items.LAPIS_LAZULI, 4)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_DIAMOND), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get(), 1, ItemRegistry.TYRVING.get(), 1)
                .addSpirit(WICKED_SPIRIT.get(), 24)
                .addSpirit(ELDRITCH_SPIRIT.get(), 6)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 16)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 8)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 4)
                .addExtraItem(Ingredient.of(Tags.Items.OBSIDIAN), 4)
                .addExtraItem(ItemRegistry.CORRUPTED_RESONANCE.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_BELT.get(), 1, ItemRegistry.BELT_OF_THE_MAGEBANE.get(), 1)
                .addSpirit(WICKED_SPIRIT.get(), 32)
                .addSpirit(ARCANE_SPIRIT.get(), 16)
                .addSpirit(ELDRITCH_SPIRIT.get(), 4)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 16)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 8)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 6)
                .addExtraItem(ItemRegistry.CORRUPTED_RESONANCE.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.of(Tags.Items.INGOTS_IRON), 4, ItemRegistry.ESOTERIC_SPOOL.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .build(consumer);


        new SpiritInfusionRecipeBuilder(ItemRegistry.ANOMALOUS_DESIGN.get(), 1, ItemRegistry.COMPLETE_DESIGN.get(), 1)
                .addSpirit(SACRED_SPIRIT.get(), 4)
                .addSpirit(WICKED_SPIRIT.get(), 4)
                .addSpirit(ARCANE_SPIRIT.get(), 4)
                .addSpirit(ELDRITCH_SPIRIT.get(), 4)
                .addSpirit(AERIAL_SPIRIT.get(), 4)
                .addSpirit(AQUEOUS_SPIRIT.get(), 4)
                .addSpirit(EARTHEN_SPIRIT.get(), 4)
                .addSpirit(INFERNAL_SPIRIT.get(), 4)
                .build(consumer);

        metalImpetusRecipe(consumer, ItemRegistry.IRON_IMPETUS, Items.IRON_INGOT);
        metalImpetusRecipe(consumer, ItemRegistry.COPPER_IMPETUS, Items.COPPER_INGOT);
        metalImpetusRecipe(consumer, ItemRegistry.GOLD_IMPETUS, Items.GOLD_INGOT);
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
                                .addSpirit(EARTHEN_SPIRIT.get(), 8)
                                .addSpirit(INFERNAL_SPIRIT.get(), 8)
                                .addExtraItem(Ingredient.of(Tags.Items.GUNPOWDER), 4)
                                .addExtraItem(Ingredient.of(ItemRegistry.CTHONIC_GOLD.get()), 1)
                                .addExtraItem(Ingredient.of(ingot), 6)
                                ::build)
                .generateAdvancement()
                .build(consumer, MalumMod.malumPath("impetus_creation_" + ingot.location().getPath().replace("ingots/", "")));
    }

    public void metalImpetusRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<ImpetusItem> output, Item ingot) {
        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_IMPETUS.get(), 1, output.get(), 1)
                .addSpirit(EARTHEN_SPIRIT.get(), 8)
                .addSpirit(INFERNAL_SPIRIT.get(), 8)
                .addExtraItem(Ingredient.of(Tags.Items.GUNPOWDER), 4)
                .addExtraItem(Ingredient.of(ItemRegistry.CTHONIC_GOLD.get()), 1)
                .addExtraItem(Ingredient.of(ingot), 6)
                .build(consumer);
    }
}