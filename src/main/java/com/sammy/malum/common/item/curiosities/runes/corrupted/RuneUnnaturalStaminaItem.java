package com.sammy.malum.common.item.curiosities.runes.corrupted;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.runes.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.helpers.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class RuneUnnaturalStaminaItem extends MalumRuneCurioItem {

    public RuneUnnaturalStaminaItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AERIAL_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.MOVEMENT_SPEED, uuid -> new AttributeModifier(uuid,
                "Curio Movement Speed", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }


    public static boolean forceSprint(LivingEntity livingEntity) {
        return CurioHelper.hasCurioEquipped(livingEntity, ItemRegistry.RUNE_OF_UNNATURAL_STAMINA.get());
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("unnatural_stamina");
    }
}