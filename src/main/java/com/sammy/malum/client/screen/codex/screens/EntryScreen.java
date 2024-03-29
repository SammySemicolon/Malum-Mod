package com.sammy.malum.client.screen.codex.screens;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.config.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class EntryScreen<T extends AbstractProgressionCodexScreen<T>> extends AbstractMalumScreen {

    public static EntryScreen<?> entryScreen;

    public static final ResourceLocation BOOK_TEXTURE = MalumMod.malumPath("textures/gui/book/entry.png");
    public static final ResourceLocation ELEMENT_SOCKET = MalumMod.malumPath("textures/gui/book/entry_elements/element_socket.png");

    public final int bookWidth = 312;
    public final int bookHeight = 200;
    public final ProgressionEntryObject<T> openObject;

    public final BookObjectHandler<EntryScreen<T>> bookObjectHandler = new BookObjectHandler<>(this);

    public List<Runnable> lateRendering = new ArrayList<>();
    public int grouping;

    public EntryScreen(ProgressionEntryObject<T> openObject) {
        super(Component.empty());
        this.openObject = openObject;
        final List<BookObject<EntryScreen<T>>> bookObjects = bookObjectHandler.getBookObjects();
        bookObjects.add(new ArrowObject<>(this, -21, 150, false));
        bookObjects.add(new ArrowObject<>(this, bookWidth - 15, 150, true));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        BookEntry<T> openEntry = openObject.entry;
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        PoseStack poseStack = guiGraphics.pose();
        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();
        renderTexture(BOOK_TEXTURE, poseStack, guiLeft, guiTop, 0, 0, bookWidth, bookHeight);

        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage<T> page = openEntry.pages.get(i);
                    final boolean isRightSide = i % 2 == 1;
                    int pageLeft = guiLeft + (isRightSide ? 161 : 9);
                    int pageTop = guiTop + 8;
                    final ResourceLocation background = page.getBackground(isRightSide);
                    if (background != null) {
                        renderTexture(background, poseStack, pageLeft, pageTop, 0, 0, 142, 172);
                    }
                }
            }
        }
        bookObjectHandler.renderObjects(guiGraphics, guiLeft, guiTop, mouseX, mouseY, partialTicks);
        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage<T> page = openEntry.pages.get(i);
                    final boolean isRightSide = i % 2 == 1;
                    int pageLeft = guiLeft + (isRightSide ? 161 : 9);
                    int pageTop = guiTop + 8;
                    boolean isRepeat = i % 2 != 0 && page.getClass().equals(openEntry.pages.get(i - 1).getClass());
                    page.render(this, guiGraphics, pageLeft, pageTop,mouseX, mouseY, partialTicks,isRepeat);
                }
            }
            lateRendering.forEach(Runnable::run);
            lateRendering.clear();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        bookObjectHandler.click(mouseX, mouseY);
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (ClientConfig.SCROLL_DIRECTION.getConfigValue()) {
            scroll = -scroll;
        }
        if (scroll > 0) {
            nextPage();
        } else {
            previousPage(false);
        }
        return super.mouseScrolled(mouseX, mouseY, scroll);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (minecraft.options.keyRight.matches(keyCode, scanCode)) {
            nextPage();
            return true;
        }
        else if (minecraft.options.keyLeft.matches(keyCode, scanCode)) {
            previousPage(false);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        close(false);
    }

    @Override
    public Supplier<SoundEvent> getSweetenerSound() {
        return openObject.screen.getSweetenerSound();
    }

    public boolean hasNextPage() {
        return grouping < openObject.entry.pages.size() / 2f - 1;
    }

    public void nextPage() {
        if (hasNextPage()) {
            grouping += 1;
            playPageFlipSound(SoundRegistry.ARCANA_PAGE_FLIP, getSweetenerPitch());
        }
    }

    public void previousPage(boolean ignore) {
        if (grouping > 0) {
            grouping -= 1;
            playPageFlipSound(SoundRegistry.ARCANA_PAGE_FLIP, getSweetenerPitch());
        } else {
            close(ignore);
        }
    }

    public void close(boolean ignoreNextInput) {
        openObject.screen.openScreen(ignoreNextInput);
        playSweetenedSound(SoundRegistry.ARCANA_ENTRY_CLOSE, 0.85f);
        openObject.exit();
    }

    public static void openScreen(ProgressionEntryObject<?> progressionEntryObject) {
        entryScreen = new EntryScreen<>(progressionEntryObject);
        entryScreen.playSweetenedSound(SoundRegistry.ARCANA_ENTRY_OPEN, 1.15f);
        Minecraft.getInstance().setScreen(entryScreen);
    }

    public float getSweetenerPitch() {
        return 1 + (float) grouping / openObject.entry.pages.size();
    }

    public int getGuiLeft() {
        return (width - bookWidth) / 2;
    }

    public int getGuiTop() {
        return (height - bookHeight) / 2;
    }

    public void renderLate(Runnable runnable) {
        lateRendering.add(runnable);
    }
}