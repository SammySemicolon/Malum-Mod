package com.sammy.malum.client.texture.palette;

import com.mojang.blaze3d.platform.*;
import com.sammy.malum.client.texture.*;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.List;

public class LoadedDyePalettes extends LoadedTexture {

    protected final HashMap<DyeColor, PaletteData> palettes = new HashMap<>();
    protected final int colorAmount;

    public LoadedDyePalettes(String readLocation, int colorAmount) {
        super(readLocation);
        this.colorAmount = colorAmount;
    }

    public LoadedDyePalettes(ResourceLocation readLocation, int colorAmount) {
        super(readLocation);
        this.colorAmount = colorAmount;
    }

    public PaletteData get(DyeColor color) {
        return palettes.get(color);
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
    }

    public PaletteData readPalette(NativeImage image, int x, int y) {
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < colorAmount; i++) {
            int rgba = image.getPixelRGBA(x + i, y);
            colors.add(rgba);
        }
        return new PaletteData(colors);
    }
}