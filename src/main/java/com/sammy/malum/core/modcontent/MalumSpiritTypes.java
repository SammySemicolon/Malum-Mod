package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.spirits.CountPredicate;
import com.sammy.malum.common.items.SpiritSplinterItem;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Predicate;

public class MalumSpiritTypes
{
    public static ArrayList<MalumSpiritType> SPIRITS = new ArrayList<>();
    
    public static final Color LIFE_SPIRIT_COLOR = new Color(153, 222, 58);
    public static MalumSpiritType LIFE_SPIRIT;
    
    public static final Color DEATH_SPIRIT_COLOR = new Color(135, 54, 46);
    public static MalumSpiritType DEATH_SPIRIT;
    
    public static final Color AIR_SPIRIT_COLOR = new Color(173, 255, 243);
    public static  MalumSpiritType AIR_SPIRIT;
    
    public static final Color WATER_SPIRIT_COLOR = new Color(27, 163, 255);
    public static  MalumSpiritType WATER_SPIRIT;
    
    public static final Color FIRE_SPIRIT_COLOR = new Color(163, 13, 10);
    public static  MalumSpiritType FIRE_SPIRIT;
    
    public static final Color EARTH_SPIRIT_COLOR = new Color(62, 101, 44);
    public static  MalumSpiritType EARTH_SPIRIT;
    
    public static final Color MAGIC_SPIRIT_COLOR = new Color(224, 88, 205);
    public static  MalumSpiritType MAGIC_SPIRIT;
    
    public static final Color ELDRITCH_SPIRIT_COLOR = new Color(19, 13, 17);
    public static MalumSpiritType ELDRITCH_SPIRIT;
    
    public static void init()
    {
        LIFE_SPIRIT = create("life", MalumSpiritTypes::isWild, MalumSpiritTypes::howWild, LIFE_SPIRIT_COLOR, MalumItems.LIFE_SPIRIT_SPLINTER);
        DEATH_SPIRIT = create("death", MalumSpiritTypes::isGrave, MalumSpiritTypes::howGrave, DEATH_SPIRIT_COLOR, MalumItems.DEATH_SPIRIT_SPLINTER);
        AIR_SPIRIT = create("air", MalumSpiritTypes::isSwift, MalumSpiritTypes::howSwift, AIR_SPIRIT_COLOR, MalumItems.AIR_SPIRIT_SPLINTER);
        WATER_SPIRIT = create("water", MalumSpiritTypes::isAquatic, MalumSpiritTypes::howAquatic, WATER_SPIRIT_COLOR, MalumItems.WATER_SPIRIT_SPLINTER);
        FIRE_SPIRIT = create("fire", MalumSpiritTypes::isNetherborne, MalumSpiritTypes::howNetherborne, FIRE_SPIRIT_COLOR, MalumItems.FIRE_SPIRIT_SPLINTER);
        EARTH_SPIRIT = create("earth", MalumSpiritTypes::isFusible, MalumSpiritTypes::howFusible, EARTH_SPIRIT_COLOR, MalumItems.FIRE_SPIRIT_SPLINTER);
        MAGIC_SPIRIT = create("magic", MalumSpiritTypes::isArcane, MalumSpiritTypes::howArcane, MAGIC_SPIRIT_COLOR, MalumItems.MAGIC_SPIRIT_SPLINTER);
        ELDRITCH_SPIRIT = create("eldritch", MalumSpiritTypes::isEldritch, MalumSpiritTypes::howEldritch, ELDRITCH_SPIRIT_COLOR, MalumItems.ELDRITCH_SPIRIT_SPLINTER);
    }
    
