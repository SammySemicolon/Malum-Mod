package com.sammy.malum.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Font.class)
public interface AccessorFont {

    @Invoker("getFontSet")
    FontSet malum$getFontSet(ResourceLocation pFontLocation);
}
