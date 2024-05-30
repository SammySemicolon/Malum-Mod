package com.sammy.malum.client.renderer.entity.bolt;

import com.sammy.malum.common.entity.bolt.HexBoltEntity;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class HexBoltEntityRenderer extends AbstractBoltEntityRenderer<HexBoltEntity> {
    public HexBoltEntityRenderer(EntityRendererProvider.Context context) {
        super(context, SpiritTypeRegistry.WICKED_SPIRIT.getPrimaryColor(), SpiritTypeRegistry.WICKED_SPIRIT.getSecondaryColor());
    }
}
