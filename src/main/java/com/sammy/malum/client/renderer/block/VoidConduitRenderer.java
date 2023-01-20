package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.blockentity.VoidConduitBlockEntity;
import com.sammy.malum.common.blockentity.totem.TotemPoleBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static team.lodestar.lodestone.helpers.RenderHelper.FULL_BRIGHT;


public class VoidConduitRenderer implements BlockEntityRenderer<VoidConduitBlockEntity> {

    public static final ResourceLocation VIGNETTE = MalumMod.malumPath("textures/block/weeping_well/primordial_soup_vignette.png");

    public VoidConduitRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(VoidConduitBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderQuad(poseStack);
    }

    public void renderQuad(PoseStack poseStack) {
        float height = 0.75f;
        float width = 1.5f;
        VertexConsumer textureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(VIGNETTE));
        Vector3f[] positions = new Vector3f[]{new Vector3f(-width, height, width), new Vector3f(width, height, width), new Vector3f(width, height, -width), new Vector3f(-width, height, -width)};
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.001f, 0.5f);
        builder.renderQuad(textureConsumer, poseStack, positions, 1f);
        builder.setPosColorLightmapDefaultFormat();
        poseStack.popPose();
    }
}