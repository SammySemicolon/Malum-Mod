package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.item.*;

import java.util.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.RITUAL_PLINTH;

public class RitualEntries {


    public static void setupEntries(List<BookEntry> entries) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        entries.add(new BookEntry<>(
                "rituals", 0, 17)
                .setWidgetConfig(w -> w.setIcon(RITUAL_PLINTH).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
        );

        entries.add(new BookEntry<>(
                "ritual_of_grotesque_expulsion", 0, 19)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_GROTESQUE_EXPULSION, "grotesque_expulsion_ritual"))
        );

        entries.add(new BookEntry<>(
                "ritual_of_idle_mending", 0, 15)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_IDLE_MENDING, "idle_mending_ritual"))
        );

        entries.add(new BookEntry<>(
                "ritual_of_manabound_enhancement", -2, 17)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_MANABOUND_ENHANCEMENT, "manabound_enhancement_ritual"))
        );

        entries.add(new BookEntry<>(
                "ritual_of_hexing_transmission", 2, 17)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_HEXING_TRANSMISSION, "hexing_transmission_ritual"))
        );

        entries.add(new BookEntry<>(
                "ritual_of_warped_time", 1, 18)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_WARPED_TIME, "warped_time_ritual"))
        );

        entries.add(new BookEntry<>(
                "ritual_of_aqueous_something", -1, 16)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
        );

        entries.add(new BookEntry<>(
                "ritual_of_cthonic_conversion", -1, 18)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_CTHONIC_CONVERSION, "cthonic_conversion_ritual"))
        );

        entries.add(new BookEntry<>(
                "ritual_of_earthen_something", 1, 16)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
        );
    }
}
