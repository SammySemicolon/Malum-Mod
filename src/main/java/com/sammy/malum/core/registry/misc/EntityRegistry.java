package com.sammy.malum.core.registry.misc;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.entity_renderer.FloatingItemEntityRenderer;
import com.sammy.malum.client.entity_renderer.MalumBoatRenderer;
import com.sammy.malum.client.entity_renderer.ScytheBoomerangEntityRenderer;
import com.sammy.malum.common.entity.MalumBoatEntity;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.entity.spirit.PlayerHomingItemEntity;
import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MalumMod.MODID);
    
    public static final RegistryObject<EntityType<PlayerHomingItemEntity>> PLAYER_HOMING_ITEM = ENTITY_TYPES.register("player_homing_item",
            () -> EntityType.Builder.<PlayerHomingItemEntity>of((e,w)->new PlayerHomingItemEntity(w), MobCategory.MISC).sized(0.5F, 0.75F).clientTrackingRange(10)
                    .build(MalumHelper.prefix("spirit_essence").toString()));
    
    public static final RegistryObject<EntityType<ScytheBoomerangEntity>> SCYTHE_BOOMERANG = ENTITY_TYPES.register("scythe_boomerang",
            () -> EntityType.Builder.<ScytheBoomerangEntity>of((e,w)->new ScytheBoomerangEntity(w), MobCategory.MISC).sized(2.5F, 0.75F).clientTrackingRange(10)
                    .build(MalumHelper.prefix("scythe_boomerang").toString()));

    public static final RegistryObject<EntityType<MalumBoatEntity>> RUNEWOOD_BOAT = ENTITY_TYPES.register("runewood_boat",
            () -> EntityType.Builder.<MalumBoatEntity>of((t, w)->new MalumBoatEntity(t, w, ItemRegistry.RUNEWOOD_BOAT, ItemRegistry.RUNEWOOD_PLANKS), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(MalumHelper.prefix("runewood_boat").toString()));

    public static final RegistryObject<EntityType<MalumBoatEntity>> SOULWOOD_BOAT = ENTITY_TYPES.register("soulwood_boat",
            () -> EntityType.Builder.<MalumBoatEntity>of((t, w)->new MalumBoatEntity(t, w, ItemRegistry.SOULWOOD_BOAT, ItemRegistry.SOULWOOD_PLANKS), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(MalumHelper.prefix("soulwood_boat").toString()));

    @Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void bindEntityRenderers(FMLClientSetupEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.PLAYER_HOMING_ITEM.get(), (manager) -> new FloatingItemEntityRenderer(manager, Minecraft.getInstance().getItemRenderer()));
            RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.SCYTHE_BOOMERANG.get(), (manager) -> new ScytheBoomerangEntityRenderer(manager, Minecraft.getInstance().getItemRenderer()));
            RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.RUNEWOOD_BOAT.get(), (manager) -> new MalumBoatRenderer(manager, "runewood"));
            RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.SOULWOOD_BOAT.get(), (manager) -> new MalumBoatRenderer(manager, "soulwood"));
        }
    }
}
