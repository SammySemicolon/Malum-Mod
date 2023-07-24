package com.sammy.malum.common.item.curiosities.curios.alchemical;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.PotionEvent;
import team.lodestar.lodestone.helpers.CurioHelper;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.setup.LodestoneAttributeRegistry;
import top.theillusivec4.curios.api.*;

import java.util.UUID;

public class CurioAlchemicalRing extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioAlchemicalRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, LodestoneAttributeRegistry.MAGIC_RESISTANCE.get(), uuid -> new AttributeModifier(uuid,
                "Curio Magic Resistance", 1f, AttributeModifier.Operation.ADDITION));
    }

    public static void onPotionApplied(PotionEvent.PotionAddedEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (event.getOldPotionEffect() == null && CurioHelper.hasCurioEquipped(entity, ItemRegistry.RING_OF_ALCHEMICAL_MASTERY.get())) {
            MobEffectInstance effect = event.getPotionEffect();
            MobEffect type = effect.getEffect();
            float multiplier = MobEffectRegistry.ALCHEMICAL_PROFICIENCY_MAP.getOrDefault(type.getRegistryName(), 1f);
            if (type.isBeneficial()) {
                EntityHelper.extendEffect(effect, entity, (int) (effect.getDuration()*0.25f*multiplier));
            }
            else if (type.getCategory().equals(MobEffectCategory.HARMFUL)) {
                EntityHelper.shortenEffect(effect, entity, (int) (effect.getDuration()*0.33f*multiplier));
            }
        }
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, double arcaneResonance) {
        collector.getActiveEffectsMap().forEach((e, i) -> {
            float multiplier = MobEffectRegistry.ALCHEMICAL_PROFICIENCY_MAP.getOrDefault(e.getRegistryName(), 1f);
            if (e.isBeneficial()) {
                int base = 40 +(int)(arcaneResonance*20);
                EntityHelper.extendEffect(i, collector, (int) (base*multiplier), 1200);
            }
            else if (e.getCategory().equals(MobEffectCategory.HARMFUL)) {
                int base = 60 +(int)(arcaneResonance*30);
                EntityHelper.shortenEffect(i, collector, (int) (base*multiplier));
            }
        });
    }
}