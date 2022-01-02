package com.sammy.malum.core.registry.misc;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID);

    public static final Attribute MAGIC_RESISTANCE = register("magic_resistance", new RangedAttribute("attribute.name.malum.magic_resistance", 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute MAGIC_PROFICIENCY = register("magic_proficiency", new RangedAttribute("attribute.name.malum.magic_proficiency", 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute SCYTHE_PROFICIENCY = register("scythe_proficiency", new RangedAttribute("attribute.name.malum.scythe_proficiency", 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute SPIRIT_SPOILS = register("spirit_spoils", new RangedAttribute("attribute.name.malum.spirit_spoils", 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute SPIRIT_REACH = register("spirit_reach", new RangedAttribute("attribute.name.malum.spirit_reach", 5D, 0.0D, 2048.0D).setSyncable(true));

    public static final Attribute HEART_OF_STONE_CAP = register("heart_of_stone_cap", new RangedAttribute("attribute.name.malum.heart_of_stone_cap", 0D, 0.0D, 30.0D).setSyncable(true));

    public static final Attribute SOUL_WARD_STRENGTH = register("soul_ward_strength", new RangedAttribute("attribute.name.malum.soul_ward_strength", 2D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute SOUL_WARD_RECOVERY_SPEED = register("soul_ward_recovery_speed", new RangedAttribute("attribute.name.malum.soul_ward_recovery_speed", 1D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute SOUL_WARD_SHATTER_PENALTY = register("soul_ward_shatter_penalty", new RangedAttribute("attribute.name.malum.soul_ward_shatter_penalty", 4D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute SOUL_WARD_CAP = register("soul_ward_cap", new RangedAttribute("attribute.name.malum.soul_ward_cap", 13D, 0.0D, 2048.0D).setSyncable(true));

    public static Attribute register(String name, Attribute attribute) {
        ATTRIBUTES.register(name, () -> attribute);
        return attribute;
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(e -> {
            event.add(e, MAGIC_RESISTANCE);
            event.add(e, MAGIC_PROFICIENCY);
            event.add(e, SCYTHE_PROFICIENCY);
            event.add(e, SPIRIT_SPOILS);
            event.add(e, SPIRIT_REACH);

            event.add(e, HEART_OF_STONE_CAP);

            event.add(e, SOUL_WARD_STRENGTH);
            event.add(e, SOUL_WARD_RECOVERY_SPEED);
            event.add(e, SOUL_WARD_SHATTER_PENALTY);
            event.add(e, SOUL_WARD_CAP);
        });
    }
}