package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.setup.content.potion.EffectRegistry;
import com.sammy.malum.core.systems.rites.EntityAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.rites.PotionRiteEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.setup.server.PacketRegistry.INSTANCE;

public class SacredRiteType extends MalumRiteType {
    public SacredRiteType() {
        super("sacred_rite", ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new EntityAffectingRiteEffect() {
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, Animal.class).forEach(e -> {
                    if (e.getHealth() < e.getMaxHealth() / 2f) {
                        e.heal(2);
                    }
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
                if (totemBase.getLevel().isClientSide()) {
                    return;
                }
                getNearbyEntities(totemBase, Animal.class).forEach(e -> {
                    if (e.getAge() < 0) {
                        if (totemBase.getLevel().random.nextFloat() <= 0.04f) {
                            INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(getSpirit().getColor(), e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                            e.ageUp(25);
                        }
                    } else if (e instanceof Bee bee) {
                        Bee.BeePollinateGoal goal = bee.beePollinateGoal;
                        if (goal.canBeeUse()) {
                            bee.beePollinateGoal.successfulPollinatingTicks += 20;
                            bee.beePollinateGoal.tick();
                        }
                    }
                });
            }
        };
    }
}