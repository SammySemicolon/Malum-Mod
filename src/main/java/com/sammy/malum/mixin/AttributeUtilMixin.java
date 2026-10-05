package com.sammy.malum.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.sammy.malum.common.item.curiosities.tools.spellweaver.*;
import com.sammy.malum.common.item.curiosities.weapons.greatsword.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.common.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import team.lodestar.wayward_attributes.tweaks.*;

@Mixin(AttributeUtil.class)
public class AttributeUtilMixin {
    @WrapOperation(method = "applyTextFor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/Attribute;getBaseId()Lnet/minecraft/resources/ResourceLocation;"))
    private static ResourceLocation waywardAttributes$markAttributeAsBase(Attribute instance, Operation<ResourceLocation> original) {
        var id = SpellweavingPickaxeItem.getBaseId(instance);
        if (id != null) {
            return id;
        }
        id = VindictiveBrandSwordItem.getBaseId(instance);
        if (id != null) {
            return id;
        }
        return original.call(instance);
    }
}
