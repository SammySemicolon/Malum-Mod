package com.sammy.malum.core.modcontent;

import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.souls.CountPredicate;
import com.sammy.malum.core.systems.souls.MalumSpiritType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.awt.*;
import java.util.ArrayList;

public class MalumSoulTypes
{
    public static ArrayList<MalumSpiritType> SPIRITS = new ArrayList<>();

    public static final Color HOLY_SPIRIT_COLOR = new Color(234, 73, 95);
    public static MalumSpiritType HOLY_SPIRIT;

    public static final Color WICKED_SPIRIT_COLOR = new Color(127, 34, 84);
    public static MalumSpiritType WICKED_SPIRIT;

    public static final Color ARCANE_SPIRIT_COLOR = new Color(218, 68, 231);
    public static  MalumSpiritType ARCANE_SPIRIT;

    public static final Color ELDRITCH_SPIRIT_COLOR = new Color(72, 31, 118);
    public static MalumSpiritType ELDRITCH_SPIRIT;

    public static final Color AERIAL_SPIRIT_COLOR = new Color(51, 229, 155);
    public static  MalumSpiritType AERIAL_SPIRIT;

    public static final Color AQUATIC_SPIRIT_COLOR = new Color(37, 77, 147);
    public static  MalumSpiritType AQUATIC_SPIRIT;

    public static final Color INFERNAL_SPIRIT_COLOR = new Color(210, 134, 39);
    public static  MalumSpiritType INFERNAL_SPIRIT;

    public static final Color EARTHEN_SPIRIT_COLOR = new Color(98, 180, 40);
    public static  MalumSpiritType EARTHEN_SPIRIT;

    public static void init()
    {
        HOLY_SPIRIT = create("holy", MalumSoulTypes::isLife, HOLY_SPIRIT_COLOR, MalumItems.HOLY_SPIRIT);
        WICKED_SPIRIT = create("wicked", MalumSoulTypes::isDeath, WICKED_SPIRIT_COLOR, MalumItems.WICKED_SPIRIT);
        ARCANE_SPIRIT = create("arcane", MalumSoulTypes::isMagic, ARCANE_SPIRIT_COLOR, MalumItems.ARCANE_SPIRIT);
        ELDRITCH_SPIRIT = create("eldritch", MalumSoulTypes::isEldritch, ELDRITCH_SPIRIT_COLOR, MalumItems.ELDRITCH_SPIRIT);
        AERIAL_SPIRIT = create("aerial", MalumSoulTypes::isAir, AERIAL_SPIRIT_COLOR, MalumItems.AERIAL_SPIRIT);
        AQUATIC_SPIRIT = create("aquatic", MalumSoulTypes::isWater, AQUATIC_SPIRIT_COLOR, MalumItems.AQUATIC_SPIRIT);
        INFERNAL_SPIRIT = create("infernal", MalumSoulTypes::isFire, INFERNAL_SPIRIT_COLOR, MalumItems.INFERNAL_SPIRIT);
        EARTHEN_SPIRIT = create("earthen", MalumSoulTypes::isEarth, EARTHEN_SPIRIT_COLOR, MalumItems.EARTHEN_SPIRIT);
    }

