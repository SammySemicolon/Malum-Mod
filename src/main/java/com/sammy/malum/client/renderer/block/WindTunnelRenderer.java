package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.base.ElementalArtificeBlock;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial.WindTunnelBlock;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial.WindTunnelBlockEntity;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.core.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.modules.rendering.LodestoneRenderingSystem;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.builder.data.CubeVertexData;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

import static com.sammy.malum.core.handlers.WindTunnelHandler.MAX_STRENGTH;


public class WindTunnelRenderer implements BlockEntityRenderer<WindTunnelBlockEntity> {

    public WindTunnelRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public boolean shouldRenderOffScreen(WindTunnelBlockEntity blockEntity) {
        return true;
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(WindTunnelBlockEntity tunnel) {
        var pos = tunnel.getBlockPos();
        var facing = tunnel.getBlockState().getValue(WindTunnelBlock.FACING);
        int length = tunnel.getTunnelLength();
        int stepZ = facing.getStepZ();
        int stepY = facing.getStepY();
        int stepX = facing.getStepX();
        var offset = new Vec3(stepX, stepY, stepZ).scale(length);
        return new AABB(pos).expandTowards(offset);
    }

    @Override
    public void render(WindTunnelBlockEntity tunnel, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!ElementalArtificeBlock.isPowered(tunnel.getBlockState())) {
            return;
        }
        var state = tunnel.getBlockState();
        var facing = state.getValue(WindTunnelBlock.FACING);
        int tunnelLength = tunnel.getTunnelLength();
        boolean isInward = tunnel.isModified();

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        int x = facing.get2DDataValue();

        if (facing.getAxis().isHorizontal()) {
            poseStack.mulPose(Axis.YN.rotationDegrees(x * 90));
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
        } else if (facing.equals(Direction.DOWN)) {
            poseStack.mulPose(Axis.XN.rotationDegrees(180));
        } else if (facing.equals(Direction.UP)) {
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
        }
        var up = state.getValue(WindTunnelBlock.UP);
        var down = state.getValue(WindTunnelBlock.DOWN);
        var left = state.getValue(WindTunnelBlock.LEFT);
        var right = state.getValue(WindTunnelBlock.RIGHT);
        renderBorder(poseStack, up, down, left, right);

        if (up && down && left && right) {
            poseStack.popPose();
            return;
        }
        poseStack.translate(0f, 0.5f, 0);

        float xStart = -0.4f;
        float xEnd = 0.4f;
        float yStart = 0f;
        float yEnd = 1f * tunnelLength;
        float zStart = -0.4f;
        float zEnd = 0.4f;
        float offset = 0.1f;
        if (up) {
            zEnd += offset;
        }
        if (down) {
            zStart -= offset;
        }
        if (left) {
            xEnd += offset;
        }
        if (right) {
            xStart -= offset;
        }

        var windTunnelArea = CubeVertexData.makeCubePositions(xStart, xEnd, yStart, yEnd, zStart, zEnd);
        var windTunnel = LodestoneRenderTypes.TRANSPARENT_TEXTURE.apply(MalumRenderTypeTokens.WIND_TUNNEL)
                .addUniformData(UniformData.LUMITRANSPARENT)
                .addModifier(b -> b.setCullState(RenderStateShard.NO_CULL));
        var windFlow = LodestoneRenderTypes.TRANSPARENT_TEXTURE.apply(MalumRenderTypeTokens.WIND_STREAKS)
                .addUniformData(UniformData.LUMITRANSPARENT)
                .addModifier(b -> b.setCullState(RenderStateShard.NO_CULL));


        var builder = VFXBuilders.createWorld();
        for (int i = 0; i < 4; i++) {
            var isOccluded = state.getValue(WindTunnelBlock.getDirectionProperty(i));
            if (isOccluded) {
                continue;
            }
            Direction direction = Direction.from2DDataValue(i);
            float offsetDirection = (isInward ? -1f : 1f);
            for (int j = 0; j < 2; j++) {
                boolean isTunnel = j == 0;
                var renderType = isTunnel ? windTunnel : windFlow;
                float interval = isTunnel ? 60 : 15;
                float horizontalInterval = interval * 4;
                float uOffset = getOffset(tunnel, horizontalInterval, partialTicks) * offsetDirection;
                float vOffset = getOffset(tunnel, interval, partialTicks) * offsetDirection;
                float alpha = isTunnel ? 0.25f : 0.6f;
                float u0 = (isInward ? 1f : 0f) + uOffset;
                float u1 = u0 + 1f;
                float v0 = isInward ? -vOffset : vOffset;
                float v1 = v0 + tunnelLength * (isInward ? -1f : 1f);
                builder
                        .replaceBufferSource(LodestoneRenderingSystem.LATE_DEFERRED_RENDER)
                        .setAlpha(alpha)
                        .setUV(u0, v0, u1, v1)
                        .setRenderType(renderType)
                        .renderCubeSide(poseStack, windTunnelArea, direction);
            }
        }
        poseStack.popPose();
    }

