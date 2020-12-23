package com.sammy.malum.common.integration.jei;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.integration.jei.tainttransfusion.FurnaceTaintTransfusionRecipeCategory;
import com.sammy.malum.common.integration.jei.tainttransfusion.TaintTransfusionRecipeCategory;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.recipes.TaintTransfusion;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.stream.Collectors;

@JeiPlugin
public class JEIHandler implements IModPlugin
{
    private static final ResourceLocation ID = new ResourceLocation(MalumMod.MODID, "main");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(
                //new ArcaneCraftingRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new TaintTransfusionRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new FurnaceTaintTransfusionRecipeCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry)
    {
        //registry.addRecipes(ArcaneCraftingRecipe.recipes, ArcaneCraftingRecipeCategory.UID);
        registry.addRecipes(TaintTransfusion.conversions, TaintTransfusionRecipeCategory.UID);
        registry.addRecipes(TaintTransfusion.advancedConversions, FurnaceTaintTransfusionRecipeCategory.UID);
    
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
    {
        registry.addRecipeCatalyst(new ItemStack(MalumItems.SEED_OF_CORRUPTION.get()), TaintTransfusionRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(MalumItems.TAINTED_FURNACE.get()), TaintTransfusionRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(MalumItems.TAINTED_FURNACE.get()), FurnaceTaintTransfusionRecipeCategory.UID);
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid()
    {
        return ID;
    }
}
