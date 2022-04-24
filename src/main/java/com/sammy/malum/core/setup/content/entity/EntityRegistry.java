package com.sammy.malum.core.setup.content.entity;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.renderer.entity.FloatingItemEntityRenderer;
import com.sammy.malum.client.renderer.entity.MalumBoatRenderer;
import com.sammy.malum.client.renderer.entity.ScytheBoomerangEntityRenderer;
import com.sammy.malum.client.renderer.entity.SoulEntityRenderer;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.entity.spirit.MirrorItemEntity;
import com.sammy.malum.common.entity.spirit.SpiritItemEntity;
import com.sammy.malum.common.entity.spirit.SoulEntity;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.ortus.entity.OrtusBoatEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MalumMod.MALUM);

    public static final RegistryObject<EntityType<SpiritItemEntity>> NATURAL_SPIRIT = ENTITY_TYPES.register("natural_spirit",
            () -> EntityType.Builder.<SpiritItemEntity>of((e, w)->new SpiritItemEntity(w), MobCategory.MISC).sized(0.5F, 0.75F).clientTrackingRange(10)
                    .build(MalumMod.prefix("natural_spirit").toString()));

    public static final RegistryObject<EntityType<MirrorItemEntity>> MIRROR_ITEM = ENTITY_TYPES.register("mirror_item",
            () -> EntityType.Builder.<MirrorItemEntity>of((e, w)->new MirrorItemEntity(w), MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10)
                    .build(MalumMod.prefix("mirror_item").toString()));

    public static final RegistryObject<EntityType<SoulEntity>> NATURAL_SOUL = ENTITY_TYPES.register("natural_soul",
            () -> EntityType.Builder.<SoulEntity>of((e, w)->new SoulEntity(w), MobCategory.MISC).sized(1.5F, 1.5F).clientTrackingRange(10)
                    .build(MalumMod.prefix("natural_soul").toString()));
    
    public static final RegistryObject<EntityType<ScytheBoomerangEntity>> SCYTHE_BOOMERANG = ENTITY_TYPES.register("scythe_boomerang",
            () -> EntityType.Builder.<ScytheBoomerangEntity>of((e,w)->new ScytheBoomerangEntity(w), MobCategory.MISC).sized(2.5F, 0.75F).clientTrackingRange(10)
                    .build(MalumMod.prefix("scythe_boomerang").toString()));

    public static final RegistryObject<EntityType<OrtusBoatEntity>> RUNEWOOD_BOAT = ENTITY_TYPES.register("runewood_boat",
            () -> EntityType.Builder.<OrtusBoatEntity>of((t, w)->new OrtusBoatEntity(t, w, ItemRegistry.RUNEWOOD_BOAT, ItemRegistry.RUNEWOOD_PLANKS), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(MalumMod.prefix("runewood_boat").toString()));

    public static final RegistryObject<EntityType<OrtusBoatEntity>> SOULWOOD_BOAT = ENTITY_TYPES.register("soulwood_boat",
            () -> EntityType.Builder.<OrtusBoatEntity>of((t, w)->new OrtusBoatEntity(t, w, ItemRegistry.SOULWOOD_BOAT, ItemRegistry.SOULWOOD_PLANKS), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(MalumMod.prefix("soulwood_boat").toString()));

    @Mod.EventBusSubscriber(modid= MalumMod.MALUM, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void bindEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            EntityRenderers.register(EntityRegistry.NATURAL_SPIRIT.get(), FloatingItemEntityRenderer::new);
            EntityRenderers.register(EntityRegistry.MIRROR_ITEM.get(), FloatingItemEntityRenderer::new);
            EntityRenderers.register(EntityRegistry.NATURAL_SOUL.get(), SoulEntityRenderer::new);
            EntityRenderers.register(EntityRegistry.SCYTHE_BOOMERANG.get(), ScytheBoomerangEntityRenderer::new);
            EntityRenderers.register(EntityRegistry.RUNEWOOD_BOAT.get(), (manager) -> new MalumBoatRenderer(manager, "runewood"));
            EntityRenderers.register(EntityRegistry.SOULWOOD_BOAT.get(), (manager) -> new MalumBoatRenderer(manager, "soulwood"));
        }
    }
}
