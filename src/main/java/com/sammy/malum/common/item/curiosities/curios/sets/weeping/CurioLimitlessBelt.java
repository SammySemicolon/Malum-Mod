package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class CurioLimitlessBelt extends MalumCurioItem implements IMalumEventResponderItem, IVoidItem {

    public CurioLimitlessBelt(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("soul_ward_physical_absorption"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAP.get(), uuid -> new AttributeModifier(uuid,
                "Curio Soul Ward Capacity", 1f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    public float adjustSoulWardDamageAbsorption(LivingHurtEvent event, LivingEntity wardedEntity, ItemStack stack, float original) {
        if (!event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC)) {
            return CommonConfig.SOUL_WARD_MAGIC.getConfigValue().floatValue();
        }
        return IMalumEventResponderItem.super.adjustSoulWardDamageAbsorption(event, wardedEntity, stack, original);
    }
}
