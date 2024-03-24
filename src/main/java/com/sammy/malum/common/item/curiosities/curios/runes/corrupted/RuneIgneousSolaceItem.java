package com.sammy.malum.common.item.curiosities.curios.runes.corrupted;

import com.sammy.malum.common.item.curiosities.curios.runes.MalumRuneCurioItem;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

import java.util.function.Consumer;

public class RuneIgneousSolaceItem extends MalumRuneCurioItem implements IEventResponderItem {

    public RuneIgneousSolaceItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("burning_resistance"));
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.isOnFire()) {
            event.setAmount(event.getAmount()*0.75f);
        }
    }
}
