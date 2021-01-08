package com.sammy.malum.common.integration.jei;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.integration.jei.spiritkiln.SpiritKilnRecipeCategory;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

@JeiPlugin
public class JEIHandler implements IModPlugin
{
    private static final ResourceLocation ID = new ResourceLocation(MalumMod.MODID, "main");
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new SpiritKilnRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }
    
    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry)
    {
        registry.addRecipes(MalumSpiritKilnRecipes.recipes, SpiritKilnRecipeCategory.UID);
    
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
    {
        registry.addRecipeCatalyst(new ItemStack(MalumItems.SPIRIT_KILN.get()), SpiritKilnRecipeCategory.UID);
    }
    @Nonnull
    @Override
    public ResourceLocation getPluginUid()
    {
        return ID;
    }
}