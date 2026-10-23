package com.sammy.malum.client.screen.codex.pages;

public class PageLangKeyCommons {
    public static String headlineKey(String key) {
        return "malum.gui.book.entry." + key + ".headline";
    }

    public static String textKey(String key) {
        return "malum.gui.book.entry." + key + ".text";
    }

    public static String getRecipeInfoHeadlineKey(String recipeType) {
        return "malum.gui.book.entry.page.info." + recipeType + ".headline";
    }

    public static String getRecipeInfoKey(String recipeType) {
        return "malum.gui.book.entry.page.info." + recipeType;
    }
}
