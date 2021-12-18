package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SmeltingBookPage extends BookPage
{
    private final ItemStack inputStack;
    private final ItemStack outputStack;
    public SmeltingBookPage(ItemStack inputStack, ItemStack outputStack)
    {
        super(DataHelper.prefix("textures/gui/book/pages/smelting_page.png"));
        this.inputStack = inputStack;
        this.outputStack = outputStack;
    }
    public SmeltingBookPage(Item inputItem, Item outputItem)
    {
        this(inputItem.getDefaultInstance(), outputItem.getDefaultInstance());
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderItem(poseStack, inputStack, guiLeft+67, guiTop+59,mouseX,mouseY);
        ProgressionBookScreen.renderItem(poseStack, outputStack, guiLeft+67, guiTop+126,mouseX,mouseY);

    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderItem(poseStack, inputStack, guiLeft+209, guiTop+59,mouseX,mouseY);
        ProgressionBookScreen.renderItem(poseStack, outputStack, guiLeft+209, guiTop+126,mouseX,mouseY);
    }
}
