package com.sammy.malum.core.init.spirits;

import com.sammy.malum.core.systems.spirits.types.MalumSpiritType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Predicate;

public class MalumSpiritTypes
{
    public static ArrayList<MalumSpiritType> SPIRITS = new ArrayList<>();
    
    public static final Color WILD_SPIRIT_COLOR = new Color(118, 255, 21);
    public static final MalumSpiritType WILD_SPIRIT = create("wild",  MalumSpiritTypes::isWild,WILD_SPIRIT_COLOR);
    public static final Color UNDEAD_SPIRIT_COLOR = new Color(109, 23, 18);
    public static final MalumSpiritType UNDEAD_SPIRIT = create("undead", LivingEntity::isEntityUndead, UNDEAD_SPIRIT_COLOR);
    public static final Color NIMBLE_SPIRIT_COLOR = new Color(173, 255, 243);
    public static final MalumSpiritType NIMBLE_SPIRIT = create("nimble",  MalumSpiritTypes::isNimble, NIMBLE_SPIRIT_COLOR);
    public static final Color AQUATIC_SPIRIT_COLOR = new Color(27, 163, 255);
    public static final MalumSpiritType AQUATIC_SPIRIT = create("aquatic",  e -> e.getCreatureAttribute().equals(CreatureAttribute.WATER), AQUATIC_SPIRIT_COLOR);
    public static final Color SINISTER_SPIRIT_COLOR = new Color(142, 0, 135);
    public static final MalumSpiritType SINISTER_SPIRIT = create("sinister",  MalumSpiritTypes::isSinister, SINISTER_SPIRIT_COLOR);
    public static final Color ARCANE_SPIRIT_COLOR = new Color(224, 88, 205);
    public static final MalumSpiritType ARCANE_SPIRIT = create("arcane",  MalumSpiritTypes::isArcane, ARCANE_SPIRIT_COLOR);
    public static final Color SULPHURIC_SPIRIT_COLOR = new Color(213, 196, 79);
    public static final MalumSpiritType SULPHURIC_SPIRIT = create("sulphuric", MalumSpiritTypes::isSulphuric,SULPHURIC_SPIRIT_COLOR);
    public static final Color NETHERBORNE_SPIRIT_COLOR = new Color(163, 13, 10);
    public static final MalumSpiritType NETHERBORNE_SPIRIT = create("netherborne",  MalumSpiritTypes::isNetherborne,NETHERBORNE_SPIRIT_COLOR);
    public static final Color REMEDIAL_SPIRIT_COLOR = new Color(245, 255, 110);
    public static final MalumSpiritType REMEDIAL_SPIRIT = create("remedial",  MalumSpiritTypes::isRemedial, REMEDIAL_SPIRIT_COLOR);
    public static final Color FUSIBLE_SPIRIT_COLOR = new Color(147, 144, 141);
    public static final MalumSpiritType FUSIBLE_SPIRIT = create("fusible",  MalumSpiritTypes::isFusible, FUSIBLE_SPIRIT_COLOR);
    
    public static final Color TERMINUS_SPIRIT_COLOR = new Color(69, 5, 116);
    public static final MalumSpiritType TERMINUS_SPIRIT = create("terminus", MalumSpiritTypes::isTerminus, TERMINUS_SPIRIT_COLOR);
    public static final Color ELDRITCH_SPIRIT_COLOR = new Color(30, 16, 38);
    public static final MalumSpiritType ELDRITCH_SPIRIT = create("eldritch", MalumSpiritTypes::isEldritch, ELDRITCH_SPIRIT_COLOR);
    
    public static MalumSpiritType create(String identifier, Predicate<LivingEntity> predicate, Color color)
    {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, predicate, color, identifier + "_spirit_splinter");
        SPIRITS.add(spiritType);
        return spiritType;
    }
    
    public static boolean isWild(LivingEntity entity) //animal.
    {
        return entity instanceof AnimalEntity || entity instanceof SquidEntity;
    }
    
    public static boolean isNimble(LivingEntity entity) //capable of flying or fast
    {
        return entity instanceof FlyingEntity || entity instanceof VexEntity || entity instanceof ParrotEntity || entity instanceof BatEntity || entity instanceof CatEntity || entity instanceof FoxEntity || entity instanceof OcelotEntity || entity instanceof RabbitEntity || entity instanceof StriderEntity || entity instanceof EndermanEntity || entity instanceof SilverfishEntity;
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
    
    public static boolean isArcane(LivingEntity entity) //made or capable of using magic, or a phenomenon
    {
        if (entity.getType().getRegistryName().getPath().equals("wraith"))
        {
            return true;
        }
        return entity instanceof SpellcastingIllagerEntity || entity instanceof VexEntity || entity instanceof WanderingTraderEntity || entity instanceof WitchEntity || entity instanceof PhantomEntity || entity instanceof GhastEntity || entity instanceof MooshroomEntity;
    }
    
    public static boolean isSulphuric(LivingEntity entity) //immune to explosions, filled with sulphur or explosive
    {
        if (entity.isImmuneToExplosions())
        {
            return true;
        }
        return entity instanceof CreeperEntity || entity instanceof BlazeEntity;
    }
    
    public static boolean isNetherborne(LivingEntity entity) //immune to fire, or born in the nether
    {
        return entity instanceof ZoglinEntity || entity instanceof GhastEntity || entity instanceof HoglinEntity || entity instanceof AbstractPiglinEntity || entity instanceof MagmaCubeEntity || entity instanceof StriderEntity;
    }
    
    public static boolean isRemedial(LivingEntity entity) //creating or created
    {
        if (entity.getCreatureAttribute().equals(CreatureAttribute.ARTHROPOD))
        {
            return true;
        }
        return entity instanceof AbstractVillagerEntity || entity instanceof GhastEntity || entity instanceof SheepEntity || entity instanceof AbstractPiglinEntity || entity instanceof ChickenEntity || entity instanceof CowEntity || entity instanceof BeeEntity || entity instanceof SlimeEntity || entity instanceof GolemEntity;
    }
    
    public static boolean isFusible(LivingEntity entity) //metal, iron stuff
    {
        if (entity.getType().getRegistryName().getPath().equals("zombie_brute"))
        {
            return true;
        }
        return entity instanceof IronGolemEntity || entity instanceof ZombieEntity || entity instanceof ZombieHorseEntity || entity instanceof AbstractPiglinEntity;
    }
    
    public static boolean isTerminus(LivingEntity entity) //end
    {
        return entity instanceof EndermanEntity || entity instanceof ShulkerEntity || entity instanceof EndermiteEntity;
    }
    
    public static boolean isEldritch(LivingEntity entity) //bosses
    {
        return !entity.isNonBoss();
    }
    public static void init() {}
}