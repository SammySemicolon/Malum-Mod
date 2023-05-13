package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.EntryScreen;
import com.sammy.malum.client.screen.codex.pages.SpiritRiteTextPage;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.client.Minecraft;

import java.util.Optional;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;
import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.*;

public class RiteEntryObject extends EntryObject {
    public final MalumRiteType riteType;
    public RiteEntryObject(BookEntry entry, int posX, int posY) {
        super(entry.setDark(), posX, posY);
        Optional<SpiritRiteTextPage> page = entry.pages.stream().filter(p -> p instanceof SpiritRiteTextPage).map(p -> ((SpiritRiteTextPage) p)).findAny();
        if (page.isPresent()) {
            this.riteType = page.get().riteType;
        } else {
            throw new IllegalArgumentException("Entry " + entry.translationKey() + " lacks a spirit rite page");
        }
    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY) {
        EntryScreen.openScreen(this);
    }

    @Override
    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTransparentTexture(FADE_TEXTURE, poseStack, posX - 13, posY - 13, 1, 252, 58, 58, 512, 512);
        renderTexture(FRAME_TEXTURE, poseStack, posX, posY, 1, getFrameTextureV(), width, height, 512, 512);
        renderTexture(FRAME_TEXTURE, poseStack, posX, posY, 100, getBackgroundTextureV(), width, height, 512, 512);
        renderRiteIcon(riteType, poseStack, entry.isSoulwood, posX + 8, posY + 8);
    }
}