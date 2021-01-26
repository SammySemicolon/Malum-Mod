package com.sammy.malum.common.events;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.effects.Entangled;
import com.sammy.malum.common.entities.ScytheBoomerangEntity;
import com.sammy.malum.common.items.tools.scythes.ScytheItem;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.enchantments.MalumEnchantments;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SplinterHandlingEvents
{
    @SubscribeEvent
    public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event)
    {
        if (event.getTarget() instanceof LivingEntity)
        {
            LivingEntity entity = (LivingEntity) event.getTarget();
            if (MalumHelper.areWeOnServer(entity.world))
            {
                if (entity.getActivePotionEffect(MalumEffects.ENTANGLED.get()) == null)
                {
                    if (event.getItemStack().getItem().equals(MalumItems.RUDIMENTARY_SNARE.get()))
                    {
                        entity.addPotionEffect(new EffectInstance(MalumEffects.ENTANGLED.get(), 200, 0));
                        event.getItemStack().shrink(1);
                        event.getPlayer().swingArm(event.getHand());
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            ItemStack stack = ItemStack.EMPTY;
            PlayerEntity attacker = (PlayerEntity) event.getSource().getTrueSource();
            if (event.getEntityLiving().getActivePotionEffect(MalumEffects.ENTANGLED.get()) != null)
            {
                SpiritHelper.summonSpirits(event.getEntityLiving(), attacker, stack);
                return;
            }
            else if (attacker.getHeldItemOffhand().getItem().equals(MalumItems.RUDIMENTARY_SNARE.get()))
            {
                attacker.getHeldItemOffhand().shrink(1);
                SpiritHelper.summonSpirits(event.getEntityLiving(), attacker, stack);
                return;
            }
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
