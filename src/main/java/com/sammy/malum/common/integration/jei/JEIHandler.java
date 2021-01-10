package com.sammy.malum.common.integration.jei;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.integration.jei.chiseling.ChiselingRecipeCategory;
import com.sammy.malum.common.integration.jei.rites.RiteRecipeCategory;
import com.sammy.malum.common.integration.jei.spiritkiln.SpiritKilnRecipeCategory;
import com.sammy.malum.common.integration.jei.transfusion.TransfusionRecipeCategory;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.modcontent.MalumChiseling;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes;
import com.sammy.malum.core.modcontent.MalumTransfusions;
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
        registry.addRecipeCategories(new ChiselingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new RiteRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new TransfusionRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }
    
    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry)
    {
        registry.addRecipes(MalumSpiritKilnRecipes.INFUSING, SpiritKilnRecipeCategory.UID);
        registry.addRecipes(MalumChiseling.CHISELING, ChiselingRecipeCategory.UID);
        registry.addRecipes(MalumRites.RITES, RiteRecipeCategory.UID);
        registry.addRecipes(MalumTransfusions.TRANSFUSIONS, TransfusionRecipeCategory.UID);
    
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
    {
        registry.addRecipeCatalyst(new ItemStack(MalumItems.SPIRIT_KILN.get()), SpiritKilnRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(MalumItems.RUNIC_CHISEL.get()), ChiselingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(MalumItems.TOTEM_CORE.get()), RiteRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(MalumItems.TOTEM_CORE.get()), TransfusionRecipeCategory.UID);
    }
    @Nonnull
    @Override
    public ResourceLocation getPluginUid()
    {
        return ID;
    }
}