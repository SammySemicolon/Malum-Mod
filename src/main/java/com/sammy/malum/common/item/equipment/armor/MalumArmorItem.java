package com.sammy.malum.common.item.equipment.armor;

import com.sammy.malum.client.model.SpiritHunterArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.Nullable;

import static com.sammy.malum.core.init.items.MalumArmorTiers.ArmorTierEnum.SPIRIT_HUNTER;

public class MalumArmorItem extends ArmorItem
{
    protected LazyValue<Object> model;
    public MalumArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
    {
        super(materialIn, slot, builder);
    }
    public RegistryObject<Item>[] getArmorList()
    {
        return null;
    }
    public boolean hasArmorSet(LivingEntity livingEntity, RegistryObject<Item>[] armor)
    {
        return hasArmorPiece(livingEntity, armor[0].get(), EquipmentSlotType.HEAD) && hasArmorPiece(livingEntity, armor[1].get(), EquipmentSlotType.CHEST) && hasArmorPiece(livingEntity, armor[2].get(), EquipmentSlotType.LEGS) && hasArmorPiece(livingEntity, armor[3].get(), EquipmentSlotType.FEET);
    }
    public boolean hasArmorPiece(LivingEntity livingEntity, Item item, EquipmentSlotType slot)
    {
        return livingEntity.getItemStackFromSlot(slot).getItem().equals(item);
    }

    public String texture()
    {
        return null;
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
        return (A) model.getValue();
    }
}