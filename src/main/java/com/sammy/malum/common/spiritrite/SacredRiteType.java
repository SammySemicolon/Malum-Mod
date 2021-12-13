package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.SACRED_SPIRIT;

public class SacredRiteType extends MalumRiteType {
    public SacredRiteType() {
        super("sacred_rite", false, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public void riteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(PlayerEntity.class, world, pos).forEach(e -> e.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100, 0)));
    }

    @Override
    public void corruptedRiteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(AnimalEntity.class, world, pos).forEach(e -> {
            if (e.getGrowingAge() < 0) {
                e.addGrowth(2);
            }
        });
    }

    @Override
    public int range() {
        return 4;
    }
}
