package com.sammy.malum.core.handlers;

import com.mojang.datafixers.util.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.packets.malignant_conversion.*;
import com.sammy.malum.core.listeners.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.network.*;

import java.util.*;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class MalignantConversionHandler {

    public static final UUID NEGATIVE_MODIFIER_UUID = UUID.fromString("ff803d68-a615-4279-a59a-d847db2481d7");
    public static final HashMap<Attribute, UUID> POSITIVE_MODIFIER_UUIDS = new HashMap<>();

    public final HashMap<Attribute, Double> cachedAttributeValues = new HashMap<>();
    public boolean skipConversionLogic;

    public static void checkForAttributeChanges(LivingEvent.LivingTickEvent event) {
        final LivingEntity livingEntity = event.getEntity();
        if (!livingEntity.level().isClientSide) {
            var handler = MalumLivingEntityDataCapability.getCapability(livingEntity).malignantConversionHandler;
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
                for (MalignantConversionReloadListener.MalignantConversionData data : conversionData.values()) { //check for any changed attributes, and apply malignant conversion to them if they've been updated
                    Attribute attribute = data.sourceAttribute();
                    AttributeInstance instance = livingEntity.getAttribute(attribute);
                    if (instance != null) {
                        if (handler.cachedAttributeValues.containsKey(attribute)) {
                            convertAttribute(handler, livingEntity, data.sourceAttribute(), data.consumptionRatio(), data.ignoreBaseValue(), data.targetAttributes(), false);
                        }
                    }
                }
                if (handler.cachedAttributeValues.containsKey(conversionAttribute)) { //update attributes when malignant conversion changes
                    if (handler.cachedAttributeValues.get(conversionAttribute) != conversionInstance.getValue()) {
                        for (MalignantConversionReloadListener.MalignantConversionData data : conversionData.values()) {
                            convertAttribute(handler, livingEntity, data.sourceAttribute(), data.consumptionRatio(), data.ignoreBaseValue(), data.targetAttributes(), true);
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

    private static void convertAttribute(MalignantConversionHandler handler, LivingEntity livingEntity, Attribute sourceAttribute, double consumptionRatio, boolean ignoreBaseValue, List<Pair<Attribute, Double>> targetAttributes, boolean skipCacheComparison) {
        var attributes = livingEntity.getAttributes();
        double malignantConversion = attributes.getValue(AttributeRegistry.MALIGNANT_CONVERSION.get());

        var sourceInstance = livingEntity.getAttribute(sourceAttribute);
        if (sourceInstance != null) {
            var originalModifier = sourceInstance.getModifier(NEGATIVE_MODIFIER_UUID);
            if (originalModifier != null) {
                sourceInstance.removeModifier(originalModifier);
            }
            boolean hasMalignantConversion = malignantConversion > 0;
            if (skipCacheComparison || handler.cachedAttributeValues.get(sourceAttribute) != sourceInstance.getValue()) {
                double cachedValue = sourceInstance.getValue() - (ignoreBaseValue ? sourceInstance.getBaseValue() : 0);
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
                                MALUM_CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new SyncMalignantConversionPacket(targetAttribute, uuid));
                            }
                        }
                    }
                }
                handler.cachedAttributeValues.put(sourceAttribute, sourceInstance.getValue());
                if (hasMalignantConversion) {
                    var modifier = new AttributeModifier(NEGATIVE_MODIFIER_UUID, "Malignant Conversion: " + Component.translatable(sourceAttribute.getDescriptionId()),
                            -malignantConversion * consumptionRatio, AttributeModifier.Operation.MULTIPLY_TOTAL);
                    sourceInstance.addTransientModifier(modifier);
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
