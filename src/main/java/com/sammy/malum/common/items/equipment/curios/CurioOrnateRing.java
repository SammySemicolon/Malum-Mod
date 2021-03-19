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

public class CurioOrnateRing extends Item implements ICurio
{
    public CurioOrnateRing(Properties builder)
    {
        super(builder);
    }
    
    private static final UUID TOUGHNESS = UUID.fromString("335abd8a-0e66-4b11-aa33-4aee88f0f614");
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            @Override
            public void playRightClickEquipSound(LivingEntity livingEntity)
            {
                livingEntity.world.playSound(null, livingEntity.getPosition(), MalumSounds.SINISTER_EQUIP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
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
                map.put(Attributes.ARMOR, new AttributeModifier(TOUGHNESS, MalumMod.MODID + ":ornate_ring_toughness_boost", 1f, AttributeModifier.Operation.ADDITION));
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