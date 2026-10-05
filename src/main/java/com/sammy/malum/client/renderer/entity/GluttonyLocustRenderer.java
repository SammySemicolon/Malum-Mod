package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.activator.gluttony.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.trail.*;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

import java.awt.*;
import java.util.List;

public class GluttonyLocustRenderer extends EntityRenderer<GluttonyDamageActivator> {

   private static final Color GLUTTONY_GREEN = new Color(47, 81, 28);
   private static final Color GLUTTONY_DARK = new Color(31, 35, 30);
   private static final Color GLUTTONY_SHADE = new Color(14, 14, 16);

   public GluttonyLocustRenderer(EntityRendererProvider.Context pContext) {
      super(pContext);
   }

   @Override
   public void render(GluttonyDamageActivator entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
      float delta = entity.getVisualEffectScalar();
      var additive = LodestoneRenderTypes.ADDITIVE_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL);
      var transparent = LodestoneRenderTypes.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL).addUniformData(UniformData.LUMITRANSPARENT);
      var trails = List.of(entity.trail, entity.longTrail);

      var builder = VFXBuilders.createWorld().setRenderType(additive);
      for (TrailPointBuilder trail : trails) {
         RenderUtils.renderEntityTrail(poseStack, builder, trail, entity, GLUTTONY_GREEN, GLUTTONY_DARK, delta * 0.3f, delta * 0.75f, partialTicks);
      }
      builder.setRenderType(transparent);
      for (TrailPointBuilder trail : trails) {
         RenderUtils.renderEntityTrail(poseStack, builder, trail, entity, GLUTTONY_GREEN, GLUTTONY_SHADE, delta * 0.35f, delta * 2f, partialTicks);
         RenderUtils.renderEntityTrail(poseStack, builder, trail, entity, GLUTTONY_DARK, GLUTTONY_SHADE, delta * 0.6f, delta * 3f, partialTicks);
      }
      super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
   }

   @Override
   public ResourceLocation getTextureLocation(GluttonyDamageActivator entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}