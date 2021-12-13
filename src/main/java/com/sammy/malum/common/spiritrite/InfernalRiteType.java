package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class InfernalRiteType extends MalumRiteType
{
    public InfernalRiteType()
    {
        super("infernal_rite", false, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public void riteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(PlayerEntity.class, world, pos, false).forEach(e -> e.addPotionEffect(new EffectInstance(EffectRegistry.INFERNAL_AURA.get(), 100, 1)));
    }

    @Override
    public void corruptedRiteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(MonsterEntity.class, world, pos, false).forEach(e -> e.setFire(2));
    }
}
