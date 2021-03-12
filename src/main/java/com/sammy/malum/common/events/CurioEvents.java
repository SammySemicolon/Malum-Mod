package com.sammy.malum.common.events;

import com.sammy.malum.common.items.equipment.curios.SimpleCurio;
import com.sammy.malum.common.items.equipment.curios.CurioArchangelRing;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import static net.minecraftforge.eventbus.api.Event.Result.DENY;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CurioEvents
{
    @SubscribeEvent
    public static void noHungerPlease(PotionEvent.PotionApplicableEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            if (event.getPotionEffect().getPotion().equals(Effects.HUNGER))
            {
                PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
                if (CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.LIVING_CAPACITOR.get(), playerEntity).isPresent())
                {
                    event.setResult(DENY);
                    playerEntity.heal(4);
                    playerEntity.getFoodStats().addStats(2,2);
                    playerEntity.world.playSound(null,playerEntity.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.4f, 0.8f);
                }
            }
        }
    }
    @SubscribeEvent
    public static void archangelFallDamage(LivingFallEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioArchangelRing, event.getEntityLiving()).ifPresent(triple -> {
                event.setDistance(event.getDistance() / 2);
            });
        }
    }
    
    @SubscribeEvent
    public static void imperviousSealHurt(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof SimpleCurio, playerEntity).ifPresent(triple -> {
                if (event.getSource().isExplosion())
                {
                    event.setAmount(event.getAmount() * 0.1f);
                }
            });
        }
    }
}