package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.stream.Collectors;

public class CyclingPage extends BookPage {
    public final List<? extends BookPage> pages;

    public CyclingPage(BookPage... pages) {
        this(List.of(pages));
    }

    public CyclingPage(List<? extends BookPage> pages) {
        super(null);
        this.pages = pages.stream().filter(BookPage::isValid).collect(Collectors.toList());
    }

    @Override
    public ResourceLocation getBackground(boolean isRightSide) {
        int index = getIndex();
        return pages.get(index).getBackground(isRightSide);
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        int index = getIndex();
        pages.get(index).render(screen, guiGraphics, left, top, mouseX, mouseY, partialTicks, isRepeat);
    }

    public int getIndex() {
        return (int) (Minecraft.getInstance().level.getGameTime() % (20L * pages.size()) / 20);
    }
}