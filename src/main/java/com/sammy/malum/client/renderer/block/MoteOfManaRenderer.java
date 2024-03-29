package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.mana_mote.MoteOfManaBlockEntity;
import com.sammy.malum.common.block.mana_mote.SpiritMoteBlock;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Vector3f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;


public class MoteOfManaRenderer implements BlockEntityRenderer<MoteOfManaBlockEntity> {

    public static final ResourceLocation MOTE_OF_MANA_TEXTURE = MalumMod.malumPath("textures/block/mote_of_mana.png");

    public MoteOfManaRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MoteOfManaBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderQuad(blockEntityIn, poseStack);
    }

    public void renderQuad(MoteOfManaBlockEntity blockEntityIn, PoseStack poseStack) {
        Vector3f[] bottomVertices = new Vector3f[]{new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(1, 0, 1), new Vector3f(1, 0, 0)};
        Vector3f[] topVertices = new Vector3f[]{new Vector3f(0, 1, 0), new Vector3f(0, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 0)};

        applyWobble(bottomVertices, 0f);
        applyWobble(topVertices, 0.5f);

        Collection<Vector3f[]> offsetMap = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            offsetMap.add(new Vector3f[]{bottomVertices[i], bottomVertices[(i + 1) % 4], topVertices[(i + 1) % 4], topVertices[(i) % 4]});
        }
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        MalumSpiritType spiritType = ((SpiritMoteBlock) blockEntityIn.getBlockState().getBlock()).spiritType;
        drawWobblyCube(poseStack, spiritType.getPrimaryColor(), 1f, 0.86f, offsetMap, bottomVertices, topVertices);
        drawWobblyCube(poseStack, spiritType.getSecondaryColor(), -0.92f, 0.6f, offsetMap, bottomVertices, topVertices);
        drawWobblyCube(poseStack, spiritType.getPrimaryColor(), 1.12f, 0.5f, offsetMap, bottomVertices, topVertices);
        poseStack.popPose();
    }

    public static void drawWobblyCube(PoseStack poseStack, Color color, float scale, float alpha, Collection<Vector3f[]> offsetMap, Vector3f[] bottomVertices, Vector3f[] topVertices) {
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        for (Vector3f[] offsets : offsetMap) {
            drawSide(poseStack, color, alpha, offsets);
        }
        drawSide(poseStack, color, alpha, new Vector3f[]{bottomVertices[3], bottomVertices[2], bottomVertices[1], bottomVertices[0]});
        drawSide(poseStack, color, alpha, topVertices);
        poseStack.popPose();
    }

    public static void drawSide(PoseStack poseStack, Color color, float alpha, Vector3f[] offsets) {
        VertexConsumer consumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(MOTE_OF_MANA_TEXTURE));
        VFXBuilders.createWorld()
                .setPosColorTexLightmapDefaultFormat()
                .setColor(color, alpha)
                .renderQuad(consumer, poseStack, offsets, 1f);
    }

    public static void applyWobble(Vector3f[] offsets, float initialOffset) {
        applyWobble(offsets, initialOffset, 0.015f);
    }

    public static void applyWobble(Vector3f[] offsets, float initialOffset, float strength) {
        float offset = initialOffset;
        for (Vector3f vector3f : offsets) {
            double time = ((Minecraft.getInstance().level.getGameTime() / 40.0F) % Math.PI * 2);
            float sine = Mth.sin((float) (time + (offset * Math.PI * 2))) * strength;
            vector3f.add(sine, -sine, sine);
            offset += 0.25f;
        }
    }
}