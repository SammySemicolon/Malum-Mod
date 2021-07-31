package com.sammy.malum.client.screen.old_book.entries;

import java.util.ArrayList;

public class BookEntryGrouping
{
    public ArrayList<BookEntry> entries = new ArrayList<>();
    public boolean isFirst;
    public BookEntryGrouping(boolean isFirst) {
        this.isFirst = isFirst;
    }
    
    
}