    public static MalumSpiritType create(String identifier, Predicate<LivingEntity> predicate, CountPredicate countPredicate, Color color, RegistryObject<Item> splinterItem)
    {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, predicate, countPredicate, color, (SpiritSplinterItem) splinterItem.get());
        SPIRITS.add(spiritType);
        return spiritType;
    }
    
    public static boolean isWild(LivingEntity entity) //animal.
    {
        return entity instanceof AnimalEntity || entity instanceof SquidEntity;
    }
    
    public static int howWild(LivingEntity entity) //size.
    {
        if (entity instanceof AbstractHorseEntity)
        {
            return 3;
        }
        if (entity instanceof CowEntity || entity instanceof SheepEntity)
        {
            return 2;
        }
        return 1;
    }
    
    public static boolean isGrave(LivingEntity entity) //dead.
    {
        return entity.isEntityUndead();
    }
    
    public static int howGrave(LivingEntity entity) //how menacing.
    {
        if (entity instanceof WitherEntity)
        {
            return 20;
        }
        if (entity instanceof ZombieHorseEntity || entity instanceof SkeletonHorseEntity)
        {
            return 3;
        }
        if (entity instanceof ZoglinEntity || entity instanceof ZombifiedPiglinEntity)
        {
            return 2;
        }
        return 1;
    }
    
    public static boolean isSwift(LivingEntity entity) //capable of flying or fast
    {
        return entity instanceof AbstractHorseEntity || entity instanceof SpiderEntity || entity instanceof FlyingEntity || entity instanceof VexEntity || entity instanceof ParrotEntity || entity instanceof BatEntity || entity instanceof CatEntity || entity instanceof FoxEntity || entity instanceof OcelotEntity || entity instanceof RabbitEntity || entity instanceof StriderEntity || entity instanceof EndermanEntity || entity instanceof SilverfishEntity;
    }
    
    public static int howSwift(LivingEntity entity) //zoom potential.
    {
        if (entity instanceof StriderEntity || entity instanceof SilverfishEntity || entity instanceof AbstractHorseEntity)
        {
            return 5;
        }
        if (entity instanceof FlyingEntity || entity instanceof VexEntity || entity instanceof ParrotEntity)
        {
            return 3;
        }
        if (entity instanceof RabbitEntity || entity instanceof FoxEntity || entity instanceof SpiderEntity)
        {
            return 2;
        }
        return 1;
    }
    
    public static boolean isAquatic(LivingEntity entity) //le fishe
    {
        return entity.getCreatureAttribute().equals(CreatureAttribute.WATER);
    }
    
    public static int howAquatic(LivingEntity entity) //how is le fishe doing.
    {
        if (entity instanceof ElderGuardianEntity)
        {
            return 5;
        }
        if (entity instanceof GuardianEntity)
        {
            return 3;
        }
        if (entity instanceof SquidEntity)
        {
            return 2;
        }
        return 1;
    }
    
    public static boolean isArcane(LivingEntity entity) //made or capable of using magic, or a phenomenon
    {
        if (entity.getType().getRegistryName().getPath().equals("wraith"))
        {
            return true;
        }
        return entity instanceof SpellcastingIllagerEntity || entity instanceof SlimeEntity || entity instanceof AbstractSkeletonEntity || entity instanceof VexEntity || entity instanceof WanderingTraderEntity || entity instanceof WitchEntity || entity instanceof PhantomEntity || entity instanceof GhastEntity || entity instanceof MooshroomEntity;
    }
    
    public static int howArcane(LivingEntity entity) //wooooo magic yeahhh.
    {
        if (entity instanceof WanderingTraderEntity || entity instanceof SpellcastingIllagerEntity || entity instanceof WitchEntity)
        {
            return 5;
        }
        if (entity.getType().getRegistryName().getPath().equals("necromancer"))
        {
            return 5;
        }
        if (entity instanceof SlimeEntity || entity instanceof VexEntity || entity instanceof PhantomEntity || entity instanceof GhastEntity || entity instanceof MooshroomEntity)
        {
            return 3;
        }
        if (entity.getType().getRegistryName().getPath().equals("wraith"))
        {
            return 3;
        }
        return 1;
    }
    
    public static boolean isNetherborne(LivingEntity entity) //born in the netha
    {
        if (entity.isImmuneToFire())
        {
            return true;
        }
        return entity instanceof CreeperEntity || entity instanceof HuskEntity || entity instanceof ZoglinEntity || entity instanceof GhastEntity || entity instanceof HoglinEntity || entity instanceof AbstractPiglinEntity || entity instanceof MagmaCubeEntity || entity instanceof StriderEntity || entity instanceof WitherEntity || entity instanceof WitherSkeletonEntity;
    }
    
    public static int howNetherborne(LivingEntity entity) //how annoying are they to kill.
    {
        if (entity instanceof WitherEntity)
        {
            return 25;
        }
        if (entity instanceof GhastEntity || entity instanceof StriderEntity)
        {
            return 3;
        }
        if (entity instanceof AbstractPiglinEntity || entity instanceof BlazeEntity)
        {
            return 2;
        }
        return 1;
    }
    
    public static boolean isFusible(LivingEntity entity) //metal, iron stuff
    {
        if (entity.getType().getRegistryName().getPath().equals("zombie_brute"))
        {
            return true;
        }
        return entity instanceof IronGolemEntity || entity instanceof ZombieEntity || entity instanceof ZombieHorseEntity || entity instanceof AbstractPiglinEntity;
    }
    
    public static int howFusible(LivingEntity entity) //what if we merge into one :flushed:
    {
        if (entity instanceof IronGolemEntity)
        {
            return 25;
        }
        if (entity.getType().getRegistryName().getPath().equals("zombie_brute"))
        {
            return 5;
        }
        return 1;
    }
    
    public static boolean isEldritch(LivingEntity entity) //bosses
    {
        if (entity instanceof RavagerEntity)
        {
            return true;
        }
        return !entity.isNonBoss();
    }
    
    public static int howEldritch(LivingEntity entity) //hear me out, boss monster
    {
        if (entity instanceof RavagerEntity)
        {
            return 1;
        }
        return 4;
    }
}
