package com.sammy.malum.common.items.equipment.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.models.ModelAncestralVeil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CurioAncestralVeil extends Item implements ICurio
{
    private static final ResourceLocation CROWN_TEXTURE = MalumHelper.prefix("textures/armor/ancestral_veil.png");
    
    public CurioAncestralVeil(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            private Object model;
    
            @Override
            public void playRightClickEquipSound(LivingEntity livingEntity)
            {
                livingEntity.world.playSound(null, livingEntity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }
    
            @Override
            public boolean canRender(String identifier, int index, LivingEntity livingEntity)
            {
                return true;
            }
    
            @Override
            public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
            {
    
                if (!(this.model instanceof ModelAncestralVeil))
                {
                    model = new ModelAncestralVeil<>();
                }
                ModelAncestralVeil<?> veil = (ModelAncestralVeil<?>) this.model;
                ICurio.RenderHelper.followHeadRotations(livingEntity, veil.veil);
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, veil.getRenderType(CROWN_TEXTURE), false, stack.hasEffect());
                veil.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
    
    
            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }
        });
    }
}