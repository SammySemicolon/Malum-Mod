package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioWardedBelt extends MalumCurioItem
{
    private static final UUID ARMOR = UUID.fromString("532a0e67-0456-49fa-b6c9-ad392f196948");
    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("7c2fda2b-fb8f-4759-a1de-1c4f2fa286ab");
    private static final UUID KNOCKBACK_RESISTANCE = UUID.fromString("cb9948b7-f866-4bba-9148-161996ab83e7");
    private static final UUID MAGIC_RESISTANCE = UUID.fromString("59b826ad-56cf-42fb-b46b-cba50455aeda");

    public CurioWardedBelt(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR, new AttributeModifier(ARMOR, "Curio armor boost", 2, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS, "Curio armor toughness boost", 1, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(KNOCKBACK_RESISTANCE, "Curio knockback resistance", 0.2f, AttributeModifier.Operation.ADDITION));
        map.put(AttributeRegistry.MAGIC_RESISTANCE, new AttributeModifier(MAGIC_RESISTANCE, "Curio magic resistance", 1f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isGilded()
    {
        return true;
    }
}