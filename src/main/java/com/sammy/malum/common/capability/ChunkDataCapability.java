package com.sammy.malum.common.capability;

import com.sammy.malum.core.systems.capability.SimpleCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class ChunkDataCapability implements SimpleCapability {

    //shove all chunk data here, use ChunkDataCapability.getCapability(chunk) to access data.

    public static Capability<ChunkDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });


    public ChunkDataCapability() {
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
    }

    public static LazyOptional<ChunkDataCapability> getCapability(LevelChunk chunk) {
        return chunk.getCapability(CAPABILITY);
    }
}
