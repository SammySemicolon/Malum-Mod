package com.sammy.malum.registry.common;


import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.MalumRegistries;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;

import java.awt.*;
import java.util.List;

import static com.sammy.malum.MalumMod.MALUM;

public class SpiritTypeRegistry {

    public static final DeferredRegister<MalumSpiritType> SPIRITS = DeferredRegister.create(MalumRegistries.SPIRITS, MALUM);

    public static RegistryObject<MalumSpiritType> SACRED_SPIRIT = SPIRITS.register("sacred", () -> new SpiritTypeBuilder(ItemRegistry.SACRED_SPIRIT, BlockRegistry.MOTE_OF_SACRED_ARCANA)
            .setColorData(new Color(238, 44, 136), new Color(40, 143, 243), 0.8f)
            .setItemColor(c -> c.primaryColor)
            .build());

    public static RegistryObject<MalumSpiritType> WICKED_SPIRIT = SPIRITS.register("wicked", () -> new SpiritTypeBuilder(ItemRegistry.WICKED_SPIRIT, BlockRegistry.MOTE_OF_WICKED_ARCANA)
            .setColorData(new Color(121, 44, 236), new Color(72, 21, 255), 0.8f)
            .setItemColor(c -> c.primaryColor)
            .build());

    public static RegistryObject<MalumSpiritType> ARCANE_SPIRIT = SPIRITS.register("arcane", () -> new SpiritTypeBuilder(ItemRegistry.ARCANE_SPIRIT, BlockRegistry.MOTE_OF_RAW_ARCANA)
            .setColorData(new Color(213, 70, 255), new Color(32, 222, 229), 1.1f, Easing.SINE_IN_OUT)
            .setItemColor(c -> ColorHelper.brighter(c.primaryColor, 1))
            .build());


    public static RegistryObject<MalumSpiritType> ELDRITCH_SPIRIT = SPIRITS.register("eldritch",() -> new SpiritTypeBuilder(ItemRegistry.ELDRITCH_SPIRIT, BlockRegistry.MOTE_OF_ELDRITCH_ARCANA)
            .setColorData(new Color(203, 12, 248), new Color(24, 78, 164), 0.9f)
            .setItemColor(c -> ColorHelper.darker(c.primaryColor, 1))
            .build());

    public static RegistryObject<MalumSpiritType> AERIAL_SPIRIT = SPIRITS.register("aerial", () -> new SpiritTypeBuilder(ItemRegistry.AERIAL_SPIRIT, BlockRegistry.MOTE_OF_AERIAL_ARCANA)
            .setColorData(new Color(75, 243, 218), new Color(243, 218, 75), 1.1f, Easing.SINE_IN)
            .setItemColor(c -> ColorHelper.brighter(c.primaryColor, 1))
            .build());

    public static RegistryObject<MalumSpiritType> AQUEOUS_SPIRIT = SPIRITS.register("aqueous", () -> new SpiritTypeBuilder(ItemRegistry.AQUEOUS_SPIRIT, BlockRegistry.MOTE_OF_AQUEOUS_ARCANA)
            .setColorData(new Color(29, 100, 232), new Color(41, 238, 133), 0.8f, Easing.SINE_IN_OUT)
            .setItemColor(c -> ColorHelper.brighter(c.primaryColor, 1))
            .build());

    public static RegistryObject<MalumSpiritType> INFERNAL_SPIRIT = SPIRITS.register("infernal", () -> new SpiritTypeBuilder(ItemRegistry.INFERNAL_SPIRIT, BlockRegistry.MOTE_OF_INFERNAL_ARCANA)
            .setColorData(new Color(250, 154, 31), new Color(210, 39, 150), 0.9f, Easing.SINE_IN_OUT)
            .setItemColor(c -> ColorHelper.brighter(c.primaryColor, 1))
            .build());

    public static RegistryObject<MalumSpiritType> EARTHEN_SPIRIT = SPIRITS.register("earthen", () -> new SpiritTypeBuilder(ItemRegistry.EARTHEN_SPIRIT, BlockRegistry.MOTE_OF_EARTHEN_ARCANA)
            .setColorData(new Color(72, 238, 25), new Color(208, 26, 65), 0.9f, Easing.SINE_IN)
            .setItemColor(c -> ColorHelper.brighter(c.primaryColor, 1))
            .build());
    public static SpiritTypeProperty SPIRIT_TYPE_PROPERTY = new SpiritTypeProperty("spirit_type", MalumRegistries.SPIRITS.getValues());

    public static int getIndexForSpiritType(MalumSpiritType type) {
        List<MalumSpiritType> types = MalumRegistries.SPIRITS.getValues().stream().toList();
        return types.indexOf(type);
    }
}
