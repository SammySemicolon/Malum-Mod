package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.*;
import net.minecraft.world.item.*;

import java.util.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static net.minecraft.world.item.Items.*;

public class ArtificeEntries {

    public static void setupEntries(List<BookEntry> entries) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        entries.add(new BookEntry<>(
                "spirit_stones", 3, 6)
                .setWidgetConfig(w -> w.setIcon(TAINTED_ROCK))
                .addPage(new HeadlineTextPage("spirit_stones.tainted_rock", "spirit_stones.tainted_rock.1"))
                .addPage(SpiritInfusionPage.fromOutput(TAINTED_ROCK.get()))
                .addPage(CraftingBookPage.itemPedestalPage(TAINTED_ROCK_ITEM_PEDESTAL.get(), TAINTED_ROCK.get(), TAINTED_ROCK_SLAB.get()))
                .addPage(CraftingBookPage.itemStandPage(TAINTED_ROCK_ITEM_STAND.get(), TAINTED_ROCK.get(), TAINTED_ROCK_SLAB.get()))
                .addPage(new HeadlineTextPage("spirit_stones.twisted_rock", "spirit_stones.twisted_rock.1"))
                .addPage(SpiritInfusionPage.fromOutput(TWISTED_ROCK.get()))
                .addPage(CraftingBookPage.itemPedestalPage(TWISTED_ROCK_ITEM_PEDESTAL.get(), TWISTED_ROCK.get(), TWISTED_ROCK_SLAB.get()))
                .addPage(CraftingBookPage.itemStandPage(TWISTED_ROCK_ITEM_STAND.get(), TWISTED_ROCK.get(), TWISTED_ROCK_SLAB.get()))
        );

        entries.add(new BookEntry<>(
                "ether", 5, 6)
                .setWidgetConfig(w -> w.setIcon(ETHER))
                .addPage(new HeadlineTextPage("ether", "ether.1"))
                .addPage(SpiritInfusionPage.fromOutput(ETHER.get()))
                .addPage(new TextPage("ether.2"))
                .addPage(new CraftingBookPage(ETHER_TORCH.get(), EMPTY, EMPTY, EMPTY, EMPTY, ETHER.get(), EMPTY, EMPTY, STICK, EMPTY))
                .addPage(new CraftingBookPage(TAINTED_ETHER_BRAZIER.get(), EMPTY, EMPTY, EMPTY, TAINTED_ROCK.get(), ETHER.get(), TAINTED_ROCK.get(), STICK, TAINTED_ROCK.get(), STICK))
                .addPage(new CraftingBookPage(TWISTED_ETHER_BRAZIER.get(), EMPTY, EMPTY, EMPTY, TWISTED_ROCK.get(), ETHER.get(), TWISTED_ROCK.get(), STICK, TWISTED_ROCK.get(), STICK))
                .addPage(new HeadlineTextPage("ether.iridescent", "ether.iridescent.1"))
                .addPage(new TextPage("ether.iridescent.2"))
                .addPage(SpiritInfusionPage.fromOutput(IRIDESCENT_ETHER.get()))
                .addPage(new CraftingBookPage(IRIDESCENT_ETHER_TORCH.get(), EMPTY, EMPTY, EMPTY, EMPTY, IRIDESCENT_ETHER.get(), EMPTY, EMPTY, STICK, EMPTY))
                .addPage(new CraftingBookPage(TAINTED_IRIDESCENT_ETHER_BRAZIER.get(), EMPTY, EMPTY, EMPTY, TAINTED_ROCK.get(), IRIDESCENT_ETHER.get(), TAINTED_ROCK.get(), STICK, TAINTED_ROCK.get(), STICK))
                .addPage(new CraftingBookPage(TWISTED_IRIDESCENT_ETHER_BRAZIER.get(), EMPTY, EMPTY, EMPTY, TWISTED_ROCK.get(), IRIDESCENT_ETHER.get(), TWISTED_ROCK.get(), STICK, TWISTED_ROCK.get(), STICK))
        );

