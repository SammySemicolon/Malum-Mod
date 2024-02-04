package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.common.recipe.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraftforge.data.loading.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

@SuppressWarnings("all")
public class SpiritTransmutationRecipePage extends BookPage {
    private final String headlineTranslationKey;
    private final List<SpiritTransmutationRecipe> recipes;

    public SpiritTransmutationRecipePage(String headlineTranslationKey, Predicate<SpiritTransmutationRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/transmutation_recipe_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.recipes = DatagenModLoader.isRunningDataGen() ? null : new ArrayList<>();
        if (!DatagenModLoader.isRunningDataGen()) {
            final ClientLevel level = Minecraft.getInstance().level;
            final SpiritTransmutationRecipe recipe = SpiritTransmutationRecipe.getRecipe(level, predicate);
            if (recipe != null) {
                recipes.add(recipe);
            }
            if (recipe.group != null) {
                for (SpiritTransmutationRecipe otherRecipe : SpiritTransmutationRecipe.getRecipes(level)) {
                    if (!recipe.equals(otherRecipe) && recipe.group.equals(otherRecipe.group)) {
                        recipes.add(otherRecipe);
                    }
                }
            }
        }
    }

    public SpiritTransmutationRecipePage(String headlineTranslationKey, SpiritTransmutationRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/transmutation_recipe_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.recipes = new ArrayList<>(List.of(recipe));
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    public static SpiritTransmutationRecipePage fromInput(String headlineTranslationKey, Item inputItem) {
        return new SpiritTransmutationRecipePage(headlineTranslationKey, s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    public static SpiritTransmutationRecipePage fromOutput(String headlineTranslationKey, Item outputItem) {
        return new SpiritTransmutationRecipePage(headlineTranslationKey, s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }

    @Override
    public boolean isValid() {
        return recipes != null;
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        int index = (int) (Minecraft.getInstance().level.getGameTime() % (20L * recipes.size()) / 20);
        SpiritTransmutationRecipe recipe = recipes.get(index);
        renderItem(screen, guiGraphics, recipe.ingredient, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, guiLeft + 67, guiTop + 126, mouseX, mouseY);
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 218 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        int index = (int) (Minecraft.getInstance().level.getGameTime() % (20L * recipes.size()) / 20);
        SpiritTransmutationRecipe recipe = recipes.get(index);
        renderItem(screen, guiGraphics, recipe.ingredient, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, guiLeft + 209, guiTop + 126, mouseX, mouseY);
    }
}