    private float getOffset(WindTunnelBlockEntity tunnel, float interval, float partialTicks) {
        int tunnelLength = tunnel.getTunnelLength();
        float intensity = tunnelLength/MAX_STRENGTH;
        float rate = 1+intensity;
        double time = Minecraft.getInstance().level.getGameTime() + partialTicks;
        return (float) ((time * rate / interval) % interval);
    }

    private void renderBorder(PoseStack poseStack, boolean up, boolean down, boolean left, boolean right) {
        var border = LodestoneRenderTypes.TRANSPARENT_TEXTURE.apply(MalumRenderTypeTokens.WIND_COVERAGE_BORDER)
                .addUniformData(UniformData.LUMITRANSPARENT)
                .addModifier(b -> b.setCullState(RenderStateShard.NO_CULL));
        var uv = getBorderUV(!up, !down, !left, !right);
        var u0 = uv.x;
        var u1 = uv.x + 0.25f;
        var v0 = uv.y;
        var v1 = uv.y + 0.25f;
        var builder = VFXBuilders.createWorld();
        poseStack.pushPose();
        poseStack.translate(0f, 0.5125f, 0);
        poseStack.mulPose(Axis.XN.rotationDegrees(90));
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        builder
                .replaceBufferSource(LodestoneRenderingSystem.LATE_DEFERRED_RENDER)
                .setAlpha(0.9f)
                .setUV(u0, v0, u1, v1)
                .setRenderType(border)
                .renderQuad(poseStack, 0.65f);
        poseStack.popPose();
    }

    public Vector2f getBorderUV(boolean up, boolean down, boolean left, boolean right) {
        int u;
        int v;

        if (up && down && left && right) {
            u = 0;
            v = 3;
        } else if (up && down && left) {
            u = 1;
            v = 3;
        } else if (up && down && right) {
            u = 3;
            v = 3;
        } else if (up && left && right) {
            u = 0;
            v = 0;
        } else if (down && left && right) {
            u = 0;
            v = 2;
        } else if (up && down) {
            u = 2;
            v = 3;
        } else if (left && right) {
            u = 0;
            v = 1;
        } else if (up && left) {
            u = 1;
            v = 0;
        } else if (up && right) {
            u = 3;
            v = 0;
        } else if (down && left) {
            u = 1;
            v = 2;
        } else if (down && right) {
            u = 3;
            v = 2;
        } else if (up) {
            u = 2;
            v = 0;
        } else if (down) {
            u = 2;
            v = 2;
        } else if (left) {
            u = 1;
            v = 1;
        } else if (right) {
            u = 3;
            v = 1;
        } else {
            u = 2;
            v = 1;
        }
        return new Vector2f(u / 4f, v / 4f);
    }
}