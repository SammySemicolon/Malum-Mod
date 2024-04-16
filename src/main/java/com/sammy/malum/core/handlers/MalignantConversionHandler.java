package com.sammy.malum.core.handlers;

import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.item.curiosities.armor.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraftforge.event.entity.living.*;

public class MalignantConversionHandler {

    public double malignantConversionCache;

    public static void checkForAttributeChanges(LivingEvent.LivingTickEvent event) {
        final LivingEntity livingEntity = event.getEntity();
        if (!livingEntity.level().isClientSide) {
            MalignantConversionHandler handler = MalumLivingEntityDataCapability.getCapability(livingEntity).malignantConversionHandler;
            final AttributeInstance attribute = livingEntity.getAttribute(AttributeRegistry.MALIGNANT_CONVERSION.get());
            if (attribute != null) {
                if (handler.malignantConversionCache != attribute.getValue()) {
                    MalignantStrongholdArmorItem.updateAttributes(livingEntity);
                }
                handler.malignantConversionCache = attribute.getValue();
            }
            else if (handler.malignantConversionCache > 0) {
                handler.malignantConversionCache = 0;
                MalignantStrongholdArmorItem.updateAttributes(livingEntity);
            }
        }
    }
}
