package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PoppetOfVengeance extends PoppetItem
{
    public PoppetOfVengeance(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target, SimpleInventory inventory)
    {
        float amount = Math.max(2, event.getAmount() * 0.1f);
        target.hurtResistantTime = 0;
        SpiritHelper.causeVoodooDamage(playerEntity, target, amount);
        super.effect(poppet, event, world, playerEntity, target, inventory);
    }
}
