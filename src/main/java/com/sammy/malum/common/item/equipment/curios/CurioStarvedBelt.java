package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.potion.EffectRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.setup.OrtusAttributeRegistry;
import com.sammy.ortus.systems.item.IEventResponderItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioStarvedBelt extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioStarvedBelt(Properties builder) {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR, new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Curio armor boost", 2, AttributeModifier.Operation.ADDITION));
        map.put(OrtusAttributeRegistry.MAGIC_RESISTANCE.get(), new AttributeModifier(uuids.computeIfAbsent(1, (i) -> UUID.randomUUID()), "Curio magic resistance", 2f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, boolean isNatural) {
        MobEffect starvation = EffectRegistry.STARVATION.get();
        MobEffectInstance effect = collector.getEffect(starvation);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(starvation, 100, 0, false, false, false));
        } else {
            EntityHelper.amplifyEffect(effect, collector, 1, 4);
        }
    }

    @Override
    public boolean isOrnate() {
        return true;
    }
}