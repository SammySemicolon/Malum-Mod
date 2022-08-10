package com.sammy.malum.common.worldevent;

import com.sammy.malum.common.block.blight.BlightedGrassBlock;
import com.sammy.malum.common.block.blight.BlightedSoilBlock;
import com.sammy.malum.common.packets.particle.block.blight.BlightMistParticlePacket;
import com.sammy.malum.common.worldgen.SoulwoodTreeFeature;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.WorldEventTypes;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;

public class ActiveBlightEvent extends WorldEventInstance {
    public int blightTimer, intensity, rate, times;
    public BlockPos sourcePos;

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
            createBlight(level);
            intensity++;
        } else {
            blightTimer--;
        }
    }

    public void createBlight(Level level) {
        createBlight(level, sourcePos, intensity);
    }

    public static void createBlight(Level level, BlockPos sourcePos, int intensity) {
        LodestoneBlockFiller blightFiller = new LodestoneBlockFiller(false);
        Random rand = level.getRandom();
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST};
        int blightSize = intensity + rand.nextInt(Math.max(1, intensity / 2));
        Set<BlockPos> affectedPositions = SoulwoodTreeFeature.createBlight(level, blightFiller, BlockRegistry.BLIGHTED_SPIRE, level.getRandom(), sourcePos.below(), blightSize, 0.025f).stream().filter(e -> (e.state.getBlock() instanceof BlightedSoilBlock)).map(e -> e.pos).collect(Collectors.toSet());
        for (Direction direction : directions) {
            BlockPos relative = sourcePos.below().relative(direction).offset(rand.nextInt(intensity + 2), 0, rand.nextInt(intensity + 2));
            SoulwoodTreeFeature.createBlight(level, blightFiller, BlockRegistry.BLIGHTED_WEED, level.getRandom(), relative, blightSize, 0.05f);
            Direction otherDirection = directions[rand.nextInt(directions.length)];
            if (otherDirection.equals(direction)) {
                continue;
            }
            affectedPositions.addAll(SoulwoodTreeFeature.createBlight(level, blightFiller, BlockRegistry.BLIGHTED_WEED, level.getRandom(), relative.relative(otherDirection, intensity + rand.nextInt(Math.max(1, intensity / 2))), blightSize - 1, intensity * 0.1f).stream().filter(e -> (e.state.getBlock() instanceof BlightedSoilBlock)).map(e -> e.pos).collect(Collectors.toSet()));
            blightSize--;
        }
        affectedPositions.forEach(p -> MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new BlightMistParticlePacket(p)));
        level.playSound(null, sourcePos, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, 1.8f);
        blightFiller.fill(level);
    }
}