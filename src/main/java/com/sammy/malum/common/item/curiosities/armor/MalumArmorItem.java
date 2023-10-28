package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

public class MalumArmorItem extends LodestoneArmorItem {
    public MalumArmorItem(ArmorMaterial materialIn, ArmorItem.Type slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        ArmorSkin skin = ArmorSkin.getAppliedItemSkin(stack);
        if (skin != null && entity instanceof LivingEntity livingEntity) {
            return ArmorSkinRenderingData.RENDERING_DATA.apply(skin).getTexture(livingEntity).toString();
        }
        return super.getArmorTexture(stack, entity, slot, type);
    }
}
