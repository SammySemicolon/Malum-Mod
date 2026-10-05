package com.sammy.malum.client.renderer.block.banner;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.common.block.building.banner.fancy.FancyBannerBlockEntity;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Vector3f;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.rendering.builder.WorldVFXBuilder;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;


public class FancyBannerRenderer extends MalumBannerRenderer<FancyBannerBlockEntity> {
    public FancyBannerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void renderBanner(FancyBannerBlockEntity blockEntity, PoseStack poseStack, WorldVFXBuilder builder, Vector3f[] vertices) {
        var key = getBlockId(blockEntity);
        var token = RenderTypeToken.createToken(key.withPath(s -> "textures/block/building/wool/" + s + ".png"));
        var banner = LodestoneRenderTypes.CUTOUT_TEXTURE.apply(token).addModifier(b -> b.setCullState(RenderStateShard.NO_CULL));
        builder.setRenderType(banner).renderQuad(poseStack, vertices);
    }
}
