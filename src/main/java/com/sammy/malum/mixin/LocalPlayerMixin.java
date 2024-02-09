package com.sammy.malum.mixin;

import com.sammy.malum.common.item.curiosities.runes.corrupted.*;
import net.minecraft.client.player.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Inject(method = "hasEnoughFoodToStartSprinting", at = @At("HEAD"), cancellable = true)
    private void malum$hasEnoughFoodToStartSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (RuneUnnaturalStaminaItem.forceSprint((LocalPlayer)((Object)this))) {
            cir.setReturnValue(true);
        }
    }
}
