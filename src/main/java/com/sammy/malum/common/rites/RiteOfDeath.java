package com.sammy.malum.common.rites;

import com.sammy.malum.core.mod_systems.rites.MalumRiteType;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
import com.sammy.malum.network.packets.particle.BurstParticlePacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.mod_content.MalumSpiritTypes.*;
import static com.sammy.malum.network.NetworkManager.INSTANCE;

public class RiteOfDeath extends MalumRiteType
{
    public RiteOfDeath()
    {
        super("rite_of_death", false, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public void executeRite(ServerWorld world, BlockPos pos)
    {
        ArrayList<LivingEntity> entities = (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos).grow(range()));
        if (entities.isEmpty())
        {
            return;
        }
        LivingEntity target = entities.get(world.rand.nextInt(entities.size()));
        target.hurtResistantTime = 0;
        target.attackEntityFrom(SpiritHelper.voodooDamageSource(), 4);
        Vector3d targetPos = target.getPositionVec().add(0, target.getHeight()/2f,0);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), BurstParticlePacket.fromSpirits(targetPos.getX(), targetPos.getY(), targetPos.getZ(), WICKED_SPIRIT));
    }
}