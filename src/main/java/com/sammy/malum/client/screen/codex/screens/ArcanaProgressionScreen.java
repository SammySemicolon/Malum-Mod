package com.sammy.malum.client.screen.codex.screens;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.entries.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.recipe.*;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.*;
import com.sammy.malum.client.screen.codex.pages.text.*;
import com.sammy.malum.common.events.*;
import com.sammy.malum.common.item.codex.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraftforge.common.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class ArcanaProgressionScreen extends AbstractProgressionCodexScreen<ArcanaProgressionScreen> {

    public static final ResourceLocation BACKGROUND_TEXTURE = malumPath("textures/gui/book/background.png");

    public static ArcanaProgressionScreen screen;

    public static final List<BookEntry<ArcanaProgressionScreen>> ENTRIES = new ArrayList<>();

    public boolean isVoidTouched;

    protected ArcanaProgressionScreen() {
        super(1024, 2560);
        minecraft = Minecraft.getInstance();
        setupEntries();
        MinecraftForge.EVENT_BUS.post(new SetupMalumCodexEntriesEvent());
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
        screen.faceObject(screen.bookObjectHandler.getBookObjects().get(0));
        screen.playSound(SoundRegistry.ARCANA_TRANSITION_NORMAL, 1.25f, 1f);
        screen.timesTransitioned++;
        screen.transitionTimer = screen.getTransitionDuration();
        EncyclopediaEsotericaItem.shouldOpenVoidCodex = false;
    }

    @Override
    public void renderBackground(PoseStack poseStack) {
        renderBackground(poseStack, BACKGROUND_TEXTURE, 0.1f, 0.4f);
    }

    @Override
    public Collection<BookEntry<ArcanaProgressionScreen>> getEntries() {
        return ENTRIES;
    }

    @Override
    public Supplier<SoundEvent> getSweetenerSound() {
        return SoundRegistry.ARCANA_SWEETENER_NORMAL;
    }

    public void setupEntries() {
        addEntry("chronicles_of_the_void", 0, -1, b -> b
                .setWidgetSupplier((s, e, x, y) -> new ScreenOpenerObject<>(s, e, x, y, VoidProgressionScreen::openCodexViaTransition, malumPath("textures/gui/book/icons/void_button.png"), 20, 20))
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.DARK_GRAND_RUNEWOOD).setValidityChecker(p -> p.isVoidTouched))
        );

        IntroductionEntries.setupEntries(this);
        ArtificeEntries.setupEntries(this);
        AugmentationEntries.setupEntries(this);
        TinkeringEntries.setupEntries(this);
        RuneWorkingEntries.setupEntries(this);
        TotemMagicEntries.setupEntries(this);

        addEntry("ritual_magic", 0, 17, b -> b
                .setWidgetConfig(w -> w.setIcon(RITUAL_PLINTH).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
                .addPage(new HeadlineTextPage<>("ritual_magic", "ritual_magic.1"))
        );
//        RitualEntries.setupEntries(ENTRIES);

        addEntry("necklace_of_the_mystic_mirror", 6, 12, b -> b
                .setWidgetConfig(w -> w.setIcon(NECKLACE_OF_THE_MYSTIC_MIRROR))
                .addPage(new HeadlineTextPage<>("necklace_of_the_mystic_mirror", "necklace_of_the_mystic_mirror.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_MYSTIC_MIRROR.get()))
        );

        addEntry("mirror_magic", 6, 10, b -> b
                .setWidgetConfig(w -> w.setIcon(SPECTRAL_LENS).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage<>("mirror_magic", "mirror_magic.1"))
                .addPage(SpiritInfusionPage.fromOutput(SPECTRAL_LENS.get()))
        );

        addEntry("voodoo_magic", -6, 10, b -> b
                .setWidgetConfig(w -> w.setIcon(POPPET).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage<>("voodoo_magic", "voodoo_magic.1"))
                .addPage(SpiritInfusionPage.fromOutput(POPPET.get()))
        );

        addEntry("belt_of_the_magebane", -2, 14, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.SOULWOOD).setIcon(BELT_OF_THE_MAGEBANE))
                .addPage(new HeadlineTextPage<>("belt_of_the_magebane", "belt_of_the_magebane.1"))
                .addPage(new TextPage<>("belt_of_the_magebane.2"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_MAGEBANE.get()))
        );

        addEntry("tyrving", 2, 14, b -> b
                .setWidgetConfig(w -> w.setStyle(BookWidgetStyle.SOULWOOD).setIcon(TYRVING))
                .addPage(new HeadlineTextPage<>("tyrving", "tyrving.1"))
                .addPage(SpiritInfusionPage.fromOutput(TYRVING.get()))
                .addPage(new TextPage<>("tyrving.2"))
                .addPage(SpiritRepairPage.fromInput(TYRVING.get()))
        );

        addEntry("the_device", 0, -10, b -> b
                .setWidgetSupplier(VanishingEntryObject::new)
                .setWidgetConfig(w -> w.setIcon(THE_DEVICE))
                .addPage(new HeadlineTextPage<>("the_device", "the_device"))
                .addPage(new CraftingPage<>(THE_DEVICE.get(),
                        TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get(),
                        TAINTED_ROCK.get(), TWISTED_ROCK.get(), TAINTED_ROCK.get(),
                        TWISTED_ROCK.get(), TAINTED_ROCK.get(), TWISTED_ROCK.get()))
        );
    }
}