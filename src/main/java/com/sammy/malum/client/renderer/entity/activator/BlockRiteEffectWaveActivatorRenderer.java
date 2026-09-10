package com.sammy.malum.client.renderer.entity.activator;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.activator.rite.*;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.builder.data.CubeVertexData;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.trail.*;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

public class BlockRiteEffectWaveActivatorRenderer extends AbstractEffectActivatorEntityRenderer<BlockRiteEffectWaveActivator> {

    public static final RenderTypeToken FRONT = RenderTypeToken.createToken(MalumMod.malumPath("textures/vfx/rite_wave.png"));
    public static final RenderTypeToken SIDE = RenderTypeToken.createToken(MalumMod.malumPath("textures/vfx/rite_wave_side.png"));

    public BlockRiteEffectWaveActivatorRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public SpiritArcanaType getSpiritType(BlockRiteEffectWaveActivator entity) {
        return entity.getSpiritType();
    }

    @Override
    public float getScale(BlockRiteEffectWaveActivator entity, boolean longTrail) {
        return 0.5f * entity.getVisualEffectScalar();
    }

    @Override
    public float getAlpha(BlockRiteEffectWaveActivator entity, boolean longTrail) {
        return 0.45f * entity.getVisualEffectScalar() * (longTrail ? 1f : 2f);
    }

    @Override
    public TrailPointBuilder getTrail(BlockRiteEffectWaveActivator entity) {
        return entity.trail;
    }

    @Override
    public TrailPointBuilder getLongTrail(BlockRiteEffectWaveActivator entity) {
        return entity.longTrail;
    }

    @Override
    public void render(BlockRiteEffectWaveActivator entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        var spirit = entity.getSpiritType();
        var front = LodestoneRenderTypes.ADDITIVE_TEXTURE.apply(FRONT).addUniformData(UniformData.LUMITRANSPARENT);
        var side = LodestoneRenderTypes.ADDITIVE_TEXTURE.apply(SIDE).addUniformData(UniformData.LUMITRANSPARENT);
        var builder = SpiritBasedWorldVFXBuilder.create(spirit);
        var primaryColor = spirit.getPrimaryColor();


        var direction = entity.getMovementDirection();
        if (direction == null) {
            var movement = entity.getDeltaMovement();
            direction = Direction.fromDelta(Mth.sign(movement.x), Mth.sign(movement.y), Mth.sign(movement.z));
        }
        if (direction == null) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0, 0.5f, 0);
        if (direction.getAxis().isHorizontal()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));
            poseStack.mulPose(Axis.XN.rotationDegrees(90));
        } else if (direction.equals(Direction.DOWN)) {
            poseStack.mulPose(Axis.XN.rotationDegrees(180));
        }


        float ageDelta = Math.min(entity.getAge() + partialTicks, 30) / 30f;

        float length = Easing.SINE_IN_OUT.ease(ageDelta) * 4;
        float alpha = Easing.SINE_IN_OUT.ease(ageDelta);
        poseStack.translate(0, -length/2f + 0.5f, 0);
        poseStack.scale(1, length, 1);
        var cube = CubeVertexData.makeCubePositions(1.05f);
        builder.setAlpha(alpha).setColor(primaryColor)
                .setRenderType(front).renderCubeSides(poseStack, cube, Direction.UP);
        builder.setAlpha(alpha)
                .setRenderType(side).renderCubeSides(poseStack, cube, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);


        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }
}
