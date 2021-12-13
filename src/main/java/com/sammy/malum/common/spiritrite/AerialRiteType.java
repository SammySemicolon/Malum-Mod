package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.network.packets.particle.BurstParticlePacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class AerialRiteType extends MalumRiteType
{
    public AerialRiteType()
    {
        super("aerial_rite", false, ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public void riteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(PlayerEntity.class, world, pos, false).forEach(e -> e.addPotionEffect(new EffectInstance(EffectRegistry.AERIAL_AURA.get(), 100, 1)));
    }

    @Override
    public void corruptedRiteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(PlayerEntity.class, world, pos, true).forEach(e -> e.addPotionEffect(new EffectInstance(EffectRegistry.CORRUPTED_AERIAL_AURA.get(), 200, 1)));
    }
}
