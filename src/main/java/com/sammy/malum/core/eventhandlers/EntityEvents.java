package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.tools.ScytheItem;
import com.sammy.malum.core.registry.items.ITemTagRegistry;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.items.ItemRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
    public static void onEntityKill(LivingDeathEvent event) {
        if (event.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            LivingEntity target = event.getEntityLiving();
            ItemStack stack = attacker.getHeldItemMainhand();
            if (event.getSource().getImmediateSource() instanceof ScytheBoomerangEntity) {
                stack = ((ScytheBoomerangEntity) event.getSource().getImmediateSource()).scythe;
            }
            Item item = stack.getItem();
            if (item instanceof IEventResponderItem) {
                IEventResponderItem eventItem = (IEventResponderItem) item;
                eventItem.deathEvent(event, attacker, target, stack);
            }
            if (ITemTagRegistry.SOUL_HUNTER_WEAPON.getAllElements().contains(item))
            {
                SpiritHelper.playerSummonSpirits(target, attacker, stack);
            }
            attacker.getArmorInventoryList().forEach(s ->{
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
        if (event.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            LivingEntity target = event.getEntityLiving();
            ItemStack stack = attacker.getHeldItemMainhand();
            if (event.getSource().getImmediateSource() instanceof ScytheBoomerangEntity)
            {
                stack = ((ScytheBoomerangEntity) event.getSource().getImmediateSource()).scythe;
            }
            Item item = stack.getItem();
            if (item instanceof IEventResponderItem) {
                IEventResponderItem eventItem = (IEventResponderItem) item;
                eventItem.hurtEvent(event, attacker, target, stack);
            }
            attacker.getArmorInventoryList().forEach(s ->{
                if (s.getItem() instanceof IEventResponderItem)
                {
                    IEventResponderItem eventItem = (IEventResponderItem) s.getItem();
                    eventItem.hurtEvent(event, attacker, target, s);
                }
            });
            ArrayList<ItemStack> curios = MalumHelper.equippedCurios(attacker, p -> p.getItem() instanceof IEventResponderItem);
            curios.forEach(c -> ((IEventResponderItem)c.getItem()).hurtEvent(event, attacker, target, c));

            if (event.getSource().isMagicDamage()) {
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
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
            if (MalumHelper.areWeOnServer(playerEntity.world)) {
                if (playerEntity.getUniqueID().equals(UUID.fromString(sammy_uuid))) {
                    if (!MalumHelper.findCosmeticCurio(s -> s.getItem().equals(ItemRegistry.TOKEN_OF_GRATITUDE.get()), playerEntity).isPresent()) {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, ItemRegistry.TOKEN_OF_GRATITUDE.get().getDefaultInstance());
                    }
                }
            }
        }
    }
}