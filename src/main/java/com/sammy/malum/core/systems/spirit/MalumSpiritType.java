package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MalumSpiritType
{
    public static Map<ResourceLocation, MalumEntitySpiritDataBundle> SPIRIT_DATA = new HashMap<>();
    public final Color color;
    public final String identifier;
    protected Supplier<Item> splinterItem;

    public MalumSpiritType(String identifier, Color color, RegistryObject<Item> splinterItem)
    {
        this.identifier = identifier;
        this.color = color;
        this.splinterItem = splinterItem;
    }

    public MalumSpiritItem splinterItem()
    {
        return (MalumSpiritItem) splinterItem.get();
    }

    public ResourceLocation overlayTexture()
    {
        return MalumHelper.prefix("spirit/overlay_" + identifier);
    }
    public ResourceLocation runewoodCutoutTexture()
    {
        return MalumHelper.prefix("spirit/cutout_" + identifier);
    }
    public ResourceLocation soulwoodCutoutTexture()
    {
        return MalumHelper.prefix("spirit/corrupted_cutout_" + identifier);
    }
}
