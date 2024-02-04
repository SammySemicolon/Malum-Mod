package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.core.systems.ritual.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritRitualTextPage extends BookPage {
    public final MalumRitualType ritualType;
    private final String translationKey;

    public SpiritRitualTextPage(MalumRitualType ritualType, String translationKey) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_rite_page.png"));
        this.ritualType = ritualType;
        this.translationKey = translationKey;
    }

    public String headlineTranslationKey() {
        return ritualType.translationIdentifier();
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderWrappingText(guiGraphics, translationKey(), guiLeft + 16, guiTop + 84, 126);
        final int riteIconX = guiLeft + 67;
        final int riteIconY = guiTop + 44;
        renderRitualIcon(ritualType, guiGraphics.pose(), false, 0.35f, riteIconX, riteIconY);
        if (screen.isHovering(mouseX, mouseY, riteIconX, riteIconY, 16, 16)) {
            screen.renderLate(()->guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, ritualType.makeCodexDetailedDescriptor(), mouseX, mouseY));
        }
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 218 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderWrappingText(guiGraphics, translationKey(), guiLeft + 156, guiTop + 84, 126);
        final int riteIconX = guiLeft + 209;
        final int riteIconY = guiTop + 44;
        renderRitualIcon(ritualType, guiGraphics.pose(), false, 0.35f, riteIconX, riteIconY);
        if (screen.isHovering(mouseX, mouseY, riteIconX, riteIconY, 16, 16)) {
            screen.renderLate(()->guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, ritualType.makeCodexDetailedDescriptor(), mouseX, mouseY));
        }
    }
}
