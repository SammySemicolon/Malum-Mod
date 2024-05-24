package com.sammy.malum.common.components;

import com.sammy.malum.MalumMod;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class MalumComponents implements EntityComponentInitializer, WorldComponentInitializer {
    public static final ComponentKey<MalumPlayerDataComponent> MALUM_PLAYER_COMPONENT =
            ComponentRegistry.getOrCreate(MalumMod.malumPath("player"), MalumPlayerDataComponent.class);
    public static final ComponentKey<MalumLivingEntityDataComponent> MALUM_LIVING_ENTITY_COMPONENT =
            ComponentRegistry.getOrCreate(MalumMod.malumPath("living_entity"), MalumLivingEntityDataComponent.class);
    public static final ComponentKey<MalumItemDataComponent> MALUM_ITEM_COMPONENT =
            ComponentRegistry.getOrCreate(MalumMod.malumPath("item"), MalumItemDataComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(Player.class, MALUM_PLAYER_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(MalumPlayerDataComponent::new);
        registry.beginRegistration(LivingEntity.class, MALUM_LIVING_ENTITY_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(MalumLivingEntityDataComponent::new);
        registry.beginRegistration(ItemEntity.class, MALUM_ITEM_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(MalumItemDataComponent::new);
    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {

    }
}
