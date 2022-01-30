package com.sammy.malum.core.registry;

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

    public static final Attribute HEART_OF_STONE_STRENGTH = register("heart_of_stone_strength", new RangedAttribute("attribute.name.malum.heart_of_stone_strength", 2D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute HEART_OF_STONE_RECOVERY_SPEED = register("heart_of_stone_recovery_speed", new RangedAttribute("attribute.name.malum.heart_of_stone_recovery_speed", 0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute HEART_OF_STONE_COST = register("heart_of_stone_hunger_cost", new RangedAttribute("attribute.name.malum.heart_of_stone_hunger_cost", 0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute HEART_OF_STONE_CAP = register("heart_of_stone_capacity", new RangedAttribute("attribute.name.malum.heart_of_stone_capacity", 0D, 0.0D, 2048.0D).setSyncable(true));

    public static final Attribute SOUL_WARD_STRENGTH = register("soul_ward_strength", new RangedAttribute("attribute.name.malum.soul_ward_strength", 1D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute SOUL_WARD_RECOVERY_SPEED = register("soul_ward_recovery_speed", new RangedAttribute("attribute.name.malum.soul_ward_recovery_speed", 0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute SOUL_WARD_DAMAGE_PENALTY = register("soul_ward_damage_penalty", new RangedAttribute("attribute.name.malum.soul_ward_damage_penalty", 3D, 0.0D, 2048.0D).setSyncable(true));
    public static final Attribute SOUL_WARD_CAP = register("soul_ward_capacity", new RangedAttribute("attribute.name.malum.soul_ward_capacity", 0D, 0.0D, 2048.0D).setSyncable(true));

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

            event.add(e, HEART_OF_STONE_STRENGTH);
            event.add(e, HEART_OF_STONE_RECOVERY_SPEED);
            event.add(e, HEART_OF_STONE_COST);
            event.add(e, HEART_OF_STONE_CAP);

            event.add(e, SOUL_WARD_STRENGTH);
            event.add(e, SOUL_WARD_RECOVERY_SPEED);
            event.add(e, SOUL_WARD_DAMAGE_PENALTY);
            event.add(e, SOUL_WARD_CAP);
        });
    }
}