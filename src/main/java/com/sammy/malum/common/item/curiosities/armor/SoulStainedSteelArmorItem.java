package com.sammy.malum.common.item.curiosities.armor;

import com.google.common.collect.*;
import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.registry.client.ModelRegistry;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.UUID;
import java.util.function.Consumer;

import static com.sammy.malum.registry.common.item.ArmorTiers.ArmorTierEnum.SOUL_STAINED_STEEL;

public class SoulStainedSteelArmorItem extends MalumArmorItem {
    public SoulStainedSteelArmorItem(ArmorItem.Type slot, Properties builder) {
        super(SOUL_STAINED_STEEL, slot, builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createExtraAttributes(Type type) {
        Multimap<Attribute, AttributeModifier> attributes = ArrayListMultimap.create();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
        attributes.put(AttributeRegistry.SOUL_WARD_CAP.get(), new AttributeModifier(uuid, "Soul Ward Cap", 3f, AttributeModifier.Operation.ADDITION));
        attributes.put(AttributeRegistry.SOUL_WARD_RECOVERY_RATE.get(), new AttributeModifier(uuid, "Soul Ward Recovery Rate", 0.15f, AttributeModifier.Operation.MULTIPLY_BASE));
        return attributes;
    }

    public String getTexture() {
        return "soul_stained_steel_reforged";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public LodestoneArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                float pticks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
                ArmorSkin skin = ArmorSkin.getAppliedItemSkin(itemStack);
                LodestoneArmorModel model = ModelRegistry.SOUL_STAINED_ARMOR;
                if (skin != null) {
                    model = ArmorSkinRenderingData.RENDERING_DATA.apply(skin).getModel(entity);
                }
                model.slot = armorSlot;
                model.copyFromDefault(_default);
                model.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }
}