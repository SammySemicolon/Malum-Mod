package com.sammy.malum.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MalumMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents
{
    protected static final ResourceLocation ICONS_TEXTURE = MalumHelper.prefix("textures/gui/locked_hearts.png");
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void renderChill(RenderGameOverlayEvent.Post event)
    {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH)
        {
            if (player.isPotionActive(MalumEffects.FOOLS_LUCK.get()))
            {
                MatrixStack matrixStack = event.getMatrixStack();
                matrixStack.push();
    
                RenderSystem.pushTextureAttributes();
                mc.getTextureManager().bindTexture(ICONS_TEXTURE);
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
            }
        }
    }
}