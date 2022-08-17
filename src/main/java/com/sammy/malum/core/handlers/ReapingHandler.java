package com.sammy.malum.core.handlers;

import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.listeners.ReapingDataReloadListener;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.reaping.MalumReapingDropsData;
import team.lodestar.lodestone.helpers.ItemHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReapingHandler {

    public static void tryCreateReapingDrops(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        LivingEntity target = event.getEntityLiving();
        LivingEntity attacker = null;
        if (event.getSource().getEntity() instanceof LivingEntity directAttacker) {
            attacker = directAttacker;
        }
        if (attacker == null) {
            attacker = target.getLastHurtByMob();
        }
        if (target.getMobType().equals(MobType.UNDEAD) && attacker instanceof Player player) {
            MalumPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
                if (!c.obtainedEncyclopedia) {
                    c.obtainedEncyclopedia = true;
                    SpiritHelper.createSpiritEntities(List.of(ItemRegistry.ENCYCLOPEDIA_ARCANA.get().getDefaultInstance()), target, 1.25f, player);
                }
            });
        }
        List<MalumReapingDropsData> data = ReapingDataReloadListener.REAPING_DATA.get(target.getType().getRegistryName());
        if (data != null) {
            MalumLivingEntityDataCapability capability = MalumLivingEntityDataCapability.getCapability(target);
            float multiplier = capability.exposedSoul > 0 ? 1 : 0.35f;
            for (MalumReapingDropsData dropData : data) {
                Level level = target.level;
                Random random = level.random;
                if (random.nextFloat() < dropData.chance * multiplier) {
                    Ingredient ingredient = dropData.drop;
                    ItemStack stack = ItemHelper.copyWithNewCount(ingredient.getItems()[random.nextInt(ingredient.getItems().length)], Mth.nextInt(random, dropData.min, dropData.max));
                    ItemEntity itemEntity = new ItemEntity(level, target.getX(), target.getY(), target.getZ(), stack);
                    itemEntity.setDefaultPickUpDelay();
                    itemEntity.setDeltaMovement(Mth.nextFloat(random, -0.1F, 0.1F), Mth.nextFloat(random, 0.25f, 0.5f), Mth.nextFloat(random, -0.1F, 0.1F));
                    level.addFreshEntity(itemEntity);
                }
            }
        }
    }
}
