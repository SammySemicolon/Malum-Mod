package com.sammy.malum.common.item.tools;

import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.core.setup.client.ParticleRegistry;
import com.sammy.malum.core.setup.content.DamageSourceRegistry;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.item.enchantment.Enchantments;
import team.lodestar.lodestone.helpers.CurioHelper;
import team.lodestar.lodestone.systems.item.ModCombatItem;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MalumScytheItem extends ModCombatItem implements IMalumEventResponderItem {

    public MalumScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn + 3, attackSpeedIn - 3.2f, builderIn);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        //TODO: convert this to a ToolAction, or something alike
        boolean canSweep = !CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE.get()) && !CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get());
        if (attacker instanceof Player player) {
            SoundEvent sound;
            if (canSweep) {
                spawnSweepParticles(player, ParticleRegistry.SCYTHE_SWEEP_ATTACK_PARTICLE.get());
                sound = SoundEvents.PLAYER_ATTACK_SWEEP;
            } else {
                spawnSweepParticles(player, ParticleRegistry.SCYTHE_CUT_ATTACK_PARTICLE.get());
                sound = SoundRegistry.SCYTHE_CUT.get();
            }
            attacker.level.playSound(null, target.getX(), target.getY(), target.getZ(), sound, attacker.getSoundSource(), 1, 1);
        }

        if (!canSweep || event.getSource().isMagic() || event.getSource().getMsgId().equals(DamageSourceRegistry.SCYTHE_SWEEP_IDENTIFIER)) {
            return;
        }
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, attacker);
        float damage = event.getAmount() * (0.5f + EnchantmentHelper.getSweepingDamageRatio(attacker));
        target.level.getEntities(attacker, target.getBoundingBox().inflate(1 + level * 0.25f)).forEach(e -> {
            if (e instanceof LivingEntity livingEntity) {
                if (livingEntity.isAlive()) {
                    livingEntity.hurt(new EntityDamageSource(DamageSourceRegistry.SCYTHE_SWEEP_IDENTIFIER, attacker), damage);
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

    public static ItemStack getScytheItemStack(DamageSource source, LivingEntity attacker) {
        ItemStack stack = attacker.getMainHandItem();

        if (source.getDirectEntity() instanceof ScytheBoomerangEntity) {
            stack = ((ScytheBoomerangEntity) source.getDirectEntity()).scythe;
        }
        return stack.getItem() instanceof MalumScytheItem ? stack : ItemStack.EMPTY;
    }
}