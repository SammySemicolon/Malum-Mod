package com.sammy.malum.client.renderer.entity.cultist.altar;

import com.sammy.malum.client.renderer.entity.AbstractBoltEntityRenderer;
import com.sammy.malum.common.entity.mob.cultist.altar.projectile.CultistBlessingProjectile;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.rendering.rendeertype.LodestoneRenderTypeBuilder;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

public class CultistBlessingRenderer extends AbstractBoltEntityRenderer<CultistBlessingProjectile> {
    public CultistBlessingRenderer(EntityRendererProvider.Context context) {
        super(context, CultistBlessingProjectile.CULTIST_PINK, CultistBlessingProjectile.CULTIST_PURPLE);
    }

    @Override
    public LodestoneRenderTypeBuilder getTrailRenderType(boolean isTransparent) {
        return LodestoneRenderTypes.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL).addUniformData(UniformData.LUMITRANSPARENT);
    }

    @Override
    public float getScaleMultiplier() {
        return 0.5f;
    }
}
