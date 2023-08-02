package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.*;
import com.sammy.malum.common.entity.night_terror.NightTerrorSeekerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Math;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.client.renderer.entity.FloatingItemEntityRenderer.renderSpiritGlimmer;
import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

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
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
        Vector3f offset = new Vector3f(-x, -y, -z);

        float fadeOut = Easing.SINE_IN_OUT.ease(Mth.clamp((entity.age - entity.fadeoutStart) / (float) entity.fadeoutDuration, 0, 1), 0, 1, 1);
        float trailVisibility = Mth.clamp(entity.age / 10f,0, 1) * (entity.age > entity.fadeoutStart ? (1 - fadeOut) : 1);

        Color firstColor = NightTerrorSeekerEntity.NIGHT_TERROR_PURPLE;
        Color secondColor = NightTerrorSeekerEntity.NIGHT_TERROR_DARK;

        VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(LIGHT_TYPE);
        builder.setColor(firstColor);
        builder.renderTrail(lightBuffer, poseStack, offset, mappedPastPositions, f -> 0.3f, f -> builder.setAlpha(trailVisibility * Math.max(0, Easing.SINE_IN.ease(f, 0, 0.5f, 1))));
        builder.renderTrail(lightBuffer, poseStack, offset, mappedPastPositions, f -> 0.2f, f -> builder.setAlpha(trailVisibility * Math.max(0, Easing.SINE_IN.ease(f, 0, 0.75f, 1))));

        VertexConsumer darkBuffer = DELAYED_RENDER.getBuffer(DARK_TYPE);
        builder.setColor(secondColor);
        builder.renderTrail(darkBuffer, poseStack, offset, mappedPastPositions, f -> 0.35f, f -> builder.setAlpha(trailVisibility * Math.max(0, Easing.SINE_IN.ease(f, 0, 0.5f, 1))));
        builder.renderTrail(darkBuffer, poseStack, offset, mappedPastPositions, f -> 0.25f, f -> builder.setAlpha(trailVisibility * Math.max(0, Easing.SINE_IN.ease(f, 0, 0.75f, 1))));

        poseStack.translate(0, 0.25F, 0);
        poseStack.scale(1.2f * trailVisibility, 1.2f * trailVisibility, 1.2f * trailVisibility);
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