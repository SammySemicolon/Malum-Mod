package com.sammy.malum.client.renderer.block.enscription;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.client.renderer.renderpass.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.world.phys.*;
import org.joml.*;
import team.lodestar.lodestone.modules.rendering.LodestoneRenderingSystem;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.builder.WorldVFXBuilder;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

import java.awt.*;


public class VoidConduitRenderer implements BlockEntityRenderer<VoidConduitBlockEntity> {

    public VoidConduitRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(VoidConduitBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        float height = 0.375f;
        float width = 4.5f;

        Vector3f[] positions = new Vector3f[]{new Vector3f(-width, height, width), new Vector3f(width, height, width), new Vector3f(width, height, -width), new Vector3f(-width, height, -width)};
        WorldVFXBuilder builder = VFXBuilders.createWorld();
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.01f, 0.5f);

        var vignette = LodestoneRenderTypes.TRANSPARENT_TEXTURE.apply(MalumRenderTypeTokens.VOID_VIGNETTE);
        builder.replaceBufferSource(LodestoneRenderingSystem.LATE_DEFERRED_RENDER)
                .setRenderType(vignette);

        poseStack.pushPose();
        for (int i = 0; i < 5; i++) {
            float alpha = 0.75f - i * 0.1f;
            builder.setAlpha(alpha).renderQuad(poseStack, positions);
            poseStack.translate(0, 0.15f, 0);
        }
        poseStack.popPose();

        long gameTime = blockEntityIn.getLevel().getGameTime();
        float uOffset = ((gameTime + partialTicks) % 4000) / 2000f;
        float vOffset = ((gameTime + 500f + partialTicks) % 8000) / 8000f;

        builder.replaceBufferSource(LodestoneRenderingSystem.DEFERRED_RENDER.getTarget());

        Color[] colors = new Color[] {
                MalumSpiritTypes.ELDRITCH_SPIRIT.getPrimaryColor(),
                MalumSpiritTypes.WICKED_SPIRIT.getSecondaryColor(),
                MalumSpiritTypes.AQUEOUS_SPIRIT.getPrimaryColor(),
                MalumSpiritTypes.AERIAL_SPIRIT.getSecondaryColor()
        };
        for (int i = 0; i < 8; i++) {
            float speed = 1000f + 250f * i;
            float alpha = 0.115f - i * 0.01f;

            var uniforms = UniformData.create()
                    .setUniform("Speed", speed)
                    .setUniform("Width", 1024f)
                    .setUniform("Height", 1024f)
                    .setSampler("Skybox", ParallelWorldRenderer.INSTANCE.getTarget().getColorTextureId())
                    .build();

            var distortion = MalumRenderTypes.WEEPING_SPYHOLE.apply(MalumRenderTypeTokens.VOID_NOISE).addUniformData(uniforms);
            builder.setColor(colors[i % 4]).setLightLevel(blockEntityIn.getBlockPos()).setRenderType(distortion);

            builder.setAlpha(alpha);
            builder.setUV(-uOffset, vOffset, 1 - uOffset, 1 + vOffset).renderQuad(poseStack, positions, 1f);
            builder.setUV(uOffset, -vOffset, 1 + uOffset, 1 - vOffset).renderQuad(poseStack, positions, 1f);
            uOffset = -uOffset - 0.2f;
            vOffset = -vOffset + 0.7f;
            poseStack.translate(0, 0.15f, 0);
            poseStack.mulPose(Axis.YP.rotationDegrees(90));
        }
        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(VoidConduitBlockEntity voidConduit) {
        var pos = voidConduit.getBlockPos();
        return new AABB(pos.getX() - 6, pos.getY() - 2, pos.getZ() - 6, pos.getX() + 7, pos.getY() + 3, pos.getZ() + 7);
    }
}