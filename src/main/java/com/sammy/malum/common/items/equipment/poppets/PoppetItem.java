package com.sammy.malum.common.items.equipment.poppets;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.items.equipment.curios.CurioPoppetBelt;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.inventory.ItemInventory;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.function.Predicate;

import static com.mojang.datafixers.util.Pair.*;

public class PoppetItem extends Item
{
    public PoppetItem(Properties properties)
    {
        super(properties);
    }
    
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target, SimpleInventory inventory)
    {
        if (playerEntity.isCreative())
        {
            return;
        }
        poppet.damageItem(1, playerEntity, (l) -> {
            l.world.playSound(null, l.getPosition(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1, 1);
            if (inventory != null)
            {
                for (int i = 0; i < inventory.slotCount; i++)
                {
                    ItemStack stack = inventory.getStackInSlot(i);
                    if (ItemStack.areItemStacksEqual(stack, poppet))
                    {
                        inventory.setStackInSlot(i, ItemStack.EMPTY);
                        return;
                    }
                }
            }
        });
    }
    
    public static ArrayList<Pair<ItemStack, ItemInventory>> poppets(PlayerEntity playerEntity, Predicate<ItemStack> predicate)
    {
        ArrayList<Pair<ItemStack, ItemInventory>> poppets = new ArrayList<>();
        if (predicate.test(playerEntity.getHeldItemOffhand()))
        {
            poppets.add(of(playerEntity.getHeldItemOffhand(), null));
        }
        if (predicate.test(playerEntity.getHeldItemMainhand()))
        {
            poppets.add(of(playerEntity.getHeldItemMainhand(), null));
        }
        if (CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.POPPET_BELT.get(), playerEntity).isPresent())
        {
            ItemStack belt = CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.POPPET_BELT.get(), playerEntity).get().right;
            ItemInventory inventory = CurioPoppetBelt.create(belt);
            for (int i = 0; i < inventory.slotCount; i++)
            {
                ItemStack stack = inventory.getStackInSlot(i);
                if (predicate.test(stack))
                {
                    poppets.add(of(stack, inventory));
                }
            }
        }
        return poppets;
    }
    
    public static PoppetItem cast(ItemStack stack)
    {
        return ((PoppetItem) (stack.getItem()));
    }
}
