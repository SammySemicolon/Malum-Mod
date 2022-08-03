package com.sammy.malum.common.item.equipment.armor;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.client.model.SpiritHunterArmorModel;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.systems.item.ItemSkin;
import com.sammy.ortus.setup.OrtusAttributeRegistry;
import com.sammy.ortus.systems.item.OrtusArmorItem;
import com.sammy.ortus.systems.model.OrtusArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.UUID;
import java.util.function.Consumer;

import static com.sammy.malum.core.setup.content.item.ArmorTiers.ArmorTierEnum.SPIRIT_HUNTER;

public class SoulHunterArmorItem extends OrtusArmorItem {
    public SoulHunterArmorItem(EquipmentSlot slot, Properties builder) {
        super(SPIRIT_HUNTER, slot, builder);
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
        builder.put(OrtusAttributeRegistry.MAGIC_PROFICIENCY.get(), new AttributeModifier(uuid, "Magic Proficiency", 2f, AttributeModifier.Operation.ADDITION));
        return builder;
    }

    @Override
    public String getTextureLocation() {
        return "malum:textures/armor/";
    }

    public String getTexture() {
        return "spirit_hunter_reforged";
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        ItemSkin skin = ItemRegistry.ClientOnly.getSkin(stack);
        return skin != null ? skin.textureLocation.toString() : super.getArmorTexture(stack, entity, slot, type);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public OrtusArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                float pticks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
                ItemSkin skin = ItemRegistry.ClientOnly.getSkin(itemStack);
                OrtusArmorModel model = skin != null ? skin.modelSupplier.get() : ItemRegistry.ClientOnly.SPIRIT_HUNTER_ARMOR;

                model.slot = slot;
                model.copyFromDefault(_default);
                model.setupAnim(entity, entity.animationPosition, entity.animationSpeed, entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }
}