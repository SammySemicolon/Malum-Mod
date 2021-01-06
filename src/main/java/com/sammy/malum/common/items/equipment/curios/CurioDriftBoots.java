package com.sammy.malum.common.items.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.curios.CurioProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class CurioDriftBoots extends Item implements ICurio
{
    public CurioDriftBoots(Properties builder)
    {
        super(builder);
    }
    
    private static final UUID DRIFT_GRAVITY = UUID.fromString("20fa6b59-20bc-4a63-b45e-edaa2fc69cf7");
    private static final UUID DRIFT_SPEED = UUID.fromString("4dc15e7b-1c28-44be-ad1d-4b827269610b");
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            @Override
            public void playRightClickEquipSound(LivingEntity livingEntity)
            {
                livingEntity.world.playSound(null, livingEntity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }
    
            @Override
            public boolean showAttributesTooltip(String identifier)
            {
                return false;
            }
    
            @Override
            public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier)
            {
                Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
                map.put(ForgeMod.ENTITY_GRAVITY.get(), new AttributeModifier(DRIFT_GRAVITY, MalumMod.MODID + ":drift_gravity_boost", -0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(DRIFT_SPEED, MalumMod.MODID + ":drift_speed_boost", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                return map;
            }
    
            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }
        });
    }
}