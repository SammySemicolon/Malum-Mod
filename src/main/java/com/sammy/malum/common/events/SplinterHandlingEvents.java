package com.sammy.malum.common.events;

import com.sammy.malum.common.entities.ScytheBoomerangEntity;
import com.sammy.malum.common.items.tools.scythes.ScytheItem;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SplinterHandlingEvents
{
    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            ItemStack stack = ItemStack.EMPTY;
            PlayerEntity attacker = (PlayerEntity) event.getSource().getTrueSource();
            if (attacker.swingingHand != null)
            {
                stack = attacker.getHeldItem(attacker.swingingHand);
            }
            if (attacker.isHandActive() && stack.isEmpty())
            {
                stack = attacker.getActiveItemStack();
            }
            if (event.getSource().getImmediateSource() instanceof ScytheBoomerangEntity)
            {
                ScytheBoomerangEntity entity = (ScytheBoomerangEntity) event.getSource().getImmediateSource();
                stack = entity.scythe;
            }
            Item item = stack.getItem();
            if (item instanceof ScytheItem)
            {
                SpiritHelper.summonSpirits(event.getEntityLiving(), attacker, stack);
            }
        }
    }
}
