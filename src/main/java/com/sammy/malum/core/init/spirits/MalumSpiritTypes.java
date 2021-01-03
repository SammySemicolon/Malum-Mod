package com.sammy.malum.core.init.spirits;

import com.sammy.malum.core.systems.spirits.CountPredicate;
import com.sammy.malum.core.systems.spirits.types.MalumSpiritType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.world.DimensionType;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Predicate;

public class MalumSpiritTypes
{
    public static ArrayList<MalumSpiritType> SPIRITS = new ArrayList<>();
    
    public static final Color WILD_SPIRIT_COLOR = new Color(118, 255, 21);
    public static final MalumSpiritType WILD_SPIRIT = create("wild",  MalumSpiritTypes::isWild, MalumSpiritTypes::howWild,WILD_SPIRIT_COLOR);
    public static final Color UNDEAD_SPIRIT_COLOR = new Color(109, 23, 18);
    public static final MalumSpiritType UNDEAD_SPIRIT = create("undead", MalumSpiritTypes::isUndead, MalumSpiritTypes::howUndead, UNDEAD_SPIRIT_COLOR);
    public static final Color NIMBLE_SPIRIT_COLOR = new Color(173, 255, 243);
    public static final MalumSpiritType NIMBLE_SPIRIT = create("nimble",  MalumSpiritTypes::isNimble,MalumSpiritTypes::howNimble, NIMBLE_SPIRIT_COLOR);
    public static final Color AQUATIC_SPIRIT_COLOR = new Color(27, 163, 255);
    public static final MalumSpiritType AQUATIC_SPIRIT = create("aquatic",  MalumSpiritTypes::isAquatic, MalumSpiritTypes::howAquatic, AQUATIC_SPIRIT_COLOR);
    public static final Color SINISTER_SPIRIT_COLOR = new Color(142, 0, 135);
    public static final MalumSpiritType SINISTER_SPIRIT = create("sinister",  MalumSpiritTypes::isSinister,MalumSpiritTypes::howSinister, SINISTER_SPIRIT_COLOR);
    public static final Color ARCANE_SPIRIT_COLOR = new Color(224, 88, 205);
    public static final MalumSpiritType ARCANE_SPIRIT = create("arcane",  MalumSpiritTypes::isArcane, MalumSpiritTypes::howArcane, ARCANE_SPIRIT_COLOR);
    public static final Color SULPHURIC_SPIRIT_COLOR = new Color(255, 163, 89);
    public static final MalumSpiritType SULPHURIC_SPIRIT = create("sulphuric", MalumSpiritTypes::isSulphuric, MalumSpiritTypes::howSulphuric,SULPHURIC_SPIRIT_COLOR);
    public static final Color NETHERBORNE_SPIRIT_COLOR = new Color(163, 13, 10);
    public static final MalumSpiritType NETHERBORNE_SPIRIT = create("netherborne",  MalumSpiritTypes::isNetherborne, MalumSpiritTypes::howNetherborne,NETHERBORNE_SPIRIT_COLOR);
    public static final Color AVARICIOUS_SPIRIT_COLOR = new Color(253, 245, 95);
    public static final MalumSpiritType AVARICIOUS_SPIRIT = create("remedial",  MalumSpiritTypes::isAvaricious, MalumSpiritTypes::howAvaricious, AVARICIOUS_SPIRIT_COLOR);
    public static final Color FUSIBLE_SPIRIT_COLOR = new Color(147, 144, 141);
    public static final MalumSpiritType FUSIBLE_SPIRIT = create("fusible",  MalumSpiritTypes::isFusible, MalumSpiritTypes::howFusible, FUSIBLE_SPIRIT_COLOR);
    
    public static final Color TERMINUS_SPIRIT_COLOR = new Color(6, 57, 49);
    public static final MalumSpiritType TERMINUS_SPIRIT = create("terminus", MalumSpiritTypes::isTerminus, MalumSpiritTypes::howTerminus, TERMINUS_SPIRIT_COLOR);
    public static final Color ELDRITCH_SPIRIT_COLOR = new Color(19, 13, 17);
    public static final MalumSpiritType ELDRITCH_SPIRIT = create("eldritch", MalumSpiritTypes::isEldritch, MalumSpiritTypes::howEldritch, ELDRITCH_SPIRIT_COLOR);
    
    public static MalumSpiritType create(String identifier, Predicate<LivingEntity> predicate, CountPredicate countPredicate, Color color)
    {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, predicate,countPredicate, color, identifier + "_spirit_splinter");
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
    public static boolean isUndead(LivingEntity entity) //dead.
    {
        return entity.isEntityUndead();
    }
    
