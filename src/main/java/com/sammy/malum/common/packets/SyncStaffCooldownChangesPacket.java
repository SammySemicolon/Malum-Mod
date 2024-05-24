package com.sammy.malum.common.packets;

import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import net.minecraft.client.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.world.item.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.systems.network.*;

import java.util.function.*;

public class SyncStaffCooldownChangesPacket extends LodestoneClientPacket {
    private final Item item;
    private final int enchantmentLevel;

    public SyncStaffCooldownChangesPacket(Item pItem, int enchantmentLevel) {
        this.item = pItem;
        this.enchantmentLevel = enchantmentLevel;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeId(BuiltInRegistries.ITEM, item);
        buf.writeInt(enchantmentLevel);
    }

    @Environment(EnvType.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        ReplenishingEnchantment.replenishStaffCooldown((AbstractStaffItem) item, Minecraft.getInstance().player, enchantmentLevel);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SyncStaffCooldownChangesPacket.class, SyncStaffCooldownChangesPacket::encode, SyncStaffCooldownChangesPacket::decode, SyncStaffCooldownChangesPacket::handle);
    }

    public static SyncStaffCooldownChangesPacket decode(FriendlyByteBuf buf) {
        return new SyncStaffCooldownChangesPacket(buf.readById(BuiltInRegistries.ITEM), buf.readInt());
    }
}