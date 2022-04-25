package com.sammy.malum.common.item.equipment.armor;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.client.model.SpiritHunterArmorModel;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.ortus.item.OrtusArmorItem;
import com.sammy.ortus.setup.OrtusAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.UUID;

import static com.sammy.malum.core.setup.content.item.ArmorTiers.ArmorTierEnum.SPIRIT_HUNTER;

public class SpiritHunterArmorItem extends OrtusArmorItem {
    public SpiritHunterArmorItem(EquipmentSlot slot, Properties builder) {
        super(SPIRIT_HUNTER, slot, builder);
    }
    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
        builder.put(OrtusAttributes.MAGIC_PROFICIENCY.get(), new AttributeModifier(uuid, "Magic Proficiency", 1f, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.SCYTHE_PROFICIENCY.get(), new AttributeModifier(uuid, "Scythe Proficiency", 1f, AttributeModifier.Operation.ADDITION));
        return builder;
    }
    public String getTexture() {
        return "spirit_hunter";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public SpiritHunterArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                float pticks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
                ItemRegistry.ClientOnly.SPIRIT_HUNTER_ARMOR.slot = slot;
                ItemRegistry.ClientOnly.SPIRIT_HUNTER_ARMOR.copyFromDefault(_default);
                ItemRegistry.ClientOnly.SPIRIT_HUNTER_ARMOR.setupAnim(entity, entity.animationPosition, entity.animationSpeed, entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return ItemRegistry.ClientOnly.SPIRIT_HUNTER_ARMOR;
            }
        });
    }
}