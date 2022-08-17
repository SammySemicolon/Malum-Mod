package com.sammy.malum.core.setup.content;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritTypeProperty;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(modid= MalumMod.MALUM, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class SpiritTypeRegistry {
    public static Map<String, MalumSpiritType> SPIRITS = new LinkedHashMap<>();

    public static MalumSpiritType SACRED_SPIRIT = create("sacred", new Color(243, 40, 143), ItemRegistry.SACRED_SPIRIT, 1f);

    public static MalumSpiritType WICKED_SPIRIT = create("wicked", new Color(155, 62, 245), ItemRegistry.WICKED_SPIRIT, 1.5f);

    public static MalumSpiritType ARCANE_SPIRIT = create("arcane", new Color(212, 55, 255), ItemRegistry.ARCANE_SPIRIT, 1.25f);

    public static MalumSpiritType ELDRITCH_SPIRIT = create("eldritch", new Color(125, 29, 215), new Color(39, 201, 103), ItemRegistry.ELDRITCH_SPIRIT, 3f);

    public static MalumSpiritType AERIAL_SPIRIT = create("aerial", new Color(75, 243, 218), ItemRegistry.AERIAL_SPIRIT, 1.25f);

    public static MalumSpiritType AQUEOUS_SPIRIT = create("aqueous", new Color(29, 100, 232), ItemRegistry.AQUEOUS_SPIRIT, 1.25f);

    public static MalumSpiritType INFERNAL_SPIRIT = create("infernal", new Color(210, 134, 39), ItemRegistry.INFERNAL_SPIRIT, 1.5f);

    public static MalumSpiritType EARTHEN_SPIRIT = create("earthen", new Color(73, 234, 27), ItemRegistry.EARTHEN_SPIRIT, 1.5f);

    public static SpiritTypeProperty SPIRIT_TYPE_PROPERTY = new SpiritTypeProperty("spirit_type", SPIRITS.values());

    public static MalumSpiritType create(String identifier, Color color, RegistryObject<Item> splinterItem, float weight) {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, color, splinterItem, weight);
        SPIRITS.put(identifier, spiritType);
        return spiritType;
    }

    public static MalumSpiritType create(String identifier, Color color, Color endColor, RegistryObject<Item> splinterItem, float weight) {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, color, endColor, splinterItem, weight);
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
        SPIRITS.forEach((s, t) -> event.addSprite(t.getOverlayTexture()));
    }
}
