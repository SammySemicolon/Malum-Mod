package com.sammy.malum.common.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.systems.item.ModCombatItem;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ScytheItem extends ModCombatItem implements IEventResponderItem {

    public ScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, float magicDamageBoost, Properties builderIn) {
        super(tier, attackDamageIn+3, attackSpeedIn-3.2f, builderIn, createExtraAttributes(magicDamageBoost));
    }

    public static ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes(float magicDamageBoost) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (magicDamageBoost != 0) {
            builder.put(AttributeRegistry.MAGIC_PROFICIENCY, new AttributeModifier(UUID.fromString("d1d17de1-c944-4cdb-971e-f9c4ce260cfe"), "Weapon magic proficiency", magicDamageBoost, AttributeModifier.Operation.ADDITION));
        }
        return builder;
    }


    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (EnchantmentHelper.getItemEnchantmentLevel(MalumEnchantments.REBOUND.get(), itemstack) > 0) {
            if (!level.isClientSide) {
                playerIn.setItemInHand(handIn, ItemStack.EMPTY);
                double baseDamage = playerIn.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
                float multiplier = 1.2f;
                double damage = 1.0F + baseDamage * multiplier;

                int slot = handIn == InteractionHand.OFF_HAND ? playerIn.getInventory().getContainerSize() - 1 : playerIn.getInventory().selected;
                ScytheBoomerangEntity entity = new ScytheBoomerangEntity(level);
                entity.setPos(playerIn.position().x, playerIn.position().y + playerIn.getBbHeight() / 2f, playerIn.position().z);

                entity.setData((float) damage, playerIn.getUUID(), slot, itemstack);
                entity.getEntityData().set(ScytheBoomerangEntity.SCYTHE, itemstack);

                entity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, (float) (1.5F + playerIn.getAttributeValue(AttributeRegistry.SCYTHE_PROFICIENCY)*0.125f), 0F);
                level.addFreshEntity(entity);
            }
            playerIn.awardStat(Stats.ITEM_USED.get(this));

            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }
        return super.use(level, playerIn, handIn);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            SoundEvent sound;
            if (ItemHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE)) {
                spawnSweepParticles((Player) attacker, ParticleRegistry.SCYTHE_CUT_ATTACK_PARTICLE.get());
                sound = SoundRegistry.SCYTHE_CUT;
            } else {
                spawnSweepParticles((Player) attacker, ParticleRegistry.SCYTHE_SWEEP_ATTACK_PARTICLE.get());
                sound = SoundEvents.PLAYER_ATTACK_SWEEP;
            }
            attacker.level.playSound(null, target.getX(), target.getY(), target.getZ(), sound, attacker.getSoundSource(), 1, 1);
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (ItemHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE)) {
            return;
        }
        float damage = event.getAmount() * (0.5f+EnchantmentHelper.getSweepingDamageRatio(attacker));
        target.level.getEntities(attacker, target.getBoundingBox().inflate(1)).forEach(e ->
        {
            if (e instanceof LivingEntity livingEntity) {
                if (livingEntity.isAlive()) {
                    livingEntity.hurt(DamageSource.mobAttack(attacker), damage);
                    livingEntity.knockback(0.4F, Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F))));
                }
            }
        });
    }

    public void spawnSweepParticles(Player player, SimpleParticleType type) {
        double d0 = (-Mth.sin(player.getYRot() * ((float) Math.PI / 180F)));
        double d1 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
        if (player.level instanceof ServerLevel) {
            ((ServerLevel) player.level).sendParticles(type, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }
}