package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.ortus.helpers.EntityHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.UUID;

import static com.sammy.malum.core.setup.content.item.ItemTagRegistry.GROSS_FOODS;

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
    public void pickupSpirit(LivingEntity collector, ItemStack stack, boolean isNatural) {
        MobEffect gluttony = MalumMobEffectRegistry.GLUTTONY.get();
        MobEffectInstance effect = collector.getEffect(gluttony);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(gluttony, 100, 0, true, true, true));
        } else {
            EntityHelper.extendEffect(effect, collector, 50, 200);
            EntityHelper.amplifyEffect(effect, collector, 1, 4);
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