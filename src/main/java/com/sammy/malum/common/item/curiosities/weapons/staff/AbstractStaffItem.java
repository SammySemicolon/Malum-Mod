package com.sammy.malum.common.item.curiosities.weapons.staff;

import com.sammy.malum.common.entity.bolt.AbstractStaffBoltProjectile;
import com.sammy.malum.common.item.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.handlers.enchantment.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.enchantment.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;
import team.lodestar.lodestone.modules.toolkit.item.*;
import team.lodestar.wayward_attributes.WaywardTags;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;


public abstract class AbstractStaffItem extends LodestoneCombatItem implements IMalumEventResponderBody {

    public AbstractStaffItem(Tier tier, float magicDamage, float chargeRate, int chargeCapacity, LodestoneItemProperties properties) {
        this(tier, 1f, -3f, magicDamage, chargeRate, chargeCapacity, properties);
    }
    public AbstractStaffItem(Tier tier, float attackDamage, float attackSpeed, float magicDamage, float chargeRate, int chargeCapacity, LodestoneItemProperties properties) {
        super(tier, attackDamage, attackSpeed, properties.mergeAttributes(
                ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID,  attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(WaywardAttributeTypes.MAGIC_DAMAGE, new AttributeModifier(WaywardAttributeTypes.BASE_MAGIC_DAMAGE, magicDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(MalumAttributes.CHARGE_CAPACITY, new AttributeModifier(MalumAttributes.BASE_CHARGE_CAPACITY, chargeCapacity, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(MalumAttributes.CHARGE_DURATION, new AttributeModifier(MalumAttributes.BASE_CHARGE_DURATION, chargeRate, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build()));
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void spawnChargeParticles(Level pLevel, LivingEntity pLivingEntity, Vec3 pos, ItemStack pStack, float chargePercentage);

    public abstract int getProjectileCount(Level level, LivingEntity livingEntity, float chargePercentage);

    public abstract void fireProjectile(LivingEntity player, ItemStack stack, Level level, InteractionHand hand, int count);

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker instanceof ServerPlayer player && event.getSource().is(WaywardTags.DamageTypeTags.CAN_TRIGGER_MAGIC_DAMAGE)) {
            var level = player.serverLevel();
            SoundPlayer.create(MalumGearSoundEvents.STAFF_STRIKES).volume(2f).pitch(0.85f, 1.25f).play(target, attacker.getSoundSource());
            MalumParticleEffectTypes.STAFF_SLAM.createEffect()
                    .originatesFrom(attacker)
                    .targets(target)
                    .color(stack)
                    .forwardOffset(1.4f).upwardOffset(0.3f)
                    .spawn(level);
            if (EnchantmentKeys.getEnchantmentLevel(level, EnchantmentKeys.REPLENISHING, stack) > 0) {
                ReplenishingHandler.triggerReplenishing(event.getSource(), attacker, stack);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        float duration = (float) pPlayer.getAttributes().getValue(MalumAttributes.CHARGE_DURATION) * 20;
        if (!pPlayer.getAbilities().instabuild) {
            var data = pPlayer.getData(MalumAttachmentTypes.STAFF_ABILITIES);
            if (!data.canUseStaff(pPlayer)) {
                return InteractionResultHolder.fail(itemstack);
            }
        }
        if (pLevel instanceof ServerLevel serverLevel) {
            if (duration <= 0) {
                shoot(itemstack, serverLevel, pPlayer, getProjectileCount(pLevel, pPlayer, 1f));
            }
        }
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        int useDuration = getUseDuration(pStack, pLivingEntity);
        float duration = (float) pLivingEntity.getAttributes().getValue(MalumAttributes.CHARGE_DURATION) * 20;
        if (duration <= 0) {
            pLivingEntity.releaseUsingItem();
            return;
        }

        float delta = Math.min(duration, useDuration - pRemainingUseDuration) / duration;
        final InteractionHand hand = pLivingEntity.getUsedItemHand();
        if (pLevel.isClientSide) {
            Vec3 pos = getProjectileSpawnPos(pLivingEntity, hand, 1.5f, 0.6f);
            spawnChargeParticles(pLevel, pLivingEntity, pos, pStack, delta);
            return;
        }
        int fullyCharged = useDuration - Mth.ceil(duration);
        if (pRemainingUseDuration == fullyCharged) {
            float pitch = Mth.nextFloat(pLevel.random, 1.6f, 1.8f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), MalumGearSoundEvents.STAFF_CHARGED.get(), SoundSource.PLAYERS, 1.25f, pitch);
            if (GeasEffectHandler.hasGeas(pLivingEntity, MalumGeasEffectTypes.OATH_OF_THE_OVEREAGER_FIST)) {
                if (pLivingEntity instanceof Player player) {
                    player.getCooldowns().addCooldown(this, 5);
                }
                pLivingEntity.releaseUsingItem();
            }
            return;
        }
        if (pRemainingUseDuration > fullyCharged && pRemainingUseDuration % 5 == 0) {
            float pitch = delta + Mth.nextFloat(pLevel.random, 0.6f, 0.7f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), MalumGearSoundEvents.STAFF_POWERS_UP.get(), SoundSource.PLAYERS, 0.75f, pitch);
            return;
        }
        if (pRemainingUseDuration % 5 == 0) {
            float pitch = Mth.nextFloat(pLevel.random, 0.3f, 0.4f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), MalumGearSoundEvents.STAFF_POWERS_UP.get(), SoundSource.PLAYERS, 0.5f, pitch);
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        float duration = (float) pLivingEntity.getAttributes().getValue(MalumAttributes.CHARGE_DURATION) * 20;
        float delta = Math.min(duration, getUseDuration(pStack, pLivingEntity) - pTimeCharged) / duration;
        int projectileCount = getProjectileCount(pLevel, pLivingEntity, delta);
        if (projectileCount > 0) {
            if (pLevel instanceof ServerLevel serverLevel) {
                shoot(pStack, serverLevel, pLivingEntity, projectileCount);
            }
            return;
        }
        float pitch = Mth.nextFloat(pLevel.random, 0.5f, 0.8f);
        pLevel.playSound(null, pLivingEntity.blockPosition(), MalumGearSoundEvents.STAFF_SIZZLES_OUT.get(), SoundSource.PLAYERS, 0.5f, pitch);
    }

    public void shoot(ItemStack stack, ServerLevel level, LivingEntity entity, int projectileCount) {
        InteractionHand hand = entity.getUsedItemHand();
        float magicDamage = (float) entity.getAttributes().getValue(WaywardAttributeTypes.MAGIC_DAMAGE);
        if (magicDamage == 0) {
            float pitch = Mth.nextFloat(level.random, 0.5f, 0.8f);
            level.playSound(null, entity.blockPosition(), MalumGearSoundEvents.STAFF_SIZZLES_OUT.get(), SoundSource.PLAYERS, 0.5f, pitch);
            entity.swing(hand, true);
            return;
        }
        for (int i = 0; i < projectileCount; i++) {
            fireProjectile(entity, stack, level, hand, i);
        }
        if (entity instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.hurtAndBreak(2, player, EquipmentSlot.MAINHAND);
                var data = player.getData(MalumAttachmentTypes.STAFF_ABILITIES);
                data.consumeStaffCharge(player);
            }
            player.swing(hand, true);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public Vec3 getProjectileSpawnPos(LivingEntity player, InteractionHand hand, float distance, float spread) {
        int angle = hand == InteractionHand.MAIN_HAND ? 225 : 90;
        double radians = Math.toRadians(angle - player.yHeadRot);
        return player.position().add(player.getLookAngle().scale(distance)).add(spread * Math.sin(radians), player.getBbHeight() * 0.9f, spread * Math.cos(radians));
    }

    public <T extends AbstractStaffBoltProjectile> T fireProjectile(LivingEntity owner, InteractionHand hand, BoltFactory<T> factory, float velocity, float pitchOffset, float magicDamage, int spawnDelay) {
        var level = owner.level();
        var pos = getProjectileSpawnPos(owner, hand, 0.5f, 0.5f);
        var entity = factory.makeBolt(level);
        entity.setPos(pos);
        boolean isHoming = GeasEffectHandler.hasGeas(owner, MalumGeasEffectTypes.OATH_OF_THE_OVERKEEN_EYE);
        entity.shootFromStaff(owner, owner.getXRot(), owner.getYRot(), -pitchOffset, velocity, 0F);
        entity.setData(owner, magicDamage, spawnDelay, isHoming);
        return entity;
    }

    public interface BoltFactory<T> {
        T makeBolt(Level level);
    }
}
