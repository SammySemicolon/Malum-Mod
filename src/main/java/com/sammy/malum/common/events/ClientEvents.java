package com.sammy.malum.common.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.systems.spirits.item.ISpiritUsingItem;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Random;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents
{
    protected static final ResourceLocation ICONS_TEXTURE = MalumHelper.prefix("textures/gui/overlay.png");
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void renderHearts(RenderGameOverlayEvent.Post event)
    {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH)
        {
            ModifiableAttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            float healthMax = (float) attrMaxHealth.getValue();
    
            int left = event.getWindow().getScaledWidth() / 2 - 91;
            int top = event.getWindow().getScaledHeight() - ForgeIngameGui.left_height;
    
            int healthRows = MathHelper.ceil(healthMax / 2.0F / 10.0F);
            int rowHeight = Math.max(10 - (healthRows - 2), 3);
    
            int health = MathHelper.ceil(player.getHealth());
    
            int ticks = mc.ingameGUI.getTicks();
    
            int regen = -1;
            if (player.isPotionActive(Effects.REGENERATION))
            {
                regen = ticks % 25;
            }
            Random rand = new Random();
            rand.setSeed((long)(ticks * 312871));
            
            if (player.isPotionActive(MalumEffects.FOOLS_LUCK.get()))
            {
                MatrixStack matrixStack = event.getMatrixStack();
                matrixStack.push();
                
                RenderSystem.pushTextureAttributes();
                mc.getTextureManager().bindTexture(ICONS_TEXTURE);
                
                for (int i = MathHelper.ceil((healthMax) / 2.0F) - 1; i >= 0; --i)
                {
                    int x = left + i % 10 * 8;
                    int y = top + rowHeight - 1;
                    if (health <= 4)
                    {
                        y += rand.nextInt(2);
                    }
                    if (i == regen) {
                        y -= 2;
                    }
                    if (i * 2 + 1 < health)
                    {
                        mc.ingameGUI.blit(matrixStack, x, y, 0, 0, 9, 9);
                    }
                    else if (i * 2 + 1 == health)
                    {
                        mc.ingameGUI.blit(matrixStack, x, y, 9, 0, 9, 9);
                    }
                }
                RenderSystem.popAttributes();
                matrixStack.pop();
                return;
            }
            if (player.isPotionActive(MalumEffects.BLEEDING.get()))
            {
                MatrixStack matrixStack = event.getMatrixStack();
                matrixStack.push();
        
                RenderSystem.pushTextureAttributes();
                mc.getTextureManager().bindTexture(ICONS_TEXTURE);
        
                for (int i = MathHelper.ceil((healthMax) / 2.0F) - 1; i >= 0; --i)
                {
                    int x = left + i % 10 * 8;
                    int y = top + rowHeight - 1;
                    if (health <= 4)
                    {
                        y += rand.nextInt(2);
                    }
                    if (i == regen) {
                        y -= 2;
                    }
                    if (i * 2 + 1 < health)
                    {
                        mc.ingameGUI.blit(matrixStack, x, y, 0, 9, 9, 9);
                    }
                    else if (i * 2 + 1 == health)
                    {
                        mc.ingameGUI.blit(matrixStack, x, y, 9, 9, 9, 9);
                    }
                }
                RenderSystem.popAttributes();
                matrixStack.pop();
            }
        }
    }
}
