package com.sammy.malum.items.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class CurioGildedGauntlet extends Item implements ICurio
{
    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("495ee836-14bb-4bfe-9811-44b62ed81b76");
    
    public CurioGildedGauntlet(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            @Override
            public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier)
            {
                Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();
                if (CuriosApi.getCuriosHelper().getCurioTags(stack.getItem()).contains(identifier))
                {
                    atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Attack Speed bonus", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
                return atts;
            }
            
            @Override
            public boolean showAttributesTooltip(String identifier)
            {
                return false;
            }
            
            @Override
            public void playRightClickEquipSound(LivingEntity livingEntity)
            {
                livingEntity.world.playSound(null, livingEntity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }
            
            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }
        });
    }
}