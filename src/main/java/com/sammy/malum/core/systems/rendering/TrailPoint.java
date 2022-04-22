package com.sammy.malum.core.systems.rendering;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class TrailPoint {

    public final float xp;
    public final float xn;
    public final float yp;
    public final float yn;
    public final float z;

    public TrailPoint(float xp, float xn, float yp, float yn, float z) {
        this.xp = xp;
        this.xn = xn;
        this.yp = yp;
        this.yn = yn;
        this.z = z;
    }

    public void renderStart(VertexConsumer builder, int packedLight, float r, float g, float b, float a, float u0, float v0, float u1, float v1) {
        builder.vertex(xp, yp, z).color(r, g, b, a).uv(u0, v0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).endVertex();
        builder.vertex(xn, yn, z).color(r, g, b, a).uv(u1, v0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).endVertex();
    }

    public void renderEnd(VertexConsumer builder, int packedLight, float r, float g, float b, float a, float u0, float v0, float u1, float v1) {
        builder.vertex(xn, yn, z).color(r, g, b, a).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).endVertex();
        builder.vertex(xp, yp, z).color(r, g, b, a).uv(u0, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).endVertex();
    }
}