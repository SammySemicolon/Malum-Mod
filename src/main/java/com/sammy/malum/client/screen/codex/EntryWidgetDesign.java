package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.display.*;
import net.minecraft.resources.*;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.client.screen.codex.helper.CodexRenderHelper.renderTexture;

public final class EntryWidgetDesign {

    public static final EntryWidgetDesign LARGE =
            new EntryWidgetDesign("large");

    public static final EntryWidgetDesign MEDIUM =
            new EntryWidgetDesign("medium");

    public static final EntryWidgetDesign SMALL =
            new EntryWidgetDesign("small");

    private final String id;
    private ResourceLocation texture;

    private EntryWidgetDesign(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ResourceLocation getTexture() {
        if (texture == null) {
            texture = malumPath("textures/gui/book/objects/entry_" + id + ".png");
        }
        return texture;
    }

    public void render(PoseStack poseStack, int left, int top, float effectDelta) {
        var offset = hashCode() % 800;
        var texture = getTexture();
        CodexOutlineRenderer.create(this, left-16, top-16, 32, 32)
                .setEffectStrength(effectDelta)
                .setOffset(offset)
                .setOutlineWidth(3)
                .setShadowWidth(5)
                .renderOutline(poseStack);
        renderTexture(texture, poseStack, left, top, 0, 0, 32, 32);
    }
}