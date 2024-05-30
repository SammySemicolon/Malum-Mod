package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sammy.malum.client.BufferWrapper;
import com.sammy.malum.client.renderer.entity.FloatingItemEntityRenderer;
import com.sammy.malum.common.block.curiosities.spirit_crucible.CrucibleTuning;
import com.sammy.malum.common.block.curiosities.spirit_crucible.SpiritCrucibleCoreBlockEntity;
import com.sammy.malum.common.item.augment.AbstractAugmentItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.List;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class SpiritCrucibleRenderer implements BlockEntityRenderer<SpiritCrucibleCoreBlockEntity> {

    private static float tuningForkHeldTimer = 0;
    private static boolean isHoldingFork;

    public SpiritCrucibleRenderer(BlockEntityRendererProvider.Context context) {
    }

    public static void checkForTuningFork(Minecraft minecraft) {
        final LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }
        final Item tuningFork = ItemRegistry.TUNING_FORK.get();
        if ((player.getMainHandItem().getItem().equals(tuningFork) || player.getOffhandItem().getItem().equals(tuningFork))) {
            if (tuningForkHeldTimer < 20) {
                tuningForkHeldTimer++;
            }
            isHoldingFork = true;
        } else if (tuningForkHeldTimer > 0) {
            tuningForkHeldTimer--;
            isHoldingFork = false;
        }
    }

    @Override
    public void render(SpiritCrucibleCoreBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        LodestoneBlockEntityInventory inventory = blockEntityIn.spiritInventory;
        int spiritsRendered = 0;
        if (!inventory.isEmpty()) {
            for (int i = 0; i < inventory.slotCount; i++) {
                ItemStack item = inventory.getStackInSlot(i);
                if (item.getItem() instanceof SpiritShardItem shardItem) {
                    poseStack.pushPose();
                    Vector3f offset = blockEntityIn.getSpiritItemOffset(spiritsRendered++, partialTicks).toVector3f();
                    poseStack.translate(offset.x(), offset.y(), offset.z());
                    FloatingItemEntityRenderer.renderSpiritGlimmer(poseStack, shardItem.type, partialTicks);
                    poseStack.mulPose(Axis.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    itemRenderer.renderStatic(item, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
                    poseStack.popPose();
                }
            }
        }
        ItemStack stack = blockEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = blockEntityIn.getCentralItemOffset();
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
        final LodestoneBlockEntityInventory augmentInventory = blockEntityIn.augmentInventory;

        int augmentsRendered = 0;
        if (!augmentInventory.isEmpty()) {
            float total = augmentInventory.slotCount;
            float time = 240;
            for (int i = 0; i < total; i++) {
                ItemStack item = augmentInventory.getStackInSlot(i);
                if (item.getItem() instanceof AbstractAugmentItem) {
                    double angle = augmentsRendered / total * (Math.PI * 2);
                    angle -= (((long) (blockEntityIn.spiritSpin + partialTicks) % time) / time) * (Math.PI * 2);
                    poseStack.pushPose();
                    Vector3f offset = blockEntityIn.getAugmentItemOffset(augmentsRendered++, partialTicks).toVector3f();
                    poseStack.translate(offset.x(), offset.y(), offset.z());
                    poseStack.mulPose(Axis.YP.rotation((float) angle - (i % 2 == 0 ? 4.71f : 1.57f)));
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    itemRenderer.renderStatic(item, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
                    poseStack.popPose();
                }
            }
        }
        if (tuningForkHeldTimer > 5) {
            final Font font = Minecraft.getInstance().font;
            float timer = Mth.clamp((tuningForkHeldTimer + (isHoldingFork ? 1 : -1) * partialTicks), 0, 20);
            float scalar = Easing.SINE_IN_OUT.ease(timer / 20f, 0, 1, 1);
            float scale = 0.016F - (1 - scalar) * 0.004f;
            final Font.DisplayMode display = Font.DisplayMode.NORMAL;
            MultiBufferSource textBuffer = new BufferWrapper(LodestoneRenderTypeRegistry.ADDITIVE_TEXT, RenderHandler.DELAYED_RENDER.getTarget());
            final List<CrucibleTuning.CrucibleAttributeType> validValues = CrucibleTuning.CrucibleAttributeType.getValidValues(blockEntityIn.acceleratorData);
            poseStack.pushPose();
            poseStack.translate(0.5f, 2f, 0.55f);
            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            poseStack.mulPose(Axis.YP.rotationDegrees(180f));
            for (int i = 0; i < validValues.size(); i++) {
                CrucibleTuning.CrucibleAttributeType tuningType = validValues.get(i);
                String dataPrint = tuningType.statDisplayFunction.apply(blockEntityIn.getAccelerationData());
                boolean important = tuningType.equals(blockEntityIn.tuningType);
                MutableComponent dataText = Component.literal(" <" + dataPrint + ">");
                MutableComponent text = Component.translatable(tuningType.translation());
                MutableComponent outlineText = Component.translatable(tuningType.translation());
                if (important) {
                    dataText.withStyle(ChatFormatting.BOLD);
                    text = Component.literal("[").append(text).append(Component.literal("]")).withStyle(ChatFormatting.BOLD);
                    outlineText = Component.literal("[").append(outlineText).append(Component.literal("]")).withStyle(ChatFormatting.BOLD);
                }

                text.append(dataText).withStyle(ChatFormatting.AQUA);
                outlineText.append(dataText).withStyle(ChatFormatting.LIGHT_PURPLE);

                poseStack.pushPose();
                poseStack.translate(0, i * 0.15f, 0);
                poseStack.scale(scale, -scale, -scale);
                Matrix4f pose = poseStack.last().pose();
                float f = (-font.width(text) / 2f);
                float xPos = 0 + f;
                int color = ColorHelper.getColor(1, 1, 1, 0.38f * scalar);
                font.drawInBatch(text, xPos, 0, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);

                color = ColorHelper.getColor(1, 1, 1, 0.18f * scalar);
                font.drawInBatch(text, xPos - 0.5f, 0, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);
                font.drawInBatch(text, xPos - 0.5f, 0, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);
                font.drawInBatch(text, xPos, 0.5f, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);
                font.drawInBatch(text, xPos, -0.5f, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);

                color = ColorHelper.getColor(1, 1, 1, 0.12f * scalar);
                font.drawInBatch(text, xPos - 1, 0, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);
                font.drawInBatch(outlineText, xPos + 1, 0, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);
                font.drawInBatch(outlineText, xPos, 1, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);
                font.drawInBatch(text, xPos, -1, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);

                font.drawInBatch(outlineText, xPos - 0.5f, -0.5f, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);
                font.drawInBatch(text, xPos - 0.5f, 0.5f, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);
                font.drawInBatch(outlineText, xPos + 0.5f, 0.5f, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);
                font.drawInBatch(text, xPos + 0.5f, -0.5f, color, false, pose, textBuffer, display, 0, LightTexture.FULL_BRIGHT);
                poseStack.popPose();
            }
            poseStack.popPose();
        }
    }
}
