package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.stream.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

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
    public ResourceLocation getBackground() {
        int index = getIndex();
        return pages.get(index).getBackground();
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        int index = getIndex();
        pages.get(index).render(minecraft, guiGraphics, screen, mouseX, mouseY, partialTicks, isRepeat);
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int index = getIndex();
        pages.get(index).renderLeft(minecraft, guiGraphics, screen, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int index = getIndex();
        pages.get(index).renderRight(minecraft, guiGraphics, screen, mouseX, mouseY, partialTicks);
    }

    public int getIndex() {
        return (int) (Minecraft.getInstance().level.getGameTime() % (20L * pages.size()) / 20);
    }
}