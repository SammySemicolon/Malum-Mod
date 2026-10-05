package com.sammy.malum.mixin.client;

import com.mojang.authlib.GameProfile;
import com.sammy.malum.common.item.curiosities.tools.VisionaryScryglassItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

	@Inject(method = "getFieldOfViewModifier", at = @At("RETURN"), cancellable = true)
	private void getFieldOfViewModifier(CallbackInfoReturnable<Float> cir) {
		Player player = (Player) ((Object)this);
		if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && VisionaryScryglassItem.isScopingScryglass(player)) {
			cir.setReturnValue(cir.getReturnValue() * 0.5f);
		}
	}
}