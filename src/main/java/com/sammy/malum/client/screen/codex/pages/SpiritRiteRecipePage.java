package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.*;
import net.minecraft.world.item.*;

import java.util.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderItem;

public class SpiritRiteRecipePage extends BookPage {
    private final MalumRiteType riteType;

    public SpiritRiteRecipePage(MalumRiteType riteType) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_rite_recipe_page.png"));
        this.riteType = riteType;
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, float yOffset, int mouseX, int mouseY, float partialTicks, float xOffset) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderRite(poseStack, screen, guiLeft + 67, guiTop + 123, mouseX, mouseY, riteType.spirits);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, float yOffset, int mouseX, int mouseY, float partialTicks, float xOffset) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderRite(poseStack, screen, guiLeft + 209, guiTop + 123, mouseX, mouseY, riteType.spirits);
    }

    public void renderRite(PoseStack poseStack, EntryScreen screen, int left, int top, int mouseX, int mouseY, List<MalumSpiritType> spirits) {
        for (int i = 0; i < spirits.size(); i++) {
            ItemStack stack = spirits.get(i).getSplinterItem().getDefaultInstance();
            renderItem(screen, poseStack, stack, left, top - 20 * i, mouseX, mouseY);
        }
    }
}
