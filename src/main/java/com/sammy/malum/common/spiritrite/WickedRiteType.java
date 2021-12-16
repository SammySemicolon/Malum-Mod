package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class WickedRiteType extends MalumRiteType
{
    public WickedRiteType()
    {
        super("wicked_rite", false, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public void riteEffect(Level level, BlockPos pos)
    {
        if (!level.isClientSide) {
            getNearbyEntities(LivingEntity.class, level, pos, false).forEach(e -> {
                if (e.getHealth() > 2.5f) {
                    e.hurt(DamageSourceRegistry.VOODOO, 2);
                }
            });
        }
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            getNearbyEntities(LivingEntity.class, level, pos, true).forEach(e -> {
                if (e.getHealth() <= 2.5f) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(WICKED_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                    e.hurt(DamageSourceRegistry.FORCED_SHATTER, 10f);
                }
            });
        }
    }
}