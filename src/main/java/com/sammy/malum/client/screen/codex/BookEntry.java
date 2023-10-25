package com.sammy.malum.client.screen.codex;

import com.sammy.malum.client.screen.codex.objects.EntryObject;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public class BookEntry {
    public final ItemStack iconStack;
    public final String identifier;
    public final int xOffset;
    public final int yOffset;
    public List<BookPage> pages = new ArrayList<>();
    public EntryObjectSupplier objectSupplier = EntryObject::new;

    public boolean isSoulwood;
    public boolean isDark;

    public BookEntry(String identifier, int xOffset, int yOffset) {
        this.iconStack = null;
        this.identifier = identifier;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public BookEntry(String identifier, Item item, int xOffset, int yOffset) {
        this.iconStack = item.getDefaultInstance();
        this.identifier = identifier;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public String translationKey() {
        return "malum.gui.book.entry." + identifier;
    }

    public String descriptionTranslationKey() {
        return "malum.gui.book.entry." + identifier + ".description";
    }

    public BookEntry setSoulwood() {
        isSoulwood = true;
        return this;
    }

    public BookEntry setDark() {
        isDark = true;
        return this;
    }

    public BookEntry addPage(BookPage page) {
        if (page.isValid()) {
            pages.add(page.setParentEntry(this));
        }
        return this;
    }

    public BookEntry addModCompatPage(BookPage page, String modId) {
        if (ModList.get().isLoaded(modId)) {
            addPage(page);
        }
        return this;
    }

    public BookEntry setObjectSupplier(EntryObjectSupplier objectSupplier) {
        this.objectSupplier = objectSupplier;
        return this;
    }

    public interface EntryObjectSupplier {
        EntryObject getBookObject(AbstractProgressionCodexScreen screen, BookEntry entry, int x, int y);
    }
}