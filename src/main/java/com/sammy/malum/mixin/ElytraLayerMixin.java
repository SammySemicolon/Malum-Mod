package com.sammy.malum.mixin;

import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin {

    /*TODO
    @Inject(method = "getElytraTexture", at = @At("RETURN"), cancellable = true, remap = false)
    private void malumGetElytraTexture(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity instanceof Player player) {
            cir.setReturnValue(getElytraTexture(player.getUUID(), new ResourceLocation("textures/entity/elytra.png")));
        }
    }

     */
}