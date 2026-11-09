package com.sammy.malum.common.data.attachment.abilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.*;
import com.sammy.malum.registry.common.MalumAttachmentTypes;
import com.sammy.malum.registry.common.MalumMobEffects;
import com.sammy.malum.registry.common.sound.MalumSoundEvents;
import com.sammy.malum.registry.common.MalumTags;
import com.sammy.malum.visual_effects.AvariceParticleEffects;
import io.netty.buffer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.*;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;

public class AvariceData {

    public static Codec<AvariceData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.BOOL.optionalFieldOf("hasProspectorMark", false).forGetter(AvariceData::hasAvariceMark),
            Codec.INT.optionalFieldOf("carriedAvarice", 0).forGetter(AvariceData::getCarriedAvarice)
    ).apply(obj, AvariceData::new));

    public static StreamCodec<ByteBuf, AvariceData> STREAM_CODEC = ByteBufCodecs.fromCodec(AvariceData.CODEC);
    private boolean hasAvariceMark;
    private int carriedAvarice;

    private AvariceData(boolean hasAvariceMark, int carriedAvarice) {
        this.hasAvariceMark = hasAvariceMark;
    }

    public AvariceData() {
    }

    public boolean hasAvariceMark() {
        return hasAvariceMark;
    }

    public int getCarriedAvarice() {
        return carriedAvarice;
    }

    public static void applyAvariceMark(BlockDropsEvent event) {
        if (event.getBreaker() instanceof LivingEntity breaker) {
            int amount = getAppliedAvarice(breaker);
            if (amount <= 0) {
                return;
            }
            for (ItemEntity drop : event.getDrops()) {
                tryMarkEntity(drop, amount);
            }
        }
    }

    public static int getAppliedAvarice(LivingEntity entity) {
        return CurioProspectorBelt.hasProspectorBelt(entity) ? 1 : 0;
    }

    public void tickData(Entity entity) {
        if (entity.level().isClientSide) {
            AvariceParticleEffects.avariceItemParticles(entity.level(), entity);
        }
    }

    public void enableMark(int amount) {
        this.hasAvariceMark = true;
        this.carriedAvarice = amount;
    }

    public static void entityTick(EntityTickEvent.Pre event) {
        var entity = event.getEntity();
        entity.getExistingData(MalumAttachmentTypes.AVARICE).ifPresent(d -> d.tickData(entity));
    }

    public static void pickupItem(ItemEntityPickupEvent.Post event) {
        ItemEntity entity = event.getItemEntity();
        entity.getExistingData(MalumAttachmentTypes.AVARICE).ifPresent(data -> {
            if (data.hasAvariceMark()) {
                applyAvarice(event.getPlayer(), data.carriedAvarice);
            }
        });
    }

    public static void popResourceAndMarkEntity(Level level, BlockPos pos, ItemStack stack, int amount) {
        double d0 = (double) EntityType.ITEM.getHeight() / 2.0;
        double d1 = (double) pos.getX() + 0.5 + Mth.nextDouble(level.random, -0.25, 0.25);
        double d2 = (double) pos.getY() + 0.5 + Mth.nextDouble(level.random, -0.25, 0.25) - d0;
        double d3 = (double) pos.getZ() + 0.5 + Mth.nextDouble(level.random, -0.25, 0.25);
        Block.popResource(level, () -> tryMarkEntity(new ItemEntity(level, d1, d2, d3, stack), amount), stack);
    }

    public static ItemEntity tryMarkEntity(ItemEntity entity, int amount) {
        if (entity.getItem().is(MalumTags.Items.PROSPECTORS_TREASURE)) {
            entity.getData(MalumAttachmentTypes.AVARICE).enableMark(amount);
            entity.syncData(MalumAttachmentTypes.AVARICE);
        }
        return entity;
    }

    public static void applyAvarice(LivingEntity target, int amount) {
        var effect = MalumMobEffects.AVARICE;
        var instance = target.getEffect(effect);
        float volume = 1f;
        if (instance == null) {
            target.addEffect(new MobEffectInstance(effect, 400, amount-1, true, true, true));
        } else {
            EntityHelper.amplifyEffect(instance, target, amount, 9);
            EntityHelper.extendEffect(instance, target, 400, 2400);
            volume -= Math.min(instance.getAmplifier()*0.05f, 0.5f);
        }
        if (CurioHeartyAvariceRing.hasHeartyRing(target)) {
            target.heal(4);
            if (target instanceof Player player) {
                player.getFoodData().eat(1, 2f);
            }
        }
        SoundPlayer.create(MalumSoundEvents.AVARICE_COLLECT).volume(volume).pitch(0.8f, 1.2f).play(target);
    }
}