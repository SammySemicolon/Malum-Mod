package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.spirits.CountPredicate;
import com.sammy.malum.core.systems.spirits.item.SpiritSplinterItem;
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
import net.minecraft.item.Item;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.RegistryObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Predicate;

public class MalumSpiritTypes
{
    public static ArrayList<MalumSpiritType> SPIRITS = new ArrayList<>();
    
    public static final Color WILD_SPIRIT_COLOR = new Color(118, 255, 21);
    public static  MalumSpiritType WILD_SPIRIT;
    
    public static final Color UNDEAD_SPIRIT_COLOR = new Color(109, 23, 18);
    public static  MalumSpiritType UNDEAD_SPIRIT;
    
    public static final Color NIMBLE_SPIRIT_COLOR = new Color(173, 255, 243);
    public static  MalumSpiritType NIMBLE_SPIRIT;
    
    public static final Color AQUATIC_SPIRIT_COLOR = new Color(27, 163, 255);
    public static  MalumSpiritType AQUATIC_SPIRIT;
    
    public static final Color SINISTER_SPIRIT_COLOR = new Color(142, 0, 135);
    public static  MalumSpiritType SINISTER_SPIRIT;
    
    public static final Color ARCANE_SPIRIT_COLOR = new Color(224, 88, 205);
    public static  MalumSpiritType ARCANE_SPIRIT;
    
    public static final Color SULPHURIC_SPIRIT_COLOR = new Color(255, 163, 89);
    public static  MalumSpiritType SULPHURIC_SPIRIT;
    
    public static final Color NETHERBORNE_SPIRIT_COLOR = new Color(163, 13, 10);
    public static  MalumSpiritType NETHERBORNE_SPIRIT;
    
    public static final Color AVARICIOUS_SPIRIT_COLOR = new Color(253, 245, 95);
    public static  MalumSpiritType AVARICIOUS_SPIRIT;
    
    public static final Color FUSIBLE_SPIRIT_COLOR = new Color(147, 144, 141);
    public static MalumSpiritType FUSIBLE_SPIRIT;
    
    public static final Color CHARRED_SPIRIT_COLOR = new Color(66, 47, 47);
    public static MalumSpiritType CHARRED_SPIRIT;
    
    public static final Color TERMINUS_SPIRIT_COLOR = new Color(6, 57, 49);
    public static MalumSpiritType TERMINUS_SPIRIT;
    
    public static final Color ELDRITCH_SPIRIT_COLOR = new Color(19, 13, 17);
    public static MalumSpiritType ELDRITCH_SPIRIT;
    
    public static void init()
    {
        WILD_SPIRIT = create("wild", MalumSpiritTypes::isWild, MalumSpiritTypes::howWild, WILD_SPIRIT_COLOR, MalumItems.WILD_SPIRIT_SPLINTER);
        UNDEAD_SPIRIT = create("undead", MalumSpiritTypes::isUndead, MalumSpiritTypes::howUndead, UNDEAD_SPIRIT_COLOR, MalumItems.UNDEAD_SPIRIT_SPLINTER);
        NIMBLE_SPIRIT = create("nimble", MalumSpiritTypes::isNimble, MalumSpiritTypes::howNimble, NIMBLE_SPIRIT_COLOR, MalumItems.NIMBLE_SPIRIT_SPLINTER);
        AQUATIC_SPIRIT = create("aquatic", MalumSpiritTypes::isAquatic, MalumSpiritTypes::howAquatic, AQUATIC_SPIRIT_COLOR, MalumItems.AQUATIC_SPIRIT_SPLINTER);
        SINISTER_SPIRIT = create("sinister", MalumSpiritTypes::isSinister, MalumSpiritTypes::howSinister, SINISTER_SPIRIT_COLOR, MalumItems.SINISTER_SPIRIT_SPLINTER);
        ARCANE_SPIRIT = create("arcane", MalumSpiritTypes::isArcane, MalumSpiritTypes::howArcane, ARCANE_SPIRIT_COLOR, MalumItems.ARCANE_SPIRIT_SPLINTER);
        SULPHURIC_SPIRIT = create("sulphuric", MalumSpiritTypes::isSulphuric, MalumSpiritTypes::howSulphuric, SULPHURIC_SPIRIT_COLOR, MalumItems.SULPHURIC_SPIRIT_SPLINTER);
        NETHERBORNE_SPIRIT = create("netherborne", MalumSpiritTypes::isNetherborne, MalumSpiritTypes::howNetherborne, NETHERBORNE_SPIRIT_COLOR, MalumItems.NETHERBORNE_SPIRIT_SPLINTER);
        AVARICIOUS_SPIRIT = create("avaricious", MalumSpiritTypes::isAvaricious, MalumSpiritTypes::howAvaricious, AVARICIOUS_SPIRIT_COLOR, MalumItems.AVARICIOUS_SPIRIT_SPLINTER);
        FUSIBLE_SPIRIT = create("fusible", MalumSpiritTypes::isFusible, MalumSpiritTypes::howFusible, FUSIBLE_SPIRIT_COLOR, MalumItems.FUSIBLE_SPIRIT_SPLINTER);
        CHARRED_SPIRIT = create("charred", MalumSpiritTypes::isCharred, MalumSpiritTypes::howCharred, CHARRED_SPIRIT_COLOR, MalumItems.CHARRED_SPIRIT_SPLINTER);
        TERMINUS_SPIRIT = create("terminus", MalumSpiritTypes::isTerminus, MalumSpiritTypes::howTerminus, TERMINUS_SPIRIT_COLOR, MalumItems.TERMINUS_SPIRIT_SPLINTER);
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
    
    public static boolean isUndead(LivingEntity entity) //dead.
    {
        return entity.isEntityUndead();
    }
    
    public static int howUndead(LivingEntity entity) //how menacing.
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
    
    public static boolean isNimble(LivingEntity entity) //capable of flying or fast
    {
        return entity instanceof AbstractHorseEntity || entity instanceof SpiderEntity || entity instanceof FlyingEntity || entity instanceof VexEntity || entity instanceof ParrotEntity || entity instanceof BatEntity || entity instanceof CatEntity || entity instanceof FoxEntity || entity instanceof OcelotEntity || entity instanceof RabbitEntity || entity instanceof StriderEntity || entity instanceof EndermanEntity || entity instanceof SilverfishEntity;
    }
    
    public static int howNimble(LivingEntity entity) //zoom potential.
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
        if (entity.isImmuneToFire())
        {
            return true;
        }
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
    
    public static boolean isCharred(LivingEntity entity) //wither skeletons
    {
        return entity instanceof WitherSkeletonEntity || entity instanceof WitherEntity;
    }
    
    public static int howCharred(LivingEntity entity) //oven.
    {
        if (entity instanceof WitherEntity)
        {
            return 10;
        }
        if (entity instanceof WitherSkeletonEntity)
        {
            return 2;
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
}
