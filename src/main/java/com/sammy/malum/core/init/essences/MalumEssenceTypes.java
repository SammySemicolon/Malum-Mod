package com.sammy.malum.core.init.essences;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.essences.SimpleEssenceType;
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
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.function.Predicate;

import static com.sammy.malum.core.systems.essences.SimpleEssenceType.essenceTypeEnum.essence;
import static com.sammy.malum.core.systems.essences.SimpleEssenceType.essenceTypeEnum.spirit;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MalumEssenceTypes
{
    public static ArrayList<SimpleEssenceType> SPIRITS = new ArrayList<>();
    public static ArrayList<SimpleEssenceType> ESSENCES = new ArrayList<>();
    public static final SimpleEssenceType WILD_ESSENCE = create("wild", "", e -> e instanceof AnimalEntity);
    public static final SimpleEssenceType UNDEAD_ESSENCE = create("undead", "", LivingEntity::isEntityUndead);
    public static final SimpleEssenceType NIMBLE_ESSENCE = create("nimble", "", MalumEssenceTypes::isNimble);
    public static final SimpleEssenceType AQUATIC_ESSENCE = create("aquatic", "", e -> e.getCreatureAttribute().equals(CreatureAttribute.WATER));
    public static final SimpleEssenceType SINISTER_ESSENCE = create("sinister", "", MalumEssenceTypes::isSinister);
    public static final SimpleEssenceType ARCANE_ESSENCE = create("arcane", "", MalumEssenceTypes::isArcane);
    public static final SimpleEssenceType SULPHURIC_ESSENCE = create("sulphuric", "", MalumEssenceTypes::isSulphuric);
    public static final SimpleEssenceType NETHERBORNE_ESSENCE = create("netherborne", "", MalumEssenceTypes::isNetherborne);
    public static final SimpleEssenceType BENEVOLENT_ESSENCE = create("benevolent", "", MalumEssenceTypes::isBenevolent);
    public static final SimpleEssenceType REMEDIAL_ESSENCE = create("remedial", "", MalumEssenceTypes::isRemedial);
    public static final SimpleEssenceType TERMINUS_ESSENCE = create("terminus", "", MalumEssenceTypes::isTerminus);
    public static final SimpleEssenceType ELDRITCH_ESSENCE = create("eldritch", "", MalumEssenceTypes::isEldritch);
    @SubscribeEvent
    public static void spirits(FMLCommonSetupEvent event)
    {
        for (EntityType<?> type : ForgeRegistries.ENTITIES)
        {
            if (!type.getClassification().equals(EntityClassification.MISC) || type.equals(EntityType.VILLAGER) || type.equals(EntityType.SNOW_GOLEM) || type.equals(EntityType.IRON_GOLEM))
            {
                SPIRITS.add(create(type.getRegistryName().getPath()));
            }
        }
        for (SimpleEssenceType spirit : SPIRITS)
        {
            MalumMod.LOGGER.info(spirit.identifier);
        }
    }
    public static SimpleEssenceType create(String identifier, String description, Predicate<LivingEntity> predicate)
    {
        SimpleEssenceType essenceType = new SimpleEssenceType(MalumMod.MODID + ":" + identifier + "_essence", description, predicate, essence);
        ESSENCES.add(essenceType);
        return essenceType;
    }
    public static SimpleEssenceType create(String identifier)
    {
        SimpleEssenceType essenceType = new SimpleEssenceType(MalumMod.MODID + ":" + identifier + "_spirit", null, null, spirit);
        ESSENCES.add(essenceType);
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
    
    public static boolean isBenevolent(LivingEntity entity) //giving
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
    
    public static boolean isTerminus(LivingEntity entity) //creating or created
    {
        return entity instanceof EndermanEntity || entity instanceof ShulkerEntity || entity instanceof EndermiteEntity;
    }
    
    public static boolean isEldritch(LivingEntity entity) //bosses
    {
        return !entity.isNonBoss();
    }
}