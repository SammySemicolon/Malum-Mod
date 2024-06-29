package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.common.entity.boomerang.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.*;

public class MalumScytheItem extends ModCombatItem implements IMalumEventResponderItem {

    public MalumScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn + 3 + tier.getAttackDamageBonus(), attackSpeedIn - 3.2f, builderIn);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        //TODO: convert this to a ToolAction, or something alike
        boolean canSweep = !CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE.get()) && !CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get());
        var level = attacker.level();
        if (attacker instanceof Player player) {
            SoundEvent sound;
            if (canSweep) {
                spawnSweepParticles(player, ParticleRegistry.SCYTHE_SWEEP_PARTICLE.get());
                sound = SoundEvents.PLAYER_ATTACK_SWEEP;
            } else {
                spawnSweepParticles(player, ParticleRegistry.SCYTHE_CUT_PARTICLE.get());
                sound = SoundRegistry.SCYTHE_CUT.get();
            }
            level.playSound(null, target.getX(), target.getY(), target.getZ(), sound, attacker.getSoundSource(), 1, 1);
        }

        if (!canSweep || event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC) || event.getSource().getMsgId().equals(DamageTypeRegistry.SCYTHE_SWEEP_IDENTIFIER)) {
            return;
        }
        int sweeping = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, attacker);
        float damage = event.getAmount() * (0.5f + EnchantmentHelper.getSweepingDamageRatio(attacker));
        level.getEntities(attacker, target.getBoundingBox().inflate(1 + sweeping * 0.25f)).forEach(e -> {
            if (e instanceof LivingEntity livingEntity) {
                if (livingEntity.isAlive()) {
                    livingEntity.hurt((DamageTypeRegistry.create(level, DamageTypeRegistry.SCYTHE_SWEEP, attacker)), damage);
                    livingEntity.knockback(0.4F, Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F))));
                }
            }
        });
    }

    public static void spawnSweepParticles(Player player, SimpleParticleType type) {
        double d0 = (-Mth.sin(player.getYRot() * ((float) Math.PI / 180F)));
        double d1 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(type, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }

    public static ItemStack getScytheItemStack(DamageSource source, LivingEntity attacker) {
        ItemStack stack = attacker.getMainHandItem();

        if (source.getDirectEntity() instanceof ScytheBoomerangEntity scytheBoomerang) {
            stack = scytheBoomerang.getItem();
        }
        return stack.getItem() instanceof MalumScytheItem ? stack : ItemStack.EMPTY;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.equals(Enchantments.SWEEPING_EDGE)) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}