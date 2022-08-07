package com.sammy.malum.core.systems.item;

import team.lodestar.lodestone.systems.model.LodestoneArmorModel;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Supplier;

public class ItemSkin {
    public static final String MALUM_SKIN_TAG = "malum:item_skin";

    public final String key;
    public final ResourceLocation armorTextureLocation;
    public final Supplier<LodestoneArmorModel> modelSupplier;
    public final int index;
    public Supplier<DatagenData> dataSupplier;

    public ItemSkin(String key, ResourceLocation armorTextureLocation, Supplier<LodestoneArmorModel> modelSupplier, int index) {
        this.key = key;
        this.armorTextureLocation = armorTextureLocation;
        this.modelSupplier = modelSupplier;
        this.index = index;
    }

    public ItemSkin addDatagenData(Supplier<DatagenData> data) {
        this.dataSupplier = data;
        return this;
    }


    public record DatagenData(ResourceLocation itemTexturePath,
                              ResourceLocation itemModelPath,
                              List<String> itemTextureNames) {
    }
}