package com.sammy.malum.core.mod_content;

import com.sammy.malum.client.screen.old_book.categories.BookCategory;
import com.sammy.malum.client.screen.old_book.categories.DarkArtsCategory;
import com.sammy.malum.client.screen.old_book.categories.DiscoveryCategory;
import com.sammy.malum.client.screen.old_book.categories.SpiritTinkeringCategory;

import java.util.ArrayList;

public class MalumBookCategories
{
    public static ArrayList<BookCategory> CATEGORIES;
    public static void init()
    {
        CATEGORIES = new ArrayList<>();
        CATEGORIES.add(new DiscoveryCategory());
        CATEGORIES.add(new DarkArtsCategory());
        CATEGORIES.add(new SpiritTinkeringCategory());
//        CATEGORIES.add(new TinkeringCategory());
    }
    
}
