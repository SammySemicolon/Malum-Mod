package com.sammy.malum.client.renderer.entity.bolt;

import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.entity.nitrate.*;
import net.minecraft.client.renderer.entity.*;

public class AuricFlameBoltEntityRenderer extends AbstractBoltEntityRenderer<AuricFlameBoltEntity> {
    public AuricFlameBoltEntityRenderer(EntityRendererProvider.Context context) {
        super(context, EthericNitrateEntity.AURIC_YELLOW, EthericNitrateEntity.AURIC_PURPLE);
    }
}
