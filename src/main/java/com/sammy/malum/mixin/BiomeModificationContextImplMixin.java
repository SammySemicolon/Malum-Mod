package com.sammy.malum.mixin;

import net.minecraft.core.RegistryAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(targets = {
        "net.fabricmc.fabric.impl.biome.modification.BiomeModificationContextImpl",
        "org.quiltmc.qsl.worldgen.biome.impl.modification.BiomeModificationContextImpl"
})
public interface BiomeModificationContextImplMixin {

    @Accessor("registries")
    RegistryAccess getRegistries();
}