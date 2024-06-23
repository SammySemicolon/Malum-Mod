package com.sammy.malum.visual_effects.networked;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public interface ClientSupplier<T> extends Supplier<T> {
    @Override
    @Environment(EnvType.CLIENT)
    T get();
}
