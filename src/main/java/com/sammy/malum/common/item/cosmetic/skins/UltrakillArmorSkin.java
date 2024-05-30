package com.sammy.malum.common.item.cosmetic.skins;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.SimpleArmorSkinRenderingData;
import com.sammy.malum.client.model.cosmetic.ultrakill.UltrakillMachineArmorModel;
import com.sammy.malum.common.entity.EntityModelLoader;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

import static com.sammy.malum.MalumMod.malumPath;

public class UltrakillArmorSkin extends ArmorSkin {

    public static UltrakillMachineArmorModel ULTRAKILL_MACHINE;

    public UltrakillArmorSkin(String id, Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem) {
        super(id, validArmorClass, weaveItem);

    }

    @Environment(EnvType.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        ULTRAKILL_MACHINE = new UltrakillMachineArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(UltrakillMachineArmorModel.LAYER));

        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/" + id + ".png"), ULTRAKILL_MACHINE);
    }
}
