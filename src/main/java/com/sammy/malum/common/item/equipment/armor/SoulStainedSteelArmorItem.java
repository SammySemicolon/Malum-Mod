package com.sammy.malum.common.item.equipment.armor;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.client.model.SoulStainedSteelArmorModel;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.ortus.setup.OrtusAttributes;
import com.sammy.ortus.systems.item.OrtusArmorItem;
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

import static com.sammy.malum.core.setup.content.item.ArmorTiers.ArmorTierEnum.SOUL_STAINED_STEEL;

public class SoulStainedSteelArmorItem extends OrtusArmorItem {
    public SoulStainedSteelArmorItem(EquipmentSlot slot, Properties builder) {
        super(SOUL_STAINED_STEEL, slot, builder);
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
        builder.put(OrtusAttributes.MAGIC_RESISTANCE.get(), new AttributeModifier(uuid, "Magic Resistance", 1f, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.SOUL_WARD_CAP.get(), new AttributeModifier(uuid, "Soul Ward Cap", 3f, AttributeModifier.Operation.ADDITION));
        return builder;
    }

    @Override
    public String getTextureLocation() {
        return "malum:textures/armor/";
    }

    public String getTexture() {
        return "soul_stained_steel";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public SoulStainedSteelArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                float pticks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
                ItemRegistry.ClientOnly.SOUL_STAINED_ARMOR.slot = slot;
                ItemRegistry.ClientOnly.SOUL_STAINED_ARMOR.copyFromDefault(_default);
                ItemRegistry.ClientOnly.SOUL_STAINED_ARMOR.setupAnim(entity, entity.animationPosition, entity.animationSpeed, entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return ItemRegistry.ClientOnly.SOUL_STAINED_ARMOR;
            }
        });
    }
}