package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.sammy.malum.common.entity.FloatingItemEntity;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.sammy.malum.MalumMod.prefix;
import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class FloatingItemEntityRenderer extends EntityRenderer<FloatingItemEntity> {
    public final ItemRenderer itemRenderer;

    public FloatingItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final ResourceLocation LIGHT_TRAIL = prefix("textures/vfx/light_trail.png");
    private static final RenderType LIGHT_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(LIGHT_TRAIL);

    @Override
    public void render(FloatingItemEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        ArrayList<Vec3> positions = new ArrayList<>(entity.pastPositions);
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld();

        int amount = 3;
        for (int i = 0; i < amount; i++) {
            float index = (amount - 1) - i;
            float size = index * 0.15f + (float) Math.exp(index * 0.3f);
            float alpha = 0.1f * (float) Math.exp(i * 0.3f);
            Color color = entity.startColor;
            builder.setColor(color).setOffset((float) -entity.getX(), (float) -entity.getY(), (float) -entity.getZ())
                    .setAlpha(alpha)
                    .renderTrail(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, positions.stream().map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList()), f -> f * size)
                    .renderTrail(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, positions.stream().map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList()), f -> Easing.QUARTIC_IN_OUT.ease(f, 0, size, 1));
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