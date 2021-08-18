package com.sammy.malum.common.rites;

import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.mod_systems.rites.MalumRiteType;
import com.sammy.malum.network.packets.particle.BurstParticlePacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.init.MalumSpiritTypes.*;
import static com.sammy.malum.network.NetworkManager.INSTANCE;

public class RiteOfWarding extends MalumRiteType
{
    public RiteOfWarding()
    {
        super("rite_of_warding", false, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public int range()
    {
        return defaultRange()/2;
    }

    @Override
    public void executeRite(ServerWorld world, BlockPos pos)
    {
        ArrayList<PlayerEntity> entities = (ArrayList<PlayerEntity>) world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos).grow(range()));
        entities.forEach(e -> {
            if (e.getActivePotionEffect(MalumEffects.AURA_OF_WARDING.get()) == null)
            {
                Vector3d targetPos = e.getPositionVec().add(0, e.getHeight()/2f,0);

                INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), BurstParticlePacket.fromSpirits(targetPos.x,targetPos.y,targetPos.z, EARTHEN_SPIRIT));
            }
            e.addPotionEffect(new EffectInstance(MalumEffects.AURA_OF_WARDING.get(), 100, 1));
        });
        super.executeRite(world, pos);
    }
}
