package com.sammy.malum.common.capability;

import com.sammy.malum.core.registry.content.SpiritAffinityRegistry;
import com.sammy.malum.core.systems.capability.SimpleCapability;
import com.sammy.malum.core.systems.spirit.MalumSpiritAffinity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerDataCapability implements SimpleCapability {

    public static Capability<PlayerDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public MalumSpiritAffinity affinity;

    public boolean firstTimeJoin = false;

    public int soulWard;
    public int soulWardCap;
    public float soulWardAcceleration;

    public int heartOfStone=6;
    public int heartOfStoneCap;

    public PlayerDataCapability() {
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        if (affinity != null) {
            tag.putString("affinity", affinity.identifier);
        }
        tag.putInt("soulWard", soulWard);
        tag.putInt("soulWardCap", soulWardCap);
        tag.putFloat("soulWardAcceleration", soulWardAcceleration);

        tag.putInt("heartOfStone", heartOfStone);
        tag.putFloat("heartOfStoneCap", heartOfStoneCap);

        tag.putBoolean("firstTimeJoin", firstTimeJoin);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        affinity = SpiritAffinityRegistry.AFFINITIES.get(tag.getString("affinity"));

        soulWard = tag.getInt("soulWard");
        soulWardCap = tag.getInt("soulWardCap");
        soulWardAcceleration = tag.getFloat("soulWardAcceleration");

        heartOfStone = tag.getInt("heartOfStone");
        heartOfStoneCap = tag.getInt("heartOfStoneCap");

        firstTimeJoin = tag.getBoolean("firstTimeJoin");
    }

    public static LazyOptional<PlayerDataCapability> getCapability(Player player) {
        return player.getCapability(CAPABILITY);
    }
}
