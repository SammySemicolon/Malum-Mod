package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;

import java.util.*;
import java.util.stream.*;

public class CyclingPage<T extends EntryScreen<T, K>, K extends AbstractMalumScreen<K>> extends BookPage<T> {
    public final List<? extends BookPage<T>> pages;

    @SafeVarargs
    public CyclingPage(BookPage<T>... pages) {
        this(List.of(pages));
    }

    public CyclingPage(List<? extends BookPage<T>> pages) {
        super(null);
        this.pages = pages.stream().filter(BookPage::isValid).collect(Collectors.toList());
    }

    @Override
    public ResourceLocation getBackground(boolean isRightSide) {
        int index = getIndex();
        return pages.get(index).getBackground(isRightSide);
    }

    @Override
    public void render(T screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        int index = getIndex();
        pages.get(index).render(screen, guiGraphics, left, top, mouseX, mouseY, partialTicks, isRepeat);
    }

    public int getIndex() {
        return (int) (Minecraft.getInstance().level.getGameTime() % (20L * pages.size()) / 20);
    }
}