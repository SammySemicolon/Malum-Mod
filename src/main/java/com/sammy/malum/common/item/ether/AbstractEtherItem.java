package com.sammy.malum.common.item.ether;

import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.setup.client.ScreenParticleRegistry;
import com.sammy.malum.core.systems.easing.Easing;
import com.sammy.malum.core.systems.rendering.particle.ParticleBuilders;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import com.sammy.malum.core.systems.rendering.particle.screen.emitter.ParticleEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.awt.*;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;

public abstract class AbstractEtherItem extends BlockItem implements DyeableLeatherItem, ItemParticleEmitter {
    public static final String firstColor = "firstColor";
    public static final String secondColor = "secondColor";
    public static final int defaultFirstColor = 15712278;
    public static final int defaultSecondColor = 4607909;

    public final boolean iridescent;

    public AbstractEtherItem(Block blockIn, Properties builder, boolean iridescent) {
        super(blockIn, builder);
        this.iridescent = iridescent;
    }

    public String colorLookup() {
        return iridescent ? secondColor : firstColor;
    }

    public int getSecondColor(ItemStack stack) {
        if (!iridescent) {
            return getFirstColor(stack);
        }
        CompoundTag tag = stack.getTagElement("display");

        return tag != null && tag.contains(secondColor, 99) ? tag.getInt(secondColor) : defaultSecondColor;
    }

    public void setSecondColor(ItemStack stack, int color) {
        stack.getOrCreateTagElement("display").putInt(secondColor, color);
    }

    public int getFirstColor(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("display");
        return tag != null && tag.contains(firstColor, 99) ? tag.getInt(firstColor) : defaultFirstColor;
    }

    public void setFirstColor(ItemStack stack, int color) {
        stack.getOrCreateTagElement("display").putInt(firstColor, color);
    }

    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder, ParticleEmitter emitter) {
        Level level = Minecraft.getInstance().level;
        float gameTime = level.getGameTime()+Minecraft.getInstance().timer.partialTick;
        AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
        Color firstColor = ColorHelper.getColor(etherItem.getFirstColor(stack));
        Color secondColor = ColorHelper.getColor(etherItem.getSecondColor(stack));
        Random rand = Minecraft.getInstance().level.getRandom();
        ParticleBuilders.create(ScreenParticleRegistry.STAR)
                .setAlpha(0.07f, 0f)
                .setLifetime(8)
                .setScale((float) (0.8f+Math.sin(gameTime*0.05f)*0.1f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset((0.075f * gameTime % 6.28f))
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .setStack(stack)
                .repeat(x, y, 1)
                .setScale((float) (0.8f-Math.sin(gameTime*0.05f)*0.1f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(-(0.01f * gameTime % 6.28f))

                .repeat(x, y, 1);

        ParticleBuilders.create(ScreenParticleRegistry.SPARKLE)
                .setAlpha(0.04f, 0f)
                .setLifetime(16 + rand.nextInt(4))
                .setSpin(nextFloat(rand, 0.2f, 0.4f))
                .setScale(0.4f, 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.1f)
                .randomMotion(0.4f, 0.4f)
                .overwriteRenderOrder(renderOrder)
                .setStack(stack)
                .repeat(x, y, 1);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("display");
        return tag != null && tag.contains(colorLookup(), 99) ? tag.getInt(colorLookup()) : defaultFirstColor;
    }

    @Override
    public boolean hasCustomColor(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("display");
        return tag != null && tag.contains(colorLookup(), 99);
    }

    @Override
    public void clearColor(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("display");
        if (tag != null && tag.contains(colorLookup())) {
            tag.remove(colorLookup());
        }
    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateTagElement("display").putInt(colorLookup(), color);
    }
}