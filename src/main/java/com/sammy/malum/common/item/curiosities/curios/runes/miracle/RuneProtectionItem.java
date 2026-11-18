package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.*;
import com.sammy.malum.*;
import com.sammy.malum.core.helpers.TooltipComponentHelper;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.handlers.item_events.IEventResponderBody;
import team.lodestar.wayward_attributes.WaywardTags;
import top.theillusivec4.curios.api.*;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RuneProtectionItem extends MiracleRuneCurioItem implements IEventResponderBody {

    public RuneProtectionItem(Properties builder) {
        super(builder, MalumSpiritTypes.EARTHEN_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("protection_rune"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.ARMOR,
                new AttributeModifier(MalumMod.malumPath("protection_rune"), 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, @Nullable LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker == null) {
            return;
        }
        var source = event.getSource();
        if (source.is(WaywardTags.DamageTypeTags.IS_MAGIC)) {
            return;
        }
        if (target.getRandom().nextFloat() < 0.2f) {
            event.setNewDamage(event.getNewDamage() * 0.5f);
            //TODO: Block Sound
        }
    }
}
