package com.sammy.malum.common.item.curiosities.curios.sets.soulward;

import com.google.common.collect.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.registry.common.tag.*;
import top.theillusivec4.curios.api.*;

import java.util.function.*;

public class CurioMagebaneBelt extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioMagebaneBelt(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.curio.effect.belt_of_the_magebane"));
    }

    @Override
    public void onSoulwardAbsorbDamage(LivingHurtEvent event, Player wardedPlayer, ItemStack stack, float soulwardLost, float damageAbsorbed) {
        DamageSource source = event.getSource();
        if (source.getEntity() != null) {
            if (source.is(LodestoneDamageTypeTags.IS_MAGIC)) {
                SoulWardHandler handler = MalumPlayerDataCapability.getCapability(wardedPlayer).soulWardHandler;
                handler.soulWardProgress = 0;
            }
        }
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_RECOVERY_SPEED.get(), uuid -> new AttributeModifier(uuid,
                "Curio Soul Ward Recovery Speed", 3f, AttributeModifier.Operation.ADDITION));
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAP.get(), uuid -> new AttributeModifier(uuid,
                "Curio Soul Ward Capacity", 6f, AttributeModifier.Operation.ADDITION));
    }
}