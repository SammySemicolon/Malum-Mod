package com.sammy.malum.registry.common;

import net.minecraft.world.entity.ai.attributes.*;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.malum.MalumMod.MALUM;
import static team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry.registerAttribute;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MALUM);
    public static final RegistryObject<Attribute> SCYTHE_PROFICIENCY = registerAttribute(ATTRIBUTES, MALUM, "scythe_proficiency", (id) -> new RangedAttribute(id, 1D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SPIRIT_SPOILS = registerAttribute(ATTRIBUTES, MALUM, "spirit_spoils", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> ARCANE_RESONANCE = registerAttribute(ATTRIBUTES, MALUM, "arcane_resonance", (id) -> new RangedAttribute(id, 1.0D, 0.0D, 2048.0D).setSyncable(true));

    public static final RegistryObject<Attribute> SOUL_WARD_INTEGRITY = registerAttribute(ATTRIBUTES, MALUM, "soul_ward_integrity", (id) -> new RangedAttribute(id, 1D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SOUL_WARD_RECOVERY_RATE = registerAttribute(ATTRIBUTES, MALUM, "soul_ward_recovery_rate", (id) -> new RangedAttribute(id, 1D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SOUL_WARD_CAP = registerAttribute(ATTRIBUTES, MALUM, "soul_ward_capacity", (id) -> new RangedAttribute(id, 0D, 0.0D, 2048.0D).setSyncable(true));

    public static final RegistryObject<Attribute> RESERVE_STAFF_CHARGES = registerAttribute(ATTRIBUTES, MALUM, "reserve_staff_charges", (id) -> new RangedAttribute(id, 0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MALIGNANT_CONVERSION = registerAttribute(ATTRIBUTES, MALUM, "malignant_conversion", (id) -> new RangedAttribute(id, 0D, 0.0D, 1.0D).setSyncable(true));

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(e -> {
            event.add(e, SCYTHE_PROFICIENCY.get());
            event.add(e, SPIRIT_SPOILS.get());
            event.add(e, ARCANE_RESONANCE.get());

            event.add(e, SOUL_WARD_INTEGRITY.get());
            event.add(e, SOUL_WARD_RECOVERY_RATE.get());
            event.add(e, SOUL_WARD_CAP.get());
            event.add(e, RESERVE_STAFF_CHARGES.get());
            event.add(e, MALIGNANT_CONVERSION.get());
        });
    }
}