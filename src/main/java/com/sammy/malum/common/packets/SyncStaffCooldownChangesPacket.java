package com.sammy.malum.common.packets;

import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import net.minecraft.client.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

import java.util.function.*;

public class SyncStaffCooldownChangesPacket extends OneSidedPayloadData {
    private final Item item;
    private final int enchantmentLevel;

    public SyncStaffCooldownChangesPacket(FriendlyByteBuf buf) {
        super(buf);
        this.item =  ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);
        this.enchantmentLevel = buf.readInt();
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeById();
        friendlyByteBuf.writeInt(this.enchantmentLevel);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handle(IPayloadContext iPayloadContext) {
        ReplenishingEnchantment.replenishStaffCooldown((AbstractStaffItem) item, Minecraft.getInstance().player, enchantmentLevel);
    }
}