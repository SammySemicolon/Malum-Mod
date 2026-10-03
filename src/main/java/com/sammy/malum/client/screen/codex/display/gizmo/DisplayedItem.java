package com.sammy.malum.client.screen.codex.display.gizmo;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.display.IGizmoHolder;
import com.sammy.malum.client.screen.codex.display.texture.DynamicTextureBuilder;
import com.sammy.malum.client.screen.codex.display.texture.request.ItemTextureRequest;
import com.sammy.malum.client.screen.codex.screens.AbstractMalumCodexScreen;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;

public class DisplayedItem extends DisplayedGizmo {

    protected final ItemStack itemDisplay;

    public DisplayedItem(ItemStack itemDisplay) {
        this.itemDisplay = itemDisplay;
    }

    public static DisplayedItem item(ItemLike item) {
        return new DisplayedItem(item.asItem().getDefaultInstance());
    }

    public static DisplayedItem item(ItemLike item, int count) {
        return new DisplayedItem(new ItemStack(item, count));
    }

    public static DisplayedItem item(ItemStack stack) {
        return new DisplayedItem(stack);
    }

    @Override
    public void renderDecals(AbstractMalumCodexScreen screen, IGizmoHolder holder, GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY) {
        var request = ItemTextureRequest.create(itemDisplay, r -> r.withSuffix("_gizmo"));
        var dynamicTexture = DynamicTextureBuilder.create(request).setTextureSize(16, 16).bakeTexture();
        if (dynamicTexture == null) {
            return;
        }
        dynamicTexture.bind(0);
        PoseStack stack = guiGraphics.pose();
        stack.pushPose();
        var builder = VFXBuilders.createScreen()
                .setShader(GameRenderer::getPositionTexColorShader)
                .setUV(0, 1, 1, 0)
                .setPositionWithWidth(x, y, 16, 16)
                .setZLevel(200)
                .setColor(color)
                .blit(stack);
        if (isHoveredOver) {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            float alphaScale = color.getRed() / 255f;
            builder.setAlpha(0.5f * alphaScale).blit(stack);
            RenderSystem.defaultBlendFunc();
        }

        guiGraphics.renderItemDecorations(Minecraft.getInstance().font, itemDisplay, x, y, null);
        stack.popPose();
    }

    @Override
    public void gatherTooltip(IGizmoHolder holder, GizmoTooltipBuilder tooltip) {
        tooltip.addAll(Screen.getTooltipFromItem(Minecraft.getInstance(), itemDisplay));
    }
}