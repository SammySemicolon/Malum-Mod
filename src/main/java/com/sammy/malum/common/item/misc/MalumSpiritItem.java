package com.sammy.malum.common.item.misc;

import com.sammy.malum.core.systems.item.IFloatingGlowItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.world.item.Item;

import java.awt.*;

public class MalumSpiritItem extends Item implements IFloatingGlowItem {
    public MalumSpiritType type;

    public MalumSpiritItem(Properties properties, MalumSpiritType type) {
        super(properties);
        this.type = type;
    }

    @Override
    public Color getColor() {
        return type.color;
    }

    @Override
    public Color getEndColor() {
        return type.endColor;
    }

}
