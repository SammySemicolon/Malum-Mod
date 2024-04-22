package com.sammy.malum.common.spiritrite.eldritch;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.*;
import com.sammy.malum.common.spiritrite.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.network.*;

import java.util.*;
import java.util.stream.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchSacredRiteType extends TotemicRiteType {
    public EldritchSacredRiteType() {
        super("greater_sacred_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT) {

            @Override
            public int getRiteEffectHorizontalRadius() {
                return 4;
            }

            @Override
            public int getRiteEffectVerticalRadius() {
                return 2;
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                BlockPos pos = totemBase.getBlockPos();
                getNearbyBlocks(totemBase, BonemealableBlock.class).forEach(p -> {
                    if (level.random.nextFloat() <= 0.06f) {
                        BlockState state = level.getBlockState(p);
                        for (int i = 0; i < 5 + level.random.nextInt(3); i++) {
                            state.randomTick((ServerLevel) level, p, level.random);
                        }
                        BlockPos particlePos = state.canOcclude() ? p : p.below();
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new SacredMistRiteEffectPacket(List.of(SACRED_SPIRIT.identifier), particlePos));
                    }
                });
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {

            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                Map<Class<? extends Animal>, List<Animal>> animalMap = getNearbyEntities(totemBase, Animal.class, e -> e.canFallInLove() && e.getAge() == 0).collect(Collectors.groupingBy(Animal::getClass));

                for (List<Animal> animals : animalMap.values()) {
                    if (animals.size() > 20) {
                        continue;
                    }
                    animals.forEach(e -> {
                        if (level.random.nextFloat() <= 0.2f) {
                            e.setInLoveTime(600);
                            MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MajorEntityEffectParticlePacket(SACRED_SPIRIT.getPrimaryColor(), e.getX(), e.getY() + e.getBbHeight() / 2f, e.getZ()));
                        }
                    });
                }
            }
        };
    }
}