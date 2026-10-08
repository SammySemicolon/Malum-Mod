package com.sammy.malum.client.screen.codex.pages.recipe.vanilla;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.display.*;
import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.registry.common.util.building.WoodBlockSet;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.client.screen.codex.display.gizmo.DisplayedItem.item;

public class CraftingPage extends BookPage implements IGizmoHolder {

    public static final ResourceLocation CRAFTING_SEGMENTS = MalumMod.malumPath("textures/gui/book/entry_elements/crafting_segments.png");

    public record CraftingGridContents(DisplayedGizmo[] inputs) {

        public CraftingGridContents() {
            this(new DisplayedGizmo[9]);
        }

        public CraftingGridContents fill(DisplayedGizmo display) {
            Arrays.fill(inputs, display);
            return this;
        }

        @SafeVarargs
        public final CraftingGridContents fill(DisplayedGizmo display, BiConsumer<CraftingGridContents, DisplayedGizmo>... fillers) {
            for (BiConsumer<CraftingGridContents, DisplayedGizmo> filler : fillers) {
                filler.accept(this, display);
            }
            return this;
        }

        public CraftingGridContents topLayer(DisplayedGizmo display) {
            return topLeft(display).top(display).topRight(display);
        }

        public CraftingGridContents middleLayer(DisplayedGizmo display) {
            return left(display).middle(display).right(display);
        }

        public CraftingGridContents bottomLayer(DisplayedGizmo display) {
            return bottomLeft(display).bottom(display).bottomRight(display);
        }

        public CraftingGridContents topLeft(DisplayedGizmo display) {
            return set(0, display);
        }

        public CraftingGridContents top(DisplayedGizmo display) {
            return set(1, display);
        }

        public CraftingGridContents topRight(DisplayedGizmo display) {
            return set(2, display);
        }

        public CraftingGridContents left(DisplayedGizmo display) {
            return set(3, display);
        }

        public CraftingGridContents middle(DisplayedGizmo display) {
            return set(4, display);
        }

        public CraftingGridContents right(DisplayedGizmo display) {
            return set(5, display);
        }

        public CraftingGridContents bottomLeft(DisplayedGizmo display) {
            return set(6, display);
        }

        public CraftingGridContents bottom(DisplayedGizmo display) {
            return set(7, display);
        }

        public CraftingGridContents bottomRight(DisplayedGizmo display) {
            return set(8, display);
        }

        public CraftingGridContents set(int index, DisplayedGizmo display) {
            inputs[index] = display;
            return this;
        }
    }

    private final DisplayedGizmo output;
    private final CraftingGridContents gridContents;


    public static CraftingPage pedestal(WoodBlockSet set) {
        return pedestal(item(set.itemPedestal), item(set.planks.block), item(set.planks.slab));
    }

    public static CraftingPage pedestal(DisplayedGizmo pedestal, DisplayedGizmo block, DisplayedGizmo slab) {
        return new CraftingPage(pedestal, c -> c.topLayer(slab).middle(block).bottomLayer(slab));
    }

    public static CraftingPage stand(WoodBlockSet set) {
        return stand(item(set.itemPedestal), item(set.planks.block), item(set.planks.slab));
    }

    public static CraftingPage stand(DisplayedGizmo pedestal, DisplayedGizmo block, DisplayedGizmo slab) {
        return new CraftingPage(pedestal, c -> c.middleLayer(slab).bottomLayer(block));
    }

    public static CraftingPage compacting(DisplayedGizmo block, DisplayedGizmo input) {
        return new CraftingPage(block, c -> c.fill(input));
    }

    public static CraftingPage crafting(DisplayedGizmo block, Consumer<CraftingGridContents> builder) {
        return new CraftingPage(block, builder);
    }

    protected CraftingPage(DisplayedGizmo output, Consumer<CraftingGridContents> builder) {
        this.output = output;
        this.gridContents = new CraftingGridContents();
        builder.accept(gridContents);
    }

    @Override
    public ResourceLocation getBackground() {
        return MalumMod.malumPath("textures/gui/book/pages/crafting_page.png");
    }

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {

        var segments = VFXBuilders.createScreen().setTexture(CRAFTING_SEGMENTS)
                        .setShader(GameRenderer::getPositionTexColorShader);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int index = x + y * 3;
                var display = gridContents.inputs[index];
                if (display == null) {
                    continue;
                }
                int itemPosX = left + 43 + x * 20;
                int itemPosY = top + 50 + y * 20;

                segments.setPositionWithWidth(itemPosX, itemPosY, 16, 16)
                        .setUVWithWidth(x*20, y*20, 18, 18, 58, 58)
                        .blit(guiGraphics.pose());

                display.render(screen, this, guiGraphics, itemPosX, itemPosY, mouseX, mouseY, partialTicks);
            }
        }

        output.render(screen, this, guiGraphics, left + 63, top + 162, mouseX, mouseY, partialTicks);
    }
}