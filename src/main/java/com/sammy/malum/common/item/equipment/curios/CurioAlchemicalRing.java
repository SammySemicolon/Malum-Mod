package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import team.lodestar.lodestone.helpers.CurioHelper;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.setup.LodestoneAttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.PotionEvent;

import java.util.UUID;

public class CurioAlchemicalRing extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioAlchemicalRing(Properties builder) {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(LodestoneAttributeRegistry.MAGIC_RESISTANCE.get(), new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Curio magic resistance", 1, AttributeModifier.Operation.ADDITION));
        return map;
    }

    public static void onPotionApplied(PotionEvent.PotionAddedEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (event.getOldPotionEffect() == null && CurioHelper.hasCurioEquipped(entity, ItemRegistry.RING_OF_ALCHEMICAL_MASTERY)) {
            MobEffectInstance effect = event.getPotionEffect();
            MobEffect type = effect.getEffect();
            float multiplier = MalumMobEffectRegistry.ALCHEMICAL_PROFICIENCY_MAP.getOrDefault(type.getRegistryName(), 1f);
            if (type.isBeneficial()) {
                EntityHelper.extendEffect(effect, entity, (int) (effect.getDuration()*0.25f*multiplier));
            }
            else if (type.getCategory().equals(MobEffectCategory.HARMFUL)) {
                EntityHelper.shortenEffect(effect, entity, (int) (effect.getDuration()*0.33f*multiplier));
            }
        }
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, boolean isNatural) {
        collector.getActiveEffectsMap().forEach((e, i) -> {
            float multiplier = MalumMobEffectRegistry.ALCHEMICAL_PROFICIENCY_MAP.getOrDefault(e.getRegistryName(), 1f);
            if (e.isBeneficial()) {
                EntityHelper.extendEffect(i, collector, (int) (40*multiplier), 1200);
            }
            else if (e.getCategory().equals(MobEffectCategory.HARMFUL)) {
                EntityHelper.shortenEffect(i, collector, (int) (60*multiplier));
            }
        });
    }

    @Override
    public boolean isGilded() {
        return true;
    }
}