package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.BookWidgetStyle;
import com.sammy.malum.client.screen.codex.pages.recipe.RuneworkingPage;
import com.sammy.malum.client.screen.codex.pages.recipe.SpiritInfusionPage;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.CraftingPage;
import com.sammy.malum.client.screen.codex.pages.text.HeadlineTextPage;
import com.sammy.malum.client.screen.codex.pages.text.TextPage;
import com.sammy.malum.client.screen.codex.screens.ArcanaProgressionScreen;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class RuneWorkingEntries {

    public static void setupEntries(ArcanaProgressionScreen screen) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        screen.addEntry("runeworking", -11, 8, b -> b
                .configureWidget(w -> w.setIcon(RUNIC_WORKBENCH).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage("runeworking", "runeworking.1"))
                .addPage(new TextPage("runeworking.2"))
                .addPage(new TextPage("runeworking.3"))
                .addPage(SpiritInfusionPage.fromOutput(RUNIC_WORKBENCH.get()))
                .addPage(SpiritInfusionPage.fromOutput(TAINTED_ROCK_TABLET.get()))
        );

        screen.addEntry("runic_brooch", -10, 9, b -> b
                .configureWidget(w -> w.setIcon(RUNIC_BROOCH))
                .addPage(new HeadlineTextPage("runic_brooch", "runic_brooch.1"))
                .addPage(CraftingPage.broochPage(RUNIC_BROOCH.get(), HALLOWED_GOLD_INGOT.get(), BLOCK_OF_HALLOWED_GOLD.get()))
        );

        screen.addEntry("elaborate_brooch", -9, 10, b -> b
                .configureWidget(w -> w.setIcon(ELABORATE_BROOCH))
                .addPage(new HeadlineTextPage("elaborate_brooch", "elaborate_brooch.1"))
                .addPage(CraftingPage.broochPage(ELABORATE_BROOCH.get(), SOUL_STAINED_STEEL_INGOT.get(), BLOCK_OF_SOUL_STAINED_STEEL.get()))
        );

        screen.addEntry("glass_brooch", -11, 10, b -> b
                .configureWidget(w -> w.setIcon(GLASS_BROOCH))
                .addPage(new HeadlineTextPage("glass_brooch", "glass_brooch.1"))
                .addPage(SpiritInfusionPage.fromOutput(GLASS_BROOCH.get()))
        );

        screen.addEntry("gluttonous_brooch", -10, 11, b -> b
                .configureWidget(w -> w.setIcon(GLUTTONOUS_BROOCH))
                .addPage(new HeadlineTextPage("gluttonous_brooch", "gluttonous_brooch.1"))
                .addPage(SpiritInfusionPage.fromOutput(GLUTTONOUS_BROOCH.get()))
        );

        screen.addEntry("rune_of_idle_restoration", -12, 7, b -> b
                .configureWidget(w -> w.setIcon(RUNE_OF_IDLE_RESTORATION))
                .addPage(new HeadlineTextPage("rune_of_idle_restoration", "rune_of_idle_restoration.1"))
                .addPage(RuneworkingPage.fromOutput(RUNE_OF_IDLE_RESTORATION.get()))
        );

        screen.addEntry("rune_of_culling", -13, 7, b -> b
                .configureWidget(w -> w.setIcon(RUNE_OF_CULLING))
                .addPage(new HeadlineTextPage("rune_of_culling", "rune_of_culling.1"))
                .addPage(RuneworkingPage.fromOutput(RUNE_OF_CULLING.get()))
        );

        screen.addEntry("rune_of_dexterity", -14, 8, b -> b
                .configureWidget(w -> w.setIcon(RUNE_OF_DEXTERITY))
                .addPage(new HeadlineTextPage("rune_of_dexterity", "rune_of_dexterity.1"))
                .addPage(RuneworkingPage.fromOutput(RUNE_OF_DEXTERITY.get()))
        );

        screen.addEntry("rune_of_fervor", -13, 8, b -> b
                .configureWidget(w -> w.setIcon(RUNE_OF_FERVOR))
                .addPage(new HeadlineTextPage("rune_of_fervor", "rune_of_fervor.1"))
                .addPage(RuneworkingPage.fromOutput(RUNE_OF_FERVOR.get()))
        );

        screen.addEntry("rune_of_aliment_cleansing", -13, 9, b -> b
                .configureWidget(w -> w.setIcon(RUNE_OF_ALIMENT_CLEANSING))
                .addPage(new HeadlineTextPage("rune_of_aliment_cleansing", "rune_of_aliment_cleansing.1"))
                .addPage(RuneworkingPage.fromOutput(RUNE_OF_ALIMENT_CLEANSING.get()))
        );

        screen.addEntry("rune_of_reactive_shielding", -14, 9, b -> b
                .configureWidget(w -> w.setIcon(RUNE_OF_REACTIVE_SHIELDING))
                .addPage(new HeadlineTextPage("rune_of_reactive_shielding", "rune_of_reactive_shielding.1"))
                .addPage(RuneworkingPage.fromOutput(RUNE_OF_REACTIVE_SHIELDING.get()))
        );

        screen.addEntry("rune_of_reinforcement", -14, 10, b -> b
                .configureWidget(w -> w.setIcon(RUNE_OF_REINFORCEMENT))
                .addPage(new HeadlineTextPage("rune_of_reinforcement", "rune_of_reinforcement.1"))
                .addPage(RuneworkingPage.fromOutput(RUNE_OF_REINFORCEMENT.get()))
        );

        screen.addEntry("rune_of_volatile_distortion", -15, 10, b -> b
                .configureWidget(w -> w.setIcon(RUNE_OF_VOLATILE_DISTORTION))
                .addPage(new HeadlineTextPage("rune_of_volatile_distortion", "rune_of_volatile_distortion.1"))
                .addPage(RuneworkingPage.fromOutput(RUNE_OF_VOLATILE_DISTORTION.get()))
        );
    }

}
