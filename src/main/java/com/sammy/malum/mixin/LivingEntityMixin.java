package com.sammy.malum.mixin;

import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "hasEffect", at = @At("RETURN"), cancellable = true) //TODO: this doesn't seem like a good idea at all.
    private void malumGluttonyExtendsHungerMixin(MobEffect effect, CallbackInfoReturnable<Boolean> cir) {
        if (effect.equals(MobEffects.HUNGER) && ((LivingEntity)((Object)this)).hasEffect(MalumMobEffectRegistry.GLUTTONY.get())) {
            cir.setReturnValue(true);
        }
    }
}
