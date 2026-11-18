package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.entity.activator.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.modules.core.easing.Easing;

import java.util.function.*;

public class CurioWatcherNecklace extends MalumCurioItem implements IMalumEventResponderBody, IVoidItem {
    public CurioWatcherNecklace(Properties builder) {
        super(builder, MalumTrinketFamily.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("full_health_fake_collection"));
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (target.getHealth() >= target.getMaxHealth() * 0.9875f) {
            var data = target.getData(MalumAttachmentTypes.CURIO_DATA);
            if (data.watcherNecklaceCooldown == 0) {
                var level = attacker.level();
                var random = level.getRandom();
                var uuid = attacker.getUUID();
                var position = target.position().add(0, target.getBbHeight() / 2f, 0);
                int amount = target instanceof Player ? 2 : 1;
                var velocity = new Vec3(
                        Easing.SINE_IN_OUT.asWeighedRandom(random, -0.4f, 0.4f),
                        Easing.SINE_IN_OUT.asWeighedRandom(random, 0.1f, 0.2f),
                        Easing.SINE_IN_OUT.asWeighedRandom(random, -0.4f, 0.4f)
                );


                for (int i = 0; i < amount; i++) {
                    SpiritCollectionActivator entity = new SpiritCollectionActivator(level, uuid, position, velocity);
                    level.addFreshEntity(entity);
                }
                data.watcherNecklaceCooldown = 400;
            }
        }
    }

    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            var data = entity.getData(MalumAttachmentTypes.CURIO_DATA);
            if (data.watcherNecklaceCooldown > 0) {
                data.watcherNecklaceCooldown--;
            }
        }
    }
}