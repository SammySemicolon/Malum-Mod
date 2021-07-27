package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.EntryObject;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_content.MalumSpiritAltarRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.math.vector.Vector3d;

import static net.minecraft.client.gui.AbstractGui.blit;

public class SpiritInfusionPage extends BookPage
{
    public final MalumSpiritAltarRecipes.MalumSpiritAltarRecipe recipe;

    public SpiritInfusionPage(Item outputStack)
    {
        MalumSpiritAltarRecipes.MalumSpiritAltarRecipe finalRecipe = null;
        for (MalumSpiritAltarRecipes.MalumSpiritAltarRecipe recipe : MalumSpiritAltarRecipes.RECIPES)
        {
            if (recipe.outputIngredient.getItemAlt().getItem().equals(outputStack))
            {
                finalRecipe = recipe;
                break;
            }
        }
        if (finalRecipe == null)
        {
            MalumMod.LOGGER.info("this ain't good");
            //throw new NullPointerException();
            finalRecipe = MalumSpiritAltarRecipes.RECIPES.get(0);
        }
        this.recipe = finalRecipe;
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/infusion.png");
    }
    
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        super.draw(stack, object, screen, mouseX, mouseY, guiLeft, guiTop, isSecondPage);
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        Minecraft minecraft = Minecraft.getInstance();
        int posX = position.first;
        int posY = position.second;
        int inputX = posX + 56;
        int inputY = posY + 48;

        for (int i = 0; i < recipe.extraItemIngredients.size(); i++)
        {
            Vector3d itemOffset = itemOffset(false, i, recipe.extraItemIngredients.size());
            minecraft.getTextureManager().bindTexture(BACKGROUND);
            blit(stack, inputX + (int)itemOffset.x-2, inputY + (int)itemOffset.z-2, 131, 1, 20, 21, 512, 512);
            screen.drawItem(stack, recipe.extraItemIngredients.get(i).getItem(), inputX + (int)itemOffset.x, inputY + (int)itemOffset.z, mouseX, mouseY);
        }
        for (int i = 0; i < recipe.spiritIngredients.size(); i++)
        {
            Vector3d itemOffset = itemOffset(recipe.extraItemIngredients.size() > 1, i,recipe.spiritIngredients.size());
            minecraft.getTextureManager().bindTexture(BACKGROUND);
            blit(stack, inputX + (int)itemOffset.x-2, inputY + (int)itemOffset.z-2, 131, 1, 20, 21, 512, 512);
            screen.drawItem(stack, recipe.spiritIngredients.get(i).getItem(), inputX + (int)itemOffset.x, inputY + (int)itemOffset.z, mouseX, mouseY);
        }
        screen.drawItem(stack, recipe.inputIngredient.getItemAlt(), inputX, inputY, mouseX, mouseY);
        screen.drawItem(stack, recipe.outputIngredient.getItemAlt(), inputX, posY + 115, mouseX, mouseY);

        screen.drawItem(stack, MalumItems.SPIRIT_ALTAR.get().getDefaultInstance(), posX + 101, posY + 115, mouseX, mouseY);
    }
    public static Vector3d itemOffset(boolean far, int slot, int maxSlots)
    {
        float distance = far ? 6.75f : 5f;
        long gameTime = 0;
        if (maxSlots % 2 == 1)
        {
            gameTime = 12;
        }
        if (maxSlots % 3 == 0)
        {
            if (far)
            {
                gameTime = 12;
                distance = 6.25f;
            }
            else
            {
                gameTime = 4;
                distance = 5.25f;
            }
        }
        if (maxSlots % 4 == 0)
        {
            if (far)
            {
                gameTime = 2;
            }
            else
            {
                gameTime = 0;
            }
        }
        Vector3d position = MalumHelper.rotatedCirclePosition(new Vector3d(0.5f, 0, 0.5f), distance, distance, slot, maxSlots, gameTime, 16);
        if (position.x < 0)
        {
            position = position.subtract(1,0,0);
        }
        return position;
    }
}