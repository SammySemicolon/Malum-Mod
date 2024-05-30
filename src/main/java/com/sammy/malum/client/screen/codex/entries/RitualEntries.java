package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.BookWidgetStyle;
import com.sammy.malum.client.screen.codex.objects.progression.RitualEntryObject;
import com.sammy.malum.client.screen.codex.pages.text.SpiritRitualTextPage;
import com.sammy.malum.client.screen.codex.screens.ArcanaProgressionScreen;
import com.sammy.malum.registry.common.RitualRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static com.sammy.malum.registry.common.item.ItemRegistry.RITUAL_PLINTH;

public class RitualEntries {


    public static void setupEntries(ArcanaProgressionScreen screen) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        screen.addEntry("rituals", 0, 17, b -> b
                .setWidgetConfig(w -> w.setIcon(RITUAL_PLINTH).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
        );

        screen.addEntry("ritual_of_grotesque_expulsion", 0, 19, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_GROTESQUE_EXPULSION, "grotesque_expulsion_ritual"))
        );

        screen.addEntry("ritual_of_idle_mending", 0, 15, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_IDLE_MENDING, "idle_mending_ritual"))
        );

        screen.addEntry("ritual_of_manabound_enhancement", -2, 17, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_MANABOUND_ENHANCEMENT, "manabound_enhancement_ritual"))
        );

        screen.addEntry("ritual_of_hexing_transmission", 2, 17, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_HEXING_TRANSMISSION, "hexing_transmission_ritual"))
        );

        screen.addEntry("ritual_of_warped_time", 1, 18, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_WARPED_TIME, "warped_time_ritual"))
        );

        screen.addEntry("ritual_of_marine_spoil", -1, 16, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_MARINE_SPOIL, "marine_spoil_ritual"))
        );

        screen.addEntry("ritual_of_cthonic_conversion", -1, 18, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_CTHONIC_EXCHANGE, "cthonic_conversion_ritual"))
        );

        screen.addEntry("ritual_of_terran_unearthing", 1, 16, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_TERRAN_UNEARTHING, "terran_unearthing_ritual"))
        );
    }
}
