package com.sammy.malum.core.systems.spirit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.capability.LivingEntityDataCapability;
import com.sammy.malum.common.capability.PlayerDataCapability;
import com.sammy.malum.common.entity.spirit.SoulEntity;
import com.sammy.malum.common.item.spirit.SoulStaveItem;
import com.sammy.malum.common.packets.particle.SoulPurgeParticlePacket;
import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.rendering.RenderTypes;
import com.sammy.malum.core.systems.rendering.Shaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.network.PacketDistributor;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.core.helper.DataHelper.prefix;
import static com.sammy.malum.core.registry.PacketRegistry.INSTANCE;
import static com.sammy.malum.core.systems.rendering.RenderManager.DELAYED_RENDER;
import static com.sammy.malum.core.systems.rendering.RenderUtilities.renderQuad;
import static net.minecraft.util.Mth.nextFloat;

public class SoulHarvestHandler {

    public static void specialSpawn(LivingSpawnEvent.SpecialSpawn event)
    {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            LivingEntityDataCapability.getCapability(livingEntity).ifPresent(ec -> {
                if (event.getSpawnReason().equals(MobSpawnType.SPAWNER)) {
                    ec.spawnerSpawned = true;
                }
            });
        }
    }
    public static void addEntity(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            LivingEntityDataCapability.getCapability(livingEntity).ifPresent(ec -> {
                ec.spiritData = SpiritHelper.getEntitySpiritData(livingEntity);
                if (livingEntity instanceof Mob mob) {
                    if (ec.soulless) {
                        removeSentience(mob);
                    }
                }
            });
        }
    }
    public static void entityTarget(LivingSetAttackTargetEvent event)
    {
        if (event.getEntityLiving() instanceof Mob mob) {
            LivingEntityDataCapability.getCapability(mob).ifPresent(ec -> {
                if (ec.soulless) {
                    mob.target = null;
                }
            });
        }
    }
    public static void entityTick(LivingEvent.LivingUpdateEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
        LivingEntityDataCapability.getCapability(entity).ifPresent(ec -> {
            if (ec.exposedSoul > 0)
            {
                ec.exposedSoul--;
            }
            if (ec.ownerUUID != null && ec.soulHarvestProgress > 0)
            {
                Player player = entity.level.getPlayerByUUID(ec.ownerUUID);
                if (player != null)
                {
                    PlayerDataCapability.getCapability(player).ifPresent(c -> {
                        if (!player.isUsingItem() && ec.soulHarvestProgress > 10)
                        {
                            ec.soulHarvestProgress -=2f;
                        }
                        if (ec.soulHarvestProgress <= 10 && !ec.soulless) {
                            if (c.targetedSoulUUID == null || !entity.getUUID().equals(c.targetedSoulUUID)) {
                                ec.soulHarvestProgress -= 0.5f;
                                if (ec.soulHarvestProgress == 0) {
                                    ec.ownerUUID = null;
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        PlayerDataCapability.getCapability(player).ifPresent(c -> {
            boolean isHoldingStave = (player.isHolding(s -> s.getItem() instanceof SoulStaveItem));
            if (isHoldingStave) {
                boolean isUsingStave = player.isUsingItem();
                int harvestVFXCap = isUsingStave ? 160 : 10;
                if (c.targetedSoulUUID != null) {
                    Entity entity = player.level.getEntity(c.targetedSoulId);
                    if (entity instanceof LivingEntity livingEntity) {
                        LivingEntityDataCapability.getCapability(livingEntity).ifPresent(ec -> {
                            if (ec.soulHarvestProgress < harvestVFXCap) {
                                ec.soulHarvestProgress += 1;
                            }
                        });
                    }
                }
                if (isUsingStave) {
                    //harvest soul
                    if (player.level instanceof ServerLevel) {
                        if (c.targetedSoulUUID != null) {
                            Entity entity = player.level.getEntity(c.targetedSoulId);
                            if (entity instanceof LivingEntity livingEntity) {
                                LivingEntityDataCapability.getCapability(livingEntity).ifPresent(ec -> {
                                    if (ec.getHarvestProgress() >= 150) {
                                        Vec3 position = entity.position().add(0, entity.getEyeHeight() / 2f, 0);
                                        SoulEntity soulEntity = new SoulEntity(entity.level, SpiritHelper.getEntitySpiritData(livingEntity), player.getUUID(),
                                                position.x,
                                                position.y,
                                                position.z,
                                                nextFloat(MalumMod.RANDOM, -0.1f, 0.1f),
                                                0.05f + nextFloat(MalumMod.RANDOM, 0.05f, 0.15f),
                                                nextFloat(MalumMod.RANDOM, -0.1f, 0.1f));
                                        player.level.addFreshEntity(soulEntity);
                                        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new SoulPurgeParticlePacket(ec.spiritData.primaryType.color, ec.spiritData.primaryType.endColor, position.x, position.y, position.z));
                                        if (livingEntity instanceof Mob mob) {
                                            removeSentience(mob);
                                        }
                                        ec.soulless = true;
                                        ec.ownerUUID = player.getUUID();
                                        player.swing(player.getUsedItemHand(), true);
                                        player.releaseUsingItem();
                                        LivingEntityDataCapability.sync(livingEntity);
                                    }
                                });
                            }
                        }
                    }
                } else {
                    //fetch soul
                    c.soulFetchCooldown--;
                    if (c.soulFetchCooldown <= 0) {
                        c.soulFetchCooldown = 5;
                        ArrayList<LivingEntity> entities = new ArrayList<>(player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(7f), e -> !e.getUUID().equals(player.getUUID())));
                        double biggestAngle = 0;
                        LivingEntity chosenEntity = null;
                        for (LivingEntity entity : entities) {
                            if (!entity.isAlive() || LivingEntityDataCapability.isSoulless(entity) || !LivingEntityDataCapability.hasSpiritData(entity)) {
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
                        if (chosenEntity != null && (!chosenEntity.getUUID().equals(c.targetedSoulUUID) || LivingEntityDataCapability.getOwner(chosenEntity) == null)) {
                            c.targetedSoulUUID = chosenEntity.getUUID();
                            c.targetedSoulId = chosenEntity.getId();
                            LivingEntityDataCapability.getCapability(chosenEntity).ifPresent(ec -> ec.ownerUUID = player.getUUID());
                            if (chosenEntity.level instanceof ServerLevel) {
                                LivingEntityDataCapability.sync(chosenEntity);
                            }
                        }
                        if (chosenEntity == null) {
                            c.targetedSoulUUID = null;
                            c.targetedSoulId = -1;
                        }
                    }
                }
            }
            else if (c.targetedSoulUUID != null)
            {
                c.targetedSoulUUID = null;
                c.targetedSoulId = -1;
            }
        });
    }
    public static void removeSentience(Mob mob)
    {
        mob.goalSelector.getAvailableGoals().removeIf(g -> g.getGoal() instanceof LookAtPlayerGoal || g.getGoal() instanceof MeleeAttackGoal || g.getGoal() instanceof SwellGoal || g.getGoal() instanceof PanicGoal || g.getGoal() instanceof RandomLookAroundGoal || g.getGoal() instanceof AvoidEntityGoal);
    }
    public static class ClientOnly {
        private static final ResourceLocation HARVEST_NOISE = prefix("textures/vfx/soul_noise_secondary.png");
        private static final RenderType HARVEST_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialScatterNoiseQuadRenderType(HARVEST_NOISE), () -> {

            ShaderInstance instance = Shaders.radialScatterNoise.getInstance().get();
            instance.safeGetUniform("Speed").set(-5500f);
            instance.safeGetUniform("ScatterPower").set(40f);
            instance.safeGetUniform("Intensity").set(55f);
        });
        private static final ResourceLocation SOUL_NOISE = prefix("textures/vfx/soul_noise.png");
        private static final RenderType SOUL_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialNoiseQuadRenderType(SOUL_NOISE), () -> {
            ShaderInstance instance = Shaders.radialNoise.getInstance().get();
            instance.safeGetUniform("Speed").set(4500f);
            instance.safeGetUniform("Intensity").set(45f);
        });
        private static final ResourceLocation PREVIEW_NOISE = prefix("textures/vfx/harvest_noise.png");
        private static final RenderType PREVIEW_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialScatterNoiseQuadRenderType(PREVIEW_NOISE), () -> {

            ShaderInstance instance = Shaders.radialScatterNoise.getInstance().get();
            instance.safeGetUniform("Speed").set(-3500f);
            instance.safeGetUniform("ScatterPower").set(-60f);
            instance.safeGetUniform("Intensity").set(55f);
        });

        public static class HarvestRenderLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

            public HarvestRenderLayer(RenderLayerParent<T, M> parent) {
                super(parent);
            }

            @Override
            public void render(PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float partialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
                LivingEntityDataCapability.getCapability(pLivingEntity).ifPresent(c -> {
                    if (c.ownerUUID != null) {
                        Player player = pLivingEntity.level.getPlayerByUUID(c.ownerUUID);
                        if (player != null && player.isAlive() && pLivingEntity.isAlive()) {
                            poseStack.popPose();
                            float previewProgress = c.getPreviewProgress() / 10f;
                            float harvestProgress = c.getHarvestProgress() / 100f;
                            if (previewProgress > 0f) {
                                poseStack.pushPose();
                                AABB boundingBox = pLivingEntity.getBoundingBox();
                                Vec3 playerPosition = new Vec3(player.xo, player.yo, player.zo).lerp(player.position(), partialTicks);
                                Vec3 entityPosition = new Vec3(pLivingEntity.xo, pLivingEntity.yo, pLivingEntity.zo).lerp(pLivingEntity.position(), partialTicks);
                                Vec3 toPlayer = playerPosition.subtract(entityPosition).normalize().multiply(boundingBox.getXsize() * 0.5f, 0, boundingBox.getZsize() * 0.5f);

                                poseStack.translate(toPlayer.x, toPlayer.y + pLivingEntity.getBbHeight() / 2f, toPlayer.z);
                                poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
                                poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));

                                Color soulColor = c.spiritData.primaryType.color;
                                Color previewColor = ColorHelper.darker(c.spiritData.primaryType.color, 2);
                                Color harvestColor = ColorHelper.brighter(c.spiritData.primaryType.color, 2);
                                VertexConsumer soulNoise = DELAYED_RENDER.getBuffer(SOUL_NOISE_TYPE);
                                VertexConsumer previewNoise = DELAYED_RENDER.getBuffer(PREVIEW_NOISE_TYPE);
                                float scale = previewProgress;
                                renderQuad(soulNoise, poseStack, scale, scale, soulColor.getRed(), soulColor.getGreen(), soulColor.getBlue(), (int) (255 * previewProgress), 0, 0, 1, 1);
                                scale += 0.3f * previewProgress;
                                renderQuad(previewNoise, poseStack, scale, scale, previewColor.getRed(), previewColor.getGreen(), previewColor.getBlue(), (int) (255 * previewProgress), 0, 0, 1, 1);
                                if (harvestProgress > 0f)
                                {
                                    VertexConsumer harvestNoise = DELAYED_RENDER.getBuffer(HARVEST_NOISE_TYPE);
                                    scale = harvestProgress * 0.9f;
                                    renderQuad(harvestNoise, poseStack, scale, scale, harvestColor.getRed(), harvestColor.getGreen(), harvestColor.getBlue(), (int) Math.min(255, 255 * harvestProgress), 0, 0, 1, 1);
                                }
                                poseStack.popPose();
                            }
                            poseStack.pushPose();
                        }
                    }
                });
            }
        }
    }
}
