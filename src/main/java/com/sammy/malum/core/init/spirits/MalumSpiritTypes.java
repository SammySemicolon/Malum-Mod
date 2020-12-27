package com.sammy.malum.core.init.spirits;

import com.sammy.malum.core.systems.spirits.types.MalumSpiritType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.passive.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Predicate;

public class MalumSpiritTypes
{
    public static ArrayList<MalumSpiritType> SPIRITS = new ArrayList<>();
    
    public static final MalumSpiritType WILD_SPIRIT = create("wild",  e -> e instanceof AnimalEntity,165, 255, 40);
    public static final MalumSpiritType UNDEAD_SPIRIT = create("undead", LivingEntity::isEntityUndead, 101, 9, 18);
    public static final MalumSpiritType NIMBLE_SPIRIT = create("nimble",  MalumSpiritTypes::isNimble, 195, 213, 213);
    public static final MalumSpiritType AQUATIC_SPIRIT = create("aquatic",  e -> e.getCreatureAttribute().equals(CreatureAttribute.WATER), 85, 240, 255);
    public static final MalumSpiritType SINISTER_SPIRIT = create("sinister",  MalumSpiritTypes::isSinister, 133, 16, 161);
    public static final MalumSpiritType ARCANE_SPIRIT = create("arcane",  MalumSpiritTypes::isArcane, 255, 44, 176);
    public static final MalumSpiritType SULPHURIC_SPIRIT = create("sulphuric", MalumSpiritTypes::isSulphuric,255, 176, 44);
    public static final MalumSpiritType NETHERBORNE_SPIRIT = create("netherborne",  MalumSpiritTypes::isNetherborne,187, 51, 50);
    public static final MalumSpiritType REMEDIAL_SPIRIT = create("remedial",  MalumSpiritTypes::isRemedial, 220, 251, 237);
    public static final MalumSpiritType TERMINUS_SPIRIT = create("terminus", MalumSpiritTypes::isTerminus, 50, 17, 84);
    public static final MalumSpiritType ELDRITCH_SPIRIT = create("eldritch", MalumSpiritTypes::isEldritch, 35, 24, 47);
    
    public static MalumSpiritType create(String identifier, Predicate<LivingEntity> predicate, int r, int g, int b)
    {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, predicate, new Color(r,g,b), identifier + "_spirit_splinter");
        SPIRITS.add(spiritType);
        return spiritType;
    }
    
    public static boolean isNimble(LivingEntity entity) //capable of flying or fast
    {
        return entity instanceof FlyingEntity || entity instanceof VexEntity || entity instanceof ParrotEntity || entity instanceof BatEntity || entity instanceof CatEntity || entity instanceof FoxEntity || entity instanceof OcelotEntity || entity instanceof RabbitEntity || entity instanceof StriderEntity || entity instanceof EndermanEntity || entity instanceof SilverfishEntity;
    }
    
    public static boolean isSinister(LivingEntity entity) //illagers
    {
        if (entity.getCreatureAttribute().equals(CreatureAttribute.ILLAGER))
        {
            return true;
        }
        return entity instanceof RavagerEntity || entity instanceof VexEntity || entity instanceof WitchEntity;
    }
    
    public static boolean isArcane(LivingEntity entity) //made or capable of using magic, or a phenomenon
    {
        return entity instanceof VexEntity || entity instanceof WanderingTraderEntity || entity instanceof WitchEntity || entity instanceof PhantomEntity || entity instanceof GhastEntity || entity instanceof MooshroomEntity;
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
        if (entity.isImmuneToFire())
        {
            return true;
        }
        return entity instanceof ZoglinEntity || entity instanceof GhastEntity || entity instanceof HoglinEntity || entity instanceof AbstractPiglinEntity || entity instanceof MagmaCubeEntity || entity instanceof StriderEntity;
    }
    
    public static boolean isRemedial(LivingEntity entity) //creating or created
    {
        if (entity.getCreatureAttribute().equals(CreatureAttribute.ARTHROPOD))
        {
            return true;
        }
        return entity instanceof AbstractVillagerEntity || entity instanceof GhastEntity || entity instanceof SheepEntity || entity instanceof BeeEntity || entity instanceof SlimeEntity || entity instanceof GolemEntity;
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