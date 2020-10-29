package com.sammy.malum.items.armor;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.init.ModItems;
import com.sammy.malum.models.ModelSpiritHunterArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;

public class ItemSpiritHunterArmor extends ModArmor
{
    
    private final LazyValue<BipedModel<?>> model;
    
    public ItemSpiritHunterArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
    {
        super(materialIn, slot, builder);
        this.model = DistExecutor.runForDist(() -> () -> new LazyValue<>(() -> new ModelSpiritHunterArmor(slot)), () -> () -> null);
    }
    
    public static boolean hasArmorSet(PlayerEntity playerEntity)
    {
        return MalumHelper.hasArmorSet(playerEntity, ModItems.spirit_hunter_helm, ModItems.spirit_hunter_chestplate, ModItems.spirit_hunter_leggings, ModItems.spirit_hunter_shoes);
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
        return "malum:textures/armor/spirit_hunter_armor.png";
    }
    
}