package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.UUID;

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
        float amount = event.getAmount();
        int amplifier = (int) Math.ceil(amount / 4f);
        if (amplifier >= 4) {
            amplifier *= amplifier/4f;
        }
        if (amplifier >= 20) {
            amplifier += amplifier-20;
        }
        attacked.addEffect(new MobEffectInstance(MalumMobEffectRegistry.WICKED_INTENT.get(), 40, amplifier-1));
    }
}