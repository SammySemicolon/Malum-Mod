package com.sammy.malum.core.systems.spirits;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.core.init.essences.MalumSpiritTypes;
import com.sammy.malum.core.systems.spirits.block.ISpiritHolderTileEntity;
import com.sammy.malum.core.systems.spirits.block.ISpiritRequestTileEntity;
import com.sammy.malum.core.systems.spirits.block.ISpiritTransferTileEntity;
import com.sammy.malum.core.systems.spirits.item.IEssenceHolder;
import com.sammy.malum.core.systems.spirits.types.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpiritHelper
{
    public static boolean validate(ItemStack stack)
    {
        return stack.getItem() instanceof IEssenceHolder;
    }
    public static ArrayList<Pair<String, Integer>> itemSpirits(ItemStack stack, boolean includeEmpty)
    {
        ArrayList<Pair<String, Integer>> map = new ArrayList<>();
        if (validate(stack))
        {
            CompoundNBT tag = stack.getOrCreateTag();
            IEssenceHolder holder = (IEssenceHolder) stack.getItem();
            for (int i = 0; i < holder.getEssenceSlots(); i++)
            {
                if (tag.contains("essenceIdentifier" + i))
                {
                    if (tag.getInt("essenceCount" + i) > 0)
                    {
                        map.add(Pair.of(tag.getString("essenceIdentifier" + i), tag.getInt("essenceCount" + i)));
                    }
                    else
                    {
                        if (includeEmpty)
                        {
                            map.add(Pair.of("empty", 0));
                        }
                        tag.remove("essenceIdentifier" + i);
                        tag.remove("essenceCount" + i);
                    }
                }
                else if (includeEmpty)
                {
                    map.add(Pair.of("empty", 0));
                }
            }
        }
        return map;
    }
    public static Pair<String, Integer> itemSpirit(ItemStack stack, int slot)
    {
        Pair<String, Integer> pair = null;
        if (validate(stack))
        {
            CompoundNBT tag = stack.getOrCreateTag();
            if (tag.contains("essenceIdentifier" + slot))
            {
                if (tag.getInt("essenceCount" + slot) > 0)
                {
                    pair = new Pair<>(tag.getString("essenceIdentifier" + slot), tag.getInt("essenceCount" + slot));
                }
                else
                {
                    tag.remove("essenceIdentifier" + slot);
                    tag.remove("essenceCount" + slot);
                }
            }
        }
        return pair;
    }
    public static void addSpirit(ItemStack stack, String identifier, int count, int slot)
    {
        CompoundNBT tag = stack.getOrCreateTag();
        tag.putString("essenceIdentifier" + slot, identifier);
        tag.putInt("essenceCount" + slot, count);
    }
    public static int giveStackSpirit(ItemStack stack, String identifier, int count)
    {
        int extra = 0;
        if (validate(stack))
        {
            IEssenceHolder holder = (IEssenceHolder) stack.getItem();
            CompoundNBT tag = stack.getOrCreateTag();
            for (int i = 0; i < holder.getEssenceSlots(); i++)
            {
                int simulatedResult = tag.getInt("essenceCount" + i) + count;
                if (simulatedResult > holder.getMaxEssence())
                {
                    extra = simulatedResult - holder.getMaxEssence();
                    simulatedResult = holder.getMaxEssence();
                }
                if (!tag.getString("essenceIdentifier" + i).equals("") && !tag.getString("essenceIdentifier" + i).equals(identifier))
                {
                    continue;
                }
                addSpirit(stack, identifier, simulatedResult, i);
                if (extra == 0)
                {
                    break;
                }
            }
        }
        return extra;
    }
    public static void harvestSpirit(ArrayList<Pair<String, Integer>> spirits, PlayerEntity player)
    {
        List<ItemStack> items = new ArrayList<>();
        if (validate(player.getHeldItemOffhand()))
        {
            items.add(player.getHeldItemOffhand());
        }
        
        items.addAll(player.inventory.mainInventory.stream().filter(SpiritHelper::validate).collect(Collectors.toList()));
        if (items.isEmpty())
        {
            return;
        }
        for (ItemStack stack : items)
        {
            for (int i = 0; i < spirits.size(); i++)
            {
                String spirit = spirits.get(i).getFirst();
                int count = spirits.get(i).getSecond();
                if (count == 0)
                {
                    continue;
                }
                IEssenceHolder holder = (IEssenceHolder) stack.getItem();
                ArrayList<Pair<String, Integer>> itemSpirits = itemSpirits(stack, false);
                if (itemSpirits.size() >= holder.getEssenceSlots() && itemSpirits.stream().noneMatch(p -> p.getFirst().equals(spirit)))
                {
                    continue;
                }
                int extra = giveStackSpirit(stack, spirit, count);
                spirits.set(i, Pair.of(spirit, extra));
            }
        }
    }
    public static ArrayList<Pair<String, Integer>> entitySpirits(LivingEntity entity)
    {
        ArrayList<Pair<String, Integer>> spirits = new ArrayList<>();
        for (MalumSpiritType type : MalumSpiritTypes.SPIRITS)
        {
            if (type.predicate.test(entity))
            {
                spirits.add(Pair.of(type.identifier, 1));
            }
        }
        return spirits;
    }
    public static boolean isSpiritRelated(TileEntity entity)
    {
        return entity instanceof ISpiritTransferTileEntity || entity instanceof ISpiritHolderTileEntity || entity instanceof ISpiritRequestTileEntity;
    }
}