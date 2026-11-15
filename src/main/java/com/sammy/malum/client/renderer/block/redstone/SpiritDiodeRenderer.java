package com.sammy.malum.client.renderer.block.redstone;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.common.block.curiosities.artifice.waveform.WaveformDiodeBlock;
import com.sammy.malum.common.block.curiosities.artifice.waveform.SpiritDiodeBlockEntity;
import com.sammy.malum.core.systems.item.HeldItemTracker;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import com.sammy.malum.registry.common.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;
import team.lodestar.lodestone.modules.rendering.LodestoneRenderingSystem;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.builder.data.CubeVertexData;

import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;
import team.lodestar.lodestone.systems.rendering.wrapper.LodestoneBufferWrapper;

import java.awt.*;


public abstract class SpiritDiodeRenderer<T extends SpiritDiodeBlockEntity> implements BlockEntityRenderer<T> {

    private static final MultiBufferSource TEXT = new LodestoneBufferWrapper(LodestoneRenderTypes.ADDITIVE_TEXT, LodestoneRenderingSystem.DEFERRED_RENDER.getTarget());

    protected final RenderTypeToken output;
    protected final String langKey;
    protected static final Color COLOR = new Color(170, 15, 1);

    public static final HeldItemTracker CLAW_TRACKER = new HeldItemTracker(p -> p.is(MalumTags.Items.IS_REDSTONE_TOOL));

    public SpiritDiodeRenderer(BlockEntityRendererProvider.Context context, ResourceLocation tokenTexture, String langKey) {
        this.output = RenderTypeToken.createToken(tokenTexture);
        this.langKey = langKey;
    }

    public abstract float getGlowDelta(T blockEntityIn, float delta);
    @Override
    public void render(T blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (blockEntityIn.visualTransitionDuration != 0) {
            float timing = (blockEntityIn.getLevel().getGameTime() - blockEntityIn.visualStartTime) / (float) blockEntityIn.visualTransitionDuration;
            float delta = Mth.clampedLerp(blockEntityIn.visualTransitionStart, blockEntityIn.visualTransitionEnd, timing);
            float glow = Mth.clamp(getGlowDelta(blockEntityIn, delta), 0, 1);
            if (glow > 0) {
                float alpha = Easing.CUBIC_OUT.ease(glow) * 0.8f;
                float glowAlpha = Easing.SINE_OUT.ease(glow) * 0.4f;
                var cubeVertexData = CubeVertexData.makeCubePositions(1.002f);
                var builder = VFXBuilders.createWorld()
                        .setColor(COLOR);
                poseStack.pushPose();
                poseStack.translate(0.5f, 0.5f, 0.5f);
                BlockState state = blockEntityIn.getBlockState();
                for (int i = 0; i < 4; i++) {
                    var direction = Direction.from2DDataValue(i);
                    var token = getTokenForSide(state, direction);
                    var transparent = LodestoneRenderTypes.TRANSPARENT_TEXTURE.apply(token);
                    var additive = LodestoneRenderTypes.ADDITIVE_TEXTURE.apply(token);
                    builder.setAlpha(alpha).setRenderType(transparent).renderCubeSide(poseStack, cubeVertexData, direction);
                    builder.setAlpha(glowAlpha).setRenderType(additive).renderCubeSide(poseStack, cubeVertexData, direction);
                }
                poseStack.popPose();
            }
        }

        if (CLAW_TRACKER.isVisible()) {
            Minecraft minecraft = Minecraft.getInstance();
            var font = minecraft.font;
            float delta = CLAW_TRACKER.getDelta(partialTicks);
            float eased = Easing.SINE_IN_OUT.ease(delta);
            float scale = 0.016F - (1 - eased) * 0.004f;
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.75f, 0.55f);
            poseStack.mulPose(minecraft.getEntityRenderDispatcher().cameraOrientation());
            poseStack.scale(scale, -scale, -scale);
            var base = Component.translatable(langKey).append(Component.translatable("malum.waveform_artifice.value_display", blockEntityIn.frequency, blockEntityIn.type.getText(blockEntityIn)));
            MutableComponent textComponent = base.copy().withStyle(ChatFormatting.RED);
            MutableComponent outlineComponent = base.withStyle(ChatFormatting.DARK_RED);

            float f = (-font.width(textComponent) / 2f);
            float xPos = 0 + f;
            Matrix4f pose = poseStack.last().pose();
            float alpha = 0.38f * eased; //We're rendering in additive, so whether we change alpha or the color it doesn't really matter.
            //Minecraft for whatever reason treats alpha values between 0-3 as 255 when rendering text, so we're going to modify color instead of alpha
            int color = ColorHelper.getColor(1, 1, 1, alpha);
            if (alpha > 0.02f) {
                renderText(textComponent, xPos, 0, color, pose);
            }
            alpha = 0.18f * eased;
            if (alpha > 0.02f) {
                color = ColorHelper.getColor(1, 1, 1, alpha);
                renderText(textComponent, xPos - 0.5f, 0, color, pose);
                renderText(textComponent, xPos - 0.5f, 0, color, pose);
                renderText(textComponent, xPos, 0.5f, color, pose);
                renderText(textComponent, xPos, -0.5f, color, pose);
            }
            alpha = 0.12f * eased;
            if (alpha > 0.02f) {
                color = ColorHelper.getColor(1, 1, 1, alpha);
                renderText(textComponent, xPos - 1, 0, color, pose);
                renderText(outlineComponent, xPos + 1, 0, color, pose);
                renderText(outlineComponent, xPos, 1, color, pose);
                renderText(textComponent, xPos, -1, color, pose);
                renderText(outlineComponent, xPos - 0.5f, -0.5f, color, pose);
                renderText(textComponent, xPos - 0.5f, 0.5f, color, pose);
                renderText(outlineComponent, xPos + 0.5f, 0.5f, color, pose);
                renderText(textComponent, xPos + 0.5f, -0.5f, color, pose);
            }
            poseStack.popPose();
        }
    }

    public static void renderText(MutableComponent component, float xPos, float yPos, int color, Matrix4f pose) {
        var font = Minecraft.getInstance().font;
        font.drawInBatch(component, xPos, yPos, color, false, pose, TEXT, Font.DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT);
    }

    public RenderTypeToken getTokenForSide(BlockState state, Direction direction) {
        Direction facing = state.getValue(WaveformDiodeBlock.FACING);
        if (direction.equals(facing.getOpposite())) {
            return MalumRenderTypeTokens.DIODE_INPUT;
        }
        if (direction.equals(facing)) {
            return output;
        }
        return MalumRenderTypeTokens.DIODE_LOCKED;
    }
}
