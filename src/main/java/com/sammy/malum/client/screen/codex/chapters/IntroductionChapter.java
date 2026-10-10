package com.sammy.malum.client.screen.codex.chapters;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.display.CodexIconRenderer;
import com.sammy.malum.client.screen.codex.display.gizmo.*;
import com.sammy.malum.client.screen.codex.pages.CyclingPage;
import com.sammy.malum.client.screen.codex.pages.PageSelectionPage;
import com.sammy.malum.client.screen.codex.pages.display.SoulstoneGrowthStagePage;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.CraftingPage;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.CraftingPage.CraftingGridContents;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.SmeltingPage;
import com.sammy.malum.client.screen.codex.pages.text.*;
import com.sammy.malum.core.systems.spirit.SpiritLike;
import com.sammy.malum.registry.common.MalumContent.*;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import net.minecraft.world.item.Items;

import static com.sammy.malum.client.screen.codex.display.gizmo.DisplayedItem.item;
import static com.sammy.malum.client.screen.codex.pages.InteractionPage.bottling;
import static com.sammy.malum.client.screen.codex.pages.InteractionPage.stripping;
import static com.sammy.malum.client.screen.codex.pages.recipe.vanilla.CraftingPage.compacting;
import static com.sammy.malum.client.screen.codex.pages.recipe.vanilla.CraftingPage.crafting;
import static com.sammy.malum.client.screen.codex.pages.text.HeadlineTextGizmoPage.headlineTextGizmoPage;
import static com.sammy.malum.client.screen.codex.pages.text.HeadlineTextPage.headlineText;
import static com.sammy.malum.client.screen.codex.pages.text.TextPage.textPage;
import static com.sammy.malum.registry.common.MalumContent.BuildingBlocks.*;
import static com.sammy.malum.registry.common.MalumContent.CompactBlocks.*;
import static com.sammy.malum.registry.common.MalumContent.ENCYCLOPEDIA_ARCANA;
import static com.sammy.malum.registry.common.MalumContent.Materials.*;
import static com.sammy.malum.registry.common.MalumContent.Sorcery.*;
import static net.minecraft.world.item.Items.GRASS_BLOCK;
import static net.minecraft.world.item.Items.WHEAT;

public class IntroductionChapter extends BookChapter {

