package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.common.entity.nitrate.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.rendering.*;

import java.util.*;
import java.util.stream.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.client.renderer.entity.FloatingItemEntityRenderer.*;
import static com.sammy.malum.common.entity.nitrate.VividNitrateEntity.*;
import static team.lodestar.lodestone.handlers.RenderHandler.*;

public class VividNitrateEntityRenderer extends EntityRenderer<VividNitrateEntity> {

    public VividNitrateEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final ResourceLocation LIGHT_TRAIL = malumPath("textures/vfx/light_trail.png");
    private static final RenderType LIGHT_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);

    @Override
    public void render(VividNitrateEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
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
//        for (int i = 0; i < 3; i++) {
//            float size = 0.3f + i * 0.12f;
//            float alpha = (0.16f - i * 0.035f) * trailVisibility;
//            builder
//                    .setAlpha(alpha)
//                    .renderTrail(lightBuffer, poseStack, mappedPastPositions, f -> size, f -> builder.setAlpha(alpha * f).setColor(COLOR_FUNCTION.apply(new ColorFunctionData(entity.level, f*3f))))
//                    .renderTrail(lightBuffer, poseStack, mappedPastPositions, f -> 1.5f * size, f -> builder.setAlpha(alpha * f * 1.5f).setColor(COLOR_FUNCTION.apply(new ColorFunctionData(entity.level, f*2f))))
//                    .renderTrail(lightBuffer, poseStack, mappedPastPositions, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(COLOR_FUNCTION.apply(new ColorFunctionData(entity.level, f*2f))));
//        }
//
//        poseStack.translate(0, entity.getYOffset(partialTicks) + 0.25F, 0);
//        poseStack.scale(1.2f*trailVisibility,1.2f*trailVisibility,1.2f*trailVisibility);
//        builder.setOffset(0, 0, 0);
//        builder.setColor(COLOR_FUNCTION.apply(new ColorFunctionData(entity.level, 0.85f)));
//        builder.setAlpha(trailVisibility);
//        renderSpiritGlimmer(poseStack, builder, partialTicks);
//        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(VividNitrateEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
