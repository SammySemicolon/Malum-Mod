package com.sammy.malum.mixin;

import net.minecraft.client.gui.font.GlyphRenderTypes;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BakedGlyph.class)
public interface AccessorBakedGlyph {

    @Accessor("renderTypes")
    GlyphRenderTypes malum$getRenderTypes();
}
