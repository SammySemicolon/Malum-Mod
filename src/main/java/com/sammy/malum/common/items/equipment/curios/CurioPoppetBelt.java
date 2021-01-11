package com.sammy.malum.common.items.equipment.curios;

import com.sammy.malum.common.items.equipment.poppets.PoppetItem;
import com.sammy.malum.core.systems.curios.CurioProvider;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CurioPoppetBelt extends Item implements ICurio
{
    public CurioPoppetBelt(Properties builder)
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack belt = playerIn.getHeldItem(handIn);
        ItemStack poppet = playerIn.getHeldItem(handIn.equals(Hand.MAIN_HAND) ? Hand.OFF_HAND : Hand.MAIN_HAND);
        CompoundNBT nbt = belt.getOrCreateTag();
        if (poppet.getItem() instanceof PoppetItem)
        {
            SimpleInventory inventory = create(belt);
            if (inventory.nonEmptyItems() < 3)
            {
                inventory.setStackInSlot(inventory.firstEmptyItem(), poppet);
                inventory.writeData(nbt);
                poppet.shrink(1);
            }
        }
        else if (playerIn.isSneaking())
        {
            SimpleInventory inventory = create(belt);
            inventory.dumpItems(worldIn, playerIn.getPositionVec());
            inventory.writeData(nbt);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    public static SimpleInventory create(ItemStack stack)
    {
        SimpleInventory inventory = new SimpleInventory(3,1);
        inventory.readData(stack.getOrCreateTag());
        return inventory;
    }
}


