package com.sammy.malum.client.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.core.setup.item.ItemRegistry;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.renderTexture;
import static com.sammy.malum.client.screen.codex.pages.SpiritCruciblePage.uOffset;
import static com.sammy.malum.client.screen.codex.pages.SpiritCruciblePage.vOffset;
import static com.sammy.malum.core.helper.DataHelper.prefix;

public class SpiritFocusingRecipeCategory implements IRecipeCategory<SpiritFocusingRecipe>
{
    public static final ResourceLocation BACKGROUND_TEXTURE = prefix("textures/gui/book/pages/spirit_crucible_page.png");
    public static final ResourceLocation UID = prefix("spirit_focusing");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritFocusingRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_focusing_jei.png"), 0, 0, 140, 183);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemRegistry.SPIRIT_CRUCIBLE.get()));
    }

    @Override
    public void draw(SpiritFocusingRecipe recipe, PoseStack poseStack, double mouseX, double mouseY) {
        overlay.draw(poseStack);
        renderItemFrameTexture(poseStack, 54, 12, recipe.spirits.size());
    }

    @Nonnull
    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends SpiritFocusingRecipe> getRecipeClass()
    {
        return SpiritFocusingRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("malum.jei." + UID.getPath());
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(SpiritFocusingRecipe recipe, IIngredients iIngredients)
    {
        ArrayList<ItemStack> items = new ArrayList<>(Arrays.asList(recipe.input.getItems()));

        recipe.spirits.forEach(ingredient -> items.add(ingredient.getStack()));
        iIngredients.setInputs(VanillaTypes.ITEM, items);
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.output.stack());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpiritFocusingRecipe recipe, IIngredients iIngredients)
    {
        int index = 0;
        index = addItems(iRecipeLayout, 54, 12, recipe.spirits, index);

        iRecipeLayout.getItemStacks().init(index+1, true, 61,56);
        iRecipeLayout.getItemStacks().set(index+1, Arrays.asList(recipe.input.getItems()));

        iRecipeLayout.getItemStacks().init(index+2, true, 61,123);
        iRecipeLayout.getItemStacks().set(index+2, recipe.output.stack());
    }

    public void renderItemFrameTexture(PoseStack poseStack, int left, int top, int itemCount)
    {
        int index = itemCount-1;
        int textureWidth = 32 + index * 19;
        int offset = (int) (9f * index);
        left -= offset;
        int uOffset = uOffset()[index];
        int vOffset = vOffset()[index];
        renderTexture(BACKGROUND_TEXTURE, poseStack, left,top,uOffset,vOffset, textureWidth, 32, 512, 512);
    }
    public int addItems(IRecipeLayout iRecipeLayout, int left, int top, List<ItemWithCount> items, int baseIndex)
    {
        int index = items.size()-1;
        int offset = (int) (9f * index);
        left -= offset;
        for (int i = 0; i < items.size(); i++)
        {
            ItemWithCount item = items.get(i);
            iRecipeLayout.getItemStacks().init(baseIndex+i, true, left+7+19*i,top+7);
            iRecipeLayout.getItemStacks().set(baseIndex+i, item.getStack());
        }
        return baseIndex+items.size()+1;
    }
}