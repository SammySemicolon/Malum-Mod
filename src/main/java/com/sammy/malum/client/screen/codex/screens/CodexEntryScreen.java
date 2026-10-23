package com.sammy.malum.client.screen.codex.screens;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.handlers.*;
import com.sammy.malum.client.screen.codex.objects.ArrowObject;
import com.sammy.malum.client.screen.codex.objects.EntryBookmarkObject;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.screens.progression.*;
import com.sammy.malum.config.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;

import javax.annotation.*;

import java.util.Optional;
import java.util.function.Consumer;

import static com.sammy.malum.client.screen.codex.helper.CodexRenderHelper.*;

@SuppressWarnings("DataFlowIssue")
public class CodexEntryScreen extends AbstractMalumCodexScreen {

    public static final ResourceLocation FRAME_TEXTURE = MalumMod.malumPath("textures/gui/book/entry_frame.png");
    public static final ResourceLocation PAPER_TEXTURE = MalumMod.malumPath("textures/gui/book/entry_paper.png");

    public static final ResourceLocation ITEM_SOCKET = MalumMod.malumPath("textures/gui/book/entry_elements/item_sockets.png");

    public static final int BOOK_WIDTH = 384;
    public static final int BOOK_HEIGHT = 304;


    public static final int PAGE_WIDTH = 142;
    public static final int PAGE_HEIGHT = 210;

    public static float textJump;

    @Nullable
    protected final AbstractMalumCodexScreen parentScreen;

    protected final BookEntry openEntry;
    protected int openPageIndex;

    protected final BookObjectHandler<CodexEntryScreen> bookmarks = new BookObjectHandler<>();
    protected final BookObjectHandler<CodexEntryScreen> codexObjects = new BookObjectHandler<>();

    protected BookObjectHandler<CodexEntryScreen> leftPageObjects;
    protected BookObjectHandler<CodexEntryScreen> rightPageObjects;

    public CodexEntryScreen(BookEntry openEntry) {
        this(null, openEntry);
    }

