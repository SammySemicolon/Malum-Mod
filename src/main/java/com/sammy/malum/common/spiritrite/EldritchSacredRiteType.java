package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class EldritchSacredRiteType extends MalumRiteType {
    public EldritchSacredRiteType() {
        super("eldritch_sacred_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public void riteEffect(ServerWorld world, BlockPos pos) {
        getNearbyBlocks(IGrowable.class, world, pos).forEach(p -> {
            if (world.rand.nextFloat() <= 0.02f) {
                BlockState state = world.getBlockState(p);
                IGrowable growable = (IGrowable) state.getBlock();
                if (growable.canGrow(world, p, state, false)) {
                    growable.grow(world, world.rand, p, state);
                }
            }
        });
    }

    @Override
    public void corruptedRiteEffect(ServerWorld world, BlockPos pos) {
        ArrayList<AnimalEntity> entities = getNearbyEntities(AnimalEntity.class, world, pos);
        entities.removeIf(e -> e.getGrowingAge() < 0);
        if (entities.size() > 30)
        {
            return;
        }
        entities.forEach(e -> {
            if (e.canFallInLove() && e.getGrowingAge() == 0) {
                if (world.rand.nextFloat() <= 0.05f) {
                    e.setInLove(null);
                }
            }
        });
    }

    @Override
    public int range() {
        return defaultRange()/2;
    }
}
