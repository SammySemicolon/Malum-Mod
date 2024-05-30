package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlockEntity;
import com.sammy.malum.registry.client.RenderTypeRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Vector3f;
import org.joml.Vector4f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;


public class VoidConduitRenderer implements BlockEntityRenderer<VoidConduitBlockEntity> {

    public static final RenderTypeToken VIGNETTE = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/weeping_well/primordial_soup_vignette.png"));
    public static final RenderTypeToken NOISE_TEXTURE = RenderTypeToken.createToken(MalumMod.malumPath("textures/vfx/void_noise.png"));

    public VoidConduitRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(VoidConduitBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderQuad(blockEntityIn, poseStack, partialTicks);
    }

    public void renderQuad(VoidConduitBlockEntity voidConduit, PoseStack poseStack, float partialTicks) {
        if (voidConduit.lingeringRadiance == 0) {
            float height = 0.375f;
            float width = 1.5f;


            Vector3f[] positions = new Vector3f[]{new Vector3f(-width, height, width), new Vector3f(width, height, width), new Vector3f(width, height, -width), new Vector3f(-width, height, -width)};
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld();
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.01f, 0.5f);

            builder.replaceBufferSource(RenderHandler.LATE_DELAYED_RENDER.getTarget()).setRenderType(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(VIGNETTE)).renderQuad(poseStack, positions, 1f);
            final long gameTime = voidConduit.getLevel().getGameTime();
            float uOffset = ((gameTime + partialTicks) % 4000) / 2000f;
            float vOffset = ((gameTime + 500f + partialTicks) % 8000) / 8000f;
            float alpha = 0.05f;

            final LodestoneRenderType renderType = RenderTypeRegistry.ADDITIVE_DISTORTED_TEXTURE.applyAndCache(NOISE_TEXTURE);
            builder.replaceBufferSource(RenderHandler.DELAYED_RENDER.getTarget());
            for (int i = 0; i < 3; i++) {
                builder.setAlpha(alpha);
                float speed = 1000f + 250f * i;
                builder.setColor(SpiritTypeRegistry.WICKED_SPIRIT.getPrimaryColor()).setRenderType(LodestoneRenderTypeRegistry.applyUniformChanges(LodestoneRenderTypeRegistry.copyAndStore(i, renderType), s -> {
                    s.safeGetUniform("Speed").set(speed);
                    s.safeGetUniform("Width").set(48f);
                    s.safeGetUniform("Height").set(48f);
                    s.safeGetUniform("UVEncasement").set(new Vector4f(-10, 20, -10, 20));
                }));
                builder.setUV(-uOffset, vOffset, 1 - uOffset, 1 + vOffset).renderQuad(poseStack, positions, 1f);
                builder.setUV(uOffset, -vOffset, 1 + uOffset, 1 - vOffset).renderQuad(poseStack, positions, 1f);
                alpha -= 0.0125f;
                uOffset = -uOffset - 0.2f;
                vOffset = -vOffset + 0.4f;
                poseStack.translate(0, 0.05f, 0);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                if (i == 0) {
                    builder.setColor(SpiritTypeRegistry.ELDRITCH_SPIRIT.getPrimaryColor());
                }
            }
            poseStack.popPose();
        }
    }
}