package com.sammy.malum.common.item.curiosities.weapons.staff;

import com.google.common.collect.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.*;

public abstract class AbstractStaffItem extends ModCombatItem implements IMalumEventResponderItem {

    public final float chargeDuration;
    public final float magicDamage;

    public AbstractStaffItem(Tier tier, float attackSpeed, int chargeDuration, float magicDamage, Properties builderIn) {
        super(tier, 1,  -2.8f+attackSpeed, builderIn);
        this.chargeDuration = chargeDuration;
        this.magicDamage = magicDamage;
    }
    public AbstractStaffItem(Tier tier, int chargeDuration, float magicDamage, Properties builderIn) {
        this(tier, 0f, chargeDuration, magicDamage, builderIn);
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void spawnChargeParticles(Level pLevel, LivingEntity pLivingEntity, Vec3 pos, ItemStack pStack, float chargePercentage);

    public abstract int getCooldownDuration(Level level, LivingEntity livingEntity);
    public abstract int getProjectileCount(Level level, LivingEntity livingEntity, float chargePercentage);

    public abstract void fireProjectile(LivingEntity player, ItemStack stack, Level level, InteractionHand hand, float chargePercentage, int count);

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(LodestoneAttributes.MAGIC_DAMAGE.get(), new AttributeModifier(LodestoneAttributes.MAGIC_DAMAGE.getId(), magicDamage, AttributeModifier.Operation.ADD_VALUE));
        return builder;
    }

    @Override
    public void hurtEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker instanceof Player player && !(event.getSource().getDirectEntity() instanceof AbstractBoltProjectileEntity)) {
            Level level = player.level();
            MalumScytheItem.spawnSweepParticles(player, ParticleRegistry.STAFF_SLAM_PARTICLE.get());
            level.playSound(null, target.blockPosition(), SoundRegistry.STAFF_STRIKES.get(), attacker.getSoundSource(), 0.75f, Mth.nextFloat(level.random, 0.5F, 1F));
            if (event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC)) {
                ReplenishingEnchantment.replenishStaffCooldown(attacker, stack);
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        final float chargePercentage = Math.min(chargeDuration, getUseDuration(pStack, pLivingEntity) - pTimeCharged) / chargeDuration;
        int projectileCount = getProjectileCount(pLevel, pLivingEntity, chargePercentage);
        if (projectileCount > 0) {
            InteractionHand hand = pLivingEntity.getUsedItemHand();
            if (!pLevel.isClientSide) {

                float magicDamage = (float) pLivingEntity.getAttributes().getValue(LodestoneAttributes.MAGIC_DAMAGE);
                if (magicDamage == 0) {
                    float pitch = Mth.nextFloat(pLevel.random, 0.5f, 0.8f);
                    pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_SIZZLES.get(), SoundSource.PLAYERS, 0.5f, pitch);
                    pLivingEntity.swing(hand, true);
                    return;
                }
                for (int i = 0; i < projectileCount; i++) {
                    fireProjectile(pLivingEntity, pStack, pLevel, hand, chargePercentage, i);
                }
                if (pLivingEntity instanceof Player player) {
                    player.awardStat(Stats.ITEM_USED.get(this));
                    if (!player.getAbilities().instabuild) {
                        pStack.hurtAndBreak(2, player, EquipmentSlot.MAINHAND);
                        final MalumPlayerDataCapability capability = MalumPlayerDataCapability.getCapability(player);
                        if (capability.reserveStaffChargeHandler.chargeCount > 0) {
                            capability.reserveStaffChargeHandler.chargeCount--;
                        }
                        else {
                            player.getCooldowns().addCooldown(this, getCooldownDuration(pLevel, pLivingEntity));
                        }
                    }
                    player.swing(hand, true);
                }
            }
        }
        else {
            float pitch = Mth.nextFloat(pLevel.random, 0.5f, 0.8f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_SIZZLES.get(), SoundSource.PLAYERS, 0.5f, pitch);
        }
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        final int useDuration = getUseDuration(pStack, pLivingEntity);
        final float chargePercentage = Math.min(chargeDuration, useDuration - pRemainingUseDuration) / chargeDuration;
        if (pLevel.isClientSide) {
            InteractionHand hand = pLivingEntity.getUsedItemHand();
            Vec3 pos = getProjectileSpawnPos(pLivingEntity, hand, 1.5f, 0.6f);
            spawnChargeParticles(pLevel, pLivingEntity, pos, pStack, chargePercentage);
        }
        if (pRemainingUseDuration == useDuration - chargeDuration) {
            float pitch = Mth.nextFloat(pLevel.random, 1.2f, 1.6f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_CHARGED.get(), SoundSource.PLAYERS, 1.25f, pitch);
        }
        else if (pRemainingUseDuration > useDuration - chargeDuration && pRemainingUseDuration % 5 == 0) {
            float pitch = 0.25f + chargePercentage + Mth.nextFloat(pLevel.random, 0.2f, 0.6f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_POWERS_UP.get(), SoundSource.PLAYERS, 0.75f, pitch);
        }
        else if (pRemainingUseDuration % 5 == 0) {
            float pitch = Mth.nextFloat(pLevel.random, 0.2f, 0.6f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_POWERS_UP.get(), SoundSource.PLAYERS, 0.5f, pitch);
        }
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.getCooldowns().isOnCooldown(itemstack.getItem())) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
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
}