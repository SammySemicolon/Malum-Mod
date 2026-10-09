package com.sammy.malum.mixin.client;

import net.minecraft.client.gui.Font;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Font.StringRenderOutput.class)
public interface StringRenderOutputAccessor {
    @Accessor("this$0")
    Font malum$getFont();
}
