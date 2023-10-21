package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;

import java.awt.*;
import java.util.List;
import java.util.*;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(modid= MalumMod.MALUM, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class SpiritTypeRegistry {

    public static Map<String, MalumSpiritType> SPIRITS = new LinkedHashMap<>();

    public static MalumSpiritType SACRED_SPIRIT = create(MalumSpiritType.create("sacred", ItemRegistry.SACRED_SPIRIT, BlockRegistry.MOTE_OF_SACRED_ARCANA)
            .setColorData(new Color(238, 44, 136), new Color(40, 143, 243), 0.8f)
            .setItemColor(c -> c.primaryColor)
            .build());

    public static MalumSpiritType WICKED_SPIRIT = create(MalumSpiritType.create("wicked", ItemRegistry.WICKED_SPIRIT, BlockRegistry.MOTE_OF_WICKED_ARCANA)
            .setColorData(new Color(121, 44, 236), new Color(72, 21, 255), 0.8f)
            .setItemColor(c -> c.primaryColor)
            .build());

    public static MalumSpiritType ARCANE_SPIRIT = create(MalumSpiritType.create("arcane", ItemRegistry.ARCANE_SPIRIT, BlockRegistry.MOTE_OF_RAW_ARCANA)
            .setColorData(new Color(213, 70, 255), new Color(32, 222, 229), 1.1f, Easing.SINE_IN_OUT)
            .setItemColor(c -> ColorHelper.brighter(c.primaryColor, 1))
            .build());

    public static MalumSpiritType ELDRITCH_SPIRIT = create(MalumSpiritType.create("eldritch", ItemRegistry.ELDRITCH_SPIRIT, BlockRegistry.MOTE_OF_ELDRITCH_ARCANA)
            .setColorData(new Color(203, 12, 248), new Color(24, 78, 164), 0.9f)
            .setItemColor(c -> ColorHelper.darker(c.primaryColor, 1))
            .build());

    public static MalumSpiritType AERIAL_SPIRIT = create(MalumSpiritType.create("aerial", ItemRegistry.AERIAL_SPIRIT, BlockRegistry.MOTE_OF_AERIAL_ARCANA)
            .setColorData(new Color(75, 243, 218), new Color(243, 218, 75), 1.1f, Easing.SINE_IN)
            .setItemColor(c -> ColorHelper.brighter(c.primaryColor, 1))
            .build());

    public static MalumSpiritType AQUEOUS_SPIRIT = create(MalumSpiritType.create("aqueous", ItemRegistry.AQUEOUS_SPIRIT, BlockRegistry.MOTE_OF_AQUEOUS_ARCANA)
            .setColorData(new Color(29, 100, 232), new Color(41, 238, 133), 0.8f, Easing.SINE_IN_OUT)
            .setItemColor(c -> ColorHelper.brighter(c.primaryColor, 1))
            .build());

    public static MalumSpiritType INFERNAL_SPIRIT = create(MalumSpiritType.create("infernal", ItemRegistry.INFERNAL_SPIRIT, BlockRegistry.MOTE_OF_INFERNAL_ARCANA)
            .setColorData(new Color(250, 154, 31), new Color(210, 39, 150), 0.9f, Easing.SINE_IN_OUT)
            .setItemColor(c -> ColorHelper.brighter(c.primaryColor, 1))
            .build());

    public static MalumSpiritType EARTHEN_SPIRIT = create(MalumSpiritType.create("earthen", ItemRegistry.EARTHEN_SPIRIT, BlockRegistry.MOTE_OF_EARTHEN_ARCANA)
            .setColorData(new Color(72, 238, 25), new Color(208, 26, 65), 0.9f, Easing.SINE_IN)
            .setItemColor(c -> ColorHelper.brighter(c.primaryColor, 1))
            .build());

    public static SpiritTypeProperty SPIRIT_TYPE_PROPERTY = new SpiritTypeProperty("spirit_type", SPIRITS.values());

    public static MalumSpiritType create(MalumSpiritType spiritType) {
        SPIRITS.put(spiritType.identifier, spiritType);
        return spiritType;
    }

    public static int getIndexForSpiritType(MalumSpiritType type) {
        List<MalumSpiritType> types = SPIRITS.values().stream().toList();
        return types.indexOf(type);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void stitchTextures(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            return;
        }
        SPIRITS.forEach((s, t) -> event.addSprite(t.getTotemGlowTexture()));
    }
}
