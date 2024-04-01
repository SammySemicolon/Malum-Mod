package com.sammy.malum.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.client.renderer.text.SubtractiveTextGlyphRenderTypes;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.apache.commons.compress.utils.Lists;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Font.StringRenderOutput.class)
public class FontStringRenderOutputMixin {

	@Final
	@Shadow
	Font this$0;

	@Final
	@Shadow
	MultiBufferSource bufferSource;

	@Final
	@Shadow
	private Matrix4f pose;

	@Final
	@Shadow
	private int packedLightCoords;

	@Unique
	private List<BakedGlyph.Effect> malum$inverseEffects;

	@ModifyExpressionValue(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Style;getColor()Lnet/minecraft/network/chat/TextColor;"))
	public TextColor enableSubtractiveBlending(TextColor color, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
		if (color != null && color.getValue() == MalumSpiritType.INVERT_COLOR) {
			subtractiveEnabled.set(true);
			return TextColor.fromLegacyFormat(ChatFormatting.WHITE);
		}

		return color;
	}

	@WrapOperation(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;renderType(Lnet/minecraft/client/gui/Font$DisplayMode;)Lnet/minecraft/client/renderer/RenderType;"))
	public RenderType useSubtractiveRenderingType(BakedGlyph instance, Font.DisplayMode displayMode, Operation<RenderType> original, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
		if (subtractiveEnabled.get()) {
			return ((SubtractiveTextGlyphRenderTypes) (Object) ((AccessorBakedGlyph) instance).malum$getRenderTypes()).malum$getSubtractiveType();
		}

		return original.call(instance, displayMode);
	}

	@WrapWithCondition(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font$StringRenderOutput;addEffect(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph$Effect;)V"))
	public boolean flagEffectAsSubtractive(Font.StringRenderOutput self, BakedGlyph.Effect effect, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
		if (subtractiveEnabled.get()) {
			if (malum$inverseEffects == null)
				malum$inverseEffects = Lists.newArrayList();
			malum$inverseEffects.add(effect);
			return false;
		}

		return true;
	}

	@Inject(method = "finish", at = @At("RETURN"))
	public void renderSubtractiveEffects(int pBackgroundColor, float pX, CallbackInfoReturnable<Float> cir) {
		if (malum$inverseEffects != null) {
			BakedGlyph bakedglyph = ((AccessorFont) this$0).malum$getFontSet(Style.DEFAULT_FONT).whiteGlyph();
			RenderType subtractiveType = ((SubtractiveTextGlyphRenderTypes) (Object) ((AccessorBakedGlyph) bakedglyph).malum$getRenderTypes()).malum$getSubtractiveType();
			VertexConsumer vertexconsumer = bufferSource.getBuffer(subtractiveType);

			for(BakedGlyph.Effect bakedglyph$effect : malum$inverseEffects) {
				bakedglyph.renderEffect(bakedglyph$effect, pose, vertexconsumer, packedLightCoords);
			}
		}
	}

}
