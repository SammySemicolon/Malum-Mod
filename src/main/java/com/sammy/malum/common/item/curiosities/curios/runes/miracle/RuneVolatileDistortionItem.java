package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.handlers.item_events.IEventResponderBody;

import java.util.function.Consumer;

public class RuneVolatileDistortionItem extends MiracleRuneCurioItem implements IEventResponderBody {

    public RuneVolatileDistortionItem(Properties builder) {
        super(builder, MalumSpiritTypes.ELDRITCH_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("volatile_distortion_rune"));
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker.getRandom().nextFloat() < 0.2f) {
            event.setNewDamage(event.getNewDamage() * 2);
        }
    }
}
