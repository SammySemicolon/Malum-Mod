package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.sammy.malum.common.entity.FloatingItemEntity;
import com.sammy.ortus.handlers.RenderHandler;
import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.data.ForgeRecipeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sammy.malum.MalumMod.prefix;
import static com.sammy.ortus.handlers.RenderHandler.*;
import static com.sammy.ortus.setup.OrtusRenderTypeRegistry.queueUniformChanges;

public class FloatingItemEntityRenderer extends EntityRenderer<FloatingItemEntity> {
    public final ItemRenderer itemRenderer;

    public FloatingItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final ResourceLocation LIGHT_TRAIL = prefix("textures/vfx/light_trail.png");
    private static final RenderType LIGHT_TYPE = OrtusRenderTypeRegistry.TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);

    private static final ResourceLocation MESSY_TRAIL = prefix("textures/vfx/messy_trail.png");
    private static final RenderType MESSY_TYPE = OrtusRenderTypeRegistry.SCROLLING_TEXTURE_TRIANGLE.apply(MESSY_TRAIL);

    @Override
    public void render(FloatingItemEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        ArrayList<EntityHelper.PastPosition> positions = new ArrayList<>(entity.pastPositions);
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
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setOffset(-x, -y, -z);

        VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(LIGHT_TYPE);
        float trailVisibility = Math.min(entity.windUp * 5f, 1);

        for (int i = 0; i < 3; i++) {
            float size = 0.25f + i * 0.1f;
            float alpha = (0.16f - i * 0.04f) * trailVisibility;
            int finalI = i;
            VertexConsumer messy = DELAYED_RENDER.getBuffer(queueUniformChanges(OrtusRenderTypeRegistry.copy(i, MESSY_TYPE),
                    (instance -> instance.safeGetUniform("Speed").set(1000 + 250f * finalI))));
            builder
                    .setAlpha(alpha)
                    .renderTrail(messy, poseStack, mappedPastPositions, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f*3f, entity.endColor, entity.startColor)))
                    .renderTrail(messy, poseStack, mappedPastPositions, f -> 1.5f * size, f -> builder.setAlpha(alpha * f * 1.5f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f*2f, entity.endColor, entity.startColor)))
                    .renderTrail(lightBuffer, poseStack, mappedPastPositions, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f*2f, entity.endColor, entity.startColor)));
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

        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingItemEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}