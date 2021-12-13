package com.sammy.malum.core.systems.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

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

    public int range() {
        return defaultRange();
    }

    public int defaultInterval() {
        return 20;
    }

    public int interval() {
        return defaultInterval();
    }

    public void executeRite(ServerWorld world, BlockPos pos, boolean corrupted) {
        if (corrupted) {
            corruptedRiteEffect(world, pos);
        } else {
            riteEffect(world, pos);
        }
    }

    public void riteEffect(ServerWorld world, BlockPos pos) {

    }

    public void corruptedRiteEffect(ServerWorld world, BlockPos pos) {

    }

    public <T extends LivingEntity> ArrayList<T> getNearbyEntities(Class<T> clazz, ServerWorld world, BlockPos pos)
    {
        return (ArrayList<T>) world.getEntitiesWithinAABB(clazz, new AxisAlignedBB(pos).grow(range()));
    }
    public ArrayList<BlockPos> getNearbyBlocks(Class<?> clazz, ServerWorld world, BlockPos pos)
    {
        return MalumHelper.getBlocks(pos, range(), p -> clazz.isInstance(world.getBlockState(p).getBlock()));
    }
}
