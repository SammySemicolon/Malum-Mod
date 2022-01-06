package com.sammy.malum.common.item.spirit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.capability.PlayerDataCapability;
import com.sammy.malum.core.systems.rendering.RenderTypes;
import com.sammy.malum.core.systems.rendering.Shaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.TickEvent;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.core.helper.DataHelper.prefix;
import static com.sammy.malum.core.systems.rendering.RenderManager.DELAYED_RENDER;
import static com.sammy.malum.core.systems.rendering.RenderUtilities.renderQuad;

public class SoulStaveItem extends Item {
    public SoulStaveItem(Properties pProperties) {
        super(pProperties);
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel serverLevel) {
            PlayerDataCapability.getCapability(pPlayer).ifPresent(c -> {
                if (c.targetedSoulUUID != null) {
                    LivingEntity entity = (LivingEntity) serverLevel.getEntity(c.targetedSoulUUID);
                    if (entity != null && entity.isAlive()) {
                        pPlayer.startUsingItem(pUsedHand);
                    }
                }
            });
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.isUsingItem()) {
            ItemStack stack = player.getUseItem();
            //charge soul
        } else if (player.isHolding(s -> s.getItem() instanceof SoulStaveItem)) {
            //fetch soul
            PlayerDataCapability.getCapability(player).ifPresent(c -> {
                c.soulFetchCooldown--;
                if (c.soulFetchCooldown <= 0) {
                    c.soulFetchCooldown = 5;
                    ArrayList<LivingEntity> entities = new ArrayList<>(player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(7f), e -> !e.getUUID().equals(player.getUUID())));
                    double biggestAngle = 0;
                    LivingEntity chosenEntity = null;
                    for (LivingEntity entity : entities) {
                        if (!entity.isAlive()) {
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
                    if (chosenEntity != null && !chosenEntity.getUUID().equals(c.targetedSoulUUID)) {
                        c.targetedSoulUUID = chosenEntity.getUUID();
                        c.targetedSoulId = chosenEntity.getId();
                    }
                    if (chosenEntity == null) {
                        c.targetedSoulUUID = null;
                    }
                }
            });
        }
    }

    public static class ClientOnly {
        private static final Color SOUL_NOISE_COLOR = new Color(182, 61, 183);
        private static final ResourceLocation SOUL_NOISE = prefix("textures/vfx/soul_noise.png");
        private static final RenderType SOUL_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialNoiseQuadRenderType(SOUL_NOISE), () -> {
            ShaderInstance instance = Shaders.radialNoise.getInstance().get();
            instance.safeGetUniform("Speed").set(4500f);
            instance.safeGetUniform("Intensity").set(45f);
        });
        private static final Color HARVEST_NOISE_COLOR = new Color(79, 30, 204);
        private static final ResourceLocation HARVEST_NOISE = prefix("textures/vfx/harvest_noise.png");
        private static final RenderType HARVEST_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialScatterNoiseQuadRenderType(HARVEST_NOISE), () -> {

            ShaderInstance instance = Shaders.radialScatterNoise.getInstance().get();
            instance.safeGetUniform("Speed").set(3500f);
            instance.safeGetUniform("ScatterPower").set(-60f);
            instance.safeGetUniform("Intensity").set(55f);
        });

        public static void renderHarvestVFX(RenderLevelLastEvent event) {
            LocalPlayer player = Minecraft.getInstance().player;
            PlayerDataCapability.getCapability(player).ifPresent(c -> {
                if (c.targetedSoulUUID != null) {
                    Entity entity = player.level.getEntity(c.targetedSoulId);
                    if (entity instanceof LivingEntity livingEntity) {
                        if (livingEntity.isAlive()) {
                            PoseStack poseStack = event.getPoseStack();
                            float partialTicks = event.getPartialTick();
                            poseStack.pushPose();
                            //TODO: make this an entity render layer
                            Vec3 playerPosition = new Vec3(player.xo, player.yo, player.zo).lerp(player.position(), partialTicks);
                            Vec3 entityPosition = new Vec3(livingEntity.xo, livingEntity.yo, livingEntity.zo).lerp(livingEntity.position(), partialTicks);
                            Vec3 toPlayer = playerPosition.subtract(entityPosition).normalize().multiply(0.5f, 0f, 0.5f);
                            Vec3 translation = entityPosition.subtract(playerPosition).add(toPlayer).add(0, livingEntity.getBbHeight(), 0);

                            poseStack.translate(translation.x, translation.y, translation.z);

                            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
                            poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));

                            VertexConsumer soulNoise = DELAYED_RENDER.getBuffer(SOUL_NOISE_TYPE);
                            VertexConsumer harvestNoise = DELAYED_RENDER.getBuffer(HARVEST_NOISE_TYPE);
                            float scale = 1f;
                            renderQuad(soulNoise, poseStack, scale, scale, SOUL_NOISE_COLOR.getRed(), SOUL_NOISE_COLOR.getGreen(), SOUL_NOISE_COLOR.getBlue(), 255, 0, 0, 1, 1);
                            scale = 1.2f;
                            renderQuad(harvestNoise, poseStack, scale, scale, HARVEST_NOISE_COLOR.getRed(), HARVEST_NOISE_COLOR.getGreen(), HARVEST_NOISE_COLOR.getBlue(), 255, 0, 0, 1, 1);
                            poseStack.popPose();
                        }
                    }
                }
            });
        }
    }
}