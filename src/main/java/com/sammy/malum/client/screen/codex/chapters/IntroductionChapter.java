package com.sammy.malum.client.screen.codex.chapters;

import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.EntryAcceptor;
import com.sammy.malum.client.screen.codex.EntryBookmark;
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
                .addPage(headlineTextGizmoPage("introduction", item(ENCYCLOPEDIA_ARCANA)))
                .addPage(textPage("introduction.2"))
                .addPage(textPage("introduction.3"))
                .addPage(textPage("introduction.4"))
                .addPage(textPage("introduction.5"));

        var spiritCrystals = addEntry("spirit_crystals", 1, 1)
                .setIcon(soulShard)
                .addPage(headlineTextGizmoPage("spirit_crystals", soulShard))
                .addPage(textPage("spirit_crystals.2"))
                .addPage(textPage("spirit_crystals.3"))
                .requires(introduction);


        var runewood = addEntry("runewood", 0, 2)
                .setIcon(item(RUNEWOOD_SAPLING))
                .addPage(headlineTextGizmoPage("runewood", item(RUNEWOOD_SAPLING)))
                .addPage(PageSelectionPage.create(s -> s
                                .add(item(GRASS_BLOCK), headlineText("runewood.placement"))
                                .add(item(RUNEWOOD_SAPLING), headlineText("runewood.genesis"))
                                .add(item(AZURE_RUNEWOOD_SAPLING), headlineText("runewood.azure"))
                        )
                )
                .addPage(headlineTextGizmoPage("runewood.arcane_charcoal", item(Materials.ARCANE_CHARCOAL)))
                .addPage(PageSelectionPage.create(s -> s
                                .add(item(Materials.ARCANE_CHARCOAL),
                                        new SmeltingPage(item(RUNEWOOD_SET.log), item(Materials.ARCANE_CHARCOAL)))
                                .add(item(BLOCK_OF_ARCANE_CHARCOAL),
                                        compacting(item(BLOCK_OF_ARCANE_CHARCOAL), item(Materials.ARCANE_CHARCOAL)))
                        )
                )
                .addPage(headlineTextGizmoPage("runewood.runic_sap", item(Materials.RUNIC_SAP_BOTTLE)))
                .addPage(PageSelectionPage.create(s -> s
                                .add(item(RUNEWOOD_SET.strippedSappyLog),
                                        stripping(item(RUNEWOOD_SET.sappyLog), item(RUNEWOOD_SET.strippedSappyLog)))
                                .add(item(Materials.RUNIC_SAP_BOTTLE),
                                        bottling(item(RUNEWOOD_SET.strippedSappyLog), item(Materials.RUNIC_SAP_BOTTLE)))
                                .add(item(Materials.RUNIC_SAPBALL),
                                        crafting(item(Materials.RUNIC_SAPBALL), c -> c.top(item(WHEAT)).middle(item(Materials.RUNIC_SAP_BOTTLE))))
                        )
                )
                .requires(spiritCrystals);

        var arcaneWonders = addEntry("arcane_wonders", -1, 3)
                .setIcon(overworld)
                .addPage(headlineTextGizmoPage("arcane_wonders", overworld))
                .addPage(textPage("arcane_wonders.2"))
                .addPage(textPage("arcane_wonders.3"))
                .addPage(textPage("arcane_wonders.4"))
                .requires(runewood);

        var soulstone = addEntry("soulstone", 0, 4)
                .setIcon(item(Materials.RAW_SOULSTONE))
                .addPage(headlineTextGizmoPage("soulstone", item(Materials.RAW_SOULSTONE)))
                .addPage(PageSelectionPage.create(s -> s
                                .addHeadline(item(Materials.SOULSTONE_ORE), "soulstone.synopsis")
                                .addHeadline(item(Materials.RAW_SOULSTONE), "soulstone.ore_deposits")
                                .addHeadline(item(Materials.SOULSTONE_BUD), "soulstone.buds")
                        )
                )
                .addPage(headlineTextGizmoPage("soulstone.refinement", item(Materials.REFINED_SOULSTONE)))
                .addPage(PageSelectionPage.create(s -> s
                                .add(item(Materials.REFINED_SOULSTONE), new CyclingPage(
                                                new SmeltingPage(item(Materials.RAW_SOULSTONE), item(Materials.REFINED_SOULSTONE, 2)),
                                                new SmeltingPage(item(Materials.SOULSTONE_BUD), item(Materials.REFINED_SOULSTONE, 2))
                                        )
                                )
                                .add(item(BLOCK_OF_REFINED_SOULSTONE),
                                        compacting(item(BLOCK_OF_REFINED_SOULSTONE), item(Materials.REFINED_SOULSTONE)))
                                .add(item(BLOCK_OF_RAW_SOULSTONE),
                                        compacting(item(BLOCK_OF_RAW_SOULSTONE), item(Materials.RAW_SOULSTONE)))
                        )
                ).requires(arcaneWonders);

        var soulstoneBuds = addEntry("soulstone_buds", 2, 4)
                .setIcon(item(Materials.SOULSTONE_BUD))
                .addPage(headlineTextGizmoPage("soulstone_buds", item(Materials.SOULSTONE_BUD)))
                .addPage(textPage("soulstone_buds.2"))
                .addPage(headlineTextGizmoPage("realizing_soulstone_buds", item(Materials.REALIZED_SOULSTONE_BUD)))
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
                        .fill(item(Materials.REFINED_SOULSTONE), CraftingGridContents::topRight)
                ))
                .requires(soulstone);

        var spiritInfusion = addEntry("spirit_infusion", 0, 8)
                .setIcon(item(Sorcery.SPIRIT_ALTAR))
                .addPage(headlineTextGizmoPage("spirit_infusion", item(Sorcery.SPIRIT_ALTAR)))
                .addPage(textPage("spirit_infusion.2"))
                .addPage(textPage("spirit_infusion.3"))
                .addPage(PageSelectionPage.create(s -> s
                                .add(item(Sorcery.SPIRIT_ALTAR), crafting(item(Sorcery.SPIRIT_ALTAR), c -> c
                                        .fill(item(Materials.REFINED_SOULSTONE), CraftingGridContents::top)
                                        .fill(item(Items.GOLD_INGOT), CraftingGridContents::left, CraftingGridContents::right)
                                        .fill(item(RUNEWOOD_SET.planks.block), CraftingGridContents::middle, CraftingGridContents::bottomLayer)
                                ))
                                .add(item(RUNEWOOD_SET.itemPedestal), CraftingPage.pedestal(RUNEWOOD_SET))
                                .add(item(RUNEWOOD_SET.itemStand), CraftingPage.stand(RUNEWOOD_SET))

                        )
                )
                .requires(scythes);

        var commonReagents = addEntry("common_reagents", 2, 9)
                .setIcon(item(Materials.HEX_ASH))
                .addPage(headlineText("common_reagents"))
                .addPage(textPage("common_reagents.2"))
                .addRightBookmark(new EntryBookmark(item(Materials.HEX_ASH),
                        BookEntry.create("common_reagents.hex_ash")
                                .addPage(headlineTextGizmoPage("common_reagents.hex_ash", item(Materials.HEX_ASH)))
                ))
                .addRightBookmark(new EntryBookmark(item(Materials.LIVING_FLESH),
                        BookEntry.create("common_reagents.living_flesh")
                                .addPage(headlineTextGizmoPage("common_reagents.living_flesh", item(Materials.LIVING_FLESH)))
                ))
                .addRightBookmark(new EntryBookmark(item(Materials.ALCHEMICAL_CALX),
                        BookEntry.create("common_reagents.alchemical_calx")
                                .addPage(headlineTextGizmoPage("common_reagents.alchemical_calx", item(Materials.ALCHEMICAL_CALX)))
                ))
                .requires(spiritInfusion);


        var esotericReaping = addEntry("esoteric_reaping", -2, 9)
                .setIcon(item(Materials.WARP_FLUX))
                .addPage(headlineText("esoteric_reaping"))
                .addPage(textPage("esoteric_reaping.2"))
                .addRightBookmark(new EntryBookmark(item(Materials.GRIM_TALC),
                        BookEntry.create("esoteric_reaping.grim_talc")
                                .addPage(headlineTextGizmoPage("esoteric_reaping.grim_talc", item(Materials.GRIM_TALC)))
                ))
                .addRightBookmark(new EntryBookmark(item(Materials.ROTTING_ESSENCE),
                        BookEntry.create("esoteric_reaping.rotting_essence")
                                .addPage(headlineTextGizmoPage("esoteric_reaping.rotting_essence", item(Materials.ROTTING_ESSENCE)))
                ))
                .addRightBookmark(new EntryBookmark(item(Materials.EERIE_WEAVE),
                        BookEntry.create("esoteric_reaping.eerie_weave")
                                .addPage(headlineTextGizmoPage("esoteric_reaping.eerie_weave", item(Materials.EERIE_WEAVE)))
                ))
                .addRightBookmark(new EntryBookmark(item(Materials.WARP_FLUX),
                        BookEntry.create("esoteric_reaping.warp_flux")
                                .addPage(headlineTextGizmoPage("esoteric_reaping.warp_flux", item(Materials.WARP_FLUX)))
                ))

                .addLeftBookmark(new EntryBookmark(creatureCores,
                        BookEntry.create("common_reagents.core_keeping")
                                .addPage(headlineTextGizmoPage("common_reagents.core_keeping", creatureCores))
                                .addPage(textPage("common_reagents.core_keeping.2"))
                                .addPage(textPage("common_reagents.core_keeping.3"))
                                .addPage(textPage("common_reagents.core_keeping.4"))
                                .addRightBookmark(new EntryBookmark(item(Materials.WIND_NUCLEUS),
                                        BookEntry.create("common_reagents.core_keeping.wind_nucleus")
                                                .addPage(headlineTextGizmoPage("common_reagents.core_keeping.wind_nucleus", item(Materials.WIND_NUCLEUS)))
                                ))
                                .addRightBookmark(new EntryBookmark(item(Materials.PYRE_NUCLEUS),
                                        BookEntry.create("common_reagents.core_keeping.pyre_nucleus")
                                                .addPage(headlineTextGizmoPage("common_reagents.core_keeping.pyre_nucleus", item(Materials.PYRE_NUCLEUS)))
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
                .addPage(HeadlineSpiritPage.spirit(translationKey, symbol))
                .addPage(TextPage.textPage(translationKey + ".2"));
    }
}