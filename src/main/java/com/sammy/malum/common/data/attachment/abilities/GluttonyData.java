package com.sammy.malum.common.data.attachment.abilities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.entity.activator.gluttony.GluttonyLocustEntity;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.registry.common.MalumAttachmentTypes;
import com.sammy.malum.registry.common.MalumAttributes;
import com.sammy.malum.registry.common.sound.MalumSoundEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GluttonyData {

    public static Codec<GluttonyData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            UUIDUtil.CODEC.fieldOf("attacked_target").forGetter(GluttonyData::getAttackedTarget)
    ).apply(obj, GluttonyData::new));

    public static StreamCodec<ByteBuf, GluttonyData> STREAM_CODEC = ByteBufCodecs.fromCodec(GluttonyData.CODEC);

    private UUID attackedTarget;

    public GluttonyData() {
    }

    public GluttonyData(UUID attackedTarget) {
        this.attackedTarget = attackedTarget;
    }

    public UUID getAttackedTarget() {
        return attackedTarget;
    }

    public static void directLocusts(LivingDamageEvent.Post event) {
        var source = event.getSource();
        if (!(source.getEntity() instanceof LivingEntity attacker)) {
            return;
        }
        if (!attacker.hasData(MalumAttachmentTypes.GLUTTONY)) {
            return;
        }
        var data = attacker.getData(MalumAttachmentTypes.GLUTTONY);
        data.attackedTarget = event.getEntity().getUUID();
    }
}