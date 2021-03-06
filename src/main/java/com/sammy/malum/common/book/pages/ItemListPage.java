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

public class ItemListPage extends BookPage
{
    public ArrayList<ArrayList<ItemStack>> inputs = new ArrayList<>();
    
    public ItemListPage(Item... items)
    {
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/list.png");
        addList(items);
    }
    public ItemListPage addList(Item... items)
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        Arrays.stream(items).forEach(i -> stacks.add(i.getDefaultInstance()));
        inputs.add(stacks);
        return this;
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
        for (int i = 0; i < inputs.size(); i++)
        {
            ArrayList<ItemStack> stacks = inputs.get(i);
            int startingPosX = posX + 59 - (step/2)* stacks.size();
            int itemPosY = posY + 6 + 26 * i;
            for (int j = 0; j < stacks.size(); j++)
            {
                ItemStack itemStack = stacks.get(j);
                int itemPosX = startingPosX + step * j;
                minecraft.getTextureManager().bindTexture(BACKGROUND);
                blit(stack, itemPosX, itemPosY, 119, 1, 20, 21, 512, 512);
                screen.drawItem(stack, itemStack, itemPosX+2, itemPosY+2, mouseX, mouseY);
            }
        }
    }
}