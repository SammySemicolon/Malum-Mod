package com.sammy.malum.datagen.recipe;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.data.component.*;
import com.sammy.malum.registry.common.MalumContent;

import net.minecraft.advancements.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.datagen.recipe.RecipeDatagenCommons.has;
import static com.sammy.malum.datagen.recipe.RecipeDatagenCommons.smeltAndBlast;
import static com.sammy.malum.registry.common.MalumContent.Blight.*;
import static com.sammy.malum.registry.common.MalumContent.BuildingBlocks.*;
import static com.sammy.malum.registry.common.MalumContent.CompactBlocks.*;
import static com.sammy.malum.registry.common.MalumContent.Materials.*;
import static com.sammy.malum.registry.common.MalumContent.BuildingBlocks.THE_DEVICE;
import static com.sammy.malum.registry.common.MalumContent.Vanity.*;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.*;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.*;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.smelting;
import static net.minecraft.world.item.Items.*;

public class MalumVanillaRecipes implements IConditionBuilder {

    protected static void buildRecipes(RecipeOutput output) {
        var hasBlight = has(MalumContent.Blight.BLIGHTED_GUNK);
        var hasHallowedGold = has(HALLOWED_GOLD_INGOT);
        var hasSoulStainedSteel = has(SOUL_STAINED_STEEL_INGOT);

        Pair<String, Criterion<?>> hasBrilliance = Pair.of("has_brilliance", has(RAW_BRILLIANCE));
        Pair<String, Criterion<?>> hasSoulstone = Pair.of("has_soulstone", has(RAW_SOULSTONE));

        Pair<String, Criterion<?>> hasBlazingQuartz = Pair.of("has_blazing_quartz", has(BLAZING_QUARTZ));


        shaped(RecipeCategory.MISC, STONE_BOOKSHELF)
                .define('#', STONE_BRICKS)
                .define('X', BOOK)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .unlockedBy("has_book", has(BOOK)).save(output);


        //KEY ITEMS
        shapeless(RecipeCategory.MISC, MalumContent.ENCYCLOPEDIA_ARCANA)
                .requires(BOOK)
                .requires(REFINED_SOULSTONE)
                .unlockedBy("has_soulstone", has(RAW_SOULSTONE))
                .save(output);
        shaped(RecipeCategory.MISC, MalumContent.Gear.CRUDE_SCYTHE)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('Y', REFINED_SOULSTONE)
                .define('X', Tags.Items.INGOTS_IRON)
                .pattern("XXY")
                .pattern(" #X")
                .pattern("#  ")
                .unlockedBy("has_soulstone", has(RAW_SOULSTONE))
                .save(output);
        shaped(RecipeCategory.MISC, MalumContent.Sorcery.SPIRIT_ALTAR)
                .define('Z', Tags.Items.INGOTS_GOLD)
                .define('Y', REFINED_SOULSTONE)
                .define('X', RUNEWOOD_SET.planks.block)
                .pattern(" Y ")
                .pattern("ZXZ")
                .pattern("XXX")
                .unlockedBy("has_soulstone", has(RAW_SOULSTONE))
                .save(output);
        shaped(RecipeCategory.MISC, MalumContent.Sorcery.WEAVERS_WORKBENCH)
                .define('Z', HALLOWED_GOLD_INGOT)
                .define('Y', HEX_ASH)
                .define('X', RUNEWOOD_SET.planks.block)
                .pattern("XYX")
                .pattern("XZX")
                .unlockedBy("has_hex_ash", has(HEX_ASH))
                .save(output);
        shaped(RecipeCategory.MISC, MalumContent.Sorcery.SOUL_BRAZIER)
                .define('Z', CTHONIC_GOLD)
                .define('Y', HALLOWED_GOLD_INLAY)
                .define('X', RUNEWOOD_SET.planks.block)
                .define('W', TAINTED_ROCK_SET.rock.block)
                .pattern("YZY")
                .pattern("XXX")
                .pattern("WXW")
                .unlockedBy("has_hallowed_gold", hasHallowedGold)
                .save(output);
        shaped(RecipeCategory.MISC, MalumContent.Sorcery.SPIRIT_JAR)
                .define('X', HALLOWED_GOLD_INGOT)
                .define('Y', Tags.Items.GLASS_BLOCKS)
                .pattern("X")
                .pattern("Y")
                .unlockedBy("has_hallowed_gold", hasHallowedGold)
                .save(output);
        shaped(RecipeCategory.MISC, MalumContent.Gear.SOULWOVEN_POUCH)
                .define('X', Tags.Items.STRINGS)
                .define('Y', SOULWOVEN_SILK)
                .pattern("X")
                .pattern("Y")
                .unlockedBy("has_soulwoven_silk", has(SOULWOVEN_SILK))
                .save(output);
        shaped(RecipeCategory.MISC, MalumContent.Totemancy.TOTEMIC_STAFF)
                .define('X', Tags.Items.RODS_WOODEN)
                .define('Y', RUNEWOOD_SET.planks.block)
                .pattern("  Y")
                .pattern(" X ")
                .pattern("X  ")
                .unlockedBy("has_totem_base", has(MalumContent.Totemancy.RUNEWOOD_TOTEM_BASE))
                .save(output);

        //CRAFTING COMPONENTS
        shaped(RecipeCategory.MISC, CONVOLUTED_LENS, 2)
                .define('X', HALLOWED_GOLD_NUGGET)
                .define('Y', WARP_FLUX)
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .unlockedBy("has_hallowed_gold", hasHallowedGold).save(output);

        shapeless(RecipeCategory.MISC, MIMICRY_RELAY)
                .requires(MalumContent.AlchemyAndMetallics.IRON_METALLICS.getNode())
                .requires(CTHONIC_GOLD_FRAGMENT)
                .requires(Tags.Items.GEMS_QUARTZ)
                .unlockedBy("has_iron_impetus", has(MalumContent.AlchemyAndMetallics.IRON_METALLICS.getImpetus())).save(output);

        shapeless(RecipeCategory.MISC, RUNIC_SAPBALL).requires(RUNIC_SAP_BOTTLE).requires(WHEAT).unlockedBy("has_runic_sap", has(RUNIC_SAP_BOTTLE)).save(output);
        shapeless(RecipeCategory.MISC, AZOIC_SAPBALL).requires(AZOIC_SAP_BOTTLE).requires(WHEAT).unlockedBy("has_azoic_sap", has(AZOIC_SAP_BOTTLE)).save(output);

        //BLIGHT
        shapeless(RecipeCategory.MISC, BLIGHT).requires(BLIGHTED_GUNK).unlockedBy("has_blight", hasBlight).save(output);
        shaped(RecipeCategory.MISC, BLIGHTED_EARTH)
                .define('X', BLIGHTED_GUNK)
                .pattern("XX")
                .pattern("XX")
                .unlockedBy("has_blight", hasBlight).save(output);
        shaped(RecipeCategory.MISC, COLUMNAR_BLIGHT)
                .define('X', BLIGHTED_GUNK)
                .pattern("X")
                .pattern("X")
                .pattern("X")
                .unlockedBy("has_blight", hasBlight).save(output);
        shapeless(RecipeCategory.BUILDING_BLOCKS, BLIGHTED_SOULWOOD).requires(BLIGHTED_GUNK).requires(SOULWOOD_SET.log).unlockedBy("has_blight", hasBlight).save(output);

        //BANNERS
        shaped(RecipeCategory.BUILDING_BLOCKS, SOULWOVEN_BANNER).define('X', RUNEWOOD_SET.planks.block).define('Y', SOULWOVEN_SILK).pattern("X").pattern("Y").pattern("Y").unlockedBy("has_soulwoven_silk", has(SOULWOVEN_SILK)).save(output);
        bannerRecipe(output, ROTTING_ESSENCE, SoulwovenBannerPatternDataComponent.HUNGER);
        bannerRecipe(output, GRIM_TALC, SoulwovenBannerPatternDataComponent.HORNS);
        bannerRecipe(output, EERIE_WEAVE, SoulwovenBannerPatternDataComponent.HEFT);
        bannerRecipe(output, WARP_FLUX, SoulwovenBannerPatternDataComponent.HALLUCINATION);

        //SPIRIT METALS
        RecipeDatagenCommons.blockIngotExchange(output, SOUL_STAINED_STEEL_INGOT, BLOCK_OF_SOUL_STAINED_STEEL);
        RecipeDatagenCommons.ingotNuggetExchange(output, SOUL_STAINED_STEEL_NUGGET, SOUL_STAINED_STEEL_INGOT);
        plating(output, SOUL_STAINED_STEEL_NUGGET, SOUL_STAINED_STEEL_INGOT, SOUL_STAINED_STEEL_PLATING);

        RecipeDatagenCommons.blockIngotExchange(output, HALLOWED_GOLD_INGOT, BLOCK_OF_HALLOWED_GOLD);
        RecipeDatagenCommons.ingotNuggetExchange(output, HALLOWED_GOLD_NUGGET, HALLOWED_GOLD_INGOT);
        plating(output, HALLOWED_GOLD_NUGGET, HALLOWED_GOLD_INGOT, HALLOWED_GOLD_INLAY);

        RecipeDatagenCommons.blockIngotExchange(output, MALIGNANT_PEWTER_INGOT, BLOCK_OF_MALIGNANT_PEWTER);
        RecipeDatagenCommons.ingotNuggetExchange(output, MALIGNANT_PEWTER_NUGGET, MALIGNANT_PEWTER_INGOT);
        plating(output, MALIGNANT_PEWTER_NUGGET, MALIGNANT_PEWTER_INGOT, MALIGNANT_PEWTER_PLATING);

        //TOOLS
        shaped(RecipeCategory.MISC, MalumContent.Gear.SOUL_STAINED_STEEL_HOE).define('#', Tags.Items.RODS_WOODEN).define('X', SOUL_STAINED_STEEL_INGOT).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_soul_stained_steel", hasSoulStainedSteel).save(output);
        shaped(RecipeCategory.MISC, MalumContent.Gear.SOUL_STAINED_STEEL_PICKAXE).define('#', Tags.Items.RODS_WOODEN).define('X', SOUL_STAINED_STEEL_INGOT).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_soul_stained_steel", hasSoulStainedSteel).save(output);
        shaped(RecipeCategory.MISC, MalumContent.Gear.SOUL_STAINED_STEEL_AXE).define('#', Tags.Items.RODS_WOODEN).define('X', SOUL_STAINED_STEEL_INGOT).pattern("XX ").pattern("X# ").pattern(" # ").unlockedBy("has_soul_stained_steel", hasSoulStainedSteel).save(output);
        shaped(RecipeCategory.MISC, MalumContent.Gear.SOUL_STAINED_STEEL_SHOVEL).define('#', Tags.Items.RODS_WOODEN).define('X', SOUL_STAINED_STEEL_INGOT).pattern("X").pattern("#").pattern("#").unlockedBy("has_soul_stained_steel", hasSoulStainedSteel).save(output);
        shaped(RecipeCategory.MISC, MalumContent.Gear.SOUL_STAINED_STEEL_SWORD).define('#', Tags.Items.RODS_WOODEN).define('X', SOUL_STAINED_STEEL_INGOT).pattern("X").pattern("X").pattern("#").unlockedBy("has_soul_stained_steel", hasSoulStainedSteel).save(output);

        //TRINKETS
        shaped(RecipeCategory.MISC, MalumContent.Gear.GILDED_RING).define('X', HALLOWED_GOLD_INGOT).define('Y', Tags.Items.LEATHERS).pattern("XY ").pattern("Y Y").pattern(" Y ").unlockedBy("has_hallowed_gold", hasHallowedGold).save(output);
        shaped(RecipeCategory.MISC, MalumContent.Gear.GILDED_BELT).define('X', HALLOWED_GOLD_INLAY).define('Y', Tags.Items.LEATHERS).pattern(" Y ").pattern("Y Y").pattern(" X ").unlockedBy("has_hallowed_gold", hasHallowedGold).save(output);

        shaped(RecipeCategory.MISC, MalumContent.Gear.ORNATE_RING).define('X', SOUL_STAINED_STEEL_INGOT).define('Y', Tags.Items.LEATHERS).pattern("XY ").pattern("Y Y").pattern(" Y ").unlockedBy("has_soul_stained_steel", hasSoulStainedSteel).save(output);
        shaped(RecipeCategory.MISC, MalumContent.Gear.ORNATE_NECKLACE).define('X', SOUL_STAINED_STEEL_PLATING).define('Y', EERIE_WEAVE).pattern(" Y ").pattern("Y Y").pattern(" X ").unlockedBy("has_soul_stained_steel", hasSoulStainedSteel).save(output);

        shaped(RecipeCategory.MISC, MalumContent.Gear.RUNIC_BROOCH).define('X', HALLOWED_GOLD_INLAY).define('Y', BLOCK_OF_HALLOWED_GOLD).define('Z', Tags.Items.LEATHERS).pattern(" Z ").pattern("ZXZ").pattern(" Y ").unlockedBy("has_hallowed_gold", hasHallowedGold).save(output);
        shaped(RecipeCategory.MISC, MalumContent.Gear.ELABORATE_BROOCH).define('X', SOUL_STAINED_STEEL_PLATING).define('Y', BLOCK_OF_SOUL_STAINED_STEEL).define('Z', Tags.Items.LEATHERS).pattern(" Z ").pattern("ZXZ").pattern(" Y ").unlockedBy("has_soul_stained_steel", hasSoulStainedSteel).save(output);

        smeltAndBlast(output, malumPath("blazing_quartz_ore"), Ingredient.of(BLAZING_QUARTZ_ORE), RecipeCategory.MISC, hasBlazingQuartz, BLAZING_QUARTZ, 0.25f);

        smeltAndBlast(output, malumPath("brilliance_ore"), Ingredient.of(BRILLIANT_STONE), RecipeCategory.MISC, hasBrilliance, REFINED_BRILLIANCE, 2, 1f);
        smeltAndBlast(output, malumPath("brilliance_deepslate_ore"), Ingredient.of(BRILLIANT_DEEPSLATE), RecipeCategory.MISC, hasBrilliance, REFINED_BRILLIANCE, 2, 1f);
        smeltAndBlast(output, malumPath("raw_brilliance"), Ingredient.of(RAW_BRILLIANCE), RecipeCategory.MISC, hasBrilliance, REFINED_BRILLIANCE, 2, 1f);

        smeltAndBlast(output, malumPath("soulstone_ore"), Ingredient.of(SOULSTONE_ORE), RecipeCategory.MISC, hasSoulstone, REFINED_SOULSTONE, 2, 0.25f);
        smeltAndBlast(output, malumPath("soulstone_deepslate_ore"), Ingredient.of(DEEPSLATE_SOULSTONE_ORE), RecipeCategory.MISC, hasSoulstone, REFINED_SOULSTONE, 2, 0.25f);
        smeltAndBlast(output, malumPath("raw_soulstone"), Ingredient.of(RAW_SOULSTONE), RecipeCategory.MISC, hasSoulstone, REFINED_SOULSTONE, 2, 0.25f);
        smeltAndBlast(output, malumPath("soulstone_bud"), Ingredient.of(SOULSTONE_BUD), RecipeCategory.MISC, hasSoulstone, REFINED_SOULSTONE, 2, 0.5f);
        smeltAndBlast(output, malumPath("realized_soulstone_bud"), Ingredient.of(REALIZED_SOULSTONE_BUD), RecipeCategory.MISC, hasSoulstone, REFINED_SOULSTONE, 16, 2f);

        //FULL BLOCKS
        RecipeDatagenCommons.blockIngotExchange(output, RAW_SOULSTONE, BLOCK_OF_RAW_SOULSTONE);
        RecipeDatagenCommons.blockIngotExchange(output, REFINED_SOULSTONE, BLOCK_OF_REFINED_SOULSTONE);
        RecipeDatagenCommons.blockIngotExchange(output, RAW_BRILLIANCE, BLOCK_OF_RAW_BRILLIANCE);
        RecipeDatagenCommons.blockIngotExchange(output, REFINED_BRILLIANCE, BLOCK_OF_BRILLIANCE);
        RecipeDatagenCommons.blockIngotExchange(output, BLAZING_QUARTZ, BLOCK_OF_BLAZING_QUARTZ);
        RecipeDatagenCommons.blockIngotExchange(output, CTHONIC_GOLD, BLOCK_OF_CTHONIC_GOLD);

        RecipeDatagenCommons.blockIngotExchange(output, ROTTING_ESSENCE, BLOCK_OF_ROTTING_ESSENCE);
        RecipeDatagenCommons.blockIngotExchange(output, GRIM_TALC, BLOCK_OF_GRIM_TALC);
        RecipeDatagenCommons.blockIngotExchange(output, EERIE_WEAVE, BLOCK_OF_EERIE_WEAVE);
        RecipeDatagenCommons.blockIngotExchange(output, WARP_FLUX, BLOCK_OF_WARP_FLUX);

        RecipeDatagenCommons.blockIngotExchange(output, WIND_NUCLEUS, BLOCK_OF_WIND_NUCLEI);
        RecipeDatagenCommons.blockIngotExchange(output, PYRE_NUCLEUS, BLOCK_OF_PYRE_NUCLEI);

        RecipeDatagenCommons.blockIngotExchange(output, HEX_ASH, BLOCK_OF_HEX_ASH);
        RecipeDatagenCommons.blockIngotExchange(output, LIVING_FLESH, BLOCK_OF_LIVING_FLESH);
        RecipeDatagenCommons.blockIngotExchange(output, ALCHEMICAL_CALX, BLOCK_OF_ALCHEMICAL_CALX);
        RecipeDatagenCommons.blockIngotExchange(output, ARCANE_CHARCOAL, BLOCK_OF_ARCANE_CHARCOAL);

        RecipeDatagenCommons.blockIngotExchange(output, CALCIFIED_EBONY, BLOCK_OF_EBONY);

        RecipeDatagenCommons.blockIngotExchange(output, NULL_SLATE, BLOCK_OF_NULL_SLATE);
        RecipeDatagenCommons.blockIngotExchange(output, VOID_SALTS, BLOCK_OF_VOID_SALTS);
        RecipeDatagenCommons.blockIngotExchange(output, MNEMONIC_FRAGMENT, BLOCK_OF_MNEMONIC_FRAGMENT);
        RecipeDatagenCommons.blockIngotExchange(output, MALIGNANT_LEAD, BLOCK_OF_MALIGNANT_LEAD);
        RecipeDatagenCommons.blockIngotExchange(output, AURIC_EMBERS, BLOCK_OF_AURIC_EMBERS);

        //MISC
        shaped(RecipeCategory.MISC, NETHERRACK, 2).define('Z', BLAZING_QUARTZ).define('Y', Tags.Items.COBBLESTONES).pattern("ZY").pattern("YZ").unlockedBy("has_blazing_quartz", has(BLAZING_QUARTZ)).save(output, malumPath("netherrack_from_blazing_quartz"));
        shapeless(RecipeCategory.MISC, EXPERIENCE_BOTTLE).requires(REFINED_BRILLIANCE).requires(GLASS_BOTTLE).unlockedBy("has_brilliance", has(REFINED_BRILLIANCE)).save(output, malumPath("experience_bottle_from_brilliance"));

        shapeless(RecipeCategory.MISC, BONE_MEAL, 6).requires(GRIM_TALC).unlockedBy("has_grim_talc", has(GRIM_TALC)).save(output, malumPath("bonemeal_from_grim_talc"));
        shaped(RecipeCategory.MISC, SKELETON_SKULL).define('#', GRIM_TALC).define('&', Tags.Items.BONES).pattern("&&&").pattern("&#&").pattern("&&&").unlockedBy("has_grim_talc", has(GRIM_TALC)).save(output, malumPath("skeleton_skull_from_grim_talc"));
        shaped(RecipeCategory.MISC, ZOMBIE_HEAD).define('#', GRIM_TALC).define('&', ROTTEN_FLESH).pattern("&&&").pattern("&#&").pattern("&&&").unlockedBy("has_grim_talc", has(GRIM_TALC)).save(output, malumPath("zombie_head_from_grim_talc"));

        shaped(RecipeCategory.MISC, TORCH, 6).define('#', BLAZING_QUARTZ).define('&', STICK).pattern("#").pattern("&").unlockedBy("has_blazing_quartz", has(BLAZING_QUARTZ)).save(output, malumPath("torch_from_blazing_quartz"));

        //THE DEVICE
        shaped(RecipeCategory.MISC, THE_DEVICE).define('X', TWISTED_ROCK_SET.rock.block).define('Y', TAINTED_ROCK_SET.rock.block).pattern("XYX").pattern("YXY").pattern("XYX").unlockedBy("has_bedrock", has(BEDROCK)).save(output);


        //WEAVES
        weaveRecipe(output, BREAD, ACE_PRIDEWEAVE);
        weaveRecipe(output, BOOK, AGENDER_PRIDEWEAVE);
        weaveRecipe(output, ARROW, ARO_PRIDEWEAVE);
        weaveRecipe(output, WHEAT_SEEDS, AROACE_PRIDEWEAVE);
        weaveRecipe(output, WHEAT, BI_PRIDEWEAVE);
        weaveRecipe(output, RAW_IRON, DEMIBOY_PRIDEWEAVE);
        weaveRecipe(output, RAW_COPPER, DEMIGIRL_PRIDEWEAVE);
        weaveRecipe(output, MOSS_BLOCK, ENBY_PRIDEWEAVE);
        weaveRecipe(output, MELON_SLICE, GAY_PRIDEWEAVE);
        weaveRecipe(output, WATER_BUCKET, GENDERFLUID_PRIDEWEAVE);
        weaveRecipe(output, GLASS_BOTTLE, GENDERQUEER_PRIDEWEAVE);
        weaveRecipe(output, AZALEA, INTERSEX_PRIDEWEAVE);
        weaveRecipe(output, HONEYCOMB, LESBIAN_PRIDEWEAVE);
        weaveRecipe(output, CARROT, PAN_PRIDEWEAVE);
        weaveRecipe(output, REPEATER, PLURAL_PRIDEWEAVE);
        weaveRecipe(output, COMPARATOR, POLY_PRIDEWEAVE);
        weaveRecipe(output, STONE_BRICK_WALL, PRIDE_PRIDEWEAVE);
        weaveRecipe(output, EGG, TRANS_PRIDEWEAVE);
    }

    protected static void bannerRecipe(RecipeOutput consumer, ItemLike material, SoulwovenBannerPatternDataComponent pattern) {
        shapeless(RecipeCategory.BUILDING_BLOCKS, pattern.getDefaultStack()).requires(SOULWOVEN_BANNER).requires(material).unlockedBy("has_soulwoven_silk", has(SOULWOVEN_SILK)).save(consumer, pattern.getRecipeId());
    }

    protected static void weaveRecipe(RecipeOutput consumer, ItemLike sideItem, ItemLike output) {
        shapeless(RecipeCategory.MISC, output).requires(ESOTERIC_SPOOL).requires(sideItem).unlockedBy("has_spool", has(ESOTERIC_SPOOL)).save(consumer);
    }

    protected static void plating(RecipeOutput consumer, ItemLike nuggetForm, ItemLike ingotForm, ItemLike result) {
        String itemName = BuiltInRegistries.ITEM.getKey(ingotForm.asItem()).getPath();
        shaped(RecipeCategory.MISC, result, 2)
                .define('X', nuggetForm)
                .define('Y', ingotForm)
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .unlockedBy("has_" + itemName, has(ingotForm))
                .save(consumer);
    }
}
