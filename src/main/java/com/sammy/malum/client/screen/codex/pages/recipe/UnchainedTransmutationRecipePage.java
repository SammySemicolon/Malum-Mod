//package com.sammy.malum.client.screen.codex.pages.recipe;
//
//import com.sammy.malum.MalumMod;
//import com.sammy.malum.client.screen.codex.helper.*;
//import com.sammy.malum.client.screen.codex.pages.*;
//import com.sammy.malum.client.screen.codex.screens.CodexEntryScreen;
//import com.sammy.malum.common.recipe.UnchainedTransmutationRecipe;
//import com.sammy.malum.registry.common.recipe.*;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneRecipeSearch;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Predicate;
//
//import static com.sammy.malum.client.screen.codex.helper.CodexItemHelper.renderIngredient;
//
//public class UnchainedTransmutationRecipePage extends BookPage {
//
//    private final Component headline;
//    private final List<UnchainedTransmutationRecipe> recipes = new ArrayList<>();
//
//    public UnchainedTransmutationRecipePage(String headline, Predicate<UnchainedTransmutationRecipe> predicate) {
//        this.headline = Component.translatable(PageLangKeyCommons.headlineKey(headline));
//        var level = Minecraft.getInstance().level;
//        if (level == null) {
//            return;
//        }
//        var search = LodestoneRecipeSearch.search(level, MalumRecipeTypes.UNCHAINED_TRANSMUTATION);
//        var recipe = search.findRecipe(predicate);
//        if (recipe == null) {
//            return;
//        }
//        var group = recipe.getGroup();
//        if (group != null) {
//            recipes.addAll(search.findRecipes(r -> r.getGroup().equals(group)));
//        }
//    }
//
//    public static UnchainedTransmutationRecipePage fromInput(String headlineTranslationKey, Item inputItem) {
//        return new UnchainedTransmutationRecipePage(headlineTranslationKey, s -> s.getInput().test(inputItem.getDefaultInstance()));
//    }
//
//    public static UnchainedTransmutationRecipePage fromOutput(String headlineTranslationKey, Item outputItem) {
//        return new UnchainedTransmutationRecipePage(headlineTranslationKey, s -> s.getOutputRaw().is(outputItem));
//    }
//
//    @Override
//    public ResourceLocation getBackground() {
//        return MalumMod.malumPath("textures/gui/book/pages/transmutation_recipe_page.png");
//    }
//
//    @Override
//    public boolean isValid() {
//        return !recipes.isEmpty();
//    }
//
//    @Override
//    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
////        CodexTextHelper.renderHeadline(guiGraphics, headline, left, top);
//
//        UnchainedTransmutationRecipe recipe = recipes.get(getIndex());
//        renderIngredient(screen, guiGraphics, recipe.getInput(), left + 63, top + 56, mouseX, mouseY);
//        CodexItemHelper.renderItem(screen, guiGraphics, recipe.getOutputRaw(), left + 63, top + 132, mouseX, mouseY);
//
//        renderRecipeInfo(guiGraphics, screen, "unchained_transmutation", left + 62, top + 78, mouseX, mouseY);
//    }
//
//    public int getIndex() {
//        return (int) (Minecraft.getInstance().level.getGameTime() % (20L * recipes.size()) / 20);
//    }
//}