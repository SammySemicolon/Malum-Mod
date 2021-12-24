package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.objects;

public class VanishingEntryObject extends EntryObject
{
    public VanishingEntryObject(BookEntry entry, int posX, int posY) {
        super(entry, posX, posY);
    }

    @Override
    public void exit() {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.level.playSound(playerEntity, playerEntity.blockPosition(), SoundRegistry.SUSPICIOUS_SOUND, SoundSource.PLAYERS, 1.0f, 1.0f);
        objects.remove(this);
    }
}
