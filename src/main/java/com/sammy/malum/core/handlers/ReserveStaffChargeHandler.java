package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.systems.item.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.player.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.client.gui.overlay.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.living.*;
import org.joml.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

import java.lang.Math;


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

    public static void recoverStaffCharges(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
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