package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.EntryObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CraftingPage extends BookPage
{
    public ItemStack result;
    public ItemStack[] inputs;
    
    public CraftingPage(ItemStack result, Item... inputs)
    {
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/crafting.png");
        ItemStack[] stackInputs = new ItemStack[inputs.length];
        for (int i = 0; i < inputs.length; i++)
        {
            if (inputs[i].equals(Items.BARRIER))
            {
                stackInputs[i] = ItemStack.EMPTY;
                continue;
            }
            stackInputs[i] = inputs[i].getDefaultInstance();
        }
        this.result = result;
        this.inputs = stackInputs;
    }
    public CraftingPage(Item result, Item... inputs)
    {
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/crafting.png");
        ItemStack stackResult = result.getDefaultInstance();
        ItemStack[] stackInputs = new ItemStack[inputs.length];
        for (int i = 0; i < inputs.length; i++)
        {
            if (inputs[i].equals(Items.BARRIER))
            {
                stackInputs[i] = ItemStack.EMPTY;
                continue;
            }
            stackInputs[i] = inputs[i].getDefaultInstance();
        }
        this.result = stackResult;
        this.inputs = stackInputs;
    }
    
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        super.draw(stack, object, screen, mouseX, mouseY, guiLeft, guiTop, isSecondPage);
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        int posX = position.first;
        int posY = position.second;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                int index = i * 3 + j;
                if (index < inputs.length && !inputs[index].isEmpty())
                {
                    ItemStack itemStack = inputs[index];
                    int itemPosX = posX + 29 + j*27;
                    int itemPosY = posY + 19 + i*27;
                    screen.drawItem(stack, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }
        screen.drawItem(stack, result, posX+56, posY+114, mouseX, mouseY);
    }
    public static CraftingPage blockCraftingPage(Item output, Item input)
    {
        return new CraftingPage(output, input,input,input,input,input,input,input,input,input);
    }
    public static CraftingPage nuggetCraftingPage(Item output, Item input)
    {
        Item EMPTY = Items.BARRIER;
        return new CraftingPage(new ItemStack(output, 9), EMPTY,EMPTY,EMPTY,EMPTY,input,EMPTY,EMPTY,EMPTY,EMPTY);
    }
}