package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;
import java.util.*;


public class VoidConduitRenderer implements BlockEntityRenderer<VoidConduitBlockEntity> {

    public static final ArrayList<Color> COLORS = new ArrayList<>();
    static {
        COLORS.add(new Color(242, 242, 89));
        COLORS.add(new Color(242, 89, 242));
        COLORS.add(new Color(89, 242, 242));
        COLORS.add(new Color(242, 89, 89));
        COLORS.add(new Color(116, 240, 30));
        COLORS.add(new Color(240, 30, 116));
        COLORS.add(new Color(30, 116, 240));
        COLORS.add(new Color(116, 30, 240));
    }


    public static final ResourceLocation VIGNETTE = MalumMod.malumPath("textures/block/weeping_well/primordial_soup_vignette.png");
    public static final ResourceLocation SQUARE = MalumMod.malumPath("textures/vfx/expanding_square.png");

    public VoidConduitRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(VoidConduitBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderQuad(blockEntityIn, poseStack, partialTicks);
    }

    public void renderQuad(VoidConduitBlockEntity voidConduit, PoseStack poseStack, float partialTicks) {
        float height = 0.75f;
        float width = 1.5f;
        VertexConsumer textureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(VIGNETTE));
        Vector3f[] positions = new Vector3f[]{new Vector3f(-width, height, width), new Vector3f(width, height, width), new Vector3f(width, height, -width), new Vector3f(-width, height, -width)};
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.001f, 0.5f);
        builder.renderQuad(textureConsumer, poseStack, positions, 1f);
        poseStack.popPose();
        if (voidConduit.lingeringRadiance > 0) {
            float effectStrength = 1f;
            if (voidConduit.lingeringRadiance < 2400) {
                effectStrength = (float) voidConduit.lingeringRadiance / 2400f;
            }
            // create a for loop that renders the square animation using an additive texture render type
            // the square should be rendered at the same position as the vignette
            textureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(SQUARE));
            poseStack.pushPose();
            poseStack.translate(0.5f, 0f, 0.5f);
            for (int i = 0; i < 8; i++) {
                poseStack.translate(0f, 0.001f, 0f);
                //the frame for the animation should be calculated using the current tick and the index of the for loop
                //the frame should be calculated using the modulus operator
                float gameTime = (voidConduit.getLevel().getGameTime() + partialTicks) % 160;
                int frame = (int) ((gameTime / 20 + i) % 8);
                builder.setUV(0.125f * frame, 0f, 0.125f * (frame + 1), 1f);
                builder.setColor(COLORS.get(i));
                builder.renderQuad(textureConsumer, poseStack, positions, effectStrength);
            }
            poseStack.popPose();
        }
    }
}