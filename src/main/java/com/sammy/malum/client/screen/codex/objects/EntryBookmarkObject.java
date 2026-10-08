package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.EntryBookmark;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.registry.common.sound.MalumSoundEvents;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.*;
import net.minecraft.util.Mth;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;

import java.awt.*;

public class EntryBookmarkObject extends SelectableEntryObject<CodexEntryScreen> {

    public static final ResourceLocation LEFT = MalumMod.malumPath("textures/gui/book/buttons/bookmark_left.png");
    public static final ResourceLocation RIGHT = MalumMod.malumPath("textures/gui/book/buttons/bookmark_right.png");

    public final boolean flipped;

    protected float oldHeldDelta;
    protected float heldDelta;

    public EntryBookmarkObject(EntryBookmark entryBookmark, int posY, boolean flipped, int posX) {
        super(entryBookmark.entry, entryBookmark.icon, posX, posY, 26, 32);
        this.flipped = flipped;
    }

    @Override
    public void tick(CodexEntryScreen screen, double mouseX, double mouseY) {
        oldHeldDelta = heldDelta;
        heldDelta = DataHelper.approach(heldDelta, isHoveredOver ? 1 : 0, 0.75f);
        if (wasHoveredOver != isHoveredOver) {
            var sound = isHoveredOver ? MalumSoundEvents.ARCANA_BOOKMARK_HOVER : MalumSoundEvents.ARCANA_BOOKMARK_UNHOVER;
            screen.playSound(sound, 1, 1);
        }
    }

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int entryX = getOffsetX();
        int entryY = getOffsetY();
        var poseStack = guiGraphics.pose();
        float held = Mth.lerp(partialTicks, oldHeldDelta, heldDelta);
        int offset = (int) ((flipped ? 1 : -1) * held * 13);
        entryX += offset;
        int gizmoX = entryX + 26;
        if (flipped) {
            entryX -= 16;
            gizmoX = entryX + 12;
        }

        int dark = (int) Easing.SINE_IN_OUT.lerp(held, 160, 255);
        Color color = new Color(dark, dark, dark);
        VFXBuilders.createScreen()
                .setPositionWithWidth(entryX, entryY, 54, 28)
                .setShader(GameRenderer::getPositionTexColorShader)
                .setTexture(flipped ? RIGHT : LEFT)
                .setColor(color)
                .blit(poseStack);
        icon.setColor(color);
        icon.render(screen, this, guiGraphics, gizmoX, entryY+4, mouseX, mouseY, partialTicks);
    }
}