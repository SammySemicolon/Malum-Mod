package com.sammy.malum.client.screen.container.coffer;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.sound.MalumSoundEvents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;

public class CofferPinButton extends Button {

    private static final ResourceLocation PIN_OFF = MalumMod.malumPath("textures/gui/container/coffer_pin_disabled.png");
    private static final ResourceLocation PIN_ON = MalumMod.malumPath("textures/gui/container/coffer_pin_enabled.png");

    private final CofferContainerScreen cofferContainerScreen;
    private final int row;

    protected CofferPinButton(CofferContainerScreen cofferContainerScreen, int x, int y, int row, OnPress press) {
        super(x, y, 15, 18, CommonComponents.EMPTY, press, DEFAULT_NARRATION);
        this.cofferContainerScreen = cofferContainerScreen;
        this.row = row;
    }

    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        boolean pin = cofferContainerScreen.container.checkPin(row);
        var texture = pin ? PIN_ON : PIN_OFF;

        int xOffset = -2;
        int yOffset = 1;

        if (pin) {
            xOffset += 4;
        }
        if (isHovered) {
            yOffset -= 1;
        }
        for (int i = 0; i < row; i++)
            if (cofferContainerScreen.container.checkPin(i))
                yOffset += 2;

        guiGraphics.blit(texture, getX() + xOffset, getY() + yOffset, 0, 0, 14, 9, 14, 9);
    }

    public void playDownSound(SoundManager handler) {
        handler.play(SimpleSoundInstance.forUI(MalumSoundEvents.ARCANA_BUTTON_CLICK, 1.0F));
    }
}
