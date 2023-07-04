package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.block.mana_mote.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.*;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(modid= MalumMod.MALUM, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class SpiritTypeRegistry {
    public static Map<String, MalumSpiritType> SPIRITS = new LinkedHashMap<>();

    public static MalumSpiritType SACRED_SPIRIT = create("sacred", new Color(243, 40, 143), ItemRegistry.SACRED_SPIRIT, BlockRegistry.MOTE_OF_SACRED_ARCANA);

    public static MalumSpiritType WICKED_SPIRIT = create("wicked", new Color(155, 62, 245), ItemRegistry.WICKED_SPIRIT, BlockRegistry.MOTE_OF_WICKED_ARCANA);

    public static MalumSpiritType ARCANE_SPIRIT = create("arcane", new Color(212, 55, 255), ItemRegistry.ARCANE_SPIRIT, BlockRegistry.MOTE_OF_RAW_ARCANA);

    public static MalumSpiritType ELDRITCH_SPIRIT = create("eldritch", new Color(125, 29, 215), new Color(39, 201, 103), ItemRegistry.ELDRITCH_SPIRIT, BlockRegistry.MOTE_OF_ELDRITCH_ARCANA);

    public static MalumSpiritType AERIAL_SPIRIT = create("aerial", new Color(75, 243, 218), ItemRegistry.AERIAL_SPIRIT, BlockRegistry.MOTE_OF_AERIAL_ARCANA);

    public static MalumSpiritType AQUEOUS_SPIRIT = create("aqueous", new Color(29, 100, 232), ItemRegistry.AQUEOUS_SPIRIT, BlockRegistry.MOTE_OF_AQUEOUS_ARCANA);

    public static MalumSpiritType INFERNAL_SPIRIT = create("infernal", new Color(210, 134, 39), ItemRegistry.INFERNAL_SPIRIT, BlockRegistry.MOTE_OF_INFERNAL_ARCANA);

    public static MalumSpiritType EARTHEN_SPIRIT = create("earthen", new Color(73, 234, 27), ItemRegistry.EARTHEN_SPIRIT, BlockRegistry.MOTE_OF_EARTHEN_ARCANA);

    public static SpiritTypeProperty SPIRIT_TYPE_PROPERTY = new SpiritTypeProperty("spirit_type", SPIRITS.values());

    public static MalumSpiritType create(String identifier, Color color, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote) {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, color, spiritShard, spiritMote);
        SPIRITS.put(identifier, spiritType);
        return spiritType;
    }

    public static MalumSpiritType create(String identifier, Color color, Color endColor, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote) {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, color, endColor, spiritShard, spiritMote);
        SPIRITS.put(identifier, spiritType);
        return spiritType;
    }

    public static int getIndexForSpiritType(MalumSpiritType type) {
        List<MalumSpiritType> types = SPIRITS.values().stream().toList();
        return types.indexOf(type);
    }

    public static MalumSpiritType getSpiritTypeForIndex(int slot) {
        if (slot >= SPIRITS.size())
            return null;
        List<MalumSpiritType> types = SPIRITS.values().stream().toList();
        return types.get(slot);
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
