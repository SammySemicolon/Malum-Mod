package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.helpers.*;

import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.screen.*;

import java.util.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritRiteRecipePage extends BookPage {

    private static final ScreenParticleHolder RITE_PARTICLES = new ScreenParticleHolder();

    private final MalumRiteType riteType;

    public SpiritRiteRecipePage(MalumRiteType riteType) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_rite_recipe_page.png"));
        this.riteType = riteType;
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        if (!isRepeat) {
            if (ScreenParticleHandler.canSpawnParticles) {
                RITE_PARTICLES.tick();
            }
            ScreenParticleHandler.renderParticles(RITE_PARTICLES);
        }
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderRite(guiGraphics, screen, guiLeft + 67, guiTop + 123, mouseX, mouseY, riteType.spirits);
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderRite(guiGraphics, screen, guiLeft + 209, guiTop + 123, mouseX, mouseY, riteType.spirits);
    }

    public void renderRite(GuiGraphics guiGraphics, EntryScreen screen, int left, int top, int mouseX, int mouseY, List<MalumSpiritType> spirits) {
        var rand = screen.getMinecraft().level.random;
        PoseStack poseStack = guiGraphics.pose();
        for (int i = 0; i < spirits.size(); i++) {
            final int y = top - 20 * i;
            MalumSpiritType spiritType = spirits.get(i);
            ResourceLocation spiritTexture = spiritType.getTotemGlowTexture();
            ItemStack stack = spirits.get(i).spiritShard.get().getDefaultInstance();
            renderRiteIcon(spiritTexture, spiritType, poseStack, parentEntry.isSoulwood, 0.25f, left, y);
            if (screen.isHovering(mouseX, mouseY, left, y, 16, 16)) {
                guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, Screen.getTooltipFromItem(Minecraft.getInstance(), stack), mouseX, mouseY);
            }
            if (ScreenParticleHandler.canSpawnParticles) {
                final int x = left + 8;
                float xOffset = 25;
                float yMotion = RandomHelper.randomBetween(rand, 0.2f, 0.4f) * (rand.nextBoolean() ? -1 : 1);
                int lifetime = RandomHelper.randomBetween(rand, 40, 80);
                float scale = RandomHelper.randomBetween(rand, 0.2f, 0.6f);
                float spin = RandomHelper.randomBetween(rand, 0.2f, 0.4f);
                ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.WISP, RITE_PARTICLES)
                        .setTransparencyData(GenericParticleData.create(0.04f, 0.4f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                        .setSpinData(SpinParticleData.create(spin).build())
                        .setScaleData(GenericParticleData.create(scale, 0).build())
                        .setColorData(spiritType.createMainColorData().setCoefficient(0.25f).build())
                        .setLifetime(lifetime)
                        .setMotion(0, yMotion)
                        .spawn(x - xOffset, y)
                        .spawn(x + xOffset, y);
            }
        }
    }
}
