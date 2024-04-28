package com.sammy.malum.compability.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class HiddenRecipeSet<T> {

	private final RecipeType<T> recipeType;
	private final Set<RecipeEntry<T>> hiddenRecipes = new HashSet<>();

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static HiddenRecipeSet<?> createSet(RecipeType<?> recipeType) {
		return new HiddenRecipeSet(recipeType);
	}

	public HiddenRecipeSet(RecipeType<T> recipeType) {
		this.recipeType = recipeType;
	}

	public void recipeHidden(Set<Item> items, T recipe) {
		hiddenRecipes.add(new RecipeEntry<>(items, recipe));
	}

	public void scanForHiddenRecipes(IRecipeManager manager, IFocusFactory focusFactory, Collection<Item> nowHidden) {
		Map<T, Set<Item>> disabledRecipes = new HashMap<>();
		for (Item item : nowHidden) {
			ItemStack stack = new ItemStack(item);
			IFocus<ItemStack> asIngredient = focusFactory.createFocus(RecipeIngredientRole.INPUT, VanillaTypes.ITEM_STACK, stack);
			IFocus<ItemStack> asResult = focusFactory.createFocus(RecipeIngredientRole.OUTPUT, VanillaTypes.ITEM_STACK, stack);
			IFocus<ItemStack> asCatalyst = focusFactory.createFocus(RecipeIngredientRole.CATALYST, VanillaTypes.ITEM_STACK, stack);
			manager.createRecipeLookup(recipeType)
				.limitFocus(List.of(asIngredient, asResult, asCatalyst))
				.get().forEach(it -> disabledRecipes.computeIfAbsent(it, ignored -> new HashSet<>()).add(item));
		}

		manager.hideRecipes(recipeType, disabledRecipes.keySet());

		for (var entry : disabledRecipes.entrySet()) {
			recipeHidden(entry.getValue(), entry.getKey());
		}
	}


	public void unhideRecipesThatAreNowShown(IRecipeManager manager, Collection<Item> nowShown) {
		Set<T> toUnhide = new HashSet<>();
		Set<RecipeEntry<T>> currentHiddenRecipes = new HashSet<>(hiddenRecipes);
		for (var entry : currentHiddenRecipes) {
			if (nowShown.containsAll(entry.requireAllToShow)) {
				toUnhide.add(entry.recipe);
				hiddenRecipes.remove(entry);
			} else {
				entry.requireAllToShow.removeAll(nowShown);
			}
		}

		if (!toUnhide.isEmpty())
			manager.unhideRecipes(recipeType, toUnhide);
	}

	private record RecipeEntry<T>(Set<Item> requireAllToShow, T recipe) {
		// NO-OP
	}
}
