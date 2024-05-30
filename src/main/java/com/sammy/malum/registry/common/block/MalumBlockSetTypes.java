package com.sammy.malum.registry.common.block;

import com.sammy.malum.registry.common.SoundRegistry;
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
            new BlockSetType("soulwood", true,
                    SoundRegistry.SOULWOOD,
                    SoundRegistry.SOULWOOD_DOOR_CLOSE.get(), SoundRegistry.SOULWOOD_DOOR_OPEN.get(),
                    SoundRegistry.SOULWOOD_TRAPDOOR_CLOSE.get(), SoundRegistry.SOULWOOD_TRAPDOOR_OPEN.get(),
                    SoundRegistry.SOULWOOD_PRESSURE_PLATE_CLICK_OFF.get(), SoundRegistry.SOULWOOD_PRESSURE_PLATE_CLICK_ON.get(),
                    SoundRegistry.SOULWOOD_BUTTON_CLICK_OFF.get(), SoundRegistry.SOULWOOD_BUTTON_CLICK_ON.get()));
}
