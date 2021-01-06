package com.sammy.malum.common.events;

import com.sammy.malum.common.items.equipment.curios.CurioKarmicHolder;
import com.sammy.malum.common.items.equipment.curios.CurioFlaskOfGreed;
import com.sammy.malum.common.items.equipment.curios.CurioDriftBoots;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CurioEvents
{
    @SubscribeEvent
    public static void arcaneSandalsJumpSound(LivingEvent.LivingJumpEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioDriftBoots, playerEntity).ifPresent(triple -> {
                playerEntity.world.playSound(null, playerEntity.getPosition(), MalumSounds.DRIFT_BOOTS_JUMP, SoundCategory.PLAYERS, 0.4f, 0.75f + playerEntity.world.rand.nextFloat() * 0.5f);
            });
        }
    }
    @SubscribeEvent
    public static void arcaneSandalsFallDamage(LivingFallEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioDriftBoots, event.getEntityLiving()).ifPresent(triple -> {
                event.setDistance(event.getDistance() / 3);
            });
        }
    }
    
    @SubscribeEvent
    public static void triggerThroneOfGold(LootingLevelEvent event)
    {
        if (event.getDamageSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getDamageSource().getTrueSource();
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioFlaskOfGreed, playerEntity).ifPresent(triple -> event.setLootingLevel(event.getLootingLevel() + 1));
        }
    }
    @SubscribeEvent
    public static void triggerFoolsLuck(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (playerEntity.getHealth() - event.getAmount() <= 5f)
            {
                if (playerEntity.getActivePotionEffect(Effects.WEAKNESS) == null)
                {
                    CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioKarmicHolder, playerEntity).ifPresent(triple -> {
                        playerEntity.addPotionEffect(new EffectInstance(MalumEffects.FOOLS_LUCK.get(), 200, 0));
                        playerEntity.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1200, 0));
                        playerEntity.world.playSound(null, playerEntity.getPosition(), MalumSounds.KARMIC_HOLDER_ACTIVATE, SoundCategory.PLAYERS, 1, 1);
                    });
                }
            }
            if (playerEntity.getActivePotionEffect(MalumEffects.FOOLS_LUCK.get()) != null)
            {
                event.setAmount(event.getAmount() * 0.25f);
            }
        }
    }
    @SubscribeEvent
    public static void foolsLuckExtra(LivingKnockBackEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (playerEntity.getActivePotionEffect(MalumEffects.FOOLS_LUCK.get()) != null)
            {
                event.setStrength(event.getOriginalStrength() * 0.1f);
            }
        }
    }
}