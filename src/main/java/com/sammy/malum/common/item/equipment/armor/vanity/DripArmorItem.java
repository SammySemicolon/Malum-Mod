package com.sammy.malum.common.item.equipment.armor.vanity;

import com.sammy.malum.common.item.equipment.armor.SoulHunterArmorItem;
import net.minecraft.world.entity.EquipmentSlot;

public class DripArmorItem extends SoulHunterArmorItem
{
    public DripArmorItem(EquipmentSlot slot, Properties builder)
    {
        super(slot, builder);
    }

    @Override
    public String getTexture()
    {
        return "drip_armor";
    }
}
