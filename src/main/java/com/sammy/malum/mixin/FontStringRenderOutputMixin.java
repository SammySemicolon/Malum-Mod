package com.sammy.malum.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.client.renderer.text.SubtractiveTextGlyphRenderTypes;
import com.sammy.malum.core.systems.spirit.*;
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
	MultiBufferSource bufferSource;

	@Final
	@Shadow
	private Matrix4f pose;

	@Final
	@Shadow
	private int packedLightCoords;

	@Shadow @Final private boolean dropShadow;
	@Shadow @Final private float a;
	@Unique
	private List<BakedGlyph.Effect> malum$inverseEffects;

	@ModifyExpressionValue(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Style;getColor()Lnet/minecraft/network/chat/TextColor;"))
	public TextColor enableSubtractiveBlending(TextColor color, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
		if (color != null && color.getValue() == UmbralSpiritType.INVERT_COLOR) {
			subtractiveEnabled.set(true);
			return TextColor.fromLegacyFormat(ChatFormatting.WHITE);
		}

		return color;
	}

	@WrapOperation(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;renderType(Lnet/minecraft/client/gui/Font$DisplayMode;)Lnet/minecraft/client/renderer/RenderType;"))
	public RenderType useSubtractiveRenderingType(BakedGlyph instance, Font.DisplayMode displayMode, Operation<RenderType> original, @Local(ordinal = 0) float alpha, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
		if (subtractiveEnabled.get() && alpha >= 0.5f) {
			return ((SubtractiveTextGlyphRenderTypes) (Object) ((AccessorBakedGlyph) instance).malum$getRenderTypes()).malum$getSubtractiveType();
		}

		return original.call(instance, displayMode);
	}

	@WrapOperation(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;renderChar(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;ZZFFFLorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFI)V"))
	public void shouldRenderCharacter(Font self, BakedGlyph glyph, boolean bold, boolean italic, float boldOffset, float x, float y, Matrix4f matrix, VertexConsumer vertexConsumer, float red, float green, float blue, float alpha, int packedLight, Operation<Void> original, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
		if (subtractiveEnabled.get()) {
			if (dropShadow) return;

			if (alpha >= 0.5f) {
				red = green = blue = alpha * 2 - 1;
			} else {
				red = green = blue = 0;
			}
		}

		original.call(self, glyph, bold, italic, boldOffset, x, y, matrix, vertexConsumer, red, green, blue, alpha, packedLight);
	}

	@WrapWithCondition(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font$StringRenderOutput;addEffect(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph$Effect;)V"))
	public boolean flagEffectAsSubtractive(Font.StringRenderOutput self, BakedGlyph.Effect effect, @Local(ordinal = 0) float alpha, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
		if (subtractiveEnabled.get() && !dropShadow && alpha >= 0.5f) {
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
			BakedGlyph bakedglyph = ((AccessorFont)this).malum$getFontSet(Style.DEFAULT_FONT).whiteGlyph();
			RenderType subtractiveType = ((SubtractiveTextGlyphRenderTypes) (Object) ((AccessorBakedGlyph) bakedglyph).malum$getRenderTypes()).malum$getSubtractiveType();
			VertexConsumer vertexconsumer = bufferSource.getBuffer(subtractiveType);

			for(BakedGlyph.Effect bakedglyph$effect : malum$inverseEffects) {
				bakedglyph.renderEffect(bakedglyph$effect, pose, vertexconsumer, packedLightCoords);
			}
		}
	}

}
