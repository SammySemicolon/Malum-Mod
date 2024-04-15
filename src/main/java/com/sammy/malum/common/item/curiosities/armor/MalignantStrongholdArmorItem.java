package com.sammy.malum.common.item.curiosities.armor;

import com.google.common.collect.*;
import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.common.item.cosmetic.skins.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.extensions.common.*;
import team.lodestar.lodestone.systems.model.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.item.ArmorTiers.ArmorTierEnum.*;

public class MalignantStrongholdArmorItem extends MalumArmorItem {

    public MalignantStrongholdArmorItem(Type slot, Properties builder) {
        super(MALIGNANT_ALLOY, slot, builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createExtraAttributes(Type type) {
        Multimap<Attribute, AttributeModifier> attributes = ArrayListMultimap.create();
//        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
//        CuriosApi.addSlotModifier(attributes, "rune", uuid, 1, AttributeModifier.Operation.ADDITION);
        return attributes;
    }

    public String getTexture() {
        return "malignant_stronghold";
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
//                if (model instanceof MalignantStrongholdArmorModel malignantStrongholdArmorModel) {
//                    final LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(entity);
//                    if (curiosInventory.isPresent()) {
//                        final List<AbstractRuneCurioItem> equippedRunes = curiosInventory
//                                .map(i -> i.findCurios(s -> s.getItem() instanceof AbstractRuneCurioItem))
//                                .map(l -> l.stream()
//                                        .filter(c -> c.slotContext().visible())
//                                        .map(c -> (AbstractRuneCurioItem) c.stack().getItem()).collect(Collectors.toList()))
//                                .orElse(Collections.emptyList());
//                        malignantStrongholdArmorModel.updateGlow(equippedRunes);
//                    }
//                }
                model.slot = armorSlot;
                model.copyFromDefault(_default);
                model.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }
}