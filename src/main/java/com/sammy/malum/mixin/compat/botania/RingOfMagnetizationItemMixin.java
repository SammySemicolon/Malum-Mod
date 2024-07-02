package com.sammy.malum.mixin.compat.botania;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.common.item.equipment.bauble.RingOfMagnetizationItem;
import vazkii.botania.xplat.XplatAbstractions;

@Pseudo
@Mixin(RingOfMagnetizationItem.class)
public class RingOfMagnetizationItemMixin {

    @WrapOperation(method = "getEquippedAttributeModifiers", at = @At(value = "INVOKE", target = "Lvazkii/botania/xplat/XplatAbstractions;isModLoaded(Ljava/lang/String;)Z"))
    private boolean malum$isModLoaded(XplatAbstractions instance, String s, Operation<Boolean> original) {
        return false;
    }
}
