package com.sammy.malum.common.item.curiosities.runes.corrupted;

import com.sammy.malum.common.item.curiosities.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;

public class RuneHereticItem extends MalumRuneCurioItem implements IEventResponderItem {

    public RuneHereticItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public void takeDamageEvent(LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        MobEffect silenced = MobEffectRegistry.SILENCED.get();
        MobEffectInstance effect = attacker.getEffect(silenced);
        if (effect == null) {
            attacker.addEffect(new MobEffectInstance(silenced, 300, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, attacker, 1, 9);
            EntityHelper.extendEffect(effect, attacker, 60, 600);
        }
        attacked.level().playSound(null, attacked.getX(), attacked.getY(), attacked.getZ(), SoundRegistry.DRAINING_MOTIF.get(), attacked.getSoundSource(), 1f, 1.5f);
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("heretic");
    }
}