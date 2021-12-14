package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class WickedRiteType extends MalumRiteType
{
    public WickedRiteType()
    {
        super("wicked_rite", false, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public void riteEffect(World world, BlockPos pos)
    {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyEntities(LivingEntity.class, world, pos, false).forEach(e -> {
                if (e.getHealth() > 2.5f) {
                    e.attackEntityFrom(DamageSourceRegistry.VOODOO, 2);
                }
            });
        }
    }

    @Override
    public void corruptedRiteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyEntities(LivingEntity.class, world, pos, true).forEach(e -> {
                if (e.getHealth() <= 2.5f) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(WICKED_SPIRIT_COLOR, e.getPosition().getX(), e.getPosition().getY() + e.getHeight() / 2f, e.getPosition().getZ()));
                    e.attackEntityFrom(DamageSourceRegistry.FORCED_SHATTER, 10f);
                }
            });
        }
    }
}