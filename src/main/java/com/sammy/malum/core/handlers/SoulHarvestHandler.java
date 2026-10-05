package com.sammy.malum.core.handlers;

import com.mojang.serialization.*;
import com.sammy.malum.common.data.attachment.*;
import com.sammy.malum.common.data.listener.reaping.ReapingDropsData;
import com.sammy.malum.common.data.listener.spirit.EntitySpiritDropData;
import com.sammy.malum.common.data.listener.reaping.ReapingDataReloadListener;
import com.sammy.malum.common.entity.spirit.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.registries.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.Easing;

import javax.annotation.*;
import java.util.*;

public class SoulHarvestHandler {
    public static final Codec<SoulHarvestHandler> CODEC = Codec.unit(SoulHarvestHandler::new);

    public static void onDeath(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        var target = event.getEntity();
        if (target instanceof Player) {
            return;
        }
        var data = target.getData(MalumAttachmentTypes.LIVING_SOUL_INFO);
        if ((CommonConfig.SOULLESS_SPAWNERS.getConfigValue() && data.isSpawnerSpawned())) {
            return;
        }
        var source = event.getSource();
        var level = target.level();
        var attacker = source.getEntity() instanceof LivingEntity living ? living : target.getLastHurtByMob();
        if (data.shouldDropSpirits()) {
            dropSpiritInfusedDrops(target);
            dropEncyclopediaArcana(target, attacker);
            dropSpirits(target, attacker);
            if (attacker != null) {
                attacker.getData(MalumAttachmentTypes.LIVING_SOUL_INFO).setMostRecentShatter(level.getGameTime());
            }
            data.setSoulless(true);
        }
    }

