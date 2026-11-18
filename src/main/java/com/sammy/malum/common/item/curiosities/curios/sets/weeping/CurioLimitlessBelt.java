package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.IMalumEventResponderBody;
import com.sammy.malum.common.item.IVoidItem;
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
import team.lodestar.lodestone.modules.core.easing.Easing;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class CurioLimitlessBelt extends MalumCurioItem implements IMalumEventResponderBody, IVoidItem {

    public CurioLimitlessBelt(Properties builder) {
        super(builder, MalumTrinketFamily.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("soul_ward_boundless_integrity"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        var id = MalumMod.malumPath("belt_of_the_limitless");
        addAttributeModifier(map, MalumAttributes.SOUL_WARD_CAPACITY, new AttributeModifier(id, 1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        addAttributeModifier(map, MalumAttributes.SOUL_WARD_RECOVERY_RATE, new AttributeModifier(id, -0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    @Override
    public void modifySoulWardPropertiesEvent(ModifySoulWardPropertiesEvent event, LivingEntity wardedEntity, ItemStack stack) {
        var handler = event.getSoulWardHandler();

        double capacity = wardedEntity.getAttributeValue(MalumAttributes.SOUL_WARD_CAPACITY);
        double delta = (handler.getSoulWard() / capacity);

        double integrityMultiplier = Easing.CIRC_IN.lerp(delta, 1f, 4f);
        event.modifyIntegrity(event.getNewIntegrity() * integrityMultiplier);
    }
}
