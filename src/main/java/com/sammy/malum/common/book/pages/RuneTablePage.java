package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.EntryObject;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_content.MalumRuneTableRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RuneTablePage extends BookPage
{
    public final MalumRuneTableRecipes.MalumRuneTableRecipe recipe;

    public RuneTablePage(Item item)
    {
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/rune_table.png");

        MalumRuneTableRecipes.MalumRuneTableRecipe finalRecipe = null;
        for (MalumRuneTableRecipes.MalumRuneTableRecipe recipe : MalumRuneTableRecipes.RECIPES)
        {
            if (recipe.outputIngredient.getItemAlt().getItem().equals(item))
            {
                finalRecipe = recipe;
                break;
            }
        }
        if (finalRecipe == null)
        {
            MalumMod.LOGGER.info("this ain't good");
            finalRecipe = MalumRuneTableRecipes.RECIPES.get(0);
        }
        this.recipe = finalRecipe;
    }
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        super.draw(stack, object, screen, mouseX, mouseY, guiLeft, guiTop, isSecondPage);
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        int posX = position.first;
        int posY = position.second;
        int[] x = new int[]{31, 55, 81};
        for (int i = 0; i < recipe.itemIngredients.size(); i++)
        {
            ItemStack itemStack = recipe.itemIngredients.get(i).getItem();
            int itemPosX = posX + 31+ 25*i;
            int itemPosY = posY + 48;
            screen.drawItem(stack, itemStack, itemPosX, itemPosY, mouseX, mouseY);
        }
        screen.drawItem(stack, recipe.outputIngredient.getItemAlt(), posX + 56, posY + 115, mouseX, mouseY);

        screen.drawItem(stack, MalumItems.RUNE_TABLE.get().getDefaultInstance(), posX + 101, posY + 115, mouseX, mouseY);

    }
}