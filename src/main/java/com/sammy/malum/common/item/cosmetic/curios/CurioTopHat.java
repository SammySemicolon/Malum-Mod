package com.sammy.malum.common.item.cosmetic.curios;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

public class CurioTopHat extends MalumCurioItem implements IEventResponderItem {

    public CurioTopHat(Properties builder) {
        super(builder, MalumTrinketType.CLOTH);
    }
}