package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;

import java.util.function.Predicate;

public class SpiritRepairPage extends BookPage {
    private final SpiritRepairRecipe recipe;

    public SpiritRepairPage(Predicate<SpiritRepairRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_repair_page.png"));
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritRepairRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }

    public SpiritRepairPage(SpiritRepairRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_repair_page.png"));
        this.recipe = recipe;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static SpiritRepairPage fromInput(Item inputItem) {
        return new SpiritRepairPage(s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderComponents(screen, guiGraphics, recipe.spirits, left + 63, top + 16, mouseX, mouseY, false);
        final List<ItemStack> damaged = recipe.inputs.stream().map(Item::getDefaultInstance).peek(s -> s.setDamageValue(Mth.floor(s.getMaxDamage() * recipe.durabilityPercentage))).collect(Collectors.toList());
        final List<ItemStack> repaired = recipe.inputs.stream().map(s -> SpiritRepairRecipe.getRepairRecipeOutput(s.getDefaultInstance())).collect(Collectors.toList());
        renderItem(screen, guiGraphics, damaged, left + 82, top + 59, mouseX, mouseY);
        renderComponent(screen, guiGraphics, recipe.repairMaterial, left + 44, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, repaired, left + 63, top + 126, mouseX, mouseY);

    }
}