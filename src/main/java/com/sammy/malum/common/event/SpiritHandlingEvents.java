package com.sammy.malum.common.event;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.tools.spirittools.ISpiritTool;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpiritHandlingEvents
{
    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event)
    {
        if (event.getSource().getTrueSource() instanceof LivingEntity)
        {
            ItemStack stack = ItemStack.EMPTY;
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            if (event.getSource().getImmediateSource() instanceof ScytheBoomerangEntity)
            {
                ScytheBoomerangEntity entity = (ScytheBoomerangEntity) event.getSource().getImmediateSource();
                stack = entity.scythe;
            }
            else
            {
                stack = MalumHelper.heldItem(attacker, s -> s.getItem() instanceof ISpiritTool);
            }
            if (stack != null)
            {
                if (stack.getItem() instanceof ISpiritTool && event.getSource().isProjectile())
                {
                    return;
                }
                SpiritHelper.summonSpirits(event.getEntityLiving(), attacker, stack);
            }
        }
    }
}
