package com.sammy.malum.common.item.cosmetic.curios;

import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

public class CurioTopHat extends MalumTinketsItem implements IEventResponderItem {

    public CurioTopHat(Properties builder) {
        super(builder, MalumTrinketType.CLOTH);
    }
}