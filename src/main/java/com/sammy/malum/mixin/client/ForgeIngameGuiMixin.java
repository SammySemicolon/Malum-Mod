package com.sammy.malum.mixin.client;

import com.sammy.malum.registry.common.MobEffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Gui.class)
public class ForgeIngameGuiMixin {

    @ModifyArg(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"))
    private MobEffect malum$renderFood(MobEffect effect) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && effect.equals(MobEffects.HUNGER) && player.hasEffect(MobEffectRegistry.GLUTTONY.get())) {
            return MobEffectRegistry.GLUTTONY.get();
        }
        return MobEffects.HUNGER;
    }
}
