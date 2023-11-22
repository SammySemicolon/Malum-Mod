package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.*;
import com.sammy.malum.core.systems.rites.*;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchSacredRiteType extends MalumRiteType {
    public EldritchSacredRiteType() {
        super("greater_sacred_rite", ELDRITCH_SPIRIT.get(), ARCANE_SPIRIT.get(), SACRED_SPIRIT.get(), SACRED_SPIRIT.get());
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT) {

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
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new SacredMistRiteEffectPacket(List.of(SACRED_SPIRIT.get().getRegistryName().getNamespace()), particlePos));
                    }
                });
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {

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
                        if (level.random.nextFloat() <= 0.01f) {
                            e.setInLoveTime(600);
                            MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MajorEntityEffectParticlePacket(SACRED_SPIRIT.get().getPrimaryColor(), e.getX(), e.getY() + e.getBbHeight() / 2f, e.getZ()));
                        }
                    });
                }
            }
        };
    }

    @Override
    public MalumRiteType setRegistryName(ResourceLocation name) {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return null;
    }

    @Override
    public Class<MalumRiteType> getRegistryType() {
        return null;
    }
}