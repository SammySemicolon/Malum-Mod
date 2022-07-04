package com.sammy.malum.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeIngameGui.class)
public class ForgeIngameGuiMixin {

    @ModifyArg(method = "renderFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"))
    private MobEffect malumGluttonyIsVisuallyHungerMixin(MobEffect effect) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (effect.equals(MobEffects.HUNGER) && player.hasEffect(MalumMobEffectRegistry.GLUTTONY.get())) {
            return MalumMobEffectRegistry.GLUTTONY.get();
        }
        return MobEffects.HUNGER;
    }
}