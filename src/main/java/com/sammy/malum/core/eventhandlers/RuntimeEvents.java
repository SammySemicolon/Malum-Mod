package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.common.capability.PlayerDataCapability;
import com.sammy.malum.common.effect.CorruptedAerialAura;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.equipment.curios.CurioTokenOfGratitude;
import com.sammy.malum.common.item.spirit.ScytheItem;
import com.sammy.malum.common.item.spirit.SoulStaveItem;
import com.sammy.malum.common.spiritaffinity.ArcaneAffinity;
import com.sammy.malum.common.spiritaffinity.EarthenAffinity;
import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.registry.item.ItemTagRegistry;
import com.sammy.malum.core.registry.AttributeRegistry;
import com.sammy.malum.core.registry.DamageSourceRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.helper.SpiritHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RuntimeEvents {
    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        PlayerDataCapability.attachPlayerCapability(event);
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        PlayerDataCapability.playerJoin(event);
        CurioTokenOfGratitude.giveItem(event);
    }
    @SubscribeEvent
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        CorruptedAerialAura.onEntityJump(event);
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ArcaneAffinity.recoverSoulWard(event);
        EarthenAffinity.recoverHeartOfStone(event);
        SoulStaveItem.playerTick(event);
    }
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        ArcaneAffinity.consumeSoulWard(event);
        EarthenAffinity.consumeHeartOfStone(event);
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            LivingEntity target = event.getEntityLiving();

            ItemHelper.eventResponders(attacker).forEach(s -> ((IEventResponderItem)s.getItem()).hurtEvent(event, attacker, target, s));
            ItemHelper.eventResponders(target).forEach(s -> ((IEventResponderItem)s.getItem()).takeDamageEvent(event, target, attacker, s));

            ItemStack stack = attacker.getMainHandItem();
            if (event.getSource().getDirectEntity() instanceof ScytheBoomerangEntity)
            {
                stack = ((ScytheBoomerangEntity) event.getSource().getDirectEntity()).scythe;
            }
            float amount = event.getAmount();
            if (event.getSource().isMagic()) {
                float resistance = (float) target.getAttributeValue(AttributeRegistry.MAGIC_RESISTANCE);
                float proficiency = (float) attacker.getAttributeValue(AttributeRegistry.MAGIC_PROFICIENCY);

                float multiplier = (float) (1 * Math.exp(-0.15f*resistance) * Math.exp(0.075f*proficiency));
                event.setAmount(amount*multiplier);
            }
            if (stack.getItem() instanceof ScytheItem)
            {
                float proficiency = (float) attacker.getAttributeValue(AttributeRegistry.SCYTHE_PROFICIENCY);
                event.setAmount(amount+proficiency*0.5f);
            }
        }
    }
    @SubscribeEvent
    public static void triggerEventResponses(LivingDeathEvent event) {
        if (event.getSource().getMsgId().equals(DamageSourceRegistry.VOODOO_NO_SHATTER.getMsgId()))
        {
            return;
        }
        if (event.getSource().getMsgId().equals(DamageSourceRegistry.FORCED_SHATTER.getMsgId()))
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
            ItemHelper.eventResponders(attacker).forEach(s -> ((IEventResponderItem)s.getItem()).killEvent(event, attacker, target, s));
        }
    }
}