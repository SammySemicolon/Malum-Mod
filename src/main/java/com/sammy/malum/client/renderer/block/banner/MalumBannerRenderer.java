package com.sammy.malum.client.renderer.block.banner;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sammy.malum.common.block.building.banner.fancy.FancyBannerBlockEntity;
import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3f;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.builder.WorldVFXBuilder;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;


public abstract class MalumBannerRenderer<T extends FancyBannerBlockEntity> implements BlockEntityRenderer<T> {

    public MalumBannerRenderer(BlockEntityRendererProvider.Context context) {
    }

    public abstract void renderBanner(T blockEntity, PoseStack poseStack, WorldVFXBuilder builder, Vector3f[] vertices);


    @Override
    public void render(T blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        var blockState = blockEntity.getBlockState();
        var pos = blockEntity.getBlockPos();

        var type = blockState.getValue(SoulwovenBannerBlock.BANNER_TYPE);
        var direction = type.direction.getAxis().isVertical() ? type.equals(SoulwovenBannerBlock.BannerType.HANGING_Z) ? Direction.NORTH : Direction.WEST : type.direction;
        float sway = ((float) Math.floorMod((pos.getX() * 7L + pos.getY() * 9L + pos.getZ() * 13L) + blockEntity.getLevel().getGameTime(), 100L) + partialTicks) / 100.0F;
        float swayRotation = (0.01F * Mth.cos((float) (Math.PI * 2) * sway)) * (float) Math.PI;

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        if (type.direction.getAxis().isHorizontal()) {
            poseStack.translate(0, -0.25f, 0.0625f);
            swayRotation -= 0.0125f;
        } else {
            poseStack.translate(0, 0, 0.5f);
            swayRotation = swayRotation - 0.0157f;
        }
        poseStack.translate(0, 1f, 0);
        poseStack.mulPose(Axis.XP.rotation(swayRotation));
        float xStart = 0;
        float xEnd = 1;
        float yStart = -2;
        float yEnd = 0;

        var vertices = new Vector3f[]{new Vector3f(xEnd, yStart, 0), new Vector3f(xStart, yStart, 0), new Vector3f(xStart, yEnd, 0), new Vector3f(xEnd, yEnd, 0)};
        var builder = VFXBuilders.createWorld().setLightLevel(pos);

        renderBanner(blockEntity, poseStack, builder, vertices);
        builder.renderQuad(poseStack, vertices);
        poseStack.popPose();
    }

    public ResourceLocation getBlockId(T blockEntity) {
        return blockEntity.getBlockState().getBlock().builtInRegistryHolder().key().location();
    }

    @Override
    public AABB getRenderBoundingBox(FancyBannerBlockEntity altar) {
        var pos = altar.getBlockPos();
        return new AABB(pos.getX(), pos.getY() - 1, pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }
}