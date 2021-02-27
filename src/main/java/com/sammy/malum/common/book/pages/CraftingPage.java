package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.BookObject;
import com.sammy.malum.common.book.objects.CategoryObject;
import com.sammy.malum.common.book.objects.EntryObject;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static com.sammy.malum.common.book.BookScreen.drawWrappingText;
import static com.sammy.malum.common.book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class CraftingPage extends BookPage
{
    public ItemStack result;
    public ItemStack[] inputs;
    
    public CraftingPage(ItemStack result, ItemStack... inputs)
    {
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/crafting.png");
        this.result = result;
        this.inputs = inputs;
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
                    int itemPosX = posX + 23 + j*22;
                    int itemPosY = posY + 19 + i*22;
                    screen.drawItem(stack, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }
        screen.drawItem(stack, result, posX+45, posY+108, mouseX, mouseY);
        super.draw(stack, object, screen, mouseX, mouseY, guiLeft, guiTop, isSecondPage);
    }
}