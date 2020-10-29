package com.sammy.malum.items.curios;

import com.sammy.malum.SpiritConsumer;
import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.SpiritDescription;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.makeGenericSpiritDependantTooltip;

public class CurioEnchantedLectern extends Item implements ICurio, SpiritConsumer, SpiritDescription
{
    public CurioEnchantedLectern(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {});
    }
    
    @Override
    public int durability()
    {
        return 20;
    }
    
    @Override
    public String spirit()
    {
        return "minecraft:witch";
    }
    
    @Override
    public ArrayList<ITextComponent> components()
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(makeGenericSpiritDependantTooltip("malum.tooltip.enchanted_lectern.effect", SpiritDataHelper.getName(spirit())));
        return components;
    }
}