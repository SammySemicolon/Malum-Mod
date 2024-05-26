package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;

import java.awt.*;
import java.util.List;
import java.util.*;

@SuppressWarnings("unchecked")
public class SpiritTypeRegistry {

    public static Map<String, MalumSpiritType> SPIRITS = new LinkedHashMap<>();

    public static MalumSpiritType SACRED_SPIRIT = register(MalumSpiritType.create("sacred",
                    new SpiritVisualMotif(new Color(238, 44, 136), new Color(40, 143, 243), 0.8f, Easing.LINEAR),
                    ItemRegistry.SACRED_SPIRIT, BlockRegistry.MOTE_OF_SACRED_ARCANA)
            .setItemColor(SpiritVisualMotif::getPrimaryColor)
            .build());

    public static MalumSpiritType WICKED_SPIRIT = register(MalumSpiritType.create("wicked",
                    new SpiritVisualMotif(new Color(121, 44, 236), new Color(72, 21, 255), 0.8f, Easing.LINEAR),
                    ItemRegistry.WICKED_SPIRIT, BlockRegistry.MOTE_OF_WICKED_ARCANA)
            .setItemColor(SpiritVisualMotif::getPrimaryColor)
            .build());

    public static MalumSpiritType ARCANE_SPIRIT = register(MalumSpiritType.create("arcane",
                    new SpiritVisualMotif(new Color(213, 70, 255), new Color(32, 222, 229), 1.1f, Easing.SINE_IN_OUT),
                    ItemRegistry.ARCANE_SPIRIT, BlockRegistry.MOTE_OF_RAW_ARCANA)
            .setItemColor(c -> ColorHelper.brighter(c.getPrimaryColor(), 1))
            .build());

    public static MalumSpiritType ELDRITCH_SPIRIT = register(MalumSpiritType.create("eldritch",
                    new SpiritVisualMotif(new Color(203, 12, 248), new Color(24, 78, 164), 0.9f, Easing.LINEAR),
                    ItemRegistry.ELDRITCH_SPIRIT, BlockRegistry.MOTE_OF_ELDRITCH_ARCANA)
            .setItemColor(c -> ColorHelper.darker(c.getPrimaryColor(), 1))
            .build());

    public static MalumSpiritType AERIAL_SPIRIT = register(MalumSpiritType.create("aerial",
                    new SpiritVisualMotif(new Color(75, 243, 218), new Color(243, 218, 75), 1.1f, Easing.SINE_IN),
                    ItemRegistry.AERIAL_SPIRIT, BlockRegistry.MOTE_OF_AERIAL_ARCANA)
            .setItemColor(c -> ColorHelper.brighter(c.getPrimaryColor(), 1))
            .build());

    public static MalumSpiritType AQUEOUS_SPIRIT = register(MalumSpiritType.create("aqueous",
                    new SpiritVisualMotif(new Color(29, 100, 232), new Color(41, 238, 133), 0.8f, Easing.SINE_IN_OUT),
                    ItemRegistry.AQUEOUS_SPIRIT, BlockRegistry.MOTE_OF_AQUEOUS_ARCANA)
            .setItemColor(c -> ColorHelper.brighter(c.getPrimaryColor(), 1))
            .build());

    public static MalumSpiritType EARTHEN_SPIRIT = register(MalumSpiritType.create("earthen",
                    new SpiritVisualMotif(new Color(72, 238, 25), new Color(208, 26, 65), 0.9f, Easing.SINE_IN),
                    ItemRegistry.EARTHEN_SPIRIT, BlockRegistry.MOTE_OF_EARTHEN_ARCANA)
            .setItemColor(c -> ColorHelper.brighter(c.getPrimaryColor(), 1))
            .build());

    public static MalumSpiritType INFERNAL_SPIRIT = register(MalumSpiritType.create("infernal",
                    new SpiritVisualMotif(new Color(250, 154, 31), new Color(210, 39, 150), 0.9f, Easing.SINE_IN_OUT),
                    ItemRegistry.INFERNAL_SPIRIT, BlockRegistry.MOTE_OF_INFERNAL_ARCANA)
            .setItemColor(c -> ColorHelper.brighter(c.getPrimaryColor(), 1))
            .build());

    public static SpiritTypeProperty SPIRIT_TYPE_PROPERTY = new SpiritTypeProperty("spirit_type", SPIRITS.values());

    public static MalumSpiritType UMBRAL_SPIRIT = register(MalumSpiritType.create("umbral",
                    new UmbralSpiritMotif(new Color(19, 5, 24), new Color(7, 1, 1), 0.9f, Easing.SINE_IN_OUT),
                    ItemRegistry.UMBRAL_SPIRIT, null)
            .setItemColor(SpiritVisualMotif::getPrimaryColor)
            .build(UmbralSpiritType::new));

    public static MalumSpiritType register(MalumSpiritType spiritType) {
        SPIRITS.put(spiritType.identifier, spiritType);
        return spiritType;
    }

    public static int getIndexForSpiritType(MalumSpiritType type) {
        List<MalumSpiritType> types = SPIRITS.values().stream().toList();
        return types.indexOf(type);
    }

    public static void init() {
    }
}