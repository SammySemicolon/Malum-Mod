package com.sammy.malum.common.cosmetic.ancient;

import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.common.cosmetic.*;
import com.sammy.malum.common.item.curiosities.armor.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

import static com.sammy.malum.MalumMod.*;

public class AncientClothArmorSkin extends ArmorSkin {
    public AncientClothArmorSkin(String id, Item weaveItem) {
        super(id, SoulHunterArmorItem.class, weaveItem);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/ancient_soul_hunter.png"), ModelRegistry.ANCIENT_SOUL_HUNTER_ARMOR);
    }
}
