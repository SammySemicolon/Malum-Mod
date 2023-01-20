package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.recipe.SpiritTransmutationRecipe;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.recipe.WrappedIngredient;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class SpiritTransmutationPage extends BookPage {
    private final String headlineTranslationKey;
    private final List<WrappedIngredient> itemTree = new ArrayList<>();

    public SpiritTransmutationPage(String headlineTranslationKey, Item start) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_transmutation_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            return;
        }
        SpiritTransmutationRecipe recipe = SpiritTransmutationRecipe.getRecipe(Minecraft.getInstance().level, start);
        while (true) {
            if (recipe == null) {
                itemTree.add(new WrappedIngredient(Ingredient.of(ItemRegistry.BLIGHTED_SOIL.get())));
                break;
            }
            itemTree.add(new WrappedIngredient(recipe.ingredient));
            ItemStack output = recipe.output;
            recipe = SpiritTransmutationRecipe.getRecipe(Minecraft.getInstance().level, s -> s.ingredient.test(output));
        }
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    @Override
    public boolean isValid() {
        return !itemTree.isEmpty();
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        ProgressionBookScreen.renderText(poseStack, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        List<WrappedIngredient> copy = new ArrayList<>(itemTree);
        ProgressionBookScreen.renderComponent(poseStack, copy.remove(0), guiLeft + 67, guiTop + 44, mouseX, mouseY);
        ProgressionBookScreen.renderComponent(poseStack, copy.remove(copy.size() - 1), guiLeft + 67, guiTop + 126, mouseX, mouseY);
        ProgressionBookScreen.renderComponents(poseStack, copy, guiLeft + 65, guiTop + 82, mouseX, mouseY, false);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        ProgressionBookScreen.renderText(poseStack, component, guiLeft + 218 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        List<WrappedIngredient> copy = new ArrayList<>(itemTree);
        ProgressionBookScreen.renderComponent(poseStack, copy.remove(0), guiLeft + 209, guiTop + 44, mouseX, mouseY);
        ProgressionBookScreen.renderComponent(poseStack, copy.remove(copy.size() - 1), guiLeft + 209, guiTop + 126, mouseX, mouseY);
        ProgressionBookScreen.renderComponents(poseStack, DataHelper.reverseOrder(new ArrayList<>(), copy), guiLeft + 207, guiTop + 82, mouseX, mouseY, false);
    }
}