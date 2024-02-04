package com.sammy.malum.common.item.curiosities.nitrate;

import com.sammy.malum.common.entity.nitrate.*;

public class EthericNitrateItem extends AbstractNitrateItem {

    public EthericNitrateItem(Properties pProperties) {
        super(pProperties, p -> new EthericNitrateEntity(p, p.level()));
    }
}