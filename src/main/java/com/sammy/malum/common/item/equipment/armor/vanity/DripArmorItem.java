package com.sammy.malum.common.item.equipment.armor.vanity;

import com.sammy.malum.client.model.DripArmor;
import com.sammy.malum.common.item.equipment.armor.SoulHunterArmorItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;

import net.minecraft.item.Item.Properties;

public class DripArmorItem extends SoulHunterArmorItem
{
    public DripArmorItem(EquipmentSlotType slot, Properties builder)
    {
        super(slot, builder);
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            this.model = DistExecutor.runForDist(() -> () -> new LazyValue<>(() -> new DripArmor(slot)), () -> () -> null);
        }
    }

    @Override
    public String texture()
    {
        return "drip_armor";
    }
}
