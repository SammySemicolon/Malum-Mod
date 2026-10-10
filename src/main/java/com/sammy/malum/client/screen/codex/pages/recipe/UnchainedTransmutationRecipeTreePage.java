//package com.sammy.malum.client.screen.codex.pages.recipe;
//
//import com.sammy.malum.MalumMod;
//import com.sammy.malum.client.screen.codex.helper.*;
//import com.sammy.malum.client.screen.codex.pages.*;
//import com.sammy.malum.client.screen.codex.screens.CodexEntryScreen;
//import com.sammy.malum.common.recipe.UnchainedTransmutationRecipe;
//import com.sammy.malum.registry.client.MalumScreenParticles;
//import com.sammy.malum.registry.common.MalumContent;
//import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
//import com.sammy.malum.registry.common.recipe.*;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.Mth;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.crafting.*;
//import net.minecraft.world.level.Level;
//import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;
//
//import team.lodestar.lodestone.modules.core.easing.Easing;
//import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneRecipeSearch;
//import team.lodestar.lodestone.modules.rendering.particle.standard.builder.ScreenParticleBuilder;
//import team.lodestar.lodestone.modules.rendering.particle.standard.data.GenericParticleData;
//import team.lodestar.lodestone.modules.rendering.particle.standard.data.spin.SpinParticleData;
//import team.lodestar.lodestone.modules.rendering.particle.standard.screen.ScreenParticleHolder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UnchainedTransmutationRecipeTreePage extends BookPage {
//
//    private static final ScreenParticleHolder TRANSMUTATION_PARTICLES = new ScreenParticleHolder();
//
//    private final Component headline;
//    private final List<Ingredient> itemTree = new ArrayList<>();
//
//    public UnchainedTransmutationRecipeTreePage(String headline, Item start) {
//        this.headline = Component.translatable(PageLangKeyCommons.headlineKey(headline));
//
//        Level level = Minecraft.getInstance().level;
//        if (level != null) {
//            var search = LodestoneRecipeSearch.search(level, MalumRecipeTypes.UNCHAINED_TRANSMUTATION);
//            var input = new SingleRecipeInput(start.getDefaultInstance());
//            UnchainedTransmutationRecipe recipe;
//            while (true) {
//                recipe = search.findRecipe(input);
//                if (recipe == null) {
//                    itemTree.add(Ingredient.of(MalumContent.Blight.BLIGHTED_EARTH.get()));
//                    break;
//                }
//                itemTree.add(recipe.getInput());
//                input = new SingleRecipeInput(recipe.getOutputRaw());
//            }
//        }
//    }
//
//    @Override
//    public ResourceLocation getBackground() {
//        return MalumMod.malumPath("textures/gui/book/pages/transmutation_recipe_tree_page.png");
//    }
//
//    @Override
//    public boolean isValid() {
//        return !itemTree.isEmpty();
//    }
//
//    @Override
//    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
////        CodexTextHelper.renderHeadline(guiGraphics, headline, left, top);
//        if (!isRepeat) {
//            if (ScreenParticleHandler.canSpawnParticles) {
//                TRANSMUTATION_PARTICLES.tick();
//            }
//            TRANSMUTATION_PARTICLES.render();
//        }
//        CodexItemHelper.renderIngredient(screen, guiGraphics, itemTree.getFirst(), left + 63, top + 38, mouseX, mouseY);
//        CodexItemHelper.renderIngredient(screen, guiGraphics, itemTree.getLast(), left + 63, top + 142, mouseX, mouseY);
//
//        int leftStart = left + 73 - (itemTree.size())*10;
//        for (int i = 1; i < itemTree.size()-1; i++) {
//            CodexItemHelper.renderIngredient(screen, guiGraphics, itemTree.get(i), leftStart+i*20, top + 90, mouseX, mouseY);
//        }
//
//        renderRecipeInfo(guiGraphics, screen, "unchained_transmutation_tree", left + 62, top + 60, mouseX, mouseY);
//
//        int particlesX = left + 25;
//        int particlesY = top + 98;
//        if (ScreenParticleHandler.canSpawnParticles) {
//            var level = Minecraft.getInstance().level;
//            RandomSource rand = level.random;
//            long time = level.getGameTime();
//            for (int i = 0; i < 36; i++) {
//                int yOffsetScale = 4 + Mth.floor(i/4f);
//                float scale = Easing.SINE_IN_OUT.asWeighedRandom(rand, 0.6f, 0.9f);
//                float spin = Easing.SINE_IN_OUT.asWeighedRandom(rand, 0.2f, 0.4f);
//                float xTime = ((time + i * 33) % 240) / 240f;
//                float yTime = ((time + i * 27) % 100f) / 100f;
//                final double xOffset = 92 * xTime;
//                final double yOffset = Math.sin(yTime * 6.28f) * yOffsetScale;
//                ScreenParticleBuilder.create(MalumScreenParticles.LIGHT_SPEC, TRANSMUTATION_PARTICLES)
//                        .setTransparencyData(GenericParticleData.create(0.2f, 0.4f, 0f).build())
//                        .setSpinData(SpinParticleData.create(spin).build())
//                        .setScaleData(GenericParticleData.create(0, scale, 0).build())
//                        .setColorData(MalumSpiritTypes.ARCANE_SPIRIT.createColorData().setCoefficient(0.75f).build())
//                        .setLifetime(i % 2 == 0 ? 20 : 40)
//                        .setLifeDelay(i % 3 == 0 ? 0 : 4)
//                        .spawn(particlesX + xOffset, particlesY + yOffset);
//            }
//        }
//    }
//}
