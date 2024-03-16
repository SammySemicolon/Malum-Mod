package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.*;
import net.minecraft.world.item.*;

import java.util.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class AugmentationEntries {

    public static void setupEntries(List<BookEntry> entries) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        entries.add(new BookEntry<>(
                "crucible_augmentation", 11, 8)
                .setWidgetConfig(w -> w.setIcon(TUNING_FORK).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage("crucible_augmentation", "crucible_augmentation.1"))
                .addPage(new TextPage("crucible_augmentation.2"))
                .addPage(new TextPage("crucible_augmentation.3"))
                .addPage(SpiritInfusionPage.fromOutput(TUNING_FORK.get()))
        );

        entries.add(new BookEntry<>(
                "mending_diffuser", 12, 7)
                .setWidgetConfig(w -> w.setIcon(MENDING_DIFFUSER))
                .addPage(new HeadlineTextPage("mending_diffuser", "mending_diffuser.1"))
                .addPage(SpiritInfusionPage.fromOutput(MENDING_DIFFUSER.get()))
        );

        entries.add(new BookEntry<>(
                "impurity_stabilizer", 13, 7)
                .setWidgetConfig(w -> w.setIcon(IMPURITY_STABILIZER))
                .addPage(new HeadlineTextPage("impurity_stabilizer", "impurity_stabilizer.1"))
                .addPage(SpiritInfusionPage.fromOutput(IMPURITY_STABILIZER.get()))
        );

        entries.add(new BookEntry<>(
                "accelerating_inlay", 14, 8)
                .setWidgetConfig(w -> w.setIcon(ACCELERATING_INLAY))
                .addPage(new HeadlineTextPage("accelerating_inlay", "accelerating_inlay.1"))
                .addPage(SpiritInfusionPage.fromOutput(ACCELERATING_INLAY.get()))
        );

        entries.add(new BookEntry<>(
                "blazing_diode", 13, 8)
                .setWidgetConfig(w -> w.setIcon(BLAZING_DIODE))
                .addPage(new HeadlineTextPage("blazing_diode", "blazing_diode.1"))
                .addPage(SpiritInfusionPage.fromOutput(BLAZING_DIODE.get()))
        );

        entries.add(new BookEntry<>(
                "prismatic_focus_lens", 13, 9)
                .setWidgetConfig(w -> w.setIcon(PRISMATIC_FOCUS_LENS))
                .addPage(new HeadlineTextPage("prismatic_focus_lens", "prismatic_focus_lens.1"))
                .addPage(SpiritInfusionPage.fromOutput(PRISMATIC_FOCUS_LENS.get()))
        );

        entries.add(new BookEntry<>(
                "intricate_assembly", 14, 9)
                .setWidgetConfig(w -> w.setIcon(INTRICATE_ASSEMBLY))
                .addPage(new HeadlineTextPage("intricate_assembly", "intricate_assembly.1"))
                .addPage(SpiritInfusionPage.fromOutput(INTRICATE_ASSEMBLY.get()))
        );

        entries.add(new BookEntry<>(
                "shielding_apparatus", 14, 10)
                .setWidgetConfig(w -> w.setIcon(SHIELDING_APPARATUS))
                .addPage(new HeadlineTextPage("shielding_apparatus", "shielding_apparatus.1"))
                .addPage(SpiritInfusionPage.fromOutput(SHIELDING_APPARATUS.get()))
        );

        entries.add(new BookEntry<>(
                "warping_engine", 15, 10)
                .setWidgetConfig(w -> w.setIcon(WARPING_ENGINE))
                .addPage(new HeadlineTextPage("warping_engine", "warping_engine.1"))
                .addPage(new TextPage("warping_engine.2"))
                .addPage(SpiritInfusionPage.fromOutput(WARPING_ENGINE.get()))
        );
    }
}
