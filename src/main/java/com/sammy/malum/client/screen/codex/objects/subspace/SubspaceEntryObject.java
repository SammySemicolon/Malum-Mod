package com.sammy.malum.client.screen.codex.objects.subspace;

import com.mojang.blaze3d.systems.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.handlers.*;
import com.sammy.malum.client.screen.codex.objects.BookObject;
import com.sammy.malum.client.screen.codex.objects.ProgressionEntryObject;
import com.sammy.malum.client.screen.codex.screens.progression.*;
import com.sammy.malum.core.systems.spirit.SpiritLike;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import org.joml.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.builder.ScreenVFXBuilder;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.*;

import javax.annotation.*;
import java.lang.Math;
import java.util.function.Consumer;

import static com.sammy.malum.MalumMod.malumPath;
import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class SubspaceEntryObject extends ProgressionEntryObject {

    private static final ResourceLocation SUBSPACE_TEXTURE = malumPath("textures/gui/book/subspace_container.png");
    private static final ResourceLocation GLOW_TEXTURE = malumPath("textures/gui/book/subentry_glow.png");
//    private static final ResourceLocation ICON_LEFT_TEXTURE = malumPath("textures/gui/book/subentry_icon_left.png");
//    private static final ResourceLocation ICON_RIGHT_TEXTURE = malumPath("textures/gui/book/subentry_icon_right.png");

    private static final int WARMUP_TIME = 16;

    protected final SubspaceProgressionObjectHandler storedObjects = new SubspaceProgressionObjectHandler();
    protected final EntryStorage entryStorage;
    protected final int subspaceSize;

    protected boolean isOpen = false;
    protected int openDuration;

    public SubspaceEntryObject(PlacedBookEntry entry, EntryStorage entryStorage, int subspaceSize) {
        super(entry);
        this.entryStorage = entryStorage;
        this.subspaceSize = subspaceSize;
    }

//    @Override
//    public boolean shouldGizmoBeConsideredHoveredOver() {
//        if (isOpen) {
//            return false;
//        }
//        return super.shouldGizmoBeConsideredHoveredOver();
//    }

    @Override
    public void additionalSetup(AbstractProgressionCodexScreen screen) {
        storedObjects.setupEntryObjects(screen, entryStorage);
    }

    @Override
    public boolean hasPriority(AbstractProgressionCodexScreen screen) {
        return isOpen;
    }

    @Override
    public void tick(AbstractProgressionCodexScreen screen, double mouseX, double mouseY) {
        int centerX = getCenterX();
        int centerY = getCenterY();
        if (isOpen) {
            int xDiff = centerX - (int) mouseX;
            int yDiff = centerY - (int) mouseY;
            double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
            if (distance > (double) subspaceSize / 2 + 16) {
                screen.playSweetenedSound(MalumSoundEvents.ARCANA_SUBENTRY_CLOSE, 0.75f);
                isOpen = false;
            }

            if (openDuration < WARMUP_TIME) {
                openDuration++;
            }
            storedObjects.tick(screen, mouseX, mouseY);
        } else {
            if (openDuration > 0) {
                openDuration--;
            }
            super.tick(screen, mouseX, mouseY);
        }
    }

    //☺
    @Override
    public void renderLate(AbstractProgressionCodexScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (isOpen || openDuration > 0) {
            var pose = guiGraphics.pose();
            float duration = openDuration;
            if (isOpen) {
                if (duration < WARMUP_TIME) {
                    duration += partialTicks;
                }
            } else {
                duration -= partialTicks;
            }
            float delta = Easing.SINE_IN_OUT.ease(Math.min(0.1f + duration / (float) WARMUP_TIME, 1));
            var minecraft = Minecraft.getInstance();
            int scale = (int) minecraft.getWindow().getGuiScale();
            int size = (int) (subspaceSize * delta);
            int offset = size / 2;
            int x = getOffsetX() + width / 2 - offset;
            int y = getOffsetY() + height / 2 - offset;
            int margin = 6;
            pose.pushPose();
            pose.translate(0, 0, 500);
            renderSubspace(guiGraphics, x - margin, y - margin, size + margin * 2, delta);

            GL11.glEnable(GL_SCISSOR_TEST);
            GL11.glScissor(
                    x * scale,
                    minecraft.getWindow().getHeight() - (y + size) * scale,
                    size * scale,
                    size * scale);

            storedObjects.renderObjects(screen, guiGraphics, xOffset, yOffset, mouseX, mouseY, partialTicks);
            GL11.glDisable(GL_SCISSOR_TEST);
            if (isOpen) {
                storedObjects.renderObjectsLate(screen, guiGraphics, mouseX, mouseY, partialTicks);
            }
            screen.doLateRendering(guiGraphics, mouseX, mouseY);
            pose.popPose();
        }
    }

    @Override
    public boolean tryClick(AbstractProgressionCodexScreen screen, double mouseX, double mouseY) {
        if (isOpen) {
            return storedObjects.click(screen, mouseX, mouseY);
        }
        return super.tryClick(screen, mouseX, mouseY);
    }

    @Override
    public boolean tryRelease(AbstractProgressionCodexScreen screen, double mouseX, double mouseY) {
        if (isOpen) {
            return storedObjects.release(screen, mouseX, mouseY);
        }
        return super.tryRelease(screen, mouseX, mouseY);
    }

    @Override
    public boolean click(AbstractProgressionCodexScreen screen, double mouseX, double mouseY) {
        for (BookObject<AbstractProgressionCodexScreen> object : screen.progressionObjects.getObjects()) {
            if (object instanceof SubspaceEntryObject otherSubspace && otherSubspace != this && otherSubspace.isOpen) {
                return false;
            }
        }
        if (!isOpen) {
            screen.playSweetenedSound(MalumSoundEvents.ARCANA_SUBENTRY_OPEN, 1.25f);
            isOpen = true;
            return true;
        }
        return super.click(screen, mouseX, mouseY);
    }

    public static void renderSubspace(GuiGraphics graphics, int x, int y, int size, float delta) {
        ExtendedShaderInstance shaderInstance = LodestoneShaders.MANUAL_NINE_SLICE.getShaderInstance();
        shaderInstance.safeGetUniform("YFrequency").set(10f);
        shaderInstance.safeGetUniform("XFrequency").set(10f);
        shaderInstance.safeGetUniform("Speed").set(400f);
        shaderInstance.safeGetUniform("Intensity").set(100f);
        shaderInstance.safeGetUniform("UVCoordinates").set(new Vector4f(-1f, 2f, -1f, 2f));
        shaderInstance.safeGetUniform("Size").set(3f,3f);

        ScreenVFXBuilder builder = VFXBuilders.createScreen()
                .setShader(shaderInstance)
                .setAlpha(delta);

        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        renderSubspaceTexture(graphics, builder, x, y, size);
        builder.setAlpha(0.2f * delta);
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        shaderInstance.safeGetUniform("Speed").set(800f);
        shaderInstance.applyUniformDefaults();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void renderSubspaceTexture(GuiGraphics graphics, ScreenVFXBuilder builder, int x, int y, int size) {
        builder.setTexture(SUBSPACE_TEXTURE).setPositionWithWidth(x, y, size, size).blit(graphics.pose());
    }

    public static void renderGlowTexture(GuiGraphics graphics, ScreenVFXBuilder builder, int x, int y) {
        builder.setTexture(GLOW_TEXTURE).setPositionWithWidth(x, y, 32, 32).blit(graphics.pose());
    }

}