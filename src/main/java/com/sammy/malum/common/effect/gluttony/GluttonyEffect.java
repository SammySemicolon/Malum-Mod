package com.sammy.malum.common.effect.gluttony;

import com.sammy.malum.*;
import com.sammy.malum.common.data.listener.spirit.EntitySpiritDropData;
import com.sammy.malum.common.entity.activator.gluttony.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.toolkit.enchanting.LodestoneEnchantmentEffectCommonsHelper;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;

import java.util.function.*;

import static net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable.Result.DO_NOT_APPLY;

public class GluttonyEffect extends MobEffect {

    public GluttonyEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
        var id = MalumMod.malumPath("gluttony");
        addAttributeModifier(WaywardAttributeTypes.MAGIC_PROFICIENCY, id, 0.05f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            player.causeFoodExhaustion(0.005f * (amplifier + 1));
        }
        return true;
    }

    public static void onDeath(LivingDeathEvent event) {
        var source = event.getSource();
        if (!(source.getEntity() instanceof LivingEntity attacker)) {
            return;
        }
        var instance = attacker.getEffect(MalumMobEffects.GLUTTONY);
        if (instance == null) {
            return;
        }
        var target = event.getEntity();
        int spawnedLocusts = (instance.amplifier + 1) * 2;
        if (spawnLocusts(attacker, target, spawnedLocusts)) {
            attacker.removeEffect(MalumMobEffects.GLUTTONY);
        }
    }

    public static boolean spawnLocusts(LivingEntity attacker, LivingEntity target, int amount) {
        var level = target.level();
        var random = level.random;

        var area = target.getBoundingBox().inflate(8f, 3f, 8f);
        var predicate = LodestoneEnchantmentEffectCommonsHelper.attackPredicate(attacker).and(t -> !t.isDeadOrDying() && attacker.hasLineOfSight(t));
        var targets = level.getEntitiesOfClass(LivingEntity.class, area, predicate);

        if (targets.isEmpty()) {
            return false;
        }
        LivingEntity highestWicked = null;
        if (!attacker.equals(target) && target instanceof Player && !target.isDeadOrDying()) {
            highestWicked = target;
        }
        if (highestWicked != null) {
            int wickedAmount = -1;
            for (LivingEntity otherTarget : targets) {
                int weight = EntitySpiritDropData.getSpiritData(otherTarget).map(e -> e.countSpirit(MalumSpiritTypes.WICKED_SPIRIT)).orElse(0);
                if (weight > wickedAmount) {
                    highestWicked = otherTarget;
                    wickedAmount = weight;
                }
            }
        }
        if (CurioHelper.hasCurioEquipped(attacker, MalumContent.Gear.RING_OF_SWARMING_ROT.get())) {
            float multiplier = 1 + random.nextFloat();
            amount = Mth.ceil(amount * multiplier);
        }
        float velocity = 0.7f;
        for (int i = 0; i < amount; i++) {
            var propagationTarget = highestWicked != null ? highestWicked : targets.get(random.nextInt(targets.size()));
            var velocityVector = new Vec3(
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -velocity, velocity),
                    Easing.QUAD_IN_OUT.asWeighedRandom(random, velocity / 2f, velocity),
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -velocity, velocity)
            );
            var position = target.position().add(0, target.getBbHeight() * 0.5f, 0);
            var locust = new GluttonyDamageActivator(level, attacker.getUUID(), 3, propagationTarget.getUUID(), position, velocityVector);
            level.addFreshEntity(locust);
        }
        return true;
    }

    public static void canApplyPotion(MobEffectEvent.Applicable event) {
        var instance = event.getEffectInstance();
        if (instance == null) {
            return;
        }
        var entity = event.getEntity();
        if (instance.getEffect().equals(MobEffects.HUNGER)) {
            if (hasAnyGluttony(entity)) {
                event.setResult(DO_NOT_APPLY);
            }
        }
    }

    public static void removeExistingHunger(MobEffectEvent.Added event) {
        var entity = event.getEntity();
        if (entity.hasEffect(MobEffects.HUNGER)) {
            if (hasAnyGluttony(entity)) {
                entity.removeEffect(MobEffects.HUNGER);
            }
        }
    }

    public static boolean hasAnyGluttony(LivingEntity entity) {
        return entity.hasEffect(MalumMobEffects.GLUTTONY) || entity.hasEffect(MalumMobEffects.TRIAL_OF_FAITH) || entity.hasEffect(MalumMobEffects.DESPERATE_NEED);
    }

    public static GluttonyEffectProperties applyGluttony(LivingEntity target, Consumer<GluttonyEffectProperties> gluttonyBuilder) {
        var properties = getGluttonyEffectProperties(target, gluttonyBuilder);
        properties.apply(target);
        return properties;
    }

    public static GluttonyEffectProperties getGluttonyEffectProperties(LivingEntity target, Consumer<GluttonyEffectProperties> gluttonyBuilder) {
        var properties = createGluttony();
        gluttonyBuilder.accept(properties);
        var event = new ModifyGluttonyPropertiesEvent(target, properties);
        ItemEventHandler.getEventResponders(target).forEach(lookup -> lookup.run(IMalumEventResponder.class,
                (eventResponderItem, stack) -> eventResponderItem.modifyGluttonyPropertiesEvent(event, target)));
        NeoForge.EVENT_BUS.post(event);
        return event.getProperties();
    }

    public static GluttonyEffectProperties createGluttony() {
        return new GluttonyEffectProperties();
    }

    @SuppressWarnings("UnusedReturnValue")
    public static final class GluttonyEffectProperties {

        private Holder<MobEffect> effectType = MalumMobEffects.GLUTTONY;
        private int duration = 200;
        private int initialAmplifier = 1;
        private int amplifierGain;
        private int amplifierLimit = -1;

        private GluttonyEffectProperties() {
        }

        public void apply(LivingEntity target) {
            var effectType = getEffectType();
            var effectInstance = target.getEffect(effectType);
            if (effectInstance == null) {
                int initialDuration = getDuration();
                int initialAmplifier = getInitialAmplifier() - 1;
                if (initialDuration <= 0) {
                    return;
                }
                target.addEffect(new MobEffectInstance(effectType, initialDuration, initialAmplifier, true, true, true));
            } else {
                int amplifierLimit = getAmplifierLimit() - 1;
                int amplifierGain = getAmplifierGain();
                if (amplifierGain > 0) {
                    effectInstance.duration = getDuration();
                    EntityHelper.amplifyEffect(effectInstance, target, amplifierGain, amplifierLimit);
                }
            }
        }

        public Holder<MobEffect> getEffectType() {
            return effectType;
        }

        public GluttonyEffectProperties replaceEffectType(Holder<MobEffect> effectType) {
            this.effectType = effectType;
            return this;
        }

        public GluttonyEffectProperties setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public GluttonyEffectProperties scaleDuration(float scalar) {
            this.duration = Mth.floor(duration * scalar);
            return this;
        }

        public GluttonyEffectProperties setInitialAmplifier(int initialAmplifier) {
            this.initialAmplifier = initialAmplifier;
            return this;
        }

        public GluttonyEffectProperties setAmplifierGain(int amplifierGain) {
            this.amplifierGain = amplifierGain;
            return this;
        }

        public GluttonyEffectProperties setAmplifierLimit(int amplifierLimit) {
            this.amplifierLimit = amplifierLimit;
            return this;
        }

        public GluttonyEffectProperties scaleInitialAmplifier(float scalar) {
            this.initialAmplifier = Mth.floor(initialAmplifier * scalar);
            return this;
        }

        public GluttonyEffectProperties scaleAmplifierGain(float scalar) {
            this.amplifierGain = Mth.floor(amplifierGain * scalar);
            return this;
        }

        public GluttonyEffectProperties scaleAmplifierLimit(float scalar) {
            if (amplifierLimit == -1) {
                return this;
            }
            this.amplifierLimit = Mth.floor(amplifierLimit * scalar);
            return this;
        }

        public int getDuration() {
            return duration;
        }

        public int getInitialAmplifier() {
            int limit = getAmplifierLimit();
            return limit == -1 ? initialAmplifier : Math.min(initialAmplifier, limit);
        }

        public int getAmplifierGain() {
            return amplifierGain;
        }

        public int getAmplifierLimit() {
            return amplifierLimit;
        }
    }
}