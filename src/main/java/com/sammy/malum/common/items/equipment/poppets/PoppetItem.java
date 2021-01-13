package com.sammy.malum.common.items.equipment.poppets;

import com.ibm.icu.impl.Pair;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.equipment.curios.CurioPoppetBelt;
import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.antlr.v4.runtime.misc.Triple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PoppetItem extends Item
{
    public PoppetItem(Properties properties)
    {
        super(properties);
    }
    public boolean onlyDirect()
    {
        return false;
    }
    public void effect(ItemStack poppet, LivingHurtEvent event,World world, PlayerEntity playerEntity, ArrayList<LivingEntity> targets, SimpleInventory inventory, int slot)
    {
        targets.forEach(t -> effect(poppet, event,world,playerEntity,t, inventory, slot));
    }
    public void effect(ItemStack poppet, LivingHurtEvent event,World world, PlayerEntity playerEntity, LivingEntity target, SimpleInventory inventory, int slot)
    {
        poppet.damageItem(1, playerEntity, (l) -> {
            l.world.playSound(null, l.getPosition(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1, 1);
            if (inventory != null)
            {
                inventory.setStackInSlot(slot, ItemStack.EMPTY);
                inventory.writeData(inventory.beltItem.getOrCreateTag());
            }
        });
    }
    public static ArrayList<Triple<ItemStack, SimpleInventory, Integer>> poppets(PlayerEntity playerEntity, Predicate<ItemStack> predicate)
    {
        ArrayList<Triple<ItemStack, SimpleInventory, Integer>> poppets = new ArrayList<>();
        if (predicate.test(playerEntity.getHeldItemOffhand()))
        {
            poppets.add(new Triple<>(playerEntity.getHeldItemOffhand(), null, 0));
        }
        if (predicate.test(playerEntity.getHeldItemMainhand()))
        {
            poppets.add(new Triple<>(playerEntity.getHeldItemMainhand(), null, 0));
        }
        if (CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.POPPET_BELT.get(), playerEntity).isPresent())
        {
            ItemStack belt = CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.POPPET_BELT.get(), playerEntity).get().right;
            SimpleInventory inventory = CurioPoppetBelt.create(belt);
            for (int i = 0; i < inventory.slotCount; i++)
            {
                ItemStack stack = inventory.getStackInSlot(i);
                if (predicate.test(stack))
                {
                    poppets.add(new Triple<>(stack, inventory, i));
                }
            }
        }
        return poppets;
    }
    public static PoppetItem cast(ItemStack stack)
    {
        return ((PoppetItem)(stack.getItem()));
    }
}
