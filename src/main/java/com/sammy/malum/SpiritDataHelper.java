package com.sammy.malum;

import com.sammy.malum.blocks.utility.spiritstorage.SpiritStoringTileEntity;
import com.sammy.malum.capabilities.MalumDataProvider;
import com.sammy.malum.events.SpiritHarvestEvent;
import com.sammy.malum.events.SpiritIntegrityUpdateEvent;
import com.sammy.malum.init.ModEventFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.Optional;

@SuppressWarnings("unused")
public class SpiritDataHelper
{
    public static final String countNBT = "malum:spirit_count";
    public static final String typeNBT = "malum:spirit_type";
    public static final String spiritIntegrityNBT = "malum:spirit_integrity";
    
    public static Optional<EntityType<?>> getType(String name)
    {
        return EntityType.byKey(name);
    }
    
    public static boolean lacksSpirit(LivingEntity livingEntity)
    {
        return MalumDataProvider.getDread(livingEntity) || MalumDataProvider.getCharm(livingEntity);
    }
    
    //region TOOLTIPS
    public static String getName(String name)
    {
        return getType(name).map(entityType -> entityType.getName().getString()).orElse(null);
    }
    
    public static String getSpirit(LivingEntity entity)
    {
        return entity.getType().getRegistryName().toString();
    }
    
    //endregion
    
    //region BLOCKS
    
    public static boolean extractSpiritFromStorage(ItemStack stack, SpiritStoringTileEntity tileEntity, int cap, String spirit)
    {
        if (doesStorageHaveSpirit(tileEntity))
        {
            if (doesItemHaveSpirit(stack))
            {
                if (doesItemHaveSpirit(stack, spirit))
                {
                    if (stack.getTag().getInt(countNBT) < ((SpiritStorage) stack.getItem()).capacity())
                    {
                        decreaseSpiritOfStorage(tileEntity, spirit);
                        increaseSpiritOfItem(stack, spirit);
                        return true;
                    }
                }
            }
            else
            {
                decreaseSpiritOfStorage(tileEntity, spirit);
                stack.getOrCreateTag().putString(typeNBT, spirit);
                stack.getTag().putInt(countNBT, 1);
                return true;
            }
        }
        return false;
    }
    
    public static boolean insertSpiritIntoStorage(ItemStack stack, SpiritStoringTileEntity tileEntity, int cap, String spirit)
    {
        if (doesStorageHaveSpirit(tileEntity))
        {
            if (doesStorageHaveSpirit(tileEntity, spirit))
            {
                if (doesItemHaveSpirit(stack, spirit))
                {
                    if (tileEntity.count < cap)
                    {
                        increaseSpiritOfStorage(tileEntity, cap, spirit);
                        decreaseSpiritOfItem(stack, spirit);
                        return true;
                    }
                }
            }
        }
        else
        {
            increaseSpiritOfStorage(tileEntity, cap, spirit);
            decreaseSpiritOfItem(stack, spirit);
            return true;
        }
        return false;
    }
    
