package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.block.BlockMistParticlePacket;
import com.sammy.malum.common.packets.particle.entity.MajorEntityEffectParticlePacket;
import com.sammy.malum.core.systems.rites.BlockAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.EntityAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;
import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class EldritchSacredRiteType extends MalumRiteType {
    public EldritchSacredRiteType() {
        super("greater_sacred_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {
            @Override
            public int getRiteEffectRadius() {
                return BASE_RADIUS * 2;
            }

            @Override
            public BlockPos getRiteEffectCenter(TotemBaseBlockEntity totemBase) {
                return totemBase.getBlockPos();
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                BlockPos pos = totemBase.getBlockPos();
                getNearbyBlocks(totemBase, BonemealableBlock.class).forEach(p -> {
                    if (level.random.nextFloat() <= 0.06f) {
                        BlockState state = level.getBlockState(p);
                        for (int i = 0; i < 5 + level.random.nextInt(3); i++) {
                            state.randomTick((ServerLevel) level, p, level.random);
                        }
                        BlockPos particlePos = state.canOcclude() ? p : p.below();
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new BlockMistParticlePacket(SACRED_SPIRIT.getColor(), particlePos));
                    }
                });
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new EntityAffectingRiteEffect() {

            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                List<Animal> entities = getNearbyEntities(totemBase, Animal.class, e -> e.canFallInLove() && e.getAge() == 0).collect(Collectors.toList()); //TODO: it'd be interesting to separate different entity types and then breed those respectively, this would allow you to have up to 30 cows, sheep and pigs rather than up to 30 animals
                if (entities.size() > 30) {
                    return;
                }
                entities.forEach(e -> {
                    if (level.random.nextFloat() <= 0.01f) {
                        e.setInLoveTime(600);
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MajorEntityEffectParticlePacket(SACRED_SPIRIT.getColor(), e.getX(), e.getY() + e.getBbHeight() / 2f, e.getZ()));
                    }
                });
            }
        };
    }
}