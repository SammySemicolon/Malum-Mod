package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.common.item.IVoidItem;

import java.util.function.*;

public class CurioWatcherNecklace extends MalumCurioItem implements IVoidItem {
    public CurioWatcherNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.curio.effect.necklace_of_the_watcher"));
    }
}