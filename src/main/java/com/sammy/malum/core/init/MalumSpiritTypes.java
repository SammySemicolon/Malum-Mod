package com.sammy.malum.core.init;

import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.item.Item;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.fml.RegistryObject;

import java.awt.*;
import java.util.ArrayList;

import static net.minecraft.entity.CreatureAttribute.WATER;

@SuppressWarnings("unchecked")
public class MalumSpiritTypes
{
    public static ArrayList<MalumSpiritType> SPIRITS = new ArrayList<>();

    public static final Color SACRED_SPIRIT_COLOR = new Color(234, 73, 95);
    public static MalumSpiritType SACRED_SPIRIT = create("sacred", SACRED_SPIRIT_COLOR, MalumItems.SACRED_SPIRIT);

    public static final Color WICKED_SPIRIT_COLOR = new Color(178, 29, 232);
    public static MalumSpiritType WICKED_SPIRIT = create("wicked", WICKED_SPIRIT_COLOR, MalumItems.WICKED_SPIRIT);

    public static final Color ARCANE_SPIRIT_COLOR = new Color(231, 68, 196);
    public static MalumSpiritType ARCANE_SPIRIT = create("arcane", ARCANE_SPIRIT_COLOR, MalumItems.ARCANE_SPIRIT);

    public static final Color ELDRITCH_SPIRIT_COLOR = new Color(142, 62, 234, 255);
    public static MalumSpiritType ELDRITCH_SPIRIT = create("eldritch", ELDRITCH_SPIRIT_COLOR, MalumItems.ELDRITCH_SPIRIT);

    public static final Color AERIAL_SPIRIT_COLOR = new Color(51, 229, 155);
    public static MalumSpiritType AERIAL_SPIRIT = create("aerial", AERIAL_SPIRIT_COLOR, MalumItems.AERIAL_SPIRIT);

    public static final Color AQUATIC_SPIRIT_COLOR = new Color(42, 114, 232);
    public static MalumSpiritType AQUATIC_SPIRIT = create("aquatic", AQUATIC_SPIRIT_COLOR, MalumItems.AQUATIC_SPIRIT);

    public static final Color INFERNAL_SPIRIT_COLOR = new Color(210, 134, 39);
    public static MalumSpiritType INFERNAL_SPIRIT = create("infernal", INFERNAL_SPIRIT_COLOR, MalumItems.INFERNAL_SPIRIT);

    public static final Color EARTHEN_SPIRIT_COLOR = new Color(98, 180, 40);
    public static MalumSpiritType EARTHEN_SPIRIT = create("earthen", EARTHEN_SPIRIT_COLOR, MalumItems.EARTHEN_SPIRIT);

    public static MalumSpiritType create(String identifier, Color color, RegistryObject<Item> splinterItem)
    {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, color, splinterItem);
        SPIRITS.add(spiritType);
        return spiritType;
    }
}
