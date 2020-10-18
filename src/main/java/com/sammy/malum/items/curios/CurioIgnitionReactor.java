
package com.sammy.malum.items.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.SpiritConsumer;
import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.SpiritDescription;
import com.sammy.malum.models.ModelKittysTail;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.makeGenericSpiritDependantTooltip;

public class CurioIgnitionReactor extends Item implements ICurio, SpiritConsumer, SpiritDescription
{
    public CurioIgnitionReactor(Properties builder)
    {
        super(builder);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
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
    
            public final ResourceLocation kittys_tail_texture =
                    new ResourceLocation(MalumMod.MODID, "textures/other/kittys_tail.png");
            public ModelKittysTail<LivingEntity> kittys_tail;
    
            @Override
            public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
            {
                if (kittys_tail == null)
                {
                    kittys_tail = new ModelKittysTail<>();
                }
                kittys_tail.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                kittys_tail.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);
                RenderHelper.followBodyRotations(livingEntity, kittys_tail);
                RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
                RenderHelper.translateIfSneaking(matrixStack, livingEntity);
    

                { //tail rotates based on movement
                    Vector3d finalA = new Vector3d(livingEntity.getMotion().x,0,livingEntity.getMotion().z);
                    Vector3d look = new Vector3d(livingEntity.getLookVec().x,0,livingEntity.getLookVec().z);
                    Vector3d desiredDirection = look.rotateYaw(90).normalize();
                    Vector3d sidewaysVelocity = desiredDirection.scale(finalA.dotProduct(desiredDirection));
                    double magnitude = sidewaysVelocity.normalize().length();
                    double rotation = magnitude * 55;
                    matrixStack.rotate(Vector3f.YP.rotationDegrees((float) rotation));
                }
                {//tail rotates based on sineWave
                    double xRotation = Math.sin(livingEntity.world.getGameTime() / 18f) * 6;
                    double yRotation = -Math.sin(livingEntity.world.getGameTime() / 36f) * 12;
    
                    matrixStack.rotate(Vector3f.XP.rotationDegrees((float) xRotation));
                    matrixStack.rotate(Vector3f.YP.rotationDegrees((float) yRotation));
                }
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, kittys_tail.getRenderType(kittys_tail_texture), false, false);
                kittys_tail.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
        });
    }
    
    @Override
    public int durability()
    {
        return 50;
    }
    
    @Override
    public String spirit()
    {
        return "minecraft:blaze";
    }
    
    @Override
    public ArrayList<ITextComponent> components()
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(makeGenericSpiritDependantTooltip("malum.tooltip.ignition_reactor.effect", SpiritDataHelper.getName(spirit())));
        return components;
    }
}