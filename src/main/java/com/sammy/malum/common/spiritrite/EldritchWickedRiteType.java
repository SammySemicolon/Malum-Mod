package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class EldritchWickedRiteType extends MalumRiteType {
    public EldritchWickedRiteType() {
        super("eldritch_wicked_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public void riteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyEntities(LivingEntity.class, world, pos, false).forEach(e -> {
                if (!(e instanceof PlayerEntity)) {
                    e.attackEntityFrom(DamageSourceRegistry.VOODOO, 5);
                }
            });
        }
    }

    @Override
    public void corruptedRiteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            ArrayList<AnimalEntity> entities = getNearbyEntities(AnimalEntity.class, world, pos, true);
            if (entities.size() < 30) {
                return;
            }
            int maxKills = entities.size() - 30;
            for (AnimalEntity entity : entities) {
                if (!entity.isInLove() && entity.getGrowingAge() > 0) {
                    entity.attackEntityFrom(DamageSourceRegistry.VOODOO, entity.getMaxHealth());
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new MagicParticlePacket(WICKED_SPIRIT_COLOR, entity.getPosition().getX(), entity.getPosition().getY() + entity.getHeight() / 2f, entity.getPosition().getZ()));
                    maxKills--;
                }
                if (maxKills <= 0) {
                    return;
                }
            }
        }
    }
}