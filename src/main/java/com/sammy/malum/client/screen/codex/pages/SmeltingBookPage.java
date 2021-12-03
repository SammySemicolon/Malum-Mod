package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SmeltingBookPage extends BookPage
{
    private final ItemStack inputStack;
    private final ItemStack outputStack;
    public SmeltingBookPage(ItemStack inputStack, ItemStack outputStack)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/smelting_page.png"));
        this.inputStack = inputStack;
        this.outputStack = outputStack;
    }
    public SmeltingBookPage(Item inputItem, Item outputItem)
    {
        this(inputItem.getDefaultInstance(), outputItem.getDefaultInstance());
    }

    @Override
    public void renderLeft(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderItem(matrixStack, inputStack, guiLeft+67, guiTop+59,mouseX,mouseY);
        ProgressionBookScreen.renderItem(matrixStack, outputStack, guiLeft+67, guiTop+126,mouseX,mouseY);

    }

    @Override
    public void renderRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderItem(matrixStack, inputStack, guiLeft+209, guiTop+59,mouseX,mouseY);
        ProgressionBookScreen.renderItem(matrixStack, outputStack, guiLeft+209, guiTop+126,mouseX,mouseY);
    }
}
