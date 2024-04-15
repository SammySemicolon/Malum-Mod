package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;

import javax.annotation.*;

import static com.sammy.malum.client.screen.codex.screens.EntryScreen.*;

public abstract class BookPage<T extends AbstractMalumScreen<T>> {
    @Nullable
    protected final ResourceLocation background;
    protected BookEntry<T> bookEntry;

    public BookPage(@Nullable ResourceLocation background) {
        this.background = background;
    }

    public boolean isValid() {
        return true;
    }


    public void render(T screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
    }
    public void renderLate(T screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
    }

    public void click(T screen, int left, int top, double mouseX, double mouseY, double relativeMouseX, double relativeMouseY) {
    }


    public void render(Minecraft minecraft, GuiGraphics guiGraphics, T screen, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
    }

    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, T screen, int mouseX, int mouseY, float partialTicks) {

    }

    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, T screen, int mouseX, int mouseY, float partialTicks) {

    }

    public ResourceLocation getBackground(boolean isRightSide) {
        return background;
    }

    public int getGuiLeft() {
        return (entryScreen.width - entryScreen.bookWidth) / 2;
    }

    public int getGuiTop() {
        return (entryScreen.height - entryScreen.bookHeight) / 2;
    }

    public BookPage<T> setBookEntry(BookEntry<T> bookEntry) {
        this.bookEntry = bookEntry;
        return this;
    }
}