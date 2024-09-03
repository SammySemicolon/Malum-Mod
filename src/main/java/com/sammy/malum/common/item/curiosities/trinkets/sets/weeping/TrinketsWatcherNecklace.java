package com.sammy.malum.common.item.curiosities.trinkets.sets.weeping;

import com.sammy.malum.common.components.MalumComponents;
import com.sammy.malum.common.entity.activator.SpiritCollectionActivatorEntity;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;

import java.util.function.Consumer;

public class TrinketsWatcherNecklace extends MalumTinketsItem implements IMalumEventResponderItem, IVoidItem {
    public TrinketsWatcherNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("full_health_fake_collection"));
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (target.getHealth() >= target.getMaxHealth() * 0.9875f) {
            MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.maybeGet(target).ifPresent(c -> {
                if (c.watcherNecklaceCooldown == 0) {
                    float speed = 0.4f;
                    final Level level = attacker.level();
                    var random = level.getRandom();
                    Vec3 position = target.position().add(0, target.getBbHeight() / 2f, 0);
                    int amount = target instanceof Player ? 5 : 2;
                    for (int i = 0; i < amount; i++) {
                        SpiritCollectionActivatorEntity entity = new SpiritCollectionActivatorEntity(level, attacker.getUUID(),
                                position.x,
                                position.y,
                                position.z,
                                RandomHelper.randomBetween(random, -speed, speed),
                                RandomHelper.randomBetween(random, 0.05f, 0.06f),
                                RandomHelper.randomBetween(random, -speed, speed));
                        level.addFreshEntity(entity);
                    }
                    c.watcherNecklaceCooldown = 100;
                }
            });
        }
    }

    public static void entityTick(LivingEntityEvents.LivingTickEvent livingTickEvent) {
        MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.maybeGet(livingTickEvent.getEntity()).ifPresent(c -> {
            if (c.watcherNecklaceCooldown > 0) {
                c.watcherNecklaceCooldown--;
            }
        });
    }
}