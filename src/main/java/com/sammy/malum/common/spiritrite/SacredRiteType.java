package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;
import static net.minecraft.world.entity.ai.goal.EatBlockGoal.*;

public class SacredRiteType extends MalumRiteType {

    public SacredRiteType() {
        super("sacred_rite", ARCANE_SPIRIT.get(), SACRED_SPIRIT.get(), SACRED_SPIRIT.get());
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, LivingEntity.class, e -> !(e instanceof Monster)).forEach(e -> {
                    if (e.getHealth() < e.getMaxHealth()) {
                        e.heal(2);
                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(SACRED_SPIRIT.get().getPrimaryColor()));
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
                            ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(SACRED_SPIRIT.get().getPrimaryColor()));
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
                    if (IS_TALL_GRASS.test(sheep.level.getBlockState(blockpos)) || sheep.level.getBlockState(blockpos.below()).is(Blocks.GRASS_BLOCK)) {
                        EatBlockGoal goal = sheep.eatBlockGoal;
                        goal.start();
                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(sheep, new ColorEffectData(SACRED_SPIRIT.get().getPrimaryColor()));
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
                    ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(bee, new ColorEffectData(SACRED_SPIRIT.get().getPrimaryColor()));
                }
            }
        });

        m.put(Chicken.class, new NourishmentRiteActor<>(Chicken.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Chicken chicken) {
                chicken.eggTime -= 80;
                ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(chicken, new ColorEffectData(SACRED_SPIRIT.get().getPrimaryColor()));
            }
        });
    });

    @Override
    public MalumRiteType setRegistryName(ResourceLocation name) {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return null;
    }

    @Override
    public Class<MalumRiteType> getRegistryType() {
        return null;
    }

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
