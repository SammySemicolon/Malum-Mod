package com.sammy.malum.client.screen.codex.objects.button;

import com.sammy.malum.client.screen.codex.display.*;
import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import com.sammy.malum.client.screen.codex.objects.BookObject;
import com.sammy.malum.client.screen.codex.screens.CodexEntryScreen;
import com.sammy.malum.registry.common.sound.MalumSoundEvents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.*;
import team.lodestar.lodestone.helpers.DataHelper;

import static com.sammy.malum.client.screen.codex.helper.CodexRenderHelper.renderTexture;

public abstract class AbstractButtonObject extends BookObject<CodexEntryScreen> implements IGizmoHolder {

    protected final ResourceLocation base;
    protected final ResourceLocation hover;
    protected final ResourceLocation pressed;
    protected final ResourceLocation active;

    protected final DisplayedGizmo icon;
    protected final int buttonIndex;

    protected float oldOutlineVisibility;
    protected float outlineVisibility;

    public AbstractButtonObject(DisplayedGizmo icon, ResourceLocation baseTexture, int buttonIndex, int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        this.base = baseTexture.withSuffix(".png");
        this.hover = baseTexture.withSuffix("_hover.png");
        this.pressed = baseTexture.withSuffix("_pressed.png");
        this.active = baseTexture.withSuffix("_active.png");

        this.icon = icon;
        this.buttonIndex = buttonIndex;
    }

    public abstract boolean isSelected();

    public abstract int getGizmoOffset();

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int x = getOffsetX();
        int y = getOffsetY();

        y += choose(0, -1, 1, -2);
        var texture = choose(base, hover, pressed, active);
        var poseStack = guiGraphics.pose();
        int xOffset = (64 - width)/2;
        int yOffset = (64 - height)/2;

        float effectStrength = Mth.lerp(partialTicks, oldOutlineVisibility, outlineVisibility);
        CodexOutlineRenderer.create(texture, x-xOffset, y-yOffset, width, height)
                .setEffectStrength(effectStrength)
                .setDistortion(50f)
                .setOffset(buttonIndex * 600)
                .setOutlineWidth(4)
                .setShadowWidth(5)
                .renderOutline(poseStack);
        renderTexture(texture, poseStack, x, y, 0, 0, width, height);
        if (icon != null) {
            int offset = getGizmoOffset();
            icon.render(screen, this, guiGraphics, x + offset, y + offset, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void tick(CodexEntryScreen screen, double mouseX, double mouseY) {
        oldOutlineVisibility = outlineVisibility;
        float target = choose(0f, 0.5f, 0.75f, 1.0f);
        outlineVisibility = DataHelper.approach(outlineVisibility, target, 0.125f);
        if (!isSelected()) {
            if (wasHoveredOver != isHoveredOver) {
                var sound = isHoveredOver ? MalumSoundEvents.ARCANA_BUTTON_HOVER : MalumSoundEvents.ARCANA_BUTTON_UNHOVER;
                screen.playSound(sound, 1, 1);
            }
        }
    }

    @Override
    public boolean release(CodexEntryScreen screen, double mouseX, double mouseY) {
        if (!isSelected()) {
            screen.playSound(MalumSoundEvents.ARCANA_BUTTON_CLICK, 0.5f, 1);
        }
        return super.release(screen, mouseX, mouseY);
    }

    @Override
    public boolean click(CodexEntryScreen screen, double mouseX, double mouseY) {
        if (!isSelected()) {
            screen.playSound(MalumSoundEvents.ARCANA_BUTTON_UNCLICK, 1, 1);
        }
        return super.click(screen, mouseX, mouseY);
    }

    public <T> T choose(T onDefault, T onHover, T onPressed, T onSelected) {
        if (isSelected()) {
            return onSelected;
        }
        if (isPressed) {
            return onPressed;
        }
        if (isHoveredOver) {
            return onHover;
        }
        return onDefault;
    }
}