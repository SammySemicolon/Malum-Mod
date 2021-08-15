package com.sammy.malum.client.screen.cooler_book.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.cooler_book.CoolerBookScreen;
import com.sammy.malum.core.mod_content.MalumSpiritAltarRecipes;
import com.sammy.malum.core.mod_content.MalumSpiritAltarRecipes.MalumSpiritAltarRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

import static com.sammy.malum.client.screen.cooler_book.CoolerBookScreen.renderTexture;

public class SpiritInfusionPage extends CoolerBookPage
{
    private final MalumSpiritAltarRecipe recipe;
    public SpiritInfusionPage(ItemStack outputStack)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/spirit_infusion_page.png"));
        for (MalumSpiritAltarRecipes.MalumSpiritAltarRecipe recipe : MalumSpiritAltarRecipes.RECIPES)
        {
            if (recipe.outputIngredient.matches(outputStack))
            {
                this.recipe = recipe;
                return;
            }
        }
        this.recipe = null;
    }
    public SpiritInfusionPage(MalumSpiritAltarRecipe recipe)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = recipe;
    }
    public SpiritInfusionPage(Item outputItem)
    {
        this(outputItem.getDefaultInstance());
    }

    @Override
    public boolean hasAttachment()
    {
        return true;
    }

    @Override
    public ItemStack attachmentIcon()
    {
        return recipe.outputIngredient.getStaticItem();
    }

    @Override
    public void renderLeft(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ItemStack inputStack = recipe.inputIngredient.getStaticItem();
        ItemStack outputStack = recipe.outputIngredient.getStaticItem();
        CoolerBookScreen.renderItem(matrixStack, inputStack, guiLeft+67, guiTop+59,mouseX,mouseY);
        CoolerBookScreen.renderItem(matrixStack, outputStack, guiLeft+67, guiTop+126,mouseX,mouseY);
        renderItems(matrixStack, guiLeft+15,guiTop+51, mouseX, mouseY, recipe.spiritStacks());
        if (!recipe.extraItemIngredients.isEmpty())
        {
            renderItems(matrixStack, guiLeft+15,guiTop+105, mouseX, mouseY, recipe.extraItemStacks());
        }
    }

    @Override
    public void renderRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ItemStack inputStack = recipe.inputIngredient.getStaticItem();
        ItemStack outputStack = recipe.outputIngredient.getStaticItem();
        CoolerBookScreen.renderItem(matrixStack, inputStack, guiLeft+209, guiTop+59,mouseX,mouseY);
        CoolerBookScreen.renderItem(matrixStack, outputStack, guiLeft+209, guiTop+126,mouseX,mouseY);
        renderItems(matrixStack, guiLeft+157,guiTop+51, mouseX, mouseY, recipe.spiritStacks());
        if (!recipe.extraItemIngredients.isEmpty())
        {
            renderItems(matrixStack, guiLeft+247,guiTop+51, mouseX, mouseY, recipe.extraItemStacks());
        }
    }
    public void renderItems(MatrixStack matrixStack, int left, int top, int mouseX, int mouseY, ArrayList<ItemStack> items)
    {
        //14 6, 156 6
        //15 51
        int index = items.size()-1;
        int textureHeight = 32 + index * 19;
        int offset = (int) (6.5f * index);
        top -= offset;
        int uOffset = uOffset()[index];
        int vOffset = vOffset()[index];
        renderTexture(BACKGROUND, matrixStack, left,top,uOffset,vOffset, 32, textureHeight, 512, 512);

        for (int i = 0; i < items.size(); i++)
        {
            ItemStack stack = items.get(i);
            CoolerBookScreen.renderItem(matrixStack, stack, left+8,top+8+19*i,mouseX,mouseY);
        }
    }
    public int[] uOffset()
    {
        return new int[]{360,393,393,360,327,294,294,327};
    }
    public int[] vOffset()
    {
        return new int[]{1,1,53,34,1,1,129,110};
    }
}
