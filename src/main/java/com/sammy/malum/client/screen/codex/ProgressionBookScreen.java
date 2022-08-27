package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.common.events.SetupMalumCodexEntriesEvent;
import com.sammy.malum.core.setup.content.SpiritRiteRegistry;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ShaderInstance;
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
import team.lodestar.lodestone.handlers.ScreenParticleHandler;
import team.lodestar.lodestone.setup.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.recipe.IRecipeComponent;
import team.lodestar.lodestone.systems.rendering.ExtendedShaderInstance;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.VFXBuilders.ScreenVFXBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.sammy.malum.config.ClientConfig.BOOK_THEME;
import static com.sammy.malum.core.setup.content.item.ItemRegistry.*;
import static net.minecraft.util.FastColor.ARGB32.color;
import static net.minecraft.world.item.Items.*;
import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;
import static team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle.RenderOrder.BEFORE_TOOLTIPS;

public class ProgressionBookScreen extends Screen {
    public enum BookTheme {
        DEFAULT, EASY_READING
    }

    public static final ScreenVFXBuilder BUILDER = VFXBuilders.createScreen().setPosTexDefaultFormat();

    public static final ResourceLocation FRAME_TEXTURE = MalumMod.malumPath("textures/gui/book/frame.png");
    public static final ResourceLocation FADE_TEXTURE = MalumMod.malumPath("textures/gui/book/fade.png");

    public static final ResourceLocation BACKGROUND_TEXTURE = MalumMod.malumPath("textures/gui/book/background.png");

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

