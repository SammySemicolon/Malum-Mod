package com.sammy.malum.common.items.food;

import com.sammy.malum.client.particles.skull.SkullParticleData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class SolarSyrupBottleItem extends HoneyBottleItem
{
    public SolarSyrupBottleItem(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        entityLiving.world.addParticle(new SkullParticleData(1f, true),entityLiving.getPosX(), entityLiving.getPosY()+2f, entityLiving.getPosZ(),0,0,0);
        entityLiving.heal(3);
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
