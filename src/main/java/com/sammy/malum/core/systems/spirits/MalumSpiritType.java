package com.sammy.malum.core.systems.spirits;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.SpiritItem;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Predicate;

public class MalumSpiritType
{
    public final Color color;
    public final String identifier;
    public final String translationKey;
    public final String description;
    public SpiritItem splinterItem;

    public ArrayList<SpiritCountTest> tests = new ArrayList<>();
    public MalumSpiritType(String identifier, Color color, SpiritItem splinterItem)
    {

        this.identifier = identifier;
        this.color = color;
        
        this.translationKey = "malum.tooltip.spirit." + identifier;
        this.description = translationKey + "_description";
        
        this.splinterItem = splinterItem;
        splinterItem.type = this;
    }
    public int spiritCount(LivingEntity entity)
    {
        int count = 0;
        for (SpiritCountTest test : tests)
        {
            if (test.predicate.test(entity))
            {
                if (test.value == 0)
                {
                    return 0;
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
        public SpiritCountTest setImportant()
        {
            important = true;
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
        tests.add(new SpiritCountTest(value, e -> e.getType().getRegistryName().getPath().equals(name)).setImportant());
        return this;
    }
    public <U>  MalumSpiritType addTest(int value, Class<U> clazz)
    {
        tests.add(new SpiritCountTest(value, clazz::isInstance));
        return this;
    }
    public <U>  MalumSpiritType addTest(int value, Class<? extends U>... clazzes)
    {
        tests.add(new SpiritCountTest(value, e ->
        {
            for (Class<?> clazz : clazzes)
            {
                if (!clazz.isInstance(e))
                {
                    return false;
                }
            }
            return true;
        }));
        return this;
    }
}
