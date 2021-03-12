package com.sammy.malum.common.items.food;

import com.sammy.malum.core.init.MalumItems;
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
import top.theillusivec4.curios.api.CuriosApi;

import static net.minecraftforge.eventbus.api.Event.Result.DENY;

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
            PlayerEntity playerEntity = (PlayerEntity) entityLiving;
            if (CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.LIVING_CAPACITOR.get(), playerEntity).isPresent())
            {
                playerEntity.heal(4);
                playerEntity.getFoodStats().addStats(2,2);
                playerEntity.world.playSound(null,playerEntity.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.4f, 0.8f);
                return super.onItemUseFinish(stack, worldIn, entityLiving);
            }
            playerEntity.getFoodStats().addExhaustion(20);
            worldIn.playSound(null, entityLiving.getPosition(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS,1,0.8f);
      
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
