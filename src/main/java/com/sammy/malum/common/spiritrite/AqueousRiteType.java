package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class AqueousRiteType extends MalumRiteType
{
    public AqueousRiteType()
    {
        super("aqueous_rite", false, ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }

    @Override
    public void riteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(PlayerEntity.class, world, pos, false).forEach(e -> e.addPotionEffect(new EffectInstance(EffectRegistry.AQUEOUS_AURA.get(), 100, 1)));
    }

    @Override
    public void corruptedRiteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(PlayerEntity.class, world, pos, true).forEach(e -> e.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 200, 0)));
    }
}
