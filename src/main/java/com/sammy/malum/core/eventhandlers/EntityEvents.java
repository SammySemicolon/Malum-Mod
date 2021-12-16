package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.tools.ScytheItem;
import com.sammy.malum.core.registry.item.ITemTagRegistry;
import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.UUID;

import static com.sammy.malum.common.item.equipment.curios.CurioTokenOfGratitude.sammy_uuid;

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
        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity target = event.getEntityLiving();
            ItemStack stack = attacker.getMainHandItem();
            if (event.getSource().getDirectEntity() instanceof ScytheBoomerangEntity) {
                stack = ((ScytheBoomerangEntity) event.getSource().getDirectEntity()).scythe;
            }
            Item item = stack.getItem();

            if (ITemTagRegistry.SOUL_HUNTER_WEAPON.getValues().contains(item))
            {
                SpiritHelper.playerSummonSpirits(target, attacker, stack);
            }

            if (item instanceof IEventResponderItem) {
                IEventResponderItem eventItem = (IEventResponderItem) item;
                eventItem.deathEvent(event, attacker, target, stack);
            }
            attacker.getArmorSlots().forEach(s ->{
                if (s.getItem() instanceof IEventResponderItem)
                {
                    IEventResponderItem eventItem = (IEventResponderItem) s.getItem();
                    eventItem.deathEvent(event, attacker, target, s);
                }
            });
            ArrayList<ItemStack> curios = MalumHelper.equippedCurios(attacker, p -> p.getItem() instanceof IEventResponderItem);
            curios.forEach(c -> ((IEventResponderItem)c.getItem()).deathEvent(event, attacker, target, c));
        }
    }
    @SubscribeEvent
    public static void triggerOnHurtEvents(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity target = event.getEntityLiving();
            ItemStack stack = attacker.getMainHandItem();
            if (event.getSource().getDirectEntity() instanceof ScytheBoomerangEntity)
            {
                stack = ((ScytheBoomerangEntity) event.getSource().getDirectEntity()).scythe;
            }
            Item item = stack.getItem();
            if (item instanceof IEventResponderItem) {
                IEventResponderItem eventItem = (IEventResponderItem) item;
                eventItem.hurtEvent(event, attacker, target, stack);
            }
            attacker.getArmorSlots().forEach(s ->{
                if (s.getItem() instanceof IEventResponderItem)
                {
                    IEventResponderItem eventItem = (IEventResponderItem) s.getItem();
                    eventItem.hurtEvent(event, attacker, target, s);
                }
            });
            ArrayList<ItemStack> curios = MalumHelper.equippedCurios(attacker, p -> p.getItem() instanceof IEventResponderItem);
            curios.forEach(c -> ((IEventResponderItem)c.getItem()).hurtEvent(event, attacker, target, c));

            if (event.getSource().isMagic()) {
                float resistance = (float) target.getAttributeValue(AttributeRegistry.MAGIC_RESISTANCE);
                float proficiency = (float) attacker.getAttributeValue(AttributeRegistry.MAGIC_PROFICIENCY);
                float amount = event.getAmount() + proficiency;
                float multiplier = 1 - (resistance * 0.125f);
                event.setAmount(amount*multiplier);
            }
            if (stack.getItem() instanceof ScytheItem)
            {
                float proficiency = (float) attacker.getAttributeValue(AttributeRegistry.SCYTHE_PROFICIENCY);
                float amount = event.getAmount() + proficiency;
                event.setAmount(amount);
            }
        }
    }

    @SubscribeEvent
    public static void showGratitude(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player) {
            Player playerEntity = (Player) event.getEntity();
            if (!playerEntity.level.isClientSide) {
                if (playerEntity.getUUID().equals(UUID.fromString(sammy_uuid))) {
                    if (!MalumHelper.findCosmeticCurio(s -> s.getItem().equals(ItemRegistry.TOKEN_OF_GRATITUDE.get()), playerEntity).isPresent()) {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, ItemRegistry.TOKEN_OF_GRATITUDE.get().getDefaultInstance());
                    }
                }
            }
        }
    }
}