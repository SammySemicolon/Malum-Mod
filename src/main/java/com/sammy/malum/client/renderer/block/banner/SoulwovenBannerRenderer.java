package com.sammy.malum.client.renderer.block.banner;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlock;
import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlockEntity;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import org.joml.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

import java.awt.*;
import java.lang.Math;


public class SoulwovenBannerRenderer implements BlockEntityRenderer<SoulwovenBannerBlockEntity> {

    public SoulwovenBannerRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SoulwovenBannerBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        var blockState = blockEntityIn.getBlockState();
        var pos = blockEntityIn.getBlockPos();
        var spirit = blockEntityIn.spirit;
        var type = blockState.getValue(SoulwovenBannerBlock.BANNER_TYPE);
        var direction = type.direction.getAxis().isVertical() ? type.equals(SoulwovenBannerBlock.BannerType.HANGING_Z) ? Direction.NORTH : Direction.WEST : type.direction;
        float sway = ((float) Math.floorMod((pos.getX() * 7L + pos.getY() * 9L + pos.getZ() * 13L) + blockEntityIn.getLevel().getGameTime(), 100L) + partialTicks) / 100.0F;
        float swayRotation = (0.01F * Mth.cos((float) (Math.PI * 2) * sway)) * (float) Math.PI;

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        if (type.direction.getAxis().isHorizontal()) {
            poseStack.translate(0, -0.25f, 0.0625f);
            swayRotation -= 0.0125f;
        } else {
            poseStack.translate(0, 0, 0.5f);
            swayRotation = swayRotation - 0.0157f;
        }
        poseStack.translate(0, 1f, 0);
        poseStack.mulPose(Axis.XP.rotation(swayRotation));
        float xStart = 0;
        float xEnd = 1;
        float yStart = -2;
        float yEnd = 0;
        var patternData = blockEntityIn.patternData;
        var token = RenderTypeToken.createToken(patternData.texturePath());
        var banner = LodestoneRenderTypes.CUTOUT_TEXTURE.apply(token).addModifier(b -> b.setCullState(RenderStateShard.NO_CULL));
        var vertices = new Vector3f[]{new Vector3f(xEnd, yStart, 0), new Vector3f(xStart, yStart, 0), new Vector3f(xStart, yEnd, 0), new Vector3f(xEnd, yEnd, 0)};
        var builder = VFXBuilders.createWorld()
                .setRenderType(banner)
                .setLightLevel(pos);
        builder.renderQuad(poseStack, vertices, 1f);
        if (spirit != null) {
            var glow = LodestoneRenderTypes.ADDITIVE_TEXTURE.apply(token).addModifier(b -> b.setCullState(RenderStateShard.NO_CULL));
            if (!blockEntityIn.intense) {
                glow.addUniformData(UniformData.LUMITRANSPARENT);
            }
            for (int i = 1; i < 4; i++) {
                Color color = spirit.getPrimaryColor();
                float alpha = 0.9f;
                var spiritBuilder = VFXBuilders.createWorld()
                        .setRenderType(glow)
                        .setColor(color);
                poseStack.pushPose();
                poseStack.translate(0, 0, 0.001f * i);
                spiritBuilder.setAlpha(alpha).renderQuad(poseStack, vertices);
                poseStack.translate(0, 0, -0.002f * i);
                spiritBuilder.setAlpha(alpha).renderQuad(poseStack, vertices);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(SoulwovenBannerBlockEntity altar) {
        var pos = altar.getBlockPos();
        return new AABB(pos.getX(), pos.getY() - 1, pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }
}