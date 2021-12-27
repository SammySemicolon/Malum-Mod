package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.equipment.curios.CurioTokenOfGratitude;
import com.sammy.malum.common.item.tools.ScytheItem;
import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.registry.item.ItemTagRegistry;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.UUID;

@Mod.EventBusSubscriber
public class EntityEvents {

    @SubscribeEvent
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        MobEffectInstance effectInstance = entity.getEffect(EffectRegistry.CORRUPTED_AERIAL_AURA.get());
        if (effectInstance != null)
        {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, effectInstance.getAmplifier()*0.1f, 0));
        }
    }
    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event) {
        if (event.getSource().equals(DamageSourceRegistry.FORCED_SHATTER))
        {
            SpiritHelper.createSpiritEntities(event.getEntityLiving());
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            LivingEntity target = event.getEntityLiving();
            ItemStack stack = attacker.getMainHandItem();
            DamageSource source = event.getSource();
            if (source.getDirectEntity() instanceof ScytheBoomerangEntity scytheBoomerang) {
                stack = scytheBoomerang.scythe;
            }
            Item item = stack.getItem();

            if (ItemTagRegistry.SOUL_HUNTER_WEAPON.getValues().contains(item)) {
                SpiritHelper.playerSummonSpirits(target, attacker, stack);
            }

            if (item instanceof IEventResponderItem eventItem) {
                eventItem.killEvent(event, attacker, target, stack);
            }
            attacker.getArmorSlots().forEach(s ->{
                if (s.getItem() instanceof IEventResponderItem eventItem)
                {
                    eventItem.killEvent(event, attacker, target, s);
                }
            });
            ArrayList<ItemStack> curios = ItemHelper.equippedCurios(attacker, p -> p.getItem() instanceof IEventResponderItem);
            curios.forEach(c -> ((IEventResponderItem)c.getItem()).killEvent(event, attacker, target, c));
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void processOnHurtAttributes(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            LivingEntity target = event.getEntityLiving();
            ItemStack stack = attacker.getMainHandItem();
            if (event.getSource().getDirectEntity() instanceof ScytheBoomerangEntity)
            {
                stack = ((ScytheBoomerangEntity) event.getSource().getDirectEntity()).scythe;
            }

            if (event.getSource().isMagic()) {
                float resistance = (float) target.getAttributeValue(AttributeRegistry.MAGIC_RESISTANCE);
                float proficiency = (float) attacker.getAttributeValue(AttributeRegistry.MAGIC_PROFICIENCY);

                float amount = event.getAmount();
                float multiplier = (float) (1 * Math.exp(-0.15f*resistance) * Math.exp(0.075f*proficiency));
                event.setAmount(amount*multiplier);
            }
            if (stack.getItem() instanceof ScytheItem)
            {
                float proficiency = (float) attacker.getAttributeValue(AttributeRegistry.SCYTHE_PROFICIENCY);
                float amount = event.getAmount() + proficiency*0.5f;
                event.setAmount(amount);
            }
        }
    }
    @SubscribeEvent
    public static void triggerEventResponses(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            LivingEntity attacked = event.getEntityLiving();
            ItemStack stack = attacker.getMainHandItem();
            Item item = stack.getItem();
            if (item instanceof IEventResponderItem eventItem) {
                eventItem.hurtEvent(event, attacker, attacked, stack);
            }
            attacker.getArmorSlots().forEach(s -> {
                if (s.getItem() instanceof IEventResponderItem eventItem) {
                    eventItem.hurtEvent(event, attacker, attacked, s);
                }
            });
            ArrayList<ItemStack> curios = ItemHelper.equippedCurios(attacker, p -> p.getItem() instanceof IEventResponderItem);
            curios.forEach(c -> ((IEventResponderItem) c.getItem()).hurtEvent(event, attacker, attacked, c));

            stack = attacked.getMainHandItem();
            item = stack.getItem();
            if (item instanceof IEventResponderItem eventItem) {
                eventItem.takeDamageEvent(event, attacker, attacked, stack);
            }
            attacked.getArmorSlots().forEach(s -> {
                if (s.getItem() instanceof IEventResponderItem eventItem) {
                    eventItem.takeDamageEvent(event, attacker, attacked, s);
                }
            });
            curios = ItemHelper.equippedCurios(attacked, p -> p.getItem() instanceof IEventResponderItem);
            curios.forEach(c -> ((IEventResponderItem) c.getItem()).takeDamageEvent(event, attacker, attacked, c));
        }
    }
    @SubscribeEvent
    public static void showGratitude(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player playerEntity) {
            if (!playerEntity.level.isClientSide) {
                if (playerEntity.getUUID().equals(UUID.fromString(CurioTokenOfGratitude.SAMMY))) {
                    if (ItemHelper.findCosmeticCurio(s -> s.getItem().equals(ItemRegistry.TOKEN_OF_GRATITUDE.get()), playerEntity).isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, ItemRegistry.TOKEN_OF_GRATITUDE.get().getDefaultInstance());
                    }
                }
            }
        }
    }
}