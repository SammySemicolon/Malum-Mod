package com.sammy.malum.common.item.curiosities.curios.weeping;

import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.systems.item.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;

public class CurioHiddenBladeNecklace extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioHiddenBladeNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        float amount = event.getAmount();
        int amplifier = (int) Math.ceil(amount / 4f);
        if (amplifier >= 6) {
            amplifier *= amplifier / 6f;
        }
        MobEffect effect = MobEffectRegistry.WICKED_INTENT.get();
        attacked.addEffect(new MobEffectInstance(effect, 40, amplifier + 1));
    }
}