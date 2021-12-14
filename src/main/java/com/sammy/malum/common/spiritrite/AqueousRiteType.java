package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class AqueousRiteType extends MalumRiteType {
    public AqueousRiteType() {
        super("aqueous_rite", false, ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }

    @Override
    public void riteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyEntities(PlayerEntity.class, world, pos, false).forEach(e -> {
                if (e.getActivePotionEffect(EffectRegistry.AQUEOUS_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(AQUEOUS_SPIRIT_COLOR, e.getPosition().getX(), e.getPosition().getY() + e.getHeight() / 2f, e.getPosition().getZ()));
                }
                e.addPotionEffect(new EffectInstance(EffectRegistry.AQUEOUS_AURA.get(), 100, 1));
            });
        }
    }

    @Override
    public void corruptedRiteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyEntities(PlayerEntity.class, world, pos, false).forEach(e -> {
                if (e.getActivePotionEffect(Effects.WATER_BREATHING) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(AQUEOUS_SPIRIT_COLOR, e.getPosition().getX(), e.getPosition().getY() + e.getHeight() / 2f, e.getPosition().getZ()));
                }
                e.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 200, 0));
            });
        }
    }
}