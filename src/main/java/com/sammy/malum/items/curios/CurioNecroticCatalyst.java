
package com.sammy.malum.items.curios;

import com.sammy.malum.SpiritConsumer;
import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.SpiritDescription;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.makeGenericSpiritDependantTooltip;

public class CurioNecroticCatalyst extends Item implements ICurio, SpiritConsumer, SpiritDescription
{
    public CurioNecroticCatalyst(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            @Override
            public void playRightClickEquipSound(LivingEntity livingEntity)
            {
                livingEntity.world.playSound(null, livingEntity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }
            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }
        });
    }
    @Override
    public int durability()
    {
        return 10;
    }

    @Override
    public String spirit()
    {
        return "minecraft:zombie";
    }

    @Override
    public ArrayList<ITextComponent> components()
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(makeGenericSpiritDependantTooltip("malum.tooltip.necrotic_catalyst.effect", SpiritDataHelper.getName(spirit())));
        return components;
    }
}