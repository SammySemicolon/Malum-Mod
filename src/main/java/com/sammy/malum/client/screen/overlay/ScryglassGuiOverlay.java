package com.sammy.malum.client.screen.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.tools.VisionaryScryglassItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.client.LodestoneShaders;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;

public class ScryglassGuiOverlay implements LayeredDraw.Layer {

	private static final ResourceLocation VISIONARY_SCRYGLASS_SCOPE = MalumMod.malumPath("textures/misc/visionary_scryglass_scope.png");
	private static final ResourceLocation VISIONARY_SCRYGLASS_OVERLAY = MalumMod.malumPath("textures/misc/visionary_scryglass_overlay.png");
	private static final ResourceLocation VISIONARY_SCRYGLASS_GLASS = MalumMod.malumPath("textures/misc/visionary_scryglass_glass.png");

	protected float scopeScale;

	@Override
	public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
		var minecraft = Minecraft.getInstance();

		float f = deltaTracker.getGameTimeDeltaTicks();
		scopeScale = Mth.lerp(0.5F * f, scopeScale, 1.125F);
		if (minecraft.options.getCameraType().isFirstPerson()) {
			if (VisionaryScryglassItem.isScopingScryglass(minecraft.player)) {
				renderMonocleOverlay(guiGraphics, scopeScale);
			} else {
				scopeScale = 0.5F;
			}
		}
    }

	public static void renderMonocleOverlay(GuiGraphics guiGraphics, float scopeScale) {
		int height = guiGraphics.guiHeight();
		int width = guiGraphics.guiWidth();
		float smaller = Math.min(width, height);
		float relative = Math.min(width / smaller, height / smaller) * scopeScale;
		var poseStack = guiGraphics.pose();

		int size = Mth.floor(smaller * relative);
		int left = (width - size) / 2;
		int right = left + size;
		int top = (height - size) / 2;
		int bottom = top + size;

		RenderSystem.enableBlend();
		var builder = VFXBuilders.createScreen()
				.setPositionWithWidth(left, top, size, size)
				.setTexture(VISIONARY_SCRYGLASS_SCOPE)
				.setShader(GameRenderer.getPositionTexColorShader())
				.blit(poseStack);

		var overlay = RenderType.guiOverlay();
		var color = ColorHelper.getColor(0, 0, 0, 0.5f);

		builder.setAlpha(0.5f).setTexture(VISIONARY_SCRYGLASS_GLASS).blit(poseStack);

		var shaderInstance = LodestoneShaders.SCREEN_DISTORTED_TEXTURE.getShaderInstance();
		builder.setShader(shaderInstance).setAlpha(1f).setTexture(VISIONARY_SCRYGLASS_OVERLAY);

		shaderInstance.safeGetUniform("LumiTransparency").set(1f);

		for (int i = 0; i < 4; i++) {
			float index = i + 1;
			shaderInstance.safeGetUniform("YFrequency").set(15f * index);
			shaderInstance.safeGetUniform("XFrequency").set(15f * index);

			shaderInstance.safeGetUniform("Speed").set(550f + 250f * index);
			shaderInstance.safeGetUniform("Intensity").set(80f - 15f * index);

			builder.setAlpha(1 / index).blit(poseStack);
		}


		guiGraphics.fill(overlay, 0, bottom, width, height, -90, color);
		guiGraphics.fill(overlay, 0, 0, width, top, -90, color);
		guiGraphics.fill(overlay, 0, top, left, bottom, -90, color);
		guiGraphics.fill(overlay, right, top, width, bottom, -90, color);


		RenderSystem.disableBlend();
	}
}
