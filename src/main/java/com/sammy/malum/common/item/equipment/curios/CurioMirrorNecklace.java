package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;

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
        if (event.getSource().isMagic())
        {
            Item item = stack.getItem();
            if (item instanceof IEventResponderItem eventItem) {
                eventItem.pickupSpirit(attacker, stack, false);
            }
            attacker.getArmorSlots().forEach(s ->{
                if (s.getItem() instanceof IEventResponderItem eventItem)
                {
                    eventItem.pickupSpirit(attacker, stack, false);
                }
            });
            ArrayList<ItemStack> curios = ItemHelper.equippedCurios(attacker, p -> p.getItem() instanceof IEventResponderItem);
            curios.forEach(s -> ((IEventResponderItem)s.getItem()).pickupSpirit(attacker, s, false));
        }
    }
}