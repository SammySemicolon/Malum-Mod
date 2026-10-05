package com.sammy.malum.client.renderer.texture.request.load;

import com.google.common.collect.*;
import com.mojang.blaze3d.platform.*;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.List;

public class DyePalettesLoadRequest extends NativeTextureLoadRequest {

    protected final HashMap<DyeColor, PaletteData> palettes = new HashMap<>();
    protected final int colorAmount;

    public DyePalettesLoadRequest(String readLocation, int colorAmount) {
        super(readLocation);
        this.colorAmount = colorAmount;
    }

    public DyePalettesLoadRequest(ResourceLocation readLocation, int colorAmount) {
        super(readLocation);
        this.colorAmount = colorAmount;
    }

    @Override
    public void reload(ResourceManager manager) {
        super.reload(manager);
        palettes.clear();
        if (image != null) {
            var dyes = DyeColor.values();
            for (int i = 0; i < dyes.length; i++) {
                if (i > 15) {
                    //TODO: Dye Depot Compat
                    break;
                }
                int x = 1;
                int y = i * 2;
                palettes.put(dyes[i], readPalette(image, x, y));
            }
        }
        float f = 0;
    }

    public PaletteData readPalette(NativeImage image, int x, int y) {
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < colorAmount; i++) {
            int rgba = image.getPixelRGBA(x + i, y);
            colors.add(rgba);
        }
        return new PaletteData(colors);
    }

    public static class PaletteData {
        protected final List<Integer> colors;

        public PaletteData(List<Integer> colors) {
            this.colors = ImmutableList.copyOf(colors);
        }
    }
}