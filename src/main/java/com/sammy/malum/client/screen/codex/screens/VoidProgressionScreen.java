package com.sammy.malum.client.screen.codex.screens;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.objects.progression.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.pages.recipe.*;
import com.sammy.malum.client.screen.codex.pages.text.*;
import com.sammy.malum.common.events.*;
import com.sammy.malum.common.item.codex.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.resources.*;
import net.minecraftforge.common.*;

import java.util.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class VoidProgressionScreen extends AbstractProgressionCodexScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = malumPath("textures/gui/book/void_background.png");

    public static VoidProgressionScreen screen;

    public static final List<PlacedBookEntry> VOID_ENTRIES = new ArrayList<>();

    protected VoidProgressionScreen() {
        super(SoundRegistry.ARCANA_SWEETENER_EVIL, 1024, 768);
        minecraft = Minecraft.getInstance();
        setupEntries();
        MinecraftForge.EVENT_BUS.post(new SetupMalumCodexEntriesEvent());
        setupObjects();
    }

    @Override
    public void renderBackground(PoseStack poseStack) {
        renderBackground(poseStack, BACKGROUND_TEXTURE, 0.2f, 0.2f);
    }

    @Override
    public Collection<PlacedBookEntry> getEntries() {
        return VOID_ENTRIES;
    }

    public static VoidProgressionScreen getScreenInstance() {
        if (screen == null) {
            screen = new VoidProgressionScreen();
        }
        return screen;
    }

    public static void openCodexViaItem() {
        getScreenInstance().openScreen(true);
        screen.playSweetenedSound(SoundRegistry.ARCANA_CODEX_OPEN, 1.25f);
    }

    public static void openCodexViaTransition() {
        getScreenInstance().openScreen(false);
        screen.faceObject(screen.bookObjectHandler.get(0));
        screen.playSound(SoundRegistry.ARCANA_TRANSITION_EVIL, 1.25f, 1f);
        screen.timesTransitioned++;
        screen.transitionTimer = screen.getTransitionDuration();
        EncyclopediaEsotericaItem.shouldOpenVoidCodex = true;
    }

    public void setupEntries() {
        addEntry("chronicles_of_the_soul", 0, 0, b -> b
                .setWidgetSupplier((e, x, y) -> new ScreenOpenerObject(e, x, y, ArcanaProgressionScreen::openCodexViaTransition, malumPath("textures/gui/book/icons/arcana_button.png"), 20, 20))
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_GRAND_SOULWOOD))
        );
        addEntry("void.the_weeping_well", 0, 1, b -> b
                .setWidgetConfig(w -> w.setIcon(ENCYCLOPEDIA_ESOTERICA).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
                .addPage(new HeadlineTextPage("void.the_weeping_well", "void.the_weeping_well.1"))
                .addPage(new TextPage("void.the_weeping_well.2"))
                .addPage(new TextPage("void.the_weeping_well.3"))
                .addPage(new TextPage("void.the_weeping_well.4"))
                .addPage(new TextPage("void.the_weeping_well.5"))
                .addPage(new TextPage("void.the_weeping_well.6"))
                .addPage(new TextPage("void.the_weeping_well.7"))
        );

        addEntry("void.material_study_soulstone", 0, 2, b -> b
                .setWidgetConfig(w -> w.setIcon(RAW_SOULSTONE).setStyle(BookWidgetStyle.SMALL_SOULWOOD))
                .addPage(new WeepingWellTextPage("void.material_study_soulstone", "void.material_study_soulstone.1", RAW_SOULSTONE.get()))
                .addPage(new TextPage("void.material_study_soulstone.2"))
        );

        addEntry("void.material_study_null_slate", -2, 3, b -> b
                .setWidgetConfig(w -> w.setIcon(NULL_SLATE).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new WeepingWellTextPage("void.material_study_null_slate", "void.material_study_null_slate.1", NULL_SLATE.get()))
                .addPage(new TextPage("void.material_study_null_slate.2"))
        );

        addEntry("void.material_study_mnemonic_fragment", -3, 4, b -> b
                .setWidgetConfig(w -> w.setIcon(MNEMONIC_FRAGMENT).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new WeepingWellTextPage("void.material_study_mnemonic_fragment", "void.material_study_mnemonic_fragment.1", MNEMONIC_FRAGMENT.get()))
                .addPage(new TextPage("void.material_study_mnemonic_fragment.2"))
        );

        addEntry("void.material_study_void_salts", 2, 3, b -> b
                .setWidgetConfig(w -> w.setIcon(VOID_SALTS).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new WeepingWellTextPage("void.material_study_void_salts", "void.material_study_void_salts.1", VOID_SALTS.get()))
                .addPage(new TextPage("void.material_study_void_salts.2"))
        );

        addEntry("void.material_study_auric_embers", 3, 4, b -> b
                .setWidgetConfig(w -> w.setIcon(AURIC_EMBERS).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new WeepingWellTextPage("void.material_study_auric_embers", "void.material_study_auric_embers.1", AURIC_EMBERS.get()))
                .addPage(new TextPage("void.material_study_auric_embers.2"))
        );

        addEntry("void.black_crystal", 0, 5, b -> b
                .setWidgetConfig(w -> w.setIcon(UMBRAL_SPIRIT).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
        );

        addEntry("void.umbral_arcana", -1, 6, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_SOULWOOD))
                .setWidgetSupplier((e, x, y) -> new IconObject(e, x, y, malumPath("textures/gui/book/icons/umbral_shard.png")))
        );

        addEntry("void.inverse_and_hybrid_arcana", 0, 7, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_SOULWOOD))
                .setWidgetSupplier((e, x, y) -> new IconObject(e, x, y, malumPath("textures/gui/book/icons/umbral_shard.png")))
        );

        addEntry("void.material_study_the_arcana", 1, 8, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_SOULWOOD))
                .setWidgetSupplier((e, x, y) -> new IconObject(e, x, y, malumPath("textures/gui/book/icons/umbral_shard.png")))
        );

        addEntry("void.staves_as_foci", 0, 9, b -> b
                .setWidgetConfig(w -> w.setIcon(MNEMONIC_HEX_STAFF).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
                .addReference(new EntryReference(RING_OF_THE_ENDLESS_WELL, BookEntry.build("void.staves_as_foci.ring_of_the_endless_well")))
        );


        addEntry("void.ring_of_growing_flesh", -3, 9, b -> b
                .setWidgetConfig(w -> w.setIcon(RING_OF_GROWING_FLESH).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.ring_of_growing_flesh", "void.ring_of_growing_flesh.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_GROWING_FLESH.get()))
        );
        addEntry("void.ring_of_gruesome_satiation", -4, 10, b -> b
                .setWidgetConfig(w -> w.setIcon(RING_OF_GRUESOME_SATIATION).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.ring_of_gruesome_satiation", "void.ring_of_gruesome_satiation.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_GRUESOME_SATIATION.get()))
        );
        addEntry("void.necklace_of_the_watcher", -3, 11, b -> b
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_THE_WATCHER).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.necklace_of_the_watcher", "void.necklace_of_the_watcher.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_WATCHER.get()))
        );
        addEntry("void.necklace_of_the_hidden_blade", -4, 12, b -> b
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_THE_HIDDEN_BLADE).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.necklace_of_the_hidden_blade", "void.necklace_of_the_hidden_blade.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_HIDDEN_BLADE.get()))
        );

