package com.sammy.malum.common.item.equipment;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.ImpetusItem;
import com.sammy.malum.common.packets.TotemOfUndyingEffectPacket;
import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class CeaselessImpetusItem extends ImpetusItem implements IEventResponderItem {
    public CeaselessImpetusItem(Properties p_41383_) {
        super(p_41383_);
    }

    private static boolean checkTotemDeathProtection(Player player, DamageSource p_21263_) {
        if (p_21263_.isBypassInvul()) {
            return false;
        } else {
            ItemStack itemstack = null;

            for (InteractionHand interactionhand : InteractionHand.values()) {
                ItemStack inventoryStack = player.getItemInHand(interactionhand);
                if (inventoryStack.getItem() instanceof CeaselessImpetusItem impetusItem) {
                    boolean broken = inventoryStack.hurt(1, player.level.random, null);
                    if (broken) {
                        player.setItemInHand(interactionhand, impetusItem.cracked.get().getDefaultInstance());
                    }
                    itemstack = inventoryStack;
                    break;
                }
            }

            if (itemstack != null) {
                if (player instanceof ServerPlayer serverplayer) {
                    serverplayer.awardStat(Stats.ITEM_USED.get(itemstack.getItem()), 1);
                    CriteriaTriggers.USED_TOTEM.trigger(serverplayer, itemstack);
                }

                player.setHealth(2.0F);
                for (MobEffectInstance activeEffect : player.getActiveEffects()) {
                    if (!activeEffect.getEffect().isBeneficial())
                    {
                        player.removeEffect(activeEffect.getEffect());
                    }
                }
                ItemHelper.giveAmplifyingEffect(MobEffects.REGENERATION, player, 400, 0, 3);
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 0));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0));

                INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new TotemOfUndyingEffectPacket(player, itemstack));
            }

            return itemstack != null;
        }
    }
    @Mod.EventBusSubscriber(modid = MalumMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    static class DeathEvent
    {
        @SubscribeEvent
        public static void triggerCeaselessImpetus(LivingDeathEvent event)
        {
            if (event.getEntityLiving() instanceof Player player) {
                if (CeaselessImpetusItem.checkTotemDeathProtection(player, event.getSource()))
                {
                    event.setCanceled(true);
                }
            }
        }
    }
}