package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.setup.client.ScreenParticleRegistry;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.easing.Easing;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.systems.rendering.particle.ParticleBuilders;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.UUID;

public class CurioMagebaneBelt extends MalumCurioItem implements IEventResponderItem, ItemParticleEmitter {

    public CurioMagebaneBelt(Properties builder) {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(AttributeRegistry.MAGIC_RESISTANCE.get(), new AttributeModifier(UUID.randomUUID(), "Curio magic resistance", 2f, AttributeModifier.Operation.ADDITION));
        map.put(AttributeRegistry.SOUL_WARD_CAP.get(), new AttributeModifier(UUID.randomUUID(), "Soul Ward Cap", 3f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isOrnate() {
        return true;
    }


    @OnlyIn(value = Dist.CLIENT)
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        Level level = Minecraft.getInstance().level;
        float gameTime = level.getGameTime() + Minecraft.getInstance().timer.partialTick;
        Color firstColor = SpiritTypeRegistry.ELDRITCH_SPIRIT_COLOR;
        Color secondColor = ColorHelper.darker(SpiritTypeRegistry.ELDRITCH_SPIRIT_COLOR, 2);
        ParticleBuilders.create(ScreenParticleRegistry.STAR)
                .setAlpha(0.045f, 0f)
                .setLifetime(8)
                .setScale((float) (0.85f + Math.sin(gameTime * 0.05f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, -2, 2)
                .repeat(x, y, 1)
                .setScale((float) (0.85f - Math.sin(gameTime * 0.075f) * 0.15f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1)
                .setScale((float) (0.95f - Math.sin(gameTime * 0.1f) * 0.175f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.8f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
    }
}