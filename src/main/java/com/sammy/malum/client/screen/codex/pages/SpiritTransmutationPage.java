package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.data.loading.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.screen.*;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritTransmutationPage extends BookPage {

    private static final ScreenParticleHolder TRANSMUTATION_PARTICLES = new ScreenParticleHolder();

    private final String headlineTranslationKey;
    private final List<WrappedIngredient> itemTree = new ArrayList<>();

    public SpiritTransmutationPage(String headlineTranslationKey, Item start) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_transmutation_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        if (DatagenModLoader.isRunningDataGen()) {
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
    public void render(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        if (!isRepeat) {
            if (ScreenParticleHandler.canSpawnParticles) {
                TRANSMUTATION_PARTICLES.tick();
            }
            ScreenParticleHandler.renderParticles(TRANSMUTATION_PARTICLES);
        }
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderComponent(screen, guiGraphics, itemTree.get(0), guiLeft + 67, guiTop + 44, mouseX, mouseY);
        renderComponent(screen, guiGraphics, itemTree.get(itemTree.size() - 1), guiLeft + 67, guiTop + 126, mouseX, mouseY);

        int leftStart = guiLeft + 77 - (itemTree.size())*10;
        for (int i = 1; i < itemTree.size()-1; i++) {
            WrappedIngredient wrappedIngredient = itemTree.get(i);
            renderComponent(screen, guiGraphics, wrappedIngredient, leftStart+i*20, guiTop + 85, mouseX, mouseY);
        }
        spawnParticles(guiLeft + 75, guiTop + 94, false);
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 218 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderComponent(screen, guiGraphics, itemTree.get(0), guiLeft + 209, guiTop + 44, mouseX, mouseY);
        renderComponent(screen, guiGraphics, itemTree.get(itemTree.size() - 1), guiLeft + 209, guiTop + 126, mouseX, mouseY);
        int leftStart = guiLeft + 199 + (itemTree.size())*10;
        for (int i = 1; i < itemTree.size()-1; i++) {
            WrappedIngredient wrappedIngredient = itemTree.get(itemTree.size() - i);
            renderComponent(screen, guiGraphics, wrappedIngredient, leftStart-i*20, guiTop + 85, mouseX, mouseY);
        }
        spawnParticles(guiLeft + 218, guiTop + 94, true);
    }

    public void spawnParticles(float x,float y, boolean rightSide) {
        if (ScreenParticleHandler.canSpawnParticles) {
            RandomSource rand = Minecraft.getInstance().level.random;
            float scale = RandomHelper.randomBetween(rand, 0.2f, 0.4f);
            float spin = RandomHelper.randomBetween(rand, 0.2f, 0.4f);
            final double xOffset = Math.sin(((rightSide ? -1 : 1) *Minecraft.getInstance().level.getGameTime() % 320) / 320f * Math.PI * 2) * 46;
            final double yOffset = Math.sin((Minecraft.getInstance().level.getGameTime() % 80) / 80f * Math.PI * 2) * 6;
            ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.WISP, TRANSMUTATION_PARTICLES)
                    .setTransparencyData(GenericParticleData.create(0.2f, 0.4f, 0.2f).build())
                    .setSpinData(SpinParticleData.create(spin).build())
                    .setScaleData(GenericParticleData.create(0, scale, 0).build())
                    .setColorData(SpiritTypeRegistry.ELDRITCH_SPIRIT.createMainColorData().setCoefficient(0.25f).build())
                    .setLifetime(80)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .spawn(x-xOffset, y+yOffset);
        }
    }
}