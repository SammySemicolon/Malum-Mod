package com.sammy.malum.client.renderer.curio;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.setup.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.client.*;

public class TopHatCurioRenderer implements ICurioRenderer {

    private static final ResourceLocation HAT = MalumMod.malumPath("textures/cosmetic/tophat.png");

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        final LivingEntity livingEntity = slotContext.entity();
        renderTopHat(livingEntity, LodestoneRenderTypeRegistry.TEXTURE.applyAndCache(HAT), poseStack, renderTypeBuffer, light);
    }

    public static void renderTopHat(LivingEntity livingEntity, RenderType renderType, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light) {
        VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(renderType);
        ICurioRenderer.followHeadRotations(livingEntity, ModelRegistry.TOP_HAT.topHat);
        ModelRegistry.TOP_HAT.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}