package com.sammy.malum.common.item.curiosities.weapons;

import com.google.common.collect.*;
import com.sammy.malum.common.entity.*;
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
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

public class MagicStaveItem extends MalumStaveItem {

    public final float magicDamage;

    public MagicStaveItem(Tier tier, float attackDamageIn, float attackSpeedIn, float magicDamage, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.magicDamage = magicDamage;
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(LodestoneAttributeRegistry.MAGIC_DAMAGE.get(), new AttributeModifier(LodestoneAttributeRegistry.UUIDS.get(LodestoneAttributeRegistry.MAGIC_DAMAGE), "Weapon magic damage", magicDamage, AttributeModifier.Operation.ADDITION));
        return builder;
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        final int useDuration = getUseDuration(pStack);
        if (pLevel.isClientSide) {
            RandomSource random = pLevel.random;
            InteractionHand hand = pLivingEntity.getUsedItemHand();
            Vec3 pos = getProjectileSpawnPos(pLivingEntity, hand, 1.5f, 0.6f);
            float pct = Math.min(20, useDuration - pRemainingUseDuration) / 20f;
            final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0.25f, 0.5f).setSpinOffset(RandomHelper.randomBetween(random, 0f, 6.28f)).build();
            DirectionalParticleBuilder.create(ParticleRegistry.HEXAGON)
                    .setTransparencyData(GenericParticleData.create(0.6f * pct, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setSpinData(spinData)
                    .setScaleData(GenericParticleData.create(0.3f * pct, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createMainColorData().build())
                    .setLifetime(5)
                    .setDirection(pLivingEntity.getLookAngle().normalize())
                    .setMotion(pLivingEntity.getLookAngle().normalize().scale(0.05f))
                    .enableNoClip()
                    .enableForcedSpawn()
                    .disableCull()
                    .setLifeDelay(2)
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                    .spawn(pLevel, pos.x, pos.y, pos.z)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .spawn(pLevel, pos.x, pos.y, pos.z);
        }
        if (pRemainingUseDuration == useDuration - 20) {
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAVE_CHARGED.get(), SoundSource.PLAYERS, 1f, Mth.nextFloat(pLevel.random, 1f, 1.4f));
        }
        else if (pRemainingUseDuration > useDuration - 20 && pRemainingUseDuration % 5 == 0) {
            float pct = 0.25f+Math.min(20, useDuration - pRemainingUseDuration) / 20f;
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAVE_POWERS_UP.get(), SoundSource.PLAYERS, 0.5f, pct+Mth.nextFloat(pLevel.random, 0.2f, 0.4f));
        }
        else if (pRemainingUseDuration % 5 == 0) {
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAVE_POWERS_UP.get(), SoundSource.PLAYERS, 0.25f, Mth.nextFloat(pLevel.random, 0.2f, 0.6f));

        }

        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pTimeCharged <= getUseDuration(pStack) - 20) {
            InteractionHand hand = pLivingEntity.getUsedItemHand();
            for (int i = 0; i < 3; i++) {
                fireProjectile(pLivingEntity, pStack, pLevel, hand, i);
            }
            if (pLivingEntity instanceof Player player) {
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    pStack.hurtAndBreak(1, player, (p_220009_1_) -> {
                        p_220009_1_.broadcastBreakEvent(hand);
                    });
                    player.getCooldowns().addCooldown(this, 80);
                }
                pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAVE_FIRES.get(), SoundSource.PLAYERS, 0.5f, Mth.nextFloat(pLevel.random, 0.8f, 1.2f));
                player.swing(hand, true);
            }
        }
        else {
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAVE_SIZZLES.get(), SoundSource.PLAYERS, 0.5f, Mth.nextFloat(pLevel.random, 0.5f, 0.8f));
        }
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
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
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public void fireProjectile(LivingEntity player, ItemStack stack, Level level, InteractionHand hand, int count) {
        if (!level.isClientSide) {
            float pitchOffset = 3f + count;
            int spawnDelay = count * 3;
            float velocity = 2.5f + 0.5f * count;
            float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributeRegistry.MAGIC_DAMAGE.get()) * 1.5f;
            Vec3 pos = getProjectileSpawnPos(player, hand, 0.5f, 0.5f);
            HexProjectileEntity entity = new HexProjectileEntity(level, pos.x, pos.y, pos.z);
            entity.setData(player, magicDamage, spawnDelay);
            entity.setItem(stack);

            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), -pitchOffset, velocity, 0F);
            level.addFreshEntity(entity);
        }
    }

    public Vec3 getProjectileSpawnPos(LivingEntity player, InteractionHand hand, float distance, float spread) {
        int angle = hand == InteractionHand.MAIN_HAND ? 225 : 90;
        return player.position().add(player.getLookAngle().scale(distance)).add(spread * Math.sin(Math.toRadians(angle - player.yHeadRot)), player.getBbHeight() * 0.9f, spread * Math.cos(Math.toRadians(angle - player.yHeadRot)));
    }
}
