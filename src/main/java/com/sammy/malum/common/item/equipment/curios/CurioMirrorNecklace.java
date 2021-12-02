package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.UUID;

public class CurioMirrorNecklace extends MalumCurioItem implements IEventResponderItem
{
    public CurioMirrorNecklace(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean isOrnate()
    {
        return true;
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().isMagicDamage())
        {

        }
    }
}