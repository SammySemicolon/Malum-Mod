package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.common.item.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
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
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.equals(Enchantments.SWEEPING_EDGE)) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        boolean canSweep = canSweep(attacker);
        var level = attacker.level();

        if (!event.getSource().is(DamageTypeRegistry.SCYTHE_MELEE)) {
            return;
        }
        if (!canSweep) {
            SoundHelper.playSound(attacker, SoundRegistry.SCYTHE_CUT.get(), 1, 0.75f);
            ParticleHelper.spawnVerticalSlashParticle(ParticleEffectTypeRegistry.SCYTHE_SLASH, attacker);
            return;
        }

        SoundHelper.playSound(attacker, SoundRegistry.SCYTHE_SWEEP.get(), 1, 1);
        ParticleHelper.spawnHorizontalSlashParticle(ParticleEffectTypeRegistry.SCYTHE_SLASH, attacker);
        int sweeping = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, attacker);
        float damage = event.getAmount() * (0.5f + EnchantmentHelper.getSweepingDamageRatio(attacker));
        float radius = 1 + sweeping * 0.25f;
        level.getEntities(attacker, target.getBoundingBox().inflate(radius)).forEach(e -> {
            if (e instanceof LivingEntity livingEntity) {
                if (livingEntity.isAlive()) {
                    livingEntity.hurt((DamageTypeRegistry.create(level, DamageTypeRegistry.SCYTHE_SWEEP, attacker)), damage);
                    livingEntity.knockback(0.4F,
                            Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)),
                            (-Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F))));
                }
            }
        });
    }

    public static boolean canSweep(LivingEntity attacker) {
        //TODO: convert this to a ToolAction, or something alike
        return !CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE.get()) &&
                !CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get());
    }

}