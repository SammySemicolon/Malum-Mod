package com.sammy.malum.client.renderer.entity.weapon.staff;

import com.sammy.malum.client.renderer.entity.AbstractBoltEntityRenderer;
import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import net.minecraft.client.renderer.entity.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

public class DrainingBoltRenderer extends AbstractBoltEntityRenderer<DrainingBolt> {

    public DrainingBoltRenderer(EntityRendererProvider.Context context) {
        super(context, ErosionScepterItem.MALIGNANT_PURPLE, ErosionScepterItem.MALIGNANT_BLACK);
    }

    @Override
    public LodestoneRenderTypeBuilder getTrailRenderType(boolean isTransparent) {
        return LodestoneRenderTypes.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL).addUniformData(UniformData.LUMITRANSPARENT);
    }

    @Override
    public float getAlphaMultiplier() {
        return 1.8f;
    }

    @Override
    public float getScaleMultiplier() {
        return 1.6f;
    }
}
