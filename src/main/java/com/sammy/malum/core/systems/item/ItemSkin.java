package com.sammy.malum.core.systems.item;

import team.lodestar.lodestone.systems.model.LodestoneArmorModel;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class ItemSkin {

    public final ResourceLocation textureLocation;
    public final Supplier<LodestoneArmorModel> modelSupplier;

    public ItemSkin(ResourceLocation textureLocation, Supplier<LodestoneArmorModel> modelSupplier) {
        this.textureLocation = textureLocation;
        this.modelSupplier = modelSupplier;
    }
}