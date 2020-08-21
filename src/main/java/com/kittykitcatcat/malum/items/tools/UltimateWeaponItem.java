package com.kittykitcatcat.malum.items.tools;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.particles.bonk.BonkParticleData;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

import static com.kittykitcatcat.malum.MalumMod.random;


public class UltimateWeaponItem extends SwordItem
{
    public UltimateWeaponItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage + 3, attackSpeed - 2.4f, properties.maxDamage(material.getMaxUses()));
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker instanceof PlayerEntity)
        {
            target.addVelocity(attacker.getLookVec().x * 2, 0.2, attacker.getLookVec().z * 2);
            attacker.world.playSound(null, attacker.getPosition(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1F, MathHelper.nextFloat(random, 1.5f, 2.0f));
            attacker.world.playSound(null, attacker.getPosition(), ModSounds.bonk, SoundCategory.PLAYERS, 1F, MathHelper.nextFloat(random, 1.5f, 2.0f));
            if (attacker.getEntityWorld() instanceof ServerWorld)
            {
                double d0 = -MathHelper.sin(attacker.rotationYaw * ((float) Math.PI / 180F));
                double d1 = MathHelper.cos(attacker.rotationYaw * ((float) Math.PI / 180F));
                ((ServerWorld) attacker.world).spawnParticle(new BonkParticleData(1f), target.getPosX() - d0 + MathHelper.nextFloat(MalumMod.random, -0.25f, 0.25f), attacker.getPosYHeight(0.85D), target.getPosZ() - d1 + MathHelper.nextFloat(MalumMod.random, -0.25f, 0.25f), 0, d0, 0.0D, d1, 0.0D);
            }
        }
        return super.hitEntity(stack, target, attacker);
    }
}