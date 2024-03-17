package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.item.*;

import java.util.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static net.minecraft.world.item.Items.*;

public class TotemMagicEntries {


    public static void setupEntries(List<BookEntry> entries) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        entries.add(new BookEntry<>(
                "totem_magic", 0, 9)
                .setWidgetConfig(w -> w.setIcon(RUNEWOOD_TOTEM_BASE).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("totem_magic", "totem_magic.1", RUNEWOOD_TOTEM_BASE.get()))
                .addPage(new TextPage("totem_magic.2"))
                .addPage(new TextPage("totem_magic.3"))
                .addPage(new TextPage("totem_magic.4"))
                .addPage(new TextPage("totem_magic.5"))
                .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_TOTEM_BASE.get()))
        );

        entries.add(new BookEntry<>(
                "managing_totems", 0, 10)
                .setWidgetConfig(w -> w.setIcon(TOTEMIC_STAFF).setStyle(BookWidgetStyle.SMALL_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("totemic_staff", "totemic_staff.1", TOTEMIC_STAFF.get()))
                .addPage(new TextPage("totemic_staff.2"))
                .addPage(new TextPage("totemic_staff.3"))
                .addPage(new CraftingBookPage(TOTEMIC_STAFF.get(),
                        EMPTY, EMPTY, RUNEWOOD_PLANKS.get(),
                        EMPTY, STICK, EMPTY,
                        STICK, EMPTY, EMPTY
                ))
        );

        entries.add(new BookEntry<ArcanaProgressionScreen>(
                "sacred_rite", -2, 10)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.SACRED_RITE, "sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.SACRED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "greater_sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        entries.add(new BookEntry<>(
                "corrupt_sacred_rite", -3, 10)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.SACRED_RITE, "corrupt_sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.SACRED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "corrupt_greater_sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        entries.add(new BookEntry<>(
                "infernal_rite", -3, 11)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.INFERNAL_RITE, "infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.INFERNAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "greater_infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        entries.add(new BookEntry<>(
                "corrupt_infernal_rite", -4, 11)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.INFERNAL_RITE, "corrupt_infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.INFERNAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "corrupt_greater_infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        entries.add(new BookEntry<>(
                "earthen_rite", -3, 12)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.EARTHEN_RITE, "earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.EARTHEN_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "greater_earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        entries.add(new BookEntry<>(
                "corrupt_earthen_rite", -4, 12)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.EARTHEN_RITE, "corrupt_earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.EARTHEN_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "corrupt_greater_earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        entries.add(new BookEntry<>(
                "wicked_rite", 2, 10)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.WICKED_RITE, "wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.WICKED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "greater_wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        entries.add(new BookEntry<>(
                "corrupt_wicked_rite", 3, 10)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.WICKED_RITE, "corrupt_wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.WICKED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "corrupt_greater_wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        entries.add(new BookEntry<>(
                "aerial_rite", 3, 11)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AERIAL_RITE, "aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AERIAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "greater_aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        entries.add(new BookEntry<>(
                "corrupt_aerial_rite", 4, 11)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AERIAL_RITE, "corrupt_aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AERIAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "corrupt_greater_aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        entries.add(new BookEntry<>(
                "aqueous_rite", 3, 12)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AQUEOUS_RITE, "aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AQUEOUS_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "greater_aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        entries.add(new BookEntry<>(
                "corrupt_aqueous_rite", 4, 12)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AQUEOUS_RITE, "corrupt_aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AQUEOUS_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "corrupt_greater_aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        entries.add(new BookEntry<>(
                "arcane_rite", 0, 11)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new HeadlineTextPage("arcane_rite", "arcane_rite.description.1"))
                .addPage(new TextPage("arcane_rite.description.2"))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ARCANE_RITE, "arcane_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ARCANE_RITE))
                .addPage(new TextPage("arcane_rite.description.3"))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ARCANE_RITE, "corrupt_arcane_rite"))
                .addPage(SpiritTransmutationRecipePage.fromInput("arcane_rite.soulwood", RUNEWOOD_SAPLING.get()))
                .addPage(new TextPage("arcane_rite.description.4"))
                .addPage(SpiritInfusionPage.fromOutput(SOULWOOD_TOTEM_BASE.get()))
        );

        entries.add(new BookEntry<>(
                "blight", -1, 12)
                .setWidgetConfig(w -> w.setIcon(BLIGHTED_GUNK).setStyle(BookWidgetStyle.SMALL_SOULWOOD))
                .addPage(new HeadlineTextPage("blight.intro", "blight.intro.1"))
                .addPage(new HeadlineTextPage("blight.composition", "blight.composition.1"))
                .addPage(new HeadlineTextPage("blight.spread", "blight.spread.1"))
                .addPage(new HeadlineTextPage("blight.arcane_rite", "blight.arcane_rite.1"))
        );

        entries.add(new BookEntry<>(
                "soulwood", 1, 12)
                .setWidgetConfig(w -> w.setIcon(SOULWOOD_GROWTH).setStyle(BookWidgetStyle.SMALL_SOULWOOD))
                .addPage(new HeadlineTextPage("soulwood.intro", "soulwood.intro.1"))
                .addPage(new HeadlineTextPage("soulwood.bonemeal", "soulwood.bonemeal.1"))
                .addPage(new HeadlineTextPage("soulwood.color", "soulwood.color.1"))
                .addPage(new HeadlineTextPage("soulwood.blight", "soulwood.blight.1"))
                .addPage(new HeadlineTextPage("soulwood.sap", "soulwood.sap.1"))
        );
        entries.add(new BookEntry<>(
                "transmutation", 0, 13)
                .setWidgetConfig(w -> w.setIcon(SOUL_SAND).setStyle(BookWidgetStyle.SMALL_SOULWOOD))
                .addPage(new HeadlineTextPage("transmutation", "transmutation.intro.1"))
                .addPage(new TextPage("transmutation.intro.2"))
                .addPage(new SpiritTransmutationRecipeTreePage("transmutation.stone", STONE))
                .addPage(new SpiritTransmutationRecipeTreePage("transmutation.deepslate", DEEPSLATE))
                .addPage(new SpiritTransmutationRecipeTreePage("transmutation.smooth_basalt", SMOOTH_BASALT))
        );
    }
}
