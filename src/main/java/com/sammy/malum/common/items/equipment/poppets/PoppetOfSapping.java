package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static net.minecraft.potion.Effects.*;

public class PoppetOfSapping extends PoppetItem
{
    public PoppetOfSapping(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target, SimpleInventory inventory, int slot)
    {
        Effect[] effects = new Effect[]{POISON, SLOWNESS, HUNGER, BLINDNESS, WEAKNESS, WITHER};
        for (int i = 0; i < 3; i++)
        {
            int effect = world.rand.nextInt(effects.length);
            MalumHelper.giveStackingEffect(effects[effect], target, 100, 1);
        }
        super.effect(poppet, event, world, playerEntity, target, inventory, slot);
    }
}
