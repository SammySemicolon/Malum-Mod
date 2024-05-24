package com.sammy.malum.common.item.cosmetic.skins;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.PrideArmorSkinRenderingData;
import net.minecraft.world.item.Item;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

public class PrideArmorSkin extends ArmorSkin {
    public PrideArmorSkin(String id, Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem) {
        super(id, validArmorClass, weaveItem);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        return new PrideArmorSkinRenderingData(id);
    }
}
