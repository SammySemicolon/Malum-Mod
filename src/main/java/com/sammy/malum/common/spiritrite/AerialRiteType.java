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

public class AerialRiteType extends MalumRiteType
{
    public AerialRiteType()
    {
        super("aerial_rite", false, ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public int range()
    {
        return defaultRange()/2;
    }

    @Override
    public void riteEffect(ServerWorld world, BlockPos pos)
    {
        ArrayList<PlayerEntity> entities = (ArrayList<PlayerEntity>) world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos).grow(range()));
        entities.forEach(e -> {
            if (e.getActivePotionEffect(EffectRegistry.AURA_OF_CELERITY.get()) == null)
            {
                Vector3d targetPos = e.getPositionVec().add(0, e.getHeight()/2f,0);

                INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), BurstParticlePacket.fromSpirits(targetPos.x,targetPos.y,targetPos.z, AERIAL_SPIRIT));
            }
            e.addPotionEffect(new EffectInstance(EffectRegistry.AURA_OF_CELERITY.get(), 100, 1));
        });
        super.riteEffect(world, pos);
    }
}
