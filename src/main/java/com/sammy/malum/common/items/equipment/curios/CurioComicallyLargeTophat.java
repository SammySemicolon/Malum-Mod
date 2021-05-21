package com.sammy.malum.common.items.equipment.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.models.ModelComicallyLargeTophat;
import com.sammy.malum.core.init.MalumSounds;
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
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CurioComicallyLargeTophat extends MalumCurioItem
{
    public CurioComicallyLargeTophat(Properties builder)
    {
        super(builder);
    }

    @Override
    public void playRightClickEquipSound(LivingEntity livingEntity, ItemStack stack)
    {
        livingEntity.world.playSound(null, livingEntity.getPosition(), MalumSounds.SINISTER_EQUIP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        livingEntity.world.playSound(null, livingEntity.getPosition(), MalumSounds.HOLY_EQUIP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        return true;
    }

    public final ResourceLocation hat_texture = MalumHelper.prefix("textures/other/tophat.png");
    public ModelComicallyLargeTophat<LivingEntity> hat;
    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
    {
        if (livingEntity instanceof PlayerEntity)
        {
            matrixStack.push();
            if (hat == null)
            {
                hat = new ModelComicallyLargeTophat<>();
            }
            ICurio.RenderHelper.followHeadRotations(livingEntity, hat.tophat);
            IVertexBuilder jtBuilder = ItemRenderer.getBuffer(renderTypeBuffer, hat.getRenderType(hat_texture), false, stack.hasEffect());
            hat.render(matrixStack, jtBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }
}