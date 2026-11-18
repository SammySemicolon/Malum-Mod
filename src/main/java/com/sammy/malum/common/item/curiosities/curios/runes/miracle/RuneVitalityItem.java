package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.*;
import com.sammy.malum.*;
import com.sammy.malum.common.item.IMalumEventResponderBody;
import com.sammy.malum.core.helpers.TooltipComponentHelper;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class RuneVitalityItem extends MiracleRuneCurioItem implements IMalumEventResponderBody {

    public RuneVitalityItem(Properties builder) {
        super(builder, MalumSpiritTypes.SACRED_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("vitality_rune"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, WaywardAttributeTypes.HEALING_MULTIPLIER,
                new AttributeModifier(MalumMod.malumPath("vitality_rune"), 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        var entity = slotContext.entity();
        if (entity.level().getGameTime() % 100L == 0) {
            float healthDelta = entity.getHealth() / entity.getMaxHealth();
            if (healthDelta > 0.75f) {
                entity.heal(1);
            }
        }
    }
}
