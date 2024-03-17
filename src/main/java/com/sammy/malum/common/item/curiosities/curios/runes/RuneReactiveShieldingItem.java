package com.sammy.malum.common.item.curiosities.curios.runes;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.function.*;

public class RuneReactiveShieldingItem extends MalumRuneCurioItem implements IEventResponderItem {

    public RuneReactiveShieldingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.EARTHEN_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.rune.effect.reactive_shielding"));
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        MobEffect shielding = MobEffectRegistry.REACTIVE_SHIELDING.get();
        MobEffectInstance effect = attacked.getEffect(shielding);
        if (effect == null) {
            if (attacked.level().random.nextFloat() < 0.5f) {
                attacked.addEffect(new MobEffectInstance(shielding, 80, 0, true, true, true));
            }
        } else {
            if (attacked.level().random.nextFloat() < 0.5f) {
                EntityHelper.amplifyEffect(effect, attacked, 1, 3);
            }
            EntityHelper.extendEffect(effect, attacked, 40, 100);
        }
    }
}