package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.*;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.*;

import java.util.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static net.minecraft.world.item.Items.*;

public class TinkeringEntries {

    public static void setupEntries(List<BookEntry> entries) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        entries.add(new BookEntry<>(
                "spirit_metals", -3, 6)
                .setWidgetConfig(w -> w.setIcon(SOUL_STAINED_STEEL_INGOT))
                .addPage(new HeadlineTextItemPage("spirit_metals.soulstained_steel", "spirit_metals.soulstained_steel.1", SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new TextPage("spirit_metals.soulstained_steel.2"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new CyclingPage(
                        CraftingBookPage.toolPage(SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingBookPage.toolPage(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingBookPage.toolPage(SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingBookPage.toolPage(SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingBookPage.toolPage(SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        new CraftingBookPage(SOUL_STAINED_STEEL_KNIFE.get(), EMPTY, EMPTY, EMPTY, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY, STICK) {
                            @Override
                            public boolean isValid() {
                                return ModList.get().isLoaded("farmersdelight");
                            }
                        }
                ))
                //TODO: the above is a temporary bandaid, implement a proper thing for this once book page/entry refactor happens
                .addPage(new HeadlineTextItemPage("spirit_metals.hallowed_gold", "spirit_metals.hallowed_gold.1", HALLOWED_GOLD_INGOT.get()))
                .addPage(new TextPage("spirit_metals.hallowed_gold.2"))
                .addPage(SpiritInfusionPage.fromOutput(HALLOWED_GOLD_INGOT.get()))
                .addPage(new HeadlineTextPage("spirit_metals.hallowed_gold.spirit_jar", "spirit_metals.hallowed_gold.spirit_jar.1"))
                .addPage(new CraftingBookPage(SPIRIT_JAR.get(), GLASS_PANE, HALLOWED_GOLD_INGOT.get(), GLASS_PANE, GLASS_PANE, EMPTY, GLASS_PANE, GLASS_PANE, GLASS_PANE, GLASS_PANE))
        );

        entries.add(new BookEntry<>(
                "soulstained_scythe", -4, 5)
                .setWidgetConfig(w -> w.setIcon(SOUL_STAINED_STEEL_SCYTHE))
                .addPage(new HeadlineTextPage("soulstained_scythe", "soulstained_scythe.1"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_SCYTHE.get()))
        );

        entries.add(new BookEntry<>(
                "soulstained_armor", -4, 7)
                .setWidgetConfig(w -> w.setIcon(SOUL_STAINED_STEEL_HELMET))
                .addPage(new HeadlineTextPage("soulstained_armor", "soulstained_armor.1"))
                .addPage(new TextPage("soulstained_armor.2"))
                .addPage(new TextPage("soulstained_armor.3"))
                .addPage(new CyclingPage(
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_HELMET.get()),
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_CHESTPLATE.get()),
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_LEGGINGS.get()),
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_BOOTS.get())
                ))
                .addPage(new CraftingBookPage(new ItemStack(SOUL_STAINED_STEEL_PLATING.get(), 2), EMPTY, SOUL_STAINED_STEEL_NUGGET.get(), EMPTY, SOUL_STAINED_STEEL_NUGGET.get(), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_NUGGET.get(), EMPTY, SOUL_STAINED_STEEL_NUGGET.get(), EMPTY))
        );

//        ENTRIES.add(new BookEntry<>(
//                "soul_ward", -3, 8)
//                .setWidgetSupplier((e, x, y) -> new IconObject(e, malumPath("textures/gui/book/icons/soul_ward.png"), x, y))
//        );

        entries.add(new BookEntry<>(
                "spirit_trinkets", -5, 6)
                .setWidgetConfig(w -> w.setIcon(ORNATE_RING))
                .addPage(new HeadlineTextPage("spirit_trinkets", "spirit_trinkets.1"))
                .addPage(new TextPage("spirit_trinkets.2"))
                .addPage(CraftingBookPage.ringPage(GILDED_RING.get(), HALLOWED_GOLD_INGOT.get()))
                .addPage(new CraftingBookPage(GILDED_BELT.get(), LEATHER, LEATHER, LEATHER, HALLOWED_GOLD_INGOT.get(), PROCESSED_SOULSTONE.get(), HALLOWED_GOLD_INGOT.get(), EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY))
                .addPage(CraftingBookPage.ringPage(ORNATE_RING.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new CraftingBookPage(ORNATE_NECKLACE.get(), EMPTY, STRING, EMPTY, STRING, EMPTY, STRING, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY))
        );

        entries.add(new BookEntry<>(
                "reactive_trinkets", -7, 6)
                .setWidgetConfig(w -> w.setIcon(RING_OF_CURATIVE_TALENT))
                .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_curative_talent", "reactive_trinkets.ring_of_curative_talent.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_CURATIVE_TALENT.get()))
                .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_alchemical_mastery", "reactive_trinkets.ring_of_alchemical_mastery.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ALCHEMICAL_MASTERY.get()))
                .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_prowess", "reactive_trinkets.ring_of_prowess.1"))
                .addPage(new TextPage("reactive_trinkets.ring_of_prowess.2"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ARCANE_PROWESS.get()))
        );

        entries.add(new BookEntry<>(
                "ring_of_esoteric_spoils", -9, 5)
                .setWidgetConfig(w -> w.setIcon(RING_OF_ESOTERIC_SPOILS))
                .addPage(new HeadlineTextPage("ring_of_esoteric_spoils", "ring_of_esoteric_spoils.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ESOTERIC_SPOILS.get()))
        );

        entries.add(new BookEntry<>(
                "belt_of_the_starved",-8, 7)
                .setWidgetConfig(w -> w.setIcon(BELT_OF_THE_STARVED))
                .addPage(new HeadlineTextPage("belt_of_the_starved", "belt_of_the_starved.1"))
                .addPage(new TextPage("belt_of_the_starved.2"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_STARVED.get()))
                .addPage(new HeadlineTextPage("belt_of_the_starved.ring_of_desperate_voracity", "belt_of_the_starved.ring_of_desperate_voracity.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_DESPERATE_VORACITY.get()))
        );

        entries.add(new BookEntry<>(
                "necklace_of_the_narrow_edge", -7, 8)
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_THE_NARROW_EDGE))
                .addPage(new HeadlineTextPage("necklace_of_the_narrow_edge", "necklace_of_the_narrow_edge.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_NARROW_EDGE.get()))
        );

        entries.add(new BookEntry<>(
                "belt_of_the_prospector", -6, 5)
                .setWidgetConfig(w -> w.setIcon(BELT_OF_THE_PROSPECTOR))
                .addPage(new HeadlineTextPage("belt_of_the_prospector", "belt_of_the_prospector.1"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_PROSPECTOR.get()))
                .addPage(new HeadlineTextPage("belt_of_the_prospector.ring_of_the_hoarder", "belt_of_the_prospector.ring_of_the_hoarder.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_THE_HOARDER.get()))
        );

        entries.add(new BookEntry<>(
                "necklace_of_blissful_harmony", -7, 4)
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_BLISSFUL_HARMONY))
                .addPage(new HeadlineTextPage("necklace_of_blissful_harmony", "necklace_of_blissful_harmony.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_BLISSFUL_HARMONY.get()))
                .addPage(new TextPage("necklace_of_blissful_harmony.2"))
        );
    }
}