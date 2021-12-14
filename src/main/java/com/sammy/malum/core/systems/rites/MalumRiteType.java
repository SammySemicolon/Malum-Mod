package com.sammy.malum.core.systems.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

    public void executeRite(World world, BlockPos pos, boolean corrupted) {
        if (corrupted) {
            corruptedRiteEffect(world, pos);
        } else {
            riteEffect(world, pos);
        }
    }

    public void riteEffect(World world, BlockPos pos) {

    }

    public void corruptedRiteEffect(World world, BlockPos pos) {

    }

    public <T extends LivingEntity> ArrayList<T> getNearbyEntities(Class<T> clazz, World world, BlockPos pos, boolean isCorrupted)
    {
        return (ArrayList<T>) world.getEntitiesWithinAABB(clazz, new AxisAlignedBB(pos).grow(range(isCorrupted)));
    }
    public ArrayList<BlockPos> getNearbyBlocks(Class<?> clazz, World world, BlockPos pos, boolean isCorrupted)
    {
        return MalumHelper.getBlocks(pos, range(isCorrupted), p -> clazz.isInstance(world.getBlockState(p).getBlock()));
    }
    public ArrayList<BlockPos> getNearbyBlocksUnderBase(Class<?> clazz, World world, BlockPos pos, boolean isCorrupted)
    {
        return MalumHelper.getPlaneOfBlocks(pos.down(), range(isCorrupted), p -> clazz.isInstance(world.getBlockState(p).getBlock()));
    }
}
