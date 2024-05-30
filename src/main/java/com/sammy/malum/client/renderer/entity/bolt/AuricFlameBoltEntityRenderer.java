package com.sammy.malum.client.renderer.entity.bolt;

import com.sammy.malum.common.entity.bolt.AuricFlameBoltEntity;
import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AuricFlameBoltEntityRenderer extends AbstractBoltEntityRenderer<AuricFlameBoltEntity> {
    public AuricFlameBoltEntityRenderer(EntityRendererProvider.Context context) {
        super(context, EthericNitrateEntity.AURIC_YELLOW, EthericNitrateEntity.AURIC_PURPLE);
    }
}
