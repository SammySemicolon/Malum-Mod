package com.sammy.malum.common.item.equipment.armor.vanity;

import com.sammy.malum.client.model.DripArmor;
import com.sammy.malum.common.item.equipment.armor.SoulHunterArmorItem;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class DripArmorItem extends SoulHunterArmorItem
{
    public DripArmorItem(EquipmentSlot slot, Properties builder)
    {
        super(slot, builder);
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            this.model = DistExecutor.runForDist(() -> () -> new LazyLoadedValue<>(() -> new DripArmor(slot)), () -> () -> null);
        }
    }

    @Override
    public String getTexture()
    {
        return "drip_armor";
    }
}
