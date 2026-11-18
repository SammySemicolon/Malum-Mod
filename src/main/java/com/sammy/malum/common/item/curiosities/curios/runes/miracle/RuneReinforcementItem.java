package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.IMalumEventResponderBody;
import com.sammy.malum.core.helpers.TooltipComponentHelper;
import com.sammy.malum.core.systems.events.ModifySoulWardPropertiesEvent;
import com.sammy.malum.core.systems.events.SoulWardDamageEvent;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import team.lodestar.wayward_attributes.WaywardTags;
import top.theillusivec4.curios.api.*;

import java.util.function.Consumer;

public class RuneReinforcementItem extends MiracleRuneCurioItem implements IMalumEventResponderBody {

    public RuneReinforcementItem(Properties builder) {
        super(builder, MalumSpiritTypes.ARCANE_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("reinforcement_rune"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, MalumAttributes.SOUL_WARD_INTEGRITY,
                new AttributeModifier(MalumMod.malumPath("reinforcement_rune"), 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }

    @Override
    public void modifySoulWardPropertiesEvent(ModifySoulWardPropertiesEvent event, LivingEntity wardedEntity, ItemStack stack) {
        event.modifyCooldownRatio(0.75f);
    }
}