    public static List<BookEntry> ENTRIES = new ArrayList<>();
    public static List<BookObject> OBJECTS = new ArrayList<>();

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
            .addPage(new HeadlineTextPage("introduction", "introduction.1"))
            .addPage(new TextPage("introduction.2"))
            .addPage(new TextPage("introduction.3"))
            .addPage(new TextPage("introduction.4"))
            .addPage(new TextPage("introduction.5"))
        );

        ENTRIES.add(new BookEntry(
            "spirit_crystals", ARCANE_SPIRIT.get(), 0, 1)
            .addPage(new HeadlineTextPage("spirit_crystals", "spirit_crystals.1"))
            .addPage(new TextPage("spirit_crystals.2"))
            .addPage(new TextPage("spirit_crystals.3"))
        );

        ENTRIES.add(new BookEntry(
            "runewood", RUNEWOOD_SAPLING.get(), 1, 2)
            .addPage(new HeadlineTextItemPage("runewood", "runewood.1", RUNEWOOD_SAPLING.get()))
            .addPage(new HeadlineTextPage("runewood.arcane_charcoal", "runewood.arcane_charcoal.1"))
            .addPage(new SmeltingBookPage(RUNEWOOD_LOG.get(), ARCANE_CHARCOAL.get()))
            .addPage(CraftingBookPage.fullPage(BLOCK_OF_ARCANE_CHARCOAL.get(), ARCANE_CHARCOAL.get()))
            .addPage(new HeadlineTextPage("runewood.holy_sap", "runewood.holy_sap.1"))
            .addPage(new SmeltingBookPage(HOLY_SAP.get(), HOLY_SYRUP.get()))
            .addPage(new CraftingBookPage(new ItemStack(HOLY_SAPBALL.get(), 3), Items.SLIME_BALL, HOLY_SAP.get()))
        );

        ENTRIES.add(new BookEntry(
            "natural_quartz", NATURAL_QUARTZ.get(), 3, 1)
            .setObjectSupplier(MinorEntryObject::new)
            .addPage(new HeadlineTextItemPage("natural_quartz", "natural_quartz.1", NATURAL_QUARTZ.get()))
        );

        ENTRIES.add(new BookEntry(
            "blazing_quartz", BLAZING_QUARTZ.get(), 4, 2)
            .setObjectSupplier(MinorEntryObject::new)
            .addPage(new HeadlineTextItemPage("blazing_quartz", "blazing_quartz.1", BLAZING_QUARTZ.get()))
            .addPage(CraftingBookPage.fullPage(BLOCK_OF_BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get()))
        );

        ENTRIES.add(new BookEntry(
            "brilliance", CLUSTER_OF_BRILLIANCE.get(), -3, 1)
            .setObjectSupplier(MinorEntryObject::new)
            .addPage(new HeadlineTextItemPage("brilliance", "brilliance.1", CLUSTER_OF_BRILLIANCE.get()))
            .addPage(new TextPage("brilliance.2"))
            .addPage(CraftingBookPage.fullPage(BLOCK_OF_BRILLIANCE.get(), CLUSTER_OF_BRILLIANCE.get()))
            .addPage(new SmeltingBookPage(new ItemStack(CLUSTER_OF_BRILLIANCE.get()), new ItemStack(CHUNK_OF_BRILLIANCE.get(), 2)))
        );

        ENTRIES.add(new BookEntry(
            "rare_earths", RARE_EARTHS.get(), -4, 2)
            .setObjectSupplier(MinorEntryObject::new)
            .addPage(new HeadlineTextItemPage("rare_earths", "rare_earths", RARE_EARTHS.get()))
        );

        ENTRIES.add(new BookEntry(
            "soulstone", PROCESSED_SOULSTONE.get(), -1, 2)
            .addPage(new HeadlineTextItemPage("soulstone", "soulstone.1", PROCESSED_SOULSTONE.get()))
            .addPage(new TextPage("soulstone.2"))
            .addPage(new SmeltingBookPage(new ItemStack(RAW_SOULSTONE.get()), new ItemStack(PROCESSED_SOULSTONE.get(), 2)))
            .addPage(CraftingBookPage.fullPage(BLOCK_OF_SOULSTONE.get(), PROCESSED_SOULSTONE.get()))
            .addPage(CraftingBookPage.fullPage(BLOCK_OF_RAW_SOULSTONE.get(), RAW_SOULSTONE.get()))
        );

        ENTRIES.add(new BookEntry(
            "scythes", CRUDE_SCYTHE.get(), 0, 3)
            .addPage(new HeadlineTextPage("scythes", "scythes.1"))
            .addPage(CraftingBookPage.scythePage(ItemRegistry.CRUDE_SCYTHE.get(), Items.IRON_INGOT, PROCESSED_SOULSTONE.get()))
            .addPage(new TextPage("scythes.2"))
            .addPage(new HeadlineTextPage("scythes.enchanting", "scythes.enchanting.1"))
            .addPage(new HeadlineTextPage("scythes.enchanting.haunted", "scythes.enchanting.haunted.1"))
            .addPage(new HeadlineTextPage("scythes.enchanting.spirit_plunder", "scythes.enchanting.spirit_plunder.1"))
            .addPage(new HeadlineTextPage("scythes.enchanting.rebound", "scythes.enchanting.rebound.1"))
        );

        ENTRIES.add(new BookEntry(
            "spirit_infusion", SPIRIT_ALTAR.get(), 0, 5)
            .setObjectSupplier(ImportantEntryObject::new)
            .addPage(new HeadlineTextPage("spirit_infusion", "spirit_infusion.1"))
            .addPage(new CraftingBookPage(SPIRIT_ALTAR.get(), AIR, PROCESSED_SOULSTONE.get(), AIR, GOLD_INGOT, RUNEWOOD_PLANKS.get(), GOLD_INGOT, RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get()))
            .addPage(new TextPage("spirit_infusion.2"))
            .addPage(new TextPage("spirit_infusion.3"))
            .addPage(CraftingBookPage.itemPedestalPage(RUNEWOOD_ITEM_PEDESTAL.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
            .addPage(CraftingBookPage.itemStandPage(RUNEWOOD_ITEM_STAND.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
            .addPage(new HeadlineTextPage("spirit_infusion.hex_ash", "spirit_infusion.hex_ash.1"))
            .addPage(SpiritInfusionPage.fromOutput(HEX_ASH.get()))
        );

        ENTRIES.add(new BookEntry(
            "esoteric_reaping", ROTTING_ESSENCE.get(), 0, 6)
            .setObjectSupplier(MinorEntryObject::new)
            .addPage(new HeadlineTextPage("esoteric_reaping", "esoteric_reaping.1"))
            .addPage(new TextPage("esoteric_reaping.2"))
            .addPage(new TextPage("esoteric_reaping.3"))
            .addPage(new HeadlineTextItemPage("esoteric_reaping.rotting_essence", "esoteric_reaping.rotting_essence.1", ROTTING_ESSENCE.get()))
            .addPage(new HeadlineTextItemPage("esoteric_reaping.grim_talc", "esoteric_reaping.grim_talc.1", GRIM_TALC.get()))
            .addPage(new HeadlineTextItemPage("esoteric_reaping.astral_weave", "esoteric_reaping.astral_weave.1", ASTRAL_WEAVE.get()))
            .addPage(new HeadlineTextItemPage("esoteric_reaping.calx", "esoteric_reaping.calx.1", ALCHEMICAL_CALX.get()))
        );

        ENTRIES.add(new BookEntry(
            "primary_arcana", SACRED_SPIRIT.get(), -2, 4)
            .addPage(new HeadlineTextItemPage("primary_arcana.sacred", "primary_arcana.sacred.1", SACRED_SPIRIT.get()))
            .addPage(new TextPage("primary_arcana.sacred.2"))
            .addPage(new HeadlineTextItemPage("primary_arcana.wicked", "primary_arcana.wicked.1", WICKED_SPIRIT.get()))
            .addPage(new TextPage("primary_arcana.wicked.2"))
            .addPage(new HeadlineTextItemPage("primary_arcana.arcane", "primary_arcana.arcane.1", ARCANE_SPIRIT.get()))
            .addPage(new TextPage("primary_arcana.arcane.2"))
            .addPage(new TextPage("primary_arcana.arcane.3"))
        );

        ENTRIES.add(new BookEntry(
            "elemental_arcana", EARTHEN_SPIRIT.get(), 2, 4)
            .addPage(new HeadlineTextItemPage("elemental_arcana.aerial", "elemental_arcana.aerial.1", AERIAL_SPIRIT.get()))
            .addPage(new TextPage("elemental_arcana.aerial.2"))
            .addPage(new HeadlineTextItemPage("elemental_arcana.earthen", "elemental_arcana.earthen.1", EARTHEN_SPIRIT.get()))
            .addPage(new TextPage("elemental_arcana.earthen.2"))
            .addPage(new HeadlineTextItemPage("elemental_arcana.infernal", "elemental_arcana.infernal.1", INFERNAL_SPIRIT.get()))
            .addPage(new TextPage("elemental_arcana.infernal.2"))
            .addPage(new HeadlineTextItemPage("elemental_arcana.aqueous", "elemental_arcana.aqueous.1", AQUEOUS_SPIRIT.get()))
            .addPage(new TextPage("elemental_arcana.aqueous.2"))
        );

        ENTRIES.add(new BookEntry(
            "eldritch_arcana", ELDRITCH_SPIRIT.get(), 0, 7)
            .addPage(new HeadlineTextItemPage("eldritch_arcana", "eldritch_arcana.1", ELDRITCH_SPIRIT.get()))
            .addPage(new TextPage("eldritch_arcana.2"))
        );

        ENTRIES.add(new BookEntry(
            "spirit_stones", TAINTED_ROCK.get(), 3, 6)
            .addPage(new HeadlineTextPage("spirit_stones.tainted_rock", "spirit_stones.tainted_rock.1"))
            .addPage(SpiritInfusionPage.fromOutput(TAINTED_ROCK.get()))
            .addPage(CraftingBookPage.itemPedestalPage(TAINTED_ROCK_ITEM_PEDESTAL.get(), TAINTED_ROCK.get(), TAINTED_ROCK_SLAB.get()))
            .addPage(CraftingBookPage.itemStandPage(TAINTED_ROCK_ITEM_STAND.get(), TAINTED_ROCK.get(), TAINTED_ROCK_SLAB.get()))
            .addPage(new HeadlineTextPage("spirit_stones.twisted_rock", "spirit_stones.twisted_rock.1"))
            .addPage(SpiritInfusionPage.fromOutput(TWISTED_ROCK.get()))
            .addPage(CraftingBookPage.itemPedestalPage(TWISTED_ROCK_ITEM_PEDESTAL.get(), TWISTED_ROCK.get(), TWISTED_ROCK_SLAB.get()))
            .addPage(CraftingBookPage.itemStandPage(TWISTED_ROCK_ITEM_STAND.get(), TWISTED_ROCK.get(), TWISTED_ROCK_SLAB.get()))
        );
        ENTRIES.add(new BookEntry(
            "ether", ETHER.get(), 5, 6)
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

        ENTRIES.add(new BookEntry(
            "spirit_fabric", SPIRIT_FABRIC.get(), 4, 5)
            .addPage(new HeadlineTextPage("spirit_fabric", "spirit_fabric.1"))
            .addPage(SpiritInfusionPage.fromOutput(SPIRIT_FABRIC.get()))
            .addPage(new HeadlineTextPage("spirit_fabric.pouch", "spirit_fabric.pouch.1"))
            .addPage(new CraftingBookPage(SPIRIT_POUCH.get(), EMPTY, STRING, EMPTY, SPIRIT_FABRIC.get(), SOUL_SAND, SPIRIT_FABRIC.get(), EMPTY, SPIRIT_FABRIC.get(), EMPTY))
        );

        ENTRIES.add(new BookEntry(
            "soulhunter_gear", SOUL_HUNTER_CLOAK.get(), 4, 7)
            .addPage(new HeadlineTextPage("soulhunter_gear", "soulhunter_gear.1"))
            .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_CLOAK.get()))
            .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_ROBE.get()))
            .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_LEGGINGS.get()))
            .addPage(SpiritInfusionPage.fromOutput(SOUL_HUNTER_BOOTS.get()))
        );

        ENTRIES.add(new BookEntry(
            "spirit_focusing", SPIRIT_CRUCIBLE.get(), 7, 6)
            .addPage(new HeadlineTextItemPage("spirit_focusing", "spirit_focusing.1", SPIRIT_CRUCIBLE.get()))
            .addPage(new TextPage("spirit_focusing.2"))
            .addPage(SpiritInfusionPage.fromOutput(SPIRIT_CRUCIBLE.get()))
            .addPage(SpiritInfusionPage.fromOutput(ALCHEMICAL_IMPETUS.get()))
        );

        ENTRIES.add(new BookEntry(
            "focus_ashes", GUNPOWDER, 6, 5)
            .addPage(new HeadlineTextPage("focus_ashes", "focus_ashes.1"))
            .addPage(SpiritCruciblePage.fromOutput(GUNPOWDER))
            .addPage(SpiritCruciblePage.fromOutput(GLOWSTONE_DUST))
            .addPage(SpiritCruciblePage.fromOutput(REDSTONE))
        );

        ENTRIES.add(new BookEntry(
            "focus_metals", IRON_NODE.get(), 8, 7)
            .addPage(new HeadlineTextItemPage("focus_metals", "focus_metals.1", IRON_NODE.get()))
            .addPage(new TextPage("focus_metals.2"))
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
            "focus_crystals", QUARTZ, 9, 5)
            .addPage(new HeadlineTextPage("focus_crystals", "focus_crystals.1"))
            .addPage(SpiritCruciblePage.fromOutput(QUARTZ))
            .addPage(SpiritCruciblePage.fromOutput(AMETHYST_SHARD))
            .addPage(SpiritCruciblePage.fromOutput(BLAZING_QUARTZ.get()))
            .addPage(SpiritCruciblePage.fromOutput(PRISMARINE_CRYSTALS))
        );

        ENTRIES.add(new BookEntry(
            "crucible_acceleration", SPIRIT_CATALYZER.get(), 7, 4)
            .addPage(new HeadlineTextPage("crucible_acceleration", "crucible_acceleration.1"))
            .addPage(new TextPage("crucible_acceleration.2"))
            .addPage(new TextPage("crucible_acceleration.3"))
            .addPage(SpiritInfusionPage.fromOutput(SPIRIT_CATALYZER.get()))
        );

        ENTRIES.add(new BookEntry(
            "arcane_restoration", TWISTED_TABLET.get(), 7, 8)
            .addPage(new HeadlineTextPage("arcane_restoration", "arcane_restoration.1"))
            .addPage(new TextPage("arcane_restoration.2"))
            .addPage(SpiritInfusionPage.fromOutput(TWISTED_TABLET.get()))
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

        ENTRIES.add(new BookEntry(
            "spirit_metals", SOUL_STAINED_STEEL_INGOT.get(), -3, 6)
            .addPage(new HeadlineTextItemPage("spirit_metals.hallowed_gold", "spirit_metals.hallowed_gold.1", HALLOWED_GOLD_INGOT.get()))
            .addPage(new TextPage("spirit_metals.hallowed_gold.2"))
            .addPage(SpiritInfusionPage.fromOutput(HALLOWED_GOLD_INGOT.get()))
            .addPage(CraftingBookPage.resonatorPage(HALLOWED_SPIRIT_RESONATOR.get(), QUARTZ, HALLOWED_GOLD_INGOT.get(), RUNEWOOD_PLANKS.get()))
            .addPage(new HeadlineTextPage("spirit_metals.hallowed_gold.spirit_jar", "spirit_metals.hallowed_gold.spirit_jar.1"))
            .addPage(new CraftingBookPage(SPIRIT_JAR.get(), GLASS_PANE, HALLOWED_GOLD_INGOT.get(), GLASS_PANE, GLASS_PANE, EMPTY, GLASS_PANE, GLASS_PANE, GLASS_PANE, GLASS_PANE))
            .addPage(new HeadlineTextItemPage("spirit_metals.soulstained_steel", "spirit_metals.soulstained_steel.1", SOUL_STAINED_STEEL_INGOT.get()))
            .addPage(new TextPage("spirit_metals.soulstained_steel.2"))
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
            "soulstained_scythe", SOUL_STAINED_STEEL_SCYTHE.get(), -4, 5)
            .addPage(new HeadlineTextPage("soulstained_scythe", "soulstained_scythe.1"))
            .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_SCYTHE.get()))
        );

        ENTRIES.add(new BookEntry(
            "soulstained_armor", SOUL_STAINED_STEEL_HELMET.get(), -4, 7)
            .addPage(new HeadlineTextPage("soulstained_armor", "soulstained_armor.1"))
            .addPage(new TextPage("soulstained_armor.2"))
            .addPage(new TextPage("soulstained_armor.3"))
            .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_HELMET.get()))
            .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_CHESTPLATE.get()))
            .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_LEGGINGS.get()))
            .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_BOOTS.get()))
        );

        ENTRIES.add(new BookEntry(
            "spirit_trinkets", ORNATE_RING.get(), -5, 6)
            .addPage(new HeadlineTextPage("spirit_trinkets", "spirit_trinkets.1"))
            .addPage(new TextPage("spirit_trinkets.2"))
            .addPage(CraftingBookPage.ringPage(GILDED_RING.get(), LEATHER, HALLOWED_GOLD_INGOT.get()))
            .addPage(new CraftingBookPage(GILDED_BELT.get(), LEATHER, LEATHER, LEATHER, HALLOWED_GOLD_INGOT.get(), PROCESSED_SOULSTONE.get(), HALLOWED_GOLD_INGOT.get(), EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY))
            .addPage(CraftingBookPage.ringPage(ORNATE_RING.get(), LEATHER, SOUL_STAINED_STEEL_INGOT.get()))
            .addPage(new CraftingBookPage(ORNATE_NECKLACE.get(), EMPTY, STRING, EMPTY, STRING, EMPTY, STRING, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY))
        );

        ENTRIES.add(new BookEntry(
            "reactive_trinkets", RING_OF_ALCHEMICAL_MASTERY.get(), -7, 6)
            .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_alchemical_mastery", "reactive_trinkets.ring_of_alchemical_mastery.1"))
            .addPage(SpiritInfusionPage.fromOutput(RING_OF_ALCHEMICAL_MASTERY.get()))
            .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_curative_talent", "reactive_trinkets.ring_of_curative_talent.1"))
            .addPage(SpiritInfusionPage.fromOutput(RING_OF_CURATIVE_TALENT.get()))
            .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_prowess", "reactive_trinkets.ring_of_prowess.1"))
            .addPage(new TextPage("reactive_trinkets.ring_of_prowess.2"))
            .addPage(SpiritInfusionPage.fromOutput(RING_OF_ARCANE_PROWESS.get()))
        );

