package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.core.systems.rites.EntityAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.SACRED_SPIRIT;
import static net.minecraft.world.entity.ai.goal.EatBlockGoal.IS_TALL_GRASS;

public class SacredRiteType extends MalumRiteType {

    public SacredRiteType() {
        super("sacred_rite", ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, LivingEntity.class, e -> !(e instanceof Monster)).forEach(e -> {
                    if (e.getHealth() < e.getMaxHealth()) {
                        e.heal(2);
                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
                    }
                });
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @SuppressWarnings("DataFlowIssue")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, Animal.class).forEach(e -> {
                    if (e.getAge() < 0) {
                        if (totemBase.getLevel().random.nextFloat() <= 0.04f) {
                            ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
                            e.ageUp(25);
                        }
                    }
                    if (NOURISHMENT_RITE_ACTORS.containsKey(e.getClass())) {
                        NourishmentRiteActor<? extends Animal> nourishmentRiteActor = NOURISHMENT_RITE_ACTORS.get(e.getClass());
                        nourishmentRiteActor.tryAct(totemBase, e);
                    }
                });
            }
        };
    }

    public static final Map<Class<? extends Animal>, NourishmentRiteActor<?>> NOURISHMENT_RITE_ACTORS = Util.make(new HashMap<>(), m -> {
        m.put(Sheep.class, new NourishmentRiteActor<>(Sheep.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Sheep sheep) {
                if (sheep.getRandom().nextInt(sheep.isBaby() ? 5 : 25) == 0) {
                    BlockPos blockpos = sheep.blockPosition();
                    final Level level = sheep.level();
                    if (IS_TALL_GRASS.test(level.getBlockState(blockpos)) || level.getBlockState(blockpos.below()).is(Blocks.GRASS_BLOCK)) {
                        EatBlockGoal goal = sheep.eatBlockGoal;
                        goal.start();
                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(sheep, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
                    }
                }
            }
        });
        m.put(Bee.class, new NourishmentRiteActor<>(Bee.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Bee bee) {
                Bee.BeePollinateGoal goal = bee.beePollinateGoal;
                if (goal.canBeeUse()) {
                    goal.successfulPollinatingTicks += 40;
                    goal.tick();
                    ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(bee, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
                }
            }
        });

        m.put(Chicken.class, new NourishmentRiteActor<>(Chicken.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Chicken chicken) {
                chicken.eggTime -= 80;
                ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(chicken, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
            }
        });
    });

    public static abstract class NourishmentRiteActor<T extends Animal> {
        public final Class<T> targetClass;

        public NourishmentRiteActor(Class<T> targetClass) {
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
