
package com.kittykitcatcat.malum.items.curios;

import com.kittykitcatcat.malum.SpiritDescription;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.capability.ICurio;

import java.util.ArrayList;

public class CurioVacantAegis extends Item implements ICurio, SpiritDescription
{
    public CurioVacantAegis(Properties builder)
    {
        super(builder);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            @Override
            public void playEquipSound(LivingEntity entityLivingBase)
            {
                entityLivingBase.world.playSound(null, entityLivingBase.getPosition(),
                        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL,
                        1.0f, 1.0f);
            }

            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }
        });
    }

    @Override
    public ArrayList<ITextComponent> components()
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(new TranslationTextComponent("malum.tooltip.aegis.desc"));
        return components;
    }
}