package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.*;
import net.minecraft.world.item.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderItem;

public class CraftingBookPage extends BookPage {
    private final ItemStack outputStack;
    private final ItemStack[] inputStacks;

    public CraftingBookPage(ItemStack outputStack, ItemStack... inputStacks) {
        super(MalumMod.malumPath("textures/gui/book/pages/crafting_page.png"));
        this.outputStack = outputStack;
        this.inputStacks = inputStacks;
    }

    public CraftingBookPage(Item outputItem, Item... inputItems) {
        this(outputItem.getDefaultInstance(), inputItems);
    }

    public CraftingBookPage(ItemStack outputStack, Item... inputItems) {
        super(MalumMod.malumPath("textures/gui/book/pages/crafting_page.png"));
        this.outputStack = outputStack;

        ItemStack[] inputStacks = new ItemStack[inputItems.length];
        for (int i = 0; i < inputItems.length; i++) {
            inputStacks[i] = inputItems[i].getDefaultInstance();
        }
        this.inputStacks = inputStacks;
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < inputStacks.length && !inputStacks[index].isEmpty()) {
                    ItemStack itemStack = inputStacks[index];
                    int itemPosX = guiLeft + 45 + j * 22;
                    int itemPosY = guiTop + 34 + i * 22;
                    renderItem(screen, poseStack, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }

        renderItem(screen, poseStack, outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);

    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < inputStacks.length && !inputStacks[index].isEmpty()) {
                    ItemStack itemStack = inputStacks[index];
                    int itemPosX = guiLeft + 187 + j * 22;
                    int itemPosY = guiTop + 34 + i * 22;
                    renderItem(screen, poseStack, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }

        renderItem(screen, poseStack, outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
    }

    public static CraftingBookPage fullPage(Item output, Item input) {
        return fullPage(output.getDefaultInstance(), input.getDefaultInstance());
    }

    public static CraftingBookPage fullPage(ItemStack output, ItemStack input) {
        return new CraftingBookPage(output, input, input, input, input, input, input, input, input, input);
    }

    public static CraftingBookPage scythePage(Item scythe, Item metal, Item reagent) {
        return scythePage(scythe.getDefaultInstance(), metal.getDefaultInstance(), reagent.getDefaultInstance());
    }

    public static CraftingBookPage scythePage(ItemStack scythe, ItemStack metal, ItemStack reagent) {
        ItemStack stick = Items.STICK.getDefaultInstance();
        ItemStack empty = Items.AIR.getDefaultInstance();
        return new CraftingBookPage(scythe, metal, metal, reagent, empty, stick, metal, stick, empty, empty);
    }

    public static CraftingBookPage resonatorPage(Item resonator, Item gem, Item metal, Item reagent) {
        return resonatorPage(resonator.getDefaultInstance(), gem.getDefaultInstance(), metal.getDefaultInstance(), reagent.getDefaultInstance());
    }

    public static CraftingBookPage resonatorPage(ItemStack resonator, ItemStack gem, ItemStack metal, ItemStack reagent) {
        ItemStack empty = Items.AIR.getDefaultInstance();
        return new CraftingBookPage(resonator, empty, reagent, empty, metal, gem, metal, empty, reagent, empty);
    }

    public static CraftingBookPage ringPage(Item ring, Item material, Item reagent) {
        return ringPage(ring.getDefaultInstance(), material.getDefaultInstance(), reagent.getDefaultInstance());
    }

    public static CraftingBookPage ringPage(ItemStack ring, ItemStack material, ItemStack reagent) {
        ItemStack empty = Items.AIR.getDefaultInstance();
        return new CraftingBookPage(ring, empty, material, reagent, material, empty, material, empty, material, empty);
    }

    public static CraftingBookPage itemPedestalPage(Item pedestal, Item fullBlock, Item slab) {
        return itemPedestalPage(pedestal.getDefaultInstance(), fullBlock.getDefaultInstance(), slab.getDefaultInstance());
    }

    public static CraftingBookPage itemPedestalPage(ItemStack pedestal, ItemStack fullBlock, ItemStack slab) {
        ItemStack empty = Items.AIR.getDefaultInstance();
        return new CraftingBookPage(pedestal, slab, slab, slab, empty, fullBlock, empty, slab, slab, slab);
    }

    public static CraftingBookPage itemStandPage(Item stand, Item fullBlock, Item slab) {
        return itemStandPage(stand.getDefaultInstance(), fullBlock.getDefaultInstance(), slab.getDefaultInstance());
    }

    public static CraftingBookPage itemStandPage(ItemStack stand, ItemStack fullBlock, ItemStack slab) {
        ItemStack empty = Items.AIR.getDefaultInstance();
        return new CraftingBookPage(stand, empty, empty, empty, slab, slab, slab, fullBlock, fullBlock, fullBlock);
    }

    public static CraftingBookPage toolPage(Item tool, Item metal) {
        return toolPage(tool.getDefaultInstance(), metal.getDefaultInstance());
    }

    public static CraftingBookPage toolPage(ItemStack tool, ItemStack metal) {
        ItemStack stick = Items.STICK.getDefaultInstance();
        ItemStack empty = Items.AIR.getDefaultInstance();
        if (tool.getItem() instanceof SwordItem) {
            return new CraftingBookPage(tool, empty, metal, empty, empty, metal, empty, empty, stick, empty);
        }
        if (tool.getItem() instanceof AxeItem) {
            return new CraftingBookPage(tool, metal, metal, empty, metal, stick, empty, empty, stick, empty);
        }
        if (tool.getItem() instanceof HoeItem) {
            return new CraftingBookPage(tool, metal, metal, empty, empty, stick, empty, empty, stick, empty);
        }
        if (tool.getItem() instanceof ShovelItem) {
            return new CraftingBookPage(tool, empty, metal, empty, empty, stick, empty, empty, stick, empty);
        }
        if (tool.getItem() instanceof PickaxeItem) {
            return new CraftingBookPage(tool, metal, metal, metal, empty, stick, empty, empty, stick, empty);
        }
        return null;
    }
}