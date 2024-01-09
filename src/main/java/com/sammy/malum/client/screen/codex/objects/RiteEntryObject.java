package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.AbstractProgressionCodexScreen;
import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.EntryScreen;
import com.sammy.malum.client.screen.codex.pages.SpiritRiteTextPage;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.Optional;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;
import static com.sammy.malum.client.screen.codex.ArcanaProgressionScreen.FRAME_FADE_TEXTURE;
import static com.sammy.malum.client.screen.codex.ArcanaProgressionScreen.FRAME_TEXTURE;

public class RiteEntryObject extends EntryObject {
    public final MalumRiteType riteType;

    public RiteEntryObject(AbstractProgressionCodexScreen screen, BookEntry entry, int posX, int posY) {
        super(screen, entry, posX, posY);
        Optional<SpiritRiteTextPage> page = entry.pages.stream().filter(p -> p instanceof SpiritRiteTextPage).map(p -> ((SpiritRiteTextPage) p)).findAny();
        if (page.isPresent()) {
            this.riteType = page.get().riteType;
        } else {
            throw new IllegalArgumentException("Entry " + entry.translationKey() + " lacks a spirit rite page");
        }
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        super.render(minecraft, guiGraphics, xOffset, yOffset, mouseX, mouseY, partialTicks);
        renderRiteIcon(riteType, guiGraphics.pose(), isCorrupted(), 0.4f, offsetPosX(xOffset) + 8, offsetPosY(yOffset) + 8);
    }

    public boolean isCorrupted() {
        return entry.identifier.contains("corrupt");
    }
}