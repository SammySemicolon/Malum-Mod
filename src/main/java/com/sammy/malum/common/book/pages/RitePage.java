package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.EntryObject;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

import static net.minecraft.client.gui.AbstractGui.blit;

public class RitePage extends HeadlineTextPage
{
    public ArrayList<Item> items;
    
    public RitePage(String text, Item... items)
    {
        super(text);
    
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/list.png");
        this.items = MalumHelper.toArrayList(items);
    }
    
    @Override
    public int textWidth()
    {
        return 70;
    }
    
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        super.draw(stack, object, screen, mouseX, mouseY, guiLeft, guiTop, isSecondPage);
        Minecraft minecraft = Minecraft.getInstance();
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        int posX = position.first;
        int posY = position.second;
        
        int step = 22;
        for (int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            int itemPosY = posY + 87 - (step / 2) * items.size();
            int itemPosX = posX + 87;
            minecraft.getTextureManager().bindTexture(BACKGROUND);
            blit(stack, itemPosX, itemPosY, 119, 1, 20, 21, 512, 512);
            screen.drawItem(stack, item.getDefaultInstance(), itemPosX + 2, itemPosY + 2, mouseX, mouseY);
        }
    }
}