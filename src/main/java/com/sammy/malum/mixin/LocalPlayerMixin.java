package com.sammy.malum.mixin;

import com.sammy.malum.common.item.curiosities.trinkets.runes.madness.RuneUnnaturalStaminaItem;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Inject(method = "hasEnoughFoodToStartSprinting", at = @At("HEAD"), cancellable = true)
    private void malum$hasEnoughFoodToStartSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (RuneUnnaturalStaminaItem.forceSprint((LocalPlayer) ((Object) this))) {
            cir.setReturnValue(true);
        }
    }
}
