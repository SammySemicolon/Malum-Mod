package com.sammy.malum.core.handlers;

import com.sammy.malum.*;
import com.sammy.malum.common.data.attachment.soul_data.GeasSoulData;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.handlers.item_events.EventResponderCollection;
import team.lodestar.lodestone.handlers.item_events.EventResponderSource;
import team.lodestar.lodestone.handlers.item_events.ItemEventHandler;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.extensions.*;

import java.util.*;

public class GeasEffectHandler {

    public static final EventResponderSource GEAS_EFFECTS = ItemEventHandler.registerSource(new GeasEventSource(MalumMod.malumPath("geas_effects")));

    public static void registerSlotExtensions(RegisterCuriosExtensionsEvent event) {
        event.registerSlotExtension(new ICurioSlotExtension() {
            @Override
            public ItemStack getDisplayStack(SlotContext slotContext, ItemStack defaultStack) {
                return getGeasDisplayStack(slotContext.entity(), slotContext.index());
            }
        }, "geas");
    }

    public static void syncGeas(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            if (entity.level().isClientSide) {
                return;
            }
            var data = entity.getData(MalumAttachmentTypes.GEAS_SOUL_INFO);
            data.setDirty(true);
        }
    }

    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            var effects = getGeasEffects(entity);
            for (GeasEffect effect : effects) {
                effect.updateAttributes(entity);
                effect.update(event, entity);
            }
        }
    }

    public static boolean addGeasEffect(LivingEntity entity, GeasEffectType geas) {
        return getGeasData(entity).tryAddGeasEffect(entity, geas);
    }

    public static boolean hasGeas(LivingEntity entity, Holder<GeasEffectType> type) {
        return getExistingGeasData(entity).map(d -> d.hasGeasEffect(entity, type)).orElse(false);
    }

    public static boolean hasGeas(LivingEntity entity, GeasEffectType type) {
        return getExistingGeasData(entity).map(d -> d.hasGeasEffect(entity, type)).orElse(false);
    }

    public static boolean removeGeasEffect(LivingEntity entity, GeasEffectType geas) {
        return getGeasData(entity).removeGeasEffect(geas);
    }

    public static ItemStack getGeasDisplayStack(LivingEntity entity, int index) {
        return getExistingGeasData(entity).map(d -> d.getGeasDisplayStack(entity, index)).orElse(null);
    }

    public static GeasEffect getGeasEffect(LivingEntity entity, Holder<GeasEffectType> type) {
        return getExistingGeasData(entity).map(d -> d.getGeasEffect(entity, type)).orElse(null);
    }

    public static List<GeasEffect> getGeasEffects(LivingEntity entity) {
        return getExistingGeasData(entity).map(d -> d.getGeasEffects(entity)).orElse(Collections.emptyList());
    }

    public static GeasSoulData getGeasData(LivingEntity entity) {
        return entity.getData(MalumAttachmentTypes.GEAS_SOUL_INFO);
    }

    public static Optional<GeasSoulData> getExistingGeasData(LivingEntity entity) {
        return entity.getExistingData(MalumAttachmentTypes.GEAS_SOUL_INFO);
    }

    public static class GeasEventSource extends EventResponderSource {
        public GeasEventSource(ResourceLocation id) {
            super(id);
        }

        @Override
        public EventResponderCollection getEventResponders(LivingEntity entity) {
            var geasEffects = getGeasEffects(entity).stream().map(EventResponderCollection.Entry::new).toList();
            return new EventResponderCollection(this, geasEffects);
        }
    }
}