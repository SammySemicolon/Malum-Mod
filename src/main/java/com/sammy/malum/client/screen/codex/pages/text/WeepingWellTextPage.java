package com.sammy.malum.client.screen.codex.pages.text;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import com.sammy.malum.registry.client.ScreenParticleRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;
import team.lodestar.lodestone.registry.common.particle.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.ScreenParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneScreenParticleRenderType;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class WeepingWellTextPage extends BookPage {

    private static final ScreenParticleHolder ITEM_PARTICLES = new ScreenParticleHolder();

    private final String headlineTranslationKey;
    private final String translationKey;
    private final ItemStack stack;

    public WeepingWellTextPage(String headlineTranslationKey, String translationKey, ItemStack stack) {
        super(MalumMod.malumPath("textures/gui/book/pages/weeping_well_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
        this.stack = stack;
    }

    public WeepingWellTextPage(String headlineTranslationKey, String translationKey, Item spirit) {
        this(headlineTranslationKey, translationKey, spirit.getDefaultInstance());
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        final ClientLevel level = Minecraft.getInstance().level;
        var rand = level.random;
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, left + 70 - Minecraft.getInstance().font.width(component.getString()) / 2, top + 5);
        renderWrappingText(guiGraphics, translationKey(), left + 6, top + 75, 130);
        if (!isRepeat) {
            if (ScreenParticleHandler.canSpawnParticles) {
                ITEM_PARTICLES.tick();
            }
            ScreenParticleHandler.renderParticles(ITEM_PARTICLES);
        }
        renderItem(screen, guiGraphics, stack, left + 63, top + 38, mouseX, mouseY);

        if (level.getGameTime() % 4L == 0) {
            if (ScreenParticleHandler.canSpawnParticles) {
                int lifetime = 100;
                float scale = 1.85f;
                float spin = 6.28f * (level.getGameTime() / 240f);
                final int x = left + 71;
                final int y = top + 46;
                ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.STAR, ITEM_PARTICLES)
                        .setTransparencyData(GenericParticleData.create(0.02f, 0.4f, 0f).build())
                        .setSpinData(SpinParticleData.create(0).setSpinOffset(spin).build())
                        .setScaleData(GenericParticleData.create(0, scale * 1.2f).build())
                        .setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createColorData().setCoefficient(0.4f).build())
                        .setLifetime(lifetime)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .setRenderType(LodestoneScreenParticleRenderType.LUMITRANSPARENT)
                        .spawn(x, y);
                ScreenParticleBuilder.create(ScreenParticleRegistry.SAW, ITEM_PARTICLES)
                        .setTransparencyData(GenericParticleData.create(0.02f, 0.2f, 0f).build())
                        .setSpinData(SpinParticleData.create(0).setSpinOffset(spin).build())
                        .setScaleData(GenericParticleData.create(0, scale * 0.9f).build())
                        .setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createColorData().setCoefficient(0.4f).build())
                        .setLifetime(lifetime)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .setRenderType(LodestoneScreenParticleRenderType.LUMITRANSPARENT)
                        .spawn(x, y);
            }
        }
    }
}
