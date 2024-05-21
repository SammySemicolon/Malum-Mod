package com.sammy.malum.mixin;

import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

//TODO: THIS IS TEMPORARY, ONLY FOR THE SERVER
@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void useOn(UseOnContext pContext, CallbackInfoReturnable<InteractionResult> cir) {
        cir.setReturnValue(InteractionResult.FAIL);
    }
}
