package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.handlers.item_events.IEventResponderBody;
import team.lodestar.wayward_attributes.WaywardTags;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.*;

public class RuneCullingItem extends MiracleRuneCurioItem implements IEventResponderBody {

    public RuneCullingItem(Properties builder) {
        super(builder, MalumSpiritTypes.WICKED_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("culling_rune"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, WaywardAttributeTypes.MAGIC_PROFICIENCY,
                new AttributeModifier(MalumMod.malumPath("culling_rune"), 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (!event.getSource().is(WaywardTags.DamageTypeTags.IS_MAGIC)) {
            return;
        }
        if (target.getHealth() > target.getMaxHealth() * 0.5f) {
            return;
        }
        event.setNewDamage(event.getNewDamage() * 1.2f);
    }
}