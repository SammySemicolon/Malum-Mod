package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;

import java.util.*;
import java.util.stream.*;

public class CyclingPage extends BookPage {
    public final List<? extends BookPage> pages;

    public CyclingPage(BookPage... pages) {
        this(List.of(pages));
    }

    public CyclingPage(List<? extends BookPage> pages) {
        this.pages = pages.stream().filter(BookPage::isValid).collect(Collectors.toList());
    }

    @Override
    public ResourceLocation getBackground() {
        var page = getCurrentPage();
        if (page != null) {
            return page.getBackground();
        }
        return null;
    }


    @Override
    public void tick(CodexEntryScreen screen, int left, int top, boolean isRepeat) {
        var page = getCurrentPage();
        if (page != null) {
            page.tick(screen, left, top, isRepeat);
        }
    }

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        var page = getCurrentPage();
        if (page != null) {
            page.render(screen, guiGraphics, left, top, mouseX, mouseY, partialTicks, isRepeat);
        }
    }

    public BookPage getCurrentPage() {
        if (pages.isEmpty()) {
            return null;
        }
        int index = getIndex();
        return pages.get(index);
    }

    public int getIndex() {
        return (int) (Minecraft.getInstance().level.getGameTime() % (20L * pages.size()) / 20);
    }
}