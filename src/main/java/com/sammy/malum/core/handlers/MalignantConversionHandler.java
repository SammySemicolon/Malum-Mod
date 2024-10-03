package com.sammy.malum.core.handlers;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.components.MalumComponents;
import com.sammy.malum.common.packets.SyncStaffCooldownChangesPacket;
import com.sammy.malum.common.packets.malignant_conversion.SyncMalignantConversionPacket;
import com.sammy.malum.core.listeners.MalignantConversionReloadListener;
import com.sammy.malum.registry.common.AttributeRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class MalignantConversionHandler {

    public static final UUID NEGATIVE_MODIFIER_UUID = UUID.fromString("ff803d68-a615-4279-a59a-d847db2481d7");
    public static final HashMap<Attribute, UUID> POSITIVE_MODIFIER_UUIDS = new HashMap<>();

    public final HashMap<Attribute, Double> cachedAttributeValues = new HashMap<>();
    public boolean skipConversionLogic;


    public static void checkForAttributeChanges(LivingEntityEvents.LivingTickEvent event) {
        final LivingEntity livingEntity = event.getEntity();
        if (!livingEntity.level().isClientSide) {
            var handler = MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.get(livingEntity).malignantConversionHandler;
            final Attribute conversionAttribute = AttributeRegistry.MALIGNANT_CONVERSION.get();
            AttributeInstance conversionInstance = livingEntity.getAttribute(conversionAttribute);
            if (conversionInstance != null) {
                if (handler.skipConversionLogic) {
                    if (conversionInstance.getValue() == 0) {
                        return;
                    }
                    handler.skipConversionLogic = false;
                }
                var conversionData = MalignantConversionReloadListener.CONVERSION_DATA;
                for (MalignantConversionReloadListener.MalignantConversionData data : conversionData.values()) {
                    Attribute attribute = data.sourceAttribute();
                    AttributeInstance instance = livingEntity.getAttribute(attribute);
                    if (instance != null) {
                        if (handler.cachedAttributeValues.containsKey(attribute)) {
                            convertAttribute(handler, livingEntity, data.sourceAttribute(), data.consumptionRatio(), data.targetAttributes(), false);
                       }
                    }
                }
                if (handler.cachedAttributeValues.containsKey(conversionAttribute)) {
                    if (handler.cachedAttributeValues.get(conversionAttribute) != conversionInstance.getValue()) {
                        for (MalignantConversionReloadListener.MalignantConversionData data : conversionData.values()) {
                            convertAttribute(handler, livingEntity, data.sourceAttribute(), data.consumptionRatio(), data.targetAttributes(), true);
                        }
                    }
                }
                handler.cachedAttributeValues.put(conversionAttribute, conversionInstance.getValue());
                if (conversionInstance.getValue() == 0) {
                    handler.skipConversionLogic = true;
                }
            }
        }
    }

    private static void convertAttribute(MalignantConversionHandler handler, LivingEntity livingEntity, Attribute sourceAttribute, double consumptionRatio, List<Pair<Attribute, Double>> targetAttributes, boolean skipCacheComparison) {
        var attributes = livingEntity.getAttributes();
        double malignantConversion = attributes.getValue(AttributeRegistry.MALIGNANT_CONVERSION.get());

        var sourceInstance = livingEntity.getAttribute(sourceAttribute);
        if (sourceInstance != null) {
            final AttributeModifier originalModifier = sourceInstance.getModifier(NEGATIVE_MODIFIER_UUID);
            if (originalModifier != null) {
                sourceInstance.removeModifier(originalModifier);
            }
            boolean hasMalignantConversion = malignantConversion > 0;
            if (skipCacheComparison || handler.cachedAttributeValues.get(sourceAttribute) != sourceInstance.getValue()) {
                double cachedValue = sourceInstance.getValue();
                for (Pair<Attribute, Double> target : targetAttributes) {
                    var targetAttribute = target.getFirst();
                    var targetInstance = livingEntity.getAttribute(targetAttribute);
                    if (targetInstance != null) {
                        var uuid = POSITIVE_MODIFIER_UUIDS.computeIfAbsent(sourceAttribute, MalignantConversionHandler::createPositiveModifierUUID);
                        targetInstance.removeModifier(uuid);
                        double bonus = cachedValue * malignantConversion * target.getSecond();
                        if (bonus > 0) {
                            var modifier = new AttributeModifier(uuid, "Malignant Conversion: " + Component.translatable(targetAttribute.getDescriptionId()),
                                    bonus, AttributeModifier.Operation.ADDITION);
                            targetInstance.addTransientModifier(modifier);
                            if (livingEntity instanceof ServerPlayer serverPlayer) {
                                MALUM_CHANNEL.sendToClient(new SyncMalignantConversionPacket(targetAttribute, uuid), serverPlayer);
                            }
                        }
                    }
                }
                handler.cachedAttributeValues.put(sourceAttribute, sourceInstance.getValue());
                if (malignantConversion > 0) {
                    sourceInstance.addTransientModifier(
                            new AttributeModifier(NEGATIVE_MODIFIER_UUID, "Malignant Conversion: " + Component.translatable(sourceAttribute.getDescriptionId()),
                                    -malignantConversion * consumptionRatio, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
            if (originalModifier != null && sourceInstance.getModifier(NEGATIVE_MODIFIER_UUID) == null && hasMalignantConversion) {
                sourceInstance.addTransientModifier(originalModifier);
            }
        }
    }

    public static void syncPositiveUUIDS(Player player, Attribute attribute, UUID uuid) {
        var target = player.getAttribute(attribute);
        if (target != null) {
            POSITIVE_MODIFIER_UUIDS.put(attribute, uuid);
        }
    }

    private static UUID createPositiveModifierUUID(Attribute attribute) { //Generates a new UUID that will remain uniform for each individual attribute
        return UUID.nameUUIDFromBytes(attribute.getDescriptionId().getBytes());
    }
}
