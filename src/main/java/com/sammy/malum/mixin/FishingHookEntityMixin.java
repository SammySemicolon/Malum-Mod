package com.sammy.malum.mixin;

import com.sammy.malum.common.effect.rite.aura.soulwood.GoodTidesEffect;
import com.sammy.malum.registry.common.MalumMobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHook.class)
public class FishingHookEntityMixin {

    @Mutable
    @Shadow
    @Final
    private int luck;
    @Mutable
    @Shadow
    @Final
    private int lureSpeed;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;II)V", at = @At("RETURN"))
    private void malumModifyFishingLuckStatsMixin(Player player, Level level, int luck, int lureSpeed, CallbackInfo ci) {
        var goodTides = GoodTidesEffect.increaseFishingStats(player);
        this.luck += goodTides.getFirst();
        this.lureSpeed += goodTides.getSecond() * 100;
    }
}