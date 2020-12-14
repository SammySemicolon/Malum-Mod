package com.sammy.malum.core.init.essences;

import com.sammy.malum.core.systems.spirits.types.MalumSpiritType;
import net.minecraft.entity.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.passive.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.function.Predicate;

public class MalumSpiritTypes
{
    public static ArrayList<MalumSpiritType> SPIRITS = new ArrayList<>();
    public static final MalumSpiritType WILD_ESSENCE = create("wild",  e -> e instanceof AnimalEntity);
    public static final MalumSpiritType UNDEAD_ESSENCE = create("undead", LivingEntity::isEntityUndead);
    public static final MalumSpiritType NIMBLE_ESSENCE = create("nimble",  MalumSpiritTypes::isNimble);
    public static final MalumSpiritType AQUATIC_ESSENCE = create("aquatic",  e -> e.getCreatureAttribute().equals(CreatureAttribute.WATER));
    public static final MalumSpiritType SINISTER_ESSENCE = create("sinister",  MalumSpiritTypes::isSinister);
    public static final MalumSpiritType ARCANE_ESSENCE = create("arcane",  MalumSpiritTypes::isArcane);
    public static final MalumSpiritType SULPHURIC_ESSENCE = create("sulphuric", MalumSpiritTypes::isSulphuric);
    public static final MalumSpiritType NETHERBORNE_ESSENCE = create("netherborne",  MalumSpiritTypes::isNetherborne);
    public static final MalumSpiritType BENEVOLENT_ESSENCE = create("benevolent",  MalumSpiritTypes::isBenevolent);
    public static final MalumSpiritType REMEDIAL_ESSENCE = create("remedial",  MalumSpiritTypes::isRemedial);
    public static final MalumSpiritType TERMINUS_ESSENCE = create("terminus", MalumSpiritTypes::isTerminus);
    public static final MalumSpiritType ELDRITCH_ESSENCE = create("eldritch", MalumSpiritTypes::isEldritch);
    
    public static MalumSpiritType create(String identifier, Predicate<LivingEntity> predicate)
    {
        MalumSpiritType essenceType = new MalumSpiritType(identifier, predicate);
        SPIRITS.add(essenceType);
        return essenceType;
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
    
    public static boolean isBenevolent(LivingEntity entity) //good
    {
        return entity instanceof AbstractVillagerEntity || entity instanceof GolemEntity;
    }
    
    public static boolean isRemedial(LivingEntity entity) //creating or created
    {
        if (entity.getCreatureAttribute().equals(CreatureAttribute.ARTHROPOD))
        {
            return true;
        }
        return entity instanceof GhastEntity || entity instanceof SheepEntity || entity instanceof BeeEntity || entity instanceof SlimeEntity || entity instanceof GolemEntity;
    }
    
    public static boolean isTerminus(LivingEntity entity) //end
    {
        return entity instanceof EndermanEntity || entity instanceof ShulkerEntity || entity instanceof EndermiteEntity;
    }
    
    public static boolean isEldritch(LivingEntity entity) //bosses
    {
        return !entity.isNonBoss();
    }
}