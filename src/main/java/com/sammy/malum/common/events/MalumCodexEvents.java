package com.sammy.malum.common.events;

import com.sammy.malum.client.screen.codex.PlacedBookEntry;
import net.fabricmc.fabric.api.event.Event;

import java.util.List;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;

public interface MalumCodexEvents {

    Event<Setup> EVENT = createArrayBacked(Setup.class, listeners -> (entries) -> {
        for (Setup listener : listeners) {
            listener.onSetup(entries);
        }
    });

    @FunctionalInterface
    interface Setup {
        void onSetup(List<PlacedBookEntry> bookEntry);
    }
}
