package com.sammy.malum.client.screen.codex;

import net.minecraft.client.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;

import java.util.function.*;

public abstract class AbstractMalumScreen extends Screen {

    protected AbstractMalumScreen(Component pTitle) {
        super(pTitle);
    }

    public abstract boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height);

    public abstract Supplier<SoundEvent> getSweetenerSound();

    public void playPageFlipSound(Supplier<SoundEvent> soundEvent, float pitch) {
        playSound(soundEvent, Math.max(1, pitch * 0.8f));
        playSound(getSweetenerSound(), pitch);
    }

    public void playSweetenedSound(Supplier<SoundEvent> soundEvent, float sweetenerPitch) {
        playSound(soundEvent);
        playSound(getSweetenerSound(), sweetenerPitch);
    }

    public void playSound(Supplier<SoundEvent> soundEvent) {
        playSound(soundEvent, 1);
    }

    public void playSound(Supplier<SoundEvent> soundEvent, float pitch) {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.playNotifySound(soundEvent.get(), SoundSource.PLAYERS, 1f, pitch);
    }

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
}