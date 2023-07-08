package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.common.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.render.ColorHelper;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;

import java.util.*;
import java.util.stream.*;

import static com.sammy.malum.MalumMod.*;
import static team.lodestar.lodestone.LodestoneLib.*;
import static team.lodestar.lodestone.handlers.RenderHandler.*;

public class FloatingItemEntityRenderer extends EntityRenderer<FloatingItemEntity> {
    public final ItemRenderer itemRenderer;

    public FloatingItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final ResourceLocation LIGHT_TRAIL = malumPath("textures/vfx/light_trail.png");
    private static final RenderType LIGHT_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);
    @Override
    public void render(FloatingItemEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
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
            positions.set(positions.size() - 1, new EntityHelper.PastPosition(new Vec3(x, y + entity.getYOffset(partialTicks) + 0.25F, z).add(entity.getDeltaMovement().multiply(partialTicks, partialTicks, partialTicks)), 0));
        }

        List<Vector4f> mappedPastPositions = positions.stream().map(p -> p.position).map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList());
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
        Vector3f offset = new Vector3f(-x, -y, -z);

        VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(LIGHT_TYPE);
        for (int i = 0; i < 3; i++) {
            float size = 0.225f + i * 0.15f;
            float alpha = (0.3f - i * 0.12f);
            builder
                    .setAlpha(alpha)
                    .renderTrail(lightBuffer, poseStack, offset, mappedPastPositions, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 3f, entity.endColor, entity.startColor)))
                    .renderTrail(lightBuffer, poseStack, offset, mappedPastPositions, f -> 1.5f * size, f -> builder.setAlpha(alpha * f / 2f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, entity.endColor, entity.startColor)))
                    .renderTrail(lightBuffer, poseStack, offset, mappedPastPositions, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, entity.endColor, entity.startColor)));
        }
        ItemStack itemStack = entity.getItem();
        BakedModel model = this.itemRenderer.getModel(itemStack, entity.level, null, entity.getItem().getCount());
        float yOffset = entity.getYOffset(partialTicks);
        float scale = model.getTransforms().getTransform(ItemTransforms.TransformType.GROUND).scale.y();
        float rotation = entity.getRotation(partialTicks);
        poseStack.translate(0.0D, (yOffset + 0.25F * scale), 0.0D);
        poseStack.mulPose(Vector3f.YP.rotation(rotation));
        this.itemRenderer.render(itemStack, ItemTransforms.TransformType.GROUND, false, poseStack, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();
        poseStack.pushPose();
        renderSpirit(entity, itemRenderer, partialTicks, poseStack, bufferIn, packedLightIn);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    public static void renderSpirit(FloatingItemEntity entity, ItemRenderer itemRenderer, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        ItemStack itemStack = entity.getItem();
        BakedModel model = itemRenderer.getModel(itemStack, entity.level, null, entity.getItem().getCount());
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(entity.startColor);
        float yOffset = entity.getYOffset(partialTicks);
        float scale = model.getTransforms().getTransform(ItemTransforms.TransformType.GROUND).scale.y();
        float rotation = entity.getRotation(partialTicks);
        poseStack.pushPose();
        poseStack.translate(0.0D, (yOffset + 0.25F * scale), 0.0D);
        poseStack.mulPose(Vector3f.YP.rotation(rotation));
        itemRenderer.render(itemStack, ItemTransforms.TransformType.GROUND, false, poseStack, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();
        poseStack.pushPose();
        poseStack.translate(0.0D, (yOffset + 0.5F * scale), 0.0D);
        renderSpiritGlimmer(poseStack, builder, partialTicks);
        poseStack.popPose();
    }

    public static void renderSpiritGlimmer(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, float partialTicks) {
        ClientLevel level = Minecraft.getInstance().level;
        float v = level.getGameTime() + partialTicks;
        float time = (float) ((Math.sin(v) + v % 15f) / 15f);
        if (time >= 0.5f) {
            time = 1f - time;
        }
        float multiplier = 1 + Easing.BOUNCE_IN_OUT.ease(time*2f, 0, 0.25f, 1);
        poseStack.pushPose();
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        for (int i = 0; i < 3; i++) {
            float size = (0.125f + i * 0.13f) * multiplier;
            float alpha = (0.75f - i * 0.3f);
            builder.setAlpha(alpha * 0.6f).renderQuad(DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(lodestonePath("textures/particle/wisp.png"))), poseStack, size * 0.75f);
            builder.setAlpha(alpha).renderQuad(DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(lodestonePath("textures/particle/twinkle.png"))), poseStack, size);
        }
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingItemEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
