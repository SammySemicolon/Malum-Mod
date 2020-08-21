package com.kittykitcatcat.malum;

import com.kittykitcatcat.malum.blocks.utility.spiritstorage.SpiritStoringTileEntity;
import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kittykitcatcat.malum.MalumHelper.*;
import static com.kittykitcatcat.malum.init.ModTooltips.*;
import static net.minecraft.util.text.TextFormatting.*;

@SuppressWarnings("unused")
public class SpiritDataHelper
{
    public static final String countNBT = "malum:spiritCount";
    public static final String typeNBT = "malum:spiritType";
    public static final String spiritIntegrityNBT = "malum:spiritIntegrity";

    public static Optional<EntityType<?>> getType(String name)
    {
        return EntityType.byKey(name);
    }
    public static boolean getHusk(LivingEntity entity)
    {
        return CapabilityValueGetter.getHusk(entity);
    }
    public static void setHusk(LivingEntity entity, boolean husk)
    {
        CapabilityValueGetter.setHusk(entity, husk);
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

    @OnlyIn(Dist.CLIENT)
    public static ITextComponent makeImportantMessage(String message, String importantMessage)
    {
        //message [importantMessage]
        return new TranslationTextComponent(message).applyTextStyle(WHITE) //message
                .appendSibling(makeImportantComponent(importantMessage, true)); //[importantMessage]
    }
    @OnlyIn(Dist.CLIENT)
    public static ITextComponent makeGenericSpiritDependantTooltip(String message, String spirit)
    {
        //Uses [spiritType] spirits to [message]
        return new TranslationTextComponent("malum.tooltip.sconsumer.desc.c").applyTextStyle(WHITE) //Uses
                .appendSibling(makeImportantComponent(spirit, true))
                .appendSibling(new TranslationTextComponent("malum.tooltip.sconsumer.desc.d"))
                .appendSibling(makeImportantComponent(message, true));
    }
    @OnlyIn(Dist.CLIENT)
    public static void makeSpiritTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        ArrayList<ITextComponent> newComponents = new ArrayList<>();
        if (tooltips.containsKey(stack.getItem()))
        {
            newComponents.addAll(tooltips.get(stack.getItem()));
        }
        if (stack.getItem() instanceof SpiritStorage)
        {
            if (doesItemHaveSpirit(stack))
            {
                SpiritStorage spiritStorage = (SpiritStorage) stack.getItem();
                if (spiritStorage.capacity() != 0)
                {
                    newComponents.add(new TranslationTextComponent("malum.tooltip.sstorage.desc").applyTextStyle(WHITE) //contains
                            .appendSibling(makeImportantComponent(stack.getTag().getInt(countNBT) + "/" + spiritStorage.capacity(), true)) //[amount/max]
                            .appendSibling(makeImportantComponent(getName(stack.getTag().getString(typeNBT)), true)) //[spiritType]
                            .appendSibling(new TranslationTextComponent("malum.tooltip.spirit.desc.c").applyTextStyle(WHITE))); //spirits
                }
            }
        }
        if (stack.getItem() instanceof SpiritConsumer)
        {
            if (stack.getTag() != null)
            {
                SpiritConsumer spiritStorage = (SpiritConsumer) stack.getItem();
                if (spiritStorage.durability() != 0)
                {
                    newComponents.add(new TranslationTextComponent("malum.tooltip.sconsumer.desc.a").applyTextStyle(WHITE) //has
                            .appendSibling(makeImportantComponent(stack.getTag().getInt(spiritIntegrityNBT) + "/" + spiritStorage.durability(), true)) //[amount/max]
                            .appendSibling(new TranslationTextComponent("malum.tooltip.sconsumer.desc.b")).applyTextStyle(WHITE)); //spirit integrity
                }
            }
        }

        if (stack.getItem() instanceof SpiritDescription)
        {
            SpiritDescription spiritStorage = (SpiritDescription) stack.getItem();
            if (spiritStorage.components() != null && !spiritStorage.components().isEmpty())
            {
                newComponents.addAll(spiritStorage.components());
            }
        }
        if (!newComponents.isEmpty())
        {
            makeTooltip(stack, worldIn, tooltip, flagIn, newComponents);
        }
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
    public static boolean decreaseSpiritOfStorage(SpiritStoringTileEntity tileEntity,String spirit)
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
    public static boolean increaseSpiritOfStorage(SpiritStoringTileEntity tileEntity, int cap,String spirit)
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
    public static void harvestSpirit(PlayerEntity player, String spirit, int amount)
    {
        amount+= CapabilityValueGetter.getExtraSpirits(player);
        int i = 0;
        if (player.inventory.getCurrentItem().getItem() instanceof SpiritStorage)
        {
            ItemStack stack = player.inventory.getCurrentItem();
            if (stack.getOrCreateTag().getString(typeNBT).equals(spirit))
            {
                if (stack.getTag().getInt(countNBT) < ((SpiritStorage) stack.getItem()).capacity())
                {
                    for (int k = 0; k < amount; k++)
                    {
                        boolean increase = increaseSpiritOfItem(stack, spirit);
                        if (increase)
                        {
                            i++;
                            if (i >= amount)
                            {
                                return;
                            }
                        }
                    }
                }
            }
        }
        for (ItemStack stack : player.inventory.mainInventory)
        {
            if (stack.getItem() instanceof SpiritStorage)
            {
                if (stack.getOrCreateTag().getInt(countNBT) < ((SpiritStorage) stack.getItem()).capacity())
                {
                    for (int k = 0; k < amount; k++)
                    {
                        boolean increase = increaseSpiritOfItem(stack, spirit);
                        if (increase)
                        {
                            i++;
                            if (i >= amount)
                            {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    public static boolean decreaseSpiritOfItem(ItemStack stack,String spirit)
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
    public static boolean increaseSpiritOfItem(ItemStack stack,String spirit)
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
        }
        return false;
    }

    public static boolean doesItemHaveSpirit(ItemStack stack, String spirit)
    {
        if (stack.getTag() != null)
        {
            CompoundNBT nbt = stack.getTag();
            if (nbt.contains(typeNBT))
            {
                if (nbt.getString(typeNBT).equals(spirit))
                {
                    return nbt.getInt(countNBT) > 0;
                }
            }
        }
        return false;
    }
    public static boolean doesItemHaveSpirit(ItemStack stack)
    {
        if (stack.getTag() != null)
        {
            CompoundNBT nbt = stack.getTag();
            if (nbt.contains(typeNBT))
            {
                return nbt.getInt(countNBT) > 0;
            }
        }
        return false;
    }
    //endregion

    //region DURABILITY

    public static boolean simulatedConsumeSpirit(PlayerEntity player, ItemStack targetStack)
    {
        if (targetStack.getItem() instanceof SpiritConsumer)
        {
            if (targetStack.getTag() != null)
            {
                CompoundNBT nbt = targetStack.getTag();
                if (nbt.getInt(spiritIntegrityNBT) > 0)
                {
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
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static boolean consumeSpirit(PlayerEntity player, ItemStack targetStack)
    {
        if (targetStack.getItem() instanceof SpiritConsumer)
        {
            if (targetStack.getTag() != null)
            {
                CompoundNBT nbt = targetStack.getTag();
                if (nbt.getInt(spiritIntegrityNBT) > 0)
                {
                    nbt.putInt(spiritIntegrityNBT, nbt.getInt(spiritIntegrityNBT) - 1);
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
                                    nbt.putInt(spiritIntegrityNBT, ((SpiritConsumer) targetStack.getItem()).durability());
                                    return true;
                                }
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

    //region VALUES

    //endregion
}