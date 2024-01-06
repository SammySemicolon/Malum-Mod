package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.core.systems.ritual.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

import java.util.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;
import static com.sammy.malum.client.screen.codex.ArcanaProgressionScreen.*;

public class RitualEntryObject extends EntryObject {
    public final MalumRitualType ritualType;

    public RitualEntryObject(AbstractProgressionCodexScreen screen, BookEntry entry, int posX, int posY) {
        super(screen, entry.setDark(), posX, posY);
        Optional<SpiritRitualTextPage> page = entry.pages.stream().filter(p -> p instanceof SpiritRitualTextPage).map(p -> ((SpiritRitualTextPage) p)).findAny();
        if (page.isPresent()) {
            this.ritualType = page.get().ritualType;
        } else {
            throw new IllegalArgumentException("Entry " + entry.translationKey() + " lacks a spirit ritual page");
        }
    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY) {
        EntryScreen.openScreen(this);
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTransparentTexture(FADE_TEXTURE, guiGraphics.pose(), posX - 13, posY - 13, 1, 252, 58, 58, 512, 512);
        renderTexture(FRAME_TEXTURE, guiGraphics.pose(), posX, posY, 1, getFrameTextureV(), width, height, 512, 512);
        renderTexture(FRAME_TEXTURE, guiGraphics.pose(), posX, posY, 100, getBackgroundTextureV(), width, height, 512, 512);
        renderRitualIcon(ritualType, guiGraphics.pose(), false, 0.35f, posX + 8, posY + 8);
    }
}