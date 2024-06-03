package com.sammy.malum.common.item.curiosities.trinkets.sets.alchemical;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;

import java.util.function.Consumer;

public class TrinketsAlchemicalRing extends MalumTinketsItem implements IMalumEventResponderItem {

    public TrinketsAlchemicalRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_extend_effect"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        addAttributeModifier(map, LodestoneAttributeRegistry.MAGIC_RESISTANCE.get(), uuid -> new AttributeModifier(uuid,
                "Curio Magic Resistance", 1f, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        collector.getActiveEffectsMap().forEach((e, i) -> {
            float multiplier = MobEffectRegistry.ALCHEMICAL_PROFICIENCY_MAP.getOrDefault(BuiltInRegistries.MOB_EFFECT.getKey(e), 1f);
            if (e.isBeneficial()) {
                int base = 40 + (int) (arcaneResonance * 20);
                EntityHelper.extendEffect(i, collector, (int) (base * multiplier), 1200);
            } else if (e.getCategory().equals(MobEffectCategory.HARMFUL)) {
                int base = 60 + (int) (arcaneResonance * 30);
                EntityHelper.shortenEffect(i, collector, (int) (base * multiplier));
            }
        });
    }
}
