package com.sammy.malum.mixin;

import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyVariable(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"))
    private double malum$travel(double value) {
        return TouchOfDarknessHandler.updateEntityGravity((LivingEntity)((Object)this), value);
    }
}
