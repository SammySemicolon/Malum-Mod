package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.common.item.spirit.SoulStaveItem;
import com.sammy.malum.common.packets.particle.entity.SuccessfulSoulHarvestParticlePacket;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

public class SoulHarvestHandler {

    public static final float PRIMING_END = 10f;
    public static final float HARVEST_DURATION = 90f;

    public UUID targetedSoulUUID;
    public int targetedSoulId;
    public int soulFetchCooldown;

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (targetedSoulUUID != null) {
            tag.putUUID("targetedSoulUUID", targetedSoulUUID);
        }
        if (targetedSoulId != 0) {
            tag.putInt("targetedSoulId", targetedSoulId);
        }
        if (soulFetchCooldown != 0) {
            tag.putInt("soulFetchCooldown", soulFetchCooldown);
        }
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("targetedSoulUUID")) {
            targetedSoulUUID = tag.getUUID("targetedSoulUUID");
        }
        targetedSoulId = tag.getInt("targetedSoulId");
        soulFetchCooldown = tag.getInt("soulFetchCooldown");
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        SoulHarvestHandler soulHarvestHandler = MalumPlayerDataCapability.getCapability(player).soulHarvestHandler;

        boolean isHoldingStave = (player.isHolding(s -> s.getItem() instanceof SoulStaveItem));
        boolean isUsingStave = player.isUsingItem();
        if (isHoldingStave) {
            if (!isUsingStave) {
                //Here we try and figure out what entity the player wants to target with their stave.
                //We basically just find all nearby entities, and search for the one with the least angle difference between the angle towards the player, and the look direction of the player
                soulHarvestHandler.soulFetchCooldown--;
                if (soulHarvestHandler.soulFetchCooldown <= 0) {
                    soulHarvestHandler.soulFetchCooldown = 5;
                    List<LivingEntity> entities = new ArrayList<>(player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(7f), e -> !e.getUUID().equals(player.getUUID())));
                    double biggestAngle = 0;
                    LivingEntity chosenEntity = null;
                    for (LivingEntity entity : entities) {
                        if (!entity.isAlive() || MalumLivingEntityDataCapability.getCapability(entity).soulData.soulless || SpiritHelper.getEntitySpiritData(entity) == null) {
                            continue;
                        }
                        Vec3 lookDirection = player.getLookAngle();
                        Vec3 directionToEntity = entity.position().subtract(player.position()).normalize();
                        double angle = lookDirection.dot(directionToEntity);
                        if (angle > biggestAngle && angle > 0.5f) {
                            biggestAngle = angle;
                            chosenEntity = entity;
                        }
                    }
                    if (chosenEntity == null) {
                        soulHarvestHandler.targetedSoulUUID = null;
                        soulHarvestHandler.targetedSoulId = -1;
                    } else if (!chosenEntity.getUUID().equals(soulHarvestHandler.targetedSoulUUID) || MalumLivingEntityDataCapability.getCapability(chosenEntity).soulData.soulThiefUUID == null) {
                        soulHarvestHandler.targetedSoulUUID = chosenEntity.getUUID();
                        soulHarvestHandler.targetedSoulId = chosenEntity.getId();
                        MalumLivingEntityDataCapability.getCapability(chosenEntity).soulData.soulThiefUUID = player.getUUID();
                        if (chosenEntity.level instanceof ServerLevel) {
                            MalumLivingEntityDataCapability.sync(chosenEntity);
                        }
                    }
                }
            }

            int desiredProgress = isUsingStave ? 160 : 10;
            if (soulHarvestHandler.targetedSoulUUID != null) {
                //Here we essentially "prime" the entity that we are targeting.
                //If the player isn't using their staff, we raise their target's soul harvest progress to ten, which the renderer understands as "put a target on this guy"
                //If the player is using their staff, we instead just start draining the soul
                Entity entity = player.level.getEntity(soulHarvestHandler.targetedSoulId);
                if (entity instanceof LivingEntity livingEntity) {
                    SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(livingEntity).soulData;
                    if (soulData.soulSeparationProgress < desiredProgress) {
                        soulData.soulSeparationProgress += 1;
                    }
                }
            }
            //and here's where all the magic happens. When a player is using their staff, we check if the harvest progress has reached it's maximum.
            //Once it has, we will spawn a soul and mark the targeted entity as soulless.
            if (isUsingStave) {
                if (player.level instanceof ServerLevel) {
                    if (soulHarvestHandler.targetedSoulUUID != null) {
                        Entity entity = player.level.getEntity(soulHarvestHandler.targetedSoulId);
                        if (entity instanceof LivingEntity livingEntity) {
                            SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(livingEntity).soulData;

                            if (soulData.soulSeparationProgress >= HARVEST_DURATION) {
                                Vec3 position = entity.position().add(0, entity.getEyeHeight() / 2f, 0);
                                MalumEntitySpiritData data = SpiritHelper.getEntitySpiritData(livingEntity);
                                MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new SuccessfulSoulHarvestParticlePacket(data.primaryType.getColor(), data.primaryType.getEndColor(), position.x, position.y, position.z));
                                if (livingEntity instanceof Mob mob) {
                                    SoulDataHandler.removeSentience(mob);
                                }
                                soulData.soulless = true;
                                soulData.soulThiefUUID = player.getUUID();
                                player.swing(player.getUsedItemHand(), true);
                                player.releaseUsingItem();
                                MalumLivingEntityDataCapability.sync(livingEntity);
                            }
                        }
                    }
                }
            }
        } else if (soulHarvestHandler.targetedSoulUUID != null) {
            soulHarvestHandler.targetedSoulUUID = null;
            soulHarvestHandler.targetedSoulId = -1;
        }
    }

    public static class ClientOnly {
        private static final ResourceLocation TARGET_TEXTURE = malumPath("textures/block/the_device.png");
        private static final RenderType TARGET_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.apply(TARGET_TEXTURE);

        @SuppressWarnings("all")
        public static void addRenderLayer(EntityRenderer<?> render) {
            if (render instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new SoulHarvestHandler.ClientOnly.HarvestRenderLayer<>(livingRenderer));
            }
        }

        public static class HarvestRenderLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

            public HarvestRenderLayer(RenderLayerParent<T, M> parent) {
                super(parent);
            }

            @Override
            public void render(PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float partialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
                MalumLivingEntityDataCapability.getCapabilityOptional(pLivingEntity).ifPresent(c -> {
                    if (c.soulData.soulThiefUUID != null) {
                        Player player = pLivingEntity.level.getPlayerByUUID(c.soulData.soulThiefUUID);
                        if (player != null && player.isAlive() && pLivingEntity.isAlive()) {
                            MalumEntitySpiritData data = SpiritHelper.getEntitySpiritData(pLivingEntity);
                            poseStack.popPose();
                            renderSoulHarvestEffects(poseStack, pLivingEntity, player, data.primaryType.getColor(), c.soulData, partialTicks);
                            poseStack.pushPose();
                        }
                    }
                });
            }
        }

        public static void renderSoulHarvestEffects(PoseStack poseStack, LivingEntity target, Player player, Color color, SoulDataHandler soulData, float partialTicks) {
            if (soulData.soulSeparationProgress > 0f) {
                poseStack.pushPose();
                AABB boundingBox = target.getBoundingBox();
                Vec3 playerPosition = new Vec3(player.xo, player.yo, player.zo).lerp(player.position(), partialTicks);
                Vec3 entityPosition = new Vec3(target.xo, target.yo, target.zo).lerp(target.position(), partialTicks);
                Vec3 toPlayer = playerPosition.subtract(entityPosition).normalize().multiply(boundingBox.getXsize() * 0.5f, 0, boundingBox.getZsize() * 0.5f);

                VertexConsumer soulNoise = DELAYED_RENDER.getBuffer(TARGET_TYPE);

                poseStack.translate(toPlayer.x, toPlayer.y + target.getBbHeight() / 2f, toPlayer.z);
                poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));

                //preview
                float intensity = Math.min(10, soulData.soulSeparationProgress) / 10f;


                VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat()
                        .setColor(color.brighter())
                        .setAlpha(intensity * 0.6f)
                        .renderQuad(soulNoise, poseStack, intensity*0.4f);
                poseStack.popPose();
            }
        }
    }
}