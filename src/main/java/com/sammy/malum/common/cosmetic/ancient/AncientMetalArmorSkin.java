package com.sammy.malum.common.cosmetic.ancient;

import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.common.cosmetic.*;
import com.sammy.malum.common.item.curiosities.armor.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

import static com.sammy.malum.MalumMod.*;

public class AncientMetalArmorSkin extends ArmorSkin {
    public AncientMetalArmorSkin(String id, Item weaveItem) {
        super(id, SoulStainedSteelArmorItem.class, weaveItem);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/ancient_soul_stained_steel.png"), ModelRegistry.ANCIENT_SOUL_STAINED_STEEL_ARMOR);
    }
}
