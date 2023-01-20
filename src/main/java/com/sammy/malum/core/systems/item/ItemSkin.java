package com.sammy.malum.core.systems.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemSkin {
    public static final String MALUM_SKIN_TAG = "malum:item_skin";

    public final String key;
    public final Function<LivingEntity, ResourceLocation> armorTextureFunction;
    public final Function<LivingEntity, LodestoneArmorModel> modelFunction;
    public final int index;
    public Supplier<DatagenData> dataSupplier;

    public ItemSkin(String key, Function<LivingEntity, ResourceLocation> armorTextureFunction, Function<LivingEntity, LodestoneArmorModel> modelFunction, int index) {
        this.key = key;
        this.armorTextureFunction = armorTextureFunction;
        this.modelFunction = modelFunction;
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