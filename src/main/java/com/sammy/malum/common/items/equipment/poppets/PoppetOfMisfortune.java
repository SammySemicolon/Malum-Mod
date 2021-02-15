package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PoppetOfMisfortune extends PoppetItem
{
    public PoppetOfMisfortune(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target, SimpleInventory inventory)
    {
        target.applyKnockback(1f, MathHelper.nextFloat(world.rand, -1,1), MathHelper.nextFloat(world.rand, -1,1));
        super.effect(poppet, event, world, playerEntity, target, inventory);
    }
}
