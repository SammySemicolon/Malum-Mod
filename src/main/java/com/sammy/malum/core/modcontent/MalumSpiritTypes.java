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
    
    public static final Color LIFE_SPIRIT_COLOR = new Color(241, 76, 123);
    public static MalumSpiritType LIFE_SPIRIT;
    
    public static final Color DEATH_SPIRIT_COLOR = new Color(113, 41, 57);
    public static MalumSpiritType DEATH_SPIRIT;
    
    public static final Color AIR_SPIRIT_COLOR = new Color(173, 255, 243);
    public static  MalumSpiritType AIR_SPIRIT;
    
    public static final Color WATER_SPIRIT_COLOR = new Color(25, 118, 231);
    public static  MalumSpiritType WATER_SPIRIT;
    
    public static final Color FIRE_SPIRIT_COLOR = new Color(175, 63, 19);
    public static  MalumSpiritType FIRE_SPIRIT;
    
    public static final Color EARTH_SPIRIT_COLOR = new Color(65, 125, 42);
    public static  MalumSpiritType EARTH_SPIRIT;
    
    public static final Color MAGIC_SPIRIT_COLOR = new Color(243, 85, 218);
    public static  MalumSpiritType MAGIC_SPIRIT;
    
    public static final Color ELDRITCH_SPIRIT_COLOR = new Color(85, 17, 66);
    public static MalumSpiritType ELDRITCH_SPIRIT;
    
    public static void init()
    {
        LIFE_SPIRIT = create("life", MalumSpiritTypes::isLife, MalumSpiritTypes::howLife, LIFE_SPIRIT_COLOR, MalumItems.LIFE_SPIRIT_SPLINTER);
        DEATH_SPIRIT = create("death", MalumSpiritTypes::isDeath, MalumSpiritTypes::howDeath, DEATH_SPIRIT_COLOR, MalumItems.DEATH_SPIRIT_SPLINTER);
        AIR_SPIRIT = create("air", MalumSpiritTypes::isAir, MalumSpiritTypes::howAir, AIR_SPIRIT_COLOR, MalumItems.AIR_SPIRIT_SPLINTER);
        WATER_SPIRIT = create("water", MalumSpiritTypes::isWater, MalumSpiritTypes::howWater, WATER_SPIRIT_COLOR, MalumItems.WATER_SPIRIT_SPLINTER);
        FIRE_SPIRIT = create("fire", MalumSpiritTypes::isFire, MalumSpiritTypes::howFire, FIRE_SPIRIT_COLOR, MalumItems.FIRE_SPIRIT_SPLINTER);
        EARTH_SPIRIT = create("earth", MalumSpiritTypes::isEarth, MalumSpiritTypes::howEarth, EARTH_SPIRIT_COLOR, MalumItems.EARTH_SPIRIT_SPLINTER);
        MAGIC_SPIRIT = create("magic", MalumSpiritTypes::isMagic, MalumSpiritTypes::howMagic, MAGIC_SPIRIT_COLOR, MalumItems.MAGIC_SPIRIT_SPLINTER);
        ELDRITCH_SPIRIT = create("eldritch", MalumSpiritTypes::isEldritch, MalumSpiritTypes::howEldritch, ELDRITCH_SPIRIT_COLOR, MalumItems.ELDRITCH_SPIRIT_SPLINTER);
    }
    
    public static MalumSpiritType create(String identifier, Predicate<LivingEntity> predicate, CountPredicate countPredicate, Color color, RegistryObject<Item> splinterItem)
    {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, predicate, countPredicate, color, (SpiritSplinterItem) splinterItem.get());
        SPIRITS.add(spiritType);
        return spiritType;
    }
    
    public static boolean isLife(LivingEntity entity) //animal.
    {
        return entity instanceof AnimalEntity || entity instanceof SquidEntity;
    }
    
    public static int howLife(LivingEntity entity) //size.
    {
        if (entity instanceof AbstractHorseEntity)
        {
            return 3;
        }
        if (entity instanceof TameableEntity || entity instanceof CowEntity || entity instanceof SheepEntity)
        {
            return 2;
        }
        return 1;
    }
    
    public static boolean isDeath(LivingEntity entity) //dead.
    {
        return entity.isEntityUndead();
    }
    
    public static int howDeath(LivingEntity entity) //how menacing.
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
    
    public static boolean isAir(LivingEntity entity) //capable of flying or fast
    {
        return entity instanceof BeeEntity || entity instanceof AbstractHorseEntity || entity instanceof SpiderEntity || entity instanceof FlyingEntity || entity instanceof VexEntity || entity instanceof ParrotEntity || entity instanceof BatEntity || entity instanceof CatEntity || entity instanceof FoxEntity || entity instanceof OcelotEntity || entity instanceof RabbitEntity || entity instanceof StriderEntity || entity instanceof EndermanEntity || entity instanceof SilverfishEntity;
    }
    
    public static int howAir(LivingEntity entity) //zoom potential.
    {
        if (entity instanceof StriderEntity || entity instanceof SilverfishEntity || entity instanceof AbstractHorseEntity)
        {
            return 5;
        }
        if (entity instanceof FlyingEntity || entity instanceof VexEntity || entity instanceof ParrotEntity)
        {
            return 3;
        }
        if (entity instanceof BeeEntity || entity instanceof RabbitEntity || entity instanceof FoxEntity || entity instanceof SpiderEntity)
        {
            return 2;
        }
        return 1;
    }
    
    public static boolean isWater(LivingEntity entity) //le fishe
    {
        return entity.getCreatureAttribute().equals(CreatureAttribute.WATER);
    }
    
    public static int howWater(LivingEntity entity) //how is le fishe doing.
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
    
    public static boolean isMagic(LivingEntity entity) //made or capable of using magic, or a phenomenon
    {
        if (entity.getType().getRegistryName().getPath().equals("wraith"))
        {
            return true;
        }
        return entity instanceof SpellcastingIllagerEntity || entity instanceof SlimeEntity || entity instanceof AbstractSkeletonEntity || entity instanceof VexEntity || entity instanceof WanderingTraderEntity || entity instanceof WitchEntity || entity instanceof PhantomEntity || entity instanceof GhastEntity || entity instanceof MooshroomEntity;
    }
    
    public static int howMagic(LivingEntity entity) //wooooo magic yeahhh.
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
    
    public static boolean isFire(LivingEntity entity) //born in the netha
    {
        if (entity.isImmuneToFire())
        {
            return true;
        }
        return entity instanceof CreeperEntity || entity instanceof HuskEntity || entity instanceof ZoglinEntity || entity instanceof GhastEntity || entity instanceof HoglinEntity || entity instanceof AbstractPiglinEntity || entity instanceof MagmaCubeEntity || entity instanceof StriderEntity || entity instanceof WitherEntity || entity instanceof WitherSkeletonEntity;
    }
    
    public static int howFire(LivingEntity entity) //how annoying are they to kill.
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
    
    public static boolean isEarth(LivingEntity entity) //metal, iron stuff
    {
        if (entity.getType().getRegistryName().getPath().equals("zombie_brute"))
        {
            return true;
        }
        return entity instanceof IronGolemEntity || entity instanceof ZombieEntity || entity instanceof ZombieHorseEntity || entity instanceof AbstractPiglinEntity;
    }
    
    public static int howEarth(LivingEntity entity) //what if we merge into one :flushed:
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
