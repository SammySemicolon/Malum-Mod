package com.sammy.malum.common.item.equipment.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.UUID;

import net.minecraft.item.Item.Properties;

public class MalumArmorItem extends ArmorItem implements IEventResponderItem
{
    protected LazyValue<Object> model;
    private Multimap<Attribute, AttributeModifier> attributes;
    public MalumArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
    {
        super(materialIn, slot, builder);
    }

    public void createAttributes()
    {
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = getAttributeBuilder(uuid);
        putExtraAttributes(attributeBuilder, uuid);
        attributes = attributeBuilder.build();
    }
    public void putExtraAttributes(ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder, UUID uuid)
    {

    }
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> getAttributeBuilder(UUID uuid) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        attributeBuilder.putAll(defaultModifiers);
        return attributeBuilder;
    }
    public String texture()
    {
        return null;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == this.slot ? this.attributes : ImmutableMultimap.of();
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return "malum:textures/armor/" + texture() + ".png";
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A original)
    {
        return (A) model.get();
    }
}