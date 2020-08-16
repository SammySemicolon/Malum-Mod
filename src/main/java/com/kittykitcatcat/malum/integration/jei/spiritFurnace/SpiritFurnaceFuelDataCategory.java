package com.kittykitcatcat.malum.integration.jei.spiritFurnace;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.recipes.SpiritFurnaceFuelData;
import com.kittykitcatcat.malum.recipes.SpiritFurnaceRecipe;
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
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SpiritFurnaceFuelDataCategory implements IRecipeCategory<SpiritFurnaceFuelData>
{
    public static final ResourceLocation UID = new ResourceLocation(MalumMod.MODID, "spirit_furnace_fuel");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritFurnaceFuelDataCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(102, 22);
        localizedName = I18n.format("malum.jei.spirit_furnace_fuel");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_furnace_fuel_overlay.png"),
            0, -1, 59, 20);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModItems.spirit_charcoal));
    }

    @Override
    public void draw(SpiritFurnaceFuelData data, double mouseX, double mouseY)
    {
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
    
        overlay.draw();
    
        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();
        
        ITextComponent timeComponent = new TranslationTextComponent("malum.fuel.time.desc")
                .appendSibling(new StringTextComponent("" + data.getFuelTime()));
        String formattedText = timeComponent.getFormattedText();
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontRenderer = minecraft.fontRenderer;
        fontRenderer.drawString(formattedText, 22, 7, 0xFF808080);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends SpiritFurnaceFuelData> getRecipeClass()
    {
        return SpiritFurnaceFuelData.class;
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
    public void setIngredients(SpiritFurnaceFuelData spiritFurnaceRecipe, IIngredients iIngredients)
    {
        iIngredients.setInput(VanillaTypes.ITEM, new ItemStack(spiritFurnaceRecipe.getFuelItem()));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpiritFurnaceFuelData spiritFurnaceRecipe, IIngredients iIngredients)
    {
        iRecipeLayout.getItemStacks().init(0, true, 3, 2);
        iRecipeLayout.getItemStacks().set(0, new ItemStack(spiritFurnaceRecipe.getFuelItem()));
    }
}
