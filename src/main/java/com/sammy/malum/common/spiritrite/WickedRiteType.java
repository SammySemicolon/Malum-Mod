package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.registry.content.SpiritRiteRegistry;
import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import com.sammy.malum.network.packets.particle.BurstParticlePacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class WickedRiteType extends MalumRiteType
{
    public WickedRiteType()
    {
        super("wicked_rite", false, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public void riteEffect(ServerWorld world, BlockPos pos)
    {
        getNearbyEntities(LivingEntity.class, world, pos, false ).forEach(e ->{
            if (e.getHealth() > 2.5f)
            {
                e.attackEntityFrom(DamageSourceRegistry.VOODOO, 2);
            }
        });
    }

    @Override
    public void corruptedRiteEffect(ServerWorld world, BlockPos pos) {
        getNearbyEntities(LivingEntity.class, world, pos, true).forEach(e ->{
            if (e.getHealth() <= 2.5f)
            {
                e.attackEntityFrom(DamageSourceRegistry.FORCED_SHATTER, 10f);
            }
        });
    }
}