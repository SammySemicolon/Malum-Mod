package com.sammy.malum.client.renderer.block.banner;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.*;
import com.sammy.malum.client.renderer.texture.*;
import com.sammy.malum.client.renderer.texture.request.*;
import com.sammy.malum.client.texture.*;
import com.sammy.malum.common.block.building.banner.fancy.FancyBannerBlockEntity;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.*;
import org.joml.Vector3f;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.builder.WorldVFXBuilder;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

public class FancyBannerRenderer extends MalumBannerRenderer<FancyBannerBlockEntity> {
    public FancyBannerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void renderBanner(FancyBannerBlockEntity blockEntity, PoseStack poseStack, WorldVFXBuilder builder, Vector3f[] vertices) {
        var color = blockEntity.color;
        var texture = MalumMod.malumPath("textures/block/banners/alchemized_banner.png");
        if (color != DyeColor.WHITE) {
            var output = texture.withPath(s -> s.replace(".png", "_" + color.getName() + ".png"));
            var request = VFXBuilderTextureRenderRequest.paletteSwap(output, TextureLoaderReloadListener.DATA.woolColors, color)
                    .setDrawnTexture(texture);

            var dynamicTexture = DynamicTextureRenderer.create(request)
                    .setTextureSize(16, 32)
                    .bakeTexture();
            if (dynamicTexture == null) {
                return;
            }
            texture = request.getWriteLocation();
        }
        var token = RenderTypeToken.createToken(texture);
        var banner = LodestoneRenderTypes.CUTOUT_TEXTURE.apply(token).addModifier(b -> b.setCullState(RenderStateShard.NO_CULL));
        builder.setRenderType(banner).renderQuad(poseStack, vertices);
    }


}
