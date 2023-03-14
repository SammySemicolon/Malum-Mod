package com.sammy.malum.core.systems.item;

import com.sammy.malum.registry.client.ItemSkinRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.function.Function;

public class ItemSkin {
    public static final String MALUM_SKIN_TAG = "malum:item_skin";

    public final Class<? extends LodestoneArmorItem> validArmorClass;
    public final Item weaveItem;
    public final Function<LivingEntity, ResourceLocation> textureFunction;
    public final Function<LivingEntity, LodestoneArmorModel> modelFunction;
    public final int index;

    public ItemSkin(Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem, Function<LivingEntity, ResourceLocation> textureFunction, Function<LivingEntity, LodestoneArmorModel> modelFunction) {
        this.validArmorClass = validArmorClass;
        this.weaveItem = weaveItem;
        this.textureFunction = textureFunction;
        this.modelFunction = modelFunction;
        this.index = ItemSkinRegistry.SKINS.size();
    }
    public static class ItemSkinDatagenData {
        public final String itemTexturePrefix;
        public final String itemModelPrefix;
        public final String helmetSuffix;
        public final String chestplateSuffix;
        public final String leggingsSuffix;
        public final String bootsSuffix;

        public ItemSkinDatagenData(String itemTexturePrefix, String itemModelPrefix, String helmetSuffix, String chestplateSuffix, String leggingsSuffix, String bootsSuffix) {
            this.itemTexturePrefix = itemTexturePrefix;
            this.itemModelPrefix = itemModelPrefix;
            this.helmetSuffix = helmetSuffix;
            this.chestplateSuffix = chestplateSuffix;
            this.leggingsSuffix = leggingsSuffix;
            this.bootsSuffix = bootsSuffix;
        }

        public String getSuffix(LodestoneArmorItem item) {
            switch (item.getSlot()) {
                case HEAD -> {
                    return helmetSuffix;
                }
                case CHEST -> {
                    return chestplateSuffix;
                }
                case LEGS -> {
                    return leggingsSuffix;
                }
                case FEET -> {
                    return bootsSuffix;
                }
            }
            return null;
        }
    }
}