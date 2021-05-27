package com.sammy.malum.common.items.equipment.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.models.ModelLapisTail;
import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
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

    public static final String sammyUUID = "0ca54301-6170-4c44-b3e0-b8afa6b81ed2";
    public final ResourceLocation lapis_tail_texture = new ResourceLocation(MalumMod.MODID, "textures/other/kittys_tail.png");
    public ModelLapisTail<LivingEntity> lapis_tail;

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
    {
        if (livingEntity instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            if (playerEntity.getUniqueID().equals(UUID.fromString(sammyUUID)))
            {
                matrixStack.push();
                if (lapis_tail == null)
                {
                    lapis_tail = new ModelLapisTail<>();
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
                lapis_tail.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                lapis_tail.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);

                ICurio.RenderHelper.followBodyRotations(livingEntity, lapis_tail);
                ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
                ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, lapis_tail.getRenderType(lapis_tail_texture), false, false);
                lapis_tail.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                matrixStack.pop();
            }
        }
    }
}