//        ENTRIES.add(new BookEntry(
//            "put_something_here", ORNATE_RING.get(), -7, 4)
//        );
//
//        ENTRIES.add(new BookEntry(
//            "put_something_here_also", ORNATE_RING.get(), -7, 8)
//        );

        ENTRIES.add(new BookEntry(
            "ring_of_esoteric_spoils", RING_OF_ESOTERIC_SPOILS.get(), -9, 5)
            .addPage(new HeadlineTextPage("ring_of_esoteric_spoils", "ring_of_esoteric_spoils.1"))
            .addPage(SpiritInfusionPage.fromOutput(RING_OF_ESOTERIC_SPOILS.get()))
        );

        ENTRIES.add(new BookEntry(
            "belt_of_the_starved", BELT_OF_THE_STARVED.get(), -8, 7)
            .addPage(new HeadlineTextPage("belt_of_the_starved", "belt_of_the_starved.1"))
            .addPage(new TextPage("belt_of_the_starved.2"))
            .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_STARVED.get()))
            .addPage(new HeadlineTextPage("belt_of_the_starved.ring_of_desperate_voracity", "belt_of_the_starved.ring_of_desperate_voracity.1"))
            .addPage(SpiritInfusionPage.fromOutput(RING_OF_DESPERATE_VORACITY.get()))
        );

        ENTRIES.add(new BookEntry(
            "necklace_of_the_narrow_edge", NECKLACE_OF_THE_NARROW_EDGE.get(), -7, 8)
            .addPage(new HeadlineTextPage("necklace_of_the_narrow_edge", "necklace_of_the_narrow_edge.1"))
            .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_NARROW_EDGE.get()))
        );

        ENTRIES.add(new BookEntry(
            "belt_of_the_prospector", BELT_OF_THE_PROSPECTOR.get(), -6, 5)
            .addPage(new HeadlineTextPage("belt_of_the_prospector", "belt_of_the_prospector.1"))
            .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_PROSPECTOR.get()))
            .addPage(new HeadlineTextPage("belt_of_the_delver.ring_of_the_hoarder", "belt_of_the_delver.ring_of_the_hoarder.1"))
            .addPage(SpiritInfusionPage.fromOutput(RING_OF_THE_HOARDER.get()))
        );

