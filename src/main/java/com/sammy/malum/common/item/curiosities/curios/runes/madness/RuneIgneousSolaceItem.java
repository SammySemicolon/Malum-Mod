package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.handlers.item_events.IEventResponderBody;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RuneIgneousSolaceItem extends MadnessRuneCurioItem implements IEventResponderBody {

    public RuneIgneousSolaceItem(Properties builder) {
        super(builder, MalumSpiritTypes.INFERNAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("burning_resistance"));
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, @Nullable LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (target.isOnFire()) {
            event.setNewDamage(event.getNewDamage() * 0.75f);
        }
    }
}
