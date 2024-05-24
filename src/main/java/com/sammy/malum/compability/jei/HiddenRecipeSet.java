package com.sammy.malum.compability.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class HiddenRecipeSet<T> {

	private final RecipeType<T> recipeType;
	private final Set<T> hiddenRecipes = new HashSet<>();

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static HiddenRecipeSet<?> createSet(RecipeType<?> recipeType) {
		return new HiddenRecipeSet(recipeType);
	}

	public HiddenRecipeSet(RecipeType<T> recipeType) {
		this.recipeType = recipeType;
	}

	public void scanAndHideRecipes(IRecipeManager manager, IFocusFactory focusFactory, Collection<TagKey<Item>> nowHidden) {
		var itemTags = ForgeRegistries.ITEMS.tags();
		if (itemTags != null) {
			List<IFocus<ItemStack>> foci = nowHidden.stream()
				.flatMap(tag -> itemTags.getTag(tag).stream())
				.distinct()
				.flatMap(item -> {
					ItemStack stack = new ItemStack(item);
					IFocus<ItemStack> asIngredient = focusFactory.createFocus(RecipeIngredientRole.INPUT, VanillaTypes.ITEM_STACK, stack);
					IFocus<ItemStack> asResult = focusFactory.createFocus(RecipeIngredientRole.OUTPUT, VanillaTypes.ITEM_STACK, stack);
					IFocus<ItemStack> asCatalyst = focusFactory.createFocus(RecipeIngredientRole.CATALYST, VanillaTypes.ITEM_STACK, stack);
					return Stream.of(asIngredient, asResult, asCatalyst);
				}).toList();

			manager.createRecipeLookup(recipeType)
				.limitFocus(foci)
				.get()
				.forEach(hiddenRecipes::add);

			manager.hideRecipes(recipeType, hiddenRecipes);
		}
	}


	public void unhidePreviouslyHiddenRecipes(IRecipeManager manager) {
		manager.unhideRecipes(recipeType, hiddenRecipes);
		hiddenRecipes.clear();
	}
}
