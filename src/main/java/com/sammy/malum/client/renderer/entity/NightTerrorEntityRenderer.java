package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.common.entity.night_terror.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.client.renderer.entity.FloatingItemEntityRenderer.*;
import static team.lodestar.lodestone.handlers.RenderHandler.*;

public class NightTerrorEntityRenderer extends EntityRenderer<NightTerrorSeekerEntity> {

    private static final ResourceLocation LIGHT_TRAIL = malumPath("textures/vfx/light_trail.png");
    private static final RenderType LIGHT_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);
    private static final RenderType DARK_TYPE = LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);

    public NightTerrorEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0f;
        this.shadowStrength = 0f;
    }

    @Override
    public void render(NightTerrorSeekerEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        List<EntityHelper.PastPosition> positions = new ArrayList<>(entity.pastPositions);
        if (positions.size() > 1) {
            for (int i = 0; i < positions.size() - 2; i++) {
                EntityHelper.PastPosition position = positions.get(i);
                EntityHelper.PastPosition nextPosition = positions.get(i + 1);
                float x = (float) Mth.lerp(partialTicks, position.position.x, nextPosition.position.x);
                float y = (float) Mth.lerp(partialTicks, position.position.y, nextPosition.position.y);
                float z = (float) Mth.lerp(partialTicks, position.position.z, nextPosition.position.z);
                positions.set(i, new EntityHelper.PastPosition(new Vec3(x, y, z), position.time));
            }
        }
        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
        if (positions.size() > 1) {
            positions.set(positions.size() - 1, new EntityHelper.PastPosition(new Vec3(x, y + 0.25F, z).add(entity.getDeltaMovement().multiply(partialTicks, partialTicks, partialTicks)), 0));
        }

        List<Vector4f> mappedPastPositions = positions.stream().map(p -> p.position).map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList());
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setOffset(-x, -y, -z);

        float trailVisibility = Mth.clamp(entity.age / 10f,0, 1);
        Color firstColor = NightTerrorSeekerEntity.NIGHT_TERROR_PURPLE;
        Color secondColor = NightTerrorSeekerEntity.NIGHT_TERROR_DARK;

        VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(LIGHT_TYPE);
        builder.setColor(firstColor);
        builder.renderTrail(lightBuffer, poseStack, mappedPastPositions, f -> 0.4f, f -> builder.setAlpha(Math.max(0, Easing.SINE_IN.ease(f, 0, 0.5f, 1))));
        builder.renderTrail(lightBuffer, poseStack, mappedPastPositions, f -> 0.2f, f -> builder.setAlpha(Math.max(0, Easing.SINE_IN.ease(f, 0, 0.75f, 1))));

        VertexConsumer darkBuffer = DELAYED_RENDER.getBuffer(DARK_TYPE);
        builder.setColor(secondColor);
        builder.renderTrail(darkBuffer, poseStack, mappedPastPositions, f -> 0.45f, f -> builder.setAlpha(Math.max(0, Easing.SINE_IN.ease(f, 0, 0.5f, 1))));
        builder.renderTrail(darkBuffer, poseStack, mappedPastPositions, f -> 0.25f, f -> builder.setAlpha(Math.max(0, Easing.SINE_IN.ease(f, 0, 0.75f, 1))));


        poseStack.translate(0, 0.25F, 0);
        poseStack.scale(1.2f * trailVisibility, 1.2f * trailVisibility, 1.2f * trailVisibility);
        builder.setOffset(0, 0, 0);
        builder.setColor(firstColor);
        builder.setAlpha(trailVisibility);
        renderSpiritGlimmer(poseStack, builder, partialTicks);

        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(NightTerrorSeekerEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}