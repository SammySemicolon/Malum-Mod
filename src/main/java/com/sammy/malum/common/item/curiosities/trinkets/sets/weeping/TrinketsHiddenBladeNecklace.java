package com.sammy.malum.common.item.curiosities.trinkets.sets.weeping;

import com.sammy.malum.common.entity.hidden_blade.HiddenBladeDelayedImpactEntity;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.core.handlers.SoulDataHandler;
import com.sammy.malum.core.helpers.ParticleHelper;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.helpers.TrinketsHelper;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;

import java.util.function.Consumer;

public class TrinketsHiddenBladeNecklace extends MalumTinketsItem implements IMalumEventResponderItem, IVoidItem {
    public TrinketsHiddenBladeNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("scythe_counterattack"));
        consumer.accept(negativeEffect("no_sweep"));
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        float amount = event.getAmount();
        int amplifier = (int) Math.ceil(amount / 4f);
        if (amplifier >= 6) {
            amplifier = Mth.ceil(amplifier * 1.5f);
        }
        MobEffect effect = MobEffectRegistry.WICKED_INTENT.get();
        attacked.addEffect(new MobEffectInstance(effect, 40, amplifier + 1));
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var source = event.getSource();
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        if (source.is(LodestoneDamageTypeTags.IS_MAGIC) || (source.is(DamageTypes.THORNS))) {
            return;
        }

        if (TrinketsHelper.hasTrinketEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get())) {
            if (SoulDataHandler.getScytheWeapon(source, attacker).isEmpty()) {
                return;
            }
            var effect = attacker.getEffect(MobEffectRegistry.WICKED_INTENT.get());
            if (effect == null) {
                return;
            }
            int duration = 25;
            float baseDamage = (float) (attacker.getAttributes().getValue(Attributes.ATTACK_DAMAGE) / duration);
            float magicDamage = (float) (attacker.getAttributes().getValue(LodestoneAttributeRegistry.MAGIC_DAMAGE.get()) / duration);
            var center = attacker.position().add(attacker.getLookAngle().scale(4));
            HiddenBladeDelayedImpactEntity entity = new HiddenBladeDelayedImpactEntity(level, center.x, center.y-3f+attacker.getBbHeight()/2f, center.z);
            entity.setData(attacker, baseDamage, magicDamage, duration);
            entity.setItem(stack);
            level.addFreshEntity(entity);
            ParticleHelper.spawnRandomOrientationSlashParticle(ParticleEffectTypeRegistry.HIDDEN_BLADE_COUNTER_SLASH, attacker);
            for (int i = 0; i < 3; i++) {
                SoundHelper.playSound(attacker, SoundRegistry.HIDDEN_BLADE_UNLEASHED.get(), 3f, RandomHelper.randomBetween(level.getRandom(), 0.75f, 1.25f));
            }
            attacker.removeEffect(effect.getEffect());
            event.setCanceled(true);
        }

    }
}
