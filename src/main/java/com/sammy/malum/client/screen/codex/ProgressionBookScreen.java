package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.objects.BookObject;
import com.sammy.malum.client.screen.codex.objects.ImportantEntryObject;
import com.sammy.malum.client.screen.codex.objects.VanishingEntryObject;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.common.events.SetupMalumCodexEntriesEvent;
import com.sammy.malum.core.setup.content.SpiritRiteRegistry;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.ortus.handlers.ScreenParticleHandler;
import com.sammy.ortus.helpers.RenderHelper;
import com.sammy.ortus.systems.recipe.IRecipeComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.core.setup.content.item.ItemRegistry.*;
import static com.sammy.ortus.systems.rendering.particle.screen.base.ScreenParticle.RenderOrder.BEFORE_TOOLTIPS;
import static net.minecraft.util.FastColor.ARGB32.color;
import static net.minecraft.world.item.Items.*;
import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class ProgressionBookScreen extends Screen {
    public static final ResourceLocation FRAME_TEXTURE = MalumMod.prefix("textures/gui/book/frame.png");
    public static final ResourceLocation FADE_TEXTURE = MalumMod.prefix("textures/gui/book/fade.png");

    public static final ResourceLocation BACKGROUND_TEXTURE = MalumMod.prefix("textures/gui/book/background.png");

    public int bookWidth = 378;
    public int bookHeight = 250;
    public int bookInsideWidth = 344;
    public int bookInsideHeight = 218;

    public final int parallax_width = 1024;
    public final int parallax_height = 2560;
    public static ProgressionBookScreen screen;
    public float xOffset;
    public float yOffset;
    public float cachedXOffset;
    public float cachedYOffset;
    public boolean ignoreNextMouseInput;

    public static ArrayList<BookEntry> ENTRIES = new ArrayList<>();
    public static ArrayList<BookObject> OBJECTS = new ArrayList<>();

    protected ProgressionBookScreen() {
        super(new TranslatableComponent("malum.gui.book.title"));
        minecraft = Minecraft.getInstance();
        setupEntries();
        MinecraftForge.EVENT_BUS.post(new SetupMalumCodexEntriesEvent());
        setupObjects();
    }

    public static void setupEntries() {
        ENTRIES.clear();
        Item EMPTY = ItemStack.EMPTY.getItem();

        ENTRIES.add(new BookEntry(
                "introduction", ENCYCLOPEDIA_ARCANA.get(), 0, 0)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("introduction", "introduction_a"))
                .addPage(new TextPage("introduction_b"))
                .addPage(new TextPage("introduction_c"))
                .addPage(new TextPage("introduction_d"))
        );

        ENTRIES.add(new BookEntry(
                "spirit_magics", SOUL_SAND, 0, 1)
                .addPage(new HeadlineTextPage("spirit_magics", "spirit_magics_a"))
                .addPage(new TextPage("spirit_magics_b"))
                .addPage(new TextPage("spirit_magics_c"))
        );

        ENTRIES.add(new BookEntry(
                "runewood", RUNEWOOD_SAPLING.get(), 1, 2)
                .addPage(new HeadlineTextPage("runewood", "runewood_a"))
                .addPage(new TextPage("runewood_b"))
                .addPage(new TextPage("runewood_c"))
                .addPage(new TextPage("runewood_d"))
                .addPage(CraftingBookPage.itemPedestalPage(RUNEWOOD_ITEM_PEDESTAL.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
                .addPage(CraftingBookPage.itemStandPage(RUNEWOOD_ITEM_STAND.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
                .addPage(new HeadlineTextPage("arcane_charcoal", "arcane_charcoal"))
                .addPage(new SmeltingBookPage(RUNEWOOD_LOG.get(), ARCANE_CHARCOAL.get()))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_ARCANE_CHARCOAL.get(), ARCANE_CHARCOAL.get()))
                .addPage(new HeadlineTextPage("holy_sap", "holy_sap_a"))
                .addPage(new TextPage("holy_sap_b"))
                .addPage(new CraftingBookPage(new ItemStack(HOLY_SAPBALL.get(), 3), Items.SLIME_BALL, HOLY_SAP.get()))
                .addPage(new TextPage("holy_sap_c"))
                .addPage(new SmeltingBookPage(HOLY_SAP.get(), HOLY_SYRUP.get()))
                .addModCompatPage(new TextPage("holy_sap_d"), "thermal_expansion")
        );

        ENTRIES.add(new BookEntry(
                "soulstone", PROCESSED_SOULSTONE.get(), -1, 2)
                .addPage(new HeadlineTextPage("soulstone", "soulstone_a"))
                .addPage(new TextPage("soulstone_b"))
                .addPage(new TextPage("soulstone_c"))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_SOULSTONE.get(), PROCESSED_SOULSTONE.get()))
        );

        ENTRIES.add(new BookEntry(
                "scythes", CRUDE_SCYTHE.get(), 0, 3)
                .addPage(new HeadlineTextPage("scythes", "scythes_a"))
                .addPage(new TextPage("scythes_b"))
                .addPage(new TextPage("scythes_c"))
                .addPage(CraftingBookPage.scythePage(ItemRegistry.CRUDE_SCYTHE.get(), Items.IRON_INGOT, PROCESSED_SOULSTONE.get()))
                .addPage(new HeadlineTextPage("haunted", "haunted"))
                .addPage(new HeadlineTextPage("spirit_plunder", "spirit_plunder"))
                .addPage(new HeadlineTextPage("rebound", "rebound"))
        );

        ENTRIES.add(new BookEntry(
                "spirit_infusion", SPIRIT_ALTAR.get(), 0, 5)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("spirit_infusion", "spirit_infusion_a"))
                .addPage(new TextPage("spirit_infusion_b"))
                .addPage(new TextPage("spirit_infusion_c"))
                .addPage(new CraftingBookPage(SPIRIT_ALTAR.get(), AIR, PROCESSED_SOULSTONE.get(), AIR, GOLD_INGOT, RUNEWOOD_PLANKS.get(), GOLD_INGOT, RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get()))
                .addPage(CraftingBookPage.itemPedestalPage(RUNEWOOD_ITEM_PEDESTAL.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
                .addPage(CraftingBookPage.itemStandPage(RUNEWOOD_ITEM_STAND.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
                .addPage(new HeadlineTextPage("hex_ash", "hex_ash"))
                .addPage(SpiritInfusionPage.fromOutput(HEX_ASH.get()))
        );

        ENTRIES.add(new BookEntry(
                "primary_arcana", ARCANE_SPIRIT.get(), -2, 4)
                .addPage(new SpiritTextPage("sacred_spirit", "sacred_spirit_a", SACRED_SPIRIT.get()))
                .addPage(new TextPage("sacred_spirit_b"))
                .addPage(new SpiritTextPage("wicked_spirit", "wicked_spirit_a", WICKED_SPIRIT.get()))
                .addPage(new TextPage("wicked_spirit_b"))
                .addPage(new SpiritTextPage("arcane_spirit", "arcane_spirit_a", ARCANE_SPIRIT.get()))
                .addPage(new TextPage("arcane_spirit_b"))
                .addPage(new TextPage("arcane_spirit_c"))
        );

        ENTRIES.add(new BookEntry(
                "elemental_arcana", EARTHEN_SPIRIT.get(), 2, 4)
                .addPage(new SpiritTextPage("earthen_spirit", "earthen_spirit_a", EARTHEN_SPIRIT.get()))
                .addPage(new TextPage("earthen_spirit_b"))
                .addPage(new SpiritTextPage("infernal_spirit", "infernal_spirit_a", INFERNAL_SPIRIT.get()))
                .addPage(new TextPage("infernal_spirit_b"))
                .addPage(new SpiritTextPage("aerial_spirit", "aerial_spirit_a", AERIAL_SPIRIT.get()))
                .addPage(new TextPage("aerial_spirit_b"))
                .addPage(new SpiritTextPage("aqueous_spirit", "aqueous_spirit_a", AQUEOUS_SPIRIT.get()))
                .addPage(new TextPage("aqueous_spirit_b"))
        );

        ENTRIES.add(new BookEntry(
                "eldritch_arcana", ELDRITCH_SPIRIT.get(), 0, 7)
                .addPage(new SpiritTextPage("eldritch_spirit", "eldritch_spirit_a", ELDRITCH_SPIRIT.get()))
                .addPage(new TextPage("eldritch_spirit_b"))
        );

        ENTRIES.add(new BookEntry(
                "arcane_rock", TAINTED_ROCK.get(), 3, 6)
                .addPage(new HeadlineTextPage("tainted_rock", "tainted_rock"))
                .addPage(SpiritInfusionPage.fromOutput(TAINTED_ROCK.get()))
                .addPage(CraftingBookPage.itemPedestalPage(TAINTED_ROCK_ITEM_PEDESTAL.get(), TAINTED_ROCK.get(), TAINTED_ROCK_SLAB.get()))
                .addPage(CraftingBookPage.itemStandPage(TAINTED_ROCK_ITEM_STAND.get(), TAINTED_ROCK.get(), TAINTED_ROCK_SLAB.get()))
                .addPage(new HeadlineTextPage("twisted_rock", "twisted_rock"))
                .addPage(SpiritInfusionPage.fromOutput(TWISTED_ROCK.get()))
                .addPage(CraftingBookPage.itemPedestalPage(TWISTED_ROCK_ITEM_PEDESTAL.get(), TWISTED_ROCK.get(), TWISTED_ROCK_SLAB.get()))
                .addPage(CraftingBookPage.itemStandPage(TWISTED_ROCK_ITEM_STAND.get(), TWISTED_ROCK.get(), TWISTED_ROCK_SLAB.get()))
        );
        ENTRIES.add(new BookEntry(
                "ether", ETHER.get(), 5, 6)
                .addPage(new HeadlineTextPage("ether", "ether_a"))
                .addPage(SpiritInfusionPage.fromOutput(ETHER.get()))
                .addPage(new TextPage("ether_b"))
                .addPage(new CraftingBookPage(ETHER_TORCH.get(), EMPTY, EMPTY, EMPTY, EMPTY, ETHER.get(), EMPTY, EMPTY, STICK, EMPTY))
                .addPage(new CraftingBookPage(TAINTED_ETHER_BRAZIER.get(), EMPTY, EMPTY, EMPTY, TAINTED_ROCK.get(), ETHER.get(), TAINTED_ROCK.get(), STICK, TAINTED_ROCK.get(), STICK))
                .addPage(new CraftingBookPage(TWISTED_ETHER_BRAZIER.get(), EMPTY, EMPTY, EMPTY, TWISTED_ROCK.get(), ETHER.get(), TWISTED_ROCK.get(), STICK, TWISTED_ROCK.get(), STICK))
                .addPage(new HeadlineTextPage("iridescent_ether", "iridescent_ether_a"))
                .addPage(new TextPage("iridescent_ether_b"))
                .addPage(SpiritInfusionPage.fromOutput(IRIDESCENT_ETHER.get()))
                .addPage(new CraftingBookPage(IRIDESCENT_ETHER_TORCH.get(), EMPTY, EMPTY, EMPTY, EMPTY, IRIDESCENT_ETHER.get(), EMPTY, EMPTY, STICK, EMPTY))
                .addPage(new CraftingBookPage(TAINTED_IRIDESCENT_ETHER_BRAZIER.get(), EMPTY, EMPTY, EMPTY, TAINTED_ROCK.get(), IRIDESCENT_ETHER.get(), TAINTED_ROCK.get(), STICK, TAINTED_ROCK.get(), STICK))
                .addPage(new CraftingBookPage(TWISTED_IRIDESCENT_ETHER_BRAZIER.get(), EMPTY, EMPTY, EMPTY, TWISTED_ROCK.get(), IRIDESCENT_ETHER.get(), TWISTED_ROCK.get(), STICK, TWISTED_ROCK.get(), STICK))
        );

        ENTRIES.add(new BookEntry(
                "spirit_fabric", SPIRIT_FABRIC.get(), 4, 5)
                .addPage(new HeadlineTextPage("spirit_fabric", "spirit_fabric"))
                .addPage(SpiritInfusionPage.fromOutput(SPIRIT_FABRIC.get()))
                .addPage(new HeadlineTextPage("spirit_pouch", "spirit_pouch"))
                .addPage(new CraftingBookPage(SPIRIT_POUCH.get(), EMPTY, STRING, EMPTY, SPIRIT_FABRIC.get(), SOUL_SAND, SPIRIT_FABRIC.get(), EMPTY, SPIRIT_FABRIC.get(), EMPTY))
        );

        ENTRIES.add(new BookEntry(
                "soul_hunter_gear", SOUL_HUNTER_CLOAK.get(), 4, 7)
                .addPage(new HeadlineTextPage("soul_hunter_armor", "soul_hunter_armor"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_CLOAK.get()))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_ROBE.get()))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_LEGGINGS.get()))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_BOOTS.get()))
        );

        ENTRIES.add(new BookEntry(
                "spirit_focusing", SPIRIT_CRUCIBLE.get(), 7, 6)
                .addPage(new HeadlineTextPage("spirit_focusing", "spirit_focusing_a"))
                .addPage(new TextPage("spirit_focusing_b"))
                .addPage(SpiritInfusionPage.fromOutput(SPIRIT_CRUCIBLE.get()))
                .addPage(SpiritInfusionPage.fromOutput(ALCHEMICAL_IMPETUS.get()))
        );

        ENTRIES.add(new BookEntry(
                "working_with_ashes", GUNPOWDER, 6, 5)
                .addPage(new HeadlineTextPage("working_with_ashes", "working_with_ashes"))
                .addPage(SpiritCruciblePage.fromOutput(GUNPOWDER))
                .addPage(SpiritCruciblePage.fromOutput(GLOWSTONE_DUST))
                .addPage(SpiritCruciblePage.fromOutput(REDSTONE))
        );

        ENTRIES.add(new BookEntry(
                "metallurgic_magic", IRON_NODE.get(), 8, 7)
                .addPage(new HeadlineTextPage("metallurgic_magic", "metallurgic_magic_a"))
                .addPage(new TextPage("metallurgic_magic_b"))
                .addPage(SpiritInfusionPage.fromOutput(IRON_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(IRON_NODE.get()))
                .addPage(SpiritInfusionPage.fromOutput(GOLD_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(GOLD_NODE.get()))
                .addPage(SpiritInfusionPage.fromOutput(COPPER_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(COPPER_NODE.get()))
                .addPage(SpiritInfusionPage.fromOutput(LEAD_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(LEAD_NODE.get()))
                .addPage(SpiritInfusionPage.fromOutput(SILVER_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(SILVER_NODE.get()))
                .addPage(SpiritInfusionPage.fromOutput(ALUMINUM_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(ALUMINUM_NODE.get()))
                .addPage(SpiritInfusionPage.fromOutput(NICKEL_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(NICKEL_NODE.get()))
                .addPage(SpiritInfusionPage.fromOutput(URANIUM_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(URANIUM_NODE.get()))
                .addPage(SpiritInfusionPage.fromOutput(OSMIUM_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(OSMIUM_NODE.get()))
                .addPage(SpiritInfusionPage.fromOutput(ZINC_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(ZINC_NODE.get()))
                .addPage(SpiritInfusionPage.fromOutput(TIN_IMPETUS.get()))
                .addPage(SpiritCruciblePage.fromOutput(TIN_NODE.get()))
        );

        ENTRIES.add(new BookEntry(
                "crucible_acceleration", SPIRIT_CATALYZER.get(), 7, 4)
                .addPage(new HeadlineTextPage("crucible_acceleration", "crucible_acceleration_a"))
                .addPage(new TextPage("crucible_acceleration_b"))
                .addPage(new TextPage("crucible_acceleration_c"))
                .addPage(SpiritInfusionPage.fromOutput(SPIRIT_CATALYZER.get()))
        );

        ENTRIES.add(new BookEntry(
                "impetus_restoration", TWISTED_TABLET.get(), 7, 8)
                .addPage(new HeadlineTextPage("impetus_restoration", "impetus_restoration_a"))
                .addPage(new TextPage("impetus_restoration_b"))
                .addPage(new TextPage("impetus_restoration_c"))
                .addPage(SpiritInfusionPage.fromOutput(TWISTED_TABLET.get()))
                .addPage(SpiritRepairPage.fromInput(CRACKED_ALCHEMICAL_IMPETUS.get()))
                .addPage(SpiritRepairPage.fromInput(CRACKED_COPPER_IMPETUS.get()))
                .addPage(new HeadlineTextPage("expanded_focusing", "expanded_focusing_a"))
                .addPage(new TextPage("expanded_focusing_b"))
                .addPage(SpiritRepairPage.fromInput(WOODEN_PICKAXE))
                .addPage(SpiritRepairPage.fromInput(STONE_PICKAXE))
                .addPage(SpiritRepairPage.fromInput(IRON_PICKAXE))
                .addPage(SpiritRepairPage.fromInput(DIAMOND_PICKAXE))
                .addPage(SpiritRepairPage.fromInput(GOLDEN_PICKAXE))
                .addPage(SpiritRepairPage.fromInput(NETHERITE_PICKAXE))
                .addPage(new TextPage("expanded_focusing_c"))
                .addPage(SpiritRepairPage.fromInput(SOUL_STAINED_STEEL_PICKAXE.get()))
                .addPage(SpiritRepairPage.fromInput(SOUL_STAINED_STEEL_SCYTHE.get()))
                .addPage(SpiritRepairPage.fromInput(SOUL_HUNTER_BOOTS.get()))
        );

        ENTRIES.add(new BookEntry(
                "crystal_creation", QUARTZ, 9, 5)
                .addPage(new HeadlineTextPage("crystal_creation", "crystal_creation"))
                .addPage(SpiritCruciblePage.fromOutput(QUARTZ))
                .addPage(SpiritCruciblePage.fromOutput(AMETHYST_SHARD))
                .addPage(SpiritCruciblePage.fromOutput(BLAZING_QUARTZ.get()))
                .addPage(SpiritCruciblePage.fromOutput(PRISMARINE_CRYSTALS))
        );

        ENTRIES.add(new BookEntry(
                "spirit_metals", SOUL_STAINED_STEEL_INGOT.get(), -3, 6)
                .addPage(new HeadlineTextPage("hallowed_gold", "hallowed_gold_a"))
                .addPage(new TextPage("hallowed_gold_b"))
                .addPage(SpiritInfusionPage.fromOutput(HALLOWED_GOLD_INGOT.get()))
                .addPage(CraftingBookPage.resonatorPage(HALLOWED_SPIRIT_RESONATOR.get(), QUARTZ, HALLOWED_GOLD_INGOT.get(), RUNEWOOD_PLANKS.get()))
                .addPage(new HeadlineTextPage("spirit_jar", "spirit_jar"))
                .addPage(new CraftingBookPage(SPIRIT_JAR.get(), GLASS_PANE, HALLOWED_GOLD_INGOT.get(), GLASS_PANE, GLASS_PANE, EMPTY, GLASS_PANE, GLASS_PANE, GLASS_PANE, GLASS_PANE))
                .addPage(new HeadlineTextPage("soul_stained_steel", "soul_stained_steel_a"))
                .addPage(new TextPage("soul_stained_steel_b"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(CraftingBookPage.resonatorPage(STAINED_SPIRIT_RESONATOR.get(), QUARTZ, SOUL_STAINED_STEEL_INGOT.get(), RUNEWOOD_PLANKS.get()))
                .addPage(CraftingBookPage.toolPage(SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(CraftingBookPage.toolPage(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(CraftingBookPage.toolPage(SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(CraftingBookPage.toolPage(SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(CraftingBookPage.toolPage(SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addModCompatPage(new CraftingBookPage(SOUL_STAINED_STEEL_KNIFE.get(), EMPTY, EMPTY, EMPTY, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY, STICK), "farmersdelight")
        );

        ENTRIES.add(new BookEntry(
                "soul_stained_scythe", SOUL_STAINED_STEEL_SCYTHE.get(), -4, 5)
                .addPage(new HeadlineTextPage("soul_stained_scythe", "soul_stained_scythe"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_SCYTHE.get()))
        );

        ENTRIES.add(new BookEntry(
                "soul_stained_armor", SOUL_STAINED_STEEL_HELMET.get(), -4, 7)
                .addPage(new HeadlineTextPage("soul_stained_armor", "soul_stained_armor_a"))
                .addPage(new TextPage("soul_stained_armor_b"))
                .addPage(new TextPage("soul_stained_armor_c"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_HELMET.get()))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_CHESTPLATE.get()))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_LEGGINGS.get()))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_BOOTS.get()))
        );

        ENTRIES.add(new BookEntry(
                "spirit_trinkets", ORNATE_RING.get(), -5, 6)
                .addPage(new HeadlineTextPage("spirit_trinkets", "spirit_trinkets_a"))
                .addPage(new TextPage("spirit_trinkets_b"))
                .addPage(CraftingBookPage.ringPage(GILDED_RING.get(), LEATHER, HALLOWED_GOLD_INGOT.get()))
                .addPage(new CraftingBookPage(GILDED_BELT.get(), LEATHER, LEATHER, LEATHER, HALLOWED_GOLD_INGOT.get(), PROCESSED_SOULSTONE.get(), HALLOWED_GOLD_INGOT.get(), EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY))
                .addPage(CraftingBookPage.ringPage(ORNATE_RING.get(), LEATHER, SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new CraftingBookPage(ORNATE_NECKLACE.get(), EMPTY, STRING, EMPTY, STRING, EMPTY, STRING, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY))

                .addPage(new HeadlineTextPage("arcane_reach", "arcane_reach"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ARCANE_REACH.get()))
                .addPage(new HeadlineTextPage("arcane_spoil", "arcane_spoil"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ARCANE_SPOIL.get()))
        );

        ENTRIES.add(new BookEntry(
                "ring_of_prowess", RING_OF_PROWESS.get(), -7, 6)
                .addPage(new HeadlineTextPage("ring_of_prowess", "ring_of_prowess_a"))
                .addPage(new TextPage("ring_of_prowess_b"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_PROWESS.get()))
        );

        ENTRIES.add(new BookEntry(
                "ring_of_wicked_intent", RING_OF_WICKED_INTENT.get(), -7, 8)
                .addPage(new HeadlineTextPage("ring_of_wicked_intent", "ring_of_wicked_intent"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_WICKED_INTENT.get()))
        );

        ENTRIES.add(new BookEntry(
                "ring_of_curative_talent", RING_OF_CURATIVE_TALENT.get(), -7, 4)
                .addPage(new HeadlineTextPage("ring_of_curative_talent", "ring_of_curative_talent"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_CURATIVE_TALENT.get()))
        );

        ENTRIES.add(new BookEntry(
                "necklace_of_the_mystic_mirror", NECKLACE_OF_THE_MYSTIC_MIRROR.get(), -6, 5)
                .addPage(new HeadlineTextPage("necklace_of_the_mystic_mirror", "necklace_of_the_mystic_mirror"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_MYSTIC_MIRROR.get()))
        );

        ENTRIES.add(new BookEntry(
                "necklace_of_the_narrow_edge", NECKLACE_OF_THE_NARROW_EDGE.get(), -8, 7)
                .addPage(new HeadlineTextPage("necklace_of_the_narrow_edge", "necklace_of_the_narrow_edge"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_NARROW_EDGE.get()))
        );

        ENTRIES.add(new BookEntry(
                "warded_belt", WARDED_BELT.get(), -9, 5)
                .addPage(new HeadlineTextPage("warded_belt", "warded_belt"))
                .addPage(SpiritInfusionPage.fromOutput(WARDED_BELT.get()))
        );

        ENTRIES.add(new BookEntry(
                "mirror_magic", SPECTRAL_LENS.get(), -6, 10)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("mirror_magic", "mirror_magic"))
                .addPage(SpiritInfusionPage.fromOutput(SPECTRAL_LENS.get()))
        );

        ENTRIES.add(new BookEntry(
                "voodoo_magic", POPPET.get(), 6, 10)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("voodoo_magic", "voodoo_magic"))
                .addPage(SpiritInfusionPage.fromOutput(POPPET.get()))
        );

        ENTRIES.add(new BookEntry(
                "altar_acceleration", RUNEWOOD_OBELISK.get(), -1, 8)
                .addPage(new HeadlineTextPage("runewood_obelisk", "runewood_obelisk"))
                .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_OBELISK.get()))
                .addPage(new HeadlineTextPage("brilliant_obelisk", "brilliant_obelisk"))
                .addPage(SpiritInfusionPage.fromOutput(BRILLIANT_OBELISK.get()))
        );

        ENTRIES.add(new BookEntry(
                "totem_magic", RUNEWOOD_TOTEM_BASE.get(), 0, 9)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("totem_magic", "totem_magic_a"))
                .addPage(new TextPage("totem_magic_b"))
                .addPage(new TextPage("totem_magic_c"))
                .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_TOTEM_BASE.get()))
        );

        ENTRIES.add(new BookEntry(
                "arcane_rite", ARCANE_SPIRIT.get(), 0, 11)
                .addPage(new HeadlineTextPage("totem_corruption", "totem_corruption_a"))
                .addPage(new TextPage("totem_corruption_b"))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "arcane_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ARCANE_RITE))
                .addPage(new TextPage("totem_corruption_c"))
                .addPage(SpiritInfusionPage.fromOutput(SOULWOOD_TOTEM_BASE.get()))
        );

        ENTRIES.add(new BookEntry(
                "sacred_rite", SACRED_SPIRIT.get(), -2, 11)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "sacred_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.SACRED_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_sacred_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        ENTRIES.add(new BookEntry(
                "wicked_rite", WICKED_SPIRIT.get(), 2, 11)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "wicked_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.WICKED_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_wicked_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        ENTRIES.add(new BookEntry(
                "earthen_rite", EARTHEN_SPIRIT.get(), -1, 12)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "earthen_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.EARTHEN_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_earthen_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        ENTRIES.add(new BookEntry(
                "infernal_rite", INFERNAL_SPIRIT.get(), 1, 12)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "infernal_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.INFERNAL_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_infernal_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        ENTRIES.add(new BookEntry(
                "aerial_rite", AERIAL_SPIRIT.get(), -1, 10)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "aerial_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.AERIAL_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_aerial_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        ENTRIES.add(new BookEntry(
                "aqueous_rite", AQUEOUS_SPIRIT.get(), 1, 10)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "aqueous_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.AQUEOUS_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_aqueous_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        ENTRIES.add(new BookEntry(
                "soulwood", SOULWOOD_GROWTH.get(), 0, 13)
                .addPage(new HeadlineTextPage("soulwood", "soulwood_a"))
                .addPage(new TextPage("soulwood_b"))
                .addPage(CraftingBookPage.itemPedestalPage(SOULWOOD_ITEM_PEDESTAL.get(), SOULWOOD_PLANKS.get(), SOULWOOD_PLANKS_SLAB.get()))
                .addPage(CraftingBookPage.itemStandPage(SOULWOOD_ITEM_STAND.get(), SOULWOOD_PLANKS.get(), SOULWOOD_PLANKS_SLAB.get()))
                .addPage(new SmeltingBookPage(SOULWOOD_LOG.get(), ARCANE_CHARCOAL.get()))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_ARCANE_CHARCOAL.get(), ARCANE_CHARCOAL.get()))
                .addPage(new CraftingBookPage(new ItemStack(UNHOLY_SAPBALL.get(), 3), Items.SLIME_BALL, UNHOLY_SAP.get()))
                .addPage(new SmeltingBookPage(UNHOLY_SAP.get(), UNHOLY_SYRUP.get()))
        );

        ENTRIES.add(new BookEntry(
                "magebane_belt", MAGEBANE_BELT.get(), 1, 15)
                .addPage(new HeadlineTextPage("magebane_belt", "magebane_belt"))
                .addPage(SpiritInfusionPage.fromOutput(MAGEBANE_BELT.get()))
        );

        ENTRIES.add(new BookEntry(
                "tyrving", TYRVING.get(), -1, 15)
                .addPage(new HeadlineTextPage("tyrving", "tyrving_a"))
                .addPage(SpiritInfusionPage.fromOutput(TYRVING.get()))
                .addPage(new TextPage("tyrving_b"))
                .addPage(SpiritRepairPage.fromInput(TYRVING.get()))
        );

        ENTRIES.add(new BookEntry(
                "ceaseless_impetus", CEASELESS_IMPETUS.get(), 0, 16)
                .addPage(new HeadlineTextPage("ceaseless_impetus", "ceaseless_impetus_a"))
                .addPage(new TextPage("ceaseless_impetus_b"))
                .addPage(SpiritInfusionPage.fromOutput(CEASELESS_IMPETUS.get()))
                .addPage(SpiritRepairPage.fromInput(CRACKED_CEASELESS_IMPETUS.get()))
        );

        ENTRIES.add(new BookEntry(
                "huh", THE_DEVICE.get(), 0, -10)
                .setObjectSupplier(VanishingEntryObject::new)
                .addPage(new HeadlineTextPage("the_device", "the_device"))
                .addPage(new CraftingBookPage(THE_DEVICE.get(), TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get()))
        );
    }

    public void setupObjects() {
        OBJECTS.clear();
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int coreX = guiLeft + bookInsideWidth;
        int coreY = guiTop + bookInsideHeight;
        int width = 40;
        int height = 48;
        for (BookEntry entry : ENTRIES) {
            OBJECTS.add(entry.objectSupplier.getBookObject(entry, coreX + entry.xOffset * width, coreY - entry.yOffset * height));
        }
        faceObject(OBJECTS.get(0));
    }

    public void faceObject(BookObject object) {
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        xOffset = -object.posX + guiLeft + bookInsideWidth;
        yOffset = -object.posY + guiTop + bookInsideHeight;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;

        renderBackground(BACKGROUND_TEXTURE, poseStack, 0.1f, 0.4f);
        GL11.glEnable(GL_SCISSOR_TEST);
        cut();

        renderEntries(poseStack, mouseX, mouseY, partialTicks);
        ScreenParticleHandler.renderParticles(BEFORE_TOOLTIPS);
        GL11.glDisable(GL_SCISSOR_TEST);

        renderTransparentTexture(FADE_TEXTURE, poseStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        renderTexture(FRAME_TEXTURE, poseStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        lateEntryRender(poseStack, mouseX, mouseY, partialTicks);
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        xOffset += dragX;
        yOffset += dragY;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        cachedXOffset = xOffset;
        cachedYOffset = yOffset;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (ignoreNextMouseInput) {
            ignoreNextMouseInput = false;
            return super.mouseReleased(mouseX, mouseY, button);
        }
        if (xOffset != cachedXOffset || yOffset != cachedYOffset) {
            return super.mouseReleased(mouseX, mouseY, button);
        }
        for (BookObject object : OBJECTS) {
            if (object.isHovering(xOffset, yOffset, mouseX, mouseY)) {
                object.click(xOffset, yOffset, mouseX, mouseY);
                break;
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (Minecraft.getInstance().options.keyInventory.matches(keyCode, scanCode)) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void renderEntries(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int i = OBJECTS.size() - 1; i >= 0; i--) {
            BookObject object = OBJECTS.get(i);
            boolean isHovering = object.isHovering(xOffset, yOffset, mouseX, mouseY);
            object.isHovering = isHovering;
            object.hover = isHovering ? Math.min(object.hover++, object.hoverCap()) : Math.max(object.hover--, 0);
            object.render(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public void lateEntryRender(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int i = OBJECTS.size() - 1; i >= 0; i--) {
            BookObject object = OBJECTS.get(i);
            object.lateRender(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public static boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height) {
        if (!isInView(mouseX, mouseY)) {
            return false;
        }
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }

    public static boolean isInView(double mouseX, double mouseY) {
        int guiLeft = (screen.width - screen.bookWidth) / 2;
        int guiTop = (screen.height - screen.bookHeight) / 2;
        return !(mouseX < guiLeft + 17) && !(mouseY < guiTop + 14) && !(mouseX > guiLeft + (screen.bookWidth - 17)) && !(mouseY > (guiTop + screen.bookHeight - 14));
    }

    public void renderBackground(ResourceLocation texture, PoseStack poseStack, float xModifier, float yModifier) {
        int guiLeft = (width - bookWidth) / 2; //TODO: literally just redo this entire garbage method, please
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 14;
        float uOffset = (parallax_width - xOffset) * xModifier;
        float vOffset = Math.min(parallax_height - bookInsideHeight, (parallax_height - bookInsideHeight - yOffset * yModifier));
        if (vOffset <= parallax_height / 2f) {
            vOffset = parallax_height / 2f;
        }
        if (uOffset <= 0) {
            uOffset = 0;
        }
        if (uOffset > (bookInsideWidth - 8) / 2f) {
            uOffset = (bookInsideWidth - 8) / 2f;
        }
        renderTexture(texture, poseStack, insideLeft, insideTop, uOffset, vOffset, bookInsideWidth, bookInsideHeight, parallax_width / 2, parallax_height / 2);
    }

    public void cut() {
        int scale = (int) getMinecraft().getWindow().getGuiScale();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 18;
        GL11.glScissor(insideLeft * scale, insideTop * scale, bookInsideWidth * scale, (bookInsideHeight + 1) * scale); // do not ask why the 1 is needed please
    }

    public static void renderTexture(ResourceLocation texture, PoseStack poseStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.setShaderTexture(0, texture);
        RenderHelper.blit(poseStack, x, y, width, height, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void renderTransparentTexture(ResourceLocation texture, PoseStack poseStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        renderTexture(texture, poseStack, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void renderComponents(PoseStack poseStack, List<? extends IRecipeComponent> components, int left, int top, int mouseX, int mouseY, boolean vertical) {
        List<ItemStack> items = components.stream().map(IRecipeComponent::getStack).collect(Collectors.toList());
        ProgressionBookScreen.renderItemList(poseStack, items, left, top, mouseX, mouseY, vertical);
    }

    public static void renderComponent(PoseStack poseStack, IRecipeComponent component, int posX, int posY, int mouseX, int mouseY) {
        if (component.getStacks().size() == 1) {
            renderItem(poseStack, component.getStack(), posX, posY, mouseX, mouseY);
            return;
        }
        int index = (int) (Minecraft.getInstance().level.getGameTime() % (20L * component.getStacks().size()) / 20);
        ItemStack stack = component.getStacks().get(index);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderTooltip(poseStack, new TranslatableComponent(stack.getDescriptionId()), mouseX, mouseY);
        }
    }

    public static void renderItem(PoseStack poseStack, Ingredient ingredient, int posX, int posY, int mouseX, int mouseY) {
        renderItem(poseStack, List.of(ingredient.getItems()), posX, posY, mouseX, mouseY);
    }
    public static void renderItem(PoseStack poseStack, List<ItemStack> stacks, int posX, int posY, int mouseX, int mouseY) {
        if (stacks.size() == 1) {
            renderItem(poseStack, stacks.get(0), posX, posY, mouseX, mouseY);
            return;
        }
        int index = (int) (Minecraft.getInstance().level.getGameTime() % (20L * stacks.size()) / 20);
        ItemStack stack = stacks.get(index);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderTooltip(poseStack, new TranslatableComponent(stack.getDescriptionId()), mouseX, mouseY);
        }
    }
    public static void renderItem(PoseStack poseStack, ItemStack stack, int posX, int posY, int mouseX, int mouseY) {
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderTooltip(poseStack, new TranslatableComponent(stack.getDescriptionId()), mouseX, mouseY);
        }
    }
    public static void renderItemList(PoseStack poseStack, List<ItemStack> items, int left, int top, int mouseX, int mouseY, boolean vertical) {
        int slots = items.size();
        renderItemFrames(poseStack, left, top, vertical, slots);
        if (vertical) {
            top -= 10 * (slots - 1);
        } else {
            left -= 10 * (slots - 1);
        }
        for (int i = 0; i < slots; i++) {
            ItemStack stack = items.get(i);
            int offset = i * 20;
            int oLeft = left + 2 + (vertical ? 0 : offset);
            int oTop = top + 2 + (vertical ? offset : 0);
            ProgressionBookScreen.renderItem(poseStack, stack, oLeft, oTop, mouseX, mouseY);
        }
    }
    public static void renderItemFrames(PoseStack poseStack, int left, int top, boolean vertical, int slots) {
        if (vertical) {
            top -= 10 * (slots - 1);
        } else {
            left -= 10 * (slots - 1);
        }
        //item slot
        for (int i = 0; i < slots; i++) {
            int offset = i * 20;
            int oLeft = left + (vertical ? 0 : offset);
            int oTop = top + (vertical ? offset : 0);
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft, oTop, 75, 192, 20, 20, 512, 512);

            if (vertical) {
                //bottom fade
                if (slots > 1 && i != slots - 1) {
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left + 1, oTop + 19, 75, 213, 18, 2, 512, 512);
                }
                //bottommost fade
                if (i == slots - 1) {
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 1, oTop + 19, 75, 216, 18, 2, 512, 512);
                }
            } else {
                //bottom fade
                renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 1, top + 19, 75, 216, 18, 2, 512, 512);
                if (slots > 1 && i != slots - 1) {
                    //side fade
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 19, top, 96, 192, 2, 20, 512, 512);
                }
            }
        }

        //crown
        int crownLeft = left + 5 + (vertical ? 0 : 10 * (slots - 1));
        renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, crownLeft, top - 5, 128, 192, 10, 6, 512, 512);

        //side bars
        if (vertical) {
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left - 4, top - 4, 99, 200, 28, 7, 512, 512);
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left - 4, top + 17 + 20 * (slots - 1), 99, 192, 28, 7, 512, 512);
        }
        // top bars
        else {
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left - 4, top - 4, 59, 192, 7, 28, 512, 512);
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left + 17 + 20 * (slots - 1), top - 4, 67, 192, 7, 28, 512, 512);
        }
    }

    public static void renderWrappingText(PoseStack mStack, String text, int x, int y, int w) {
        Font font = Minecraft.getInstance().font;
        text = new TranslatableComponent(text).getString();
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String line = "";
        for (String s : words) {
            if (font.width(line) + font.width(s) > w) {
                lines.add(line);
                line = s + " ";
            } else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(line);
        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            renderRawText(mStack, currentLine, x, y + i * (font.lineHeight + 1), getTextGlow(i / 4f));
        }
    }

    public static void renderText(PoseStack stack, String text, int x, int y) {
        renderText(stack, new TranslatableComponent(text), x, y, getTextGlow(0));
    }

    public static void renderText(PoseStack stack, Component component, int x, int y) {
        String text = component.getString();
        renderRawText(stack, text, x, y, getTextGlow(0));
    }

    public static void renderText(PoseStack stack, String text, int x, int y, float glow) {
        renderText(stack, new TranslatableComponent(text), x, y, glow);
    }

    public static void renderText(PoseStack stack, Component component, int x, int y, float glow) {
        String text = component.getString();
        renderRawText(stack, text, x, y, glow);
    }

    private static void renderRawText(PoseStack stack, String text, int x, int y, float glow) {
        Font font = Minecraft.getInstance().font;
        //182, 61, 183  227, 39, 228
        int r = (int) Mth.lerp(glow, 182, 227);
        int g = (int) Mth.lerp(glow, 61, 39);
        int b = (int) Mth.lerp(glow, 183, 228);

        font.draw(stack, text, x - 1, y, color(96, 255, 210, 243));
        font.draw(stack, text, x + 1, y, color(128, 240, 131, 232));
        font.draw(stack, text, x, y - 1, color(128, 255, 183, 236));
        font.draw(stack, text, x, y + 1, color(96, 236, 110, 226));

        font.draw(stack, text, x, y, color(255, r, g, b));
    }

    public static float getTextGlow(float offset) {
        return Mth.sin(offset + Minecraft.getInstance().player.level.getGameTime() / 40f) / 2f + 0.5f;
    }

    public void playSound() {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void openScreen(boolean ignoreNextMouseClick) {
        Minecraft.getInstance().setScreen(getInstance());
        ScreenParticleHandler.wipeParticles();
        screen.playSound();
        screen.ignoreNextMouseInput = ignoreNextMouseClick;
    }

    public static ProgressionBookScreen getInstance() {
        if (screen == null) {
            screen = new ProgressionBookScreen();
        }
        return screen;
    }
}