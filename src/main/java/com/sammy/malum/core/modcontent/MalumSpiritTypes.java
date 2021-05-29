package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
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
    public static MalumSpiritType SACRED_SPIRIT = create("sacred", SACRED_SPIRIT_COLOR, MalumItems.SACRED_SPIRIT)
            .addTest(0, "rat")
            .addTest(1, AnimalEntity.class)
            .addTest(2, e -> (e instanceof AnimalEntity && e.getMaxHealth() > 4))
            .addTest(2, AbstractVillagerEntity.class, PiglinEntity.class, HoglinEntity.class)
            .addTest(3, e -> !e.isEntityUndead() && e instanceof TameableEntity);

    public static final Color WICKED_SPIRIT_COLOR = new Color(178, 29, 232);
    public static MalumSpiritType WICKED_SPIRIT = create("wicked", WICKED_SPIRIT_COLOR, MalumItems.WICKED_SPIRIT)
            .addTest(1, "rat")
            .addTest(1, CreatureAttribute.UNDEAD)
            .addTest(1, CreatureAttribute.ILLAGER)
            .addTest(2, ZombifiedPiglinEntity.class, ZoglinEntity.class)
            .addTest(3, e -> e.isEntityUndead() && e.getMaxHealth() > 20)
            .addTest(3, e -> (e instanceof AbstractHorseEntity && e.isEntityUndead()) || e instanceof SpellcastingIllagerEntity)
            .addTest(5, RavagerEntity.class, WitherEntity.class)
            .addTest(5, "rat_king");

    public static final Color ARCANE_SPIRIT_COLOR = new Color(231, 68, 196);
    public static  MalumSpiritType ARCANE_SPIRIT = create("arcane", ARCANE_SPIRIT_COLOR, MalumItems.ARCANE_SPIRIT)
            .addTest(1, CreatureAttribute.ILLAGER)
            .addTest(1, AbstractSkeletonEntity.class, MooshroomEntity.class, SlimeEntity.class, VexEntity.class, BlazeEntity.class)
            .addTest(2, "wraith")
            .addTest(2, SkeletonHorseEntity.class)
            .addTest(3, PhantomEntity.class, SpellcastingIllagerEntity.class, WitchEntity.class, WanderingTraderEntity.class, GhastEntity.class);

    public static final Color ELDRITCH_SPIRIT_COLOR = new Color(142, 62, 234, 255);
    public static MalumSpiritType ELDRITCH_SPIRIT = create("eldritch", ELDRITCH_SPIRIT_COLOR, MalumItems.ELDRITCH_SPIRIT)
            .addTest(1, EndermiteEntity.class, EndermanEntity.class, ShulkerEntity.class)
            .addTest(5, EnderDragonEntity.class);

    public static final Color AERIAL_SPIRIT_COLOR = new Color(51, 229, 155);
    public static  MalumSpiritType AERIAL_SPIRIT = create("aerial", AERIAL_SPIRIT_COLOR, MalumItems.AERIAL_SPIRIT)
            .addTest(1, "wraith")
            .addTest(1, SpiderEntity.class, true)
            .addTest(2, IFlyingAnimal.class, BatEntity.class, SilverfishEntity.class, OcelotEntity.class, CatEntity.class, RavagerEntity.class, FoxEntity.class)
            .addTest(3, AbstractHorseEntity.class, String.class, VexEntity.class, GhastEntity.class);

    public static final Color AQUATIC_SPIRIT_COLOR = new Color(42, 114, 232);
    public static  MalumSpiritType AQUATIC_SPIRIT = create("aquatic", AQUATIC_SPIRIT_COLOR, MalumItems.AQUATIC_SPIRIT)
            .addTest(1, WATER)
            .addTest(2, WaterMobEntity.class, DrownedEntity.class)
            .addTest(2, e -> e.getCreatureAttribute().equals(WATER) && e.getMaxHealth() >= 10)
            .addTest(3, GuardianEntity.class)
            .addTest(5, ElderGuardianEntity.class);

    public static final Color INFERNAL_SPIRIT_COLOR = new Color(210, 134, 39);
    public static  MalumSpiritType INFERNAL_SPIRIT = create("infernal", INFERNAL_SPIRIT_COLOR, MalumItems.INFERNAL_SPIRIT)
            .addTest(1, WitherSkeletonEntity.class, HuskEntity.class, MagmaCubeEntity.class, AbstractPiglinEntity.class, ZombifiedPiglinEntity.class, HoglinEntity.class, ZoglinEntity.class)
            .addTest(2, CreeperEntity.class, BlazeEntity.class, GhastEntity.class)
            .addTest(3, GhastEntity.class, StriderEntity.class)
            .addTest(5, WitherEntity.class);

    public static final Color EARTHEN_SPIRIT_COLOR = new Color(98, 180, 40);
    public static  MalumSpiritType EARTHEN_SPIRIT = create("earthen", EARTHEN_SPIRIT_COLOR, MalumItems.EARTHEN_SPIRIT)
            .addTest(1, ZombieEntity.class, CreeperEntity.class, IForgeShearable.class, CowEntity.class)
            .addTest(2, HorseEntity.class, ZombieHorseEntity.class, IronGolemEntity.class)
            .addTest(3, "zombie_brute");


    public static MalumSpiritType create(String identifier, Color color, RegistryObject<Item> splinterItem)
    {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, color, splinterItem);
        SPIRITS.add(spiritType);
        return spiritType;
    }
}
