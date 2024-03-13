package com.sammy.malum.registry.common.block;

import com.sammy.malum.registry.common.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class MalumBlockSetTypes {
    public static final BlockSetType RUNEWOOD = BlockSetType.register(
            new BlockSetType("runewood", true,
                    SoundRegistry.RUNEWOOD,
                    SoundRegistry.RUNEWOOD_DOOR_CLOSE.get(), SoundRegistry.RUNEWOOD_DOOR_OPEN.get(),
                    SoundRegistry.RUNEWOOD_TRAPDOOR_CLOSE.get(), SoundRegistry.RUNEWOOD_TRAPDOOR_OPEN.get(),
                    SoundRegistry.RUNEWOOD_PRESSURE_PLATE_CLICK_OFF.get(), SoundRegistry.RUNEWOOD_PRESSURE_PLATE_CLICK_ON.get(),
                    SoundRegistry.RUNEWOOD_BUTTON_CLICK_OFF.get(), SoundRegistry.RUNEWOOD_BUTTON_CLICK_ON.get()));
    public static final BlockSetType SOULWOOD = BlockSetType.register(
            new BlockSetType("soulwood"));
    public static final BlockSetType TAINTED_ROCK = BlockSetType.register(
            new BlockSetType("tainted_rock", true, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final BlockSetType TWISTED_ROCK = BlockSetType.register(
            new BlockSetType("twisted_rock", true, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
}