    @Override
    public void init() {
        var soulShard = DisplayedTexture.texture(CodexIconRenderer.create("soul_shard", 16, 16));
        var overworld = DisplayedTexture.texture(CodexIconRenderer.create("overworld", 16, 16));
        var creatureCores = DisplayedTexture.texture(CodexIconRenderer.create("core_keeping", 16, 16));

        var introduction = addEntry("introduction", 0, 0)
                .setIcon(item(ENCYCLOPEDIA_ARCANA))
                .setDesign(EntryWidgetDesign.LARGE)
                .addPage(headlineTextGizmoPage("introduction", item(ENCYCLOPEDIA_ARCANA)))
                .addPage(textPage("introduction.2"))
                .addPage(textPage("introduction.3"))
                .addPage(textPage("introduction.4"))
                .addPage(textPage("introduction.5"));

        var spiritCrystals = addEntry("spirit_crystals", 1, 1)
                .setIcon(soulShard)
                .setDesign(EntryWidgetDesign.SMALL)
                .addPage(headlineTextGizmoPage("spirit_crystals", soulShard))
                .addPage(textPage("spirit_crystals.2"))
                .addPage(textPage("spirit_crystals.3"))
                .requires(introduction);


        var runewood = addEntry("runewood", 0, 2)
                .setIcon(item(RUNEWOOD_SAPLING))
                .addPage(headlineTextGizmoPage("runewood", item(RUNEWOOD_SAPLING)))
                .addPage(PageSelectionPage.create("runewood", s -> s
                                .addHeadline(item(GRASS_BLOCK), "runewood.placement")
                                .addHeadline(item(RUNEWOOD_SAPLING), "runewood.genesis")
                                .addHeadline(item(AZURE_RUNEWOOD_SAPLING), "runewood.azure")
                        )
                )
                .addPage(headlineTextGizmoPage("runewood.arcane_charcoal", item(ARCANE_CHARCOAL)))
                .addPage(PageSelectionPage.create("runewood.arcane_charcoal", s -> s
                                .addSmelting(item(RUNEWOOD_SET.log), item(ARCANE_CHARCOAL))
                                .addCompacting(item(ARCANE_CHARCOAL), item(BLOCK_OF_ARCANE_CHARCOAL))
                        )
                )
                .addPage(headlineTextGizmoPage("runewood.runic_sap", item(RUNIC_SAP_BOTTLE)))
                .addPage(PageSelectionPage.create("runewood.runic_sap", s -> s
                                .add(item(RUNEWOOD_SET.strippedSappyLog),
                                        stripping(item(RUNEWOOD_SET.sappyLog), item(RUNEWOOD_SET.strippedSappyLog)))
                                .add(item(RUNIC_SAP_BOTTLE),
                                        bottling(item(RUNEWOOD_SET.strippedSappyLog), item(RUNIC_SAP_BOTTLE)))
                                .add(item(RUNIC_SAPBALL),
                                        crafting(item(RUNIC_SAPBALL), c -> c.top(item(WHEAT)).middle(item(RUNIC_SAP_BOTTLE))))
                        )
                )
                .requires(spiritCrystals);

        var arcaneWonders = addEntry("arcane_wonders", -1, 3)
                .setIcon(overworld)
                .setDesign(EntryWidgetDesign.SMALL)
                .addPage(headlineTextGizmoPage("arcane_wonders", overworld))
                .addPage(textPage("arcane_wonders.2"))
                .addPage(textPage("arcane_wonders.3"))
                .addPage(textPage("arcane_wonders.4"))
                .requires(runewood);

        var soulstone = addEntry("soulstone", 0, 4)
                .setIcon(item(RAW_SOULSTONE))
                .addPage(headlineTextGizmoPage("soulstone", item(RAW_SOULSTONE)))
                .addPage(PageSelectionPage.create("soulstone", s -> s
                                .addHeadline(item(SOULSTONE_ORE), "soulstone.synopsis")
                                .addHeadline(item(RAW_SOULSTONE), "soulstone.ore_deposits")
                                .addHeadline(item(SOULSTONE_BUD), "soulstone.buds")
                        )
                )
                .addPage(headlineTextGizmoPage("soulstone.refinement", item(REFINED_SOULSTONE)))
                .addPage(PageSelectionPage.create("soulstone.refinement", s -> s
                                .addSmelting(item(RAW_SOULSTONE), item(REFINED_SOULSTONE, 2))
                                .addCompacting(item(REFINED_SOULSTONE), item(BLOCK_OF_REFINED_SOULSTONE))
                                .addCompacting(item(RAW_SOULSTONE), item(BLOCK_OF_RAW_SOULSTONE))
                        )
                ).requires(arcaneWonders);

        var soulstoneBuds = addEntry("soulstone_buds", 2, 4)
                .setIcon(item(SOULSTONE_BUD))
                .addPage(headlineTextGizmoPage("soulstone_buds", item(SOULSTONE_BUD)))
                .addPage(textPage("soulstone_buds.2"))
                .addPage(headlineTextGizmoPage("realizing_soulstone_buds", item(REALIZED_SOULSTONE_BUD)))
                .addPage(new SoulstoneGrowthStagePage())
                .requires(soulstone);

        var derealizedMetal = addEntry("derealized_metal", 3, 3)
                .setIcon(item(AlchemyAndMetallics.IRON_METALLICS.getDerealizedMetal()))
                .requires(soulstoneBuds);

        var scythes = addEntry("scythes", 0, 6)
                .setIcon(item(Gear.CRUDE_SCYTHE))
                .addPage(headlineTextGizmoPage("scythes", item(Gear.CRUDE_SCYTHE)))
                .addPage(textPage("scythes.2"))
                .addPage(textPage("scythes.3"))
                .addPage(crafting(item(Gear.CRUDE_SCYTHE), c -> c
                        .fill(item(Items.IRON_INGOT), CraftingGridContents::topLeft, CraftingGridContents::top, CraftingGridContents::right)
                        .fill(item(Items.STICK), CraftingGridContents::middle, CraftingGridContents::bottomLeft)
                        .fill(item(REFINED_SOULSTONE), CraftingGridContents::topRight)
                ))
                .requires(soulstone);

        var spiritInfusion = addEntry("spirit_infusion", 0, 8)
                .setIcon(item(SPIRIT_ALTAR))
                .setDesign(EntryWidgetDesign.LARGE)
                .addPage(headlineTextGizmoPage("spirit_infusion", item(SPIRIT_ALTAR)))
                .addPage(textPage("spirit_infusion.2"))
                .addPage(textPage("spirit_infusion.3"))
                .addPage(PageSelectionPage.create("spirit_infusion", s -> s
                                .add(item(SPIRIT_ALTAR), crafting(item(SPIRIT_ALTAR), c -> c
                                        .fill(item(REFINED_SOULSTONE), CraftingGridContents::top)
                                        .fill(item(Items.GOLD_INGOT), CraftingGridContents::left, CraftingGridContents::right)
                                        .fill(item(RUNEWOOD_SET.planks.block), CraftingGridContents::middle, CraftingGridContents::bottomLayer)
                                ))
                                .add(item(RUNEWOOD_SET.itemPedestal), CraftingPage.pedestal(RUNEWOOD_SET))
                                .add(item(RUNEWOOD_SET.itemStand), CraftingPage.stand(RUNEWOOD_SET))

                        )
                )
                .requires(scythes);

        var commonReagents = addEntry("common_reagents", 2, 9)
                .setIcon(item(HEX_ASH))
                .addPage(headlineText("common_reagents"))
                .addPage(textPage("common_reagents.2"))
                .addRightBookmark(new EntryBookmark(item(HEX_ASH),
                        BookEntry.create("common_reagents.hex_ash")
                                .addPage(headlineTextGizmoPage("common_reagents.hex_ash", item(HEX_ASH)))
                ))
                .addRightBookmark(new EntryBookmark(item(LIVING_FLESH),
                        BookEntry.create("common_reagents.living_flesh")
                                .addPage(headlineTextGizmoPage("common_reagents.living_flesh", item(LIVING_FLESH)))
                ))
                .addRightBookmark(new EntryBookmark(item(ALCHEMICAL_CALX),
                        BookEntry.create("common_reagents.alchemical_calx")
                                .addPage(headlineTextGizmoPage("common_reagents.alchemical_calx", item(ALCHEMICAL_CALX)))
                ))
                .requires(spiritInfusion);


        var esotericReaping = addEntry("esoteric_reaping", -2, 9)
                .setIcon(item(WARP_FLUX))
                .addPage(headlineText("esoteric_reaping"))
                .addPage(textPage("esoteric_reaping.2"))
                .addRightBookmark(new EntryBookmark(item(GRIM_TALC),
                        BookEntry.create("esoteric_reaping.grim_talc")
                                .addPage(headlineTextGizmoPage("esoteric_reaping.grim_talc", item(GRIM_TALC)))
                ))
                .addRightBookmark(new EntryBookmark(item(ROTTING_ESSENCE),
                        BookEntry.create("esoteric_reaping.rotting_essence")
                                .addPage(headlineTextGizmoPage("esoteric_reaping.rotting_essence", item(ROTTING_ESSENCE)))
                ))
                .addRightBookmark(new EntryBookmark(item(EERIE_WEAVE),
                        BookEntry.create("esoteric_reaping.eerie_weave")
                                .addPage(headlineTextGizmoPage("esoteric_reaping.eerie_weave", item(EERIE_WEAVE)))
                ))
                .addRightBookmark(new EntryBookmark(item(WARP_FLUX),
                        BookEntry.create("esoteric_reaping.warp_flux")
                                .addPage(headlineTextGizmoPage("esoteric_reaping.warp_flux", item(WARP_FLUX)))
                ))

                .addLeftBookmark(new EntryBookmark(creatureCores,
                        BookEntry.create("common_reagents.construct_cores")
                                .addPage(headlineTextGizmoPage("common_reagents.construct_cores", creatureCores))
                                .addPage(textPage("common_reagents.construct_cores.2"))
                                .addPage(textPage("common_reagents.construct_cores.3"))
                                .addPage(textPage("common_reagents.construct_cores.4"))
                                .addRightBookmark(new EntryBookmark(item(WIND_NUCLEUS),
                                        BookEntry.create("common_reagents.construct_cores.wind_nucleus")
                                                .addPage(headlineTextGizmoPage("common_reagents.construct_cores.wind_nucleus", item(WIND_NUCLEUS)))
                                ))
                                .addRightBookmark(new EntryBookmark(item(PYRE_NUCLEUS),
                                        BookEntry.create("common_reagents.construct_cores.pyre_nucleus")
                                                .addPage(headlineTextGizmoPage("common_reagents.construct_cores.pyre_nucleus", item(PYRE_NUCLEUS)))
                                ))
                ))
                .requires(spiritInfusion);

        var theArcanas = addSubspaceEntry("the_arcanas", 0, 12, b -> b.setIcon(item(Spirits.ARCANE_SPIRIT)).requires(spiritInfusion))
                .setSize(300);

        addSpiritEntry(theArcanas, MalumSpiritTypes.SACRED_SPIRIT, 2, -2);
        addSpiritEntry(theArcanas, MalumSpiritTypes.WICKED_SPIRIT, -2, 2);

        addSpiritEntry(theArcanas, MalumSpiritTypes.ARCANE_SPIRIT, -3, 0);
        addSpiritEntry(theArcanas, MalumSpiritTypes.ELDRITCH_SPIRIT, 3, 0);

        addSpiritEntry(theArcanas, MalumSpiritTypes.AERIAL_SPIRIT, -1, 0);
        addSpiritEntry(theArcanas, MalumSpiritTypes.AQUEOUS_SPIRIT, 0, -1);
        addSpiritEntry(theArcanas, MalumSpiritTypes.EARTHEN_SPIRIT, 1, 0);
        addSpiritEntry(theArcanas, MalumSpiritTypes.INFERNAL_SPIRIT, 0, 1);
    }

    public static void addSpiritEntry(EntryAcceptor acceptor, SpiritLike spirit, int x, int y) {
        var translationKey = spirit.getRegistryName().getPath();
        var symbol = DisplayedSpiritSymbol.spirit(spirit);
        acceptor.addEntry(translationKey, x, y)
                .setIcon(item(spirit.getSpiritStack()))
                .setDesign(EntryWidgetDesign.SMALL)
                .addPage(HeadlineSpiritPage.spirit(translationKey, symbol))
                .addPage(TextPage.textPage(translationKey + ".2"));
    }
}