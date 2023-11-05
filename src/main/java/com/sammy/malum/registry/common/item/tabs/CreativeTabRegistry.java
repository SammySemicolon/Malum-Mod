package com.sammy.malum.registry.common.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class CreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MalumMod.MALUM);

    public static final RegistryObject<CreativeModeTab> CONTENT = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".content",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_basis_of_magic"))
                    .icon(() -> ItemRegistry.SPIRIT_ALTAR.get().getDefaultInstance())
                    .displayItems((parameters, output) -> output.accept(ItemRegistry.SPIRIT_ALTAR.get())).build()
    );

    public static final RegistryObject<CreativeModeTab> BUILDING = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".building",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_arcane_construct"))
                    .icon(() -> ItemRegistry.TAINTED_ROCK.get().getDefaultInstance())
                    .displayItems((parameters, output) -> output.accept(ItemRegistry.TAINTED_ROCK.get())).build()
    );

    public static final RegistryObject<CreativeModeTab> NATURE = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".nature",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_natural_wonders"))
                    .icon(() -> ItemRegistry.RUNEWOOD_SAPLING.get().getDefaultInstance())
                    .displayItems((parameters, output) -> output.accept(ItemRegistry.RUNEWOOD_SAPLING.get())).build()
    );

    public static final RegistryObject<CreativeModeTab> METALLURGY = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".metallurgy",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_metallurgic_magics"))
                    .icon(() -> ItemRegistry.ALCHEMICAL_IMPETUS.get().getDefaultInstance())
                    .displayItems((parameters, output) -> output.accept(ItemRegistry.ALCHEMICAL_IMPETUS.get())).build()
    );

    public static final RegistryObject<CreativeModeTab> VOID = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".void",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_void_chronicles"))
                    .icon(() -> ItemRegistry.STRANGE_NUCLEUS.get().getDefaultInstance())
                    .displayItems((parameters, output) -> output.accept(ItemRegistry.STRANGE_NUCLEUS.get())).build()
    );

    public static final RegistryObject<CreativeModeTab> COSMETIC = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".cosmetic",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_cosmetics"))
                    .icon(() -> ItemRegistry.WEAVERS_WORKBENCH.get().getDefaultInstance())
                    .displayItems((parameters, output) -> output.accept(ItemRegistry.WEAVERS_WORKBENCH.get())).build()
    );

    public static void populateItemGroups(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CONTENT.getKey()) {
            event.accept(SACRED_SPIRIT);
            event.accept(WICKED_SPIRIT);
            event.accept(ARCANE_SPIRIT);
            event.accept(ELDRITCH_SPIRIT);
            event.accept(AQUEOUS_SPIRIT);
            event.accept(AERIAL_SPIRIT);
            event.accept(INFERNAL_SPIRIT);
            event.accept(EARTHEN_SPIRIT);

            event.accept(ARCANE_CHARCOAL);
            event.accept(ARCANE_CHARCOAL_FRAGMENT);
            event.accept(BLOCK_OF_ARCANE_CHARCOAL);

            event.accept(BLAZING_QUARTZ_ORE);
            event.accept(BLAZING_QUARTZ);
            event.accept(BLAZING_QUARTZ_FRAGMENT);
            event.accept(BLOCK_OF_BLAZING_QUARTZ);

            event.accept(NATURAL_QUARTZ_ORE);
            event.accept(DEEPSLATE_QUARTZ_ORE);
            event.accept(NATURAL_QUARTZ);

            event.accept(BRILLIANT_STONE);
            event.accept(BRILLIANT_DEEPSLATE);
            event.accept(CLUSTER_OF_BRILLIANCE);
            event.accept(CRUSHED_BRILLIANCE);
            event.accept(CHUNK_OF_BRILLIANCE);
            event.accept(BLOCK_OF_BRILLIANCE);

            event.accept(SOULSTONE_ORE);
            event.accept(DEEPSLATE_SOULSTONE_ORE);
            event.accept(RAW_SOULSTONE);
            event.accept(CRUSHED_SOULSTONE);
            event.accept(BLOCK_OF_RAW_SOULSTONE);
            event.accept(PROCESSED_SOULSTONE);
            event.accept(BLOCK_OF_SOULSTONE);

            event.accept(ETHER);
            event.accept(ETHER_TORCH);
            event.accept(TAINTED_ETHER_BRAZIER);
            event.accept(TWISTED_ETHER_BRAZIER);

            event.accept(IRIDESCENT_ETHER);
            event.accept(IRIDESCENT_ETHER_TORCH);
            event.accept(TAINTED_IRIDESCENT_ETHER_BRAZIER);
            event.accept(TWISTED_IRIDESCENT_ETHER_BRAZIER);

            event.accept(ETHERIC_NITRATE);
            event.accept(VIVID_NITRATE);
        }

        if (event.getTabKey() == BUILDING.getKey()) {
            //region tainted rock
            event.accept(TAINTED_ROCK);
            event.accept(SMOOTH_TAINTED_ROCK);
            event.accept(POLISHED_TAINTED_ROCK);
            event.accept(TAINTED_ROCK_BRICKS);
            event.accept(TAINTED_ROCK_TILES);
            event.accept(SMALL_TAINTED_ROCK_BRICKS);
            event.accept(RUNIC_TAINTED_ROCK_BRICKS);
            event.accept(RUNIC_TAINTED_ROCK_TILES);
            event.accept(RUNIC_SMALL_TAINTED_ROCK_BRICKS);

            event.accept(TAINTED_ROCK_COLUMN);
            event.accept(TAINTED_ROCK_COLUMN_CAP);

            event.accept(CUT_TAINTED_ROCK);
            event.accept(CHISELED_TAINTED_ROCK);
            event.accept(TAINTED_ROCK_PRESSURE_PLATE);
            event.accept(TAINTED_ROCK_BUTTON);

            event.accept(TAINTED_ROCK_WALL);
            event.accept(TAINTED_ROCK_BRICKS_WALL);
            event.accept(TAINTED_ROCK_TILES_WALL);
            event.accept(SMALL_TAINTED_ROCK_BRICKS_WALL);
            event.accept(RUNIC_TAINTED_ROCK_BRICKS_WALL);
            event.accept(RUNIC_TAINTED_ROCK_TILES_WALL);
            event.accept(RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL);

            event.accept(TAINTED_ROCK_SLAB);
            event.accept(SMOOTH_TAINTED_ROCK_SLAB);
            event.accept(POLISHED_TAINTED_ROCK_SLAB);
            event.accept(TAINTED_ROCK_BRICKS_SLAB);
            event.accept(TAINTED_ROCK_TILES_SLAB);
            event.accept(SMALL_TAINTED_ROCK_BRICKS_SLAB);
            event.accept(RUNIC_TAINTED_ROCK_BRICKS_SLAB);
            event.accept(RUNIC_TAINTED_ROCK_TILES_SLAB);
            event.accept(RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB);

            event.accept(TAINTED_ROCK_STAIRS);
            event.accept(SMOOTH_TAINTED_ROCK_STAIRS);
            event.accept(POLISHED_TAINTED_ROCK_STAIRS);
            event.accept(TAINTED_ROCK_BRICKS_STAIRS);
            event.accept(TAINTED_ROCK_TILES_STAIRS);
            event.accept(SMALL_TAINTED_ROCK_BRICKS_STAIRS);
            event.accept(RUNIC_TAINTED_ROCK_BRICKS_STAIRS);
            event.accept(RUNIC_TAINTED_ROCK_TILES_STAIRS);
            event.accept(RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS);

            event.accept(TAINTED_ROCK_ITEM_STAND);
            event.accept(TAINTED_ROCK_ITEM_PEDESTAL);
            //endregion

            //region twisted rock
            event.accept(TWISTED_ROCK);
            event.accept(SMOOTH_TWISTED_ROCK);
            event.accept(POLISHED_TWISTED_ROCK);
            event.accept(TWISTED_ROCK_BRICKS);
            event.accept(TWISTED_ROCK_TILES);
            event.accept(SMALL_TWISTED_ROCK_BRICKS);
            event.accept(RUNIC_TWISTED_ROCK_BRICKS);
            event.accept(RUNIC_TWISTED_ROCK_TILES);
            event.accept(RUNIC_SMALL_TWISTED_ROCK_BRICKS);

            event.accept(TWISTED_ROCK_COLUMN);
            event.accept(TWISTED_ROCK_COLUMN_CAP);

            event.accept(CUT_TWISTED_ROCK);
            event.accept(CHISELED_TWISTED_ROCK);
            event.accept(TWISTED_ROCK_PRESSURE_PLATE);
            event.accept(TWISTED_ROCK_BUTTON);

            event.accept(TWISTED_ROCK_WALL);
            event.accept(TWISTED_ROCK_BRICKS_WALL);
            event.accept(TWISTED_ROCK_TILES_WALL);
            event.accept(SMALL_TWISTED_ROCK_BRICKS_WALL);
            event.accept(RUNIC_TWISTED_ROCK_BRICKS_WALL);
            event.accept(RUNIC_TWISTED_ROCK_TILES_WALL);
            event.accept(RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL);

            event.accept(TWISTED_ROCK_SLAB);
            event.accept(SMOOTH_TWISTED_ROCK_SLAB);
            event.accept(POLISHED_TWISTED_ROCK_SLAB);
            event.accept(TWISTED_ROCK_BRICKS_SLAB);
            event.accept(TWISTED_ROCK_TILES_SLAB);
            event.accept(SMALL_TWISTED_ROCK_BRICKS_SLAB);
            event.accept(RUNIC_TWISTED_ROCK_BRICKS_SLAB);
            event.accept(RUNIC_TWISTED_ROCK_TILES_SLAB);
            event.accept(RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB);

            event.accept(TWISTED_ROCK_STAIRS);
            event.accept(SMOOTH_TWISTED_ROCK_STAIRS);
            event.accept(POLISHED_TWISTED_ROCK_STAIRS);
            event.accept(TWISTED_ROCK_BRICKS_STAIRS);
            event.accept(TWISTED_ROCK_TILES_STAIRS);
            event.accept(SMALL_TWISTED_ROCK_BRICKS_STAIRS);
            event.accept(RUNIC_TWISTED_ROCK_BRICKS_STAIRS);
            event.accept(RUNIC_TWISTED_ROCK_TILES_STAIRS);
            event.accept(RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS);

            event.accept(TWISTED_ROCK_ITEM_STAND);
            event.accept(TWISTED_ROCK_ITEM_PEDESTAL);
            //endregion twisted rock
        }

        if (event.getTabKey() == NATURE.getKey()) {
            //region runewood
            event.accept(HOLY_SAP);
            event.accept(HOLY_SAPBALL);
            event.accept(HOLY_SYRUP);


            event.accept(RUNEWOOD_LEAVES);
            event.accept(RUNEWOOD_SAPLING);

            event.accept(RUNEWOOD_LOG);
            event.accept(STRIPPED_RUNEWOOD_LOG);
            event.accept(RUNEWOOD);
            event.accept(STRIPPED_RUNEWOOD);

            event.accept(EXPOSED_RUNEWOOD_LOG);
            event.accept(REVEALED_RUNEWOOD_LOG);

            event.accept(RUNEWOOD_PLANKS);
            event.accept(VERTICAL_RUNEWOOD_PLANKS);
            event.accept(RUNEWOOD_PANEL);
            event.accept(RUNEWOOD_TILES);

            event.accept(RUNEWOOD_PLANKS_SLAB);
            event.accept(VERTICAL_RUNEWOOD_PLANKS_SLAB);
            event.accept(RUNEWOOD_PANEL_SLAB);
            event.accept(RUNEWOOD_TILES_SLAB);

            event.accept(RUNEWOOD_PLANKS_STAIRS);
            event.accept(VERTICAL_RUNEWOOD_PLANKS_STAIRS);
            event.accept(RUNEWOOD_PANEL_STAIRS);
            event.accept(RUNEWOOD_TILES_STAIRS);

            event.accept(CUT_RUNEWOOD_PLANKS);
            event.accept(RUNEWOOD_BEAM);

            event.accept(RUNEWOOD_DOOR);
            event.accept(RUNEWOOD_TRAPDOOR);
            event.accept(SOLID_RUNEWOOD_TRAPDOOR);

            event.accept(RUNEWOOD_PLANKS_BUTTON);
            event.accept(RUNEWOOD_PLANKS_PRESSURE_PLATE);

            event.accept(RUNEWOOD_PLANKS_FENCE);
            event.accept(RUNEWOOD_PLANKS_FENCE_GATE);

            event.accept(RUNEWOOD_ITEM_STAND);
            event.accept(RUNEWOOD_ITEM_PEDESTAL);

            event.accept(RUNEWOOD_SIGN);
            event.accept(RUNEWOOD_BOAT);
            //endregion

            //region blight
            event.accept(BLIGHTED_EARTH);
            event.accept(BLIGHTED_SOIL);
            event.accept(BLIGHTED_WEED);
            event.accept(BLIGHTED_TUMOR);
            event.accept(BLIGHTED_SOULWOOD);
            event.accept(BLIGHTED_GUNK);
            //endregion
        }

        if (event.getTabKey() == METALLURGY.getKey()) {
            event.accept(CRACKED_IRON_IMPETUS);
            event.accept(IRON_IMPETUS);
            event.accept(IRON_NODE);
            event.accept(CRACKED_COPPER_IMPETUS);
            event.accept(COPPER_IMPETUS);
            event.accept(COPPER_NODE);
            event.accept(CRACKED_GOLD_IMPETUS);
            event.accept(GOLD_IMPETUS);
            event.accept(GOLD_NODE);
            event.accept(CRACKED_LEAD_IMPETUS);
            event.accept(LEAD_IMPETUS);
            event.accept(LEAD_NODE);
            event.accept(CRACKED_SILVER_IMPETUS);
            event.accept(SILVER_IMPETUS);
            event.accept(SILVER_NODE);
            event.accept(CRACKED_ALUMINUM_IMPETUS);
            event.accept(ALUMINUM_IMPETUS);
            event.accept(ALUMINUM_NODE);
            event.accept(CRACKED_NICKEL_IMPETUS);
            event.accept(NICKEL_IMPETUS);
            event.accept(NICKEL_NODE);
            event.accept(CRACKED_URANIUM_IMPETUS);
            event.accept(URANIUM_IMPETUS);
            event.accept(URANIUM_NODE);
            event.accept(CRACKED_OSMIUM_IMPETUS);
            event.accept(OSMIUM_IMPETUS);
            event.accept(OSMIUM_NODE);
            event.accept(CRACKED_ZINC_IMPETUS);
            event.accept(ZINC_IMPETUS);
            event.accept(ZINC_NODE);
            event.accept(CRACKED_TIN_IMPETUS);
            event.accept(TIN_IMPETUS);
            event.accept(TIN_NODE);

            event.accept(CRACKED_ALCHEMICAL_IMPETUS);
            event.accept(ALCHEMICAL_IMPETUS);
        }

        if (event.getTabKey() == CONTENT.getKey()) {
            event.accept(ENCYCLOPEDIA_ARCANA);
            event.accept(SPIRIT_POUCH);
            event.accept(CRUDE_SCYTHE);
            event.accept(SOUL_STAINED_STEEL_SCYTHE);

            event.accept(SOUL_STAINED_STEEL_SWORD);
            event.accept(SOUL_STAINED_STEEL_PICKAXE);
            event.accept(SOUL_STAINED_STEEL_AXE);
            event.accept(SOUL_STAINED_STEEL_SHOVEL);
            event.accept(SOUL_STAINED_STEEL_HOE);


            event.accept(SOUL_STAINED_STEEL_HELMET);
            event.accept(SOUL_STAINED_STEEL_CHESTPLATE);
            event.accept(SOUL_STAINED_STEEL_LEGGINGS);
            event.accept(SOUL_STAINED_STEEL_BOOTS);

            event.accept(SOUL_HUNTER_CLOAK);
            event.accept(SOUL_HUNTER_ROBE);
            event.accept(SOUL_HUNTER_LEGGINGS);
            event.accept(SOUL_HUNTER_BOOTS);

            event.accept(TYRVING);

            event.accept(ETHERIC_NITRATE);
            event.accept(VIVID_NITRATE);

            event.accept(GILDED_RING);
            event.accept(GILDED_BELT);
            event.accept(ORNATE_RING);
            event.accept(ORNATE_NECKLACE);

            event.accept(RING_OF_ESOTERIC_SPOILS);
            event.accept(RING_OF_CURATIVE_TALENT);
            event.accept(RING_OF_ARCANE_PROWESS);
            event.accept(RING_OF_ALCHEMICAL_MASTERY);
            event.accept(RING_OF_DESPERATE_VORACITY);
            event.accept(RING_OF_THE_HOARDER);
            event.accept(RING_OF_THE_DEMOLITIONIST);

            event.accept(NECKLACE_OF_THE_MYSTIC_MIRROR);
            event.accept(NECKLACE_OF_TIDAL_AFFINITY);
            event.accept(NECKLACE_OF_THE_NARROW_EDGE);
            event.accept(NECKLACE_OF_BLISSFUL_HARMONY);

            event.accept(BELT_OF_THE_STARVED);
            event.accept(BELT_OF_THE_PROSPECTOR);
            event.accept(BELT_OF_THE_MAGEBANE);
        }

        if (event.getTabKey() == COSMETIC.getKey()) {
            //region cosmetics
            event.accept(ESOTERIC_SPOOL);
            event.accept(ANCIENT_WEAVE);
            event.accept(CORNERED_WEAVE);
            event.accept(DREADED_WEAVE);
            event.accept(MECHANICAL_WEAVE_V1);
            event.accept(MECHANICAL_WEAVE_V2);

            event.accept(ACE_PRIDEWEAVE);
            event.accept(AGENDER_PRIDEWEAVE);
            event.accept(ARO_PRIDEWEAVE);
            event.accept(AROACE_PRIDEWEAVE);
            event.accept(BI_PRIDEWEAVE);
            event.accept(DEMIBOY_PRIDEWEAVE);
            event.accept(DEMIGIRL_PRIDEWEAVE);
            event.accept(ENBY_PRIDEWEAVE);
            event.accept(GAY_PRIDEWEAVE);
            event.accept(GENDERFLUID_PRIDEWEAVE);
            event.accept(GENDERQUEER_PRIDEWEAVE);
            event.accept(INTERSEX_PRIDEWEAVE);
            event.accept(LESBIAN_PRIDEWEAVE);
            event.accept(PAN_PRIDEWEAVE);
            event.accept(PLURAL_PRIDEWEAVE);
            event.accept(POLY_PRIDEWEAVE);
            event.accept(PRIDE_PRIDEWEAVE);
            event.accept(TRANS_PRIDEWEAVE);

            event.accept(TOPHAT);
        }

        if (event.getTabKey() == VOID.getKey()) {
            event.accept(VOID_SALTS);
            event.accept(NULL_SLATE);
            event.accept(STRANGE_NUCLEUS);
            event.accept(CRYSTALLIZED_NIHILITY);
            event.accept(ANOMALOUS_DESIGN);
            event.accept(COMPLETE_DESIGN);
            event.accept(FUSED_CONSCIOUSNESS);
        }

        if (event.getTabKey() == VOID.getKey()) {
            event.accept(RING_OF_GROWING_FLESH);
            event.accept(RING_OF_GRUESOME_SATIATION);

            event.accept(NECKLACE_OF_THE_HIDDEN_BLADE);
            event.accept(NECKLACE_OF_THE_WATCHER);

        }

        if (false) {
            event.accept(MEPHITIC_EDGE);
            event.accept(SOUL_STAINED_STEEL_KNIFE);
            event.accept(SOULWOOD_STAVE);
            event.accept(BLOCK_OF_VOID_SALTS);
            event.accept(SOUL_VIAL);
            event.accept(UNHOLY_CARAMEL);
            event.accept(HOLY_CARAMEL);
            event.accept(THE_DEVICE);
            event.accept(THE_VESSEL);
            event.accept(CREATIVE_SCYTHE);
            event.accept(TOKEN_OF_GRATITUDE);
            event.accept(PRIMORDIAL_SOUP);

            event.accept(THE_DEVICE);
            event.accept(THE_VESSEL);
            event.accept(CREATIVE_SCYTHE);
            event.accept(TOKEN_OF_GRATITUDE);
            event.accept(PRIMORDIAL_SOUP);
        }
    }
}
