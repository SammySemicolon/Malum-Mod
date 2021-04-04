package com.sammy.malum.common.integration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
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

import javax.annotation.Nonnull;

import java.util.ArrayList;

import static com.sammy.malum.MalumHelper.prefix;
import static com.sammy.malum.common.book.BookScreen.packColor;
import static com.sammy.malum.core.modcontent.MalumRites.*;

public class RiteRecipeCategory implements IRecipeCategory<MalumRite>
{
    public static final ResourceLocation UID = prefix("totem_rites");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    
    public RiteRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(111, 37);
        localizedName = I18n.format("malum.jei.totem_rites");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/totem_rites_recipe_overlay.png"), 0, 0, 109, 35);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.TOTEM_CORE.get()));
    }
    
    @Override
    public void draw(MalumRite recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
        
        overlay.draw(matrixStack);
    
        ITextComponent timeComponent = ClientHelper.simpleTranslatableComponent(recipe.translationKey);
        String formattedText = timeComponent.getString();
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontRenderer = minecraft.fontRenderer;
        int posX = 55 - fontRenderer.getStringWidth(formattedText)/2;
        fontRenderer.drawString(matrixStack, formattedText, posX, 0, packColor(128, 174, 174, 174));
        fontRenderer.drawString(matrixStack, formattedText, posX, 2, packColor(128, 174, 174, 174));
        fontRenderer.drawString(matrixStack, formattedText, posX-1, 1, packColor(128, 174, 174, 174));
        fontRenderer.drawString(matrixStack, formattedText, posX+1, 1, packColor(128, 174, 174, 174));
        
        fontRenderer.drawString(matrixStack, formattedText, posX, 1, packColor(255, 128, 128, 128));
        
        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();
    }
    
    @Nonnull
    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }
    
    @Nonnull
    @Override
    public Class<? extends MalumRite> getRecipeClass()
    {
        return MalumRite.class;
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
    public void setIngredients(MalumRite recipe, IIngredients iIngredients)
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (MalumSpiritType spirit : recipe.spirits)
        {
            stacks.add(spirit.splinterItem.getDefaultInstance());
        }
        iIngredients.setInputs(VanillaTypes.ITEM, stacks);
    }
    
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MalumRite recipe, IIngredients iIngredients)
    {
        int i = 0;
        for (MalumSpiritType spirit : recipe.spirits)
        {
            iRecipeLayout.getItemStacks().init(i, true, 4+21*i, 12);
            iRecipeLayout.getItemStacks().set(i, spirit.splinterItem.getDefaultInstance());
            i++;
        }
    }
}