        entries.add(new BookEntry<>(
                "spirit_fabric", 4, 5)
                .setWidgetConfig(w -> w.setIcon(SPIRIT_FABRIC))
                .addPage(new HeadlineTextPage("spirit_fabric", "spirit_fabric.1"))
                .addPage(SpiritInfusionPage.fromOutput(SPIRIT_FABRIC.get()))
                .addPage(new HeadlineTextPage("spirit_fabric.pouch", "spirit_fabric.pouch.1"))
                .addPage(new CraftingBookPage(SPIRIT_POUCH.get(), EMPTY, STRING, EMPTY, SPIRIT_FABRIC.get(), SOUL_SAND, SPIRIT_FABRIC.get(), EMPTY, SPIRIT_FABRIC.get(), EMPTY))
        );

        entries.add(new BookEntry<>(
                "soulhunter_gear", 4, 7)
                .setWidgetConfig(w -> w.setIcon(SOUL_HUNTER_CLOAK))
                .addPage(new HeadlineTextPage("soulhunter_gear", "soulhunter_gear.1"))
                .addPage(new CyclingPage(
                        SpiritInfusionPage.fromOutput(SOUL_HUNTER_CLOAK.get()),
                        SpiritInfusionPage.fromOutput(SOUL_HUNTER_ROBE.get()),
                        SpiritInfusionPage.fromOutput(SOUL_HUNTER_LEGGINGS.get()),
                        SpiritInfusionPage.fromOutput(SOUL_HUNTER_BOOTS.get())
                ))
        );

        entries.add(new BookEntry<>(
                "spirit_focusing", 7, 6)
                .setWidgetConfig(w -> w.setIcon(SPIRIT_CRUCIBLE))
                .addPage(new HeadlineTextItemPage("spirit_focusing", "spirit_focusing.1", SPIRIT_CRUCIBLE.get()))
                .addPage(new TextPage("spirit_focusing.2"))
                .addPage(SpiritInfusionPage.fromOutput(SPIRIT_CRUCIBLE.get()))
                .addPage(SpiritInfusionPage.fromOutput(ALCHEMICAL_IMPETUS.get()))
        );

        entries.add(new BookEntry<>(
                "focus_ashes", 6, 5)
                .setWidgetConfig(w -> w.setIcon(GUNPOWDER))
                .addPage(new HeadlineTextPage("focus_ashes", "focus_ashes.1"))
                .addPage(SpiritCruciblePage.fromOutput(GUNPOWDER))
                .addPage(SpiritCruciblePage.fromOutput(GLOWSTONE_DUST))
                .addPage(SpiritCruciblePage.fromOutput(REDSTONE))
        );

