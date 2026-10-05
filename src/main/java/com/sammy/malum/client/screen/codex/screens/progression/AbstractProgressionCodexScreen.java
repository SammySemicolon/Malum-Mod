package com.sammy.malum.client.screen.codex.screens.progression;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.chapters.BookChapter;
import com.sammy.malum.client.screen.codex.handlers.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.client.MalumShaders;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.common.*;
import org.jetbrains.annotations.*;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;

import java.util.List;

import static com.sammy.malum.MalumMod.*;

public abstract class AbstractProgressionCodexScreen extends AbstractMalumCodexScreen {

    public static final ResourceLocation FRAME_TEXTURE = malumPath("textures/gui/book/progression_frame.png");
    public static final ResourceLocation FRAME_CUTOUT_TEXTURE = malumPath("textures/gui/book/progression_cutout.png");

    public static final ResourceLocation FRAME_FADE_TEXTURE = malumPath("textures/gui/book/frame_fade.png");

    public static int BOOK_WIDTH = 400;
    public static int BOOK_HEIGHT = 320;

    public RenderTarget target;

    protected float oldBackgroundXOffset;
    protected float oldBackgroundYOffset;
    protected float backgroundXOffset;
    protected float backgroundYOffset;

    protected float oldObjectXOffset;
    protected float oldObjectYOffset;
    protected float objectXOffset;
    protected float objectYOffset;

    protected float xOffset;
    protected float yOffset;
    protected float cachedXOffset;
    protected float cachedYOffset;

    protected boolean ignoreNextMouseInput;

    protected int voidFadeoutTimer;
    protected int voidFadeoutCounter;

    public final ProgressionObjectHandler progressionObjects = new ProgressionObjectHandler();
    public final List<BookChapter> chapters;

    protected final int backgroundImageWidth;
    protected final int backgroundImageHeight;

    // Minecraft instance, non nullable
    protected final Minecraft minecraft = Minecraft.getInstance();

    protected AbstractProgressionCodexScreen(Holder<SoundEvent> sweetenerSound, int backgroundImageWidth, int backgroundImageHeight) {
        super(Component.empty(), sweetenerSound);
        this.backgroundImageWidth = backgroundImageWidth;
        this.backgroundImageHeight = backgroundImageHeight;
        chapters = getChapters();

        NeoForge.EVENT_BUS.post(new SetupMalumCodexEntriesEvent(this));
        setupObjects();
        faceOrigin();
        target = new TextureTarget(BOOK_WIDTH, BOOK_HEIGHT, true, Minecraft.ON_OSX);
    }

    public abstract void renderBackground(PoseStack poseStack);

    public abstract List<BookChapter> getChapters();

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        renderBookInnards(guiGraphics, mouseX, mouseY, partialTicks);
        renderFrameCutout(guiGraphics);
//        if (voidFadeoutTimer > 0) {
//            CodexRenderHelper.renderTransitionFade(this, poseStack);
//        }
//        renderFade(poseStack);

