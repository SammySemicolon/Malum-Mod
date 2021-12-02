package com.sammy.malum.common.item.equipment.armor;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.client.model.SoulHunterArmor;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.UUID;

import static com.sammy.malum.core.registry.items.ArmorTiers.ArmorTierEnum.SPIRIT_HUNTER;

public class SoulHunterArmorItem extends MalumArmorItem
{
    public SoulHunterArmorItem(EquipmentSlotType slot, Properties builder)
    {
        super(SPIRIT_HUNTER, slot, builder);
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            model = DistExecutor.runForDist(() -> () -> new LazyValue<>(() -> new SoulHunterArmor(slot)), () -> () -> null);
        }
    }

    @Override
    public void putExtraAttributes(ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder, UUID uuid) {
        attributeBuilder.put(AttributeRegistry.MAGIC_PROFICIENCY, new AttributeModifier(uuid, "Magic Proficiency", 1f, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(AttributeRegistry.SCYTHE_PROFICIENCY, new AttributeModifier(uuid, "Scythe Proficiency", 1f, AttributeModifier.Operation.ADDITION));
    }

    public String texture()
    {
        return "spirit_hunter_armor";
    }
}