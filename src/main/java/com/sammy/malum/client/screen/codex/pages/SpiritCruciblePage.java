package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.renderTexture;

@SuppressWarnings("all")
public class SpiritCruciblePage extends BookPage
{
    private final SpiritFocusingRecipe recipe;
    public SpiritCruciblePage(Predicate<SpiritFocusingRecipe> predicate)
    {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_crucible_page.png"));
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritFocusingRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }
    public SpiritCruciblePage(SpiritFocusingRecipe recipe)
    {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_crucible_page.png"));
        this.recipe = recipe;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static SpiritCruciblePage fromInput(Item inputItem)
    {
        return new SpiritCruciblePage(s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }
    public static SpiritCruciblePage fromOutput(Item outputItem)
    {
        return new SpiritCruciblePage(s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ItemStack inputStack = recipe.input.getItems()[0];
        ItemStack outputStack = recipe.output.stack();
        ProgressionBookScreen.renderItem(poseStack, inputStack, guiLeft+67, guiTop+59,mouseX,mouseY);
        ProgressionBookScreen.renderItem(poseStack, outputStack, guiLeft+67, guiTop+126,mouseX,mouseY);
        renderItems(poseStack, guiLeft+59,guiTop+12, mouseX, mouseY, recipe.spirits);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ItemStack inputStack = recipe.input.getItems()[0];
        ItemStack outputStack = recipe.output.stack();
        ProgressionBookScreen.renderItem(poseStack, inputStack, guiLeft+209, guiTop+59,mouseX,mouseY);
        ProgressionBookScreen.renderItem(poseStack, outputStack, guiLeft+209, guiTop+126,mouseX,mouseY);
        renderItems(poseStack, guiLeft+201,guiTop+12, mouseX, mouseY, recipe.spirits);
    }
    public void renderItems(PoseStack poseStack, int left, int top, int mouseX, int mouseY, List<ItemWithCount> items)
    {
        int index = items.size()-1;
        int textureWidth = 32 + index * 19;
        int offset = (int) (9f * index);
        left -= offset;
        int uOffset = uOffset()[index];
        int vOffset = vOffset()[index];
        renderTexture(BACKGROUND, poseStack, left,top,uOffset,vOffset, textureWidth, 32, 512, 512);

        for (int i = 0; i < items.size(); i++)
        {
            ItemStack stack = items.get(i).getStack();
            ProgressionBookScreen.renderItem(poseStack, stack, left+8+19*i,top+8,mouseX,mouseY);
        }
    }
    public static int[] uOffset()
    {
        return new int[]{294,327,294};
    }
    public static int[] vOffset()
    {
        return new int[]{1,1,34};
    }
}
