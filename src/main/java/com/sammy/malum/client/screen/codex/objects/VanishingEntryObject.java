package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;


public class VanishingEntryObject extends EntryObject {
    public VanishingEntryObject(AbstractProgressionCodexScreen screen, BookEntry entry, int posX, int posY) {
        super(screen, entry, posX, posY);
    }

    @Override
    public void exit() {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.playNotifySound(SoundRegistry.THE_DEEP_BECKONS.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        screen.bookObjects.remove(this);
    }
}