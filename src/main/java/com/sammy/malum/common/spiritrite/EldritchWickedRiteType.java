package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.util.math.BlockPos;
import net.minecraft.Level.Level;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class EldritchWickedRiteType extends MalumRiteType {
    public EldritchWickedRiteType() {
        super("eldritch_wicked_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public void riteEffect(Level Level, BlockPos pos) {
        if (MalumHelper.areWeOnServer(Level)) {
            getNearbyEntities(LivingEntity.class, Level, pos, false).forEach(e -> {
                if (!(e instanceof Player)) {
                    e.hurt(DamageSourceRegistry.VOODOO, 5);
                }
            });
        }
    }

    @Override
    public void corruptedRiteEffect(Level Level, BlockPos pos) {
        if (MalumHelper.areWeOnServer(Level)) {
            ArrayList<AnimalEntity> entities = getNearbyEntities(AnimalEntity.class, Level, pos, true);
            if (entities.size() < 30) {
                return;
            }
            int maxKills = entities.size() - 30;
            for (AnimalEntity entity : entities) {
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