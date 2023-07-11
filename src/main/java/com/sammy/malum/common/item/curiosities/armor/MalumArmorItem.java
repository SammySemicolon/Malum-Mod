package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.common.cosmetic.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.systems.item.*;

public class MalumArmorItem extends LodestoneArmorItem {
    public MalumArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        ArmorSkin skin = ArmorSkin.getAppliedItemSkin(stack);
        if (skin != null && entity instanceof LivingEntity livingEntity) {
            return ArmorSkinRegistry.ClientOnly.SKIN_RENDERING_DATA.get(skin).getTexture(livingEntity).toString();
        }
        return super.getArmorTexture(stack, entity, slot, type);
    }
}
