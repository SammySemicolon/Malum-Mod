package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.BlockMistParticlePacket;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
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

import java.util.ArrayList;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.setup.server.PacketRegistry.INSTANCE;

public class EldritchSacredRiteType extends MalumRiteType {
    public EldritchSacredRiteType() {
        super("eldritch_sacred_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {
            @Override
            public int getRiteEffectRadius() {
                return BASE_RADIUS*=4;
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                BlockPos pos = totemBase.getBlockPos();

                if (!level.isClientSide) {
                    getNearbyBlocks(totemBase, BonemealableBlock.class).forEach(p -> {
                        if (level.random.nextFloat() <= 0.06f) {
                            BlockState state = level.getBlockState(p);
                            for (int i = 0; i < 5+level.random.nextInt(3); i++) {
                                state.randomTick((ServerLevel) level, p, level.random);
                            }
                            BlockPos particlePos = state.canOcclude() ? p : p.below();
                            INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new BlockMistParticlePacket(SACRED_SPIRIT.getColor(), particlePos.getX(), particlePos.getY(), particlePos.getZ()));
                        }
                    });
                }
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new EntityAffectingRiteEffect() {
            @Override
            public int getRiteEffectTickRate() {
                return BASE_TICK_RATE*2;
            }
            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                if (!level.isClientSide) {
                    ArrayList<Animal> entities = getNearbyEntities(totemBase, Animal.class); //TODO: it'd be interesting to separate different entity types and then breed those respectively, this would allow you to have up to 30 cows, sheep and pigs rather than up to 30 animals
                    entities.removeIf(e -> e.getAge() < 0);
                    if (entities.size() > 30) {
                        return;
                    }
                    entities.forEach(e -> {
                        if (e.canFallInLove() && e.getAge() == 0) {
                            if (level.random.nextFloat() <= 0.05f) {
                                e.setInLoveTime(600);
                                INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(SACRED_SPIRIT.getColor(), e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                            }
                        }
                    });
                }
            }
        };
    }
}
