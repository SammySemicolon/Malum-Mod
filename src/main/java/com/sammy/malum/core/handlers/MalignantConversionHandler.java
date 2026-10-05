package com.sammy.malum.core.handlers;

import com.sammy.malum.*;
import com.sammy.malum.common.data.attachment.*;
import com.sammy.malum.common.data.listener.malignant_conversion.MalignantConversionAttributePayout;
import com.sammy.malum.common.data.listener.malignant_conversion.MalignantConversionData;
import com.sammy.malum.common.data.listener.malignant_conversion.MalignantConversionReloadListener;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;

import java.util.function.*;

public class MalignantConversionHandler {

    public static final ResourceLocation NEGATIVE_MODIFIER_ID = MalumMod.malumPath("malignant_conversion_tally");
    public static final Function<Holder<Attribute>, ResourceLocation> POSITIVE_MODIFIER_IDS = Util.memoize(MalignantConversionHandler::createPositiveModifierId);

    public static void shieldPlayer(LivingIncomingDamageEvent event) {
        var entity = event.getEntity();
        if (entity.level().isClientSide()) {
            return;
        }
        var source = event.getSource();
        if (source.is(MalumTags.DamageTypes.BYPASSES_MALIGNANT_AEGIS)) {
            return;
        }
        if (!(event.getOriginalAmount() >= 2f)) {
            return;
        }
        var optional = MalignantInfluenceData.getMalignantAegisData(entity);
        if (optional.isEmpty()) {
            return;
        }
        var data = optional.get();
        int aegis = data.getMalignantAegis();
        if (aegis <= 0) {
            return;
        }
        data.reduceAegis(1);
        var container = event.getContainer();
        int invulnerabilityTicks = Math.min(container.getPostAttackInvulnerabilityTicks() * 2, 40);
        container.setPostAttackInvulnerabilityTicks(invulnerabilityTicks);
        entity.syncData(MalumAttachmentTypes.MALIGNANT_INFLUENCE);

        if (data.getMalignantAegis() == 0) {
            SoundPlayer.create(MalumSoundEvents.MALIGNANT_AEGIS_DEPLETE).play(entity);
        }
        else {
            SoundPlayer.create(MalumSoundEvents.MALIGNANT_AEGIS_HIT).volume(0.25f).play(entity);
        }
        event.setCanceled(true);
    }

    public static void entityTick(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LivingEntity living)) {
            return;
        }
        if (!(living.level() instanceof ServerLevel level)) {
            return;
        }
        MalignantInfluenceData.getMalignantAegisData(living).ifPresent(data -> data.tickData(living));
        if (living instanceof Player player) {
            if (player.isSpectator()) {
                return;
            }
        } else {
            //Malignant Conversion isn't that important on non-player entities
            //To avoid lag, we only run once every two seconds
            if (level.getGameTime() % 40 != 0) {
                return;
            }
        }
        runConversionLogic(living);
    }

    private static void runConversionLogic(LivingEntity target) {
        var conversion = target.getAttribute(MalumAttributes.MALIGNANT_CONVERSION);
        if (conversion == null) {
            return;
        }
        if (!target.hasData(MalumAttachmentTypes.MALIGNANT_INFLUENCE) && !(conversion.getValue() > 0)) {
            return;
        }
        var data = target.getData(MalumAttachmentTypes.MALIGNANT_INFLUENCE);
        var malignantConversionAttribute = conversion.getAttribute();
        var conversions = MalignantConversionReloadListener.CONVERSION_DATA.values();
        boolean forcedUpdate = checkForChanges(data, target, malignantConversionAttribute);
        for (MalignantConversionData conversionData : conversions) {
            // This for loop checks for any attribute that can be converted through malignant conversion
            // If the attribute is present on the entity and has changed since this code last ran, we will attempt to convert it
            // If the malignant conversion attribute changed itself, via forcedUpdate, we will update all attributes regardless of if they themselves changed
            if (forcedUpdate || checkForChanges(data, target, conversionData.sourceAttribute())) {
                tryConvertAttribute(target, conversion, data, conversionData);
            }
        }
        data.cacheValue(conversion);
    }

    private static void tryConvertAttribute(LivingEntity target, AttributeInstance malignantConversion, MalignantInfluenceData cacheData, MalignantConversionData conversionData) {
        var sourceAttribute = conversionData.sourceAttribute();
        var sourceInstance = target.getAttribute(sourceAttribute);
        if (sourceInstance == null) {
            return;
        }
        float conversionStrength = (float) malignantConversion.getValue();
        boolean hasMalignantConversion = conversionStrength > 0;
        //Before any actual logic, we remove any toll applied by malignant conversion
        //This is done to give us the actual raw value of the attribute, unaffected by the conversion process
        removeNegativeModifier(sourceInstance);
        double convertedAmount = sourceInstance.getValue() - (conversionData.ignoreBaseValue() ? sourceInstance.getBaseValue() : 0);
        for (MalignantConversionAttributePayout payout : conversionData.payoutData()) {
            var affectedAttribute = payout.attribute();
            var affectedInstance = target.getAttribute(affectedAttribute);
            double payoutRatio = payout.ratio();
            if (affectedInstance != null) {
                var id = POSITIVE_MODIFIER_IDS.apply(sourceAttribute);
                affectedInstance.removeModifier(id);
                double bonus = convertedAmount * conversionStrength * payoutRatio;
                if (bonus > 0) {
                    var modifier = new AttributeModifier(id, bonus, AttributeModifier.Operation.ADD_VALUE);
                    affectedInstance.addTransientModifier(modifier);
                }
            }
        }
        cacheData.cacheValue(sourceInstance);
        if (hasMalignantConversion) {
            double ratio = conversionData.consumptionRatio();
            double toll = -conversionStrength * ratio;
            addNegativeModifier(sourceInstance, toll);
        }
    }

    private static boolean checkForChanges(MalignantInfluenceData data, LivingEntity livingEntity, Holder<Attribute> attribute) {
        var instance = livingEntity.getAttribute(attribute);
        if (instance == null) {
            return false;
        }
        if (data.hasCachedValue(attribute)) {
            var modifier = instance.getModifier(NEGATIVE_MODIFIER_ID);
            boolean isFullConversion = modifier != null && modifier.amount() <= -1;
            if (isFullConversion) {
                //We're trying to detect changes to the attribute, this cannot be done if malignant conversion is fully present, so we remove the modifier in such situation
                removeNegativeModifier(instance);
            }
            double stored = data.getCachedValue(attribute);
            double real = instance.getValue();
            if (isFullConversion) {
                //We reapply the removed modifier after checking for changes
                instance.addTransientModifier(modifier);
            }
            return stored != real;
        }
        return true;
    }

    private static void removeNegativeModifier(AttributeInstance attribute) {
        var modifier = attribute.getModifier(NEGATIVE_MODIFIER_ID);
        if (modifier != null) {
            attribute.removeModifier(modifier);
        }
    }

    private static void addNegativeModifier(AttributeInstance attribute, double toll) {
        var modifier = new AttributeModifier(NEGATIVE_MODIFIER_ID, toll, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        attribute.addTransientModifier(modifier);
    }

    private static ResourceLocation createPositiveModifierId(Holder<Attribute> attribute) {
        return MalumMod.malumPath("malignant_conversion_buff_from_" + BuiltInRegistries.ATTRIBUTE.getKey(attribute.value()).getPath());
    }
}