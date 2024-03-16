package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.google.common.collect.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.config.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.registry.common.tag.*;
import top.theillusivec4.curios.api.*;

import java.util.function.*;

public class CurioLimitlessBelt extends MalumCurioItem implements IMalumEventResponderItem, IVoidItem {

    public CurioLimitlessBelt(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.curio.effect.belt_of_the_limitless"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAP.get(), uuid -> new AttributeModifier(uuid,
                "Curio Soul Ward Capacity", 1f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    public float overrideSoulwardDamageAbsorbPercentage(LivingHurtEvent event, LivingEntity wardedEntity, ItemStack stack, float original) {
        if (!event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC)) {
            return CommonConfig.SOUL_WARD_MAGIC.getConfigValue().floatValue();
        }
        return IMalumEventResponderItem.super.overrideSoulwardDamageAbsorbPercentage(event, wardedEntity, stack, original);
    }
}