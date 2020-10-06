
package com.kittykitcatcat.malum.items.curios;

import com.kittykitcatcat.malum.SpiritConsumer;
import com.kittykitcatcat.malum.SpiritDataHelper;
import com.kittykitcatcat.malum.SpiritDescription;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;

import static com.kittykitcatcat.malum.ClientHandler.makeGenericSpiritDependantTooltip;

public class CurioEnchantedLectern extends Item implements ICurio, SpiritConsumer, SpiritDescription
{
    public CurioEnchantedLectern(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused) {
        return CurioProvider.createProvider(new ICurio() {
        });
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