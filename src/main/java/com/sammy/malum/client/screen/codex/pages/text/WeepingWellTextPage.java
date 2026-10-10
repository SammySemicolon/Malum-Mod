//package com.sammy.malum.client.screen.codex.pages.text;
//
//import com.sammy.malum.*;
//import com.sammy.malum.client.screen.codex.helper.*;
//import com.sammy.malum.client.screen.codex.pages.*;
//import com.sammy.malum.client.screen.codex.screens.*;
//import com.sammy.malum.registry.client.*;
//import net.minecraft.client.*;
//import net.minecraft.client.gui.*;
//import net.minecraft.client.multiplayer.*;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.*;
//import team.lodestar.lodestone.handlers.screenparticle.*;
//import team.lodestar.lodestone.registry.common.particle.*;
//import team.lodestar.lodestone.modules.rendering.particle.standard.builder.*;
//import team.lodestar.lodestone.modules.rendering.particle.standard.data.*;
//import team.lodestar.lodestone.modules.rendering.particle.standard.data.color.ColorParticleData;
//import team.lodestar.lodestone.modules.rendering.particle.standard.data.spin.*;
//import team.lodestar.lodestone.modules.rendering.particle.standard.render_types.*;
//import team.lodestar.lodestone.modules.rendering.particle.standard.screen.*;
//
//import java.awt.*;
//
//public class WeepingWellTextPage extends BookPage {
//
//    private static final ScreenParticleHolder ITEM_PARTICLES = new ScreenParticleHolder();
//
//    private final String headlineTranslationKey;
//    private final String translationKey;
//    private final ItemStack stack;
//
//    public WeepingWellTextPage(String headlineTranslationKey, String translationKey, ItemStack stack) {
//        this.headlineTranslationKey = headlineTranslationKey;
//        this.translationKey = translationKey;
//        this.stack = stack;
//    }
//
//    public WeepingWellTextPage(String headlineTranslationKey, String translationKey, Item spirit) {
//        this(headlineTranslationKey, translationKey, spirit.getDefaultInstance());
//    }
//
//    @Override
//    public ResourceLocation getBackground() {
//        return MalumMod.malumPath("textures/gui/book/pages/weeping_well_page.png");
//    }
//
//    public String headlineTranslationKey() {
//        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
//    }
//
//    public String translationKey() {
//        return PageLangKeyCommons.textKey(translationKey);
//    }
//
//    @Override
//    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
//        final ClientLevel level = Minecraft.getInstance().level;
//        Component component = Component.translatable(headlineTranslationKey());
////        CodexTextHelper.renderText(guiGraphics, component, left + 70 - Minecraft.getInstance().font.width(component.getString()) / 2f, top + 5);
////        CodexTextHelper.renderWrappingText(guiGraphics, translationKey(), left + 6, top + 75, 130);
//        if (!isRepeat) {
//            if (ScreenParticleHandler.canSpawnParticles) {
//                ITEM_PARTICLES.tick();
//            }
//            ITEM_PARTICLES.render();
//        }
//        CodexItemHelper.renderItem(screen, guiGraphics, stack, left + 63, top + 38, mouseX, mouseY);
//
//        if (level.getGameTime() % 4L == 0) {
//            if (ScreenParticleHandler.canSpawnParticles) {
//                int lifetime = 100;
//                float scale = 1.85f;
//                float spin = 6.28f * (level.getGameTime() / 240f);
//                final int x = left + 71;
//                final int y = top + 46;
//                ScreenParticleBuilder.create(LodestoneScreenParticleTypes.STAR, ITEM_PARTICLES)
//                        .setTransparencyData(GenericParticleData.create(0.02f, 0.3f, 0f).build())
//                        .setSpinData(SpinParticleData.create(0).setSpinOffset(spin).build())
//                        .setScaleData(GenericParticleData.create(0, scale * 1.6f).build())
//                        .setColorData(ColorParticleData.create(new Color(78, 17, 43), new Color(78, 17, 43)).setCoefficient(0.8f).build())
//                        .setLifetime(lifetime)
//                        .setRenderType(LodestoneScreenParticleRenderType.LUMITRANSPARENT)
//                        .spawn(x, y);
//                ScreenParticleBuilder.create(MalumScreenParticles.SAW, ITEM_PARTICLES)
//                        .setTransparencyData(GenericParticleData.create(0.02f, 0.3f, 0f).setCoefficient(1.2f).build())
//                        .setSpinData(SpinParticleData.create(0).setSpinOffset(spin).build())
//                        .setScaleData(GenericParticleData.create(0, scale * 0.9f).build())
//                        .setColorData(ColorParticleData.create(new Color(255, 133, 155), new Color(78, 17, 43)).setCoefficient(0.8f).build())
//                        .setLifetime(lifetime)
//                        .setRenderType(LodestoneScreenParticleRenderType.LUMITRANSPARENT)
//                        .spawn(x, y);
//            }
//        }
//    }
//}
