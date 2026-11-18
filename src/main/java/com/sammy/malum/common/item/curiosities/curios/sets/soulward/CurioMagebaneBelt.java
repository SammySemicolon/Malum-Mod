 package com.sammy.malum.common.item.curiosities.curios.sets.soulward;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.IMalumEventResponderBody;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.MalumAttributes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import team.lodestar.wayward_attributes.WaywardTags;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class CurioMagebaneBelt extends MalumCurioItem implements IMalumEventResponderBody {

    public CurioMagebaneBelt(Properties builder) {
        super(builder, MalumTrinketFamily.ORNATE);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("soul_ward_magic_resilience"));
        consumer.accept(TooltipComponentHelper.negativeCurioEffect("soul_ward_long_shatter_cooldown"));
        consumer.accept(TooltipComponentHelper.negativeCurioEffect("soul_ward_physical_vulnerability"));
    }

    @Override
    public void modifySoulWardPropertiesEvent(ModifySoulWardPropertiesEvent event, LivingEntity wardedEntity, ItemStack stack) {
        if (event.getSource().is(WaywardTags.DamageTypeTags.IS_MAGIC)) {
            event.modifyIntegrity(1.5f);
        } else {
            event.modifyAbsorptionRatio(0.5f);
        }
    }

    @Override
    public void soulWardDamageEvent(SoulWardDamageEvent event, LivingEntity wardedEntity, ItemStack stack) {
        var handler = event.getSoulWardHandler();

        if (handler.isDepleted()) {
            handler.addCooldown(16f);
            return;
        }
        if (event.getSource().is(WaywardTags.DamageTypeTags.IS_MAGIC)) {
            handler.recoverSoulWard(wardedEntity, 1f);
        }
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        var id = MalumMod.malumPath("magebane_belt");
        addAttributeModifier(map, MalumAttributes.SOUL_WARD_RECOVERY_RATE,
                new AttributeModifier(id, 0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        addAttributeModifier(map, MalumAttributes.SOUL_WARD_CAPACITY,
                new AttributeModifier(id, 6f, AttributeModifier.Operation.ADD_VALUE));
    }
}
