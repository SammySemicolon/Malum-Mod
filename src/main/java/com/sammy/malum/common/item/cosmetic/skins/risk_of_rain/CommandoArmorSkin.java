package com.sammy.malum.common.item.cosmetic.skins.risk_of_rain;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.SimpleArmorSkinRenderingData;
import com.sammy.malum.client.model.cosmetic.risky.CommandoArmorModel;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

import static com.sammy.malum.MalumMod.malumPath;

public class CommandoArmorSkin extends ArmorSkin {
    public static CommandoArmorModel COMMANDO;

    public CommandoArmorSkin(String id, Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem) {
        super(id, validArmorClass, weaveItem);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        COMMANDO = new CommandoArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(CommandoArmorModel.LAYER));

        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/risk_of_rain/commando.png"), COMMANDO);
    }
}
