package com.sammy.malum.common.item.curiosities.curios.soulward;

import com.google.common.collect.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.item.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.registry.common.tag.*;
import top.theillusivec4.curios.api.*;

public class CurioReinforcementRing extends MalumCurioItem {

    public CurioReinforcementRing(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAP.get(), uuid -> new AttributeModifier(uuid,
                "Curio Soul Ward Capacity", 3f, AttributeModifier.Operation.ADDITION));
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_STRENGTH.get(), uuid -> new AttributeModifier(uuid,
                "Curio Soul Ward Strength", 1f, AttributeModifier.Operation.ADDITION));
    }
}