package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.*;
import net.minecraft.world.item.*;

import java.util.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class RuneWorkingEntries {

    public static void setupEntries(List<BookEntry> entries) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        entries.add(new BookEntry<>(
                "runeworking", -10, 8)
                .setWidgetConfig(w -> w.setIcon(RUNIC_BROOCH))
                .addPage(new HeadlineTextPage("runeworking", "runeworking.1"))
                .addPage(new TextPage("runeworking.2"))
                .addPage(new TextPage("runeworking.3"))
                .addPage(SpiritInfusionPage.fromOutput(RUNIC_WORKBENCH.get()))
                .addPage(new TextPage("runeworking.4"))
                .addPage(CraftingBookPage.broochPage(RUNIC_BROOCH.get(), HALLOWED_GOLD_INGOT.get(), BLOCK_OF_HALLOWED_GOLD.get()))
        );

        entries.add(new BookEntry<>(
                "rune_of_idle_restoration", -11, 7)
                .setWidgetConfig(w -> w.setIcon(RUNE_OF_IDLE_RESTORATION))
                .addPage(new HeadlineTextPage("rune_of_idle_restoration", "rune_of_idle_restoration.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNE_OF_IDLE_RESTORATION.get()))
        );

        entries.add(new BookEntry<>(
                "rune_of_culling", -12, 7)
                .setWidgetConfig(w -> w.setIcon(RUNE_OF_CULLING))
                .addPage(new HeadlineTextPage("rune_of_culling", "rune_of_culling.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNE_OF_CULLING.get()))
        );

        entries.add(new BookEntry<>(
                "rune_of_dexterity", -13, 8)
                .setWidgetConfig(w -> w.setIcon(RUNE_OF_DEXTERITY))
                .addPage(new HeadlineTextPage("rune_of_dexterity", "rune_of_dexterity.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNE_OF_DEXTERITY.get()))
        );

        entries.add(new BookEntry<>(
                "rune_of_haste", -12, 8)
                .setWidgetConfig(w -> w.setIcon(RUNE_OF_HASTE))
                .addPage(new HeadlineTextPage("rune_of_haste", "rune_of_haste.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNE_OF_HASTE.get()))
        );

        entries.add(new BookEntry<>(
                "rune_of_aliment_cleansing", -12, 9)
                .setWidgetConfig(w -> w.setIcon(RUNE_OF_ALIMENT_CLEANSING))
                .addPage(new HeadlineTextPage("rune_of_aliment_cleansing", "rune_of_aliment_cleansing.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNE_OF_ALIMENT_CLEANSING.get()))
        );

        entries.add(new BookEntry<>(
                "rune_of_reactive_shielding", -13, 9)
                .setWidgetConfig(w -> w.setIcon(RUNE_OF_REACTIVE_SHIELDING))
                .addPage(new HeadlineTextPage("rune_of_reactive_shielding", "rune_of_reactive_shielding.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNE_OF_REACTIVE_SHIELDING.get()))
        );

        entries.add(new BookEntry<>(
                "rune_of_reinforcement", -13, 10)
                .setWidgetConfig(w -> w.setIcon(RUNE_OF_REINFORCEMENT))
                .addPage(new HeadlineTextPage("rune_of_reinforcement", "rune_of_reinforcement.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNE_OF_REINFORCEMENT.get()))
        );

        entries.add(new BookEntry<>(
                "rune_of_volatile_distortion", -14, 10)
                .setWidgetConfig(w -> w.setIcon(RUNE_OF_VOLATILE_DISTORTION))
                .addPage(new HeadlineTextPage("rune_of_volatile_distortion", "rune_of_volatile_distortion.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNE_OF_VOLATILE_DISTORTION.get()))
        );
    }

}