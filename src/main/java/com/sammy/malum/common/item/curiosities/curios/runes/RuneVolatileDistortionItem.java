package com.sammy.malum.common.item.curiosities.curios.runes;

import com.sammy.malum.registry.common.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.function.*;

public class RuneVolatileDistortionItem extends MalumRuneCurioItem implements IEventResponderItem {

    public RuneVolatileDistortionItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.rune.effect.volatile_distortion"));
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        final RandomSource random = attacker.getRandom();
        float multiplier = Mth.nextFloat(random, 0.8f, 1.4f);
        if (random.nextFloat() < 0.1f) {
            multiplier *= 2;
        }
        event.setAmount(event.getAmount() * multiplier);
    }
}