    public static boolean decreaseSpiritOfStorage(SpiritStoringTileEntity tileEntity, String spirit)
    {
        if (doesStorageHaveSpirit(tileEntity))
        {
            if (doesStorageHaveSpirit(tileEntity, spirit))
            {
                if (tileEntity.count > 0)
                {
                    tileEntity.count -= 1;
                    if (tileEntity.count == 0)
                    {
                        tileEntity.type = null;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean increaseSpiritOfStorage(SpiritStoringTileEntity tileEntity, int cap, String spirit)
    {
        if (doesStorageHaveSpirit(tileEntity))
        {
            if (doesStorageHaveSpirit(tileEntity, spirit))
            {
                if (tileEntity.count < cap)
                {
                    tileEntity.count = Math.min(tileEntity.count + 1, cap);
                    return true;
                }
            }
        }
        else
        {
            tileEntity.type = spirit;
            tileEntity.count = 1;
            return true;
        }
        return false;
    }
    
    public static boolean doesStorageHaveSpirit(SpiritStoringTileEntity tileEntity)
    {
        if (tileEntity.type != null)
        {
            return tileEntity.count > 0;
        }
        return false;
    }
    
    public static boolean doesStorageHaveSpirit(SpiritStoringTileEntity tileEntity, String spirit)
    {
        if (tileEntity.type != null)
        {
            if (tileEntity.type.equals(spirit))
            {
                return tileEntity.count > 0;
            }
        }
        return false;
    }
    //endregion
    
    //region COUNT
    public static void harvestSpirit(PlayerEntity player, ItemStack heldStack, LivingEntity target, String spirit, int amount)
    {
        SpiritHarvestEvent.Pre preEvent = ModEventFactory.preSpiritHarvest(target, player);
        amount += preEvent.extraSpirits;
        int i = 0;
        if (heldStack.getItem() instanceof SpiritStorage)
        {
            for (int k = 0; k < amount; k++)
            {
                boolean increase = increaseSpiritOfItem(heldStack, spirit);
                if (increase)
                {
                    i++;
                    if (i >= amount)
                    {
                        ModEventFactory.postSpiritHarvest(target, player, i);
                        return;
                    }
                }
            }
        }
        if (player.getHeldItemOffhand().getItem() instanceof SpiritStorage)
        {
            for (int k = 0; k < amount; k++)
            {
                boolean increase = increaseSpiritOfItem(player.getHeldItemOffhand(), spirit);
                if (increase)
                {
                    i++;
                    if (i >= amount)
                    {
                        ModEventFactory.postSpiritHarvest(target, player, i);
                        return;
                    }
                }
            }
        }
        for (ItemStack stack : player.inventory.mainInventory)
        {
            if (stack.getItem() instanceof SpiritStorage)
            {
                for (int k = 0; k < amount; k++)
                {
                    boolean increase = increaseSpiritOfItem(stack, spirit);
                    if (increase)
                    {
                        i++;
                        if (i >= amount)
                        {
                            ModEventFactory.postSpiritHarvest(target, player, i);
                            return;
                        }
                    }
                }
            }
        }
        ModEventFactory.postSpiritHarvest(target, player, i);
    }
    
    public static boolean decreaseSpiritOfItem(ItemStack stack, String spirit)
    {
        if (stack.getItem() instanceof SpiritStorage)
        {
            if (stack.getTag() != null)
            {
                CompoundNBT nbt = stack.getTag();
                if (doesItemHaveSpirit(stack))
                {
                    if (doesItemHaveSpirit(stack, spirit))
                    {
                        if (nbt.getInt(countNBT) > 0)
                        {
                            nbt.putInt(countNBT, nbt.getInt(countNBT) - 1);
                            if (nbt.getInt(countNBT) == 0)
                            {
                                nbt.remove(typeNBT);
                                nbt.remove(countNBT);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean increaseSpiritOfItem(ItemStack stack, String spirit)
    {
        if (stack.getItem() instanceof SpiritStorage)
        {
            CompoundNBT nbt = stack.getTag();
            if (doesItemHaveSpirit(stack))
            {
                if (doesItemHaveSpirit(stack, spirit))
                {
                    if (nbt.getInt(countNBT) < ((SpiritStorage) stack.getItem()).capacity())
                    {
                        nbt.putInt(countNBT, Math.min(nbt.getInt(countNBT) + 1, ((SpiritStorage) stack.getItem()).capacity()));
                        return true;
                    }
                }
            }
            else
            {
                nbt.putString(typeNBT, spirit);
                nbt.putInt(countNBT, 1);
                return true;
            }
        }
        return false;
    }
    
    public static boolean doesItemHaveSpirit(ItemStack stack, String spirit)
    {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (nbt.contains(typeNBT))
        {
            if (nbt.getString(typeNBT).equals(spirit))
            {
                return nbt.getInt(countNBT) > 0;
            }
        }
        return false;
    }
    
    public static boolean doesItemHaveSpirit(ItemStack stack)
    {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (nbt.contains(typeNBT))
        {
            return nbt.getInt(countNBT) > 0;
        }
        return false;
    }
    //endregion
    
    //region DURABILITY
    
    public static boolean consumeSpirit(PlayerEntity player, ItemStack targetStack)
    {
        if (targetStack.getItem() instanceof SpiritConsumer)
        {
            CompoundNBT nbt = targetStack.getOrCreateTag();
            if (nbt.getInt(spiritIntegrityNBT) > 0)
            {
                int change = 1;
                SpiritIntegrityUpdateEvent.Decrease event = ModEventFactory.decreaseSpiritIntegrity(targetStack, player, change);
                change = event.integrityChange;
                if (!event.isCanceled())
                {
                    nbt.putInt(spiritIntegrityNBT, nbt.getInt(spiritIntegrityNBT) - change);
                }
                return true;
            }
            else
            {
                nbt.remove(spiritIntegrityNBT);
                for (ItemStack stack : player.inventory.mainInventory)
                {
                    if (stack.getItem() instanceof SpiritStorage)
                    {
                        if (stack.getOrCreateTag().getInt(countNBT) > 0)
                        {
                            boolean success = decreaseSpiritOfItem(stack, ((SpiritConsumer) targetStack.getItem()).spirit());
                            if (success)
                            {
                                int value = ((SpiritConsumer) targetStack.getItem()).durability();
                                SpiritIntegrityUpdateEvent.Fill event = ModEventFactory.fillSpiritIntegrity(targetStack, player, value);
                                value = event.integrityChange;
                                nbt.putInt(spiritIntegrityNBT, value);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean doesItemHaveSpiritIntegrity(ItemStack stack)
    {
        if (stack.getTag() != null)
        {
            CompoundNBT nbt = stack.getTag();
            return nbt.contains(spiritIntegrityNBT);
        }
        return false;
    }
    //endregion
}