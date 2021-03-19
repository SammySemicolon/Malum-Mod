package com.sammy.malum.common.items.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class CurioGildedBelt extends Item implements ICurio
{
    public CurioGildedBelt(Properties builder)
    {
        super(builder);
    }
    
    private static final UUID ARMOR = UUID.fromString("0baf5041-f36d-4b33-b45f-b7d340a8fa6c");
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
            public boolean showAttributesTooltip(String identifier)
            {
                return false;
            }
    
            @Override
            public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier)
            {
                Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
                map.put(Attributes.ARMOR, new AttributeModifier(ARMOR, MalumMod.MODID + ":gilded_belt_armor_boost", 2, AttributeModifier.Operation.ADDITION));
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