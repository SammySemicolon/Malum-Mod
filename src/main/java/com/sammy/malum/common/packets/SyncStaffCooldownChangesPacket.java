package com.sammy.malum.common.packets;

import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.ClientPacketListener;
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

    public SyncStaffCooldownChangesPacket(FriendlyByteBuf buf) {
        this.item = buf.readById(BuiltInRegistries.ITEM);
        this.enchantmentLevel = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeId(BuiltInRegistries.ITEM, item);
        buf.writeInt(enchantmentLevel);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void executeClient(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        ReplenishingEnchantment.replenishStaffCooldown((AbstractStaffItem) item, client.player, enchantmentLevel);
    }
}