    public static int howUndead(LivingEntity entity) //how menacing.
    {
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
    public static boolean isNimble(LivingEntity entity) //capable of flying or fast
    {
        return entity instanceof FlyingEntity || entity instanceof VexEntity || entity instanceof ParrotEntity || entity instanceof BatEntity || entity instanceof CatEntity || entity instanceof FoxEntity || entity instanceof OcelotEntity || entity instanceof RabbitEntity || entity instanceof StriderEntity || entity instanceof EndermanEntity || entity instanceof SilverfishEntity;
    }
    
    public static int howNimble(LivingEntity entity) //zoom potential.
    {
        if (entity instanceof StriderEntity || entity instanceof SilverfishEntity)
        {
            return 5;
        }
        if (entity instanceof FlyingEntity || entity instanceof VexEntity || entity instanceof ParrotEntity)
        {
            return 3;
        }
        if (entity instanceof RabbitEntity || entity instanceof FoxEntity)
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
    public static boolean isSinister(LivingEntity entity) //illagers
    {
        if (entity.getType().getRegistryName().getPath().equals("necromancer"))
        {
            return true;
        }
        if (entity.getCreatureAttribute().equals(CreatureAttribute.ILLAGER))
        {
            return true;
        }
        return entity instanceof RavagerEntity || entity instanceof VexEntity || entity instanceof WitchEntity;
    }
    public static int howSinister(LivingEntity entity) //bad guys and stuff.
    {
        if (entity instanceof RavagerEntity)
        {
            return 10;
        }
        if (entity.getType().getRegistryName().getPath().equals("necromancer"))
        {
            return 5;
        }
        if (entity instanceof VexEntity)
        {
            return 3;
        }
        return 2;
    }
    
    public static boolean isArcane(LivingEntity entity) //made or capable of using magic, or a phenomenon
    {
        if (entity instanceof AbstractSkeletonEntity)
        {
            return true;
        }
        if (entity.getType().getRegistryName().getPath().equals("wraith"))
        {
            return true;
        }
        return entity instanceof SpellcastingIllagerEntity || entity instanceof VexEntity || entity instanceof WanderingTraderEntity || entity instanceof WitchEntity || entity instanceof PhantomEntity || entity instanceof GhastEntity || entity instanceof MooshroomEntity;
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
        if (entity instanceof VexEntity || entity instanceof PhantomEntity || entity instanceof GhastEntity || entity instanceof MooshroomEntity)
        {
            return 3;
        }
        return 1;
    }
    
    public static boolean isSulphuric(LivingEntity entity) //immune to explosions, filled with sulphur or explosive
    {
        return entity instanceof CreeperEntity || entity instanceof BlazeEntity || entity instanceof MagmaCubeEntity;
    }
    public static int howSulphuric(LivingEntity entity) //blazes are based, yo.
    {
        if (entity instanceof BlazeEntity)
        {
            return 2;
        }
        return 1;
    }
    
    public static boolean isNetherborne(LivingEntity entity) //born in the netha
    {
        return entity instanceof ZoglinEntity || entity instanceof GhastEntity || entity instanceof HoglinEntity || entity instanceof AbstractPiglinEntity || entity instanceof MagmaCubeEntity || entity instanceof StriderEntity || entity instanceof WitherEntity || entity instanceof WitherSkeletonEntity;
    }
    public static int howNetherborne(LivingEntity entity) //how annoying are they to kill.
    {
        if (entity instanceof WitherEntity)
        {
            return 25;
        }
        if (entity instanceof GhastEntity)
        {
            return 3;
        }
        if (entity instanceof StriderEntity)
        {
            return 3;
        }
        if (entity instanceof AbstractPiglinEntity || entity instanceof BlazeEntity)
        {
            return 2;
        }
        return 1;
    }
    
    public static boolean isAvaricious(LivingEntity entity) //greed, filthy pigs *spit*
    {
        return entity instanceof AbstractPiglinEntity || entity instanceof AbstractVillagerEntity;
    }
    public static int howAvaricious(LivingEntity entity) //damn shawty okay
    {
        if (entity instanceof PiglinBruteEntity || entity instanceof AbstractVillagerEntity)
        {
            return 5;
        }
        return 2;
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
    
    public static boolean isTerminus(LivingEntity entity) //end
    {
        return entity instanceof EndermanEntity || entity instanceof ShulkerEntity || entity instanceof EndermiteEntity;
    }
    public static int howTerminus(LivingEntity entity) //s-scaawyyyy~ >w<
    {
        if (entity.world.getDimensionKey().getRegistryName().equals(DimensionType.THE_END_ID))
        {
            return 3;
        }
        return 1;
    }
    
    public static boolean isEldritch(LivingEntity entity) //bosses
    {
        return !entity.isNonBoss();
    }
    public static int howEldritch(LivingEntity entity) //what if we merge into one :flushed:
    {
        return 4;
    }
    public static void init() {}
}