package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.common.events.SetupMalumCodexEntriesEvent;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
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

    public static VoidProgressionScreen screen;

    public static final List<BookEntry> VOID_ENTRIES = new ArrayList<>();

    public static final ResourceLocation FRAME_TEXTURE = malumPath("textures/gui/book/frame.png");
    public static final ResourceLocation FADE_TEXTURE = malumPath("textures/gui/book/fade.png");
    public static final ResourceLocation BACKGROUND_TEXTURE = malumPath("textures/gui/book/void_background.png");

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

        renderTransparentTexture(FADE_TEXTURE, poseStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        renderTexture(FRAME_TEXTURE, poseStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        lateEntryRender(guiGraphics, mouseX, mouseY, partialTicks);
    }

    public void renderBackground(PoseStack poseStack, float xModifier, float yModifier) {
        int insideLeft = getInsideLeft();
        int insideTop = getInsideTop();
        float uOffset = (bookInsideWidth / 4f - xOffset * xModifier);
        float vOffset = (backgroundImageHeight / 10.75f - yOffset * yModifier);
        if (uOffset <= 0) {
            uOffset = 0;
        }
        if (uOffset > bookInsideWidth / 2f) {
            uOffset = bookInsideWidth / 2f;
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
        setupEntries();
        screen.playSweetenedSound(SoundRegistry.ARCANA_CODEX_OPEN, 1.25f);
        screen.setupObjects();
    }

    public static void setupEntries() {
        VOID_ENTRIES.clear();
        VOID_ENTRIES.add(new BookEntry(
                "void.introduction", ENCYCLOPEDIA_ARCANA.get(), 0, -1)
        );

        VOID_ENTRIES.add(new BookEntry(
                "void.weeping_well", BLIGHTED_EARTH.get(), 0, 1)
        );

        VOID_ENTRIES.add(new BookEntry(
                "void.void_salts", VOID_SALTS.get(), 2, 2)
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.strange_nucleus", STRANGE_NUCLEUS.get(), 1, 3)
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.crystallized_nihility", CRYSTALLIZED_NIHILITY.get(), -1, 3)
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.null_slate", NULL_SLATE.get(), -2, 2)
        );

        VOID_ENTRIES.add(new BookEntry(
                "void.ring_of_growing_flesh", RING_OF_GROWING_FLESH.get(), -3, 4)
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.ring_of_gruesome_satiation", RING_OF_GRUESOME_SATIATION.get(), -4, 5)
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.necklace_of_the_watcher", NECKLACE_OF_THE_WATCHER.get(), -2, 5)
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.necklace_of_the_hidden_blade", NECKLACE_OF_THE_HIDDEN_BLADE.get(), -3, 6)
        );

        VOID_ENTRIES.add(new BookEntry(
                "void.something1", BARRIER, 3, 4)
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.something2", BARRIER, 4, 5)
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.something3", BARRIER, 2, 5)
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.something4", BARRIER, 3, 6)
        );


        VOID_ENTRIES.add(new BookEntry(
                "void.anomalous_snare", ANOMALOUS_DESIGN.get(), 0, 6)
        );
        VOID_ENTRIES.add(new BookEntry(
                "void.fused_consciousness", FUSED_CONSCIOUSNESS.get(), 0, 7)
        );
    }
}
