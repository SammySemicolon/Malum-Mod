package com.sammy.malum.core.systems.spirits;

import com.sammy.malum.common.items.SpiritItem;
import net.minecraft.block.ObserverBlock;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MalumSpiritType
{
    public final Color color;
    public final String identifier;
    public final String translationKey;
    public final String description;
    protected Supplier<Item> splinterItem;

    public ArrayList<SpiritCountTest> tests = new ArrayList<>();
    public MalumSpiritType(String identifier, Color color, RegistryObject<Item> splinterItem)
    {

        this.identifier = identifier;
        this.color = color;

        this.translationKey = "malum.tooltip.spirit." + identifier;
        this.description = translationKey + "_description";

        this.splinterItem = splinterItem;
    }
    public SpiritItem splinterItem()
    {
        return (SpiritItem) splinterItem.get();
    }
    public int spiritCount(LivingEntity entity)
    {
        int count = 0;
        for (SpiritCountTest test : tests)
        {
            if (test.predicate.test(entity))
            {
                if (test.important)
                {
                    return test.value;
                }
                if (test.value > count)
                {
                    count = test.value;
                }
            }
        }
        return count;
    }
    public static class SpiritCountTest
    {
        public int value;
        public Predicate<LivingEntity> predicate;
        public boolean important;
        public SpiritCountTest(int value, Predicate<LivingEntity> predicate)
        {
            this.value = value;
            this.predicate = predicate;
        }
        public SpiritCountTest setImportant(boolean important)
        {
            this.important = important;
            return this;
        }
    }
    public MalumSpiritType addTest(int value, Predicate<LivingEntity> predicate)
    {
        tests.add(new SpiritCountTest(value, predicate));
        return this;
    }
    public MalumSpiritType addTest(int value, CreatureAttribute attribute)
    {
        tests.add(new SpiritCountTest(value, e -> e.getCreatureAttribute().equals(attribute)));
        return this;
    }
    public MalumSpiritType addTest(int value, String name)
    {
        tests.add(new SpiritCountTest(value, e -> e.getType().getRegistryName().getPath().equals(name)).setImportant(true));
        return this;
    }
    public <U>  MalumSpiritType addTest(int value, Class<U> clazz, boolean important)
    {
        tests.add(new SpiritCountTest(value, clazz::isInstance).setImportant(important));
        return this;
    }

    public <U>  MalumSpiritType addTest(int value, Class<? extends U>... clazzes)
    {
        tests.add(new SpiritCountTest(value, e ->
        {
            for (Class<?> clazz : clazzes)
            {
                if (clazz.isInstance(e))
                {
                    return true;
                }
            }
            return false;
        }));
        return this;
    }
}
