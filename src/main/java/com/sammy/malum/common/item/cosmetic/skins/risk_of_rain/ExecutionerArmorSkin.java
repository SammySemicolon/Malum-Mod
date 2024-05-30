package com.sammy.malum.common.item.cosmetic.skins.risk_of_rain;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.SimpleArmorSkinRenderingData;
import com.sammy.malum.client.model.cosmetic.risky.ExecutionerArmorModel;
import com.sammy.malum.common.entity.EntityModelLoader;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

import static com.sammy.malum.MalumMod.malumPath;

public class ExecutionerArmorSkin extends ArmorSkin {

    public static ExecutionerArmorModel EXECUTIONER;

    public ExecutionerArmorSkin(String id, Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem) {
        super(id, validArmorClass, weaveItem);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        EXECUTIONER = new ExecutionerArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(ExecutionerArmorModel.LAYER));

        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/risk_of_rain/executioner.png"), EXECUTIONER);
    }
}
