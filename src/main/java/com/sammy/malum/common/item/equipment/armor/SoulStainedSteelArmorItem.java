package com.sammy.malum.common.item.equipment.armor;

import com.sammy.malum.client.model.ModelSoulStainedSteelArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.Nullable;

import static com.sammy.malum.core.init.items.MalumArmorTiers.ArmorTierEnum.SOUL_STAINED_STEEL;

public class SoulStainedSteelArmorItem extends ArmorItem
{
    private LazyValue<Object> model;
    public SoulStainedSteelArmorItem(EquipmentSlotType slot, Properties builder)
    {
        super(SOUL_STAINED_STEEL, slot, builder);
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            this.model = DistExecutor.runForDist(() -> () -> new LazyValue<>(() -> new ModelSoulStainedSteelArmor(slot)), () -> () -> null);
        }
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A original)
    {
        return (A) model.getValue();
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return "malum:textures/armor/soul_stained_steel_armor.png";
    }
}