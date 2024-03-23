package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class CurioWatcherNecklace extends MalumCurioItem implements IVoidItem {
    public CurioWatcherNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("full_health_fake_collection"));
    }
}
