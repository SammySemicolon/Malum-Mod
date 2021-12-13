package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.network.packets.particle.BurstParticlePacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class EarthenRiteType extends MalumRiteType
{
    public EarthenRiteType()
    {
        super("earthen_rite", false, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public void riteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(PlayerEntity.class, world, pos, false).forEach(e -> e.addPotionEffect(new EffectInstance(EffectRegistry.EARTHEN_AURA.get(), 100, 1)));
    }

    @Override
    public void corruptedRiteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(PlayerEntity.class, world, pos, false).forEach(e -> e.addPotionEffect(new EffectInstance(EffectRegistry.CORRUPTED_EARTHEN_AURA.get(), 100, 1)));
    }
}
