package com.sammy.malum.mixin;

import net.neoforged.bus.api.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Event.class)
public interface AccessorEvent {
    @Accessor("isCanceled")
    boolean malum$isCancelled();
}
