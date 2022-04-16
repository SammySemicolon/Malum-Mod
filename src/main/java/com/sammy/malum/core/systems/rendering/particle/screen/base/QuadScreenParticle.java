package com.sammy.malum.core.systems.rendering.particle.screen.base;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import static com.sammy.malum.core.helper.RenderHelper.FULL_BRIGHT;

@OnlyIn(Dist.CLIENT)
public abstract class QuadScreenParticle extends ScreenParticle {
   protected float quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;

   protected QuadScreenParticle(ClientLevel pLevel, double pX, double pY) {
      super(pLevel, pX, pY);
   }

   protected QuadScreenParticle(ClientLevel pLevel, double pX, double pY, double pXSpeed, double pYSpeed) {
      super(pLevel, pX, pY, pXSpeed, pYSpeed);
   }

   @Override
   public void render(BufferBuilder bufferBuilder) {
      float partialTicks = Minecraft.getInstance().timer.partialTick;
      float size = getQuadSize(partialTicks) * 10;
      float u0 = getU0();
      float u1 = getU1();
      float v0 = getV0();
      float v1 = getV1();
      float roll = Mth.lerp(partialTicks, this.oRoll, this.roll);
      Vector3f[] vectors = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
      Quaternion rotation = Vector3f.ZP.rotation(roll);
      for (int i = 0; i < 4; ++i) {
         Vector3f vector3f = vectors[i];
         vector3f.transform(rotation);
         vector3f.mul(size);
         vector3f.add((float) x, (float) y, 0);
      }
      /*TODO: JEI tooltips render at 400 z, while the held by mouse item stack renders at around 380, we need a value between to be above the stack, but below JEI tooltips.
         There is definitely a better way of doing this.
       */
      int z = 390;
      bufferBuilder.vertex(vectors[0].x(), vectors[0].y(), z).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(FULL_BRIGHT).endVertex();
      bufferBuilder.vertex(vectors[1].x(), vectors[1].y(), z).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(FULL_BRIGHT).endVertex();
      bufferBuilder.vertex(vectors[2].x(), vectors[2].y(), z).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(FULL_BRIGHT).endVertex();
      bufferBuilder.vertex(vectors[3].x(), vectors[3].y(), z).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(FULL_BRIGHT).endVertex();
   }

   public float getQuadSize(float partialTicks) {
      return this.quadSize;
   }

   protected abstract float getU0();

   protected abstract float getU1();

   protected abstract float getV0();

   protected abstract float getV1();
}