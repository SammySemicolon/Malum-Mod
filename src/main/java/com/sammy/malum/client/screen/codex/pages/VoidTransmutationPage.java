package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

@SuppressWarnings("all")
public class VoidTransmutationPage extends BookPage {
    private final String headlineTranslationKey;
    private final String translationKey;
    private final ItemStack stack;

    public VoidTransmutationPage(String headlineTranslationKey, String translationKey, ItemStack stack) {
        super(MalumMod.malumPath("textures/gui/book/pages/weeping_well_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
        this.stack = stack;
    }

    public VoidTransmutationPage(String headlineTranslationKey, String translationKey, Item spirit) {
        this(headlineTranslationKey, translationKey, spirit.getDefaultInstance());
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
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
        renderWrappingText(guiGraphics, translationKey(), guiLeft + 14, guiTop + 76, 126);
        renderItem(screen, guiGraphics, stack, guiLeft + 67, guiTop + 44, mouseX, mouseY);
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderWrappingText(guiGraphics, translationKey(), guiLeft + 156, guiTop + 76, 126);
        renderItem(screen, guiGraphics, stack, guiLeft + 209, guiTop + 44, mouseX, mouseY);
    }
}
