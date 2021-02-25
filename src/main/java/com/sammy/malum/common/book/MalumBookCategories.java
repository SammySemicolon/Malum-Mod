package com.sammy.malum.common.book;

import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.categories.DarkArtsCategory;
import com.sammy.malum.common.book.categories.TotemMagicCategory;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

public class MalumBookCategories
{
    public static ArrayList<BookCategory> categories = new ArrayList<>();
    public static void init()
    {
        categories.add(new TotemMagicCategory());
        categories.add(new DarkArtsCategory());
    }
    
}
