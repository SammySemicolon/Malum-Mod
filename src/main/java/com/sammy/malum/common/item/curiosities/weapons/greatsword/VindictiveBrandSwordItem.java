package com.sammy.malum.common.item.curiosities.weapons.greatsword;

import com.sammy.malum.*;
import com.sammy.malum.common.data.attachment.gear.*;
import com.sammy.malum.common.data.component.gear.*;
import com.sammy.malum.common.entity.activator.vindictive_brand.ResentmentRitualActivator;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.weapons.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.worldevent.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.common.extensions.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.*;
import team.lodestar.lodestone.modules.toolkit.enchanting.*;
import team.lodestar.lodestone.modules.toolkit.item.*;
import team.lodestar.lodestone.modules.toolkit.item.tools.*;
import team.lodestar.lodestone.modules.toolkit.sound.*;
import team.lodestar.lodestone.modules.toolkit.worldevent.*;
import team.lodestar.wayward_attributes.core.registry.*;

import java.util.function.*;

import static team.lodestar.wayward_attributes.tweaks.SweepAttackTweaks.*;

public class VindictiveBrandSwordItem extends LodestoneSwordItem implements IMalumEventResponder, ICustomMeleeDamageTypeItem, ISpiritAffiliatedItem {

    public static final ResourceLocation BASE_INTERACTION_RANGE = MalumMod.malumPath("vindictive_brand.base_entity_interaction_range");

    public static final int MAX_STACKS = 10;
    public static final int UNSEAL_DURATION = 300;

