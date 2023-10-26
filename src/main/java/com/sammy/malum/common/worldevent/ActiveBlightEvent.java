package com.sammy.malum.common.worldevent;

import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.worldgen.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.systems.worldevent.*;
import team.lodestar.lodestone.systems.worldgen.*;

import java.util.*;

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
        createBlightVFX(level, filler);
        level.playSound(null, sourcePos, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, 1.8f);
    }

    public static void createBlightVFX(ServerLevel level, LodestoneBlockFiller filler) {
        filler.getEntries().entrySet().stream().filter(e -> e.getValue().getState().getBlock() instanceof BlightedSoilBlock).map(Map.Entry::getKey)
                .forEach(p -> ParticleEffectTypeRegistry.BLIGHTING_MIST.createPositionedEffect(level, new PositionEffectData(p)));
    }
}