    public static void dropSpiritInfusedDrops(LivingEntity target) {
        List<ReapingDropsData> data = ReapingDataReloadListener.REAPING_DATA.get(BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()));
        if (data != null) {
            for (ReapingDropsData dropData : data) {
                Level level = target.level();
                var random = level.random;
                if (random.nextFloat() < dropData.chance()) {
                    var possibleDrops = dropData.drop().getItems();
                    var stack = ItemHelper.copyWithNewCount(possibleDrops[random.nextInt(possibleDrops.length)], Mth.nextInt(random, dropData.min(), dropData.max()));
                    var itemEntity = new ItemEntity(level, target.getX(), target.getY(), target.getZ(), stack);
                    itemEntity.setDefaultPickUpDelay();
                    itemEntity.setDeltaMovement(Mth.nextFloat(random, -0.1F, 0.1F), Mth.nextFloat(random, 0.25f, 0.5f), Mth.nextFloat(random, -0.1F, 0.1F));
                    level.addFreshEntity(itemEntity);
                }
            }
        }
    }

    public static void dropEncyclopediaArcana(LivingEntity target, LivingEntity attacker) {
        if (!CommonConfig.AWARD_CODEX_ON_KILL.getConfigValue()) {
            return;
        }
        if (target.getType().is(EntityTypeTags.UNDEAD) && attacker instanceof Player player) {
            var data = player.getData(MalumAttachmentTypes.PROGRESSION_DATA);
            if (data.obtainedEncyclopedia) {
                return;
            }
            data.obtainedEncyclopedia = true;
            spawnSpirits(target)
                    .setCustomItems(MalumContent.ENCYCLOPEDIA_ARCANA.get())
                    .setPreferredCollector(attacker)
                    .spawnSpirits(attacker.level());
        }
    }

    public static void dropSpirits(LivingEntity target, LivingEntity attacker) {
        var level = target.level();
        SpiritSpawner spiritSpawner = spawnSpirits(target).setPreferredCollector(attacker);

        var itemAsSoul = EntitySpiritDropData.getSpiritData(target).map(EntitySpiritDropData::getItemAsSoul).orElse(null);
        if (itemAsSoul != null) {
            var uuid = attacker != null ? attacker.getUUID() : null;
            target.setData(MalumAttachmentTypes.CACHED_SPIRIT_DROPS, new CachedSpiritDropsData(spiritSpawner.getSpiritDrops(), uuid));
            return;
        }
        spiritSpawner.spawnSpirits(level);
    }

    public static SpiritSpawner spawnSpirits(Entity target) {
        return new SpiritSpawner(target);
    }

    public static class SpiritSpawner {

        private final Entity target;
        @Nullable
        private LivingEntity preferredCollector;
        private List<ItemStack> customItems = Collections.emptyList();

        public SpiritSpawner(Entity target) {
            this.target = target;
        }

        public SpiritSpawner setPreferredCollector(@Nullable LivingEntity preferredCollector) {
            this.preferredCollector = preferredCollector;
            return this;
        }

        public SpiritSpawner setCustomItems(Item... customItems) {
            return setCustomItems(Arrays.stream(customItems).map(Item::getDefaultInstance).toList());
        }

        public SpiritSpawner setCustomItems(ItemStack... customItems) {
            return setCustomItems(List.of(customItems));
        }

        public SpiritSpawner setCustomItems(List<ItemStack> customItems) {
            this.customItems = customItems;
            return this;
        }

        public Vec3 getSpawnPosition() {
            return target.position().add(0, target.getBbHeight() / 2f, 0);
        }

        public List<ItemStack> getSpiritDrops() {
            if (!customItems.isEmpty()) {
                return customItems;
            }
            if (!(target instanceof LivingEntity living)) {
                return Collections.emptyList();
            }
            var optional = EntitySpiritDropData.getSpiritData(living);
            if (optional.isEmpty()) {
                return Collections.emptyList();
            }
            EntitySpiritDropData data = optional.get();
            if (preferredCollector == null) {
                return data.getSpiritStacks();
            }
            return applySpiritLootBonuses(data, living, preferredCollector);
        }

        public void spawnSpirits(Level level) {
            var position = getSpawnPosition();
            var spirits = getSpiritDrops();
            for (ItemStack spirit : spirits) {
                if (spirit.isEmpty()) {
                    continue;
                }
                for (int j = 0; j < spirit.getCount(); j++) {
                    var stack = spirit.copyWithCount(1);
                    Entity entity = createSpiritEntity(stack, position);
                    level.addFreshEntity(entity);
                }
            }
            float pitch = Easing.SINE_IN_OUT.asWeighedRandom(level.random, 0.7f, 1.3f);
            level.playSound(null, position.x, position.y, position.z, MalumSoundEvents.SOUL_SHATTER, SoundSource.PLAYERS, 1.0F, pitch);
        }

        public Entity createSpiritEntity(ItemStack stack, Vec3 position) {
            var level = target.level();
            var random = level.getRandom();
            float speed = Easing.SINE_IN_OUT.asWeighedRandom(random, 0.2f, 0.4f);
            float xSpeed = Easing.QUAD_IN_OUT.asWeighedRandom(random, -speed, speed);
            float ySpeed = Easing.QUAD_IN_OUT.asWeighedRandom(random, 0.05f, 0.06f);
            float zSpeed = Easing.QUAD_IN_OUT.asWeighedRandom(random, -speed, speed);
            var velocity = new Vec3(xSpeed, ySpeed, zSpeed);
            if (CommonConfig.NO_FANCY_SPIRITS.getConfigValue()) {
                var itemEntity = new ItemEntity(level, position.x, position.y, position.z, stack);
                itemEntity.setDefaultPickUpDelay();
                itemEntity.setDeltaMovement(xSpeed * 0.6f, ySpeed * 6f, zSpeed * 0.6f);
                return itemEntity;
            }
            return new SpiritItemEntity(level, preferredCollector, stack, position, velocity);
        }
    }

    public static List<ItemStack> applySpiritLootBonuses(EntitySpiritDropData data, LivingEntity target, LivingEntity attacker) {
        List<ItemStack> spirits = new ArrayList<>(data.getSpiritStacks());
        if (spirits.isEmpty()) {
            return spirits;
        }
        var random = attacker.getRandom();
        int bonus = Mth.floor(attacker.getAttributeValue(MalumAttributes.SPIRIT_SPOILS));
        var event = new ModifySpiritSpoilsEvent(target, attacker, bonus);
        ItemEventHandler.getEventResponders(attacker)
                .forEach(lookup -> lookup.run(IMalumEventResponder.class,
                        (eventResponderItem, stack) -> eventResponderItem.modifySpiritSpoilsEvent(event, attacker)));
        NeoForge.EVENT_BUS.post(event);
        bonus = event.getNewSpiritBonus();
        for (int i = 0; i < bonus; i++) {
            int index = random.nextInt(spirits.size());
            spirits.get(index).grow(1);
        }
        return spirits;
    }

    public static void pickupSpirit(LivingEntity collector, ItemStack stack) {
        SoulHarvestHandler.triggerSpiritCollection(collector);
        ItemEntity entity = new ItemEntity(collector.level(), collector.getX(), collector.getY() + 0.5, collector.getZ(), stack);
        entity.setPickUpDelay(0);
        if (collector instanceof Player player) {
            var result = EventHooks.fireItemPickupPre(entity, player).canPickup();
            if (result.isFalse()) {
                return;
            }
            player.addItem(stack);
            player.onItemPickup(entity);
            if (!stack.isEmpty()) {
                ItemHelper.spawnItemOnEntity(collector, stack);
            }
        } else {
            collector.level().addFreshEntity(entity);
        }
    }

    public static void triggerSpiritCollection(LivingEntity collector) {
        var collectionEvent = new CollectSpiritEvent(collector);
        var resonance = collector.getAttributeValue(MalumAttributes.ARCANE_RESONANCE);
        ItemEventHandler.getEventResponders(collector).forEach(lookup -> lookup.run(IMalumEventResponder.class,
                (eventResponderItem, stack) -> eventResponderItem.spiritCollectionEvent(collectionEvent, collector, resonance)));
        NeoForge.EVENT_BUS.post(collectionEvent);
    }
}