package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.client.model.*;
import com.sammy.malum.common.item.cosmetic.skins.*;
import com.sammy.malum.common.item.curiosities.runes.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.extensions.common.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.systems.model.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.registry.common.item.ArmorTiers.ArmorTierEnum.*;

public class MalignantStrongholdArmorItem extends MalumArmorItem {

    public static final UUID RUNE_SLOT_UUID = UUID.fromString("eb4167cc-c77c-47e3-8695-ebb644e37fbc");

    public MalignantStrongholdArmorItem(Type slot, Properties builder) {
        super(MALIGNANT_ALLOY, slot, builder);
    }

    public String getTexture() {
        return "malignant_stronghold";
    }

    public static void onSwapEquipment(LivingEquipmentChangeEvent event) {
        final LivingEntity entity = event.getEntity();
        int pieces = 0;
        for (ItemStack armorSlot : entity.getArmorSlots()) {
            if (armorSlot.getItem() instanceof MalignantStrongholdArmorItem) {
                pieces++;
            }
        }
        if (pieces == 4) {
            CuriosApi.getCuriosInventory(entity).ifPresent(inventory -> {
                inventory.addTransientSlotModifier("rune", RUNE_SLOT_UUID, "malignant_armor_rune", 4, AttributeModifier.Operation.ADDITION);
            });
        }
        else {
            CuriosApi.getCuriosInventory(entity).ifPresent(inventory -> {
                inventory.removeSlotModifier("rune", RUNE_SLOT_UUID);
            });
        }
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
                LodestoneArmorModel model = ModelRegistry.MALIGNANT_LEAD_ARMOR;
                if (skin != null) {
                    model = ArmorSkinRenderingData.RENDERING_DATA.apply(skin).getModel(entity);
                }
                if (model instanceof MalignantLeadArmorModel malignantLeadArmorModel) {
                    final LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(entity);
                    if (curiosInventory.isPresent()) {
                        final List<MalumRuneCurioItem> equippedRunes = curiosInventory
                                .map(i -> i.findCurios(s -> s.getItem() instanceof MalumRuneCurioItem))
                                .map(l -> l.stream()
                                        .filter(c -> c.slotContext().visible())
                                        .map(c -> (MalumRuneCurioItem) c.stack().getItem()).collect(Collectors.toList()))
                                .orElse(Collections.emptyList());
                        malignantLeadArmorModel.updateGlow(equippedRunes);
                    }
                }
                model.slot = armorSlot;
                model.copyFromDefault(_default);
                model.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }
}