package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.setup.content.damage.DamageSourceRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.setup.server.PacketRegistry.INSTANCE;

public class EldritchWickedRiteType extends MalumRiteType {
    public EldritchWickedRiteType() {
        super("eldritch_wicked_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange()/2;
    }

    @Override
    public void riteEffect(Level level, BlockPos pos, int height) {
        if (!level.isClientSide) {
            getNearbyEntities(LivingEntity.class, level, pos, false).forEach(e -> {
                if (!(e instanceof Player)) {
                    e.hurt(DamageSourceRegistry.VOODOO, 5);
                }
            });
        }
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos, int height) {
        if (!level.isClientSide) {
            ArrayList<Animal> entities = getNearbyEntities(Animal.class, level, pos, true);
            if (entities.size() < 30) {
                return;
            }
            int maxKills = entities.size() - 30;
            for (Animal entity : entities) {
                if (!entity.isInLove() && entity.getAge() > 0) {
                    entity.hurt(DamageSourceRegistry.VOODOO, entity.getMaxHealth());
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new MagicParticlePacket(WICKED_SPIRIT_COLOR, entity.blockPosition().getX(), entity.blockPosition().getY() + entity.getBbHeight() / 2f, entity.blockPosition().getZ()));
                    maxKills--;
                }
                if (maxKills <= 0) {
                    return;
                }
            }
        }
    }
}