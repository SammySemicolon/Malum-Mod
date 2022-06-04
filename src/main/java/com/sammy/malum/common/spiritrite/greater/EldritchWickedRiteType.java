package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.setup.content.DamageSourceRegistry;
import com.sammy.malum.core.systems.rites.EntityAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
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
    public MalumRiteEffect getNaturalRiteEffect() {
        return new EntityAffectingRiteEffect() {

            @Override
            public int getRiteEffectRadius() {
                return BASE_RADIUS * 2;
            }

            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, LivingEntity.class).forEach(e -> {
                    e.hurt(DamageSourceRegistry.VOODOO, 5);
                });
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new EntityAffectingRiteEffect() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                if (!level.isClientSide) {
                    ArrayList<Animal> entities = getNearbyEntities(totemBase, Animal.class);
                    if (entities.size() < 30) {
                        return;
                    }
                    int maxKills = entities.size() - 30;
                    for (Animal entity : entities) {
                        if (!entity.isInLove() && entity.getAge() > 0) {
                            entity.hurt(DamageSourceRegistry.VOODOO, entity.getMaxHealth());
                            if (!totemBase.getLevel().isClientSide) {
                                INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new MagicParticlePacket(WICKED_SPIRIT.getColor(), entity.blockPosition().getX(), entity.blockPosition().getY() + entity.getBbHeight() / 2f, entity.blockPosition().getZ()));
                            }
                            maxKills--;
                        }
                        if (maxKills <= 0) {
                            return;
                        }
                    }
                }
            }
        };
    }
}