package com.sammy.malum.mixin;

//@Mixin(GlyphRenderTypes.class)
//public class GlyphRenderTypesMixin implements SubtractiveTextGlyphRenderTypes {
//
//	@Unique
//	private RenderType malum$subtractive;
//
//	@Inject(method = "createForIntensityTexture", at = @At("RETURN"))
//	private static void setSubtractiveLayerForIntensity(ResourceLocation pId, CallbackInfoReturnable<GlyphRenderTypes> cir) {
//		((GlyphRenderTypesMixin) (Object) cir.getReturnValue()).malum$subtractive = RenderTypeRegistry.SUBTRACTIVE_INTENSE_TEXT.applyAndCache(RenderTypeToken.createCachedToken(pId));
//	}
//
//	@Inject(method = "createForColorTexture", at = @At("RETURN"))
//	private static void setSubtractiveLayerForColor(ResourceLocation pId, CallbackInfoReturnable<GlyphRenderTypes> cir) {
//		((GlyphRenderTypesMixin) (Object) cir.getReturnValue()).malum$subtractive = RenderTypeRegistry.SUBTRACTIVE_TEXT.applyAndCache(RenderTypeToken.createCachedToken(pId));
//	}
//
//	@Override
//	public RenderType malum$getSubtractiveType() {
//		return malum$subtractive;
//	}
//}
