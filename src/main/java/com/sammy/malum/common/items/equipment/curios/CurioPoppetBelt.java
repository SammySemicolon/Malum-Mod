package com.sammy.malum.common.items.equipment.curios;

import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.equipment.poppets.BlessedPoppet;
import com.sammy.malum.common.items.equipment.poppets.PoppetItem;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.systems.inventory.ItemInventory;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;

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
            public void curioTick(String identifier, int index, LivingEntity livingEntity)
            {
                if (stack.hasTag())
                {
                    CompoundNBT nbt = stack.getTag();
                    if (nbt.contains("inventory"))
                    {
                        SimpleInventory inventory = create(stack);
                        inventory.nonEmptyStacks().stream().filter(s -> s.getItem() instanceof BlessedPoppet).forEach(s -> s.inventoryTick(
                                livingEntity.world,livingEntity,0,true
                        ));
                    }
                }
            }
    
            @Override
            public boolean canRightClickEquip()
            {
                return false;
            }
        });
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (stack.hasTag())
        {
            CompoundNBT nbt = stack.getTag();
            if (nbt.contains("inventory"))
            {
                SimpleInventory inventory = create(stack);
                inventory.nonEmptyStacks().forEach(s -> tooltip.add(ClientHelper.importantTranslatableComponent(s.getItem().getTranslationKey()))
                );
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (MalumHelper.areWeOnServer(playerIn.world))
        {
            ItemStack belt = playerIn.getHeldItem(handIn);
            ItemStack poppet = playerIn.getHeldItem(handIn.equals(Hand.MAIN_HAND) ? Hand.OFF_HAND : Hand.MAIN_HAND);
            CompoundNBT nbt = belt.getOrCreateTag();
            if (poppet.getItem() instanceof PoppetItem)
            {
                ItemInventory inventory = create(belt);
                if (inventory.nonEmptyItems() < inventory.slotCount)
                {
                    inventory.setStackInSlot(inventory.firstEmptyItem(), poppet);
                    inventory.writeData(nbt);
                    poppet.shrink(1);
                    worldIn.playSound(null, playerIn.getPosition(), MalumSounds.EQUIP, SoundCategory.PLAYERS,0.2f,1 + worldIn.rand.nextFloat() * 0.5f);
                }
            }
            else if (playerIn.isSneaking())
            {
                ItemInventory inventory = create(belt);
                for (ItemStack stack : inventory.stacks())
                {
                    MalumHelper.giveItemToPlayerNoSound(playerIn, stack);
                }
                nbt.remove("inventory");
                worldIn.playSound(null, playerIn.getPosition(),MalumSounds.EQUIP, SoundCategory.PLAYERS,0.2f,1 - worldIn.rand.nextFloat() * 0.5f);
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    public static ItemInventory create(ItemStack stack)
    {
        return new ItemInventory(stack, 2,1);
    }
}


