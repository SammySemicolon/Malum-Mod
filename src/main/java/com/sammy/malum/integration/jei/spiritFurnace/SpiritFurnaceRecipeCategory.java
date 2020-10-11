package com.sammy.malum.integration.jei.spiritFurnace;

import com.sammy.malum.MalumMod;
import com.sammy.malum.init.ModItems;
import com.sammy.malum.recipes.SpiritFurnaceRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.ClientHandler.makeTranslationComponent;

public class SpiritFurnaceRecipeCategory implements IRecipeCategory<SpiritFurnaceRecipe>
{
    public static final ResourceLocation UID = new ResourceLocation(MalumMod.MODID, "spirit_furnace");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable overlay_noSide;
    private final IDrawable icon;

    public SpiritFurnaceRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(105, 53);
        localizedName = I18n.format("malum.jei.spirit_furnace");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_furnace_overlay.png"),
            0, 0, 103, 51);
        overlay_noSide = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_furnace_overlay_no_side.png"),
                0, 0, 103, 51);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModItems.spirit_furnace));
    }

    @Override
    public void draw(SpiritFurnaceRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();

        if(recipe.getSideItem() != null)
        {
            overlay.draw(matrixStack);
        }
        else
        {
            overlay_noSide.draw(matrixStack);
        }
        
        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();

        ITextComponent timeComponent = makeTranslationComponent("malum.recipe.time") //Uses
                .append(new StringTextComponent("" + recipe.getBurnTime()));
        String formattedText = timeComponent.getUnformattedComponentText();
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontRenderer = minecraft.fontRenderer;
        fontRenderer.drawString(matrixStack,formattedText, -1, 1, 0xFF808080);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends SpiritFurnaceRecipe> getRecipeClass()
    {
        return SpiritFurnaceRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return localizedName;
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
    public void setIngredients(SpiritFurnaceRecipe spiritFurnaceRecipe, IIngredients iIngredients)
    {
        iIngredients.setInput(VanillaTypes.ITEM, new ItemStack(spiritFurnaceRecipe.getInputItem()));
        List<ItemStack> list = new ArrayList<>();
        if (spiritFurnaceRecipe.getSideItem() != null)
        {
            list.add(new ItemStack(spiritFurnaceRecipe.getSideItem()));
        }
        list.add(new ItemStack(spiritFurnaceRecipe.getOutputItem()));
        iIngredients.setOutputLists(VanillaTypes.ITEM, Collections.singletonList(list.stream().map(ItemStack::copy).filter(s -> !s.isEmpty()).collect(Collectors.toList())));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpiritFurnaceRecipe spiritFurnaceRecipe, IIngredients iIngredients)
    {
        iRecipeLayout.getItemStacks().init(0, true, 3, 17);
        iRecipeLayout.getItemStacks().set(0, new ItemStack(spiritFurnaceRecipe.getInputItem()));
        if (spiritFurnaceRecipe.getSideItem() != null)
        {
            iRecipeLayout.getItemStacks().init(2, true, 82, 5);
            iRecipeLayout.getItemStacks().set(2, new ItemStack(spiritFurnaceRecipe.getOutputItem()));
            iRecipeLayout.getItemStacks().init(3, true, 82, 28);
            iRecipeLayout.getItemStacks().set(3, new ItemStack(spiritFurnaceRecipe.getSideItem()));
        }
        else
        {
            iRecipeLayout.getItemStacks().init(2, true, 82, 17);
            iRecipeLayout.getItemStacks().set(2, new ItemStack(spiritFurnaceRecipe.getOutputItem()));
        }
    }
}
