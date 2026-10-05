package com.sammy.malum.core.handlers;

import com.sammy.malum.common.data.listener.spirit.EntitySpiritDropData;
import com.sammy.malum.registry.common.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public class EnsouledItemHarvestHandler {

    public static void onDrops(LivingDropsEvent event) {
        if (event.isCanceled()) {
            return;
        }
        var entity = event.getEntity();

        if (!entity.hasData(MalumAttachmentTypes.LIVING_SOUL_INFO)) {
            return;
        }
        var data = entity.getData(MalumAttachmentTypes.LIVING_SOUL_INFO);
        if (!data.shouldDropSpirits()) {
            return;
        }
        EntitySpiritDropData.getSpiritData(entity).map(EntitySpiritDropData::getItemAsSoul).ifPresent(itemAsSoul -> {
            for (ItemEntity item : event.getDrops()) {
                if (itemAsSoul.test(item.getItem())) {
                    moveSpiritDropsOntoItem(item, entity);
                }
            }
        });
    }

    public static void moveSpiritDropsOntoItem(ItemEntity item, LivingEntity entity) {
        var entityData = entity.getData(MalumAttachmentTypes.CACHED_SPIRIT_DROPS);
        item.setData(MalumAttachmentTypes.CACHED_SPIRIT_DROPS, entityData);
        item.setNeverPickUp();
        item.age = item.lifespan - 20;
        item.setNoGravity(true);
        item.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0.5, 1));
    }

    public static void onItemExpire(ItemExpireEvent event) {
        var item = event.getEntity();
        if (item.level() instanceof ServerLevel level) {
            var data = item.getData(MalumAttachmentTypes.CACHED_SPIRIT_DROPS);
            if (!data.getSpiritDrops().isEmpty()) {
                SoulHarvestHandler.spawnSpirits(item)
                        .setPreferredCollector(data.getSpiritOwner().map(level::getEntity).map(e -> e instanceof LivingEntity entity ? entity : null).orElse(null))
                        .setCustomItems(data.getSpiritDrops()).spawnSpirits(level);
            }
        }
    }
}