    public CodexEntryScreen(@Nullable AbstractMalumCodexScreen parentScreen, BookEntry openEntry) {
        super(Component.empty(), MalumSoundEvents.ARCANA_SWEETENER_NORMAL);
//        super(Component.empty(), openEntry.isVoid ? MalumSoundEvents.ARCANA_SWEETENER_EVIL : MalumSoundEvents.ARCANA_SWEETENER_NORMAL);
        this.parentScreen = parentScreen;
        this.openEntry = openEntry;
        addPageObjects();
        int inset = 20;
        int left = -inset;
        int right = BOOK_WIDTH - (38 - inset);
        bookmarks.add(new ArrowObject(left, 228, false));
        bookmarks.add(new ArrowObject(right, 228, true));

        var leftBookmarks = openEntry.leftBookmarks;
        int counter = 0;
        for (int i = 0; i < leftBookmarks.size(); i++) {
            var bookmark = leftBookmarks.get(i);
            if (bookmark.entry.shouldShow()) {
                int yPos = 28 + counter * 34;
                bookmarks.add(new EntryBookmarkObject(bookmark, yPos, false, left));
                counter++;
            }
        }
        var rightBookmarks = openEntry.rightBookmarks;
        counter = 0;
        for (int i = 0; i < rightBookmarks.size(); i++) {
            var bookmark = rightBookmarks.get(i);
            if (bookmark.entry.shouldShow()) {
                int yPos = 28 + counter * 34;
                bookmarks.add(new EntryBookmarkObject(bookmark, yPos, true, right));
                counter++;
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        var poseStack = guiGraphics.pose();
        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();

        int pageTop = getPageTop();
        int leftPageLeft = getLeftPageLeft();
        int rightPageLeft = getRightPageLeft();

        var leftPage = getLeftPage();
        var rightPage = getRightPage();

        renderTexture(FRAME_TEXTURE, poseStack, guiLeft, guiTop, 0, 0, BOOK_WIDTH, BOOK_HEIGHT);

        bookmarks.renderObjects(this, guiGraphics, guiLeft, guiTop, mouseX, mouseY, partialTicks);

        renderTexture(PAPER_TEXTURE, poseStack, guiLeft, guiTop, 0, 0, BOOK_WIDTH, BOOK_HEIGHT);

        renderPageBackground(guiGraphics, leftPage, pageTop, leftPageLeft);
        renderPageBackground(guiGraphics, rightPage, pageTop, rightPageLeft);

        codexObjects.renderObjects(this, guiGraphics, guiLeft, guiTop, mouseX, mouseY, partialTicks);
        renderPageObjects(guiGraphics, leftPage, leftPageObjects, pageTop, leftPageLeft, mouseX, mouseY, partialTicks, true);
        renderPageObjects(guiGraphics, rightPage, rightPageObjects, pageTop, rightPageLeft, mouseX, mouseY, partialTicks, true);

        renderPageContents(guiGraphics, leftPage, pageTop, leftPageLeft, mouseX, mouseY, partialTicks);
        renderPageContents(guiGraphics, rightPage, pageTop, rightPageLeft, mouseX, mouseY, partialTicks);

        bookmarks.renderObjectsLate(this, guiGraphics, mouseX, mouseY, partialTicks);
        codexObjects.renderObjectsLate(this, guiGraphics, mouseX, mouseY, partialTicks);
        renderPageObjects(guiGraphics, leftPage, leftPageObjects, pageTop, leftPageLeft, mouseX, mouseY, partialTicks, false);
        renderPageObjects(guiGraphics, rightPage, rightPageObjects, pageTop, rightPageLeft, mouseX, mouseY, partialTicks, false);
        doLateRendering(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        iterateOnAllObjects(o -> o.click(this, mouseX, mouseY));

        int pageTop = getPageTop();
        tryClick(getLeftPage(), pageTop, getLeftPageLeft(), mouseX, mouseY);
        tryClick(getRightPage(), pageTop, getRightPageLeft(), mouseX, mouseY);

        textJump += 1f;

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        iterateOnAllObjects(o -> o.release(this, mouseX, mouseY));

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void tick() {
        super.tick();
        iterateOnAllObjects(o -> o.tick(this));

        int pageTop = getPageTop();
        tickPage(getLeftPage(), pageTop, getLeftPageLeft());
        tickPage(getRightPage(), pageTop, getRightPageLeft());

        textJump = Math.max(textJump - 0.1f, 0);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (ClientConfig.SCROLL_DIRECTION.getConfigValue()) {
            scrollY = -scrollY;
        }
        if (scrollY > 0) {
            nextPage();
        } else {
            previousPage(false);
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (minecraft.options.keyRight.matches(keyCode, scanCode)) {
            nextPage();
            return true;
        } else if (minecraft.options.keyLeft.matches(keyCode, scanCode)) {
            previousPage(true);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        close(false);
    }

    public void renderPageBackground(GuiGraphics guiGraphics, BookPage page, int pageTop, int pageLeft) {
        if (page == null) {
            return;
        }
        var background = page.getBackground();
        if (background == null) {
            return;
        }
        renderTexture(background, guiGraphics.pose(), pageLeft, pageTop, 0, 0, PAGE_WIDTH, PAGE_HEIGHT);
    }

    public void renderPageContents(GuiGraphics guiGraphics, BookPage page, int pageTop, int pageLeft, int mouseX, int mouseY, float partialTicks) {
        if (page == null) {
            return;
        }
        var leftPage = getLeftPage();
        boolean isRepeat = !page.equals(leftPage) && page.getClass().equals(leftPage.getClass());
        page.render(this, guiGraphics, pageLeft, pageTop, mouseX, mouseY, partialTicks, isRepeat);
        lateRendering.add(() -> page.renderLate(this, guiGraphics, pageLeft, pageTop, mouseX, mouseY, partialTicks, isRepeat));
    }

    public void renderPageObjects(GuiGraphics guiGraphics, BookPage page, BookObjectHandler<CodexEntryScreen> objects, int pageTop, int pageLeft, int mouseX, int mouseY, float partialTicks, boolean isEarly) {
        if (page == null) {
            return;
        }
        if (objects == null) {
            return;
        }
        if (isEarly) {
            objects.renderObjects(this, guiGraphics, pageLeft, pageTop, mouseX, mouseY, partialTicks);
            return;
        }
        objects.renderObjectsLate(this, guiGraphics, mouseX, mouseY, partialTicks);
    }

    public void tickPage(BookPage page, int pageTop, int pageLeft) {
        if (page == null) {
            return;
        }
        var leftPage = getLeftPage();
        boolean isRepeat = !page.equals(leftPage) && page.getClass().equals(leftPage.getClass());
        page.tick(this, pageLeft, pageTop, isRepeat);
    }

    public void tryClick(BookPage page, int pageTop, int pageLeft, double mouseX, double mouseY) {
        if (page == null) {
            return;
        }
        int width = PAGE_WIDTH;
        int height = PAGE_HEIGHT;
        if (!isHovering(mouseX, mouseY, pageLeft, pageTop, width, height)) {
            return;
        }
        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();
        double relativeX = Mth.clamp(mouseX - guiLeft, guiLeft, guiLeft + width);
        double relativeY = Mth.clamp(mouseY - guiTop, guiTop, guiTop + height);
        page.click(this, pageLeft, pageTop, mouseX, mouseY, relativeX, relativeY);
    }

    public BookPage getLeftPage() {
        return getPage(0).orElse(null);
    }

    public BookPage getRightPage() {
        return getPage(1).orElse(null);
    }

    public Optional<BookPage> getPage(int offset) {
        int index = openPageIndex * 2 + offset;
        var pages = openEntry.pages;
        if (pages.size() >= index + 1) {
            return Optional.ofNullable(pages.get(index));
        }
        return Optional.empty();
    }

    public boolean hasNextPage() {
        return openPageIndex < openEntry.pages.size() / 2f - 1;
    }

    public void nextPage() {
        if (hasNextPage()) {
            openPageIndex += 1;
            addPageObjects();
            playPageFlipSound(MalumSoundEvents.ARCANA_PAGE_FLIP, getSweetenerPitch());
        }
    }

    public void previousPage(boolean ignore) {
        if (openPageIndex > 0) {
            openPageIndex -= 1;
            addPageObjects();
            playPageFlipSound(MalumSoundEvents.ARCANA_PAGE_FLIP, getSweetenerPitch());
        } else {
            close(ignore);
        }
    }

    public void addPageObjects() {
        var left = getLeftPage();
        var right = getRightPage();
        int pageTop = getPageTop();

        if (left != null) {
            leftPageObjects = left.createBookObjects(this, getLeftPageLeft(), pageTop);
        }
        if (right != null) {
            rightPageObjects = right.createBookObjects(this, getRightPageLeft(), pageTop);
        }
    }

    public void iterateOnAllObjects(Consumer<BookObjectHandler<CodexEntryScreen>> acceptor) {
        acceptor.accept(bookmarks);
        acceptor.accept(codexObjects);
        if (leftPageObjects != null) {
            acceptor.accept(leftPageObjects);
        }
        if (rightPageObjects != null) {
            acceptor.accept(rightPageObjects);
        }
    }

    public void close(boolean ignoreNextInput) {
        if (parentScreen == null) {
            ProgressionScreenHolder.getAppropriateCodexScreen().reopenCodexFromEntryScreen(isVoidTouched, ignoreNextInput);
        } else {
            ProgressionScreenHolder.openCodex(parentScreen, isVoidTouched, ignoreNextInput);
        }
        playSweetenedSound(MalumSoundEvents.ARCANA_ENTRY_CLOSE, 0.85f);
    }

    public static void openScreen(BookEntry bookEntry) {
        var minecraft = Minecraft.getInstance();
        CodexEntryScreen screen;
        if (minecraft.screen instanceof AbstractMalumCodexScreen openScreen) {
            screen = new CodexEntryScreen(openScreen, bookEntry);
            screen.setVoidTouched(openScreen.isVoidTouched);
        } else {
            screen = new CodexEntryScreen(bookEntry);
        }
        screen.playSweetenedSound(MalumSoundEvents.ARCANA_ENTRY_OPEN, 1.15f);
        minecraft.setScreen(screen);
    }

    public float getSweetenerPitch() {
        return 1 + (float) openPageIndex / openEntry.pages.size();
    }

    public int getPageTop() {
        int guiTop = getGuiTop();
        return guiTop + 28;
    }

    public int getLeftPageLeft() {
        int guiLeft = getGuiLeft();
        return guiLeft + 29;
    }

    public int getRightPageLeft() {
        int guiLeft = getGuiLeft();
        return guiLeft + 213;
    }

    public int getGuiLeft() {
        return (width - BOOK_WIDTH) / 2;
    }

    public int getGuiTop() {
        return (height - BOOK_HEIGHT) / 2;
    }
}