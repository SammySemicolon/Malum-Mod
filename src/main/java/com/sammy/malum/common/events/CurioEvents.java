package com.sammy.malum.common.events;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.equipment.curios.SimpleCurio;
import com.sammy.malum.common.items.equipment.curios.CurioBootsOFLevitation;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
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
    public static void bootsOfLevitationJump(LivingEvent.LivingJumpEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioBootsOFLevitation, playerEntity).ifPresent(triple -> {
                if (MalumHelper.areWeOnServer(playerEntity.world))
                {
                    playerEntity.world.playSound(null, playerEntity.getPosition(), MalumSounds.DRIFT_BOOTS_JUMP, SoundCategory.PLAYERS, 0.4f, 0.75f + playerEntity.world.rand.nextFloat() * 0.5f);
                }
                else
                {
                
                }
            });
        }
    }
    
    @SubscribeEvent
    public static void noHungerPlease(PotionEvent.PotionApplicableEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            if (event.getPotionEffect().getPotion().equals(Effects.HUNGER))
            {
                PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
                if (CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.POPPET_BELT.get(), playerEntity).isPresent())
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
    public static void bootsOfLevitationFallDamage(LivingFallEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioBootsOFLevitation, event.getEntityLiving()).ifPresent(triple -> {
                event.setDistance(event.getDistance() / 3);
            });
        }
    }
    
    @SubscribeEvent
    public static void flaskOfGreedLooting(LootingLevelEvent event)
    {
        if (event.getDamageSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getDamageSource().getTrueSource();
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem().equals(MalumItems.FLASK_OF_GREED.get()), playerEntity).ifPresent(triple -> event.setLootingLevel(event.getLootingLevel() + 1));
        }
    }
    
    @SubscribeEvent
    public static void karmicHolderHurt(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (playerEntity.getHealth() - event.getAmount() <= 5f)
            {
                if (playerEntity.getActivePotionEffect(Effects.WEAKNESS) == null)
                {
                    CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem().equals(MalumItems.KARMIC_HOLDER.get()), playerEntity).ifPresent(triple -> {
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
    public static void karmicHolderKnockback(LivingKnockBackEvent event)
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
    
    @SubscribeEvent
    public static void arcaneSealHurt(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof SimpleCurio, playerEntity).ifPresent(triple -> {
                if (event.getSource().isMagicDamage())
                {
                    event.setAmount(event.getAmount() * 0.5f);
                }
            });
        }
    }
    @SubscribeEvent
    public static void arcaneSealAttack(LivingAttackEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof SimpleCurio, playerEntity).ifPresent(triple -> {
                if (event.getSource().isMagicDamage())
                {
                    event.setCanceled(true);
                }
            });
        }
    }
}