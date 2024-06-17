package com.sammy.malum.mixin.client;

import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.sammy.malum.client.renderer.curio.TokenOfGratitudeRenderer.getElytraTexture;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin {

    @Inject(method = "getElytraTexture", at = @At("RETURN"), cancellable = true, remap = false)
    private void malumGetElytraTexture(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity instanceof Player player) {
            cir.setReturnValue(getElytraTexture(player.getUUID(), new ResourceLocation("textures/entity/elytra.png")));
        }
    }
}
