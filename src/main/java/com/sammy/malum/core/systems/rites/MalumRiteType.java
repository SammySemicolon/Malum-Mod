package com.sammy.malum.core.systems.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class MalumRiteType {
    public ArrayList<MalumSpiritType> spirits;
    public String identifier;
    public boolean isInstant;

    public MalumRiteType(String identifier, boolean isInstant, MalumSpiritType... spirits) {
        this.identifier = identifier;
        this.isInstant = isInstant;
        this.spirits = MalumHelper.toArrayList(spirits);
    }

    public String translationIdentifier()
    {
        return "malum.gui.rite." + identifier;
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

    public void executeRite(Level level, BlockPos pos, boolean corrupted) {
        if (corrupted) {
            corruptedRiteEffect(level, pos);
        } else {
            riteEffect(level, pos);
        }
    }

    public void riteEffect(Level level, BlockPos pos) {

    }

    public void corruptedRiteEffect(Level level, BlockPos pos) {

    }

    public <T extends LivingEntity> ArrayList<T> getNearbyEntities(Class<T> clazz, Level level, BlockPos pos, boolean corrupted)
    {
        return (ArrayList<T>) level.getEntitiesOfClass(clazz, new AABB(pos).inflate(range(corrupted)));
    }
    public ArrayList<BlockPos> getNearbyBlocks(Class<?> clazz, Level level, BlockPos pos, boolean corrupted)
    {
        return MalumHelper.getBlocks(pos, range(corrupted), p -> clazz.isInstance(level.getBlockState(p).getBlock()));
    }
    public ArrayList<BlockPos> getNearbyBlocksUnderBase(Class<?> clazz, Level level, BlockPos pos, boolean corrupted)
    {
        return MalumHelper.getPlaneOfBlocks(pos.below(), range(corrupted), p -> clazz.isInstance(level.getBlockState(p).getBlock()));
    }
}
