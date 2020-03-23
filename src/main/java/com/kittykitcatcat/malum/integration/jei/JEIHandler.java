package com.kittykitcatcat.malum.integration.jei;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.integration.jei.blockTransmutation.SpiritFurnaceRecipeCategory;
import com.kittykitcatcat.malum.integration.jei.blockTransmutation.TransmutationRecipeCategory;
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
        registry.addRecipeCategories(
            new TransmutationRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
        new SpiritFurnaceRecipeCategory(registry.getJeiHelpers().getGuiHelper())

        );
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry)
    {
        registry.addRecipes(ModRecipes.blockTransmutationRecipes, TransmutationRecipeCategory.UID);
        registry.addRecipes(ModRecipes.spiritFurnaceRecipes, SpiritFurnaceRecipeCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
    {
        registry.addRecipeCatalyst(new ItemStack(ModItems.block_transmutation_tool), TransmutationRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModItems.spirit_furnace), SpiritFurnaceRecipeCategory.UID);
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid()
    {
        return ID;
    }
}
