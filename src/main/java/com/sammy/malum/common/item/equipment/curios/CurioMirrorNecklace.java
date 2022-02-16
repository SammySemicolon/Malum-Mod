package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

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
            ItemHelper.getEventResponders(attacker).forEach(s -> {
                if (s.getItem() instanceof IEventResponderItem eventItem)
                {
                    eventItem.pickupSpirit(attacker, stack, true);
                }
            });
        }
    }
}