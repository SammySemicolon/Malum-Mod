package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.core.helpers.ParticleHelper;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.systems.item.IEventResponderItem;
import team.lodestar.lodestone.systems.item.tools.LodestoneAxeItem;

public class WeightOfWorldsItem extends LodestoneAxeItem implements IEventResponderItem {
    public WeightOfWorldsItem(Tier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public void killEvent(LivingEntity finalAttacker, LivingEntity livingEntity, ItemStack s, DamageSource damageSource, float damageAmount) {
        finalAttacker.addEffect(new MobEffectInstance(MobEffectRegistry.GRIM_CERTAINTY.get(), 200));
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker instanceof Player) {
            ParticleHelper.spawnVerticalSlashParticle(ParticleEffectTypeRegistry.SCYTHE_SLASH, attacker);
        }
        final Level level = attacker.level();
        level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.WEIGHT_OF_WORLDS_SLASH.get(), attacker.getSoundSource(), 1, 0.5f);
        final MobEffect effect = MobEffectRegistry.GRIM_CERTAINTY.get();
        if (attacker.hasEffect(effect) || level.random.nextFloat() < 0.25f) {
            event.setAmount(event.getAmount() * 2);
            level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.MALIGNANT_METAL_RESONATES.get(), attacker.getSoundSource(), 2, 0.5f);
            level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.MALIGNANT_METAL_RESONATES.get(), attacker.getSoundSource(), 2, 1.5f);
            level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.DRAINING_MOTIF.get(), attacker.getSoundSource(), 2, 0.5f);
            attacker.removeEffect(effect);
        }
    }
}
