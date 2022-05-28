package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.setup.OrtusAttributeRegistry;
import com.sammy.ortus.setup.OrtusScreenParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.ortus.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.UUID;

public class CurioMagebaneBelt extends MalumCurioItem implements IMalumEventResponderItem, ItemParticleEmitter {

    public CurioMagebaneBelt(Properties builder) {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(OrtusAttributeRegistry.MAGIC_RESISTANCE.get(), new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Curio magic resistance", 2f, AttributeModifier.Operation.ADDITION));
        map.put(AttributeRegistry.SOUL_WARD_CAP.get(), new AttributeModifier(uuids.computeIfAbsent(1, (i) -> UUID.randomUUID()), "Soul Ward Cap", 3f, AttributeModifier.Operation.ADDITION));
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
        Color firstColor = SpiritTypeRegistry.ELDRITCH_SPIRIT.getColor();
        Color secondColor = ColorHelper.darker(firstColor, 2);
        ParticleBuilders.create(OrtusScreenParticleRegistry.STAR)
                .setAlpha(0.06f, 0f)
                .setLifetime(8)
                .setScale((float) (0.85f + Math.sin(gameTime * 0.05f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCoefficient(1.25f)
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