        entries.add(new BookEntry<>(
                "focus_metals", 8, 7)
                .setWidgetConfig(w -> w.setIcon(IRON_NODE))
                .addPage(new HeadlineTextItemPage("focus_metals", "focus_metals.1", IRON_NODE.get()))
                .addPage(new TextPage("focus_metals.2"))
                .addPage(new CyclingPage(
                        SpiritInfusionPage.fromOutput(IRON_IMPETUS.get()),
                        SpiritInfusionPage.fromOutput(GOLD_IMPETUS.get()),
                        SpiritInfusionPage.fromOutput(COPPER_IMPETUS.get()),
                        SpiritInfusionPage.fromOutput(LEAD_IMPETUS.get()),
                        SpiritInfusionPage.fromOutput(SILVER_IMPETUS.get()),
                        SpiritInfusionPage.fromOutput(ALUMINUM_IMPETUS.get()),
                        SpiritInfusionPage.fromOutput(NICKEL_IMPETUS.get()),
                        SpiritInfusionPage.fromOutput(URANIUM_IMPETUS.get()),
                        SpiritInfusionPage.fromOutput(OSMIUM_IMPETUS.get()),
                        SpiritInfusionPage.fromOutput(ZINC_IMPETUS.get()),
                        SpiritInfusionPage.fromOutput(TIN_IMPETUS.get())
                ))
                .addPage(new CyclingPage(
                        SpiritCruciblePage.fromOutput(IRON_NODE.get()),
                        SpiritCruciblePage.fromOutput(GOLD_NODE.get()),
                        SpiritCruciblePage.fromOutput(COPPER_NODE.get()),
                        SpiritCruciblePage.fromOutput(LEAD_NODE.get()),
                        SpiritCruciblePage.fromOutput(SILVER_NODE.get()),
                        SpiritCruciblePage.fromOutput(ALUMINUM_NODE.get()),
                        SpiritCruciblePage.fromOutput(NICKEL_NODE.get()),
                        SpiritCruciblePage.fromOutput(URANIUM_NODE.get()),
                        SpiritCruciblePage.fromOutput(OSMIUM_NODE.get()),
                        SpiritCruciblePage.fromOutput(ZINC_NODE.get()),
                        SpiritCruciblePage.fromOutput(TIN_NODE.get())
                ))
        );

        entries.add(new BookEntry<>(
                "focus_crystals", 9, 5)
                .setWidgetConfig(w -> w.setIcon(QUARTZ))
                .addPage(new HeadlineTextPage("focus_crystals", "focus_crystals.1"))
                .addPage(SpiritCruciblePage.fromOutput(QUARTZ))
                .addPage(SpiritCruciblePage.fromOutput(AMETHYST_SHARD))
                .addPage(SpiritCruciblePage.fromOutput(BLAZING_QUARTZ.get()))
                .addPage(SpiritCruciblePage.fromOutput(PRISMARINE))
        );

        entries.add(new BookEntry<>(
                "crucible_acceleration", 7, 4)
                .setWidgetConfig(w -> w.setIcon(SPIRIT_CATALYZER))
                .addPage(new HeadlineTextPage("crucible_acceleration", "crucible_acceleration.1"))
                .addPage(new TextPage("crucible_acceleration.2"))
                .addPage(new TextPage("crucible_acceleration.3"))
                .addPage(SpiritInfusionPage.fromOutput(SPIRIT_CATALYZER.get()))
        );

        entries.add(new BookEntry<>(
                "arcane_restoration", 7, 8)
                .setWidgetConfig(w -> w.setIcon(REPAIR_PYLON))
                .addPage(new HeadlineTextPage("arcane_restoration", "arcane_restoration.1"))
                .addPage(new TextPage("arcane_restoration.2"))
                .addPage(SpiritInfusionPage.fromOutput(REPAIR_PYLON.get()))
                .addPage(SpiritRepairPage.fromInput(CRACKED_ALCHEMICAL_IMPETUS.get()))
                .addPage(SpiritRepairPage.fromInput(CRACKED_COPPER_IMPETUS.get()))
                .addPage(SpiritRepairPage.fromInput(WOODEN_PICKAXE))
                .addPage(SpiritRepairPage.fromInput(STONE_PICKAXE))
                .addPage(SpiritRepairPage.fromInput(IRON_PICKAXE))
                .addPage(SpiritRepairPage.fromInput(DIAMOND_PICKAXE))
                .addPage(SpiritRepairPage.fromInput(GOLDEN_PICKAXE))
                .addPage(SpiritRepairPage.fromInput(NETHERITE_PICKAXE))
                .addPage(new TextPage("arcane_restoration.3"))
                .addPage(SpiritRepairPage.fromInput(SOUL_STAINED_STEEL_PICKAXE.get()))
                .addPage(SpiritRepairPage.fromInput(SOUL_STAINED_STEEL_SCYTHE.get()))
                .addPage(SpiritRepairPage.fromInput(SOUL_HUNTER_BOOTS.get()))
        );
    }
}
