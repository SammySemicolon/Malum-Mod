package com.sammy.malum.items.equipment.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.items.staves.BasicStave;
import com.sammy.malum.models.ModelEtherealMagic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class CurioEtherealBulwark extends Item implements ICurio
{
    public CurioEtherealBulwark(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            private final ResourceLocation ethereal_texture = new ResourceLocation(MalumMod.MODID, "textures/other/ethereal_magic.png");
            private Object ethereal_magic_model;
            
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
            public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
            {
                if (livingEntity instanceof PlayerEntity)
                {
                    PlayerEntity playerEntity = (PlayerEntity) livingEntity;
                    ItemStack heldItem = playerEntity.getActiveItemStack();
                    if (heldItem.getItem() instanceof BasicStave)
                    {
                        float scale = Math.min(heldItem.getUseDuration() - playerEntity.getItemInUseCount(), 20) / 20f;
                        float rotation = playerEntity.world.getGameTime() * 3f;
                        
                        if (!(ethereal_magic_model instanceof ModelEtherealMagic))
                        {
                            ethereal_magic_model = new ModelEtherealMagic();
                        }
                        ModelEtherealMagic magic_model = (ModelEtherealMagic) ethereal_magic_model;
                        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, magic_model.getRenderType(ethereal_texture), false, false);
                        
                        RenderHelper.translateIfSneaking(matrixStack, livingEntity);
                        Minecraft.getInstance().getTextureManager().bindTexture(ethereal_texture);
                        GlStateManager.enableCull();
                        GlStateManager.enableBlend();
                        
                        matrixStack.rotate(Vector3f.YP.rotationDegrees(rotation));
                        matrixStack.scale(scale, scale, scale);
                        magic_model.render(matrixStack, vertexBuilder, light, NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1f);
                        GlStateManager.disableCull();
                        GlStateManager.disableBlend();
                    }
                }
            }
        });
    }
    
}