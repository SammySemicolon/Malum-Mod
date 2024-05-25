package com.sammy.malum.client.renderer.entity.bolt;

import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

public class DrainingBoltEntityRenderer extends AbstractBoltEntityRenderer<DrainingBoltEntity> {

    private static final RenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE_TRIANGLE.apply(new RenderTypeToken("malum", LIGHT_TRAIL), ShaderUniformHandler.LUMITRANSPARENT);

    public DrainingBoltEntityRenderer(EntityRendererProvider.Context context) {
        super(context, ErosionScepterItem.MALIGNANT_PURPLE, ErosionScepterItem.MALIGNANT_BLACK);
    }

    @Override
    public RenderType getTrailRenderType() {
        return TRAIL_TYPE;
    }

    @Override
    public float getAlphaMultiplier() {
        return 1.5f;
    }
}
