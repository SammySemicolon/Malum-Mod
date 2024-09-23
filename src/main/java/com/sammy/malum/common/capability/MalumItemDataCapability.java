package com.sammy.malum.common.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.CodecUtil;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MalumItemDataCapability {

    public static final EntityCapability<MalumItemDataCapability, Void> CAPABILITY = EntityCapability.createVoid(
            MalumMod.malumPath("item_data"),
            MalumItemDataCapability.class
    );

    public static final Codec<MalumItemDataCapability> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.list(ItemStack.CODEC).fieldOf("soulsToDrop").forGetter(c -> c.soulsToDrop),
            UUIDUtil.CODEC.fieldOf("attackerForSouls").forGetter(c -> c.attackerForSouls),
            Codec.FLOAT.fieldOf("totalSoulCount").forGetter(c -> c.totalSoulCount)
    ).apply(obj, MalumItemDataCapability::new));

    public List<ItemStack> soulsToDrop;
    public UUID attackerForSouls;
    public float totalSoulCount;

    public MalumItemDataCapability() {}

    public MalumItemDataCapability(List<ItemStack> soulsToDrop, UUID attackerForSouls, float totalSoulCount) {
        this.soulsToDrop = soulsToDrop;
        this.attackerForSouls = attackerForSouls;
        this.totalSoulCount = totalSoulCount;
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(CAPABILITY, EntityType.ITEM, GET_CAPABILITY);
    }

    public static ICapabilityProvider<ItemEntity, Void, MalumItemDataCapability> GET_CAPABILITY = (item, ctx) -> {
        CompoundTag tag = item.getPersistentData();
        return CodecUtil.decodeNBT(MalumItemDataCapability.CODEC, tag);
    };

    public static Optional<MalumItemDataCapability> getCapabilityOptional(ItemEntity entity) {
        return Optional.ofNullable(entity.getCapability(CAPABILITY));
    }

    public static MalumItemDataCapability getCapability(ItemEntity entity) {
        return getCapabilityOptional(entity).orElse(new MalumItemDataCapability(null, null, 0));
    }
}
