package com.sammy.malum.client.renderer.entity.nitrate;

import com.sammy.malum.common.entity.nitrate.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class EthericNitrateEntityRenderer extends AbstractNitrateEntityRenderer<EthericNitrateEntity> {

    public EthericNitrateEntityRenderer(EntityRendererProvider.Context context) {
        super(context, EthericNitrateEntity.AURIC_YELLOW, EthericNitrateEntity.AURIC_PURPLE);
    }
}