        renderObjectsLate(guiGraphics, mouseX, mouseY, partialTicks);
        doLateRendering(guiGraphics, mouseX, mouseY);
    }

    public void renderFrameCutout(GuiGraphics guiGraphics) {
        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();

        RenderSystem.setShaderTexture(0, FRAME_TEXTURE);
        RenderSystem.setShaderTexture(1, FRAME_CUTOUT_TEXTURE);
        RenderSystem.setShaderTexture(2, target.getColorTextureId());
        RenderSystem.setShader(MalumShaders.PROGRESSION_SCREEN::getShaderInstance);
        var builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        var last = guiGraphics.pose().last();

        var size = new Vector2i(BOOK_WIDTH, BOOK_HEIGHT);
        var offset = new Vector2i(0, 0);

        int x0 = guiLeft + offset.x();
        int y0 = guiTop + offset.y();
        int x1 = x0 + size.x();
        int y1 = y0 + size.y();
        builder.addVertex(last, x0, y0, 0).setUv(0,1);
        builder.addVertex(last, x0, y1, 0).setUv(0,0);
        builder.addVertex(last, x1, y1, 0).setUv(1,0);
        builder.addVertex(last, x1, y0, 0).setUv(1,1);

        BufferUploader.drawWithShader(builder.buildOrThrow());

    }

    public void renderBookInnards(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        var poseStack = guiGraphics.pose();

        Matrix4f oldProjMat = RenderSystem.getProjectionMatrix();
        Matrix4f newProjMat = getProjectionMatrix();

        Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
        matrix4fstack.pushMatrix();
        matrix4fstack.set(getModelViewMatrix());
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setProjectionMatrix(newProjMat, VertexSorting.ORTHOGRAPHIC_Z);
        target.setClearColor(0, 0, 1, 1);
        target.clear(Minecraft.ON_OSX);
        target.bindWrite(true);

        renderBackground(poseStack);
        renderObjects(guiGraphics, mouseX, mouseY, partialTicks);

        target.unbindWrite();
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

        matrix4fstack.popMatrix();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setProjectionMatrix(oldProjMat, VertexSorting.ORTHOGRAPHIC_Z);
    }

    public void renderObjects(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        float delta = minecraft.getTimer().getGameTimeDeltaPartialTick(true);
        float x = Mth.lerp(delta, oldObjectXOffset, objectXOffset) - 16;
        float y = Mth.lerp(delta, oldObjectYOffset, objectYOffset) - 16;

        mouseX -= getGuiLeft();
        mouseY -= getGuiTop();

        progressionObjects.renderObjects(this, guiGraphics, BOOK_WIDTH / 2f + x, BOOK_HEIGHT / 2f + y, mouseX, mouseY, partialTicks);
    }

    public void renderObjectsLate(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        /*
        Normally we'd have to always adjust objects like this, however
        Since movable objects are now being rendered onto another render target, their root relative to the screen is simply 0, 0 as the offset is effectively applied later in renderFrameCutout
        Thus, we manually reintroduce the offset for late object rendering which exists outside our render target.
        */
        progressionObjects.offsetObjects(this, getGuiLeft(), getGuiTop());

        progressionObjects.renderObjectsLate(this, guiGraphics, mouseX, mouseY, partialTicks);
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().ortho(
                0, BOOK_WIDTH,
                BOOK_HEIGHT, 0,
                0.05f, 2000.0f
        );
    }

    public Matrix4f getModelViewMatrix() {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.identity();
        matrix4f.translate(0, 0, -2000);
        return matrix4f;
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
        progressionObjects.click(this, mouseX, mouseY);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        xOffset += (float) dragX;
        yOffset += (float) dragY;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        cachedXOffset = xOffset;
        cachedYOffset = yOffset;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        super.onClose();
        playSweetenedSound(MalumSoundEvents.ARCANA_CODEX_CLOSE, 0.75f);
    }

    @Override
    public void tick() {
        progressionObjects.tick(this);
        if (voidFadeoutTimer > 0) {
            voidFadeoutTimer--;
        }
        oldBackgroundXOffset = backgroundXOffset;
        oldBackgroundYOffset = backgroundYOffset;
        backgroundXOffset += (xOffset - backgroundXOffset) * 0.6f;
        backgroundYOffset += (yOffset - backgroundYOffset) * 0.6f;

        oldObjectXOffset = objectXOffset;
        oldObjectYOffset = objectYOffset;
        objectXOffset += (xOffset - objectXOffset) * 0.75f;
        objectYOffset += (yOffset - objectYOffset) * 0.75f;
        super.tick();
    }

    @Override
    public boolean isHovering(double mouseX, double mouseY, float posX, float posY, int width, int height) {
        return mouseX >= 0
                && mouseY >= 0
                && mouseX <= BOOK_WIDTH
                && mouseY <= BOOK_HEIGHT
                && super.isHovering(mouseX, mouseY, posX, posY, width, height);
    }

    public void correctOOBB() {
        if (progressionObjects.hasVisibleObject(this)) {
            return;
        }
        var offsets = clampOffsets(xOffset, yOffset, 1f, 0.1f, 0.8f);
        if (offsets.x != xOffset || offsets.y != yOffset) {
            faceOrigin();
        }
    }

    public void setupObjects() {
        var window = minecraft.getWindow();
        this.width = window.getGuiScaledWidth();
        this.height = window.getGuiScaledHeight();
        for (BookChapter chapter : chapters) {
            chapter.place(this, progressionObjects);
        }
    }

    public void faceOrigin() {
        var first = progressionObjects.getFirst();
        faceObject(first);
    }

    public void faceObject(BookObject<?> object) {
        var window = minecraft.getWindow();
        this.width = window.getGuiScaledWidth();
        this.height = window.getGuiScaledHeight();
        xOffset = -object.x;
        yOffset = -object.y;
        backgroundXOffset = xOffset;
        backgroundYOffset = yOffset;
    }

    public void renderBackground(PoseStack poseStack, ResourceLocation texture, float xModifier, float yModifier) {
        float delta = minecraft.getTimer().getGameTimeDeltaPartialTick(true);
        var x = Mth.lerp(delta, oldBackgroundXOffset, backgroundXOffset);
        var y = Mth.lerp(delta, oldBackgroundYOffset, backgroundYOffset);
        var offsets = clampOffsets(x, y, 0.8f, 0f, 1f);
        float xOffset = offsets.x;
        float yOffset = offsets.y;
        float uOffset = (backgroundImageWidth / 12f) - xOffset * xModifier;
        float vOffset = (backgroundImageHeight - BOOK_HEIGHT) - yOffset * yModifier;

        VFXBuilders.createScreen().setTexture(texture)
                .setShader(GameRenderer::getPositionTexColorShader)
                .setPositionWithWidth(0, 0, BOOK_WIDTH, BOOK_HEIGHT)
                .setUVWithWidth(uOffset, vOffset, BOOK_WIDTH, BOOK_HEIGHT, backgroundImageWidth / 2f, backgroundImageHeight / 2f)
                .multiplyColor(0.75f)
                .blit(poseStack);
    }

