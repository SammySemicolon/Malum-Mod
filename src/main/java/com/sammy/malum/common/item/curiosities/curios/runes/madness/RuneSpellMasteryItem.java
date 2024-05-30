package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.curios.runes.AbstractRuneCurioItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;


public class RuneSpellMasteryItem extends AbstractRuneCurioItem {

    public RuneSpellMasteryItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack) {
        addAttributeModifier(map, LodestoneAttributeRegistry.MAGIC_PROFICIENCY.get(), uuid -> new AttributeModifier(uuid,
                "Curio Magic Proficiency", 2f, AttributeModifier.Operation.ADDITION));
        addAttributeModifier(map, AttributeRegistry.RESERVE_STAFF_CHARGES.get(), uuid -> new AttributeModifier(uuid,
                "Curio Reserve Staff Charges", 2f, AttributeModifier.Operation.ADDITION));
    }
}