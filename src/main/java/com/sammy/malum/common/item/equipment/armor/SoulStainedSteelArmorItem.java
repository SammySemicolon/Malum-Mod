package com.sammy.malum.common.item.equipment.armor;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.model.SoulStainedArmorModel;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.UUID;

import static com.sammy.malum.core.registry.items.ArmorTiers.ArmorTierEnum.SOUL_STAINED_STEEL;

import net.minecraft.item.Item.Properties;

public class SoulStainedSteelArmorItem extends MalumArmorItem {
    public SoulStainedSteelArmorItem(EquipmentSlotType slot, Properties builder) {
        super(SOUL_STAINED_STEEL, slot, builder);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            this.model = DistExecutor.runForDist(() -> () -> new LazyValue<>(() -> new SoulStainedArmorModel(slot)), () -> () -> null);
        }
        createAttributes();
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> getAttributeBuilder(UUID uuid) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = super.getAttributeBuilder(uuid);
        attributeBuilder.put(AttributeRegistry.MAGIC_RESISTANCE, new AttributeModifier(uuid, "Magic Resistance", 0.5f, AttributeModifier.Operation.ADDITION));
        return attributeBuilder;
    }

    @Override
    public void pickupSpirit(LivingEntity attacker, ItemStack stack) {
        MalumHelper.giveStackingEffect(Effects.DAMAGE_RESISTANCE, attacker, 25, 0);
    }

    public String texture() {
        return "soul_stained_steel_armor";
    }
}