    public VindictiveBrandSwordItem(LodestoneItemProperties properties) {
        super(MalumItemTiers.RELIC, 5f, 1f, properties.mergeAttributes(ItemAttributeModifiers.builder()
                .add(Attributes.SWEEPING_DAMAGE_RATIO, new AttributeModifier(BASE_SWEEP_DAMAGE, 1f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(WaywardAttributeTypes.SWEEPING_DAMAGE_RADIUS, new AttributeModifier(BASE_SWEEP_RADIUS, 3f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_INTERACTION_RANGE, 0.5f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build()));
    }

    public static ResourceLocation getBaseId(IAttributeExtension attribute) {
        if (attribute.equals(Attributes.ENTITY_INTERACTION_RANGE.value())) {
            return BASE_INTERACTION_RANGE;
        }
        return null;
    }

    public static float getUnsealedState(ItemStack stack) {
        return stack.has(MalumDataComponents.VINDICTIVE_BRAND_UNLEASHED) ? 1 : 0;
    }

    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            player.getExistingData(MalumAttachmentTypes.VINDICTIVE_BRAND_DASH_DATA).ifPresent(d -> d.tickData(player));
        }
    }

    public static void triggerDashAttack(ServerLevel level, Player player, ItemStack weapon) {
        var random = level.random;
        float baseDamage = (float) player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);

        float range = 5f;
        var area = player.getBoundingBox().inflate(range, 2f, range);

        var physicalDamageType = DamageTypeHelper.create(level, MalumDamageTypes.VINDICTIVE_BRAND_SWEEP, player);
        var predicate = dashAttackDamagePredicate(player, range);
        for (Entity target : level.getEntitiesOfClass(LivingEntity.class, area, predicate)) {
            target.invulnerableTime = 0;
            target.hurt(physicalDamageType, baseDamage);
        }

        MalumNetworkedWeaponParticleEffectType<?> particleType = MalumParticleEffectTypes.VINDICTIVE_BRAND_DASH_CLEAVE;
        var sound = MalumGearSoundEvents.VINDICTIVE_BRAND_DASH_CLEAVE;

        if (weapon.has(MalumDataComponents.VINDICTIVE_BRAND_UNLEASHED)) {
            particleType = MalumParticleEffectTypes.VINDICTIVE_BRAND_UNLEASHED_DASH_CLEAVE;
            sound = MalumGearSoundEvents.VINDICTIVE_BRAND_UNLEASHED_DASH_CLEAVE;
        }

        particleType.createEffect()
                .originatesFrom(player)
                .horizontalDeviation(0.5f)
                .color(weapon)
                .upwardOffset(-0.8f)
                .mirroredRandomly(random)
                .spawn(level);

        SoundPlayer.create(sound).pitchVariance(0.2f).play(player);
    }

    public static Predicate<LivingEntity> dashAttackDamagePredicate(LivingEntity attacker, float range) {
        return LodestoneEnchantmentEffectCommonsHelper.attackPredicate(attacker).and(e -> attacker.distanceTo(e) <= range);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(MalumMobEffects.INSATIABLE_VINDICATION)) {
                stack.set(MalumDataComponents.VINDICTIVE_BRAND_UNLEASHED, VindictiveBrandDataComponent.UNIT);
            }
            else {
                stack.remove(MalumDataComponents.VINDICTIVE_BRAND_UNLEASHED);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var cooldowns = player.getCooldowns();
        if (!cooldowns.isOnCooldown(this)) {
            var stack = player.getItemInHand(usedHand);
            boolean isEmpowered = stack.has(MalumDataComponents.VINDICTIVE_BRAND_UNLEASHED);
            var cooldown = isEmpowered ? 30 : 100;
            cooldowns.addCooldown(this, cooldown);
            player.setData(MalumAttachmentTypes.VINDICTIVE_BRAND_DASH_DATA, new VindictiveBrandDashData(player, 5));
            SoundPlayer.create(MalumGearSoundEvents.VINDICTIVE_BRAND_DASH).pitchVariance(0.2f).play(player);
            return InteractionResultHolder.success(stack);
        }
        return super.use(level, player, usedHand);
    }

    public static void applyResentment(LivingEntity target) {
        var resentment = MalumMobEffects.BURROWING_RESENTMENT;
        var effect = target.getEffect(resentment);
        if (effect == null) {
            target.addEffect(new MobEffectInstance(resentment, 120, 0, false, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, target, 1, MAX_STACKS);
            EntityHelper.extendEffect(effect, target, 60, 600);
        }
    }

    public static void sproutResentment(LivingEntity target, LivingEntity owner, int amount) {
        if (owner.hasEffect(MalumMobEffects.INSATIABLE_VINDICATION)) {
            return;
        }
        var level = target.level();
        var random = level.getRandom();
        float velocity = 0.3f;
        var velocityVector = new Vec3(
                Easing.SINE_IN_OUT.asWeighedRandom(random, -velocity, velocity),
                0.3f + Easing.QUAD_IN_OUT.asWeighedRandom(random, velocity / 2f, velocity) * 2,
                Easing.SINE_IN_OUT.asWeighedRandom(random, -velocity, velocity)
        );
        var position = target.position().add(0, target.getBbHeight() * 0.5f, 0);
        var gatheredResentment = new ResentmentRitualActivator(level, owner.getUUID(), amount, position, velocityVector);
        SoundPlayer.create(MalumGearSoundEvents.VINDICTIVE_BRAND_SPROUT_RESENTMENT).pitchVariance(0.2f).play(target, owner.getSoundSource());
        level.addFreshEntity(gatheredResentment);
    }

    public static void progressRitual(LivingEntity target, int stacks) {
        if (target.hasEffect(MalumMobEffects.INSATIABLE_VINDICATION)) {
            return;
        }
        var ritual = MalumMobEffects.UNSEALING_RITUAL;
        var effect = target.getEffect(ritual);
        if (effect == null) {
            effect = new MobEffectInstance(ritual, 1500, stacks, true, true, true);
            target.addEffect(effect);
        } else {
            EntityHelper.amplifyEffect(effect, target, stacks+1, MAX_STACKS);
            EntityHelper.extendEffect(effect, target, 30, 1500);
        }

        if (effect.amplifier == MAX_STACKS) {
            target.removeEffect(ritual);
            target.addEffect(new MobEffectInstance(MalumMobEffects.INSATIABLE_VINDICATION, UNSEAL_DURATION, 0));
        }

        float delta = stacks / (float)MAX_STACKS;
        SoundPlayer.create(MalumGearSoundEvents.VINDICTIVE_BRAND_PROGRESS_RITUAL).pitch(1f + delta * 0.5f).play(target);
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var level = attacker.level();
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        var source = event.getSource();
        if (!source.is(MalumDamageTypes.VINDICTIVE_BRAND_MELEE) && !source.is(MalumDamageTypes.INVERTED_HEART_PROPAGATION)) {
            return;
        }
        MalumNetworkedWeaponParticleEffectType<?> particleType = MalumParticleEffectTypes.VINDICTIVE_BRAND_SLASH;
        var sound = MalumGearSoundEvents.VINDICTIVE_BRAND_SWING;

        if (attacker.hasEffect(MalumMobEffects.INSATIABLE_VINDICATION)) {
            particleType = MalumParticleEffectTypes.VINDICTIVE_BRAND_UNLEASHED_SLASH;
            sound = MalumGearSoundEvents.VINDICTIVE_BRAND_UNLEASHED_SWING;
        }

        var particle = particleType.createEffect()
                .originatesFrom(attacker)
                .targets(target)
                .randomDeviationAngle(attacker.getRandom())
                .horizontalDeviation(0.5f)
                .color(stack)
                .upwardOffset(-0.2f)
                .forwardOffset(0.3f);
        if (source.is(MalumDamageTypes.INVERTED_HEART_PROPAGATION)) {
            particle.tiedToTarget().horizontalOffset(0.2f).horizontalDeviation(Easing.SINE_IN_OUT.asWeighedRandom(attacker.getRandom(), -0.5f, 0.5f)).forwardOffset(-0.8f);
        }

        SoundPlayer.create(sound).volume(1.5f).play(attacker);
        particle.mirroredRandomly(attacker.getRandom()).spawn(serverLevel);
    }

    @Override
    public void outgoingDamageEvent(LivingIncomingDamageEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var source = event.getSource();
        if (!source.is(MalumDamageTypes.VINDICTIVE_BRAND_MELEE) && !source.is(MalumDamageTypes.VINDICTIVE_BRAND_SWEEP) && !source.is(MalumDamageTypes.INVERTED_HEART_PROPAGATION)) {
            return;
        }
        if (attacker.hasEffect(MalumMobEffects.INSATIABLE_VINDICATION)) {
            int slashCount = 4;
            if (target.isAlive()) {
                var level = attacker.level();
                for (int i = 0; i < slashCount; i++) {
                    WorldEventHandler.addWorldEvent(level,
                            new DelayedDamageWorldEvent(target)
                                    .setAttacker(attacker)
                                    .setImpactParticleEffect(MalumParticleEffectTypes.VINDICTIVE_BRAND_EXTRA_SLASH, new MalumNetworkedParticleEffectColorData(MalumSpiritTypes.ELDRITCH_SPIRIT))
                                    .setDamageData(0, 2, (i+1) * 8)
                                    .setMagicDamageType(MalumDamageTypes.VINDICTIVE_BRAND_COMBO)
                                    .setSound(MalumGearSoundEvents.VINDICTIVE_BRAND_EXTRA_SWING, 1.2f, 1.4f, 1.5f));
                }
            }

            return;
        }
        applyResentment(target);
    }

    @Override
    public void incomingDamageEvent(LivingIncomingDamageEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker == null) {
            return;
        }
        var resentment = MalumMobEffects.BURROWING_RESENTMENT;
        var effect = attacker.getEffect(resentment);
        if (effect == null) {
            return;
        }
        var amplifier = effect.amplifier+1;
        var amount = event.getAmount();
        var newAmount = Math.max(amount + amplifier * 0.5f, amount * (1 + amplifier * 0.1f));
        event.setAmount(newAmount);
    }

    @Override
    public void finalizedIncomingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker == null) {
            return;
        }
        var resentment = MalumMobEffects.BURROWING_RESENTMENT;
        var effect = attacker.getEffect(resentment);
        if (effect == null) {
            return;
        }
        var amplifier = effect.amplifier;
        sproutResentment(attacker, target, amplifier);
        attacker.removeEffect(resentment);
    }

    @Override
    public void outgoingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var resentment = MalumMobEffects.BURROWING_RESENTMENT;
        var effect = target.getEffect(resentment);
        if (effect == null) {
            return;
        }
        var amplifier = effect.amplifier;
        sproutResentment(target, attacker, amplifier);
        attacker.removeEffect(resentment);
    }

    @Override
    public ResourceKey<DamageType> getDirectDamageType(Player player, ItemStack weapon) {
        return MalumDamageTypes.VINDICTIVE_BRAND_MELEE;
    }

    @Override
    public ResourceKey<DamageType> getSweepingDamageType(Player player, ItemStack weapon) {
        return MalumDamageTypes.VINDICTIVE_BRAND_SWEEP;
    }

    @Override
    public SpiritLike getDefiningSpiritType(ItemStack stack) {
        if (stack.has(MalumDataComponents.VINDICTIVE_BRAND_UNLEASHED)) {
            return MalumSpiritTypes.ELDRITCH_SPIRIT;
        }
        return null;
    }
}