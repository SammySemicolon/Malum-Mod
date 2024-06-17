package com.sammy.malum.client.screen.codex.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.BookWidgetStyle;
import com.sammy.malum.client.screen.codex.PlacedBookEntry;
import com.sammy.malum.client.screen.codex.entries.*;
import com.sammy.malum.client.screen.codex.objects.progression.ScreenOpenerObject;
import com.sammy.malum.client.screen.codex.objects.progression.VanishingEntryObject;
import com.sammy.malum.client.screen.codex.pages.recipe.SpiritInfusionPage;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.CraftingPage;
import com.sammy.malum.client.screen.codex.pages.text.HeadlineTextPage;
import com.sammy.malum.common.events.MalumCodexEvents;
import com.sammy.malum.common.item.codex.EncyclopediaEsotericaItem;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class ArcanaProgressionScreen extends AbstractProgressionCodexScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = malumPath("textures/gui/book/background.png");

    public static ArcanaProgressionScreen screen;

    public static final List<PlacedBookEntry> ENTRIES = new ArrayList<>();

    protected ArcanaProgressionScreen() {
        super(SoundRegistry.ARCANA_SWEETENER_NORMAL, 1024, 2560);
        minecraft = Minecraft.getInstance();
        setupEntries();
        MalumCodexEvents.EVENT.invoker().onSetup(ENTRIES);
        setupObjects();
    }

    public static ArcanaProgressionScreen getScreenInstance() {
        if (screen == null) {
            screen = new ArcanaProgressionScreen();
        }
        return screen;
    }

    public static void openCodexViaItem(boolean isVoidTouched) {
        final ArcanaProgressionScreen screenInstance = getScreenInstance();
        screenInstance.openScreen(true);
        screenInstance.isVoidTouched = isVoidTouched;
        screen.playSweetenedSound(SoundRegistry.ARCANA_CODEX_OPEN, 1.25f);
    }

    public static void openCodexViaTransition() {
        getScreenInstance().openScreen(false);
        screen.faceObject(screen.bookObjectHandler.get(0));
        screen.playSound(SoundRegistry.ARCANA_TRANSITION_NORMAL, 1.25f, 1f);
        screen.timesTransitioned++;
        screen.transitionTimer = screen.getTransitionDuration();
        EncyclopediaEsotericaItem.shouldOpenVoidCodex = false;
    }

    @Override
    public void renderBackground(PoseStack poseStack) {
        renderBackground(poseStack, BACKGROUND_TEXTURE, 0.2f, 0.4f);
    }

    @Override
    public Collection<PlacedBookEntry> getEntries() {
        return ENTRIES;
    }

    public void setupEntries() {
        addEntry("chronicles_of_the_void", 0, -1, b -> b
            .setWidgetSupplier((e, x, y) -> new ScreenOpenerObject(e, x, y, VoidProgressionScreen::openCodexViaTransition, malumPath("textures/gui/book/icons/void_button.png"), 20, 20))
            .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_GRAND_RUNEWOOD).setValidityChecker(p -> p.isVoidTouched))
        );

        IntroductionEntries.setupEntries(this);
        ArtificeEntries.setupEntries(this);
        AugmentationEntries.setupEntries(this);
        TinkeringEntries.setupEntries(this);
        RuneWorkingEntries.setupEntries(this);
        TotemMagicEntries.setupEntries(this);

        addEntry("ritual_magic", 0, 17, b -> b
            .configureWidget(w -> w.setIcon(RITUAL_PLINTH).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
            .addPage(new HeadlineTextPage("ritual_magic", "ritual_magic.1"))
        );
//        RitualEntries.setupEntries(ENTRIES);

        addEntry("mirror_magic", 8, 13, b -> b
            .configureWidget(w -> w.setIcon(SPECTRAL_LENS).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
            .addPage(new HeadlineTextPage("mirror_magic", "mirror_magic.1"))
            .addPage(SpiritInfusionPage.fromOutput(SPECTRAL_LENS.get()))
        );

        addEntry("voodoo_magic", -8, 13, b -> b
            .configureWidget(w -> w.setIcon(POPPET).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
            .addPage(new HeadlineTextPage("voodoo_magic", "voodoo_magic.1"))
            .addPage(SpiritInfusionPage.fromOutput(POPPET.get()))
        );

        addEntry("the_device", 0, -10, b -> b
            .setWidgetSupplier(VanishingEntryObject::new)
            .configureWidget(w -> w.setIcon(THE_DEVICE).setStyle(BookWidgetStyle.WITHERED)) // todo: judge if withered frame and tooltip disabling is good
            .disableTooltip()
            .addPage(new HeadlineTextPage("the_device", "the_device"))
            .addPage(new CraftingPage(THE_DEVICE.get(),
                TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get(),
                TAINTED_ROCK.get(), TWISTED_ROCK.get(), TAINTED_ROCK.get(),
                TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get()))
        );
    }
}
