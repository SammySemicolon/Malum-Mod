package com.sammy.malum.common.capability;

import com.sammy.malum.core.systems.capability.SimpleCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerDataCapability implements SimpleCapability {

    //shove all player data here, use PlayerDataCapability.getCapability(player) to access data.

    public static Capability<PlayerDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public boolean firstTimeJoin = false;
    public float soulWard;
    public float soulWardCap;
    public float soulWardCooldown;


    public PlayerDataCapability() {
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("firstTimeJoin", firstTimeJoin);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        firstTimeJoin = tag.getBoolean("firstTimeJoin");

    }

    public static LazyOptional<PlayerDataCapability> getCapability(Player player) {
        return player.getCapability(CAPABILITY);
    }
}
