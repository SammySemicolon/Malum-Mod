package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import com.sammy.malum.client.screen.codex.handlers.BookObjectHandler;
import com.sammy.malum.client.screen.codex.objects.button.PageSelectionObject;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.*;
import com.sammy.malum.client.screen.codex.screens.CodexEntryScreen;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

import static com.sammy.malum.client.screen.codex.display.gizmo.DisplayedItem.item;
import static com.sammy.malum.client.screen.codex.pages.text.HeadlineTextPage.headlineText;

public class PageSelectionPage extends CyclingPage {

    protected final List<DisplayedGizmo> displays;
    protected final String id;

    protected int index;

    public static PageSelectionPage create(String id, Consumer<PageSelectionBuilder> builder) {
        var result = new PageSelectionBuilder();
        builder.accept(result);
        return new PageSelectionPage(id, result);
    }

    public PageSelectionPage(String id, PageSelectionBuilder builder) {
        super(builder.data.stream().map(Selection::page).toList());
        this.id = id;
        this.displays = builder.data.stream().map(Selection::gizmo).toList();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public String getId() {
        return id;
    }

    @Override
    public BookObjectHandler<CodexEntryScreen> createBookObjects(CodexEntryScreen screen, int left, int top) {
        BookObjectHandler<CodexEntryScreen> handler = new BookObjectHandler<>();

        int total = pages.size();
        int step = 30;
        int objectStart = getPageMiddle(0) - (total * step) / 2;
        int objectTop = Mth.floor(CodexEntryScreen.PAGE_HEIGHT * 0.95f);
        for (int i = 0; i < pages.size(); i++) {
            int objectLeft = objectStart + i * step;
            handler.add(new PageSelectionObject(this, displays.get(i), i, objectLeft, objectTop));
        }
        return handler;
    }

    public record Selection(BookPage page, DisplayedGizmo gizmo) {


    }

    public static class PageSelectionBuilder {
        protected final List<Selection> data = new ArrayList<>();

        public PageSelectionBuilder addHeadline(DisplayedGizmo display, String text) {
            return add(display, headlineText(text));
        }

        public PageSelectionBuilder addSmelting(DisplayedGizmo input, DisplayedGizmo output) {
            return addTwoPiece(input, output, SmeltingPage::new);
        }

        public PageSelectionBuilder addCompacting(DisplayedGizmo input, DisplayedGizmo output) {
            return addTwoPiece(input, output, CraftingPage::compacting);
        }

        public PageSelectionBuilder addTwoPiece(DisplayedGizmo input, DisplayedGizmo output, BiFunction<DisplayedGizmo, DisplayedGizmo, ? extends BookPage> supplier) {
            return add(output, supplier.apply(input, output));
        }

        public PageSelectionBuilder add(DisplayedGizmo display, BookPage page) {
            data.add(new Selection(page, display));
            return this;
        }

        public PageSelectionBuilder add(DisplayedGizmo display, Function<DisplayedGizmo, BookPage> page) {
            data.add(new Selection(page.apply(display), display));
            return this;
        }
    }
}
