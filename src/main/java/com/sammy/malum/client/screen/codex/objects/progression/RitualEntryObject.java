package com.sammy.malum.client.screen.codex.objects.progression;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.text.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.core.systems.ritual.*;
import net.minecraft.client.gui.*;

import java.util.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class RitualEntryObject<T extends AbstractProgressionCodexScreen<T>> extends ProgressionEntryObject<T> {
    public final MalumRitualType ritualType;

    public RitualEntryObject(BookEntry<?, T> entry, int posX, int posY) {
        super(entry, posX, posY);
        Optional<SpiritRitualTextPage> page = entry.pages.stream().filter(p -> p instanceof SpiritRitualTextPage).map(p -> ((SpiritRitualTextPage) p)).findAny();
        if (page.isPresent()) {
            this.ritualType = page.get().ritualType;
        } else {
            throw new IllegalArgumentException("Entry " + entry.translationKey() + " lacks a spirit ritual page");
        }
    }

    @Override
    public void render(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(screen, guiGraphics, mouseX, mouseY, partialTicks);
        renderRitualIcon(ritualType, guiGraphics.pose(), false, 0.35f, getOffsetXPosition() + 8, getOffsetYPosition() + 8);
    }
}