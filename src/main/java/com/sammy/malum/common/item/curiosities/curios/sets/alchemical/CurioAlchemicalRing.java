package com.sammy.malum.common.item.curiosities.curios.sets.alchemical;

import com.google.common.collect.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;
import top.theillusivec4.curios.api.*;

import java.util.function.*;

public class CurioAlchemicalRing extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioAlchemicalRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.curio.effect.ring_of_alchemical_mastery"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, LodestoneAttributeRegistry.MAGIC_RESISTANCE.get(), uuid -> new AttributeModifier(uuid,
                "Curio Magic Resistance", 1f, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, double arcaneResonance) {
        collector.getActiveEffectsMap().forEach((e, i) -> {
            float multiplier = MobEffectRegistry.ALCHEMICAL_PROFICIENCY_MAP.getOrDefault(ForgeRegistries.MOB_EFFECTS.getKey(e), 1f);
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