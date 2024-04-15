package com.sammy.malum.client.screen.codex.objects.progression;

import com.sammy.malum.client.screen.codex.screens.AbstractProgressionCodexScreen;
import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;


public class VanishingEntryObject extends ProgressionEntryObject {
    public VanishingEntryObject(BookEntry entry, int posX, int posY) {
        super(entry, posX, posY);
    }

    @Override
    public void exit(AbstractProgressionCodexScreen screen) {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.playNotifySound(SoundRegistry.THE_DEEP_BECKONS.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        screen.bookObjectHandler.remove(this);
    }
}