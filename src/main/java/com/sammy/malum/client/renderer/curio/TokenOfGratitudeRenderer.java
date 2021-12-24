package com.sammy.malum.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.item.equipment.curios.CurioTokenOfGratitude;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.registry.item.ItemRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.UUID;

public class TokenOfGratitudeRenderer implements ICurioRenderer {
    private static final ResourceLocation TEXTURE = DataHelper.prefix("textures/other/sammy_texture.png");
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (slotContext.entity() instanceof Player playerEntity)
        {
            if (playerEntity.getUUID().equals(UUID.fromString(CurioTokenOfGratitude.SAMMY)))
            {
                poseStack.pushPose();
                double curSpeed = playerEntity.getDeltaMovement().length();
                if (curSpeed != 0.0)
                {
                    Vec3 A = new Vec3(playerEntity.getDeltaMovement().x, 0.0, playerEntity.getDeltaMovement().z);
                    Vec3 yawLook = Vec3.directionFromRotation(0.0f, playerEntity.getYRot());
                    Vec3 look = new Vec3(yawLook.x, 0.0, yawLook.z);
                    Vec3 desiredDirection = look.yRot((float) Math.toRadians(90)).normalize();
                    Vec3 sidewaysVelocity = desiredDirection.scale(A.dot(desiredDirection));
                    double speedAndDirection = (sidewaysVelocity.length() * -Math.signum(desiredDirection.dot(sidewaysVelocity))) / curSpeed;
                    double rotation = speedAndDirection * 55;
                    poseStack.mulPose(Vector3f.YP.rotationDegrees((float) rotation));
                }
                if (!playerEntity.isShiftKeyDown())
                {
                    double xRotation = Math.sin(playerEntity.level.getGameTime() / 18f) * 6;
                    poseStack.mulPose(Vector3f.XP.rotationDegrees((float) xRotation));
                }
                VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(TEXTURE), false, stack.hasFoil());
                ItemRegistry.ClientOnly.TAIL_MODEL.setupAnim(playerEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                ItemRegistry.ClientOnly.TAIL_MODEL.prepareMobModel(playerEntity, limbSwing, limbSwingAmount, partialTicks);
                ICurioRenderer.translateIfSneaking(poseStack, playerEntity);
                ICurioRenderer.rotateIfSneaking(poseStack, playerEntity);
                ItemRegistry.ClientOnly.TAIL_MODEL.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                poseStack.popPose();
            }
        }
    }
}
