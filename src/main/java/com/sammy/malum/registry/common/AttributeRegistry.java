package com.sammy.malum.registry.common;

import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import static com.sammy.malum.MalumMod.MALUM;
import static team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry.registerAttribute;

public class AttributeRegistry {
    public static final LazyRegistrar<Attribute> ATTRIBUTES = LazyRegistrar.create(BuiltInRegistries.ATTRIBUTE, MALUM);

    public static final RegistryObject<Attribute> SCYTHE_PROFICIENCY = registerAttribute(ATTRIBUTES, MALUM, "scythe_proficiency", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SPIRIT_SPOILS = registerAttribute(ATTRIBUTES, MALUM, "spirit_spoils", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> ARCANE_RESONANCE = registerAttribute(ATTRIBUTES, MALUM, "arcane_resonance", (id) -> new RangedAttribute(id, 1.0D, 0.0D, 2048.0D).setSyncable(true));

    public static final RegistryObject<Attribute> SOUL_WARD_INTEGRITY = registerAttribute(ATTRIBUTES, MALUM, "soul_ward_integrity", (id) -> new RangedAttribute(id, 1D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SOUL_WARD_RECOVERY_RATE = registerAttribute(ATTRIBUTES, MALUM, "soul_ward_recovery_rate", (id) -> new RangedAttribute(id, 1D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SOUL_WARD_CAP = registerAttribute(ATTRIBUTES, MALUM, "soul_ward_capacity", (id) -> new RangedAttribute(id, 0D, 0.0D, 2048.0D).setSyncable(true));

    public static final RegistryObject<Attribute> RESERVE_STAFF_CHARGES = registerAttribute(ATTRIBUTES, MALUM, "reserve_staff_charges", (id) -> new RangedAttribute(id, 0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MALIGNANT_CONVERSION = registerAttribute(ATTRIBUTES, MALUM, "malignant_conversion", (id) -> new RangedAttribute(id, 0D, 0.0D, 1.0D).setSyncable(true));
}