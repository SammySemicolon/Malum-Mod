package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class InfernalRiteType extends MalumRiteType {
    public InfernalRiteType() {
        super("infernal_rite", false, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public void riteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyEntities(PlayerEntity.class, world, pos, false).forEach(e -> {
                if (e.getActivePotionEffect(EffectRegistry.INFERNAL_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(INFERNAL_SPIRIT_COLOR, e.getPosition().getX(), e.getPosition().getY() + e.getHeight() / 2f, e.getPosition().getZ()));
                }
                e.addPotionEffect(new EffectInstance(EffectRegistry.INFERNAL_AURA.get(), 100, 1));
            });
        }
    }

    @Override
    public void corruptedRiteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyEntities(LivingEntity.class, world, pos, true).forEach(e -> {
                if (!(e instanceof PlayerEntity)) {
                    if (e.getFireTimer() != 0) {
                        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(INFERNAL_SPIRIT_COLOR, e.getPosition().getX(), e.getPosition().getY() + e.getHeight() / 2f, e.getPosition().getZ()));
                    }
                    e.setFire(2);
                }
            });
        }
    }
}
