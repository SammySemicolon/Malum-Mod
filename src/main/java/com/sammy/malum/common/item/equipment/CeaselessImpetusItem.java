package com.sammy.malum.common.item.equipment;

import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.helpers.ItemHelper;
import com.sammy.ortus.network.TotemOfUndyingEffectPacket;
import com.sammy.ortus.setup.OrtusPacketRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.setup.server.PacketRegistry.INSTANCE;

public class CeaselessImpetusItem extends ImpetusItem implements IMalumEventResponderItem {
    public CeaselessImpetusItem(Properties p_41383_) {
        super(p_41383_);
    }

    public static void preventDeath(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (event.getEntityLiving() instanceof Player player) {
            if (CeaselessImpetusItem.checkTotemDeathProtection(player, event.getSource())) {
                event.setCanceled(true);
            }
        }
    }

    public static boolean checkTotemDeathProtection(Player player, DamageSource damageSource) {
        if (damageSource.isBypassInvul()) {
            return false;
        } else {
            ItemStack itemstack = null;

            for (InteractionHand interactionhand : InteractionHand.values()) {
                ItemStack inventoryStack = player.getItemInHand(interactionhand);
                if (inventoryStack.getItem() instanceof CeaselessImpetusItem) {
                    boolean broken = inventoryStack.hurt(1, player.level.random, null);
                    if (broken) {
                        player.setItemInHand(interactionhand, ItemRegistry.CRACKED_CEASELESS_IMPETUS.get().getDefaultInstance());
                    }
                    itemstack = inventoryStack;
                    break;
                }
            }

            if (itemstack != null) {
                if (player instanceof ServerPlayer serverplayer) {
                    serverplayer.awardStat(Stats.ITEM_USED.get(itemstack.getItem()), 1);
                    CriteriaTriggers.USED_TOTEM.trigger(serverplayer, itemstack);
                    OrtusPacketRegistry.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverplayer), new TotemOfUndyingEffectPacket(player, itemstack));
                }

                player.setHealth(2.0F);
                ArrayList<MobEffect> toRemove = new ArrayList<>();
                for (MobEffectInstance activeEffect : player.getActiveEffects()) {
                    if (activeEffect.getEffect().getCategory().equals(MobEffectCategory.HARMFUL)) {
                        toRemove.add(activeEffect.getEffect());
                    }
                }

                toRemove.forEach(player::removeEffect);
                EntityHelper.giveAmplifyingEffect(MobEffects.REGENERATION, player, 400, 0, 3);
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 0));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0));

            }

            return itemstack != null;
        }
    }
}