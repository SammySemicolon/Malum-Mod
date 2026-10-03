package com.sammy.malum.client.renderer.entity.activator;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.activator.vindictive_brand.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.builder.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.trail.*;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

import java.awt.*;
import java.util.List;

public class ResentmentRitualActivatorRenderer extends EntityRenderer<ResentmentRitualActivator> {

   private static final Color RESENTMENT_BRIGHT = new Color(220, 203, 255);
   private static final Color RESENTMENT_DARK = new Color(132, 82, 227);
   private static final Color RESENTMENT_DARKEST = new Color(78, 49, 128);

   public ResentmentRitualActivatorRenderer(EntityRendererProvider.Context pContext) {
      super(pContext);
   }

   @Override
   public void render(ResentmentRitualActivator entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
      float delta = entity.getVisualEffectScalar();
      var additive = LodestoneRenderTypes.ADDITIVE_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL);
      var transparent = LodestoneRenderTypes.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL).addUniformData(UniformData.LUMITRANSPARENT);
      var trails = List.of(entity.trail, entity.longTrail);

      var builder = VFXBuilders.createWorld().setRenderType(additive);
      for (TrailPointBuilder trail : trails) {
         RenderUtils.renderEntityTrail(poseStack, builder, trail, entity, RESENTMENT_BRIGHT, RESENTMENT_DARK, delta * 0.3f, delta * 0.3f, partialTicks);
      }
      builder.setRenderType(transparent);
      for (TrailPointBuilder trail : trails) {
         RenderUtils.renderEntityTrail(poseStack, builder, trail, entity, RESENTMENT_BRIGHT, RESENTMENT_DARKEST, delta * 0.5f, delta * 2f, partialTicks);
         RenderUtils.renderEntityTrail(poseStack, builder, trail, entity, RESENTMENT_DARK, RESENTMENT_DARKEST, delta * 0.8f, delta * 3f, partialTicks);
      }
      super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
   }

   @Override
   public ResourceLocation getTextureLocation(ResentmentRitualActivator entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}