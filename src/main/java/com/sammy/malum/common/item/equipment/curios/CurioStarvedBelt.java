package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.EntityHelper;

import java.util.UUID;

public class CurioStarvedBelt extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioStarvedBelt(Properties builder) {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR, new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Curio armor boost", 2, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, double arcaneResonance) {
        MobEffect gluttony = MalumMobEffectRegistry.GLUTTONY.get();
        MobEffectInstance effect = collector.getEffect(gluttony);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(gluttony, 100+(int)(arcaneResonance*25), 0, true, true, true));
        } else {
            EntityHelper.extendEffect(effect, collector, 50, 200+(int)(arcaneResonance*50));
            EntityHelper.amplifyEffect(effect, collector, 1, 9+(int)(arcaneResonance*5));
        }
        Level level = collector.level;
        level.playSound(null, collector.blockPosition(), SoundRegistry.HUNGRY_BELT_FEEDS.get(), SoundSource.PLAYERS, 0.7f, 1.5f + level.random.nextFloat() * 0.5f);
        level.playSound(null, collector.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.7f, 0.8f + level.random.nextFloat() * 0.4f);

    }

    @Override
    public boolean isOrnate() {
        return true;
    }
}