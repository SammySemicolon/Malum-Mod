package com.sammy.malum.common.item.curiosities.runes;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;

public class RuneReactiveShieldingItem extends MalumRuneCurioItem implements IEventResponderItem {

    public RuneReactiveShieldingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.EARTHEN_SPIRIT);
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        MobEffect shielding = MobEffectRegistry.REACTIVE_SHIELDING.get();
        MobEffectInstance effect = attacked.getEffect(shielding);
        if (effect == null) {
            attacked.addEffect(new MobEffectInstance(shielding, 100, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, attacked, 1, 3);
            EntityHelper.extendEffect(effect, attacked, 20, 100);
        }
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("reactive_shielding");
    }
}