    public static MalumSpiritType create(String identifier, CountPredicate countPredicate, Color color, RegistryObject<Item> splinterItem)
    {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, countPredicate, color, (SpiritItem) splinterItem.get());
        SPIRITS.add(spiritType);
        return spiritType;
    }

    public static int isLife(LivingEntity entity) //size.
    {
        if (entity instanceof RavagerEntity)
        {
            return 5;
        }
        if (!entity.isEntityUndead() && entity instanceof TameableEntity && !(entity instanceof ParrotEntity))
        {
            return 3;
        }
        if (entity instanceof ChickenEntity || entity instanceof SquidEntity || entity instanceof DolphinEntity || entity instanceof ParrotEntity || entity instanceof AbstractRaiderEntity)
        {
            return 1;
        }
        if (entity instanceof AnimalEntity || entity instanceof AbstractVillagerEntity)
        {
            return 2;
        }
        return 0;
    }

    public static int isDeath(LivingEntity entity) //how menacing.
    {
        if (entity instanceof WitherEntity || entity instanceof RavagerEntity)
        {
            return 5;
        }
        if ((entity.isEntityUndead() && entity instanceof AbstractHorseEntity) || entity instanceof SpellcastingIllagerEntity)
        {
            return 3;
        }
        if (entity instanceof ZoglinEntity || entity instanceof ZombifiedPiglinEntity)
        {
            return 2;
        }
        if (entity.isEntityUndead() || entity instanceof AbstractRaiderEntity)
        {
            return 1;
        }
        return 0;
    }

    public static int isAir(LivingEntity entity) //zoom potential.
    {
        if (entity instanceof FlyingEntity || entity instanceof AbstractHorseEntity || entity instanceof StriderEntity || entity instanceof VexEntity || entity instanceof EndermanEntity || entity instanceof ShulkerEntity || entity instanceof BatEntity || entity instanceof ParrotEntity)
        {
            return 3;
        }
        if (entity instanceof BeeEntity || entity instanceof SilverfishEntity || entity instanceof OcelotEntity || entity instanceof CatEntity || entity instanceof RabbitEntity || entity instanceof FoxEntity)
        {
            return 2;
        }
        if (entity instanceof SpiderEntity || entity instanceof BlazeEntity)
        {
            return 1;
        }
        return 0;
    }

    public static int isWater(LivingEntity entity) //how is le fishe doing.
    {
        if (entity instanceof ElderGuardianEntity)
        {
            return 5;
        }
        if (entity instanceof GuardianEntity)
        {
            return 3;
        }
        if (entity instanceof SquidEntity || entity instanceof DrownedEntity || entity instanceof DolphinEntity)
        {
            return 2;
        }
        if (entity.getCreatureAttribute().equals(CreatureAttribute.WATER))
        {
            return 1;
        }
        return 0;
    }

    public static int isMagic(LivingEntity entity) //wooooo magic yeahhh.
    {
        if (entity instanceof SpellcastingIllagerEntity || entity instanceof WanderingTraderEntity || entity instanceof WitchEntity || entity instanceof GhastEntity)
        {
            return 3;
        }
        if (entity.getType().getRegistryName().getPath().equals("wraith") || (entity instanceof SlimeEntity && !(entity instanceof MagmaCubeEntity)) || entity instanceof PhantomEntity)
        {
            return 2;
        }
        if (entity instanceof AbstractSkeletonEntity || entity instanceof MooshroomEntity || entity instanceof MagmaCubeEntity || entity instanceof VexEntity || entity instanceof BlazeEntity || entity instanceof EndermiteEntity)
        {
            return 1;
        }
        return 0;
    }

    public static int isFire(LivingEntity entity) //how annoying are they to kill.
    {
        if (entity instanceof WitherEntity)
        {
            return 5;
        }
        if (entity instanceof GhastEntity || entity instanceof StriderEntity)
        {
            return 3;
        }
        if (entity instanceof AbstractPiglinEntity || entity instanceof BlazeEntity || entity instanceof CreeperEntity || entity instanceof ZoglinEntity || entity instanceof HoglinEntity)
        {
            return 2;
        }
        if (entity.isImmuneToFire() || entity instanceof WitherSkeletonEntity || entity instanceof MagmaCubeEntity || entity instanceof HuskEntity)
        {
            return 1;
        }
        return 0;
    }

    public static int isEarth(LivingEntity entity) //what if we merge into one :flushed:
    {
        if (entity instanceof IronGolemEntity)
        {
            return 5;
        }
        if (entity.getType().getRegistryName().getPath().equals("zombie_brute"))
        {
            return 3;
        }
        if (entity instanceof ZombieHorseEntity || entity instanceof AbstractPiglinEntity)
        {
            return 2;
        }
        if (entity instanceof ZombieEntity || entity instanceof CreeperEntity || entity instanceof EndermiteEntity || entity instanceof SheepEntity)
        {
            return 1;
        }
        return 0;
    }

    public static int isEldritch(LivingEntity entity) //hear me out, boss monster
    {
        if (!entity.isNonBoss())
        {
            return 2;
        }
        if (entity instanceof RavagerEntity || entity instanceof ElderGuardianEntity)
        {
            return 1;
        }
        if (entity instanceof EndermanEntity)
        {
            if (entity.world.rand.nextFloat() < 0.2f)
            {
                return 1;
            }
        }
        return 0;
    }
}
