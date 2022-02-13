package com.sammy.malum.core.setup;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.malum.MalumMod.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID);

    public static final RegistryObject<Attribute> MAGIC_RESISTANCE = ATTRIBUTES.register("magic_resistance", () -> new RangedAttribute("attribute.name.malum.magic_resistance", 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MAGIC_PROFICIENCY = ATTRIBUTES.register("magic_proficiency", () -> new RangedAttribute("attribute.name.malum.magic_proficiency", 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MAGIC_DAMAGE = ATTRIBUTES.register("magic_damage", () -> new RangedAttribute("attribute.name.malum.magic_damage", 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SCYTHE_PROFICIENCY = ATTRIBUTES.register("scythe_proficiency", () -> new RangedAttribute("attribute.name.malum.scythe_proficiency", 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SPIRIT_SPOILS = ATTRIBUTES.register("spirit_spoils", () -> new RangedAttribute("attribute.name.malum.spirit_spoils", 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SPIRIT_REACH = ATTRIBUTES.register("spirit_reach", () -> new RangedAttribute("attribute.name.malum.spirit_reach", 5D, 0.0D, 2048.0D).setSyncable(true));

    public static final RegistryObject<Attribute> HEART_OF_STONE_STRENGTH = ATTRIBUTES.register("heart_of_stone_strength", () -> new RangedAttribute("attribute.name.malum.heart_of_stone_strength", 2D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> HEART_OF_STONE_RECOVERY_SPEED = ATTRIBUTES.register("heart_of_stone_recovery_speed", () -> new RangedAttribute("attribute.name.malum.heart_of_stone_recovery_speed", 0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> HEART_OF_STONE_COST = ATTRIBUTES.register("heart_of_stone_hunger_cost", () -> new RangedAttribute("attribute.name.malum.heart_of_stone_hunger_cost", 0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> HEART_OF_STONE_CAP = ATTRIBUTES.register("heart_of_stone_capacity", () -> new RangedAttribute("attribute.name.malum.heart_of_stone_capacity", 0D, 0.0D, 2048.0D).setSyncable(true));

    public static final RegistryObject<Attribute> SOUL_WARD_STRENGTH = ATTRIBUTES.register("soul_ward_strength", () -> new RangedAttribute("attribute.name.malum.soul_ward_strength", 1D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SOUL_WARD_RECOVERY_SPEED = ATTRIBUTES.register("soul_ward_recovery_speed", () -> new RangedAttribute("attribute.name.malum.soul_ward_recovery_speed", 0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SOUL_WARD_DAMAGE_PENALTY = ATTRIBUTES.register("soul_ward_damage_penalty", () -> new RangedAttribute("attribute.name.malum.soul_ward_damage_penalty", 3D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SOUL_WARD_CAP = ATTRIBUTES.register("soul_ward_capacity", () -> new RangedAttribute("attribute.name.malum.soul_ward_capacity", 0D, 0.0D, 2048.0D).setSyncable(true));

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(e -> {
            event.add(e, MAGIC_RESISTANCE.get());
            event.add(e, MAGIC_PROFICIENCY.get());
            event.add(e, MAGIC_DAMAGE.get());
            event.add(e, SCYTHE_PROFICIENCY.get());
            event.add(e, SPIRIT_SPOILS.get());
            event.add(e, SPIRIT_REACH.get());

            event.add(e, HEART_OF_STONE_STRENGTH.get());
            event.add(e, HEART_OF_STONE_RECOVERY_SPEED.get());
            event.add(e, HEART_OF_STONE_COST.get());
            event.add(e, HEART_OF_STONE_CAP.get());

            event.add(e, SOUL_WARD_STRENGTH.get());
            event.add(e, SOUL_WARD_RECOVERY_SPEED.get());
            event.add(e, SOUL_WARD_DAMAGE_PENALTY.get());
            event.add(e, SOUL_WARD_CAP.get());
        });
    }
}