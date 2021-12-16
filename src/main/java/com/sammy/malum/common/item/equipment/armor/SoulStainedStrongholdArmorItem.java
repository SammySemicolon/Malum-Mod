package com.sammy.malum.common.item.equipment.armor;

import com.sammy.malum.client.model.StrongholdArmorModel;
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

import static com.sammy.malum.core.registry.items.ArmorTiers.ArmorTierEnum.SOUL_STAINED_STRONGHOLD;

import net.minecraft.item.Item.Properties;

public class SoulStainedStrongholdArmorItem extends ArmorItem
{
    private LazyValue<Object> model;
    public SoulStainedStrongholdArmorItem(EquipmentSlotType slot, Properties builder)
    {
        super(SOUL_STAINED_STRONGHOLD, slot, builder);
    
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            this.model = DistExecutor.runForDist(() -> () -> new LazyValue<>(() -> new StrongholdArmorModel(slot)), () -> () -> null);
        }
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A original)
    {
        return (A) model.get();
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return "malum:textures/armor/soul_stained_stronghold_armor.png";
    }
}