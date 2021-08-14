package com.sammy.malum.common.event;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.tools.spirittools.ISpiritTool;
import com.sammy.malum.common.item.tools.spirittools.ScytheItem;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemEvents
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
                SpiritHelper.createSpiritEntities(event.getEntityLiving(), attacker, stack);
            }
        }
    }

    @SubscribeEvent
    public static void increaseScytheDamage(LivingHurtEvent event)
    {
        if (event.getSource().getImmediateSource() instanceof LivingEntity)
        {
            LivingEntity attacker = (LivingEntity) event.getSource().getImmediateSource();
            ItemStack heldItem = MalumHelper.heldItem(attacker, (s)-> s.getItem() instanceof ScytheItem);
            if (heldItem != null)
            {
                float extraDamage = 1.0f;
                if (MalumHelper.hasArmorSet(attacker, MalumItems.SOUL_HUNTER_CLOAK.get()))
                {
                    extraDamage += 0.25f;
                }
                event.setAmount(event.getAmount() * extraDamage);
            }
        }
    }

    @SubscribeEvent
    public static void increaseMagicDamage(LivingHurtEvent event)
    {
        if (event.getSource().getImmediateSource() instanceof LivingEntity)
        {
            LivingEntity attacker = (LivingEntity) event.getSource().getImmediateSource();
            if (event.getSource().isMagicDamage())
            {
                float extraDamage = 1.0f;
                if (MalumHelper.hasArmorSet(attacker, MalumItems.SOUL_HUNTER_CLOAK.get()))
                {
                    extraDamage += 0.5f;
                }
                event.setAmount(event.getAmount() * extraDamage);
            }
        }
    }
}
