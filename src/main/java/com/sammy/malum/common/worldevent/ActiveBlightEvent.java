package com.sammy.malum.common.worldevent;

import com.sammy.malum.common.worldgen.SoulwoodTreeFeature;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.WorldEventTypes;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.ortus.systems.worldevent.WorldEventInstance;
import com.sammy.ortus.systems.worldgen.OrtusBlockFiller;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import java.util.Random;

public class ActiveBlightEvent extends WorldEventInstance {
    public int blightTimer, intensity, rate, times;
    public BlockPos sourcePos, currentPos;

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
        this.currentPos = sourcePos;
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
        OrtusBlockFiller blightFiller = new OrtusBlockFiller(false);
        Random rand = level.getRandom();
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST};
        int blightSize = intensity + rand.nextInt(Math.max(1, intensity / 2));
        SoulwoodTreeFeature.createBlight(level, blightFiller, BlockRegistry.BLIGHTED_SPIRE, level.getRandom(), sourcePos.below(), blightSize);
        for (Direction direction : directions) {
            BlockPos relative = sourcePos.below().relative(direction).offset(rand.nextInt(intensity + 2), 0, rand.nextInt(intensity + 2));
            SoulwoodTreeFeature.createBlight(level, blightFiller, BlockRegistry.BLIGHTED_WEED, level.getRandom(), relative, blightSize);
            Direction otherDirection = directions[rand.nextInt(directions.length)];
            if (otherDirection.equals(direction)) {
                continue;
            }
            SoulwoodTreeFeature.createBlight(level, blightFiller, BlockRegistry.BLIGHTED_WEED, level.getRandom(), relative.relative(otherDirection, intensity + rand.nextInt(Math.max(1, intensity / 2))), blightSize - 1);
            blightSize--;
        }
        level.playSound(null, sourcePos, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, 1.8f);
        blightFiller.fill(level);
    }
}