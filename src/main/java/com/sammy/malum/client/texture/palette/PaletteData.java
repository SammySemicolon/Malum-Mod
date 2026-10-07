package com.sammy.malum.client.texture.palette;

import com.google.common.collect.*;
import net.minecraft.util.*;

import java.util.*;

public class PaletteData {
    protected final List<Integer> colors;

    public PaletteData(List<Integer> colors) {
        this.colors = ImmutableList.copyOf(colors);
    }

    public float[] bakeUniform() {
        float[] array = new float[48];
        for (int i = 0; i < colors.size(); i++) {
            var index = i * 3;
            var color = colors.get(i);
            int red = FastColor.ABGR32.red(color);
            int green = FastColor.ABGR32.green(color);
            int blue = FastColor.ABGR32.blue(color);
            array[index] = red / 255f;
            array[index+1] = green / 255f;
            array[index+2] = blue / 255f;
        }
        return array;
    }
}
