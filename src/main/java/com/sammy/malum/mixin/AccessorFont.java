package com.sammy.malum.mixin;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.font.*;
import net.minecraft.resources.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(Font.class)
public interface AccessorFont {

    @Invoker("getFontSet")
    FontSet malum$getFontSet(ResourceLocation pFontLocation);
}
