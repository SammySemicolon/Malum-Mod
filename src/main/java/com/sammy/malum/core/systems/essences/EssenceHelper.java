package com.sammy.malum.core.systems.essences;

import com.sammy.malum.core.init.essences.MalumEssenceTypes;
import com.sammy.malum.core.systems.essences.SimpleEssenceType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EssenceHelper
{
    public static boolean validate(ItemStack stack)
    {
        return stack.getItem() instanceof IEssenceHolder;
    }
    
    public static SimpleEssenceType figureOutEssence(String identifier)
    {
        for (SimpleEssenceType essenceType : MalumEssenceTypes.ESSENCES)
        {
            if (essenceType.identifier.equals(identifier))
            {
                return essenceType;
            }
        }
        return null;
    }
    
    public static int essenceCount(ItemStack stack)
    {
        int i = 0;
        CompoundNBT compoundNBT = stack.getOrCreateTag();
        for (SimpleEssenceType essenceType : MalumEssenceTypes.ESSENCES)
        {
            if (compoundNBT.contains(essenceType.identifier))
            {
                i++;
            }
        }
        return i;
    }
    
    public static int giveItemEssence(ItemStack stack, SimpleEssenceType type, int amount)
    {
        int extra = 0;
        if (validate(stack))
        {
            IEssenceHolder holder = (IEssenceHolder) stack.getItem();
            CompoundNBT compoundNBT = stack.getOrCreateTag();
            int simulatedResult = compoundNBT.getInt(type.identifier) + amount;
            if (simulatedResult > holder.getMaxEssence())
            {
                extra = simulatedResult - holder.getMaxEssence();
                simulatedResult = holder.getMaxEssence();
            }
            compoundNBT.putInt(type.identifier, simulatedResult);
        }
        return extra;
    }
    
    public static HashMap<SimpleEssenceType, Integer> essencesToGive(LivingEntity target, PlayerEntity player, float multiplier)
    {
        HashMap<SimpleEssenceType, Integer> essencesToGive = new HashMap<>();
        for (SimpleEssenceType essenceType : MalumEssenceTypes.ESSENCES)
        {
            if (essenceType.doesEntityHaveEssence(target))
            {
                essencesToGive.put(essenceType, (int) (essenceType.howMuchEssenceDoesAnEntityHave(player, target) * multiplier));
            }
        }
        return essencesToGive;
    }
    
    public static void harvestEssence(LivingEntity target, PlayerEntity player, float multiplier)
    {
        harvestEssence(essencesToGive(target, player, multiplier), player);
    }
    public static void harvestEssence(HashMap<SimpleEssenceType, Integer> essencesToGive, PlayerEntity player)
    {
        List<ItemStack> items = new ArrayList<>();
        if (validate(player.getHeldItemOffhand()))
        {
            items.add(player.getHeldItemOffhand());
        }
        
        items.addAll(player.inventory.mainInventory.stream().filter(EssenceHelper::validate).collect(Collectors.toList()));
        if (items.isEmpty())
        {
            return;
        }
        for (ItemStack stack : items)
        {
            for (int i = 0; i < essencesToGive.size(); i++)
            {
                SimpleEssenceType essenceType = (SimpleEssenceType) essencesToGive.keySet().toArray()[i];
                if (essencesToGive.get(essenceType) == 0)
                {
                    continue;
                }
                IEssenceHolder holder = (IEssenceHolder) stack.getItem();
                if (essenceCount(stack) >= holder.getEssenceSlots() && !stack.getTag().contains(essenceType.identifier))
                {
                    continue;
                }
                int extra = giveItemEssence(stack, essenceType, essencesToGive.get(essenceType));
                essencesToGive.replace(essenceType, extra);
            }
        }
    }
}