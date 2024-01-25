package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.common.events.SetupMalumCodexEntriesEvent;
import com.sammy.malum.common.item.*;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderTexture;
import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderTransparentTexture;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static net.minecraft.world.item.Items.BARRIER;
import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class VoidProgressionScreen extends AbstractProgressionCodexScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = malumPath("textures/gui/book/void_background.png");

    public static VoidProgressionScreen screen;

    public static final List<BookEntry> VOID_ENTRIES = new ArrayList<>();

    protected VoidProgressionScreen() {
        super(1024, 768);
        minecraft = Minecraft.getInstance();
        setupEntries();
        MinecraftForge.EVENT_BUS.post(new SetupMalumCodexEntriesEvent());
        setupObjects();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        PoseStack poseStack = guiGraphics.pose();

        renderBackground(poseStack, 0.2f, 0.2f);
        GL11.glEnable(GL_SCISSOR_TEST);
        cut();

        renderEntries(guiGraphics, mouseX, mouseY, partialTicks);
        GL11.glDisable(GL_SCISSOR_TEST);

        renderTransparentTexture(FRAME_FADE_TEXTURE, poseStack, guiLeft, guiTop, 0, 0, bookWidth, bookHeight);
        renderTexture(FRAME_TEXTURE, poseStack, guiLeft, guiTop, 0, 0, bookWidth, bookHeight);
        lateEntryRender(guiGraphics, mouseX, mouseY, partialTicks);
    }

    public void renderBackground(PoseStack poseStack, float xModifier, float yModifier) {
        int insideLeft = getInsideLeft();
        int insideTop = getInsideTop();
        float uOffset = (bookInsideWidth / 4f - xOffset * xModifier);
        float vOffset = (backgroundImageHeight - bookInsideHeight - yOffset * yModifier);
        if (uOffset <= 0) {
            uOffset = 0;
        }
        if (uOffset > bookInsideWidth / 2f) {
            uOffset = bookInsideWidth / 2f;
        }
        if (vOffset <= backgroundImageHeight / 2f) {
            vOffset = backgroundImageHeight / 2f;
        }
        if (vOffset > backgroundImageHeight - bookInsideHeight) {
            vOffset = backgroundImageHeight - bookInsideHeight;
        }
        renderTexture(BACKGROUND_TEXTURE, poseStack, insideLeft, insideTop, uOffset, vOffset, bookInsideWidth, bookInsideHeight, backgroundImageWidth / 2, backgroundImageHeight / 2);
    }

    @Override
    public Collection<BookEntry> getEntries() {
        return VOID_ENTRIES;
    }

    @Override
    public Supplier<SoundEvent> getSweetenerSound() {
        return SoundRegistry.ARCANA_SWEETENER_EVIL;
    }

    @Override
    public void onClose() {
        super.onClose();
        playSweetenedSound(SoundRegistry.ARCANA_CODEX_CLOSE, 0.75f);
    }

    @Override
    public void openScreen(boolean ignoreNextMouseClick) {
        Minecraft.getInstance().setScreen(this);
        ScreenParticleHandler.clearParticles();
        this.ignoreNextMouseInput = ignoreNextMouseClick;
    }

    public static VoidProgressionScreen getScreenInstance() {
        if (screen == null) {
            screen = new VoidProgressionScreen();
        }
        return screen;
    }

    public static void openCodexViaItem() {
        getScreenInstance().openScreen(true);
        screen.playSweetenedSound(SoundRegistry.ARCANA_CODEX_OPEN, 1.25f);
    }

    public static void openCodexViaTransition() {
        getScreenInstance().openScreen(false);
        screen.faceObject(screen.bookObjects.get(0));
        screen.playSound(SoundRegistry.ARCANA_TRANSITION_EVIL, 1.25f, 1f);
        EncyclopediaArcanaItem.shouldOpenVoidCodex = true;
    }

    public static void setupEntries() {
        VOID_ENTRIES.clear();
        VOID_ENTRIES.add(new BookEntry(
                "research_of_the_rejected", 0, 0)
                .setWidgetSupplier((s, e, x, y) -> new ScreenOpenerObject(s, e, x, y, ArcanaProgressionScreen::openCodexViaTransition, malumPath("textures/gui/book/icons/arcana_button.png"), 20, 20))
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_GRAND_SOULWOOD))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.introduction", 0, 1)
                .setWidgetConfig(w -> w.setIcon(ENCYCLOPEDIA_ARCANA).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
        );

        VOID_ENTRIES.add(new BookEntry(
                "void.weeping_well", 0, 2)
                .setWidgetConfig(w -> w.setIcon(BLIGHTED_EARTH).setStyle(BookWidgetStyle.SOULWOOD))
        );

        VOID_ENTRIES.add(new BookEntry(
                "void.null_slate", -2, 3)
                .setWidgetConfig(w -> w.setIcon(NULL_SLATE).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextItemPage("void.null_slate", "void.null_slate.1", NULL_SLATE.get()))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.void_salts", -1, 4)
                .setWidgetConfig(w -> w.setIcon(VOID_SALTS).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextItemPage("void.void_salts", "void.void_salts.1", VOID_SALTS.get()))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.auric_embers", 1, 4)
                .setWidgetConfig(w -> w.setIcon(AURIC_EMBERS).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextItemPage("void.auric_embers", "void.auric_embers.1", AURIC_EMBERS.get()))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.mnemotic_fragment", 2, 3)
                .setWidgetConfig(w -> w.setIcon(MNEMONIC_FRAGMENT).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextItemPage("void.mnemotic_fragment", "void.mnemotic_fragment.1", MNEMONIC_FRAGMENT.get()))
        );

        VOID_ENTRIES.add(new BookEntry(
                "void.ring_of_growing_flesh", -3, 5)
                .setWidgetConfig(w -> w.setIcon(RING_OF_GROWING_FLESH).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.ring_of_growing_flesh", "void.ring_of_growing_flesh.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_GROWING_FLESH.get()))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.ring_of_gruesome_satiation", -4, 6)
                .setWidgetConfig(w -> w.setIcon(RING_OF_GRUESOME_SATIATION).setStyle(BookWidgetStyle.SOULWOOD))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.necklace_of_the_watcher", -2, 6)
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_THE_WATCHER).setStyle(BookWidgetStyle.SOULWOOD))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.necklace_of_the_hidden_blade", -3, 7)
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_THE_HIDDEN_BLADE).setStyle(BookWidgetStyle.SOULWOOD))
        );

        VOID_ENTRIES.add(new BookEntry(
                "void.soul_stained_steel_staff", 3, 5)
                .setWidgetConfig(w -> w.setIcon(SOUL_STAINED_STEEL_STAFF).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.soul_stained_steel_staff", "void.soul_stained_steel_staff.1"))
                .addPage(new TextPage("void.soul_stained_steel_staff.2"))
                .addPage(new TextPage("void.soul_stained_steel_staff.3"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_STAFF.get()))
                .addPage(new HeadlineTextPage("void.ring_of_the_plentiful", "void.ring_of_the_plentiful.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_THE_PLENTIFUL.get()))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.something2", 4, 6)
                .setWidgetConfig(w -> w.setIcon(BARRIER).setStyle(BookWidgetStyle.SOULWOOD))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.something3", 2, 6)
                .setWidgetConfig(w -> w.setIcon(BARRIER).setStyle(BookWidgetStyle.SOULWOOD))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.something4", 3, 7)
                .setWidgetConfig(w -> w.setIcon(BARRIER).setStyle(BookWidgetStyle.SOULWOOD))
        );

        VOID_ENTRIES.add(new BookEntry(
                "void.anomalous_design", 0, 7)
                .setWidgetConfig(w -> w.setIcon(ANOMALOUS_DESIGN).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextItemPage("void.anomalous_design", "void.anomalous_design.1", ANOMALOUS_DESIGN.get()))
                .addPage(SpiritInfusionPage.fromOutput(COMPLETE_DESIGN.get()))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.fused_consciousness", 0, 8)
                .setWidgetConfig(w -> w.setIcon(FUSED_CONSCIOUSNESS).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
                .addPage(new HeadlineTextItemPage("void.fused_consciousness", "void.fused_consciousness.1", FUSED_CONSCIOUSNESS.get()))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.belt_of_the_limitless", -2, 9)
                .setWidgetConfig(w -> w.setIcon(BELT_OF_THE_LIMITLESS).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.belt_of_the_limitless", "void.belt_of_the_limitless.1"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_LIMITLESS.get()))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.stellar_mechanism", 2, 9)
                .setWidgetConfig(w -> w.setIcon(STELLAR_MECHANISM).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.stellar_mechanism", "void.stellar_mechanism.1"))
                .addPage(SpiritInfusionPage.fromOutput(STELLAR_MECHANISM.get()))
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.staff_of_the_auric_flame", 0, 10)
                .setWidgetConfig(w -> w.setIcon(STAFF_OF_THE_AURIC_FLAME).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.staff_of_the_auric_flame", "void.staff_of_the_auric_flame.1"))
                .addPage(SpiritInfusionPage.fromOutput(STAFF_OF_THE_AURIC_FLAME.get()))
        );
    }
}