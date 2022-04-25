package com.sammy.malum.common.item.ether;

import com.sammy.ortus.setup.OrtusScreenParticles;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class EtherItem extends AbstractEtherItem {
    public EtherItem(Block blockIn, Properties builder, boolean iridescent) {
        super(blockIn, builder, iridescent);
    }


    @OnlyIn(value = Dist.CLIENT)
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        Level level = Minecraft.getInstance().level;
        float gameTime = level.getGameTime() + Minecraft.getInstance().timer.partialTick;
        AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
        Color firstColor = new Color(etherItem.getFirstColor(stack));
        Color secondColor = new Color(etherItem.getSecondColor(stack));
        float alphaMultiplier = etherItem.iridescent ? 1.5f : 1;
        ParticleBuilders.create(OrtusScreenParticles.STAR)
                .setAlpha(0.09f*alphaMultiplier, 0f)
                .setLifetime(6)
                .setScale((float) (1.5f + Math.sin(gameTime * 0.1f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, etherItem.iridescent ? -1 : 0, 3)
                .repeat(x, y, 1)
                .setScale((float) (1.4f - Math.sin(gameTime * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f-0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
    }
}