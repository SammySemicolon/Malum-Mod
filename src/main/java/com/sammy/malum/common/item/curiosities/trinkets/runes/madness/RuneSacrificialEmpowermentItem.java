package com.sammy.malum.common.item.curiosities.trinkets.runes.madness;

import com.sammy.malum.common.item.curiosities.trinkets.runes.AbstractRuneTrinketsItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

import java.util.function.Consumer;

public class RuneSacrificialEmpowermentItem extends AbstractRuneTrinketsItem implements IEventResponderItem {

    public RuneSacrificialEmpowermentItem(Properties builder) {
        super(builder, SpiritTypeRegistry.WICKED_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("scythe_chain"));
    }

    @Override
    public void killEvent(LivingEntity finalAttacker, LivingEntity livingEntity, ItemStack s, DamageSource damageSource, float damageAmount) {
        MobEffect sacrificialEmpowerment = MobEffectRegistry.SACRIFICIAL_EMPOWERMENT.get();
        MobEffectInstance effect = finalAttacker.getEffect(sacrificialEmpowerment);
        if (effect == null) {
            finalAttacker.addEffect(new MobEffectInstance(sacrificialEmpowerment, 200, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, finalAttacker, 1, 3);
            EntityHelper.extendEffect(effect, finalAttacker, 50, 200);
        }
    }
}
