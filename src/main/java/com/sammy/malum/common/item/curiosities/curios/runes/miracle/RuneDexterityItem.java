package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.*;

public class RuneDexterityItem extends MiracleRuneCurioItem {

    private static final ResourceLocation MODIFIER = MalumMod.malumPath("dexterity_rune_conditional");

    public RuneDexterityItem(Properties builder) {
        super(builder, MalumSpiritTypes.AERIAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("dexterity_rune"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.MOVEMENT_SPEED, new AttributeModifier(MalumMod.malumPath("dexterity_rune"), 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        var entity = slotContext.entity();
        var attribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(MODIFIER);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        var entity = slotContext.entity();
        if (entity.level().getGameTime() % 5L == 0) {
            var attribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute == null) {
                return;
            }
            float healthDelta = entity.getHealth() / entity.getMaxHealth();
            if (healthDelta < 0.5f) {
                if (!attribute.hasModifier(MODIFIER)) {
                    attribute.addTransientModifier(new AttributeModifier(MODIFIER, 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                }
            }
            else {
                attribute.removeModifier(MODIFIER);
            }
        }
    }
}
