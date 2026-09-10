package com.sammy.malum.client.renderer.entity.cultist.altar;

import com.sammy.malum.client.renderer.entity.AbstractBoltEntityRenderer;
import com.sammy.malum.common.entity.mob.cultist.altar.projectile.CursedBoltProjectile;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.rendering.rendeertype.LodestoneRenderTypeBuilder;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

public class CultistBoltRenderer extends AbstractBoltEntityRenderer<CursedBoltProjectile> {
    public CultistBoltRenderer(EntityRendererProvider.Context context) {
        super(context, CursedBoltProjectile.CULTIST_RED, CursedBoltProjectile.CULTIST_CRIMSON);
    }

    @Override
    public LodestoneRenderTypeBuilder getTrailRenderType(boolean isTransparent) {
        return LodestoneRenderTypes.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL).addUniformData(UniformData.LUMITRANSPARENT);
    }

    @Override
    public float getScaleMultiplier() {
        return 0.6f;
    }
}
