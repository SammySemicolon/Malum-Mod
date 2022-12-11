package com.sammy.malum.common.worldevent;

import com.sammy.malum.common.block.blight.BlightedSoilBlock;
import com.sammy.malum.common.packets.particle.block.blight.BlightMistParticlePacket;
import com.sammy.malum.common.worldgen.SoulwoodTreeFeature;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.WorldEventTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.Map;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class ActiveBlightEvent extends WorldEventInstance {
    public int blightTimer, intensity, rate, times;
    public BlockPos sourcePos;
    public Map<Integer, Double> noiseValues;

    public ActiveBlightEvent() {
        super(WorldEventTypes.ACTIVE_BLIGHT);
    }

    public ActiveBlightEvent setBlightData(int intensity, int rate, int times) {
        this.intensity = intensity;
        this.rate = rate;
        this.times = times;
        return this;
    }

    public ActiveBlightEvent setPosition(BlockPos sourcePos) {
        this.sourcePos = sourcePos;
        return this;
    }

    @Override
    public void tick(Level level) {
        if (times == 0) {
            end(level);
            return;
        }
        if (blightTimer == 0) {
            blightTimer = rate;
            times--;
            createBlight((ServerLevel) level);
            intensity+=2;
        } else {
            blightTimer--;
        }
    }

    public void createBlight(ServerLevel level) {
        LodestoneBlockFiller filler = new LodestoneBlockFiller(false);
        if (noiseValues == null) {
            noiseValues = SoulwoodTreeFeature.generateBlight(level, filler, sourcePos, intensity);
        } else {
            SoulwoodTreeFeature.generateBlight(level, filler, noiseValues, sourcePos, intensity);
        }
        filler.entries.stream().filter(e -> e.state.getBlock() instanceof BlightedSoilBlock).map(e -> e.pos).forEach(p -> MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new BlightMistParticlePacket(p)));

        level.playSound(null, sourcePos, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, 1.8f);
    }
}