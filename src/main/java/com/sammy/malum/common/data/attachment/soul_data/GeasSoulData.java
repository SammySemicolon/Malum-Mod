package com.sammy.malum.common.data.attachment.soul_data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.*;
import com.sammy.malum.common.payloads.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import io.netty.buffer.*;
import net.minecraft.core.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.network.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class GeasSoulData {

    public static final ResourceLocation GEAS_CURIO_SLOT = MalumMod.malumPath("geas_curio_slot");

    public static final Codec<GeasSoulData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            GeasEffectType.CODEC.listOf().fieldOf("geasEffects").forGetter(sd -> sd.geasEffectTypes)
    ).apply(obj, GeasSoulData::new));

    public static StreamCodec<ByteBuf, GeasSoulData> STREAM_CODEC = ByteBufCodecs.fromCodec(GeasSoulData.CODEC);

    private final List<GeasEffectType> geasEffectTypes = new ArrayList<>();
    private final List<GeasEffect> cachedGeasEffects = new ArrayList<>();
    private final List<ItemStack> cachedGeasStacks = new ArrayList<>();

    private boolean isDirty;

    public GeasSoulData() {
    }

    private GeasSoulData(List<GeasEffectType> geasEffectTypes) {
        geasEffectTypes.forEach(this::addGeasEffect);
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }

    public boolean removeGeasEffect(GeasEffectType geas) {
        if (geasEffectTypes.contains(geas)) {
            geasEffectTypes.remove(geas);
            setDirty(true);
            return true;
        }
        return false;
    }

    public boolean tryAddGeasEffect(LivingEntity target, GeasEffectType geas) {
        var attribute = target.getAttribute(MalumAttributes.GEAS_LIMIT);
        if (attribute == null) {
            return false;
        }
        int limit = Mth.ceil(attribute.getValue());
        if (geasEffectTypes.size() < limit) {
            return addGeasEffect(geas);
        }
        return false;
    }

    public boolean addGeasEffect(GeasEffectType geas) {
        if (geasEffectTypes.contains(geas)) {
            return false;
        }
        geasEffectTypes.add(geas);
        setDirty(true);
        return true;
    }

    public boolean hasGeasEffect(LivingEntity living, Holder<GeasEffectType> type) {
        return getGeasEffect(living, type) != null;
    }
    public boolean hasGeasEffect(LivingEntity living, GeasEffectType type) {
        return getGeasEffect(living, type) != null;
    }

    public List<ItemStack> getGeasItemStacks(LivingEntity entity) {
        updateCaches(entity);
        return cachedGeasStacks;
    }

    public GeasEffect getGeasEffect(LivingEntity entity, Holder<GeasEffectType> type) {
        return getGeasEffect(entity, type.value());
    }
    public GeasEffect getGeasEffect(LivingEntity entity, GeasEffectType type) {
        if (!geasEffectTypes.contains(type)) {
            return null;
        }
        return getGeasEffects(entity).get(getGeasIndex(type));
    }

    public List<GeasEffect> getGeasEffects(LivingEntity entity) {
        updateCaches(entity);
        return cachedGeasEffects;
    }

    public int getGeasIndex(GeasEffectType type) {
        return geasEffectTypes.indexOf(type);
    }

    private void updateGeasSlotCount(LivingEntity entity) {
        int geasCount = geasEffectTypes.size();
        CuriosApi.getCuriosInventory(entity).ifPresent(h -> {
            var geasSlots = h.getStacksHandler("geas");
            if (geasSlots.isEmpty()) {
                return;
            }
            var existing = geasSlots.get().getModifiers().get(GEAS_CURIO_SLOT);
            if (existing != null && existing.amount() == geasCount && existing.operation() == AttributeModifier.Operation.ADD_VALUE) {
                return;
            }
            if (existing != null) {
                h.removeSlotModifier("geas", GEAS_CURIO_SLOT);
            }
            h.addPermanentSlotModifier("geas", GEAS_CURIO_SLOT, geasCount, AttributeModifier.Operation.ADD_VALUE);
        });
    }

    public void updateCaches(LivingEntity entity) {
        if (isDirty()) {
            cachedGeasEffects.forEach(e -> {
                if (geasEffectTypes.contains(e.type)) {
                    e.removeAttributeModifiers(entity);
                } else {
                    e.onRemoved(entity);
                }
            });
            cachedGeasEffects.clear();
            cachedGeasStacks.clear();
            for (GeasEffectType geas : geasEffectTypes) {
                cachedGeasEffects.add(geas.createEffect());
                cachedGeasStacks.add(geas.createDefaultStack());
            }
            if (!entity.level().isClientSide) {
                entity.syncData(MalumAttachmentTypes.GEAS_SOUL_INFO);
            }
            if (!entity.level().isClientSide) {
                updateGeasSlotCount(entity);
            }
            setDirty(false);
        }
    }
}