package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.function.*;

public class CurioHiddenBladeNecklace extends MalumCurioItem implements IMalumEventResponderItem, IVoidItem {
    public CurioHiddenBladeNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.curio.effect.necklace_of_the_hidden_blade"));
        consumer.accept(negativeEffect("malum.gui.curio.effect.necklace_of_the_narrow_edge"));
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