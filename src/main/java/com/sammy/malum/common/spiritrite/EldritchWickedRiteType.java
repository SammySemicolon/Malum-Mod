package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class EldritchWickedRiteType extends MalumRiteType {
    public EldritchWickedRiteType() {
        super("eldritch_wicked_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public void riteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(LivingEntity.class, world, pos, false).forEach(e -> {
            if (!(e instanceof PlayerEntity)) {
                e.attackEntityFrom(DamageSourceRegistry.VOODOO, 5);
            }
        });
    }

    @Override
    public void corruptedRiteEffect(ServerWorld world, BlockPos pos) {
        ArrayList<AnimalEntity> entities = getNearbyEntities(AnimalEntity.class, world, pos, true);
        if (entities.size() < 30) {
            return;
        }
        int maxKills = entities.size() - 30;
        for (AnimalEntity entity : entities) {
            if (!entity.isInLove() && entity.getGrowingAge() > 0) {
                entity.attackEntityFrom(DamageSourceRegistry.VOODOO, entity.getMaxHealth());
                maxKills--;
            }
            if (maxKills <= 0) {
                return;
            }
        }
    }
}