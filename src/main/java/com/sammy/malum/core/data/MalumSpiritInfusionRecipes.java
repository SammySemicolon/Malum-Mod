package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.SpiritInfusionRecipeBuilder;
import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class MalumSpiritInfusionRecipes extends RecipeProvider
{
    public MalumSpiritInfusionRecipes(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    public String getName()
    {
        return "Malum Spirit Infusion Recipe Provider";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        new SpiritInfusionRecipeBuilder(Items.GUNPOWDER, 1, ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.COBBLESTONE, 16, ItemRegistry.TAINTED_ROCK.get(), 16)
                .addSpirit(SACRED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.COBBLESTONE, 16, ItemRegistry.TWISTED_ROCK.get(), 16)
                .addSpirit(WICKED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.GLOWSTONE_DUST, 4, ItemRegistry.ETHER.get(), 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addExtraItem(ItemRegistry.BLAZING_QUARTZ.get(), 2)
                .addExtraItem(Items.BLAZE_POWDER, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ETHER.get(), 1, ItemRegistry.IRIDESCENT_ETHER.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT_COLOR, 2)
                .addExtraItem(Items.PRISMARINE_CRYSTALS, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.GOLD_INGOT, 1, ItemRegistry.HALLOWED_GOLD_INGOT.get(), 1)
                .addExtraItem(Items.QUARTZ, 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .addSpirit(SACRED_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_LOG.get(), 1, ItemRegistry.RUNEWOOD_TOTEM_BASE.get(), 1)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 1)
                .addExtraItem(ItemRegistry.HALLOWED_SPIRIT_RESONATOR.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(AQUEOUS_SPIRIT_COLOR, 2)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOULWOOD_LOG.get(), 1, ItemRegistry.SOULWOOD_TOTEM_BASE.get(), 1)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 1)
                .addExtraItem(ItemRegistry.STAINED_SPIRIT_RESONATOR.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(AQUEOUS_SPIRIT_COLOR, 2)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_INGOT, 1, ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 1)
                .addExtraItem(Items.SOUL_SAND, 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .addSpirit(WICKED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.CRUDE_SCYTHE.get(), 1, ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_HELMET, 1, ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(Ingredient.fromTag(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(EARTHEN_SPIRIT, 5)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_CHESTPLATE, 1, ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(Ingredient.fromTag(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(EARTHEN_SPIRIT, 5)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_LEGGINGS, 1, ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(Ingredient.fromTag(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(EARTHEN_SPIRIT, 5)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_BOOTS, 1, ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .addExtraItem(Ingredient.fromTag(Tags.Items.GEMS_DIAMOND), 1)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(EARTHEN_SPIRIT, 5)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.fromTag(ItemTags.WOOL), 1, ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(Items.STRING, 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .addSpirit(WICKED_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_HELMET, 1, ItemRegistry.SOUL_HUNTER_CLOAK.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .addExtraItem(Ingredient.fromTag(Tags.Items.LEATHER), 1)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(AERIAL_SPIRIT, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_CHESTPLATE, 1, ItemRegistry.SOUL_HUNTER_ROBE.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .addExtraItem(Ingredient.fromTag(Tags.Items.LEATHER), 1)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(AERIAL_SPIRIT, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_LEGGINGS, 1, ItemRegistry.SOUL_HUNTER_LEGGINGS.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .addExtraItem(Ingredient.fromTag(Tags.Items.LEATHER), 1)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(AERIAL_SPIRIT, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_BOOTS, 1, ItemRegistry.SOUL_HUNTER_BOOTS.get(), 1)
                .addExtraItem(ItemRegistry.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .addExtraItem(Ingredient.fromTag(Tags.Items.LEATHER), 1)
                .addExtraItem(Ingredient.fromTag(Tags.Items.FEATHERS), 1)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(AERIAL_SPIRIT, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_RING.get(), 1, ItemRegistry.RING_OF_ARCANE_REACH.get(), 1)
                .addExtraItem(ItemRegistry.HALLOWED_SPIRIT_RESONATOR.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addSpirit(AERIAL_SPIRIT, 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_RING.get(), 1, ItemRegistry.RING_OF_ARCANE_SPOIL.get(), 1)
                .addExtraItem(ItemRegistry.STAINED_SPIRIT_RESONATOR.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addSpirit(WICKED_SPIRIT, 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_RING.get(), 1, ItemRegistry.RING_OF_PROWESS.get(), 1)
                .addExtraItem(ItemRegistry.BRILLIANCE_CLUSTER.get(), 2)
                .addExtraItem(Ingredient.fromTag(Tags.Items.GEMS_LAPIS), 4)
                .addExtraItem(Ingredient.fromTag(Tags.Items.GEMS_EMERALD), 2)
                .addSpirit(ARCANE_SPIRIT, 32)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.GILDED_RING.get(), 1, ItemRegistry.RING_OF_CURATIVE_TALENT.get(), 1)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(Items.GHAST_TEAR, 1)
                .addSpirit(SACRED_SPIRIT, 32)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_RING.get(), 1, ItemRegistry.RING_OF_WICKED_INTENT.get(), 1)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addExtraItem(Items.WITHER_SKELETON_SKULL, 1)
                .addSpirit(WICKED_SPIRIT, 32)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.PHANTOM_MEMBRANE, 2, ItemRegistry.ECTOPLASM.get(), 2)
                .addExtraItem(Items.GHAST_TEAR, 1)
                .addExtraItem(Ingredient.fromTag(Tags.Items.GUNPOWDER), 2)
                .addSpirit(INFERNAL_SPIRIT, 4)
                .addSpirit(AQUEOUS_SPIRIT_COLOR, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.DIAMOND, 2, ItemRegistry.RADIANT_SOULSTONE.get(), 1)
                .addExtraItem(ItemRegistry.ECTOPLASM.get(), 1)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 4)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_NECKLACE.get(), 1, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE.get(), 1)
                .addExtraItem(ItemRegistry.STAINED_SPIRIT_RESONATOR.get(), 1)
                .addExtraItem(Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_IRON), 1)
                .addExtraItem(Ingredient.fromTag(Tags.Items.INGOTS_IRON), 2)
                .addSpirit(WICKED_SPIRIT, 32)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ORNATE_NECKLACE.get(), 1, ItemRegistry.NECKLACE_OF_THE_MYSTIC_MIRROR.get(), 1)
                .addExtraItem(ItemRegistry.HALLOWED_SPIRIT_RESONATOR.get(), 1)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 8)
                .addExtraItem(ItemRegistry.SPECTRAL_LENS.get(), 1)
                .addSpirit(SACRED_SPIRIT, 32)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get(), 1, ItemRegistry.TYRVING.get(), 1)
                .addExtraItem(ItemRegistry.RADIANT_SOULSTONE.get(), 1)
                .addExtraItem(Ingredient.fromTag(Tags.Items.OBSIDIAN), 4)
                .addExtraItem(Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_COAL), 1)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addSpirit(WICKED_SPIRIT, 4)
                .addSpirit(ELDRITCH_SPIRIT, 4)
                .build(consumer);
    }
}