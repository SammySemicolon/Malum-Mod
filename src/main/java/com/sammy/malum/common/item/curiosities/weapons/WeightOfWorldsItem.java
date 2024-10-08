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
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        var particleEffectType = ParticleEffectTypeRegistry.SCYTHE_SLASH;
        var effect = MobEffectRegistry.GRIM_CERTAINTY.get();
        if (attacker.hasEffect(effect) || level.random.nextFloat() < 0.25f) {
            event.setAmount(event.getAmount() * 2);
            SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 2f, 0.75f);
            SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3f, 1.25f);
            SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3f, 1.75f);
            particleEffectType = ParticleEffectTypeRegistry.WEIGHT_OF_WORLDS_CRIT;
            attacker.removeEffect(effect);
        }
        ParticleHelper.createSlashingEffect(particleEffectType)
                .setVertical()
                .spawnForwardSlashingParticle(attacker);
        SoundHelper.playSound(target, SoundRegistry.WEIGHT_OF_WORLDS_CUT.get(), 2f, 0.75f);
    }
}
