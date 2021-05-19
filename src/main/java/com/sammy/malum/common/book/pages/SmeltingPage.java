package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.EntryObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SmeltingPage extends BookPage
{
    public ItemStack result;
    public ItemStack input;
    
    public SmeltingPage(ItemStack result, ItemStack input)
    {
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/smelting.png");
        this.result = result;
        this.input = input;
    }
    public SmeltingPage(Item result, Item input)
    {
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/smelting.png");
        this.result = result.getDefaultInstance();
        this.input = input.getDefaultInstance();
    }
    
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        super.draw(stack, object, screen, mouseX, mouseY, guiLeft, guiTop, isSecondPage);
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        int posX = position.first;
        int posY = position.second;
        screen.drawItem(stack, result, posX+56, posY+48, mouseX, mouseY);
        screen.drawItem(stack, input, posX+56, posY+115, mouseX, mouseY);
    }
}