package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entity.MalumBoatEntity;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.entity.spirit.PlayerHomingItemEntity;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MalumMod.MODID);
    
    public static final RegistryObject<EntityType<PlayerHomingItemEntity>> PLAYER_HOMING_ITEM = ENTITY_TYPES.register("player_homing_item",
            () -> EntityType.Builder.<PlayerHomingItemEntity>create((e,w)->new PlayerHomingItemEntity(w), EntityClassification.MISC).size(0.5F, 0.75F).trackingRange(10)
                    .build(MalumHelper.prefix("spirit_essence").toString()));
    
    public static final RegistryObject<EntityType<ScytheBoomerangEntity>> SCYTHE_BOOMERANG = ENTITY_TYPES.register("scythe_boomerang",
            () -> EntityType.Builder.<ScytheBoomerangEntity>create((e,w)->new ScytheBoomerangEntity(w), EntityClassification.MISC).size(2.5F, 0.75F).trackingRange(10)
                    .build(MalumHelper.prefix("scythe_boomerang").toString()));

    public static final RegistryObject<EntityType<MalumBoatEntity>> RUNEWOOD_BOAT = ENTITY_TYPES.register("runewood_boat",
            () -> EntityType.Builder.<MalumBoatEntity>create((t, w)->new MalumBoatEntity(t, w, MalumItems.RUNEWOOD_BOAT, MalumItems.RUNEWOOD_PLANKS), EntityClassification.MISC).size(1.375F, 0.5625F).trackingRange(10)
                    .build(MalumHelper.prefix("runewood_boat").toString()));

    public static final RegistryObject<EntityType<MalumBoatEntity>> SOULWOOD_BOAT = ENTITY_TYPES.register("soulwood_boat",
            () -> EntityType.Builder.<MalumBoatEntity>create((t, w)->new MalumBoatEntity(t, w, MalumItems.SOULWOOD_BOAT, MalumItems.SOULWOOD_PLANKS), EntityClassification.MISC).size(1.375F, 0.5625F).trackingRange(10)
                    .build(MalumHelper.prefix("soulwood_boat").toString()));
}
