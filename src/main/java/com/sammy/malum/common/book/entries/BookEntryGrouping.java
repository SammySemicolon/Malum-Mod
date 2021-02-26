package com.sammy.malum.common.book.entries;

import java.util.ArrayList;

public class BookEntryGrouping
{
    public ArrayList<BookEntry> entries = new ArrayList<>();
    public boolean isFirst;
    public BookEntryGrouping(boolean isFirst) {
        this.isFirst = isFirst;
    }
    
    
}