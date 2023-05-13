package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.common.recipe.*;
import net.minecraft.client.*;
import net.minecraft.world.item.*;
import net.minecraftforge.data.loading.*;

import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

@SuppressWarnings("all")
public class SpiritInfusionPage extends BookPage {
    private final SpiritInfusionRecipe recipe;

    public SpiritInfusionPage(Predicate<SpiritInfusionRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = DatagenModLoader.isRunningDataGen() ? null : SpiritInfusionRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }

    public SpiritInfusionPage(SpiritInfusionRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = recipe;
    }

    public static SpiritInfusionPage fromInput(Item inputItem) {
        return new SpiritInfusionPage(s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    public static SpiritInfusionPage fromOutput(Item outputItem) {
        return new SpiritInfusionPage(s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, float yOffset, int mouseX, int mouseY, float partialTicks, float xOffset) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Runnable renderSpirits = renderBufferedComponents(screen, poseStack, recipe.spirits, guiLeft + 23, guiTop + 59, mouseX, mouseY, true);
        if (!recipe.extraItems.isEmpty()) {
            renderComponents(screen, poseStack, recipe.extraItems, guiLeft + 107, guiTop + 59, mouseX, mouseY, true);
        }
        renderSpirits.run();
        renderComponent(screen, poseStack, recipe.input, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        renderItem(screen, poseStack, recipe.output, guiLeft + 67, guiTop + 126, mouseX, mouseY);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, float yOffset, int mouseX, int mouseY, float partialTicks, float xOffset) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Runnable renderSpirits = renderBufferedComponents(screen, poseStack, recipe.spirits, guiLeft + 165, guiTop + 59, mouseX, mouseY, true);
        if (!recipe.extraItems.isEmpty()) {
            Runnable renderExtraItems = renderBufferedComponents(screen, poseStack, recipe.extraItems, guiLeft + 249, guiTop + 59, mouseX, mouseY, true);
            renderExtraItems.run();
        }
        renderSpirits.run();
        renderComponent(screen, poseStack, recipe.input, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        renderItem(screen, poseStack, recipe.output, guiLeft + 209, guiTop + 126, mouseX, mouseY);
    }
}
