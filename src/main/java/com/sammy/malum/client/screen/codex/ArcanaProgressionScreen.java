package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.common.events.*;
import com.sammy.malum.common.item.codex.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static net.minecraft.world.item.Items.*;

public class ArcanaProgressionScreen extends AbstractProgressionCodexScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = malumPath("textures/gui/book/background.png");

    public static ArcanaProgressionScreen screen;

    public static final List<BookEntry> ENTRIES = new ArrayList<>();

    public boolean isVoidTouched;

    protected ArcanaProgressionScreen() {
        super(1024, 2560);
        minecraft = Minecraft.getInstance();
        setupEntries();
        MinecraftForge.EVENT_BUS.post(new SetupMalumCodexEntriesEvent());
        setupObjects();
    }

    @Override
    public void renderBackground(PoseStack poseStack) {
        renderBackground(poseStack, BACKGROUND_TEXTURE, 0.1f, 0.4f);
    }

    @Override
    public Collection<BookEntry> getEntries() {
        return ENTRIES;
    }

    @Override
    public Supplier<SoundEvent> getSweetenerSound() {
        return SoundRegistry.ARCANA_SWEETENER_NORMAL;
    }

    public static ArcanaProgressionScreen getScreenInstance() {
        if (screen == null) {
            screen = new ArcanaProgressionScreen();
        }
        return screen;
    }

    public static void openCodexViaItem(boolean isVoidTouched) {
        final ArcanaProgressionScreen screenInstance = getScreenInstance();
        screenInstance.openScreen(true);
        screenInstance.isVoidTouched = isVoidTouched;
        screen.playSweetenedSound(SoundRegistry.ARCANA_CODEX_OPEN, 1.25f);
    }

    public static void openCodexViaTransition() {
        getScreenInstance().openScreen(false);
        screen.faceObject(screen.bookObjects.get(0));
        screen.playSound(SoundRegistry.ARCANA_TRANSITION_NORMAL, 1.25f, 1f);
        screen.timesTransitioned++;
        screen.transitionTimer = screen.getTransitionDuration();
        EncyclopediaEsotericaItem.shouldOpenVoidCodex = false;
    }

    public static void setupEntries() {
        ENTRIES.clear();
        Item EMPTY = ItemStack.EMPTY.getItem();

        ENTRIES.add(new BookEntry<ArcanaProgressionScreen>(
                "chronicles_of_the_void", 0, -1)
                .setWidgetSupplier((s, e, x, y) -> new ScreenOpenerObject<>(s, e, x, y, VoidProgressionScreen::openCodexViaTransition, malumPath("textures/gui/book/icons/void_button.png"), 20, 20))
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_GRAND_RUNEWOOD).setValidityChecker(p -> p.isVoidTouched))
        );

        ENTRIES.add(new BookEntry<>(
                "introduction", 0, 0)
                .setWidgetConfig(w -> w.setIcon(ENCYCLOPEDIA_ARCANA).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage("introduction", "introduction.1"))
                .addPage(new TextPage("introduction.2"))
                .addPage(new TextPage("introduction.3"))
                .addPage(new TextPage("introduction.4"))
                .addPage(new TextPage("introduction.5"))
        );

        ENTRIES.add(new BookEntry<>(
                "spirit_crystals", 0, 1)
                .setWidgetSupplier((s, e, x, y) -> new IconObject<>(s, e, x, y, malumPath("textures/gui/book/icons/soul_shard.png")))
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_SMALL_RUNEWOOD))
                .addPage(new HeadlineTextPage("spirit_crystals", "spirit_crystals.1"))
                .addPage(new TextPage("spirit_crystals.2"))
                .addPage(new TextPage("spirit_crystals.3"))
        );

        ENTRIES.add(new BookEntry<>(
                "runewood", 1, 2)
                .setWidgetConfig(w -> w.setIcon(RUNEWOOD_SAPLING))
                .addPage(new HeadlineTextItemPage("runewood", "runewood.1", RUNEWOOD_SAPLING.get()))
                .addPage(new TextPage("runewood.2"))
                .addPage(new HeadlineTextItemPage("runewood.arcane_charcoal", "runewood.arcane_charcoal.1", ARCANE_CHARCOAL.get()))
                .addPage(new SmeltingBookPage(RUNEWOOD_LOG.get(), ARCANE_CHARCOAL.get()))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_ARCANE_CHARCOAL.get(), ARCANE_CHARCOAL.get()))
                .addPage(new HeadlineTextPage("runewood.runic_sap", "runewood.runic_sap.1"))
                .addPage(new TextPage("runewood.runic_sap.2"))
                .addPage(new CraftingBookPage(new ItemStack(RUNIC_SAPBALL.get()), RUNIC_SAP.get(), RUNIC_SAP.get()))
                .addPage(new CraftingBookPage(new ItemStack(RUNIC_SAP_BLOCK.get(), 8), RUNIC_SAP.get(), RUNIC_SAP.get(), EMPTY, RUNIC_SAP.get(), RUNIC_SAP.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "natural_quartz", 3, 1)
                .setWidgetConfig(w -> w.setIcon(NATURAL_QUARTZ).setStyle(BookWidgetStyle.SMALL_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("natural_quartz", "natural_quartz.1", NATURAL_QUARTZ.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "blazing_quartz", 4, 2)
                .setWidgetConfig(w -> w.setIcon(BLAZING_QUARTZ).setStyle(BookWidgetStyle.SMALL_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("blazing_quartz", "blazing_quartz.1", BLAZING_QUARTZ.get()))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "brilliance", -3, 1)
                .setWidgetConfig(w -> w.setIcon(CLUSTER_OF_BRILLIANCE).setStyle(BookWidgetStyle.SMALL_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("brilliance", "brilliance.1", CLUSTER_OF_BRILLIANCE.get()))
                .addPage(new TextPage("brilliance.2"))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_BRILLIANCE.get(), CLUSTER_OF_BRILLIANCE.get()))
                .addPage(new SmeltingBookPage(new ItemStack(CLUSTER_OF_BRILLIANCE.get()), new ItemStack(CHUNK_OF_BRILLIANCE.get(), 2)))
        );

        ENTRIES.add(new BookEntry<>(
                "cthonic_gold", -4, 2)
                .setWidgetConfig(w -> w.setIcon(CTHONIC_GOLD).setStyle(BookWidgetStyle.SMALL_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("cthonic_gold", "cthonic_gold.1", CTHONIC_GOLD.get()))
                .addPage(new TextPage("cthonic_gold.2"))
                .addPage(new TextPage("cthonic_gold.3"))
                .addPage(new TextPage("cthonic_gold.4"))
        );

        ENTRIES.add(new BookEntry<>(
                "soulstone", -1, 2)
                .setWidgetConfig(w -> w.setIcon(PROCESSED_SOULSTONE))
                .addPage(new HeadlineTextItemPage("soulstone", "soulstone.1", PROCESSED_SOULSTONE.get()))
                .addPage(new TextPage("soulstone.2"))
                .addPage(new SmeltingBookPage(new ItemStack(RAW_SOULSTONE.get()), new ItemStack(PROCESSED_SOULSTONE.get(), 2)))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_SOULSTONE.get(), PROCESSED_SOULSTONE.get()))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_RAW_SOULSTONE.get(), RAW_SOULSTONE.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "scythes", 0, 3)
                .setWidgetConfig(w -> w.setIcon(CRUDE_SCYTHE))
                .addPage(new HeadlineTextPage("scythes", "scythes.1"))
                .addPage(CraftingBookPage.scythePage(ItemRegistry.CRUDE_SCYTHE.get(), Items.IRON_INGOT, PROCESSED_SOULSTONE.get()))
                .addPage(new TextPage("scythes.2"))
                .addPage(new HeadlineTextPage("scythes.enchanting", "scythes.enchanting.1"))
                .addPage(new HeadlineTextPage("scythes.enchanting.haunted", "scythes.enchanting.haunted.1"))
                .addPage(new HeadlineTextPage("scythes.enchanting.spirit_plunder", "scythes.enchanting.spirit_plunder.1"))
                .addPage(new HeadlineTextPage("scythes.enchanting.rebound", "scythes.enchanting.rebound.1"))
        );

        ENTRIES.add(new BookEntry<>(
                "spirit_infusion", 0, 5)
                .setWidgetConfig(w -> w.setIcon(SPIRIT_ALTAR).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage("spirit_infusion", "spirit_infusion.1"))
                .addPage(new CraftingBookPage(SPIRIT_ALTAR.get(), AIR, PROCESSED_SOULSTONE.get(), AIR, GOLD_INGOT, RUNEWOOD_PLANKS.get(), GOLD_INGOT, RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get()))
                .addPage(new TextPage("spirit_infusion.2"))
                .addPage(new TextPage("spirit_infusion.3"))
                .addPage(CraftingBookPage.itemPedestalPage(RUNEWOOD_ITEM_PEDESTAL.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
                .addPage(CraftingBookPage.itemStandPage(RUNEWOOD_ITEM_STAND.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
                .addPage(new HeadlineTextPage("spirit_infusion.hex_ash", "spirit_infusion.hex_ash.1"))
                .addPage(SpiritInfusionPage.fromOutput(HEX_ASH.get()))
                .addPage(new HeadlineTextPage("spirit_infusion.living_flesh", "spirit_infusion.living_flesh.1"))
                .addPage(SpiritInfusionPage.fromOutput(LIVING_FLESH.get()))
                .addPage(new HeadlineTextPage("spirit_infusion.alchemical_calx", "spirit_infusion.alchemical_calx.1"))
                .addPage(SpiritInfusionPage.fromOutput(ALCHEMICAL_CALX.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "esoteric_reaping", 0, 6)
                .setWidgetConfig(w -> w.setIcon(ROTTING_ESSENCE))
                .addPage(new HeadlineTextPage("esoteric_reaping", "esoteric_reaping.1"))
                .addPage(new TextPage("esoteric_reaping.2"))
                .addPage(new TextPage("esoteric_reaping.3"))
                .addPage(new HeadlineTextItemPage("esoteric_reaping.rotting_essence", "esoteric_reaping.rotting_essence.1", ROTTING_ESSENCE.get()))
                .addPage(new HeadlineTextItemPage("esoteric_reaping.grim_talc", "esoteric_reaping.grim_talc.1", GRIM_TALC.get()))
                .addPage(new HeadlineTextItemPage("esoteric_reaping.astral_weave", "esoteric_reaping.astral_weave.1", ASTRAL_WEAVE.get()))
                .addPage(new HeadlineTextItemPage("esoteric_reaping.warp_flux", "esoteric_reaping.warp_flux.1", WARP_FLUX.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "primary_arcana", -2, 4)
                .setWidgetConfig(w -> w.setIcon(SACRED_SPIRIT))
                .addPage(new HeadlineTextItemPage("primary_arcana.sacred", "primary_arcana.sacred.1", SACRED_SPIRIT.get()))
                .addPage(new TextPage("primary_arcana.sacred.2"))
                .addPage(new HeadlineTextItemPage("primary_arcana.wicked", "primary_arcana.wicked.1", WICKED_SPIRIT.get()))
                .addPage(new TextPage("primary_arcana.wicked.2"))
                .addPage(new HeadlineTextItemPage("primary_arcana.arcane", "primary_arcana.arcane.1", ARCANE_SPIRIT.get()))
                .addPage(new TextPage("primary_arcana.arcane.2"))
                .addPage(new TextPage("primary_arcana.arcane.3"))
        );

        ENTRIES.add(new BookEntry<>(
                "elemental_arcana", 2, 4)
                .setWidgetConfig(w -> w.setIcon(EARTHEN_SPIRIT))
                .addPage(new HeadlineTextItemPage("elemental_arcana.aerial", "elemental_arcana.aerial.1", AERIAL_SPIRIT.get()))
                .addPage(new TextPage("elemental_arcana.aerial.2"))
                .addPage(new HeadlineTextItemPage("elemental_arcana.earthen", "elemental_arcana.earthen.1", EARTHEN_SPIRIT.get()))
                .addPage(new TextPage("elemental_arcana.earthen.2"))
                .addPage(new HeadlineTextItemPage("elemental_arcana.infernal", "elemental_arcana.infernal.1", INFERNAL_SPIRIT.get()))
                .addPage(new TextPage("elemental_arcana.infernal.2"))
                .addPage(new HeadlineTextItemPage("elemental_arcana.aqueous", "elemental_arcana.aqueous.1", AQUEOUS_SPIRIT.get()))
                .addPage(new TextPage("elemental_arcana.aqueous.2"))
        );

        ENTRIES.add(new BookEntry<>(
                "eldritch_arcana", 0, 7)
                .setWidgetConfig(w -> w.setIcon(ELDRITCH_SPIRIT))
                .addPage(new HeadlineTextItemPage("eldritch_arcana", "eldritch_arcana.1", ELDRITCH_SPIRIT.get()))
                .addPage(new TextPage("eldritch_arcana.2"))
        );

        ENTRIES.add(new BookEntry<>(
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

        ENTRIES.add(new BookEntry<>(
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

        ENTRIES.add(new BookEntry<>(
                "spirit_fabric", 4, 5)
                .setWidgetConfig(w -> w.setIcon(SPIRIT_FABRIC))
                .addPage(new HeadlineTextPage("spirit_fabric", "spirit_fabric.1"))
                .addPage(SpiritInfusionPage.fromOutput(SPIRIT_FABRIC.get()))
                .addPage(new HeadlineTextPage("spirit_fabric.pouch", "spirit_fabric.pouch.1"))
                .addPage(new CraftingBookPage(SPIRIT_POUCH.get(), EMPTY, STRING, EMPTY, SPIRIT_FABRIC.get(), SOUL_SAND, SPIRIT_FABRIC.get(), EMPTY, SPIRIT_FABRIC.get(), EMPTY))
        );

        ENTRIES.add(new BookEntry<>(
                "soulhunter_gear", 4, 7)
                .setWidgetConfig(w -> w.setIcon(SOUL_HUNTER_CLOAK))
                .addPage(new HeadlineTextPage("soulhunter_gear", "soulhunter_gear.1"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_CLOAK.get()))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_ROBE.get()))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_LEGGINGS.get()))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_BOOTS.get()))
        );

//        ENTRIES.add(new BookEntry<>(
//                "soul_something", 3, 8)
//                .setWidgetSupplier((e, x, y) -> new IconObject(e, malumPath("textures/gui/book/icons/soul_blade.png"), x, y))
//        );

        ENTRIES.add(new BookEntry<>(
                "spirit_focusing", 7, 6)
                .setWidgetConfig(w -> w.setIcon(SPIRIT_CRUCIBLE))
                .addPage(new HeadlineTextItemPage("spirit_focusing", "spirit_focusing.1", SPIRIT_CRUCIBLE.get()))
                .addPage(new TextPage("spirit_focusing.2"))
                .addPage(SpiritInfusionPage.fromOutput(SPIRIT_CRUCIBLE.get()))
                .addPage(SpiritInfusionPage.fromOutput(ALCHEMICAL_IMPETUS.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "focus_ashes", 6, 5)
                .setWidgetConfig(w -> w.setIcon(GUNPOWDER))
                .addPage(new HeadlineTextPage("focus_ashes", "focus_ashes.1"))
                .addPage(SpiritCruciblePage.fromOutput(GUNPOWDER))
                .addPage(SpiritCruciblePage.fromOutput(GLOWSTONE_DUST))
                .addPage(SpiritCruciblePage.fromOutput(REDSTONE))
        );

        ENTRIES.add(new BookEntry<>(
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

        ENTRIES.add(new BookEntry<>(
                "focus_crystals", 9, 5)
                .setWidgetConfig(w -> w.setIcon(QUARTZ))
                .addPage(new HeadlineTextPage("focus_crystals", "focus_crystals.1"))
                .addPage(SpiritCruciblePage.fromOutput(QUARTZ))
                .addPage(SpiritCruciblePage.fromOutput(AMETHYST_SHARD))
                .addPage(SpiritCruciblePage.fromOutput(BLAZING_QUARTZ.get()))
                .addPage(SpiritCruciblePage.fromOutput(PRISMARINE))
        );

        ENTRIES.add(new BookEntry<>(
                "crucible_acceleration", 7, 4)
                .setWidgetConfig(w -> w.setIcon(SPIRIT_CATALYZER))
                .addPage(new HeadlineTextPage("crucible_acceleration", "crucible_acceleration.1"))
                .addPage(new TextPage("crucible_acceleration.2"))
                .addPage(new TextPage("crucible_acceleration.3"))
                .addPage(SpiritInfusionPage.fromOutput(SPIRIT_CATALYZER.get()))
        );

        ENTRIES.add(new BookEntry<>(
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

        ENTRIES.add(new BookEntry<>(
                "crucible_augmentation", 10, 8)
                .setWidgetConfig(w -> w.setIcon(TUNING_FORK))
                .addPage(new HeadlineTextPage("crucible_augmentation", "crucible_augmentation.1"))
                .addPage(new TextPage("crucible_augmentation.2"))
                .addPage(new TextPage("crucible_augmentation.3"))
                .addPage(SpiritInfusionPage.fromOutput(TUNING_FORK.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "mending_diffuser", 11, 7)
                .setWidgetConfig(w -> w.setIcon(MENDING_DIFFUSER))
                .addPage(new HeadlineTextPage("mending_diffuser", "mending_diffuser.1"))
                .addPage(SpiritInfusionPage.fromOutput(MENDING_DIFFUSER.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "impurity_stabilizer", 12, 7)
                .setWidgetConfig(w -> w.setIcon(IMPURITY_STABILIZER))
                .addPage(new HeadlineTextPage("impurity_stabilizer", "impurity_stabilizer.1"))
                .addPage(SpiritInfusionPage.fromOutput(IMPURITY_STABILIZER.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "accelerating_inlay", 13, 8)
                .setWidgetConfig(w -> w.setIcon(ACCELERATING_INLAY))
                .addPage(new HeadlineTextPage("accelerating_inlay", "accelerating_inlay.1"))
                .addPage(SpiritInfusionPage.fromOutput(ACCELERATING_INLAY.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "blazing_diode", 12, 8)
                .setWidgetConfig(w -> w.setIcon(BLAZING_DIODE))
                .addPage(new HeadlineTextPage("blazing_diode", "blazing_diode.1"))
                .addPage(SpiritInfusionPage.fromOutput(BLAZING_DIODE.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "prismatic_focus_lens", 12, 9)
                .setWidgetConfig(w -> w.setIcon(PRISMATIC_FOCUS_LENS))
                .addPage(new HeadlineTextPage("prismatic_focus_lens", "prismatic_focus_lens.1"))
                .addPage(SpiritInfusionPage.fromOutput(PRISMATIC_FOCUS_LENS.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "intricate_assembly", 13, 9)
                .setWidgetConfig(w -> w.setIcon(INTRICATE_ASSEMBLY))
                .addPage(new HeadlineTextPage("intricate_assembly", "intricate_assembly.1"))
                .addPage(SpiritInfusionPage.fromOutput(INTRICATE_ASSEMBLY.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "shielding_apparatus", 13, 10)
                .setWidgetConfig(w -> w.setIcon(SHIELDING_APPARATUS))
                .addPage(new HeadlineTextPage("shielding_apparatus", "shielding_apparatus.1"))
                .addPage(SpiritInfusionPage.fromOutput(SHIELDING_APPARATUS.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "warping_engine", 14, 10)
                .setWidgetConfig(w -> w.setIcon(WARPING_ENGINE))
                .addPage(new HeadlineTextPage("warping_engine", "warping_engine.1"))
                .addPage(new TextPage("warping_engine.2"))
                .addPage(SpiritInfusionPage.fromOutput(WARPING_ENGINE.get()))
        );

        ENTRIES.add(new BookEntry<>(
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

        ENTRIES.add(new BookEntry<>(
                "soulstained_scythe", -4, 5)
                .setWidgetConfig(w -> w.setIcon(SOUL_STAINED_STEEL_SCYTHE))
                .addPage(new HeadlineTextPage("soulstained_scythe", "soulstained_scythe.1"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_SCYTHE.get()))
        );

        ENTRIES.add(new BookEntry<>(
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

        ENTRIES.add(new BookEntry<>(
                "spirit_trinkets", -5, 6)
                .setWidgetConfig(w -> w.setIcon(ORNATE_RING))
                .addPage(new HeadlineTextPage("spirit_trinkets", "spirit_trinkets.1"))
                .addPage(new TextPage("spirit_trinkets.2"))
                .addPage(CraftingBookPage.ringPage(GILDED_RING.get(), LEATHER, HALLOWED_GOLD_INGOT.get()))
                .addPage(new CraftingBookPage(GILDED_BELT.get(), LEATHER, LEATHER, LEATHER, HALLOWED_GOLD_INGOT.get(), PROCESSED_SOULSTONE.get(), HALLOWED_GOLD_INGOT.get(), EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY))
                .addPage(CraftingBookPage.ringPage(ORNATE_RING.get(), LEATHER, SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new CraftingBookPage(ORNATE_NECKLACE.get(), EMPTY, STRING, EMPTY, STRING, EMPTY, STRING, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY))
        );

        ENTRIES.add(new BookEntry<>(
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

        ENTRIES.add(new BookEntry<>(
                "ring_of_esoteric_spoils", -9, 5)
                .setWidgetConfig(w -> w.setIcon(RING_OF_ESOTERIC_SPOILS))
                .addPage(new HeadlineTextPage("ring_of_esoteric_spoils", "ring_of_esoteric_spoils.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ESOTERIC_SPOILS.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "belt_of_the_starved",-8, 7)
                .setWidgetConfig(w -> w.setIcon(BELT_OF_THE_STARVED))
                .addPage(new HeadlineTextPage("belt_of_the_starved", "belt_of_the_starved.1"))
                .addPage(new TextPage("belt_of_the_starved.2"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_STARVED.get()))
                .addPage(new HeadlineTextPage("belt_of_the_starved.ring_of_desperate_voracity", "belt_of_the_starved.ring_of_desperate_voracity.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_DESPERATE_VORACITY.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "necklace_of_the_narrow_edge", -7, 8)
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_THE_NARROW_EDGE))
                .addPage(new HeadlineTextPage("necklace_of_the_narrow_edge", "necklace_of_the_narrow_edge.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_NARROW_EDGE.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "belt_of_the_prospector", -6, 5)
                .setWidgetConfig(w -> w.setIcon(BELT_OF_THE_PROSPECTOR))
                .addPage(new HeadlineTextPage("belt_of_the_prospector", "belt_of_the_prospector.1"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_PROSPECTOR.get()))
                .addPage(new HeadlineTextPage("belt_of_the_prospector.ring_of_the_hoarder", "belt_of_the_prospector.ring_of_the_hoarder.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_THE_HOARDER.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "necklace_of_blissful_harmony", -7, 4)
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_BLISSFUL_HARMONY))
                .addPage(new HeadlineTextPage("necklace_of_blissful_harmony", "necklace_of_blissful_harmony.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_BLISSFUL_HARMONY.get()))
                .addPage(new TextPage("necklace_of_blissful_harmony.2"))
        );

        ENTRIES.add(new BookEntry<>(
                "necklace_of_the_mystic_mirror", 6, 12)
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_THE_MYSTIC_MIRROR))
                .addPage(new HeadlineTextPage("necklace_of_the_mystic_mirror", "necklace_of_the_mystic_mirror.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_MYSTIC_MIRROR.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "mirror_magic", 6, 10)
                .setWidgetConfig(w -> w.setIcon(SPECTRAL_LENS).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage("mirror_magic", "mirror_magic.1"))
                .addPage(SpiritInfusionPage.fromOutput(SPECTRAL_LENS.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "voodoo_magic", -6, 10)
                .setWidgetConfig(w -> w.setIcon(POPPET).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage("voodoo_magic", "voodoo_magic.1"))
                .addPage(SpiritInfusionPage.fromOutput(POPPET.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "altar_acceleration", -1, 8)
                .setWidgetConfig(w -> w.setIcon(RUNEWOOD_OBELISK))
                .addPage(new HeadlineTextPage("altar_acceleration.runewood_obelisk", "altar_acceleration.runewood_obelisk.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_OBELISK.get()))
                .addPage(new HeadlineTextPage("altar_acceleration.brilliant_obelisk", "altar_acceleration.brilliant_obelisk.1"))
                .addPage(SpiritInfusionPage.fromOutput(BRILLIANT_OBELISK.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "totem_magic", 0, 9)
                .setWidgetConfig(w -> w.setIcon(RUNEWOOD_TOTEM_BASE).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("totem_magic", "totem_magic.1", RUNEWOOD_TOTEM_BASE.get()))
                .addPage(new TextPage("totem_magic.2"))
                .addPage(new TextPage("totem_magic.3"))
                .addPage(new TextPage("totem_magic.4"))
                .addPage(new TextPage("totem_magic.5"))
                .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_TOTEM_BASE.get()))
        );

        ENTRIES.add(new BookEntry<ArcanaProgressionScreen>(
                "sacred_rite", -2, 10)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.SACRED_RITE, "sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.SACRED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "greater_sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "corrupt_sacred_rite", -3, 10)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.SACRED_RITE, "corrupt_sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.SACRED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "corrupt_greater_sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "infernal_rite", -3, 11)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.INFERNAL_RITE, "infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.INFERNAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "greater_infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "corrupt_infernal_rite", -4, 11)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.INFERNAL_RITE, "corrupt_infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.INFERNAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "corrupt_greater_infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "earthen_rite", -3, 12)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.EARTHEN_RITE, "earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.EARTHEN_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "greater_earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "corrupt_earthen_rite", -4, 12)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.EARTHEN_RITE, "corrupt_earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.EARTHEN_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "corrupt_greater_earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "wicked_rite", 2, 10)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.WICKED_RITE, "wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.WICKED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "greater_wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "corrupt_wicked_rite", 3, 10)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.WICKED_RITE, "corrupt_wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.WICKED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "corrupt_greater_wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "aerial_rite", 3, 11)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AERIAL_RITE, "aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AERIAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "greater_aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "corrupt_aerial_rite", 4, 11)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AERIAL_RITE, "corrupt_aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AERIAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "corrupt_greater_aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "aqueous_rite", 3, 12)
                .setWidgetSupplier(RiteEntryObject::new)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AQUEOUS_RITE, "aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AQUEOUS_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "greater_aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        ENTRIES.add(new BookEntry<>(
                "corrupt_aqueous_rite", 4, 12)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AQUEOUS_RITE, "corrupt_aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AQUEOUS_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "corrupt_greater_aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        ENTRIES.add(new BookEntry<>(
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

        ENTRIES.add(new BookEntry<>(
                "blight", -1, 12)
                .setWidgetConfig(w -> w.setIcon(BLIGHTED_GUNK).setStyle(BookWidgetStyle.SMALL_SOULWOOD))
                .addPage(new HeadlineTextPage("blight.intro", "blight.intro.1"))
                .addPage(new HeadlineTextPage("blight.composition", "blight.composition.1"))
                .addPage(new HeadlineTextPage("blight.spread", "blight.spread.1"))
                .addPage(new HeadlineTextPage("blight.arcane_rite", "blight.arcane_rite.1"))
        );

        ENTRIES.add(new BookEntry<>(
                "soulwood", 1, 12)
                .setWidgetConfig(w -> w.setIcon(SOULWOOD_GROWTH).setStyle(BookWidgetStyle.SMALL_SOULWOOD))
                .addPage(new HeadlineTextPage("soulwood.intro", "soulwood.intro.1"))
                .addPage(new HeadlineTextPage("soulwood.bonemeal", "soulwood.bonemeal.1"))
                .addPage(new HeadlineTextPage("soulwood.color", "soulwood.color.1"))
                .addPage(new HeadlineTextPage("soulwood.blight", "soulwood.blight.1"))
                .addPage(new HeadlineTextPage("soulwood.sap", "soulwood.sap.1"))
        );
        ENTRIES.add(new BookEntry<>(
                "transmutation", 0, 13)
                .setWidgetConfig(w -> w.setIcon(SOUL_SAND).setStyle(BookWidgetStyle.SMALL_SOULWOOD))
                .addPage(new HeadlineTextPage("transmutation", "transmutation.intro.1"))
                .addPage(new TextPage("transmutation.intro.2"))
                .addPage(new SpiritTransmutationRecipeTreePage("transmutation.stone", STONE))
                .addPage(new SpiritTransmutationRecipeTreePage("transmutation.deepslate", DEEPSLATE))
                .addPage(new SpiritTransmutationRecipeTreePage("transmutation.smooth_basalt", SMOOTH_BASALT))
        );

        ENTRIES.add(new BookEntry<>(
                "rituals", 0, 17)
                .setWidgetConfig(w -> w.setIcon(RITUAL_PLINTH).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
        );

        ENTRIES.add(new BookEntry<>(
                "ritual_of_grotesque_expulsion", 0, 19)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_GROTESQUE_EXPULSION, "grotesque_expulsion_ritual"))
        );

        ENTRIES.add(new BookEntry<>(
                "ritual_of_idle_mending", 0, 15)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_IDLE_MENDING, "idle_mending_ritual"))
        );

        ENTRIES.add(new BookEntry<>(
                "ritual_of_manabound_enhancement", -2, 17)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_MANABOUND_ENHANCEMENT, "manabound_enhancement_ritual"))
        );

        ENTRIES.add(new BookEntry<>(
                "ritual_of_hexing_transmission", 2, 17)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_HEXING_TRANSMISSION, "hexing_transmission_ritual"))
        );

        ENTRIES.add(new BookEntry<>(
                "ritual_of_warped_time", 1, 18)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_WARPED_TIME, "warped_time_ritual"))
        );

        ENTRIES.add(new BookEntry<>(
                "ritual_of_aqueous_something", -1, 16)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
        );

        ENTRIES.add(new BookEntry<>(
                "ritual_of_cthonic_conversion", -1, 18)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RitualEntryObject::new)
                .addPage(new SpiritRitualTextPage(RitualRegistry.RITUAL_OF_CTHONIC_CONVERSION, "cthonic_conversion_ritual"))
        );

        ENTRIES.add(new BookEntry<>(
                "ritual_of_earthen_something", 1, 16)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
        );

        ENTRIES.add(new BookEntry<>(
                "belt_of_the_magebane", -2, 14)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.SOULWOOD).setIcon(BELT_OF_THE_MAGEBANE))
                .addPage(new HeadlineTextPage("belt_of_the_magebane", "belt_of_the_magebane.1"))
                .addPage(new TextPage("belt_of_the_magebane.2"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_MAGEBANE.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "tyrving", 2, 14)
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.SOULWOOD).setIcon(TYRVING))
                .addPage(new HeadlineTextPage("tyrving", "tyrving.1"))
                .addPage(SpiritInfusionPage.fromOutput(TYRVING.get()))
                .addPage(new TextPage("tyrving.2"))
                .addPage(SpiritRepairPage.fromInput(TYRVING.get()))
        );

        ENTRIES.add(new BookEntry<>(
                "the_device", 0, -10)
                .setWidgetSupplier(VanishingEntryObject::new)
                .setWidgetConfig(w -> w.setIcon(THE_DEVICE))
                .addPage(new HeadlineTextPage("the_device", "the_device"))
                .addPage(new CraftingBookPage(THE_DEVICE.get(), TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get()))
        );
    }
}