package com.sammy.malum.common.item.cosmetic.skins.risk_of_rain;

import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.common.item.cosmetic.skins.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.systems.item.*;

import static com.sammy.malum.MalumMod.*;

public class CommandoArmorSkin extends ArmorSkin {
    public CommandoArmorSkin(String id, Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem) {
        super(id, validArmorClass, weaveItem);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/risk_of_rain/commando.png"), ModelRegistry.COMMANDO);
    }
}
