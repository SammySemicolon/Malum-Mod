package com.sammy.malum.common.items.equipment.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.models.ModelDarkPrincesCrown;
import com.sammy.malum.client.models.ModelLapisTail;
import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

import java.util.UUID;

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
        livingEntity.world.playSound(null, livingEntity.getPosition(), MalumSounds.SINISTER_EQUIP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        livingEntity.world.playSound(null, livingEntity.getPosition(), MalumSounds.HOLY_EQUIP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }

    @Nonnull
    @Override
    public ICurio.DropRule getDropRule(LivingEntity livingEntity, ItemStack stack)
    {
        return ALWAYS_KEEP;
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        return true;
    }

    public static final String sammy_uuid = "0ca54301-6170-4c44-b3e0-b8afa6b81ed2";
    public final ResourceLocation sammy_texture = new ResourceLocation(MalumMod.MODID, "textures/other/sammy_texture.png");
    public ModelLapisTail<LivingEntity> sammy_model;

    public static final String ura_uuid = "fddaefa0-31d2-4acf-9cd2-4711d0e5e5d5";
    public final ResourceLocation ura_texture = MalumHelper.prefix("textures/other/ura_texture.png");
    public ModelDarkPrincesCrown<LivingEntity> ura_model;
    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
    {
        if (livingEntity instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            if (playerEntity.getUniqueID().equals(UUID.fromString(ura_uuid)))
            {
                matrixStack.push();
                if (ura_model == null)
                {
                    ura_model = new ModelDarkPrincesCrown<>();
                }
                ICurio.RenderHelper.followHeadRotations(livingEntity, ura_model.crown);
                IVertexBuilder jtBuilder = ItemRenderer.getBuffer(renderTypeBuffer, ura_model.getRenderType(ura_texture), false, stack.hasEffect());
                ura_model.render(matrixStack, jtBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStack.pop();
                return;
            }
            if (playerEntity.getUniqueID().equals(UUID.fromString(sammy_uuid)))
            {
                matrixStack.push();
                if (sammy_model == null)
                {
                    sammy_model = new ModelLapisTail<>();
                }
                double curSpeed = livingEntity.getMotion().length();
                if (curSpeed != 0.0)
                {
                    Vector3d A = new Vector3d(livingEntity.getMotion().x, 0.0, livingEntity.getMotion().z);
                    Vector3d yawLook = Vector3d.fromPitchYaw(0.0f, livingEntity.rotationYaw);
                    Vector3d look = new Vector3d(yawLook.x, 0.0, yawLook.z);
                    Vector3d desiredDirection = look.rotateYaw((float) Math.toRadians(90)).normalize();
                    Vector3d sidewaysVelocity = desiredDirection.scale(A.dotProduct(desiredDirection));
                    double speedAndDirection = (sidewaysVelocity.length() * -Math.signum(desiredDirection.dotProduct(sidewaysVelocity))) / curSpeed;
                    double rotation = speedAndDirection * 55;
                    matrixStack.rotate(Vector3f.YP.rotationDegrees((float) rotation));
                }
                if (!playerEntity.isSneaking())
                {
                    double xRotation = Math.sin(livingEntity.world.getGameTime() / 18f) * 6;
                    matrixStack.rotate(Vector3f.XP.rotationDegrees((float) xRotation));
                }
                sammy_model.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                sammy_model.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);

                ICurio.RenderHelper.followBodyRotations(livingEntity, sammy_model);
                ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
                ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, sammy_model.getRenderType(sammy_texture), false, false);
                sammy_model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                matrixStack.pop();
            }
        }
    }
}