package com.sammy.malum.core.mod_systems.spirit;

import com.sammy.malum.common.item.SpiritItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
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

    public SpiritItem splinterItem()
    {
        return (SpiritItem) splinterItem.get();
    }
    public static class MalumEntitySpiritDataBundle
    {
        public final int totalCount;
        public final ArrayList<MalumEntitySpiritData> data;
        public MalumEntitySpiritDataBundle(ArrayList<MalumEntitySpiritData> data)
        {
            this.totalCount = data.stream().mapToInt(d->d.count).sum();
            this.data = data;
        }
    }
    public static class MalumEntitySpiritData
    {
        public final MalumSpiritType type;
        public final int count;
        public MalumEntitySpiritData(MalumSpiritType type, int count)
        {
            this.type = type;
            this.count = count;
        }
    }
}
