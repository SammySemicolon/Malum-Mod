package com.sammy.malum.common.item.equipment.armor;

import com.sammy.malum.client.model.SoulHunterArmor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;

import static com.sammy.malum.core.init.items.MalumArmorTiers.ArmorTierEnum.SPIRIT_HUNTER;

public class SoulHunterArmorItem extends MalumArmorItem
{
    public SoulHunterArmorItem(EquipmentSlotType slot, Properties builder)
    {
        super(SPIRIT_HUNTER, slot, builder);
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            model = DistExecutor.runForDist(() -> () -> new LazyValue<>(() -> new SoulHunterArmor(slot)), () -> () -> null);
        }
    }


    public String texture()
    {
        return "spirit_hunter_armor";
    }
}