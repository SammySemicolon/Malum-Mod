package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.CraftingPage;
import com.sammy.malum.common.book.pages.TextPage;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Items;

public class TotemMagicCategory extends BookCategory
{
    public TotemMagicCategory()
    {
        super(MalumItems.MALUM_BOOK.get().getDefaultInstance(), "discovery");
    
        BookEntry unholyBlend = new BookEntry(MalumItems.RUNEWOOD_SAPLING.get(), "unholy_blend")
                .addPage(new TextPage("unholy_blend"))
                .addPage(new CraftingPage(MalumItems.UNHOLY_BLEND.get(), Items.REDSTONE, Items.ROTTEN_FLESH, Items.SOUL_SAND));
        addEntries(unholyBlend);
    }
}
