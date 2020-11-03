package com.sammy.malum.integration.jei.spiritFurnace;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumMod;
import com.sammy.malum.init.ModItems;
import com.sammy.malum.recipes.CrystallineAcceleratorRecipe;
import com.sammy.malum.recipes.SpiritFurnaceRecipe;
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

public class CrystallineAcceleratorRecipeCategory implements IRecipeCategory<CrystallineAcceleratorRecipe>
{
    public static final ResourceLocation UID = new ResourceLocation(MalumMod.MODID, "crystalline_accelerator");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    
    public CrystallineAcceleratorRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(105, 53);
        localizedName = I18n.format("malum.jei.spirit_furnace");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/crystalline_accelerator_overlay.png"), 0, 0, 103, 51);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModItems.spirit_furnace));
    }
    
    @Override
    public void draw(CrystallineAcceleratorRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
    
        overlay.draw(matrixStack);
    
    
        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();
    
        ITextComponent timeComponent = makeTranslationComponent("malum.recipe.time") //Uses
                .append(new StringTextComponent("" + recipe.getBurnTime()));
        String formattedText = timeComponent.getString();
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontRenderer = minecraft.fontRenderer;
        fontRenderer.drawString(matrixStack, formattedText, -1, 1, 0xFF808080);
    }
    
    @Nonnull
    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }
    
    @Nonnull
    @Override
    public Class<? extends CrystallineAcceleratorRecipe> getRecipeClass()
    {
        return CrystallineAcceleratorRecipe.class;
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
    public void setIngredients(CrystallineAcceleratorRecipe crystallineAcceleratorRecipe, IIngredients iIngredients)
    {
        iIngredients.setInput(VanillaTypes.ITEM, new ItemStack(crystallineAcceleratorRecipe.getInputItem()));
        List<ItemStack> list = new ArrayList<>();
        if (crystallineAcceleratorRecipe.getSideItem() != null)
        {
            list.add(new ItemStack(crystallineAcceleratorRecipe.getSideItem()));
        }
        list.add(new ItemStack(crystallineAcceleratorRecipe.getOutputItem()));
        iIngredients.setOutputLists(VanillaTypes.ITEM, Collections.singletonList(list.stream().map(ItemStack::copy).filter(s -> !s.isEmpty()).collect(Collectors.toList())));
    }
    
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, CrystallineAcceleratorRecipe spiritFurnaceRecipe, IIngredients iIngredients)
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
