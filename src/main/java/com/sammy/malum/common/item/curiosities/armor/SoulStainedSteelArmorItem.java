package com.sammy.malum.common.item.curiosities.armor;

import com.google.common.collect.*;
import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.common.item.cosmetic.skins.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import team.lodestar.lodestone.systems.model.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.registry.common.item.ArmorTiers.ArmorTierEnum.*;

public class SoulStainedSteelArmorItem extends MalumArmorItem {
    public SoulStainedSteelArmorItem(ArmorItem.Type slot, Properties builder) {
        super(SOUL_STAINED_STEEL, slot, builder);
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(slot.getIndex());
        builder.put(AttributeRegistry.SOUL_WARD_CAP.get(), new AttributeModifier(uuid, "Soul Ward Cap", 3f, AttributeModifier.Operation.ADDITION));
        return builder;
    }

    @Override
    public String getTextureLocation() {
        return "malum:textures/armor/";
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
                model.setupAnim(entity, entity.animationPosition, entity.animationSpeed, entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }
}