package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.common.events.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.handlers.screenparticle.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static net.minecraft.world.item.Items.*;
import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class ProgressionBookScreen extends AbstractProgressionCodexScreen {

    public static ProgressionBookScreen screen;

    public static final List<BookEntry> ENTRIES = new ArrayList<>();

    public static final ResourceLocation FRAME_TEXTURE = malumPath("textures/gui/book/frame.png");
    public static final ResourceLocation FADE_TEXTURE = malumPath("textures/gui/book/fade.png");
    public static final ResourceLocation BACKGROUND_TEXTURE = malumPath("textures/gui/book/background.png");

    protected ProgressionBookScreen() {
        super(1024, 2560);
        minecraft = Minecraft.getInstance();
        setupEntries();
        MinecraftForge.EVENT_BUS.post(new SetupMalumCodexEntriesEvent());
        setupObjects();
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
        ScreenParticleHandler.renderEarlyParticles();
        GL11.glDisable(GL_SCISSOR_TEST);

        renderTransparentTexture(FADE_TEXTURE, poseStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        renderTexture(FRAME_TEXTURE, poseStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        lateEntryRender(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public Collection<BookEntry> getEntries() {
        return ENTRIES;
    }

    @Override
    public Supplier<SoundEvent> getSweetenerSound() {
        return SoundRegistry.ARCANA_SWEETENER_NORMAL;
    }

    @Override
    public void onClose() {
        super.onClose();
        playSweetenedSound(SoundRegistry.ARCANA_CODEX_CLOSE, 0.75f);
    }

    public static void openScreen(boolean ignoreNextMouseClick) {
        if (screen == null) {
            screen = new ProgressionBookScreen();
        }
        Minecraft.getInstance().setScreen(screen);
        ScreenParticleHandler.clearParticles();
        screen.ignoreNextMouseInput = ignoreNextMouseClick;
    }

    public static void openCodexViaItem() {
        openScreen(true);
        screen.playSweetenedSound(SoundRegistry.ARCANA_CODEX_OPEN, 1.25f);
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
                "spirit_crystals", 0, 1)
                .setObjectSupplier((e, x, y) -> new IconObject(e, malumPath("textures/gui/book/icons/soul_shard.png"), x, y))
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
                "cthonic_gold", CTHONIC_GOLD.get(), -4, 2)
                .setObjectSupplier(MinorEntryObject::new)
                .addPage(new HeadlineTextItemPage("cthonic_gold", "cthonic_gold.1", CTHONIC_GOLD.get()))
                .addPage(new TextPage("cthonic_gold.2"))
                .addPage(new TextPage("cthonic_gold.3"))
                .addPage(new TextPage("cthonic_gold.4"))
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
                .addPage(new HeadlineTextItemPage("esoteric_reaping.calx", "esoteric_reaping.calx.1", ALCHEMICAL_CALX.get()))
                .addPage(new HeadlineTextItemPage("esoteric_reaping.astral_weave", "esoteric_reaping.astral_weave.1", ASTRAL_WEAVE.get()))
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


//        ENTRIES.add(new BookEntry(
//                "soul_something", 3, 8)
//                .setObjectSupplier((e, x, y) -> new IconObject(e, malumPath("textures/gui/book/icons/soul_blade.png"), x, y))
//        );

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
                .addPage(SpiritCruciblePage.fromOutput(PRISMARINE))
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
                .addPage(new HeadlineTextItemPage("spirit_metals.hallowed_gold", "spirit_metals.hallowed_gold.1", HALLOWED_GOLD_INGOT.get()))
                .addPage(new TextPage("spirit_metals.hallowed_gold.2"))
                .addPage(SpiritInfusionPage.fromOutput(HALLOWED_GOLD_INGOT.get()))
                .addPage(CraftingBookPage.resonatorPage(HALLOWED_SPIRIT_RESONATOR.get(), QUARTZ, HALLOWED_GOLD_INGOT.get(), RUNEWOOD_PLANKS.get()))
                .addPage(new HeadlineTextPage("spirit_metals.hallowed_gold.spirit_jar", "spirit_metals.hallowed_gold.spirit_jar.1"))
                .addPage(new CraftingBookPage(SPIRIT_JAR.get(), GLASS_PANE, HALLOWED_GOLD_INGOT.get(), GLASS_PANE, GLASS_PANE, EMPTY, GLASS_PANE, GLASS_PANE, GLASS_PANE, GLASS_PANE))
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

//        ENTRIES.add(new BookEntry(
//                "soul_ward", -3, 8)
//                .setObjectSupplier((e, x, y) -> new IconObject(e, malumPath("textures/gui/book/icons/soul_ward.png"), x, y))
//        );

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
                "reactive_trinkets", RING_OF_CURATIVE_TALENT.get(), -7, 6)
                .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_curative_talent", "reactive_trinkets.ring_of_curative_talent.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_CURATIVE_TALENT.get()))
                .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_alchemical_mastery", "reactive_trinkets.ring_of_alchemical_mastery.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ALCHEMICAL_MASTERY.get()))
                .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_prowess", "reactive_trinkets.ring_of_prowess.1"))
                .addPage(new TextPage("reactive_trinkets.ring_of_prowess.2"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ARCANE_PROWESS.get()))
        );

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
                .addPage(new HeadlineTextPage("belt_of_the_prospector.ring_of_the_hoarder", "belt_of_the_prospector.ring_of_the_hoarder.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_THE_HOARDER.get()))
        );

        ENTRIES.add(new BookEntry(
                "necklace_of_blissful_harmony", NECKLACE_OF_BLISSFUL_HARMONY.get(), -7, 4)
                .addPage(new HeadlineTextPage("necklace_of_blissful_harmony", "necklace_of_blissful_harmony.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_BLISSFUL_HARMONY.get()))
                .addPage(new TextPage("necklace_of_blissful_harmony.2"))
        );

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
                "sacred_rite", -2, 10)
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.SACRED_RITE, "sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.SACRED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "sacred_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        ENTRIES.add(new BookEntry(
                "corrupt_sacred_rite", -3, 10).setSoulwood()
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.SACRED_RITE, "corrupt_sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.SACRED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "corrupt_sacred_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        ENTRIES.add(new BookEntry(
                "infernal_rite", -3, 11)
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.INFERNAL_RITE, "infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.INFERNAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "infernal_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        ENTRIES.add(new BookEntry(
                "corrupt_infernal_rite", -4, 11).setSoulwood()
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.INFERNAL_RITE, "corrupt_infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.INFERNAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "corrupt_infernal_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        ENTRIES.add(new BookEntry(
                "earthen_rite", -3, 12)
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.EARTHEN_RITE, "earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.EARTHEN_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "earthen_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        ENTRIES.add(new BookEntry(
                "corrupt_earthen_rite", -4, 12).setSoulwood()
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.EARTHEN_RITE, "corrupt_earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.EARTHEN_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "corrupt_earthen_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        ENTRIES.add(new BookEntry(
                "wicked_rite", 2, 10)
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.WICKED_RITE, "wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.WICKED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "wicked_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        ENTRIES.add(new BookEntry(
                "corrupt_wicked_rite", 3, 10).setSoulwood()
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.WICKED_RITE, "corrupt_wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.WICKED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "corrupt_wicked_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        ENTRIES.add(new BookEntry(
                "aerial_rite", 3, 11)
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AERIAL_RITE, "aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AERIAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "aerial_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        ENTRIES.add(new BookEntry(
                "corrupt_aerial_rite", 4, 11).setSoulwood()
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AERIAL_RITE, "corrupt_aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AERIAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "corrupt_aerial_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        ENTRIES.add(new BookEntry(
                "aqueous_rite", 3, 12)
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AQUEOUS_RITE, "aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AQUEOUS_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "aqueous_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        ENTRIES.add(new BookEntry(
                "corrupt_aqueous_rite", 4, 12).setSoulwood()
                .setObjectSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AQUEOUS_RITE, "corrupt_aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AQUEOUS_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "corrupt_aqueous_rite.greater"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        ENTRIES.add(new BookEntry(
                "arcane_rite", 0, 11)
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
                "blight", BLIGHTED_GUNK.get(), -1, 12).setSoulwood()
                .setObjectSupplier(MinorEntryObject::new)
                .addPage(new HeadlineTextPage("blight.intro", "blight.intro.1"))
                .addPage(new HeadlineTextPage("blight.composition", "blight.composition.1"))
                .addPage(new HeadlineTextPage("blight.spread", "blight.spread.1"))
                .addPage(new HeadlineTextPage("blight.arcane_rite", "blight.arcane_rite.1"))
        );

        ENTRIES.add(new BookEntry(
                "soulwood", SOULWOOD_GROWTH.get(), 1, 12).setSoulwood()
                .setObjectSupplier(MinorEntryObject::new)
                .addPage(new HeadlineTextPage("soulwood.intro", "soulwood.intro.1"))
                .addPage(new HeadlineTextPage("soulwood.bonemeal", "soulwood.bonemeal.1"))
                .addPage(new HeadlineTextPage("soulwood.color", "soulwood.color.1"))
                .addPage(new HeadlineTextPage("soulwood.blight", "soulwood.blight.1"))
                .addPage(new HeadlineTextPage("soulwood.sap", "soulwood.sap.1"))
        );
        ENTRIES.add(new BookEntry(
                "transmutation", SOUL_SAND, 0, 13).setSoulwood()
                .addPage(new HeadlineTextPage("transmutation", "transmutation.intro.1"))
                .addPage(new TextPage("transmutation.intro.2"))
                .addPage(new SpiritTransmutationPage("transmutation.stone", STONE))
                .addPage(new SpiritTransmutationPage("transmutation.deepslate", DEEPSLATE))
                .addPage(new SpiritTransmutationPage("transmutation.smooth_basalt", SMOOTH_BASALT))
        );

//        ENTRIES.add(new BookEntry(
//                "cursed_grit", CURSED_GRIT.get(), -2, 14).setSoulwood()
//                .addPage(new HeadlineTextPage("cursed_grit", "cursed_grit.1"))
//                .addPage(SpiritInfusionPage.fromOutput(CURSED_GRIT.get()))
//        );
//
//        ENTRIES.add(new BookEntry(
//                "etheric_nitrate", ETHERIC_NITRATE.get(), -3, 15).setSoulwood()
//                .addPage(new HeadlineTextPage("etheric_nitrate", "etheric_nitrate.1"))
//                .addPage(SpiritInfusionPage.fromOutput(ETHERIC_NITRATE.get()))
//                .addPage(new HeadlineTextPage("etheric_nitrate.vivid_nitrate", "etheric_nitrate.vivid_nitrate.1"))
//                .addPage(SpiritInfusionPage.fromOutput(VIVID_NITRATE.get()))
//        );

        ENTRIES.add(new BookEntry(
                "corrupted_resonance", CORRUPTED_RESONANCE.get(), 0, 15).setSoulwood()
                .addPage(new HeadlineTextPage("corrupted_resonance", "corrupted_resonance.1"))
                .addPage(SpiritInfusionPage.fromOutput(CORRUPTED_RESONANCE.get()))
        );

        ENTRIES.add(new BookEntry(
                "magebane_belt", BELT_OF_THE_MAGEBANE.get(), -1, 16).setSoulwood()
                .addPage(new HeadlineTextPage("magebane_belt", "magebane_belt.1"))
                .addPage(new TextPage("magebane_belt.2"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_MAGEBANE.get()))
        );

        ENTRIES.add(new BookEntry(
                "necklace_of_the_hidden_blade", NECKLACE_OF_THE_HIDDEN_BLADE.get(), 1, 16).setSoulwood()
                .addPage(new HeadlineTextPage("necklace_of_the_hidden_blade", "necklace_of_the_hidden_blade.1"))
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
}
