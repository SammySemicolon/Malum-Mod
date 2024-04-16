package com.sammy.malum.common.item.curiosities.armor;

import com.google.common.collect.*;
import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.common.item.cosmetic.skins.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.extensions.common.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.systems.model.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.registry.common.item.ArmorTiers.ArmorTierEnum.*;

public class MalignantStrongholdArmorItem extends MalumArmorItem {

    public static final UUID MALIGNANT_CONVERSION_UUID = UUID.fromString("ff803d68-a615-4279-a59a-d847db2481d7");
    public static final HashMap<Attribute, UUID> UUIDS = new HashMap<>();

    public MalignantStrongholdArmorItem(Type slot, Properties builder) {
        super(MALIGNANT_ALLOY, slot, builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createExtraAttributes(Type type) {
        Multimap<Attribute, AttributeModifier> attributes = ArrayListMultimap.create();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
        attributes.put(AttributeRegistry.MALIGNANT_CONVERSION.get(), new AttributeModifier(uuid, "Malignant Conversion", 0.25f, AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    public String getTexture() {
        return "malignant_stronghold";
    }

    public static void updateAttributes(LivingEntity livingEntity) {
        var attributes = livingEntity.getAttributes();
        var malignantConversionAttribute = AttributeRegistry.MALIGNANT_CONVERSION.get();
        if (attributes.hasAttribute(malignantConversionAttribute)) {
            convertAttribute(livingEntity, 2f, AttributeRegistry.SOUL_WARD_STRENGTH.get(), Attributes.ARMOR, Attributes.ARMOR_TOUGHNESS);
            convertAttribute(livingEntity, 1f, LodestoneAttributeRegistry.MAGIC_RESISTANCE.get(), Attributes.ARMOR, Attributes.ARMOR_TOUGHNESS);

            convertAttribute(livingEntity, 4f, AttributeRegistry.ARCANE_RESONANCE.get(), Attributes.ARMOR_TOUGHNESS);
            convertAttribute(livingEntity, 1f, AttributeRegistry.SOUL_WARD_RECOVERY_RATE.get(), Attributes.ARMOR_TOUGHNESS);

            convertAttribute(livingEntity, 2f, LodestoneAttributeRegistry.MAGIC_PROFICIENCY.get(), Attributes.ARMOR);
            convertAttribute(livingEntity, 1f, AttributeRegistry.SOUL_WARD_CAP.get(), Attributes.ARMOR);
        }
    }

    private static void convertAttribute(LivingEntity livingEntity, float conversionPotency, Attribute sourceAttribute, Attribute... targetAttributes) {
        var attributes = livingEntity.getAttributes();
        double malignantConversion = attributes.getValue(AttributeRegistry.MALIGNANT_CONVERSION.get());

        final AttributeInstance sourceInstance = livingEntity.getAttribute(sourceAttribute);
        if (sourceInstance != null) {
            sourceInstance.removeModifier(MALIGNANT_CONVERSION_UUID);
            double cachedValue = sourceInstance.getValue();
            for (Attribute target : targetAttributes) {
                final AttributeInstance targetInstance = livingEntity.getAttribute(target);
                if (targetInstance != null) {
                    final UUID uuid = UUIDS.computeIfAbsent(sourceAttribute, a -> Mth.createInsecureUUID(RandomSource.createNewThreadLocalInstance()));
                    targetInstance.removeModifier(uuid);
                    final double bonus = cachedValue * malignantConversion * conversionPotency;
                    if (bonus > 0) {
                        targetInstance.addTransientModifier(
                                new AttributeModifier(uuid, "Malignant Conversion: " + Component.translatable(target.getDescriptionId()),
                                        bonus, AttributeModifier.Operation.ADDITION));
                    }
                }
            }
            if (malignantConversion > 0) {
                sourceInstance.addTransientModifier(
                        new AttributeModifier(MALIGNANT_CONVERSION_UUID, "Malignant Conversion: " + Component.translatable(sourceAttribute.getDescriptionId()),
                                -malignantConversion, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
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