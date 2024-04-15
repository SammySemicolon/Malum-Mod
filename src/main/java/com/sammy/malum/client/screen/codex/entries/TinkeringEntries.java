package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.pages.recipe.*;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.*;
import com.sammy.malum.client.screen.codex.pages.text.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.world.item.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static net.minecraft.world.item.Items.*;

public class TinkeringEntries {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void setupEntries(ArcanaProgressionScreen screen) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        screen.addEntry("spirit_metals", -3, 6, b -> b
                .setWidgetConfig(w -> w.setIcon(SOUL_STAINED_STEEL_INGOT))
                .addPage(new HeadlineTextItemPage<>("spirit_metals.soulstained_steel", "spirit_metals.soulstained_steel.1", SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new TextPage<>("spirit_metals.soulstained_steel.2"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new CyclingPage(
                        CraftingPage.toolPage(SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingPage.toolPage(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingPage.toolPage(SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingPage.toolPage(SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingPage.toolPage(SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingPage.knifePage(SOUL_STAINED_STEEL_KNIFE.get(), SOUL_STAINED_STEEL_INGOT.get())
                ))
                .addPage(new HeadlineTextItemPage<>("spirit_metals.hallowed_gold", "spirit_metals.hallowed_gold.1", HALLOWED_GOLD_INGOT.get()))
                .addPage(new TextPage<>("spirit_metals.hallowed_gold.2"))
                .addPage(SpiritInfusionPage.fromOutput(HALLOWED_GOLD_INGOT.get()))
                .addPage(new HeadlineTextPage<>("spirit_metals.hallowed_gold.spirit_jar", "spirit_metals.hallowed_gold.spirit_jar.1"))
                .addPage(new CraftingPage<>(SPIRIT_JAR.get(), GLASS_PANE, HALLOWED_GOLD_INGOT.get(), GLASS_PANE, GLASS_PANE, EMPTY, GLASS_PANE, GLASS_PANE, GLASS_PANE, GLASS_PANE))
        );

        screen.addEntry("soulstained_scythe", -4, 5, b -> b
                .setWidgetConfig(w -> w.setIcon(SOUL_STAINED_STEEL_SCYTHE))
                .addPage(new HeadlineTextPage<>("soulstained_scythe", "soulstained_scythe.1"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_SCYTHE.get()))
        );

        screen.addEntry("soulstained_armor", -4, 7, b -> b
                .setWidgetConfig(w -> w.setIcon(SOUL_STAINED_STEEL_HELMET))
                .addPage(new HeadlineTextPage<>("soulstained_armor", "soulstained_armor.1"))
                .addPage(new TextPage<>("soulstained_armor.2"))
                .addPage(new TextPage<>("soulstained_armor.3"))
                .addPage(new CyclingPage(
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_HELMET.get()),
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_CHESTPLATE.get()),
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_LEGGINGS.get()),
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_BOOTS.get())
                ))
                .addPage(new CraftingPage<>(new ItemStack(SOUL_STAINED_STEEL_PLATING.get(), 2), EMPTY, SOUL_STAINED_STEEL_NUGGET.get(), EMPTY, SOUL_STAINED_STEEL_NUGGET.get(), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_NUGGET.get(), EMPTY, SOUL_STAINED_STEEL_NUGGET.get(), EMPTY))
        );

        screen.addEntry("spirit_trinkets", -5, 6, b -> b
                .setWidgetConfig(w -> w.setIcon(ORNATE_RING))
                .addPage(new HeadlineTextPage<>("spirit_trinkets", "spirit_trinkets.1"))
                .addPage(new TextPage<>("spirit_trinkets.2"))
                .addPage(CraftingPage.ringPage(GILDED_RING.get(), HALLOWED_GOLD_INGOT.get()))
                .addPage(new CraftingPage<>(GILDED_BELT.get(), LEATHER, LEATHER, LEATHER, HALLOWED_GOLD_INGOT.get(), PROCESSED_SOULSTONE.get(), HALLOWED_GOLD_INGOT.get(), EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY))
                .addPage(CraftingPage.ringPage(ORNATE_RING.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new CraftingPage<>(ORNATE_NECKLACE.get(), EMPTY, STRING, EMPTY, STRING, EMPTY, STRING, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY))
        );

        screen.addEntry("reactive_trinkets", -7, 6, b -> b
                .setWidgetConfig(w -> w.setIcon(RING_OF_CURATIVE_TALENT))
                .addPage(new HeadlineTextPage<>("reactive_trinkets.ring_of_curative_talent", "reactive_trinkets.ring_of_curative_talent.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_CURATIVE_TALENT.get()))
                .addPage(new HeadlineTextPage<>("reactive_trinkets.ring_of_alchemical_mastery", "reactive_trinkets.ring_of_alchemical_mastery.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ALCHEMICAL_MASTERY.get()))
                .addPage(new HeadlineTextPage<>("reactive_trinkets.ring_of_prowess", "reactive_trinkets.ring_of_prowess.1"))
                .addPage(new TextPage<>("reactive_trinkets.ring_of_prowess.2"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ARCANE_PROWESS.get()))
        );

        screen.addEntry("ring_of_esoteric_spoils", -9, 5, b -> b
                .setWidgetConfig(w -> w.setIcon(RING_OF_ESOTERIC_SPOILS))
                .addPage(new HeadlineTextPage<>("ring_of_esoteric_spoils", "ring_of_esoteric_spoils.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ESOTERIC_SPOILS.get()))
        );

        screen.addEntry("belt_of_the_starved",-8, 7, b -> b
                .setWidgetConfig(w -> w.setIcon(BELT_OF_THE_STARVED))
                .addPage(new HeadlineTextPage<>("belt_of_the_starved", "belt_of_the_starved.1"))
                .addPage(new TextPage<>("belt_of_the_starved.2"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_STARVED.get()))
                .addPage(new HeadlineTextPage<>("belt_of_the_starved.ring_of_desperate_voracity", "belt_of_the_starved.ring_of_desperate_voracity.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_DESPERATE_VORACITY.get()))
        );

        screen.addEntry("necklace_of_the_narrow_edge", -7, 8, b -> b
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_THE_NARROW_EDGE))
                .addPage(new HeadlineTextPage<>("necklace_of_the_narrow_edge", "necklace_of_the_narrow_edge.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_NARROW_EDGE.get()))
        );

        screen.addEntry("belt_of_the_prospector", -6, 5, b -> b
                .setWidgetConfig(w -> w.setIcon(BELT_OF_THE_PROSPECTOR))
                .addPage(new HeadlineTextPage<>("belt_of_the_prospector", "belt_of_the_prospector.1"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_PROSPECTOR.get()))
                .addPage(new HeadlineTextPage<>("belt_of_the_prospector.ring_of_the_hoarder", "belt_of_the_prospector.ring_of_the_hoarder.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_THE_HOARDER.get()))
        );

        screen.addEntry("necklace_of_blissful_harmony", -7, 4, b -> b
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_BLISSFUL_HARMONY))
                .addPage(new HeadlineTextPage<>("necklace_of_blissful_harmony", "necklace_of_blissful_harmony.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_BLISSFUL_HARMONY.get()))
                .addPage(new TextPage<>("necklace_of_blissful_harmony.2"))
        );
    }
}