//        addEntry("void.malignant_lead", 6, 4, b -> b
//                .setWidgetConfig(w -> w.setIcon(MALIGNANT_LEAD).setStyle(BookWidgetStyle.SOULWOOD))
//                .addPage(new WeepingWellTextPage("void.malignant_lead", "void.malignant_lead.1", MALIGNANT_LEAD.get()))
//                .addPage(new TextPage("void.malignant_lead.2"))
//        );
//


//        addEntry("void.something2", 4, 5, b -> b
//                .setWidgetConfig(w -> w.setIcon(BARRIER).setStyle(BookWidgetStyle.SOULWOOD))
//        );
//        addEntry("void.something3", 2, 5, b -> b
//                .setWidgetConfig(w -> w.setIcon(BARRIER).setStyle(BookWidgetStyle.SOULWOOD))
//        );
//
//        addEntry("void.runes", 3, 6, b -> b
//                .setWidgetConfig(w -> w.setIcon(RUNE_OF_THE_HERETIC).setStyle(BookWidgetStyle.SOULWOOD))
//                .addPage(new HeadlineTextPage("void.runes", "void.runes.1"))
//                .addPage(new EntrySelectorPage(item -> {
//                    final String translationKey = "void." + ForgeRegistries.ITEMS.getKey(item).getPath();
//                    return new EntryReference(item,
//                            BookEntry.build(translationKey)
//                                    .addPage(new HeadlineTextPage(translationKey))
//                                    .addPage(RuneworkingPage.fromOutput(item)));
//                },
//                        RUNE_OF_BOLSTERING.get(), RUNE_OF_SACRIFICIAL_EMPOWERMENT.get(), RUNE_OF_SPELL_MASTERY.get(), RUNE_OF_THE_HERETIC.get(),
//                        RUNE_OF_UNNATURAL_STAMINA.get(), RUNE_OF_TWINNED_DURATION.get(), RUNE_OF_TOUGHNESS.get(), RUNE_OF_IGNEOUS_SOLACE.get()))
//        );
//
//        addEntry("void.malignant_alloy", 6, 6, b -> b
//                .setWidgetConfig(w -> w.setIcon(MALIGNANT_ALLOY_INGOT).setStyle(BookWidgetStyle.SOULWOOD))
//        );
//
//        addEntry("void.malignant_scepter", 5, 7, b -> b
//                .setWidgetConfig(w -> w.setIcon(MALIGNANT_SCEPTER).setStyle(BookWidgetStyle.SOULWOOD))
//        );
//
//        addEntry("void.weight_of_worlds", 7, 7, b -> b
//                .setWidgetConfig(w -> w.setIcon(WEIGHT_OF_WORLDS).setStyle(BookWidgetStyle.SOULWOOD))
//        );
//
//        addEntry("void.malignant_stronghold_armor", 6, 8, b -> b
//                .setWidgetConfig(w -> w.setIcon(MALIGNANT_STRONGHOLD_HELMET).setStyle(BookWidgetStyle.SOULWOOD))
//        );
//
//
//        addEntry("void.anomalous_design", 0, 7, b -> b
//                .setWidgetConfig(w -> w.setIcon(ANOMALOUS_DESIGN).setStyle(BookWidgetStyle.SOULWOOD))
//                .addPage(new HeadlineTextItemPage("void.anomalous_design", "void.anomalous_design.1", ANOMALOUS_DESIGN.get()))
//                .addPage(SpiritInfusionPage.fromOutput(COMPLETE_DESIGN.get()))
//        );
//        addEntry("void.fused_consciousness", 0, 8, b -> b
//                .setWidgetConfig(w -> w.setIcon(FUSED_CONSCIOUSNESS).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
//                .addPage(new HeadlineTextItemPage("void.fused_consciousness", "void.fused_consciousness.1", FUSED_CONSCIOUSNESS.get()))
//        );
//        addEntry("void.belt_of_the_limitless", -2, 9, b -> b
//                .setWidgetConfig(w -> w.setIcon(BELT_OF_THE_LIMITLESS).setStyle(BookWidgetStyle.SOULWOOD))
//                .addPage(new HeadlineTextPage("void.belt_of_the_limitless", "void.belt_of_the_limitless.1"))
//                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_LIMITLESS.get()))
//        );
//        addEntry("void.stellar_mechanism", 2, 9, b -> b
//                .setWidgetConfig(w -> w.setIcon(STELLAR_MECHANISM).setStyle(BookWidgetStyle.SOULWOOD))
//                .addPage(new HeadlineTextPage("void.stellar_mechanism", "void.stellar_mechanism.1"))
//                .addPage(SpiritInfusionPage.fromOutput(STELLAR_MECHANISM.get()))
//        );
//        addEntry("void.staff_of_the_auric_flame", 0, 10, b -> b
//                .setWidgetConfig(w -> w.setIcon(STAFF_OF_THE_AURIC_FLAME).setStyle(BookWidgetStyle.SOULWOOD))
//                .addPage(new HeadlineTextPage("void.staff_of_the_auric_flame", "void.staff_of_the_auric_flame.1"))
//                .addPage(SpiritInfusionPage.fromOutput(STAFF_OF_THE_AURIC_FLAME.get()))
//        );
    }
}