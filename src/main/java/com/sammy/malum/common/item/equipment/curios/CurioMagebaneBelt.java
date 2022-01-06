package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.registry.AttributeRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioMagebaneBelt extends MalumCurioItem implements IEventResponderItem
{
    private static final UUID MAGIC_RESISTANCE = UUID.fromString("8ad20be5-cc3b-4786-bf53-1dbd969e6477");
    private static final UUID SOUL_WARD_CAP = UUID.fromString("8ad20be5-cc3b-4786-bf53-1dbd969e6477");

    public CurioMagebaneBelt(Properties builder)
    {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(AttributeRegistry.MAGIC_RESISTANCE, new AttributeModifier(MAGIC_RESISTANCE, "Curio magic resistance", 2f, AttributeModifier.Operation.ADDITION));
        map.put(AttributeRegistry.SOUL_WARD_CAP, new AttributeModifier(SOUL_WARD_CAP, "Soul Ward Cap", 3f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isOrnate()
    {
        return true;
    }
}