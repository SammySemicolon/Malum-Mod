package com.sammy.malum.client.screen.codex.pages.display;

import com.mojang.blaze3d.systems.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedItem;
import com.sammy.malum.client.screen.codex.handlers.*;
import com.sammy.malum.client.screen.codex.objects.button.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;

public class SoulstoneGrowthStagePage extends BookPage {

    protected float[] oldGrowthStage = new float[4];
    protected float[] growthStage = new float[4];

    protected int selectedStage;

    public SoulstoneGrowthStagePage() {
    }

    @Override
    public BookObjectHandler<CodexEntryScreen> createBookObjects(CodexEntryScreen screen, int left, int top) {
        BookObjectHandler<CodexEntryScreen> handler = new BookObjectHandler<>();

        int step = 22;
        int objectStart = getPageMiddle(0) - (4 * step) / 2;
        int objectTop = Mth.floor(CodexEntryScreen.PAGE_HEIGHT * 0.6f);
        for (int i = 0; i < 4; i++) {
            int objectLeft = objectStart + i * step;
            handler.add(new SoulstoneGrowthStageSelectionObject(this, DisplayedItem.item(MalumContent.Materials.SOULSTONE_BUD), i, objectLeft, objectTop));
        }
        return handler;
    }

    @Override
    public ResourceLocation getBackground() {
        return MalumMod.malumPath("textures/gui/book/pages/object_display_page.png");
    }

    @Override
    public void tick(CodexEntryScreen screen, int left, int top, boolean isRepeat) {
        for (int i = 0; i < 4; i++) {
            oldGrowthStage[i] = growthStage[i];
            float target = selectedStage == i ? 1f : 0;
            growthStage[i] = DataHelper.approach(growthStage[i], target, 0.05f);
        }
    }

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        var hud = MalumShaders.DISSOLVING_HUD_ELEMENT.getShaderInstance();
        hud.safeGetUniform("YFrequency").set(24f);
        hud.safeGetUniform("XFrequency").set(16f);
        hud.safeGetUniform("Speed").set(500f);
        hud.safeGetUniform("Width").set(64f);
        hud.safeGetUniform("Height").set(64f);

        for (int i = 0; i < 4; i++) {
            float delta = Mth.lerp(partialTicks, oldGrowthStage[i], growthStage[i]);
            if (delta > 0) {
                float expo = delta * delta;
                float alpha = 0.5f + expo * 0.5f;
                float intensity = 5f + expo * 95f;
                int textureIndex = i + 1;
                var crystal = MalumMod.malumPath("textures/gui/book/entry_elements/soulstone_" + textureIndex + ".png");
                var mask = MalumMod.malumPath("textures/gui/book/entry_elements/soulstone_" + textureIndex + "_mask.png");
                RenderSystem.setShaderTexture(1, mask);
                hud.safeGetUniform("Intensity").set(intensity);
                hud.safeGetUniform("Dissolvement").set(delta);
                RenderSystem.enableBlend();
                var builder = VFXBuilders.createScreen()
                        .setShader(hud)
                        .setAlpha(alpha)
                        .setTexture(crystal);

                builder.setPositionWithWidth(left + 39, top + 46, 64, 64).blit(guiGraphics.pose());
                RenderSystem.disableBlend();
            }
        }
    }

    public int getSelectedStage() {
        return selectedStage;
    }

    public void setStage(int selectedStage) {
        this.selectedStage = selectedStage;
    }
}