package com.sammy.malum.common.data.component;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.handlers.GeasEffectHandler;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public record GeasDataComponent(GeasEffectType geasEffectType, boolean isCreative) {

    public static Codec<GeasDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeasEffectType.CODEC.lenientOptionalFieldOf("geasEffectType").forGetter(g -> Optional.ofNullable(g.geasEffectType)),
            Codec.BOOL.fieldOf("isCreative").forGetter(g -> g.isCreative)
    ).apply(instance, (g, c) -> new GeasDataComponent(g.orElse(null), c)));

    public static StreamCodec<ByteBuf, GeasDataComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(GeasDataComponent.CODEC);

    @SuppressWarnings("DataFlowIssue")
    public static Optional<GeasDataComponent> getStoredGeasEffect(ItemStack stack) {
        if (!stack.has(MalumDataComponents.GEAS_EFFECT)) {
            return Optional.empty();
        }
        var component = stack.get(MalumDataComponents.GEAS_EFFECT);
        if (component.isInvalid()) {
            return Optional.empty();
        }
        return Optional.of(component);
    }

    public static GeasEffect getEquippedGeasEffectFromStack(LivingEntity entity, ItemStack stack) {
        var component = getStoredGeasEffect(stack).orElse(null);
        if (component == null) {
            return null;
        }
        var geas = component.geasEffectType();
        return GeasEffectHandler.getGeasData(entity).getGeasEffect(entity, geas);
    }

    public boolean isInvalid() {
        return geasEffectType == null;
    }
}