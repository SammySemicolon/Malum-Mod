package com.sammy.malum.client.screen.codex.pages.text;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class WeepingWellTextPage<T extends AbstractProgressionCodexScreen<T>> extends BookPage<T> {
    private final String headlineTranslationKey;
    private final String translationKey;
    private final ItemStack stack;

    public WeepingWellTextPage(String headlineTranslationKey, String translationKey, ItemStack stack) {
        super(MalumMod.malumPath("textures/gui/book/pages/weeping_well_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
        this.stack = stack;
    }

    public WeepingWellTextPage(String headlineTranslationKey, String translationKey, Item spirit) {
        this(headlineTranslationKey, translationKey, spirit.getDefaultInstance());
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void render(EntryScreen<T> screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, left + 70 - Minecraft.getInstance().font.width(component.getString()) / 2, top + 5);
        renderWrappingText(guiGraphics, translationKey(), left + 6, top + 75, 130);
        renderItem(screen, guiGraphics, stack, left + 63, top + 38, mouseX, mouseY);
    }
}
