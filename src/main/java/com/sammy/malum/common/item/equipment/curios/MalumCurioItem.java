package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class MalumCurioItem extends Item implements ICurioItem
{
    public MalumCurioItem(Properties properties)
    {
        super(properties);
    }

    public boolean isGilded()
    {
        return false;
    }
    public boolean isOrnate()
    {
        return false;
    }
    public int spiritYieldBonus()
    {
        return 0;
    }
    public ItemStack spiritReplacementStack(ItemStack previousStack)
    {
        return ItemStack.EMPTY;
    }
    public void consumeSpiritEffect(LivingEntity livingEntity)
    {

    }
    @Override
    public void playRightClickEquipSound(LivingEntity livingEntity, ItemStack stack)
    {
        if (isGilded())
        {
            livingEntity.world.playSound(null, livingEntity.getPosition(), MalumSounds.HOLY_EQUIP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        }
        if (isOrnate())
        {
            livingEntity.world.playSound(null, livingEntity.getPosition(), MalumSounds.SINISTER_EQUIP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean canRightClickEquip(ItemStack stack)
    {
        return true;
    }
}
