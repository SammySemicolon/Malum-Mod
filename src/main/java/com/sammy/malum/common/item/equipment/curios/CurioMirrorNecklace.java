package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;
import java.util.UUID;

public class CurioMirrorNecklace extends MalumCurioItem implements IEventResponderItem
{
    public CurioMirrorNecklace(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean isGilded()
    {
        return true;
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().isMagicDamage())
        {

            Item item = stack.getItem();
            if (item instanceof IEventResponderItem) {
                IEventResponderItem eventItem = (IEventResponderItem) item;
                eventItem.pickupSpirit(attacker, stack);
            }
            attacker.getArmorInventoryList().forEach(s ->{
                if (s.getItem() instanceof IEventResponderItem)
                {
                    IEventResponderItem eventItem = (IEventResponderItem) s.getItem();
                    eventItem.pickupSpirit(attacker, stack);
                }
            });
            ArrayList<ItemStack> curios = MalumHelper.equippedCurios(attacker, p -> p.getItem() instanceof IEventResponderItem);
            curios.forEach(s -> ((IEventResponderItem)s.getItem()).pickupSpirit(attacker, s));
        }
    }
}