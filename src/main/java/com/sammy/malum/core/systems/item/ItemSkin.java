package com.sammy.malum.core.systems.item;

import com.sammy.ortus.systems.model.OrtusArmorModel;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class ItemSkin {

    public final ResourceLocation textureLocation;
    public final Supplier<OrtusArmorModel> modelSupplier;

    public ItemSkin(ResourceLocation textureLocation, Supplier<OrtusArmorModel> modelSupplier) {
        this.textureLocation = textureLocation;
        this.modelSupplier = modelSupplier;
    }
}