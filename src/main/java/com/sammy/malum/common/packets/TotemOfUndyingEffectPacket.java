package com.sammy.malum.common.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class TotemOfUndyingEffectPacket {
    private final int entityId;
    private final ItemStack stack;

    public TotemOfUndyingEffectPacket(Entity entity, ItemStack stack) {
        this.entityId = entity.getId();
        this.stack = stack;
    }
    public TotemOfUndyingEffectPacket(int entityId, ItemStack stack) {
        this.entityId = entityId;
        this.stack = stack;
    }

    public static TotemOfUndyingEffectPacket decode(FriendlyByteBuf buf) {
        return new TotemOfUndyingEffectPacket(buf.readInt(), buf.readItem());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeItem(stack);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.triggerTotem(entityId, stack);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, TotemOfUndyingEffectPacket.class, TotemOfUndyingEffectPacket::encode, TotemOfUndyingEffectPacket::decode, TotemOfUndyingEffectPacket::execute);
    }

    public static class ClientOnly {
        public static void triggerTotem(int entityId, ItemStack stack) {
            Minecraft minecraft = Minecraft.getInstance();
            Entity entity = minecraft.level.getEntity(entityId);
            if (entity instanceof LivingEntity livingEntity) {
                minecraft.particleEngine.createTrackingEmitter(livingEntity, ParticleTypes.TOTEM_OF_UNDYING, 30);
                minecraft.level.playLocalSound(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.TOTEM_USE, livingEntity.getSoundSource(), 1.0F, 1.0F, false);
                if (livingEntity == minecraft.player) {
                    minecraft.gameRenderer.displayItemActivation(stack);
                }
            }
        }
    }
}