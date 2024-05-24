package com.sammy.malum.common.events;


/**
 * Called when the book is opened for the first time per minecraft client instance, prior to setting up book objects.
 * All the relevant book parameters are static, so you may simply edit those.
 */
public class SetupMalumCodexEntriesEvent extends Event {
    public SetupMalumCodexEntriesEvent() {
    }

    @Override
    public boolean isCancelable() {
        return false;
    }
}
