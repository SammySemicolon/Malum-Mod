package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CurioHiddenBladeNecklace extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioHiddenBladeNecklace(Properties builder) {
        super(builder);
    }

    @Override
    public boolean isOrnate() {
        return true;
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked instanceof Player player && player.getCooldowns().isOnCooldown(ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get())) {
            return;
        }
        float amount = event.getAmount();
        int amplifier = (int) Math.ceil(amount / 4f);
        if (amplifier >= 6) {
            amplifier *= amplifier / 6f;
        }
        MobEffect effect = MobEffectRegistry.WICKED_INTENT.get();
        attacked.addEffect(new MobEffectInstance(effect, 40, amplifier - 1));
    }
}