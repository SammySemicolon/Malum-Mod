package com.sammy.malum.datagen.tag;

import com.sammy.malum.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.augment.core.CoreAugmentItem;
import com.sammy.malum.common.item.curiosities.armor.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.common.item.curiosities.curios.runes.madness.*;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.common.item.metallics.FracturedMetalImpetusItem;
import com.sammy.malum.common.item.metallics.MetalNodeItem;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.common.data.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.modules.datagen.providers.tag.LodestoneItemTagsSystem;

import java.util.HashSet;
import java.util.concurrent.*;

import static com.sammy.malum.registry.common.MalumContent.AlchemyAndMetallics.*;
import static com.sammy.malum.registry.common.MalumContent.Blight.BLIGHTED_GUNK;
import static com.sammy.malum.registry.common.MalumContent.BuildingBlocks.*;
import static com.sammy.malum.registry.common.MalumContent.CompactBlocks.*;
import static com.sammy.malum.registry.common.MalumContent.*;
import static com.sammy.malum.registry.common.MalumContent.Gear.*;
import static com.sammy.malum.registry.common.MalumContent.Materials.*;
import static com.sammy.malum.registry.common.MalumContent.Spirits.*;
import static com.sammy.malum.registry.common.MalumTags.Items.*;
import static com.sammy.malum.registry.common.MalumContent.BLOCKS;
import static net.minecraft.world.item.Items.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.*;

@SuppressWarnings("unchecked")
public class MalumItemTagDatagen extends LodestoneItemTagsSystem {

    public MalumItemTagDatagen(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Malum Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        var blocks = new HashSet<>(BLOCKS.getEntries());
        var items = ITEMS.getEntries();
        addTagsFromBlockProperties(blocks);

        tag(ItemTags.TRIMMABLE_ARMOR).addTags(ARMORS);
        tag(ItemTags.TRIM_TEMPLATES, SmithingTemplateItem.class);

        tag(ItemTags.BOOKSHELF_BOOKS).add(ENCYCLOPEDIA_ARCANA.get(), ENCYCLOPEDIA_ESOTERICA.get());

        tag(Tags.Items.GEMS).add(REFINED_SOULSTONE.get(), BLAZING_QUARTZ.get());
        tag(ItemTags.LOGS).addTag(RUNEWOOD_LOGS).addTag(SOULWOOD_LOGS);
        tag(Tags.Items.SLIME_BALLS).add(RUNIC_SAPBALL.get(), AZOIC_SAPBALL.get());
        tag(Tags.Items.GEMS_QUARTZ).add(MUNDANE_QUARTZ.getCluster().asItem());

        tag(Tags.Items.RAW_MATERIALS).add(RAW_SOULSTONE.get(), RAW_BRILLIANCE.get(), CTHONIC_GOLD.get(), CTHONIC_GOLD_FRAGMENT.asItem());
        tag(Tags.Items.NUGGETS).add(HALLOWED_GOLD_NUGGET.get(), SOUL_STAINED_STEEL_NUGGET.get(), MALIGNANT_PEWTER_NUGGET.get());
        tag(Tags.Items.INGOTS).add(HALLOWED_GOLD_INGOT.get(), SOUL_STAINED_STEEL_INGOT.get(), MALIGNANT_PEWTER_INGOT.get());
        tag(Tags.Items.GEMS).add(MUNDANE_QUARTZ.getCluster().asItem(), BLAZING_QUARTZ.get(), RAW_BRILLIANCE.get());
        tag(Tags.Items.NUGGETS).addOptional(MalumMod.malumPath("copper_nugget"));
        tag(NUGGETS_COPPER).addOptional(MalumMod.malumPath("copper_nugget"));

        tag(ItemTags.DYEABLE).add(
                ETHER.asItem(), IRIDESCENT_ETHER.asItem(),
                ETHER_CANDLE.asItem(),IRIDESCENT_ETHER_CANDLE.asItem(),
                ETHER_TORCH.asItem(), IRIDESCENT_ETHER_TORCH.asItem(),
                ETHER_BRAZIER.asItem(), IRIDESCENT_ETHER_BRAZIER.asItem(),
                ETHER_CRESSET.asItem(), IRIDESCENT_ETHER_CRESSET.asItem()
        );

        tag(SOUL_SHATTER_CAPABLE_WEAPON)
                .addTags(SCYTHES, STAVES)
                .add(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_KNIFE.get())
                .add(Enscription.SHAPED_SLAB.get())
                .add(TYRVING.get(), WEIGHT_OF_WORLDS.get())
                .add(SPELLWEAVING_PICKAXE.get(), SPELLWEAVING_AXE.get())
                .add(SUNDERING_ANCHOR.get(), VINDICTIVE_BRAND.get());

        tag(MAGIC_CAPABLE_WEAPON)
                .addTags(SCYTHES, STAVES)
                .add(SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_KNIFE.get())
                .add(GLUTTONOUS_BLUDGEON.get(), TYRVING.get())
                .add(SUNDERING_ANCHOR.get(), VINDICTIVE_BRAND.get())
                .remove(EDGE_OF_DELIVERANCE.get());

        tag(SCYTHES)
                .add(CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), RAVENOUS_SCYTHE.get(), EDGE_OF_DELIVERANCE.get());
        tag(SCYTHES_COMMON).addTag(SCYTHES);

