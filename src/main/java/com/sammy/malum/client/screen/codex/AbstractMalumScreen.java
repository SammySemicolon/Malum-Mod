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

    public void playSweetenedSound(Supplier<SoundEvent> soundEvent) {
        playSound(soundEvent);
        playSound(getSweetenerSound());
    }

    public abstract Supplier<SoundEvent> getSweetenerSound();

    public void playSound(Supplier<SoundEvent> soundEvent) {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.playNotifySound(soundEvent.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
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
