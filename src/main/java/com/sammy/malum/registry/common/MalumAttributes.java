package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.modules.core.attribute.LodestoneAttributeBuilder;
import team.lodestar.lodestone.modules.core.attribute.LodestoneRangedAttribute;

import static com.sammy.malum.MalumMod.MALUM;
import static team.lodestar.lodestone.modules.core.attribute.LodestoneRangedAttribute.*;

@EventBusSubscriber()
public class MalumAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, MALUM);

    public static final ResourceLocation BASE_CHARGE_DURATION = MalumMod.malumPath("base_charge_duration");
    public static final ResourceLocation BASE_CHARGE_CAPACITY = MalumMod.malumPath("base_charge_capacity");

    public static final DeferredHolder<Attribute, Attribute> SCYTHE_PROFICIENCY = registerAttribute(create(MalumMod.malumPath("scythe_proficiency"), 1.0D, 0.0D, 2048.0D).forcePercentageDisplay().setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SPIRIT_SPOILS = registerAttribute(create(MalumMod.malumPath("spirit_spoils"), 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> ARCANE_RESONANCE = registerAttribute(create(MalumMod.malumPath("arcane_resonance"), 1.0D, 0.0D, 2048.0D).forcePercentageDisplay().setSyncable(true));
    
    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_INTEGRITY = registerAttribute(create(MalumMod.malumPath("soul_ward_integrity"), 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_RECOVERY_RATE = registerAttribute(create(MalumMod.malumPath("soul_ward_recovery_rate"), 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_RECOVERY_GAIN = registerAttribute(create(MalumMod.malumPath("soul_ward_recovery_gain"), 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_CAPACITY = registerAttribute(create(MalumMod.malumPath("soul_ward_capacity"), 0D, 0.0D, 2048.0D).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> CHARGE_DURATION = registerAttribute(create(MalumMod.malumPath("charge_duration"), 0D, 0.0D, 2048.0D).setAsBaseAttribute(BASE_CHARGE_DURATION).setSentiment(Attribute.Sentiment.NEGATIVE).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> CHARGE_CAPACITY = registerAttribute(create(MalumMod.malumPath("charge_capacity"), 0D, 0.0D, 2048.0D).setAsBaseAttribute(BASE_CHARGE_CAPACITY).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> CHARGE_RECOVERY_RATE = registerAttribute(create(MalumMod.malumPath("charge_recovery_rate"), 1.0D, 0.0D, 2048.0D).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> GEAS_LIMIT = registerAttribute(create(MalumMod.malumPath("geas_limit"), 2D, 0.0D, 8.0D).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> MALIGNANT_CONVERSION = registerAttribute(create(MalumMod.malumPath("malignant_conversion"), 0D, 0.0D, 1.0D).forcePercentageDisplay().setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> MALIGNANT_AEGIS_RECOVERY_RATE = registerAttribute(create(MalumMod.malumPath("malignant_aegis_recovery_rate"), 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> MALIGNANT_AEGIS_RECOVERY_GAIN = registerAttribute(create(MalumMod.malumPath("malignant_aegis_recovery_gain"), 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> MALIGNANT_AEGIS_CAPACITY = registerAttribute(create(MalumMod.malumPath("malignant_aegis_capacity"), 0D, 0.0D, 2048.0D).setSyncable(true));


    public static DeferredHolder<Attribute, Attribute> registerAttribute(LodestoneAttributeBuilder builder) {
        return ATTRIBUTES.register(builder.id.getPath(), builder::build);
    }
    
    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(e -> {
            for (DeferredHolder<Attribute, ? extends Attribute> entry : ATTRIBUTES.getEntries()) {
                event.add(e, entry);
            }
        });
    }
}