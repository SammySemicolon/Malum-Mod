package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.curiosities.rite.InfernalExtinguishRiteEffectPacket;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.spiritrite.PotionRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteType;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.INFERNAL_SPIRIT;

public class InfernalRiteType extends TotemicRiteType {
    public InfernalRiteType() {
        super("infernal_rite", ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.MINERS_RAGE);
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.IFRITS_EMBRACE) {

            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                super.doRiteEffect(totemBase, level);
                getNearbyBlocks(totemBase, BaseFireBlock.class).forEach(p -> {
                    BlockState state = totemBase.getLevel().getBlockState(p);
                    if (!state.is(BlockTagRegistry.ENDLESS_FLAME)) {
                        level.playSound(null, p, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);

                        MALUM_CHANNEL.sendToClientsTracking(new InfernalExtinguishRiteEffectPacket(INFERNAL_SPIRIT.getPrimaryColor(), p), level, level.getChunkAt(p).getPos());
                        MALUM_CHANNEL.sendToClientsTracking(new BlockSparkleParticlePacket(ARCANE_SPIRIT.getPrimaryColor(), p), level, level.getChunkAt(p).getPos());
                        totemBase.getLevel().removeBlock(p, false);
                    }
                });
            }

            @Override
            public Predicate<LivingEntity> getEntityPredicate() {
                return super.getEntityPredicate().and(Entity::isOnFire);
            }
        };
    }
}