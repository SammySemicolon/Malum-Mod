package com.sammy.malum.client.screen.cooler_book.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.cooler_book.ProgressionBookScreen;
import com.sammy.malum.core.mod_systems.recipe.ItemWithCount;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.mod_systems.recipe.IngredientWithCount;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.client.screen.cooler_book.ProgressionBookScreen.renderTexture;

public class SpiritInfusionPage extends BookPage
{
    private final SpiritInfusionRecipe recipe;
    @SuppressWarnings("all")
    public SpiritInfusionPage(ItemStack outputStack)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/spirit_infusion_page.png"));
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritInfusionRecipe.getRecipeForArcana(Minecraft.getInstance().world, outputStack);
    }
    public SpiritInfusionPage(Item outputItem)
    {
        this(outputItem.getDefaultInstance());
    }
    public SpiritInfusionPage(SpiritInfusionRecipe recipe)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = recipe;
    }

    @Override
    public void renderLeft(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ItemStack inputStack = recipe.input.stack();
        ItemStack outputStack = recipe.output.stack();
        if (!recipe.extraItems.isEmpty())
        {
            renderIngredients(matrixStack, guiLeft+105,guiTop+51, mouseX, mouseY, recipe.extraItems);
        }
        ProgressionBookScreen.renderItem(matrixStack, inputStack, guiLeft+67, guiTop+59,mouseX,mouseY);
        ProgressionBookScreen.renderItem(matrixStack, outputStack, guiLeft+67, guiTop+126,mouseX,mouseY);
        renderItems(matrixStack, guiLeft+15,guiTop+51, mouseX, mouseY, recipe.spirits);
    }

    @Override
    public void renderRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ItemStack inputStack = recipe.input.stack();
        ItemStack outputStack = recipe.output.stack();
        if (!recipe.extraItems.isEmpty())
        {
            renderIngredients(matrixStack, guiLeft+247,guiTop+51, mouseX, mouseY, recipe.extraItems);
        }
        ProgressionBookScreen.renderItem(matrixStack, inputStack, guiLeft+209, guiTop+59,mouseX,mouseY);
        ProgressionBookScreen.renderItem(matrixStack, outputStack, guiLeft+209, guiTop+126,mouseX,mouseY);
        renderItems(matrixStack, guiLeft+157,guiTop+51, mouseX, mouseY, recipe.spirits);
    }
    public void renderIngredients(MatrixStack matrixStack, int left, int top, int mouseX, int mouseY, List<IngredientWithCount> ingredients)
    {
        ArrayList<ItemWithCount> items = (ArrayList<ItemWithCount>) ingredients.stream().map(ItemWithCount::fromIngredient).collect(Collectors.toList());
        renderItems(matrixStack, left, top, mouseX, mouseY, items);
    }
    public void renderItems(MatrixStack matrixStack, int left, int top, int mouseX, int mouseY, List<ItemWithCount> items)
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
            ItemStack stack = items.get(i).stack();
            ProgressionBookScreen.renderItem(matrixStack, stack, left+8,top+8+19*i,mouseX,mouseY);
        }
    }
    public static int[] uOffset()
    {
        return new int[]{360,393,393,360,327,294,294,327};
    }
    public static int[] vOffset()
    {
        return new int[]{1,1,53,34,1,1,129,110};
    }
}
