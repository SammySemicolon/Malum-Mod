package com.sammy.malum.client.renderer.entity;

import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.renderer.entity.*;

import java.awt.*;

public class HexBoltEntityRenderer extends AbstractBoltEntityRenderer<HexBoltEntity> {
    public HexBoltEntityRenderer(EntityRendererProvider.Context context) {
        super(context, SpiritTypeRegistry.WICKED_SPIRIT.getPrimaryColor(), SpiritTypeRegistry.WICKED_SPIRIT.getSecondaryColor());
    }
}
