package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.entity.MajorEntityEffectParticlePacket;
import com.sammy.malum.common.packets.particle.entity.MinorEntityEffectParticlePacket;
import com.sammy.malum.core.systems.rites.EntityAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.SACRED_SPIRIT;
import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;
import static net.minecraft.world.entity.ai.goal.EatBlockGoal.IS_TALL_GRASS;

public class SacredRiteType extends MalumRiteType {

    public static final Map<Class<? extends Animal>, SacredRiteEntityActor<?>> ACTORS = Util.make(new HashMap<>(), m -> {
        m.put(Sheep.class, new SacredRiteEntityActor<>(Sheep.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Sheep sheep) {
                if (sheep.getRandom().nextInt(sheep.isBaby() ? 5 : 25) == 0) {
                    BlockPos blockpos = sheep.blockPosition();
                    if (IS_TALL_GRASS.test(sheep.level.getBlockState(blockpos)) || sheep.level.getBlockState(blockpos.below()).is(Blocks.GRASS_BLOCK)) {
                        EatBlockGoal goal = sheep.eatBlockGoal;
                        goal.start();
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> sheep), new MajorEntityEffectParticlePacket(SACRED_SPIRIT.getColor(), sheep.getX(), sheep.getY() + sheep.getBbHeight() / 2f, sheep.getZ()));
                    }
                }
            }
        });
        m.put(Bee.class, new SacredRiteEntityActor<>(Bee.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Bee bee) {
                Bee.BeePollinateGoal goal = bee.beePollinateGoal;
                if (goal.canBeeUse()) {
                    goal.successfulPollinatingTicks += 40;
                    goal.tick();
                    MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> bee), new MinorEntityEffectParticlePacket(SACRED_SPIRIT.getColor(), bee.getX(), bee.getY() + bee.getBbHeight() / 2f, bee.getZ()));
                }
            }
        });

        m.put(Chicken.class, new SacredRiteEntityActor<>(Chicken.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Chicken chicken) {
                chicken.eggTime -= 80;
                MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> chicken), new MinorEntityEffectParticlePacket(SACRED_SPIRIT.getColor(), chicken.getX(), chicken.getY() + chicken.getBbHeight() / 2f, chicken.getZ()));
            }
        });
    });

    public SacredRiteType() {
        super("sacred_rite", ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new EntityAffectingRiteEffect() {
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, LivingEntity.class).forEach(e -> {
                    if (e.getHealth() < e.getMaxHealth() * 0.75f) {
                        e.heal(2);
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MinorEntityEffectParticlePacket(getEffectSpirit().getColor(), e.getX(), e.getY() + e.getBbHeight() / 2f, e.getZ()));
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
                getNearbyEntities(totemBase, Animal.class).forEach(e -> {
                    if (e.getAge() < 0) {
                        if (totemBase.getLevel().random.nextFloat() <= 0.04f) {
                            MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MajorEntityEffectParticlePacket(getEffectSpirit().getColor(), e.getX(), e.getY() + e.getBbHeight() / 2f, e.getZ()));
                            e.ageUp(25);
                        }
                    }
                    if (ACTORS.containsKey(e.getClass())) {
                        SacredRiteEntityActor<? extends Animal> sacredRiteEntityActor = ACTORS.get(e.getClass());
                        sacredRiteEntityActor.tryAct(totemBase, e);
                    }
                });
            }
        };
    }

    public static abstract class SacredRiteEntityActor<T extends Animal> {
        public final Class<T> targetClass;

        public SacredRiteEntityActor(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        @SuppressWarnings("unchecked")
        public final void tryAct(TotemBaseBlockEntity totemBaseBlockEntity, Animal animal) {
            if (targetClass.isInstance(animal)) {
                act(totemBaseBlockEntity, (T) animal);
            }
        }

        public abstract void act(TotemBaseBlockEntity totemBaseBlockEntity, T entity);
    }
}
