package com.sammy.malum.common.item.equipment.armor.vanity;

import com.sammy.malum.client.model.DripArmorModel;
import com.sammy.malum.common.item.equipment.armor.SpiritHunterArmorItem;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

public class DripArmorItem extends SpiritHunterArmorItem {
    public DripArmorItem(EquipmentSlot slot, Properties builder) {
        super(slot, builder);
    }

    @Override
    public String getTexture() {
        return "drip";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public DripArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                float pticks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
                ItemRegistry.ClientOnly.DRIP_ARMOR.slot = slot;
                ItemRegistry.ClientOnly.DRIP_ARMOR.copyFromDefault(_default);
                ItemRegistry.ClientOnly.DRIP_ARMOR.setupAnim(entity, entity.animationPosition, entity.animationSpeed, entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return ItemRegistry.ClientOnly.DRIP_ARMOR;
            }
        });
    }
}