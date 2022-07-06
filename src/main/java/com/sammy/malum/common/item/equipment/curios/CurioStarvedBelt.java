package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.ortus.helpers.EntityHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
            collector.addEffect(new MobEffectInstance(gluttony, 300, 0, true, true, true));
        } else {
            EntityHelper.extendEffect(effect, collector, 100, 300);
            if (collector instanceof Player player) {
                player.getFoodData().addExhaustion(2f);
            }
            EntityHelper.amplifyEffect(effect, collector, 1, 4);
        }
    }

    @Override
    public boolean isOrnate() {
        return true;
    }
}