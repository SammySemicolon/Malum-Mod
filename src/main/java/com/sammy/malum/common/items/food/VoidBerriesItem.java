package com.sammy.malum.common.items.food;

import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class VoidBerriesItem extends Item
{
    public VoidBerriesItem(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        if (entityLiving instanceof PlayerEntity)
        {
            ((PlayerEntity) entityLiving).getFoodStats().addExhaustion(20);
            worldIn.playSound(null, entityLiving.getPosition(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS,1,0.8f);
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
