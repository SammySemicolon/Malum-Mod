package com.sammy.malum.common.item.cosmetic.skins;

import com.sammy.malum.client.cosmetic.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.systems.item.*;

public class PrideArmorSkin extends ArmorSkin {
    public PrideArmorSkin(String id, Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem) {
        super(id, validArmorClass, weaveItem);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        return new PrideArmorSkinRenderingData(id);
    }
}
