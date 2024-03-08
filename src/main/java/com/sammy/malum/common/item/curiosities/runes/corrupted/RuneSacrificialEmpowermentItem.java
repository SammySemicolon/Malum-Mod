package com.sammy.malum.common.item.curiosities.runes.corrupted;

import com.sammy.malum.common.item.curiosities.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;

public class RuneSacrificialEmpowermentItem extends MalumRuneCurioItem implements IEventResponderItem {

    public RuneSacrificialEmpowermentItem(Properties builder) {
        super(builder, SpiritTypeRegistry.WICKED_SPIRIT);
    }

    @Override
    public void killEvent(LivingEntity attacker, LivingEntity target, ItemStack stack) {
        MobEffect sacrificialEmpowerment = MobEffectRegistry.SACRIFICIAL_EMPOWERMENT.get();
        MobEffectInstance effect = attacker.getEffect(sacrificialEmpowerment);
        if (effect == null) {
            attacker.addEffect(new MobEffectInstance(sacrificialEmpowerment, 200, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, attacker, 1, 3);
            EntityHelper.extendEffect(effect, attacker, 100, 200);
        }
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("sacrificial_empowerment");
    }
}