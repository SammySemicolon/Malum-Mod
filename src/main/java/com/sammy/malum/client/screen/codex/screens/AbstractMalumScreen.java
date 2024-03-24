package com.sammy.malum.client.screen.codex.screens;

import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;

import java.util.function.*;

public abstract class AbstractMalumScreen extends Screen {

    protected AbstractMalumScreen(Component pTitle) {
        super(pTitle);
    }

    public boolean isHovering(double mouseX, double mouseY, float posX, float posY, int width, int height) {
        return ArcanaCodexHelper.isHovering(mouseX, mouseY, posX, posY, width, height);
    }

    public abstract Supplier<SoundEvent> getSweetenerSound();

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void playPageFlipSound(Supplier<SoundEvent> soundEvent, float pitch) {
        playSound(soundEvent, 1f, Math.max(1, pitch * 0.8f));
        playSound(getSweetenerSound(), 1f, pitch);
    }

    public void playSweetenedSound(Supplier<SoundEvent> soundEvent, float sweetenerPitch) {
        playSound(soundEvent, 1f, 1);
        playSound(getSweetenerSound(), 1f, sweetenerPitch);
    }

    public void playSound(Supplier<SoundEvent> soundEvent, float volume, float pitch) {
        Minecraft.getInstance().player.playNotifySound(soundEvent.get(), SoundSource.PLAYERS, volume, pitch);
    }
}