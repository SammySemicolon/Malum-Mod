package com.sammy.malum.core.systems.essences;

import com.sammy.malum.core.init.essences.MalumEssenceTypes;
import com.sammy.malum.core.systems.essences.SimpleEssenceType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class EssenceHelper
{
    public static void giveItemEssences(LivingEntity entity, ItemStack stack)
    {
        for (SimpleEssenceType essenceType : MalumEssenceTypes.ESSENCES)
        {
            if (essenceType.doesEntityHaveEssence(entity))
            {
                giveItemEssence(entity,stack,essenceType);
            }
        }
    }
    public static void giveItemEssence(LivingEntity entity, ItemStack stack, SimpleEssenceType type)
    {
        CompoundNBT compoundNBT = stack.getOrCreateTag();
        int value = compoundNBT.getInt(type.identifier) + type.howMuchEssenceDoesAnEntityHave(entity);
        
        compoundNBT.putInt(type.identifier, value);
    }
}
