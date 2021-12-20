package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.core.helper.DataHelper;
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
        return DataHelper.prefix("block/totem/" + identifier + "_glow");
    }
    public ResourceLocation runewoodCutoutTexture()
    {
        return DataHelper.prefix("block/totem/" + identifier + "_runewood_cutout");
    }
    public ResourceLocation soulwoodCutoutTexture()
    {
        return DataHelper.prefix("block/totem/" + identifier + "_soulwood_cutout");
    }
}
