package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.block.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.block.FireBlockExtinguishSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.entity.MajorEntityEffectParticlePacket;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import com.sammy.malum.registry.common.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.rites.AuraRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.INFERNAL_SPIRIT;
import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class InfernalRiteType extends MalumRiteType {
    public InfernalRiteType() {
        super("infernal_rite", ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new AuraRiteEffect(LivingEntity.class, MalumMobEffectRegistry.MINERS_RAGE, INFERNAL_SPIRIT);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new MalumRiteEffect() {
            @Override
            public int getRiteEffectRadius() {
                return BASE_RADIUS * 4;
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                getNearbyEntities(totemBase, LivingEntity.class).filter(e -> !(e instanceof Monster)).forEach(e -> {
                    if (e.isOnFire()) {
                        level.playSound(null, e.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MajorEntityEffectParticlePacket(getEffectSpirit().getColor(), e.getX(), e.getY() + e.getBbHeight() / 2f, e.getZ()));
                        e.addEffect(new MobEffectInstance(MalumMobEffectRegistry.IFRITS_EMBRACE.get(), 400, 1));
                        e.clearFire();
                    }
                });


                getNearbyBlocks(totemBase, BaseFireBlock.class).forEach(p -> {
                    BlockState state = totemBase.getLevel().getBlockState(p);
                    if (!state.is(BlockTagRegistry.ENDLESS_FLAME)) {
                        level.playSound(null, p, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);

                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new FireBlockExtinguishSparkleParticlePacket(INFERNAL_SPIRIT.getColor(), p));
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new BlockSparkleParticlePacket(ARCANE_SPIRIT.getColor(), p));
                        totemBase.getLevel().removeBlock(p, false);
                    }
                });
            }
        };
    }
}