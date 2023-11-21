package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import net.minecraft.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

import java.util.*;

public class MalumArmorItem extends LodestoneArmorItem {

    //TODO: access transformer for the vanilla one in ArmorItem is being fucking stupid, just putting this here as a temporary measure
    protected static final EnumMap<ArmorItem.Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266744_) -> {
        p_266744_.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        p_266744_.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        p_266744_.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        p_266744_.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });

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
