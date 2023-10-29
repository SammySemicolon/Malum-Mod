package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;

import static com.sammy.malum.MalumMod.malumPath;

public class EthericNitrateEntityRenderer extends EntityRenderer<EthericNitrateEntity> {

    public EthericNitrateEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final ResourceLocation LIGHT_TRAIL = malumPath("textures/vfx/light_trail.png");
    private static final RenderType LIGHT_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);

    @Override
    public void render(EthericNitrateEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
//        poseStack.pushPose();
//        List<EntityHelper.PastPosition> positions = new ArrayList<>(entity.pastPositions);
//        if (positions.size() > 1) {
//            for (int i = 0; i < positions.size() - 2; i++) {
//                EntityHelper.PastPosition position = positions.get(i);
//                EntityHelper.PastPosition nextPosition = positions.get(i + 1);
//                float x = (float) Mth.lerp(partialTicks, position.position.x, nextPosition.position.x);
//                float y = (float) Mth.lerp(partialTicks, position.position.y, nextPosition.position.y);
//                float z = (float) Mth.lerp(partialTicks, position.position.z, nextPosition.position.z);
//                positions.set(i, new EntityHelper.PastPosition(new Vec3(x, y, z), position.time));
//            }
//        }
//        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
//        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
//        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
//        if (positions.size() > 1) {
//            positions.set(positions.size() - 1, new EntityHelper.PastPosition(new Vec3(x, y + entity.getYOffset(partialTicks) + 0.25F, z).add(entity.getDeltaMovement().multiply(partialTicks, partialTicks, partialTicks)), 0));
//        }
//
//        List<Vector4f> mappedPastPositions = positions.stream().map(p -> p.position).map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList());
//        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setOffset(-x, -y, -z);
//
//        VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(LIGHT_TYPE);
//        float trailVisibility = Math.min(entity.windUp, 1);
//        Color firstColor = EthericNitrateEntity.FIRST_COLOR;
//        Color secondColor = EthericNitrateEntity.SECOND_COLOR;
//        for (int i = 0; i < 3; i++) {
//            float size = 0.25f + i * 0.1f;
//            float alpha = (0.16f - i * 0.04f) * trailVisibility;
//            builder
//                    .setAlpha(alpha)
//                    .renderTrail(lightBuffer, poseStack, mappedPastPositions, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 3f, secondColor, firstColor)))
//                    .renderTrail(lightBuffer, poseStack, mappedPastPositions, f -> 1.5f * size, f -> builder.setAlpha(alpha * f * 1.5f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondColor, firstColor)))
//                    .renderTrail(lightBuffer, poseStack, mappedPastPositions, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondColor, firstColor)));
//        }
//        poseStack.translate(0, entity.getYOffset(partialTicks) + 0.25F, 0);
//        poseStack.scale(1.2f*trailVisibility,1.2f*trailVisibility,1.2f*trailVisibility);
//        builder.setOffset(0, 0, 0);
//        builder.setColor(firstColor);
//        builder.setAlpha(trailVisibility);
//        renderSpiritGlimmer(poseStack, builder, partialTicks);
//
//        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(EthericNitrateEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
