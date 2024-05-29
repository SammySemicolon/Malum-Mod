package com.sammy.malum.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.client.ModelRegistry;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TopHatCurioRenderer implements TrinketRenderer {

    private static final ResourceLocation HAT = MalumMod.malumPath("textures/cosmetic/tophat.png");

    @Override
    public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, LivingEntity livingEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        renderTopHat(livingEntity, entityModel, RenderType.entityTranslucent(HAT), poseStack, multiBufferSource, light);
    }

    public static void renderTopHat(LivingEntity livingEntity, EntityModel<? extends LivingEntity> entityModel, RenderType renderType, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light) {
        VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(renderType);
        TrinketRenderer.followBodyRotations(livingEntity, (HumanoidModel<LivingEntity>) entityModel);
        //TODO DONE? ICurioRenderer.followHeadRotations(livingEntity, ModelRegistry.TOP_HAT.topHat);
        ModelRegistry.TOP_HAT.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}