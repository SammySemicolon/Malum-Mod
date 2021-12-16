package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.registry.misc.SoundRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

import static top.theillusivec4.curios.api.type.capability.ICurio.DropRule.ALWAYS_KEEP;

public class CurioTokenOfGratitude extends MalumCurioItem
{
    public CurioTokenOfGratitude(Properties builder)
    {
        super(builder);
    }

    @Override
    public void playRightClickEquipSound(LivingEntity livingEntity, ItemStack stack)
    {
        livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundRegistry.SINISTER_EQUIP, SoundSource.NEUTRAL, 1.0f, 1.0f);
        livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundRegistry.HOLY_EQUIP, SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    @Nonnull
    @Override
    public ICurio.DropRule getDropRule(LivingEntity livingEntity, ItemStack stack)
    {
        return ALWAYS_KEEP;
    }

    public static final String sammy_uuid = "0ca54301-6170-4c44-b3e0-b8afa6b81ed2";
  /*
    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        return true;
    }

    public final ResourceLocation sammy_texture = new ResourceLocation(MalumMod.MODID, "textures/other/sammy_texture.png");
    public FurryTailModel<LivingEntity> sammy_model;

    public static final String ura_uuid = "fddaefa0-31d2-4acf-9cd2-4711d0e5e5d5";
    public final ResourceLocation ura_texture = MalumHelper.prefix("textures/other/ura_texture.png");
    public DarkCrownModel<LivingEntity> ura_model;
    @Override
    public void render(String identifier, int index, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
    {
        if (livingEntity instanceof Player)
        {
            Player playerEntity = (Player) livingEntity;
            if (playerEntity.getUUID().equals(UUID.fromString(ura_uuid)))
            {
                poseStack.pushPose();
                if (ura_model == null)
                {
                    ura_model = new DarkCrownModel<>();
                }
                ICurio.RenderHelper.followHeadRotations(livingEntity, ura_model.crown);
                VertexConsumer jtBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, ura_model.renderType(ura_texture), false, stack.hasFoil());
                ura_model.renderToBuffer(poseStack, jtBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.popPose();
            }
            if (playerEntity.getUUID().equals(UUID.fromString(sammy_uuid)))
            {
                poseStack.pushPose();
                if (sammy_model == null)
                {
                    sammy_model = new FurryTailModel<>();
                }
                double curSpeed = livingEntity.getDeltaMovement().length();
                if (curSpeed != 0.0)
                {
                    Vec3 A = new Vec3(livingEntity.getDeltaMovement().x, 0.0, livingEntity.getDeltaMovement().z);
                    Vec3 yawLook = Vec3.directionFromRotation(0.0f, livingEntity.yRot);
                    Vec3 look = new Vec3(yawLook.x, 0.0, yawLook.z);
                    Vec3 desiredDirection = look.yRot((float) Math.toRadians(90)).normalize();
                    Vec3 sidewaysVelocity = desiredDirection.scale(A.dot(desiredDirection));
                    double speedAndDirection = (sidewaysVelocity.length() * -Math.signum(desiredDirection.dot(sidewaysVelocity))) / curSpeed;
                    double rotation = speedAndDirection * 55;
                    poseStack.mulPose(Vector3f.YP.rotationDegrees((float) rotation));
                }
                if (!playerEntity.isShiftKeyDown())
                {
                    double xRotation = Math.sin(livingEntity.level.getGameTime() / 18f) * 6;
                    poseStack.mulPose(Vector3f.XP.rotationDegrees((float) xRotation));
                }
                sammy_model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                sammy_model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);

                ICurio.RenderHelper.followBodyRotations(livingEntity, sammy_model);
                ICurio.RenderHelper.translateIfSneaking(poseStack, livingEntity);
                ICurio.RenderHelper.rotateIfSneaking(poseStack, livingEntity);
                VertexConsumer vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, sammy_model.renderType(sammy_texture), false, false);
                sammy_model.renderToBuffer(poseStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                poseStack.popPose();
            }
        }
    }

     */
}