//    public void renderFade(PoseStack poseStack) {
//        ExtendedShaderInstance shaderInstance = LodestoneShaders.SCREEN_DISTORTED_TEXTURE.getShaderInstance();
//        shaderInstance.safeGetUniform("YFrequency").set(32f);
//        shaderInstance.safeGetUniform("XFrequency").set(16f);
//        shaderInstance.safeGetUniform("Speed").set(1000f);
//        shaderInstance.safeGetUniform("Intensity").set(120f);
//        int insideLeft = getInsideLeft();
//        int insideTop = getInsideTop();
//        RenderSystem.depthMask(true);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        VFXBuilders.createScreen().setTexture(FRAME_FADE_TEXTURE)
//                .setShader(shaderInstance)
//                .setPositionWithWidth(insideLeft, insideTop, BOOK_INSIDE_WIDTH, BOOK_INSIDE_HEIGHT)
//                .setZLevel(400)
//                .blit(poseStack);
//        shaderInstance.applyUniformDefaults();
//        RenderSystem.disableBlend();
//    }


    public Vec2 clampOffsets(float x, float y, float horizontalClamp, float bottomClamp, float topClamp) {
        float xOffset = x;
        float xMin = -backgroundImageWidth * horizontalClamp;
        float xMax = backgroundImageWidth * horizontalClamp;
        if (xOffset < xMin || xOffset > xMax) {
            xOffset = Mth.clamp(xOffset, xMin, xMax);
        }
        float yOffset = y;
        float yMin = -backgroundImageHeight * bottomClamp;
        float yMax = backgroundImageHeight * topClamp;
        if (yOffset < yMin || yOffset > yMax) {
            yOffset = Mth.clamp(yOffset, yMin, yMax);
        }
        return new Vec2(xOffset, yOffset);
    }

    public boolean isInView(double x, double y) {
        int top = getGuiTop();
        int left = getGuiLeft();
        return x >= left
                && y >= top
                && x <= (left + BOOK_WIDTH)
                && y <= (top + BOOK_HEIGHT);
    }

//    public int getInsideLeft() {
//        return getGuiLeft() + 17;
//    }

//    public int getInsideTop() {
//        return getGuiTop() + 14;
//    }

    public int getGuiLeft() {
        return width / 2 - BOOK_WIDTH / 2;
    }

    public int getGuiTop() {
        return height / 2 - BOOK_HEIGHT / 2;
    }

    public float getVoidFadeoutDelta() {
        return (float) voidFadeoutTimer / getVoidFadeoutDuration();
    }

    public int getVoidFadeoutDuration() {
        return 80 - Mth.clamp(voidFadeoutCounter - 2, 0, 4) * 10;
    }
}