package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.systems.item.*;
import team.lodestar.lodestone.systems.item.tools.*;

public class WeightOfWorldsItem extends LodestoneAxeItem implements IEventResponderItem {
    public WeightOfWorldsItem(Tier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }


    @Override
    public void killEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        attacker.addEffect(new MobEffectInstance(MobEffectRegistry.DEACONS_FORTUNE.get(), 200));
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker != null) {
            if (attacker instanceof Player player) {
                MalumScytheItem.spawnSweepParticles(player, ParticleRegistry.SCYTHE_CUT_PARTICLE.get());
            }
            final Level level = attacker.level();
            level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.WEIGHT_OF_WORLDS_SLASH.get(), attacker.getSoundSource(), 1, 0.5f);
            final MobEffect effect = MobEffectRegistry.DEACONS_FORTUNE.get();
            if (attacker.hasEffect(effect) || level.random.nextFloat() < 0.25f) {
                event.setAmount(event.getAmount()*3);
                level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.MALIGNANT_METAL_RESONATES.get(), attacker.getSoundSource(), 2, 0.5f);
                level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.MALIGNANT_METAL_RESONATES.get(), attacker.getSoundSource(), 2, 1.5f);
                level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.DRAINING_MOTIF.get(), attacker.getSoundSource(), 2, 0.5f);
                attacker.removeEffect(effect);
            }
        }
    }
}