        tag(STAVES)
                .add(MNEMONIC_HEX_STAFF.get(), UNWINDING_CHAOS.get(), EROSION_SCEPTER.get());
        tag(STAVES_COMMON).addTag(STAVES);

        tag(Tags.Items.MELEE_WEAPON_TOOLS).add(
                CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), RAVENOUS_SCYTHE.get(), EDGE_OF_DELIVERANCE.get(),
                SOUL_STAINED_STEEL_KNIFE.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_AXE.get(),
                GLUTTONOUS_BLUDGEON.get(), TYRVING.get(),
                MNEMONIC_HEX_STAFF.get(), EROSION_SCEPTER.get(),
                UNWINDING_CHAOS.get(), SUNDERING_ANCHOR.get(), VINDICTIVE_BRAND.get());
        tag(Tags.Items.RANGED_WEAPON_TOOLS).add(MNEMONIC_HEX_STAFF.get(), EROSION_SCEPTER.get(), UNWINDING_CHAOS.get(), SUNDERING_ANCHOR.get());
        tag(ItemTags.SWORDS).add(SOUL_STAINED_STEEL_SWORD.get(), TYRVING.get());
        tag(ItemTags.PICKAXES).add(SOUL_STAINED_STEEL_PICKAXE.get(), SPELLWEAVING_PICKAXE.get());
        tag(ItemTags.AXES).add(SOUL_STAINED_STEEL_AXE.get(), SPELLWEAVING_AXE.get(), WEIGHT_OF_WORLDS.get());
        tag(ItemTags.SHOVELS).add(SOUL_STAINED_STEEL_SHOVEL.get());
        tag(ItemTags.HOES).add(SOUL_STAINED_STEEL_HOE.get());
        tag(KNIVES_COMMON).add(SOUL_STAINED_STEEL_KNIFE.get(), SUNDERING_ANCHOR.get());
        tag(KNIVES).add(SOUL_STAINED_STEEL_KNIFE.get(), SUNDERING_ANCHOR.get());

        tag(ItemTags.HEAD_ARMOR).add(SOUL_HUNTER_CLOAK.get(), SOUL_STAINED_STEEL_HELMET.get(), MALIGNANT_STRONGHOLD_HELMET.get());
        tag(ItemTags.CHEST_ARMOR).add(SOUL_HUNTER_ROBE.get(), SOUL_STAINED_STEEL_CHESTPLATE.get(), MALIGNANT_STRONGHOLD_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR).add(SOUL_HUNTER_LEGGINGS.get(), SOUL_STAINED_STEEL_LEGGINGS.get(), MALIGNANT_STRONGHOLD_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR).add(SOUL_HUNTER_BOOTS.get(), SOUL_STAINED_STEEL_BOOTS.get(), MALIGNANT_STRONGHOLD_BOOTS.get());

        tag(MELEE_ENCHANTABLE).addTags(SCYTHE_ENCHANTABLE, STAFF_ENCHANTABLE).add(GLUTTONOUS_BLUDGEON.get());
        tag(ItemTags.FIRE_ASPECT_ENCHANTABLE).addTags(SCYTHES);

        tag(HAUNTED_ENCHANTABLE).addTag(MAGIC_CAPABLE_WEAPON);
        tag(ANIMATED_ENCHANTABLE).addTag(MAGIC_CAPABLE_WEAPON);
        tag(SPIRIT_PLUNDER_ENCHANTABLE).addTag(SOUL_SHATTER_CAPABLE_WEAPON);

        tag(SCYTHE_ENCHANTABLE).addTag(SCYTHES);
        tag(REBOUND_ENCHANTABLE).addTag(SCYTHE_ENCHANTABLE);
        tag(ASCENSION_ENCHANTABLE).addTag(SCYTHE_ENCHANTABLE);

        tag(SPELLWEAVING_ENCHANTABLE).add(SPELLWEAVING_PICKAXE.get(), SPELLWEAVING_AXE.get());
        tag(WEAVERS_PROPAGATION_ENCHANTABLE).addTag(SPELLWEAVING_ENCHANTABLE);
        tag(WEAVERS_HASTE_ENCHANTABLE).addTag(SPELLWEAVING_ENCHANTABLE);

        tag(STAFF_ENCHANTABLE).addTag(STAVES);
        tag(REPLENISHING_ENCHANTABLE).addTag(STAFF_ENCHANTABLE);
        tag(CAPACITOR_ENCHANTABLE).addTag(STAFF_ENCHANTABLE);


        tag(ItemTags.DURABILITY_ENCHANTABLE).addTags(SCYTHES, STAVES)
                .add(GLUTTONOUS_BLUDGEON.get())
                .add(CATALYST_LOBBER.get(), SUNDERING_ANCHOR.get());


        tag(ASPECTED_SPIRITS).add(
                SACRED_SPIRIT.get(), WICKED_SPIRIT.get(), ARCANE_SPIRIT.get(), ELDRITCH_SPIRIT.get(),
                AERIAL_SPIRIT.get(), AQUEOUS_SPIRIT.get(), EARTHEN_SPIRIT.get(), INFERNAL_SPIRIT.get());
        tag(SPIRITS).addTag(ASPECTED_SPIRITS).add(UMBRAL_SPIRIT.get());
        tag(MOB_DROPS).add(
                ROTTING_ESSENCE.get(), GRIM_TALC.get(), EERIE_WEAVE.get(), WARP_FLUX.get(),
                ROTTEN_FLESH, SPIDER_EYE, BONE, ARROW, GUNPOWDER, STRING, SLIME_BALL,
                MAGMA_CREAM, BLAZE_ROD, BREEZE_ROD,
                LEATHER, RABBIT_HIDE, FEATHER, INK_SAC);
        tag(MATERIALS).add(
                ROTTING_ESSENCE.get(), GRIM_TALC.get(), EERIE_WEAVE.get(), WARP_FLUX.get(),
                HEX_ASH.get(), LIVING_FLESH.get(), ALCHEMICAL_CALX.get(), BLIGHTED_GUNK.asItem(),
                SOULWOVEN_SILK.get(), ETHER.asItem(), IRIDESCENT_ETHER.asItem(),
                SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_NUGGET.get(), SOUL_STAINED_STEEL_PLATING.get(),
                HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_NUGGET.get(),
                MALIGNANT_PEWTER_INGOT.get(), MALIGNANT_PEWTER_NUGGET.get(), MALIGNANT_PEWTER_PLATING.get(),
                NULL_SLATE.get(), VOID_SALTS.get(), MNEMONIC_FRAGMENT.get(), AURIC_EMBERS.get(), MALIGNANT_LEAD.get(),
                ANOMALOUS_DESIGN.get(), COMPLETE_DESIGN.get(), FUSED_CONSCIOUSNESS.get());
        tag(MINERALS).add(
                RAW_SOULSTONE.get(), REFINED_SOULSTONE.get(),
                RAW_BRILLIANCE.get(), REFINED_BRILLIANCE.get(),
                BLAZING_QUARTZ.asItem(),
                MUNDANE_QUARTZ.getCluster().asItem(), CTHONIC_GOLD.get(), CTHONIC_GOLD_FRAGMENT.asItem());

        tag(AUGMENTS, AugmentItem.class);
        tag(CORE_AUGMENTS, CoreAugmentItem.class);
        tag(METAL_NODES, MetalNodeItem.class);

        tag(IMPETUS, ImpetusItem.class);
        tag(METAL_IMPETUS, ImpetusItem.class).remove(ALCHEMICAL_IMPETUS.get(), ZEPHYR_IMPETUS.get());

        tag(FRACTURED_IMPETUS, FracturedImpetusItem.class);
        tag(FRACTURED_METAL_IMPETUS, FracturedMetalImpetusItem.class).addTag(FRACTURED_IMPETUS);

        tag(ARMORS, MalumArmorItem.class);

        tag(RUNES_STONE, MiracleRuneCurioItem.class);
        tag(RUNES_VOID, MadnessRuneCurioItem.class);
        tag(RUNES_WOODEN, TotemicRuneCurioItem.class);

        tag(IS_TOTEMIC_TOOL).add(Totemancy.TOTEMIC_STAFF.get());
        tag(IS_REDSTONE_TOOL).add(Artifice.ARTIFICERS_CLAW.get()).addOptional(ResourceLocation.parse("create:wrench"));
        tag(IS_ARTIFICE_TOOL).add(Focusing.TUNING_FORK.get()).addOptional(ResourceLocation.parse("create:wrench"));
        tag(COUNTS_AS_EMPTY_HAND).addOptional(ResourceLocation.parse("mowziesmobs:earthrend_gauntlet"));

        tag(SAPBALLS).add(RUNIC_SAPBALL.get(), AZOIC_SAPBALL.get());
        tag(GROSS_FOODS).add(ROTTEN_FLESH, ROTTING_ESSENCE.get());

        tag(PROSPECTORS_TREASURE)
                .addTags(Tags.Items.ORES, Tags.Items.STORAGE_BLOCKS, Tags.Items.INGOTS, Tags.Items.NUGGETS, Tags.Items.GEMS, Tags.Items.RAW_MATERIALS, ItemTags.COALS, METAL_NODES)
                .addOptional(ResourceLocation.parse("tetra:geode"));

        tag(SOULWOVEN_POUCH_EFFICIENT)
                .addTags(SPIRITS);
        tag(SOULWOVEN_POUCH_AUTOCOLLECT)
                .addTags(SPIRITS);

        tag(ARCANE_ELEGY_COMPONENTS).addTag(Tags.Items.MUSIC_DISCS).remove(ARCANE_ELEGY.get(), AESTHETICA.get());

        tag(VOID_SOULSTONE_CONVERSION)
                .addTags(Tags.Items.RAW_MATERIALS)
                .remove(RAW_SOULSTONE.get(), RAW_BRILLIANCE.get(), CTHONIC_GOLD.get(), CTHONIC_GOLD_FRAGMENT.asItem());

        tag(HIDDEN_ALWAYS).add(THE_DEVICE.asItem(), THE_VESSEL.asItem());

        tag(HIDDEN_UNTIL_VOID)
                .addTag(HIDDEN_UNTIL_BLACK_CRYSTAL)
                // Encyclopedia
                .add(ENCYCLOPEDIA_ESOTERICA.get())
                // Equipment
                .add(CATALYST_LOBBER.get())
                // Decor
                .add(NULL_SPIRITED_GLASS.getItem(), NULL_VARNISHED_TERRACOTTA.getItem())
                // Materials
                .add(BLOCK_OF_NULL_SLATE.asItem(), NULL_SLATE.get(),
                        BLOCK_OF_VOID_SALTS.asItem(), VOID_SALTS.get(),
                        BLOCK_OF_MNEMONIC_FRAGMENT.asItem(), MNEMONIC_FRAGMENT.get(),
                        BLOCK_OF_AURIC_EMBERS.asItem(), AURIC_EMBERS.get(),
                        BLOCK_OF_MALIGNANT_LEAD.asItem(), MALIGNANT_LEAD.get());

        tag(HIDDEN_UNTIL_BLACK_CRYSTAL)
                // Umbral Spirit
                .add(UMBRAL_SPIRIT.get())
                // Anomalous Design
                .add(ANOMALOUS_DESIGN.get(), COMPLETE_DESIGN.get(), FUSED_CONSCIOUSNESS.get())
                // Malignant Pewter
                .add(MALIGNANT_PEWTER_INGOT.get(), MALIGNANT_PEWTER_PLATING.get(),
                        MALIGNANT_PEWTER_NUGGET.get(), BLOCK_OF_MALIGNANT_PEWTER.asItem())
                // Equipment
                .add(MALIGNANT_STRONGHOLD_HELMET.get(), MALIGNANT_STRONGHOLD_CHESTPLATE.get(),
                        MALIGNANT_STRONGHOLD_LEGGINGS.get(), MALIGNANT_STRONGHOLD_BOOTS.get(),
                        WEIGHT_OF_WORLDS.get(), EDGE_OF_DELIVERANCE.get(),
                        MNEMONIC_HEX_STAFF.get(), EROSION_SCEPTER.get(),
                        UNWINDING_CHAOS.get(), SUNDERING_ANCHOR.get())
                // Runes
                .add(RUNE_OF_BOLSTERING.get(), RUNE_OF_RADIAL_EMPOWERMENT.get(),
                        RUNE_OF_SPELL_MASTERY.get(), RUNE_OF_HERESY.get(),
                        RUNE_OF_UNNATURAL_STAMINA.get(), RUNE_OF_TWINNED_DURATION.get(),
                        RUNE_OF_INDOMITABILITY.get(), RUNE_OF_IGNEOUS_SOLACE.get())
                // Trinkets
                .add(RING_OF_THE_ENDLESS_WELL.get(), RING_OF_GROWING_FLESH.get(), RING_OF_ECHOING_ARCANA.get(),
                        RING_OF_GRUESOME_CONCENTRATION.get(), NECKLACE_OF_THE_HIDDEN_BLADE.get(),
                        NECKLACE_OF_THE_WATCHER.get(), BELT_OF_THE_LIMITLESS.get())
                // Augments
                .add(Focusing.STELLAR_MECHANISM.get())
                // Aesthetica
                .add(AESTHETICA.get());

        tag(HIDDEN_AS_RESULT_ONLY).add(WEIGHT_OF_WORLDS.get(), EDGE_OF_DELIVERANCE.get(), SUNDERING_ANCHOR.get());

        tag(CONJUNCTURE_CRYSTALLARIUM_FUEL).add(CALCIFIED_EBONY.get());

        for (DeferredHolder<net.minecraft.world.item.Item, ? extends net.minecraft.world.item.Item> i : items) {
            if (i.get() instanceof MalumCurioItem) {
                final net.minecraft.world.item.Item item = i.get();
                final ResourceLocation id = i.getId();
                if (id.getPath().contains("_ring") || id.getPath().contains("ring_")) {
                    tag(RING_CURIO).add(item);
                    continue;
                }
                if (id.getPath().contains("_necklace") || id.getPath().contains("necklace_")) {
                    tag(NECKLACE_CURIO).add(item);
                    continue;
                }
                if (id.getPath().contains("_belt") || id.getPath().contains("belt_")) {
                    tag(BELT_CURIO).add(item);
                    continue;
                }
                if (id.getPath().contains("_rune") || id.getPath().contains("rune_")) {
                    tag(RUNE_CURIO).add(item);
                    continue;
                }
                if (id.getPath().contains("_brooch") || id.getPath().contains("brooch_")) {
                    tag(BROOCH_CURIO).add(item);
                }
            }
        }
    }

    @Override
    public IntrinsicTagAppender<Item> tag(TagKey<Item> tag) {
        return super.tag(tag);
    }

    protected IntrinsicTagAppender<Item> tag(TagKey<Item> tag, Class<? extends Item> itemClass) {
        var appender = super.tag(tag);
        for (DeferredHolder<Item, ? extends Item> entry : ITEMS.getEntries()) {
            var item = entry.get();
            if (itemClass.isInstance(item)) {
                appender.add(item);
            }
        }
        return appender;
    }
}