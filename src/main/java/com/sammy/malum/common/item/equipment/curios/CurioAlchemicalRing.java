package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.potion.EffectRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.ortus.helpers.CurioHelper;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.setup.OrtusAttributeRegistry;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.PotionEvent;

import java.util.Collection;
import java.util.UUID;

public class CurioAlchemicalRing extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioAlchemicalRing(Properties builder) {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(OrtusAttributeRegistry.MAGIC_RESISTANCE.get(), new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Curio magic resistance", 1, AttributeModifier.Operation.ADDITION));
        return map;
    }

    public static void onPotionApplied(PotionEvent.PotionAddedEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (event.getOldPotionEffect() == null && CurioHelper.hasCurioEquipped(entity, ItemRegistry.RING_OF_ALCHEMICAL_MASTERY)) {
            MobEffectInstance effect = event.getPotionEffect();
            MobEffect type = effect.getEffect();
            if (type.isBeneficial()) {
                EntityHelper.extendEffect(effect, entity, (int) (effect.getDuration()/2f));
            }
            else if (type.getCategory().equals(MobEffectCategory.HARMFUL)) {
                EntityHelper.shortenEffect(effect, entity, (int) (effect.getDuration()/2f));
            }
        }
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, boolean isNatural) {
        collector.getActiveEffectsMap().forEach((e, i) -> {
            if (e.isBeneficial()) {
                EntityHelper.extendEffect(i, collector, 40, 1200);
            }
            else if (e.getCategory().equals(MobEffectCategory.HARMFUL)) {
                EntityHelper.shortenEffect(i, collector, 60);
            }
        });
    }

    @Override
    public boolean isGilded() {
        return true;
    }
}