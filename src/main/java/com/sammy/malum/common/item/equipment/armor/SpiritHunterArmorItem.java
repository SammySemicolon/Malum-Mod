package com.sammy.malum.common.item.equipment.armor;

import com.sammy.malum.client.model.SpiritHunterArmor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.loading.FMLEnvironment;

import static com.sammy.malum.core.init.items.MalumArmorTiers.ArmorTierEnum.SPIRIT_HUNTER;
import static com.sammy.malum.core.init.items.MalumItems.*;

@SuppressWarnings("unchecked")
public class SpiritHunterArmorItem extends MalumArmorItem
{
    public static final RegistryObject<Item>[] ARMOR = new RegistryObject[]{SPIRIT_HUNTER_CLOAK, SPIRIT_HUNTER_ROBE, SPIRIT_HUNTER_LEGGINGS, SPIRIT_HUNTER_BOOTS};
    public SpiritHunterArmorItem(EquipmentSlotType slot, Properties builder)
    {
        super(SPIRIT_HUNTER, slot, builder);
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            model = DistExecutor.runForDist(() -> () -> new LazyValue<>(() -> new SpiritHunterArmor(slot)), () -> () -> null);
        }
    }

    @Override
    public RegistryObject<Item>[] getArmorList()
    {
        return ARMOR;
    }

    public String texture()
    {
        return "spirit_hunter_armor";
    }
}