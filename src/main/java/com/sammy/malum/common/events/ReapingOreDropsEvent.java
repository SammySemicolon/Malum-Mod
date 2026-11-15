package com.sammy.malum.common.events;

import com.sammy.malum.common.data.listener.reaping.ReapingDataReloadListener;
import com.sammy.malum.core.handlers.SoulHarvestHandler;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.event.level.BlockEvent;

import static com.sammy.malum.registry.common.MalumTags.Blocks.REAPING_ORES;
import static com.sammy.malum.registry.common.MalumTags.Items.SOUL_SHATTER_CAPABLE_WEAPON;

public class ReapingOreDropsEvent {

    public static void triggerReapingDrop(BlockEvent.BreakEvent event) {
        var player = event.getPlayer();
        var tool = player.getMainHandItem();
        var state = event.getState();

        if (!tool.is(SOUL_SHATTER_CAPABLE_WEAPON)) {
            return;
        }

        if (!state.is(REAPING_ORES)) {
            return;
        }
        RandomSource random = RandomSource.create();
        var drops = ReapingDataReloadListener.getPotentialDrops(state, random);
        SoulHarvestHandler.dropReapingDrops(player.level(), drops, player.position().add(0, player.getBbHeight() / 2f, 0));

    }
}