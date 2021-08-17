package com.sammy.malum.client.screen.cooler_book.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.cooler_book.CoolerBookScreen;
import com.sammy.malum.client.screen.cooler_book.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftingBookPage extends CoolerBookPage
{
    private final ItemStack outputStack;
    private final ItemStack[] inputStacks;
    public CraftingBookPage(ItemStack outputStack, ItemStack... inputStacks)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/crafting_page.png"));
        this.outputStack = outputStack;
        this.inputStacks = inputStacks;
    }
    public CraftingBookPage(Item outputItem, Item... inputItems)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/crafting_page.png"));
        this.outputStack = outputItem.getDefaultInstance();

        ItemStack[] inputStacks = new ItemStack[inputItems.length];
        for (int i = 0; i < inputItems.length; i++)
        {
            inputStacks[i] = inputItems[i].getDefaultInstance();
        }
        this.inputStacks = inputStacks;
    }
    @Override
    public void renderLeft(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                int index = i * 3 + j;
                if (index < inputStacks.length && !inputStacks[index].isEmpty())
                {
                    ItemStack itemStack = inputStacks[index];
                    int itemPosX = guiLeft + 45 + j*22;
                    int itemPosY = guiTop + 34 + i*22;
                    CoolerBookScreen.renderItem(matrixStack, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }

        CoolerBookScreen.renderItem(matrixStack, outputStack, guiLeft+67, guiTop+126,mouseX,mouseY);

    }

    @Override
    public void renderRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                int index = i * 3 + j;
                if (index < inputStacks.length && !inputStacks[index].isEmpty())
                {
                    ItemStack itemStack = inputStacks[index];
                    int itemPosX = guiLeft + 187 + j*22;
                    int itemPosY = guiTop + 34 + i*22;
                    CoolerBookScreen.renderItem(matrixStack, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }

        CoolerBookScreen.renderItem(matrixStack, outputStack, guiLeft+209, guiTop+126,mouseX,mouseY);
    }

    @Override
    public boolean hasAttachment()
    {
        return true;
    }

    @Override
    public ItemStack attachmentIcon()
    {
        return outputStack;
    }

    public static CraftingBookPage fullPage(Item output, Item input)
    {
        return fullPage(output.getDefaultInstance(), input.getDefaultInstance());
    }
    public static CraftingBookPage fullPage(ItemStack output, ItemStack input)
    {
        return new CraftingBookPage(output, input, input, input, input, input, input, input, input, input);
    }
    public static CraftingBookPage scythePage(Item scythe, Item metal, Item reagent)
    {
        return scythePage(scythe.getDefaultInstance(), metal.getDefaultInstance(), reagent.getDefaultInstance());
    }
    public static CraftingBookPage scythePage(ItemStack scythe, ItemStack metal, ItemStack reagent)
    {
        ItemStack stick = Items.STICK.getDefaultInstance();
        ItemStack empty = Items.AIR.getDefaultInstance();
        return new CraftingBookPage(scythe, metal, metal, reagent, empty, stick, metal, stick, empty, empty);
    }
    public static CraftingBookPage resonatorPage(Item resonator, Item gem, Item metal, Item reagent)
    {
        return resonatorPage(resonator.getDefaultInstance(), gem.getDefaultInstance(), metal.getDefaultInstance(), reagent.getDefaultInstance());
    }
    public static CraftingBookPage resonatorPage(ItemStack resonator, ItemStack gem, ItemStack metal, ItemStack reagent)
    {
        ItemStack empty = Items.AIR.getDefaultInstance();
        return new CraftingBookPage(resonator, empty, reagent, empty, metal, gem, metal, empty, reagent, empty);
    }
}
