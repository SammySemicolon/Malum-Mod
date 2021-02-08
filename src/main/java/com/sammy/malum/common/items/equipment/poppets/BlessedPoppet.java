package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class BlessedPoppet extends PoppetItem
{
    public BlessedPoppet(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, ArrayList<LivingEntity> targets, SimpleInventory inventory)
    {
    }
}