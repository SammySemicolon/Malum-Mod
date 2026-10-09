package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.display.gizmo.GizmoTooltipBuilder;
import com.sammy.malum.client.screen.codex.screens.progression.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.*;
import team.lodestar.lodestone.modules.core.easing.Easing;

import static com.sammy.malum.client.screen.codex.helper.CodexRenderHelper.renderTexture;

public class ProgressionEntryObject extends SelectableEntryObject<AbstractProgressionCodexScreen> {

    public static final int OBJECT_SPACING = 32;

    public EntryWidgetDesign design = EntryWidgetDesign.MEDIUM;

    protected int oldOutlineVisibility;
    protected int outlineVisibility;

    public ProgressionEntryObject(PlacedBookEntry entry) {
        super(entry, 32, 32);
    }

    @Override
    public boolean isInView(AbstractProgressionCodexScreen screen) {
        int posX = getOffsetX() - 16;
        int posY = getOffsetY() - 16;
        return posX + 32 >= 0
                && posY + 32 >= 0
                && posX <= AbstractProgressionCodexScreen.BOOK_WIDTH
                && posY <= AbstractProgressionCodexScreen.BOOK_HEIGHT;
    }

    @Override
    public void tick(AbstractProgressionCodexScreen screen, double mouseX, double mouseY) {
        oldOutlineVisibility = outlineVisibility;
        if (isHoveredOver) {
            if (outlineVisibility == 6) {
                screen.playSound(MalumSoundEvents.ARCANA_ENTRY_HOVER, 0.2f, 1f);
            }
            if (outlineVisibility < 20) {
                outlineVisibility = Math.min(outlineVisibility + 2, 20);
            }
            return;
        }
        if (outlineVisibility == 15) {
            screen.playSound(MalumSoundEvents.ARCANA_ENTRY_UNHOVER, 0.1f, 0.75f);
        }
        if (outlineVisibility > 0) {
            outlineVisibility--;
        }
    }

    @Override
    public void applyTransforms(AbstractProgressionCodexScreen screen, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        float effectStrength = Mth.lerp(partialTicks, oldOutlineVisibility, outlineVisibility) / 20f;
        if (effectStrength > 0) {
            float offset = Easing.CIRC_OUT.ease(effectStrength) * 2;
            poseStack.translate(0, -offset, 0);
        }
    }

    @Override
    public void render(AbstractProgressionCodexScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        var poseStack = guiGraphics.pose();
        int left = getOffsetX();
        int top = getOffsetY();
        int centerX = getCenterX();
        int centerY = getCenterY();
        renderTexture(WIDGET_FADE_TEXTURE, poseStack, centerX - 29, centerY - 29, 0, 0, 58, 58);
        if (design != null) {
            float effectStrength = Mth.lerp(partialTicks, oldOutlineVisibility, outlineVisibility) / 20f;
            design.render(poseStack, left, top, effectStrength);
        }
        icon.render(screen, this, guiGraphics, centerX - 8, centerY - 8, mouseX, mouseY, partialTicks);
    }

    @Override
    public void addGizmoTooltip(GizmoTooltipBuilder builder) {
        super.addGizmoTooltip(builder);
        var bookmarks = entry.rightBookmarks;
        for (int i = bookmarks.size()-1; i >=0; i--) {
            EntryBookmark bookmark = bookmarks.get(i);
            if (bookmark.entry.shouldShow()) {
                var slash = Component.literal("┇ ");
                var text = Component.translatable(bookmark.entry.translationKey());
                var component = slash.append(text).withStyle(ChatFormatting.DARK_GRAY);
                builder.add(component);
            }
        }
    }

    @Override
    public int getOffsetX() {
        return (int) (x * OBJECT_SPACING + xOffset);
    }

    @Override
    public int getOffsetY() {
        return (int) (y * OBJECT_SPACING + yOffset);
    }

    public int getCenterX() {
        return getOffsetX() + width / 2;
    }

    public int getCenterY() {
        return getOffsetY() + height / 2;
    }
}
