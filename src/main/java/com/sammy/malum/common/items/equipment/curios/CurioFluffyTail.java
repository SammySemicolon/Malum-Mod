package com.sammy.malum.common.items.equipment.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.models.ModelKittysTail;
import com.sammy.malum.core.systems.curios.CurioProvider;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class CurioFluffyTail extends Item implements ICurio
{
    public CurioFluffyTail(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            //cute furry, kitty
            public final ResourceLocation kittys_tail_texture = new ResourceLocation(MalumMod.MODID, "textures/other/kittys_tail.png");public ModelKittysTail<LivingEntity> kittys_tail;

            @Override
            public void playRightClickEquipSound(LivingEntity livingEntity)
            {
                livingEntity.world.playSound(null, livingEntity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }

            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }

            @Override
            public boolean canRender(String identifier, int index, LivingEntity livingEntity)
            {
                return true;
            }
            
            @Override
            public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
            {
                if (livingEntity instanceof PlayerEntity)
                {
                    PlayerEntity playerEntity = (PlayerEntity) livingEntity;
                    matrixStack.push();
                    if (kittys_tail == null)
                    {
                        kittys_tail = new ModelKittysTail<>();
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
                    kittys_tail.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    kittys_tail.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);
    
                    RenderHelper.followBodyRotations(livingEntity, kittys_tail);
                    RenderHelper.translateIfSneaking(matrixStack, livingEntity);
                    RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
                    IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, kittys_tail.getRenderType(kittys_tail_texture), false, false);
                    kittys_tail.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                    matrixStack.pop();
                }
            }
        });
    }
}