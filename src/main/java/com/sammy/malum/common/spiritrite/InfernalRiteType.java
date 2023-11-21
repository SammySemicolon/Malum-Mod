package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.*;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.network.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class InfernalRiteType extends MalumRiteType {
    public InfernalRiteType() {
        super("infernal_rite", ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.MINERS_RAGE);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.AURA) {

            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                getNearbyEntities(totemBase, LivingEntity.class).filter(e -> !(e instanceof Monster)).forEach(e -> {
                    if (e.isOnFire()) {
                        level.playSound(null, e.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MajorEntityEffectParticlePacket(getEffectSpirit().getPrimaryColor(), e.getX(), e.getY() + e.getBbHeight() / 2f, e.getZ()));
                        e.addEffect(new MobEffectInstance(MobEffectRegistry.IFRITS_EMBRACE.get(), 400, 1));
                        e.clearFire();
                    }
                });

                getNearbyBlocks(totemBase, BaseFireBlock.class).forEach(p -> {
                    BlockState state = totemBase.getLevel().getBlockState(p);
                    if (!state.is(BlockTagRegistry.ENDLESS_FLAME)) {
                        level.playSound(null, p, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);

                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new InfernalExtinguishRiteEffectPacket(INFERNAL_SPIRIT.getPrimaryColor(), p));
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new BlockSparkleParticlePacket(ARCANE_SPIRIT.getPrimaryColor(), p));
                        totemBase.getLevel().removeBlock(p, false);
                    }
                });
            }
        };
    }
}