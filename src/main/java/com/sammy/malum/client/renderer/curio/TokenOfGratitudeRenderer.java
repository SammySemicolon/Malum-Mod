package com.sammy.malum.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.equipment.curios.CurioTokenOfGratitude;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.ortus.helpers.RenderHelper;
import com.sammy.ortus.setup.OrtusRenderTypes;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class TokenOfGratitudeRenderer implements ICurioRenderer {
    private static final ResourceLocation SAMMY = MalumMod.prefix("textures/other/sammy_texture.png");
    private static final ResourceLocation OWL_PERSON = MalumMod.prefix("textures/other/owl_person_texture.png");

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (slotContext.entity() instanceof AbstractClientPlayer playerEntity) {
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.SAMMY)) {
                poseStack.pushPose();
                Vec3 movement = new Vec3(playerEntity.getDeltaMovement().x, 0, playerEntity.getDeltaMovement().z);
                double wagSpeed = playerEntity.getDeltaMovement().length();
                if (wagSpeed != 0) {
                    double factor = 55;
                    Vec3 yawLook = Vec3.directionFromRotation(0.0f, playerEntity.getYRot());
                    Vec3 look = new Vec3(yawLook.x, 0.0, yawLook.z);
                    Vec3 desiredDirection = look.yRot((float) Math.toRadians(90)).normalize();
                    Vec3 sidewaysVelocity = desiredDirection.scale(movement.dot(desiredDirection));
                    double speedAndDirection = (sidewaysVelocity.length() * -Math.signum(desiredDirection.dot(sidewaysVelocity))) / wagSpeed;
                    poseStack.mulPose(Vector3f.YP.rotationDegrees((float) (speedAndDirection * factor)));
                }
                float ambientFactor = playerEntity.isShiftKeyDown() ? 2 : 6;
                double ambientXRotation = Math.sin(playerEntity.level.getGameTime() / 18f) * ambientFactor;
                poseStack.mulPose(Vector3f.XP.rotationDegrees((float) ambientXRotation));
                double ambientYRotation = Math.cos(playerEntity.level.getGameTime() / 24f) * -ambientFactor;
                poseStack.mulPose(Vector3f.YP.rotationDegrees((float) ambientYRotation));
                VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(SAMMY), false, stack.hasFoil());
                ItemRegistry.ClientOnly.TAIL_MODEL.setupAnim(playerEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                ItemRegistry.ClientOnly.TAIL_MODEL.prepareMobModel(playerEntity, limbSwing, limbSwingAmount, partialTicks);
                ICurioRenderer.translateIfSneaking(poseStack, playerEntity);
                ICurioRenderer.rotateIfSneaking(poseStack, playerEntity);
                ItemRegistry.ClientOnly.TAIL_MODEL.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                poseStack.popPose();
            }
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.OWL_PERSON)) {
                poseStack.pushPose();
                renderGlowingEyes(playerEntity, OWL_PERSON, poseStack, renderTypeBuffer);
                poseStack.popPose();
            }
        }
    }

    public static void renderGlowingEyes(AbstractClientPlayer playerEntity, ResourceLocation texture, PoseStack poseStack, MultiBufferSource renderTypeBuffer) {
        VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(OrtusRenderTypes.SOLID_TEXTURE.apply(texture));
        ICurioRenderer.followHeadRotations(playerEntity, ItemRegistry.ClientOnly.HEAD_OVERLAY_MODEL.overlay);
        ItemRegistry.ClientOnly.HEAD_OVERLAY_MODEL.renderToBuffer(poseStack, vertexconsumer, RenderHelper.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}