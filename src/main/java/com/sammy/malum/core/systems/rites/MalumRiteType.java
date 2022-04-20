package com.sammy.malum.core.systems.rites;

import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Arrays;

public class MalumRiteType {
    public ArrayList<MalumSpiritType> spirits;
    public String identifier;

    public MalumRiteType(String identifier, MalumSpiritType... spirits) {
        this.identifier = identifier;
        this.spirits = new ArrayList<>(Arrays.asList(spirits));
    }

    public String translationIdentifier() {
        return "malum.gui.rite." + identifier;
    }

    public boolean isInstant(boolean corrupted) {
        return false;
    }

    public int defaultRange() {
        return 8;
    }

    public int range(boolean corrupted) {
        return defaultRange();
    }

    public int defaultInterval() {
        return 20;
    }

    public int interval(boolean corrupted) {
        return defaultInterval();
    }

    public void executeRite(Level level, BlockPos pos, int height, boolean corrupted) {
        if (corrupted) {
            corruptedRiteEffect(level, pos, height);
        } else {
            riteEffect(level, pos, height);
        }
    }

    public void riteEffect(Level level, BlockPos pos, int height) {

    }

    public void corruptedRiteEffect(Level level, BlockPos pos, int height) {

    }

    public <T extends LivingEntity> ArrayList<T> getNearbyEntities(Class<T> clazz, Level level, BlockPos pos, boolean corrupted) {
        return (ArrayList<T>) level.getEntitiesOfClass(clazz, new AABB(pos).inflate(range(corrupted)));
    }

    public ArrayList<BlockPos> getNearbyBlocks(Class<?> clazz, Level level, BlockPos pos, boolean corrupted) {
        return BlockHelper.getBlocks(pos, range(corrupted), p -> clazz.isInstance(level.getBlockState(p).getBlock()));
    }

    public ArrayList<BlockPos> getNearbyBlocks(Class<?> clazz, Level level, BlockPos pos, int height, boolean corrupted) {
        int range = range(corrupted);
        return BlockHelper.getBlocks(pos, range, height, range, p -> clazz.isInstance(level.getBlockState(p).getBlock()));
    }

    public ArrayList<BlockPos> getNearbyBlocksUnderBase(Class<?> clazz, Level level, BlockPos pos, boolean corrupted) {
        return BlockHelper.getPlaneOfBlocks(pos.below(), range(corrupted), p -> clazz.isInstance(level.getBlockState(p).getBlock()));
    }
}
