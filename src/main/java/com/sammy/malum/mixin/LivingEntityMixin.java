package com.sammy.malum.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.sammy.malum.registry.common.AttributeRegistry.*;
import static team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry.*;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder lodestone$CreateLivingAttributes(AttributeSupplier.Builder original) {
        return original
                .add(MAGIC_RESISTANCE.get())
                .add(MAGIC_PROFICIENCY.get())
                .add(MAGIC_DAMAGE.get())
                .add(SCYTHE_PROFICIENCY.get())
                .add(SPIRIT_SPOILS.get())
                .add(ARCANE_RESONANCE.get())
                .add(SOUL_WARD_STRENGTH.get())
                .add(SOUL_WARD_RECOVERY_RATE.get())
                .add(SOUL_WARD_CAP.get())
                .add(RESERVE_STAFF_CHARGES.get())
                .add(MALIGNANT_CONVERSION.get());
    }
}