//        ENTRIES.add(new BookEntry(
//            "necklace_of_elemental_shielding", GILDED_BELT.get(), -7, 4)
//            .addPage(new HeadlineTextPage("necklace_of_elemental_shielding", "necklace_of_elemental_shielding.1"))
//            .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_TIDAL_AFFINITY.get()))
//        );

        ENTRIES.add(new BookEntry(
            "necklace_of_the_mystic_mirror", NECKLACE_OF_THE_MYSTIC_MIRROR.get(), 6, 12)
            .addPage(new HeadlineTextPage("necklace_of_the_mystic_mirror", "necklace_of_the_mystic_mirror.1"))
            .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_MYSTIC_MIRROR.get()))
        );

        ENTRIES.add(new BookEntry(
            "mirror_magic", SPECTRAL_LENS.get(), 6, 10)
            .setObjectSupplier(ImportantEntryObject::new)
            .addPage(new HeadlineTextPage("mirror_magic", "mirror_magic.1"))
            .addPage(SpiritInfusionPage.fromOutput(SPECTRAL_LENS.get()))
        );

        ENTRIES.add(new BookEntry(
            "voodoo_magic", POPPET.get(), -6, 10)
            .setObjectSupplier(ImportantEntryObject::new)
            .addPage(new HeadlineTextPage("voodoo_magic", "voodoo_magic.1"))
            .addPage(SpiritInfusionPage.fromOutput(POPPET.get()))
        );

        ENTRIES.add(new BookEntry(
            "altar_acceleration", RUNEWOOD_OBELISK.get(), -1, 8)
            .addPage(new HeadlineTextPage("altar_acceleration.runewood_obelisk", "altar_acceleration.runewood_obelisk.1"))
            .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_OBELISK.get()))
            .addPage(new HeadlineTextPage("altar_acceleration.brilliant_obelisk", "altar_acceleration.brilliant_obelisk.1"))
            .addPage(SpiritInfusionPage.fromOutput(BRILLIANT_OBELISK.get()))
        );

        ENTRIES.add(new BookEntry(
            "totem_magic", RUNEWOOD_TOTEM_BASE.get(), 0, 9)
            .setObjectSupplier(ImportantEntryObject::new)
            .addPage(new HeadlineTextItemPage("totem_magic", "totem_magic.1", RUNEWOOD_TOTEM_BASE.get()))
            .addPage(new TextPage("totem_magic.2"))
            .addPage(new TextPage("totem_magic.3"))
            .addPage(new TextPage("totem_magic.4"))
            .addPage(new TextPage("totem_magic.5"))
            .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_TOTEM_BASE.get()))
        );

        ENTRIES.add(new BookEntry(
            "sacred_rite", SACRED_SPIRIT.get(), -2, 10)
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.SACRED_RITE, "sacred_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.SACRED_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "sacred_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        ENTRIES.add(new BookEntry(
            "corrupt_sacred_rite", SACRED_SPIRIT.get(), -3, 10).setSoulwood()
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.SACRED_RITE, "corrupt_sacred_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.SACRED_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "corrupt_sacred_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        ENTRIES.add(new BookEntry(
            "infernal_rite", INFERNAL_SPIRIT.get(), -3, 11)
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.INFERNAL_RITE, "infernal_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.INFERNAL_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "infernal_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        ENTRIES.add(new BookEntry(
            "corrupt_infernal_rite", INFERNAL_SPIRIT.get(), -4, 11).setSoulwood()
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.INFERNAL_RITE, "corrupt_infernal_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.INFERNAL_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "corrupt_infernal_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        ENTRIES.add(new BookEntry(
            "earthen_rite", EARTHEN_SPIRIT.get(), -3, 12)
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.EARTHEN_RITE, "earthen_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.EARTHEN_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "earthen_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        ENTRIES.add(new BookEntry(
            "corrupt_earthen_rite", EARTHEN_SPIRIT.get(), -4, 12).setSoulwood()
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.EARTHEN_RITE, "corrupt_earthen_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.EARTHEN_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "corrupt_earthen_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        ENTRIES.add(new BookEntry(
            "wicked_rite", WICKED_SPIRIT.get(), 2, 10)
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.WICKED_RITE, "wicked_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.WICKED_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "wicked_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        ENTRIES.add(new BookEntry(
            "corrupt_wicked_rite", WICKED_SPIRIT.get(), 3, 10).setSoulwood()
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.WICKED_RITE, "corrupt_wicked_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.WICKED_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "corrupt_wicked_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        ENTRIES.add(new BookEntry(
            "aerial_rite", AERIAL_SPIRIT.get(), 3, 11)
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AERIAL_RITE, "aerial_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AERIAL_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "aerial_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        ENTRIES.add(new BookEntry(
            "corrupt_aerial_rite", AERIAL_SPIRIT.get(), 4, 11).setSoulwood()
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AERIAL_RITE, "corrupt_aerial_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AERIAL_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "corrupt_aerial_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        ENTRIES.add(new BookEntry(
            "aqueous_rite", AQUEOUS_SPIRIT.get(), 3, 12)
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AQUEOUS_RITE, "aqueous_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AQUEOUS_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "aqueous_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        ENTRIES.add(new BookEntry(
            "corrupt_aqueous_rite", AQUEOUS_SPIRIT.get(), 4, 12).setSoulwood()
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AQUEOUS_RITE, "corrupt_aqueous_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AQUEOUS_RITE))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "corrupt_aqueous_rite.greater"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        ENTRIES.add(new BookEntry(
            "arcane_rite", ARCANE_SPIRIT.get(), 0, 11)
            .setObjectSupplier(RiteEntryObject::new)
            .addPage(new HeadlineTextPage("arcane_rite", "arcane_rite.description.1"))
            .addPage(new TextPage("arcane_rite.description.2"))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ARCANE_RITE, "arcane_rite"))
            .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ARCANE_RITE))
            .addPage(new TextPage("arcane_rite.description.3"))
            .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ARCANE_RITE, "corrupt_arcane_rite"))
            .addPage(new TextPage("arcane_rite.description.4"))
            .addPage(SpiritInfusionPage.fromOutput(SOULWOOD_TOTEM_BASE.get()))
        );

        ENTRIES.add(new BookEntry(
            "blight", BLIGHTED_SOIL.get(), -1, 12).setSoulwood()
            .addPage(new HeadlineTextPage("blight.intro", "blight.intro.1"))
            .addPage(new HeadlineTextPage("blight.composition", "blight.composition.1"))
            .addPage(new HeadlineTextPage("blight.spread", "blight.spread.1"))
            .addPage(new HeadlineTextPage("blight.arcane_rite", "blight.arcane_rite.1"))
        );

        ENTRIES.add(new BookEntry(
                "resource_transmutation", BLIGHTED_SOIL.get(), -1, 13).setSoulwood()
                .addPage(new HeadlineTextPage("transmutation.intro", "transmutation.intro.1"))
                .addPage(new SpiritTransmutationPage("transmutation.stone", STONE))
                .addPage(new SpiritTransmutationPage("transmutation.deepslate", DEEPSLATE))
                .addPage(new SpiritTransmutationPage("transmutation.deepslate", SMOOTH_BASALT))
        );

        ENTRIES.add(new BookEntry(
            "soulwood", SOULWOOD_GROWTH.get(), 1, 12).setSoulwood()
            .addPage(new HeadlineTextPage("soulwood.intro", "soulwood.intro.1"))
            .addPage(new HeadlineTextPage("soulwood.bonemeal", "soulwood.bonemeal.1"))
            .addPage(new HeadlineTextPage("soulwood.color", "soulwood.color.1"))
            .addPage(new HeadlineTextPage("soulwood.blight", "soulwood.blight.1"))
            .addPage(new HeadlineTextPage("soulwood.sap", "soulwood.sap.1"))
        );

        ENTRIES.add(new BookEntry(
                "alteration_plinth", ALTERATION_PLINTH.get(), 1, 13).setSoulwood()
                .addPage(new HeadlineTextPage("alteration_plinth.intro", "alteration_plinth.intro.1"))
        );

        ENTRIES.add(new BookEntry( //TODO: also name this something better
            "metallurgic_trinkets", NECKLACE_OF_BLISSFUL_HARMONY.get(), -2, 14).setSoulwood()
            .addPage(new HeadlineTextPage("necklace_of_blissful_harmony", "necklace_of_blissful_harmony"))
            .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_BLISSFUL_HARMONY.get()))
            .addPage(new HeadlineTextPage("ring_of_the_demolitionist", "ring_of_the_demolitionist"))
            .addPage(SpiritInfusionPage.fromOutput(RING_OF_THE_DEMOLITIONIST.get()))
            .addPage(new HeadlineTextPage("necklace_of_tidal_affinity", "necklace_of_tidal_affinity"))
            .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_TIDAL_AFFINITY.get()))
        );

        ENTRIES.add(new BookEntry(
            "etheric_nitrate", ETHERIC_NITRATE.get(), 2, 14).setSoulwood()
            .addPage(new HeadlineTextPage("etheric_nitrate", "etheric_nitrate.1"))
            .addPage(SpiritInfusionPage.fromOutput(ETHERIC_NITRATE.get()))
            .addPage(new HeadlineTextPage("etheric_nitrate.vivid_nitrate", "etheric_nitrate.vivid_nitrate.1"))
            .addPage(SpiritInfusionPage.fromOutput(VIVID_NITRATE.get()))
        );

        ENTRIES.add(new BookEntry(
            "corrupted_resonance", CORRUPTED_RESONANCE.get(), 0, 15).setSoulwood()
            .addPage(new HeadlineTextPage("corrupted_resonance", "corrupted_resonance.1"))
            .addPage(SpiritInfusionPage.fromOutput(CORRUPTED_RESONANCE.get()))
        );

        ENTRIES.add(new BookEntry(
            "magebane_belt", BELT_OF_THE_MAGEBANE.get(), -1, 16).setSoulwood()
            .addPage(new HeadlineTextPage("magebane_belt", "magebane_belt"))
            .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_MAGEBANE.get()))
        );

        ENTRIES.add(new BookEntry(
            "necklace_of_the_hidden_blade", NECKLACE_OF_THE_HIDDEN_BLADE.get(), 1, 16).setSoulwood()
            .addPage(new HeadlineTextPage("necklace_of_the_hidden_blade", "necklace_of_the_hidden_blade"))
            .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_HIDDEN_BLADE.get()))
        );

        ENTRIES.add(new BookEntry(
                "tyrving", TYRVING.get(), 0, 17).setSoulwood()
                .addPage(new HeadlineTextPage("tyrving", "tyrving.1"))
                .addPage(SpiritInfusionPage.fromOutput(TYRVING.get()))
                .addPage(new TextPage("tyrving.2"))
                .addPage(SpiritRepairPage.fromInput(TYRVING.get()))
        );

        ENTRIES.add(new BookEntry(
            "the_device", THE_DEVICE.get(), 0, -10)
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

    public static void renderRiteIcon(MalumRiteType rite, PoseStack stack, boolean corrupted, int x, int y) {
        renderRiteIcon(rite, stack, corrupted, x, y, 0);
    }
    public static void renderRiteIcon(MalumRiteType rite, PoseStack stack, boolean corrupted, int x, int y, int z) {
        ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
        shaderInstance.safeGetUniform("YFrequency").set(corrupted ? 5f : 11f);
        shaderInstance.safeGetUniform("XFrequency").set(corrupted ? 12f : 17f);
        shaderInstance.safeGetUniform("Speed").set(2000f * (corrupted ? -0.75f : 1));
        shaderInstance.safeGetUniform("Intensity").set(corrupted ? 14f : 50f);
        Supplier<ShaderInstance> shaderInstanceSupplier = () -> shaderInstance;
        Color color = rite.getEffectSpirit().getColor();

        VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                .setPosColorTexLightmapDefaultFormat()
                .setShader(shaderInstanceSupplier)
                .setColor(color)
                .setAlpha(0.9f)
                .setZLevel(z)
                .setShader(() -> shaderInstance);

        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        renderTexture(rite.getIcon(), stack, builder, x, y, 0, 0, 16, 16, 16, 16);
        builder.setAlpha(0.4f);
        renderTexture(rite.getIcon(), stack, builder, x - 1, y, 0, 0, 16, 16, 16, 16);
        renderTexture(rite.getIcon(), stack, builder, x + 1, y, 0, 0, 16, 16, 16, 16);
        renderTexture(rite.getIcon(), stack, builder, x, y - 1, 0, 0, 16, 16, 16, 16);
        if (corrupted) {
            builder.setColor(rite.getEffectSpirit().getEndColor());
        }
        renderTexture(rite.getIcon(), stack, builder, x, y + 1, 0, 0, 16, 16, 16, 16);
        shaderInstance.setUniformDefaults();
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }
    public static void renderTexture(ResourceLocation texture, PoseStack poseStack, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        renderTexture(texture, poseStack, BUILDER, x, y, u, v, width, height, textureWidth, textureHeight);
    }
    public static void renderTexture(ResourceLocation texture, PoseStack poseStack, ScreenVFXBuilder builder, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        builder.setPositionWithWidth(x, y, width, height)
                .setShaderTexture(texture)
                .setUVWithWidth(u, v, width, height, textureWidth, textureHeight)
                .draw(poseStack);
    }

    public static void renderTransparentTexture(ResourceLocation texture, PoseStack poseStack, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        renderTransparentTexture(texture, poseStack, BUILDER, x, y, u, v, width, height, textureWidth, textureHeight);
    }
    public static void renderTransparentTexture(ResourceLocation texture, PoseStack poseStack, ScreenVFXBuilder builder, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        renderTexture(texture, poseStack, builder, x, y, u, v, width, height, textureWidth, textureHeight);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void renderComponents(PoseStack poseStack, List<? extends IRecipeComponent> components, int left, int top, int mouseX, int mouseY, boolean vertical) {
        List<ItemStack> items = components.stream().map(IRecipeComponent::getStack).collect(Collectors.toList());
        ProgressionBookScreen.renderItemList(poseStack, items, left, top, mouseX, mouseY, vertical).run();
    }

    public static Runnable renderBufferedComponents(PoseStack poseStack, List<? extends IRecipeComponent> components, int left, int top, int mouseX, int mouseY, boolean vertical) {
        List<ItemStack> items = components.stream().map(IRecipeComponent::getStack).collect(Collectors.toList());
        return ProgressionBookScreen.renderItemList(poseStack, items, left, top, mouseX, mouseY, vertical);
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
            screen.renderComponentTooltip(poseStack, screen.getTooltipFromItem(stack), mouseX, mouseY);
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
            screen.renderComponentTooltip(poseStack, screen.getTooltipFromItem(stack), mouseX, mouseY);
        }
    }

    public static void renderItem(PoseStack poseStack, ItemStack stack, int posX, int posY, int mouseX, int mouseY) {
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderComponentTooltip(poseStack, screen.getTooltipFromItem(stack), mouseX, mouseY);
        }
    }

    public static Runnable renderItemList(PoseStack poseStack, List<ItemStack> items, int left, int top, int mouseX, int mouseY, boolean vertical) {
        int slots = items.size();
        renderItemFrames(poseStack, slots, left, top, vertical);
        return () -> {
            int finalLeft = left;
            int finalTop = top;
            if (vertical) {
                finalTop -= 10 * (slots - 1);
            } else {
                finalLeft -= 10 * (slots - 1);
            }
            for (int i = 0; i < slots; i++) {
                ItemStack stack = items.get(i);
                int offset = i * 20;
                int oLeft = finalLeft + 2 + (vertical ? 0 : offset);
                int oTop = finalTop + 2 + (vertical ? offset : 0);
                ProgressionBookScreen.renderItem(poseStack, stack, oLeft, oTop, mouseX, mouseY);
            }
        };
    }

    public static void renderItemFrames(PoseStack poseStack, int slots, int left, int top, boolean vertical) {
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

        //side-bars
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
        text = new TranslatableComponent(text).getString() + "\n";
        List<String> lines = new ArrayList<>();

        boolean italic = false;
        boolean bold = false;
        boolean strikethrough = false;
        boolean underline = false;
        boolean obfuscated = false;

        StringBuilder line = new StringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char chr = text.charAt(i);
            if (chr == ' ' || chr == '\n') {
                if (word.length() > 0) {
                    if (font.width(line.toString()) + font.width(word.toString()) > w) {
                        line = newLine(lines, italic, bold, strikethrough, underline, obfuscated, line);
                    }
                    line.append(word).append(' ');
                    word = new StringBuilder();
                }

                String noFormatting = ChatFormatting.stripFormatting(line.toString());

                if (chr == '\n' && !(noFormatting == null || noFormatting.isEmpty())) {
                    line = newLine(lines, italic, bold, strikethrough, underline, obfuscated, line);
                }
            } else if (chr == '$') {
                if (i != text.length() - 1) {
                    char peek = text.charAt(i + 1);
                    switch (peek) {
                        case 'i' -> {
                            word.append(ChatFormatting.ITALIC);
                            italic = true;
                            i++;
                        }
                        case 'b' -> {
                            word.append(ChatFormatting.BOLD);
                            bold = true;
                            i++;
                        }
                        case 's' -> {
                            word.append(ChatFormatting.STRIKETHROUGH);
                            strikethrough = true;
                            i++;
                        }
                        case 'u' -> {
                            word.append(ChatFormatting.UNDERLINE);
                            underline = true;
                            i++;
                        }
                        case 'k' -> {
                            word.append(ChatFormatting.OBFUSCATED);
                            obfuscated = true;
                            i++;
                        }
                        default -> word.append(chr);
                    }
                } else {
                    word.append(chr);
                }
            } else if (chr == '/') {
                if (i != text.length() - 1) {
                    char peek = text.charAt(i + 1);
                    if (peek == '$') {
                        italic = bold = strikethrough = underline = obfuscated = false;
                        word.append(ChatFormatting.RESET);
                        i++;
                    } else
                        word.append(chr);
                } else
                    word.append(chr);
            } else {
                word.append(chr);
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            renderRawText(mStack, currentLine, x, y + i * (font.lineHeight + 1), getTextGlow(i / 4f));
        }
    }

    private static StringBuilder newLine(List<String> lines, boolean italic, boolean bold, boolean strikethrough, boolean underline, boolean obfuscated, StringBuilder line) {
        lines.add(line.toString());
        line = new StringBuilder();
        if (italic) line.append(ChatFormatting.ITALIC);
        if (bold) line.append(ChatFormatting.BOLD);
        if (strikethrough) line.append(ChatFormatting.STRIKETHROUGH);
        if (underline) line.append(ChatFormatting.UNDERLINE);
        if (obfuscated) line.append(ChatFormatting.OBFUSCATED);
        return line;
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
        if (BOOK_THEME.getConfigValue().equals(BookTheme.EASY_READING)) {
            font.draw(stack, text, x, y, 0);
            return;
        }

        glow = Easing.CUBIC_IN.ease(glow, 0, 1, 1);
        int r = (int) Mth.lerp(glow, 163, 227);
        int g = (int) Mth.lerp(glow, 44, 39);
        int b = (int) Mth.lerp(glow, 191, 228);

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
