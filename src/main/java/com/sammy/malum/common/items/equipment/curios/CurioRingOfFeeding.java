package com.sammy.malum.common.items.equipment.curios;

import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CurioRingOfFeeding extends Item implements ICurio
{
    public CurioRingOfFeeding(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            @Override
            public void playRightClickEquipSound(LivingEntity livingEntity)
            {
                livingEntity.world.playSound(null, livingEntity.getPosition(), MalumSounds.HOLY_EQUIP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }
    
            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }
        });
    }
}