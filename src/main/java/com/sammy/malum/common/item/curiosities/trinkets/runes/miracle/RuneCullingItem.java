package com.sammy.malum.common.item.curiosities.trinkets.runes.miracle;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.trinkets.runes.AbstractRuneTrinketsItem;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;

public class RuneCullingItem extends AbstractRuneTrinketsItem {

    public RuneCullingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.WICKED_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        addAttributeModifier(map, LodestoneAttributeRegistry.MAGIC_PROFICIENCY.get(), uuid -> new AttributeModifier(uuid,
                "Curio Magic Proficiency", 4f, AttributeModifier.Operation.ADDITION));
    }
}