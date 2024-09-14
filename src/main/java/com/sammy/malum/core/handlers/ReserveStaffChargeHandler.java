package com.sammy.malum.core.handlers;

import com.sammy.malum.common.capability.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.nbt.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class ReserveStaffChargeHandler {
    public int chargeCount;
    public float chargeProgress;

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("chargeCount", chargeCount);
        tag.putFloat("chargeProgress", chargeProgress);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        chargeCount = tag.getInt("chargeCount");
        chargeProgress = tag.getFloat("chargeProgress");
    }

    public static void recoverStaffCharges(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            AttributeInstance reserveStaffCharges = player.getAttribute(AttributeRegistry.RESERVE_STAFF_CHARGES.get());
            if (reserveStaffCharges != null) {
                ReserveStaffChargeHandler chargeHandler = MalumPlayerDataCapability.getCapability(player).reserveStaffChargeHandler;
                if (chargeHandler.chargeCount < reserveStaffCharges.getValue()) {
                    chargeHandler.chargeProgress++;
                    if (chargeHandler.chargeProgress >= 600) {
                        chargeHandler.chargeProgress = 0;
                        chargeHandler.chargeCount++;
                        MalumPlayerDataCapability.syncTrackingAndSelf(player);
                    }
                }
            }
        }
    }
}