package com.sammy.malum.common.item.curiosities.nitrate;

import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;

public class EthericNitrateItem extends AbstractNitrateItem {

    public EthericNitrateItem(Properties pProperties) {
        super(pProperties, p -> new EthericNitrateEntity(p, p.level()));
    }
}