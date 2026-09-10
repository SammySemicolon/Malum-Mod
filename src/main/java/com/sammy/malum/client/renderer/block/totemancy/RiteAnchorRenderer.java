package com.sammy.malum.client.renderer.block.totemancy;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.block.curiosities.totem.anchor.*;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.core.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.builder.data.CubeVertexData;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.lang.Math;

public class RiteAnchorRenderer implements BlockEntityRenderer<RiteAnchorBlockEntity> {

    public RiteAnchorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(RiteAnchorBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        var spiritType = blockEntityIn.getSpirit();
        if (spiritType == null) {
            return;
        }

        float delta = blockEntityIn.getGlowDelta();

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        for (int i = 0; i < 4; i++) {
            int rotation = i * 90;
            Direction direction = Direction.fromYRot(rotation);
            if (RiteAnchorBlock.isOccluded(blockEntityIn.getBlockState(), direction)) {
                continue;
            }

            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(rotation));
            renderFace(poseStack, spiritType, MalumRenderTypeTokens.RITE_ANCHOR_GLOW_SIDE, getSideVertices(), delta, partialTicks);
            poseStack.popPose();
        }
        renderFace(poseStack, spiritType, MalumRenderTypeTokens.RITE_ANCHOR_GLOW_TOP, getTopVertices(), delta, partialTicks);

        var aimDirection = blockEntityIn.getAimDirection();
        if (aimDirection != null) {
            poseStack.pushPose();
            if (aimDirection.getData2d() != -1) {
                var vertices = getTopVertices();
                for (Vector3f vertex : vertices) {
                    vertex.add(0, 0.125f, 0);
                }
                int rotation = aimDirection.getData2d();
                poseStack.mulPose(Axis.YN.rotationDegrees(180 + rotation * 90));
                renderFace(poseStack, spiritType, MalumRenderTypeTokens.RITE_ANCHOR_GLOW_POINTER, vertices, delta, partialTicks);
            }
            else {
                for (int i = 0; i < 4; i++) {
                    var vertices = getSideVertices();
                    for (Vector3f vertex : vertices) {
                        vertex.add(0, 0.3125f, 0);
                    }
                    poseStack.pushPose();
                    poseStack.mulPose(Axis.YN.rotationDegrees(i * 90));
                    if (aimDirection == RiteAnchorBlockEntity.AimState.PULL) {
                        poseStack.mulPose(Axis.XN.rotationDegrees(180));
                        for (Vector3f vertex : vertices) {
                            vertex.add(0, -0.625f, 0);
                        }
                    }
                    renderFace(poseStack, spiritType, MalumRenderTypeTokens.RITE_ANCHOR_GLOW_POINTER_SMALL, vertices, delta, partialTicks);
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    private static void renderFace(PoseStack poseStack, SpiritArcanaType spiritType, RenderTypeToken token, Vector3f[] vertices, float delta, float partialTicks) {
        var level = Minecraft.getInstance().level;
        float gameTime = level.getGameTime() + partialTicks;
        float alpha = delta * 0.7f;
        float ease = Easing.SINE_OUT.ease(delta);
        float offsetDistance = 0.2f - ease * 0.2f;
        float wobbleStrength = 0.1f - ease * 0.075f;
        int time = 160;
        float timer = ((gameTime % time) / time);
        var renderType = LodestoneRenderTypes.ADDITIVE_TEXTURE.apply(token).addModifier(r -> r.setCullState(RenderStateShard.NO_CULL));
        for (int j = 0; j < 4; j++) {
            var color = j <= 1 ? spiritType.getPrimaryColor() : spiritType.getSecondaryColor();
            double offset = 0;
            if (offsetDistance > 0) {
                double angle = j / 4f * (Math.PI * 2);
                angle += timer * (Math.PI * 2);
                offset = (offsetDistance * Math.cos(angle));
                if (j % 2 == 0) {
                    offset *= -1;
                }
            }

            poseStack.pushPose();
            poseStack.translate(offset, 0, 0);
            CubeVertexData.applyVertexWobble(vertices, 0, wobbleStrength);
            SpiritBasedWorldVFXBuilder.create(spiritType)
                    .setColor(color, alpha)
                    .setRenderType(renderType)
                    .renderQuad(poseStack, vertices, 1f);
            poseStack.popPose();
            alpha *= (1 - (delta + 0.2f) * 0.4f);
        }
    }

    private static @NotNull Vector3f[] getSideVertices() {
            return new Vector3f[]{
                    new Vector3f(-0.55f, -0.5f, 0.55f), new Vector3f(0.55f, -0.5f, 0.55f),
                    new Vector3f(0.55f, 0.5f, 0.55f), new Vector3f(-0.55f, 0.5f, 0.55f)};
    }

    private static @NotNull Vector3f[] getTopVertices() {
        return new Vector3f[]{
                new Vector3f(-0.55f, 0.55f, 0.55f), new Vector3f(0.55f, 0.55f, 0.55f),
                new Vector3f(0.55f, 0.55f, -0.55f), new Vector3f(-0.55f, 0.55f, -0.55f)};
    }
}