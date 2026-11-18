package com.sammy.malum.client.screen.codex.screens;

import net.minecraft.client.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;

import java.util.*;
import java.util.function.*;

@SuppressWarnings("DataFlowIssue")
public abstract class AbstractMalumCodexScreen extends Screen {

    protected final Holder<SoundEvent> sweetenerSound;

    protected List<Runnable> lateRendering = new ArrayList<>();
    protected List<Component> tooltip = new ArrayList<>();

    protected boolean isVoidTouched;

    protected AbstractMalumCodexScreen(Component pTitle, Holder<SoundEvent> sweetenerSound) {
        super(pTitle);
        //Early Instantiation for access in constructor
        this.minecraft = Minecraft.getInstance();
        this.sweetenerSound = sweetenerSound;
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

    public boolean isHovering(double mouseX, double mouseY, float posX, float posY, int width, int height) {
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }

    public void playPageFlipSound(Holder<SoundEvent> soundEvent, float pitch) {
        playSound(soundEvent, 1f, Math.max(1, pitch * 0.8f));
        playSound(sweetenerSound, 0.35f, pitch);
    }

    public void playSweetenedSound(Holder<SoundEvent> soundEvent, float sweetenerPitch) {
        playSweetenedSound(soundEvent, 1f, sweetenerPitch);
    }

    public void playSweetenedSound(Holder<SoundEvent> soundEvent, float sweetenerVolume, float sweetenerPitch) {
        playSound(soundEvent, 1f, 1);
        playSound(sweetenerSound, sweetenerVolume, sweetenerPitch);
    }

    public void playSound(Holder<SoundEvent> soundEvent, float volume, float pitch) {
        minecraft.player.playNotifySound(soundEvent.value(), SoundSource.PLAYERS, volume, pitch);
    }

    public void renderTooltip(List<Component> tooltip) {
        this.tooltip = new ArrayList<>(tooltip);
    }

    public void doLateRendering(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        lateRendering.forEach(Runnable::run);
        lateRendering.clear();

        guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, tooltip, mouseX, mouseY);
        tooltip.clear();
    }

    public void setVoidTouched(boolean isVoidTouched) {
        this.isVoidTouched = isVoidTouched;
    }

    public boolean isVoidTouched() {
        return isVoidTouched;
    }
}